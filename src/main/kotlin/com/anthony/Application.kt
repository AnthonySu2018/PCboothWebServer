package com.anthony

import com.anthony.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*

fun main() {
    embeddedServer(Tomcat, port = 50000, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    configureRouting()

}
