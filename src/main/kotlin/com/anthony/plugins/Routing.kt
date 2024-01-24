package com.anthony.plugins

import com.anthony.Connection
import com.anthony.Tasks
import freemarker.cache.ClassTemplateLoader
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.LinkedHashSet


fun Application.configureRouting() {

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader,
            "templates")
    }

    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        var task ="noTask"

        webSocket("/chat") {
            println("Adding laptop!")
            val thisConnection = Connection(this)
            connections += thisConnection
            try {
                send("Laptops are connected! There are ${connections.count()} laptops here.")
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val textWithUsername = "[${thisConnection.name}]: $receivedText"
                    connections.forEach {
                        it.session.send(textWithUsername)
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $thisConnection!")
                connections -= thisConnection
            }
        }

        get("/") {
            call.respond(FreeMarkerContent("index.ftl",
                mapOf("Tasks" to Tasks(task, LocalDateTime.now().toString().substring(0,19)))))
        }
        get("/task") {
            call.respondText(task)
        }

        get("/shutdown") {
            task="shutdown"
            connections.forEach {
                it.session.send(task)
            }
            call.respondRedirect("/")
        }

        get("/reboot") {
            task="reboot"
            connections.forEach {
                it.session.send(task)
            }
            call.respondRedirect("/")
        }


        get("/noTask") {
            task="noTask"
            connections.forEach {
                it.session.send(task)
            }
            call.respondRedirect("/")
        }
    }
}
