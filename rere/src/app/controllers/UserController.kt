package com.rere.app.controllers

import com.rere.app.models.UserDao
import io.ktor.features.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.RuntimeException
import java.time.ZoneOffset

class UserController {

    private val logger: Logger = LoggerFactory.getLogger("UserController")

    data class UserInput(val id: Int)
    data class UserOutput(val id: Int, val name: String, val createdAt: Long)
    data class UserOutputList(val userOutput: List<UserOutput>)

    fun get(input: UserInput): UserOutput {
        logger.info("get! ${input.id}")
        val userDao: UserDao? = UserDao.findById(input.id)
        userDao ?: throw NotFoundException("ユーザーが見つかりません") // Not Found対応
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
