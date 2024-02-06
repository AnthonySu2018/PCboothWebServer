package com.anthony.plugins

import com.anthony.Connection
import com.anthony.TaskList
import com.sioux.anthony.androidapp.homepage.sendUDP
import freemarker.cache.ClassTemplateLoader
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.title
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.LinkedHashSet
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.routing.*
import kotlinx.html.*


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


        get("/"){

            call.respondHtml(HttpStatusCode.OK){
                head {
                    title {
                        +"Anthony's design"
                    }
                }
                body {
                    h1{+"This web server is design by Anthony"}
                    h2{+"Data:2024-02-06"}
                    h2{+"Version:2.1.0"}
                    a("http://172.18.1.232:50000/pcbooth"){+"PCbooth Dashboard"}
                    br{}
                    br{}
                    a("http://172.18.1.232:50000/ChangePicture"){+"ChangePicture Dashboard"}
                    br{}
                    br{}
                    a("http://172.18.1.232:50000/PowerSupply"){+"PowerSupply Dashboard"}
                }
            }
        }


        get("/sample"){

            call.respondHtml(HttpStatusCode.OK){
                head {
                    title {
                        +"Anthony's design"
                    }
                }
                body {


                }
            }
        }

        get("/ChangePicture"){

            call.respondHtml(HttpStatusCode.OK){
                head {
                    title {
                        +"ChangePicture"
                    }
                }
                body {
                    h1{+"更换图片后，重启播放盒子"}
                    a("http://172.18.1.232:50000/rebootPrefaceHallBigScreen"){+"重启序厅LCD大屏"}
                    br{}
                    br{}
                    a("http://172.18.1.232:50000/rebootPrefaceHallSmallScreen"){+"重启序厅工控屏"}
                    br{}
                    br{}
                    a("http://172.18.1.232:50000/rebootBackdoorOuterScreen"){+"重启后面厅外大屏"}

                }
            }
        }

        get("/rebootPrefaceHallBigScreen") {
            val remoteIP = "172.18.0.33"
            val remotePort = 50505
            val action = "172.18.0.33MRWETEND"
            sendUDP(remoteIP,remotePort,action)
            call.respondRedirect("/ChangePicture")
        }

        get("/rebootPrefaceHallSmallScreen") {
            val remoteIP = "172.18.0.10"
            val remotePort = 50505
            val action = "172.18.0.10MRWETEND"
            sendUDP(remoteIP,remotePort,action)
            call.respondRedirect("/ChangePicture")
        }

        get("/rebootBackdoorOuterScreen") {
            val remoteIP = "172.18.0.38"
            val remotePort = 50505
            val action = "172.18.0.38MRWETEND"
            sendUDP(remoteIP,remotePort,action)
            call.respondRedirect("/ChangePicture")
        }




        get("/PowerSupply"){

            call.respondHtml(HttpStatusCode.OK){
                head {
                    title {
                        +"PowerSupply"
                    }
                }
                body {
                    h1{+"展厅电源控制"}


                }
            }
        }





        get("/pcbooth") {
            call.respond(FreeMarkerContent("pcbooth.ftl",
                mapOf("TaskList" to TaskList(task, LocalDateTime.now().toString().substring(0,19)))))
        }


        get("/task") {
            call.respondText(task)
        }

        get("/shutdown") {
            task="shutdown"
            connections.forEach {
                it.session.send(task)
            }
            call.respondRedirect("/pcbooth")
        }

        get("/reboot") {
            task="reboot"
            connections.forEach {
                it.session.send(task)
            }
            call.respondRedirect("/pcbooth")
        }


        get("/noTask") {
            task="noTask"
            connections.forEach {
                it.session.send(task)
            }
            call.respondRedirect("/pcbooth")
        }


    }
}
