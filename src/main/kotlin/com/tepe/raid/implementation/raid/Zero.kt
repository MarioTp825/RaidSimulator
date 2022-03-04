package com.tepe.raid.implementation.raid

import com.tepe.raid.interfaces.Raid
import com.tepe.raid.repository.Postgres
import com.tepe.raid.utils.DataContainer

class Zero : Raid {
    private var sql = Postgres()
    private val raid = DataContainer.getInstance()

    init {
        sql.startService()
    }

    override fun add(data: String): Boolean {
        val dNumber = if (data.length < raid.diskNumber)
            raid.diskNumber
        else data.length

        val length = if (data.length < raid.diskNumber)
            data.length / raid.diskNumber
        else 1
        var start = 0
        var end = length - 1
        val pivot = data.length - 1

        for (i in 0 until dNumber) {
            raid.arrayDisk[i].let {
                if (!sql.writeAt(it.name, data.substring(start, end))) return false
            }
            start = end + 1
            end += if ((start + length) > pivot) (pivot - start) else length
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