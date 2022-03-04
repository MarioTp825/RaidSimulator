package com.tepe.raid

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class HelloApplication : Application() {

    override fun start(st: Stage) {
        stage = st
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("home.fxml"))
        val scene = Scene(fxmlLoader.load(), 1000.0, 1000.0)
        stage.title = "Raid Lag"
        stage.scene = scene
        stage.show()
    }

    companion object {
        lateinit var stage: Stage
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}