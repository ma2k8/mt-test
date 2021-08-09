package com.rere.app.models

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

object UserTable : IntIdTable("users") {
    val name = varchar("name", 50)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}

class UserDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDao>(UserTable)
    var name by UserTable.name
    var createdAt by UserTable.createdAt
    var updatedAt by UserTable.updatedAt
}
