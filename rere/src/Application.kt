package com.rere

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.features.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import com.rere.config.database.DatabaseFactory
import com.rere.config.routing.root
import com.rere.config.routing.users
import io.ktor.jackson.*
import io.ktor.routing.get
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    /*********************************************
     * ErrorHandling
     *********************************************/
    install(StatusPages) {
        data class ErrorResponse(val message: String?)

        exception<NotFoundException> {
            call.respond(HttpStatusCode.NotFound, ErrorResponse(it.message))
        }
        exception<BadRequestException> {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
        }
        exception<ParameterConversionException> {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
        }
        exception<DataConversionException> {
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(it.message))
        }
        exception<Throwable> {
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse(it.message))
        }
    }

    /*********************************************
     * GZip
     *********************************************/
    install(Compression) {
        gzip { priority = 1.0 }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    /**********************************************
     * CORS
     *********************************************/
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    /**********************************************
     * Header
     *********************************************/
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    /**********************************************
     * AuthN
     *********************************************/
    // install(Authentication) {
    // }

    /**********************************************
     * Json Serialize
     *********************************************/
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    /**********************************************
     * Routes
     *********************************************/
    routing {
        root() // /~
        routing { users() } // /users/~
    }

    /**********************************************
     * DbSettings
     *********************************************/
    DatabaseFactory.connect(environment.config)
}

