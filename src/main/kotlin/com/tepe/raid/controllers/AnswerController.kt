package com.tepe.raid.controllers

import com.tepe.raid.interfaces.Implementation
import com.tepe.raid.repository.Postgres
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ComboBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.AnchorPane
import javafx.scene.text.Text
import java.net.URL
import java.util.*

class AnswerController: Initializable {
    private lateinit var sql: Implementation

    @FXML
    private lateinit var apTables: AnchorPane

    @FXML
    private lateinit var cbDiskName: ComboBox<Any>

    @FXML
    private lateinit var tRetraivedData: Text

    @FXML
    private lateinit var taData: TextArea

    @FXML
    private lateinit var tfColumnNumber: TextField

    @FXML
    fun addRow(event: ActionEvent) {

    }

    @FXML
    fun deleteDisk(event: ActionEvent) {
        sql.deleteAt(cbDiskName.value.toString().trim())
    }

    @FXML
    fun getData(event: ActionEvent) {
        tfColumnNumber.text.toIntOrNull()?.let {
            sql.readAt(it)
        }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        sql = Postgres()
        sql.startService()
    }

}
