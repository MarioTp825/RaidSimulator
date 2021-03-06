package com.tepe.raid.interfaces

import com.tepe.raid.dto.RaidSetting

interface Implementation {
    fun createRaid(group: String): Boolean

    fun createDisk(group: Int, order: Int, type: RaidSetting, size: Int): String?

    fun writeAt(disk: String, data: String): Boolean

    fun readAt(index: Int, disk:String): String?

    fun deleteAt(disk: String): Boolean

    fun update(disk: String): Boolean

    fun startService()

    fun closeService()
}