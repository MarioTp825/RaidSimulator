package com.tepe.raid.implementation.raid

import com.tepe.raid.interfaces.Raid
import com.tepe.raid.repository.Postgres
import com.tepe.raid.utils.DataContainer

class OneZero: Raid {
    private var sql = Postgres()
    private val raid = DataContainer.getInstance()
    private var groups = raid.arrayDisk.groupBy { it.tag }

    init {
        sql.startService()
    }

    override fun add(data: String): Boolean {
        for (list in groups.values) {
            val dNumber = if (data.length < list.size)
                list.size
            else data.length

            val length = if (data.length < list.size)
                data.length / list.size
            else 1
            var start = 0
            var end = length - 1
            val pivot = data.length - 1

            for (i in 0 until dNumber) {
                list[i].let {
                    if (!sql.writeAt(it.name, data.substring(start, end))) return false
                }
                start = end + 1
                end += if ((start + length) > pivot) (pivot - start) else length
            }
            raid.next()
        }
        return true
    }

    override fun deleteDisk(disk: String): Boolean {
        var flag = true
        for (dk in groups[disk]!!) {
            flag = sql.deleteAt(dk.name) && flag
        }

        return flag.also { f ->
            if (f) groups = raid.arrayDisk.groupBy { it.tag }
        }
    }

    override fun readAt(index: Int): String {
        val first = groups.keys.first()!!
        var data = ""
        for(disk in groups[first]!! ) {
            sql.readAt(index, disk.name)?.let {
                data += it
            }
        }
        return data
    }
}