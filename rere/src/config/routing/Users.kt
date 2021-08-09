package com.rere.config.routing

import com.rere.app.controllers.UserController
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Routing.users() {
    val userController = UserController()
    route("/users") {
        get("/{id}") { input ->
            val input = UserController.UserInput(call.parameters.getOrFail<Int>("id"))
            call.respond(transaction { userController.get(input) })
        }
    }
}