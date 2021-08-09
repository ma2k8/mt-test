package com.rere.app.controllers

import com.rere.app.models.UserDao
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.RuntimeException
import java.time.ZoneOffset

class UserController {
    data class UserInput(val id: Int)
    data class UserOutput(val id: Int, val name: String, val createdAt: Long)
    data class UserOutputList(val userOutput: List<UserOutput>)

    fun get(input: UserInput): UserOutput {
        val userDao: UserDao? = UserDao.findById(input.id)
        userDao ?: throw RuntimeException("user not found") // Not Found対応
        return UserOutput(userDao.id.value, userDao.name, userDao.createdAt.toEpochSecond(ZoneOffset.UTC))
    }

    fun filter(): UserOutputList {
        var res: List<UserOutput> = mutableListOf()
        for (i in 1..500) {
            val userDao: UserDao? = UserDao.findById(i)
            userDao ?: throw RuntimeException("user not found") // Not Found対応
            println(userDao)
            res + UserOutput(userDao.id.value, userDao.name, userDao.createdAt.toEpochSecond(ZoneOffset.UTC))
        }
        return UserOutputList(res.filter { it.id % 2 == 0 })
    }

    fun filterAsync(): UserOutputList {
            var res: List<UserOutput> = mutableListOf()
            for (i in 1..500) {
                CoroutineScope(Dispatchers.Default).launch {
                    withContext(Dispatchers.IO) {
                        transaction {
                            val userDao: UserDao? = UserDao.findById(i)
                        userDao ?: throw RuntimeException("user not found") // Not Found対応
                        println(userDao)
                        res + UserOutput(
                            userDao.id.value,
                            userDao.name,
                            userDao.createdAt.toEpochSecond(ZoneOffset.UTC)
                        )
                    }
                    }
                }
            }
        return UserOutputList(res.filter { it.id % 2 == 0 })
    }


}
