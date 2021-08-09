package com.rere.config.routing

import com.rere.app.controllers.UserController
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*

fun Routing.users() {
    val userController = UserController()
    route("/users") {
        get("/{id}") { input ->
            val input = UserController.UserInput(call.parameters.getOrFail<Int>("id"))
            call.respond(userController.get(input))
        }
    }
}