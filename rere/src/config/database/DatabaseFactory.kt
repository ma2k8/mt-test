package com.rere.config.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.slf4j.LoggerFactory

object DatabaseFactory {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun connect(appConf: ApplicationConfig) {
        log.info("Initialising database")
        val pool = hikari(appConf)
        Database.connect(pool)
    }

    private fun hikari(appConf: ApplicationConfig): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "com.mysql.cj.jdbc.Driver"
            jdbcUrl = appConf.property("app.database.url").getString()
            username = appConf.property("app.database.user").getString()
            password = appConf.property("app.database.password").getString()
            maximumPoolSize = 20
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(
        block: suspend () -> T
    ): T =
        newSuspendedTransaction { block() }
}