//> using file "project.scala"

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane

object HelloWorld extends JFXApp3 :

  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
    title = "Hello"
    scene = new Scene {
        root = new BorderPane {
        padding = Insets(75)
        center = new Label("Hello World")
        }
    }
    }
  }

