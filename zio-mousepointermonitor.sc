//> using file "project.scala"
import zio._
import zio.Console._
import zio.Duration._
import java.awt.{MouseInfo, Point}

def monitorMouseChanges: ZIO[Any, Throwable, Unit] = {
  def loop(previousPos: Option[Point]): ZIO[Any, Throwable, Unit] = 
    for {
      currentPos <- ZIO.attempt(MouseInfo.getPointerInfo.getLocation)
      _          <- previousPos match {
                      case None => 
                        Console.printLine(s"Initial mouse position: (${currentPos.x}, ${currentPos.y})")
                      case Some(prev) if prev.x != currentPos.x || prev.y != currentPos.y => 
                        Console.printLine(s"Mouse moved to (${currentPos.x}, ${currentPos.y})")
                      case _ => 
                        ZIO.unit // No change, don't print
                    }
      _          <- ZIO.sleep(10.millis) // Check more frequently for better responsiveness
      _          <- loop(Some(currentPos))
    } yield ()
  
  loop(None)
}

val waitForEnter = 
  Console.printLine("Press Enter to exit...") *>
  Console.readLine.unit

/**
  * monitorMouseChanges function: This creates a recursive ZIO effect that:
  * - Tracks the previous mouse position
  * - Only prints when the position has actually changed
  * - Uses a faster polling interval (10ms) for better responsiveness
  * 
  * .race(): This runs both the mouse monitoring and the keyboard input waiting
  * concurrently. The .race() method will complete as soon as either effect
  * completes - so when you press Enter, it will stop the mouse monitoring.
  */  

object MyAapp extends ZIOAppDefault {
  def run: ZIO[ZIOAppArgs & Scope, Any, Any] = 
    for {
      _ <- Console.printLine("Starting mouse pointer change monitoring...")
      _ <- monitorMouseChanges.race(waitForEnter)
      _ <- Console.printLine("Monitoring stopped.")
    } yield ()
}




MyAapp.main(Array.empty)
  