package com.tepe.raid.dto

data class DiskHolder(
    val name: String,
    val tag: String? = null,
    var pointer: Int = -1
)
