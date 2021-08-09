package com.rere.app.controllers

class UserController {
    data class UserInput(val id: Int)
    data class UserOutput(val id: Int, val name: String)

    fun get(input: UserInput): UserOutput {
        return UserOutput(input.id, "test") // TODO: DBアクセス
    }
}