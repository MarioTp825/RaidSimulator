package com.tepe.raid.controllers

import com.tepe.raid.HelloApplication
import com.tepe.raid.dto.RaidSetting
import com.tepe.raid.dto.RaidSetting.*
import com.tepe.raid.utils.DataContainer
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import java.net.URL
import java.util.*

class HomeController : Initializable {
    private val raid = DataContainer.getInstance()

    @FXML
    private lateinit var apRaidSetting: AnchorPane

    @FXML
    private lateinit var cbRaids: ComboBox<Any>

    @FXML
    private lateinit var tInformation: Text

    @FXML
    private lateinit var tfDiscNumber: TextField

    @FXML
    private lateinit var tfDiscSize: TextField

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        cbRaids.items = FXCollections.observableArrayList(
            "Raid 0",
            "Raid 1",
            "Raid 5",
            "Raid 1 + 0",
            "Raid 0 + 1"
        )

        cbRaids.valueProperty().addListener { _, _, new ->
            tInformation.text = when (new.toString()) {
                "Raid 0" -> "Se requiere como mínimo dos discos."
                "Raid 1" -> "Se requiere como mínimo dos discos."
                "Raid 5" -> "Se requiere como mínimo tres discos."
                "Raid 1 + 0" -> "Se requiere como mínimo 4 discos."
                else -> "Se requiere como mínimo 4 discos."
            } + "\nRequiere como mínimo 20 de espacios."
        }
    }

    @FXML
    fun onCreateRaid(event: ActionEvent) {
        loadSettings(
            when (cbRaids.value.toString()) {
                "Raid 0" -> _0
                "Raid 1" -> _1
                "Raid 5" -> _5
                "Raid 1 + 0" -> _1_0
                else -> _0_1
            }
        )
    }

    private fun loadSettings(st: RaidSetting) {
        if (!loadData(st)) {
            tInformation.text += "\nDatos inválidos, verificar información."
            return
        }

        if (
            when (st) {
                _0 -> raid.diskNumber > 1
                _1 -> raid.diskNumber > 1
                _5 -> raid.diskNumber > 2
                _0_1 -> raid.diskNumber > 3
                else -> raid.diskNumber > 3
            } && raid.diskSize > 19
        ) {
            tInformation.text += "\nDatos inválidos, verificar los tamaños de los discos y su espacio."
            return
        }

        val fxmlLoader = FXMLLoader(
            HelloApplication::class.java.getResource(
                "answer.fxml"
            )
        )
        val scene = Scene(fxmlLoader.load(), 1000.0, 1000.0)
        HelloApplication.stage.title = "Raid Lab"
        HelloApplication.stage.scene = scene
    }

    private fun loadData(st: RaidSetting): Boolean {
        raid.apply {
            clear()
            type = st
            diskNumber = tfDiscNumber.text.toIntOrNull().let { it ?: -1 }
            diskSize = tfDiscSize.text.toIntOrNull().let { it ?: -1 }
        }
        return raid.diskSize != -1 || raid.diskNumber != -1
    }
    //francon.lopezm@gmail.com
}