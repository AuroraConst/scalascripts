
//> using file "project.scala"
import zio._
import zio.Console._
import zio.Duration._
import java.time.{LocalTime, ZoneId}

object ZioDuration extends ZIOAppDefault :
  


  val intro: UIO[Unit] = ZIO.succeed(println(s"Running daily maintenance at ${LocalTime.now()}"))
  val targetTime = LocalTime.of(14, 30) // 2:30 PM

  val delay = 
    // Calculate the delay until the next occurrence of targetTime
    val delayEffect = Clock.currentDateTime.map { now =>
      val nextRun = now.toLocalDate.atTime(targetTime).atZone(now.getOffset)
      val calculatedDelay = if (now.toLocalTime.isBefore(targetTime)) {
        Duration.fromInterval(now.toInstant(), nextRun.toInstant())
      } else {
        Duration.fromInterval(now.toInstant(), nextRun.plusDays(1).toInstant())
      }
      calculatedDelay
    }
    delayEffect

  override def run: ZIO[Any, Any, Any]  = 
    for
      _     <- intro
      delay <- delay
      _     <- Console.printLine(s"Next run scheduled in ${delay.toSeconds} seconds or ${delay.toHours()} hours} at ${targetTime}")
    yield ()



ZioDuration.main(Array.empty)
