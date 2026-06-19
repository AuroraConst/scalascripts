
//> using file "project.scala"
import zio._
import zio.Console._
import zio.Duration._

object Main extends ZIOAppDefault :
  val waitForQuit: ZIO[Any, java.io.IOException, Unit] =
    Console.readLine.flatMap {
      case "quit" => ZIO.debug("Exiting timer loop...")
      case _      => waitForQuit // Loop input again if it's not "quit"
    }
  val timerLoop = ZIO.debug("Tick...") *> ZIO.sleep(1.seconds)
  
  def run: ZIO[Any, Any, Any] = 
    // Concurrent race between our ticking loop and waiting for keyboard input
    timerLoop.repeat(Schedule.forever).race(waitForQuit)



 Main.main(Array.empty)