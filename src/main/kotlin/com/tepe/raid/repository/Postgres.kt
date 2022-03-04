package com.tepe.raid.repository

import com.tepe.raid.dto.RaidSetting
import com.tepe.raid.interfaces.Implementation
import com.tepe.raid.utils.password
import com.tepe.raid.utils.postgresConnection
import com.tepe.raid.utils.user
import java.sql.*
import kotlin.random.Random

class Postgres : Implementation {
    private var connection: Connection? = null
    private var statement: Statement? = null
    private var successful = false

    override fun createDisk(group: Int, order: Int, type: RaidSetting, size: Int): String? {
        val name = "disk_${Random.nextLong(999_999_999_999_999_999)}"
        execute(
            "CREATE TABLE $name (" +
                    " disk_id INT NOT NULL," +
                    " position SERIAL PRIMARY KEY," +
                    " content VARCHAR," +
                    " CONSTRAINT fk_${name}_riders FOREIGN KEY (disk_id) REFERENCES Disks(disk_id));"
        )
        if (!successful) return null

        execute(
            "INSERT INTO Disks (disk_name, \"order\", group_id, type) VALUES (" +
                    " '$name'," +
                    " $order," +
                    " $group," +
                    " ${type.ordinal} );"
        )
        if (!successful) return null

        var lastRow = -1
        val value = execute("SELECT currval(pg_get_serial_sequence('$name','disk_id'));")
        value?.next()?.let {
            if (it)
                lastRow = value.getInt(1)
        }
        if (lastRow == -1) return null

        for (i in 0 until size) {
            execute(
                "INSERT INTO $name VALUES (disk_id, content) VALUES (" +
                        " '$lastRow'," +
                        " NULL);"
            )
            if (!successful) return null
        }

        return if (successful) name else null
    }

    override fun createRaid(group: String): Boolean {
        execute("INSERT INTO Groups (tag) VALUES ('$group');")
        return successful
    }

    override fun writeAt(disk: String, data: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun readAt(index: Int, disk:String): String? {
        TODO("Not yet implemented")
    }

    override fun deleteAt(disk: String): Boolean {
        execute("UPDATE $disk SET disk_state = 0::BIT")
        if (!successful) return false
        execute("DROP TABLE $disk")
        return successful
    }

    override fun update(disk: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun startService() {
        if(connection != null
            && !connection!!.isClosed
            && statement != null
            && !statement!!.isClosed) return

        Class.forName("org.postgresql.Driver")
        connection = DriverManager.getConnection(postgresConnection, user, password)

        connection?.let {
            statement = it.createStatement()
            println("Conexión establecida")
        } ?: println("Fallo en la conexión.")
    }

    override fun closeService() {
        statement?.closeOnCompletion()
        connection?.close()
    }

    private fun execute(sql: String): ResultSet? {
        println("Ejecutando sql...")
        statement?.let {
            return try {
                successful = true
                it.executeQuery(sql)
            } catch (ex: SQLException) {
                println("Problema con 'statement'")
                successful = false
                null
            }
        }
        println("Problema con 'statement'")
        successful = false
        return null
    }

}