package com.tepe.raid.utils

import com.tepe.raid.dto.DiskHolder
import com.tepe.raid.dto.RaidSetting

class DataContainer {

    var diskSize = -1
    var diskNumber = -1
    var raidName = "NaN"
    var type: RaidSetting? = null
    val arrayDisk = mutableListOf<DiskHolder>()

    fun clear() {
        diskSize = -1
        diskNumber = -1
        raidName = "NaN"
        arrayDisk.clear()
        type = null
    }

    companion object {
        private var ct: DataContainer? = null

        fun getInstance() = if (ct == null) {
            ct = DataContainer()
            ct!!
        } else ct!!
    }
}