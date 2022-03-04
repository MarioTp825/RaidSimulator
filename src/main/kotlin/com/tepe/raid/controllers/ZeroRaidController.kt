package com.tepe.raid.controllers

import com.tepe.raid.interfaces.Implementation
import com.tepe.raid.repository.Postgres
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TextField
import java.net.URL
import java.util.*

class ZeroRaidController: Initializable {
    private lateinit var dbConnection: Implementation

    @FXML
    private lateinit var tfDiscNumber: TextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        dbConnection = Postgres()
        dbConnection.startService()
    }
}