package com.tepe.raid.interfaces

interface Raid {
    fun add(data: String):Boolean

    fun deleteDisk(disk: String):Boolean

    fun readAt(index:Int): String
}