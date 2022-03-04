package com.tepe.raid.implementation.raid

import com.tepe.raid.interfaces.Implementation
import com.tepe.raid.interfaces.Raid
import com.tepe.raid.repository.Postgres
import com.tepe.raid.utils.DataContainer

class zero : Raid {
    private var sql = Postgres()
    private val raid = DataContainer.getInstance()

    init {
        sql.startService()
    }

    override fun add(data: String): Boolean {
        val length = if (data.length < raid.diskNumber)
            data.length / raid.diskNumber
        else data.length

        for (i in 0 until length) {
            raid.arrayDisk[i].let {
                if (!sql.writeAt(it.name)) return false
            }
        }
        raid.next()
        return true
    }

    override fun deleteDisk(disk: String) = sql.deleteAt(disk)

    override fun readAt(index: Int): String {
        var data = ""
        for (disk in raid.arrayDisk) {
            sql.readAt(index, disk.name)?.let {
                data += it
            }
        }
        return data
    }


}