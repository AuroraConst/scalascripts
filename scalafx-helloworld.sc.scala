//> using file "project.scala"

import scalafx.application.JFXApp3
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Label,Button}
import scalafx.scene.layout.{BorderPane, GridPane}

object HelloWorld extends JFXApp3 :

  override def start(): Unit = {
        // 1. Create the Grid container
    val grid = new GridPane {
      hgap = 10
      vgap = 10
      padding = Insets(20)
    }  

    grid.add  (new Button("Click Me")  {
        onAction = _ => println("Hello World")
    },0,0)

    grid.add(new Label("Hello World " +
        "and welcome to ScalaFX!"), 0, 1)  

    stage = new JFXApp3.PrimaryStage {
    title = "Hello"
    scene = new Scene {
      root = new BorderPane {
      padding = Insets(75)
      center = grid
      }
      }
    }
  }
