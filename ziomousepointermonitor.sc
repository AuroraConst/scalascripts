//> using file "project.scala"
import zio._
import zio.Console._
import zio.Duration._
import java.awt.MouseInfo

val scheduledMonitoring = 
  ZIO.attempt(MouseInfo.getPointerInfo.getLocation)
    .tap(pos => Console.printLine(s"Mouse at (${pos.x}, ${pos.y})"))
    .repeat(Schedule.fixed(100.millis))

val waitForEnter = 
  Console.printLine("Press Enter to exit...") *>
  Console.readLine.unit

/**
  *  waitForEnter function: This creates a ZIO effect that prompts the user and
     waits for them to press Enter.

    .race(): This runs both the mouse monitoring and the keyboard input waiting
    concurrently. The .race() method will complete as soon as either effect
    completes - so when you press Enter, it will stop the mouse monitoring.
  */  

object MyAapp extends ZIOAppDefault {
  def run: ZIO[ZIOAppArgs & Scope, Any, Any] = 
    for {
      _ <- Console.printLine("Starting mouse pointer monitoring...")
      _ <- scheduledMonitoring.race(waitForEnter)
      _ <- Console.printLine("Monitoring stopped.")
    } yield ()
}




MyAapp.main(Array.empty)
  