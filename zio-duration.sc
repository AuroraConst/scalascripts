//> using file "project.scala"
import zio._
import zio.Console._
import zio.Duration._
import java.time.{LocalTime, ZoneId}

object ZioDuration extends ZIOAppDefault:

  case class HMS(hours: Long, minutes: Long, seconds: Long)

  def hms(totalSeconds: Long): HMS =
    val hours   = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    HMS(hours, minutes, seconds)

  val intro: UIO[Unit] =
    ZIO.succeed(println(s"Running daily maintenance at ${LocalTime.now()}"))
  val targetTime = LocalTime.of(14, 30) // 2:30 PM

  val delay =
    // Calculate the delay until the next occurrence of targetTime
    val delayEffect = Clock.currentDateTime.map { now =>
      val nextRun = now.toLocalDate.atTime(targetTime).atZone(now.getOffset)
      val nowInst = now.toInstant
      val calculatedDelay = if (now.toLocalTime.isBefore(targetTime)) {
        Duration.fromInterval(nowInst, nextRun.toInstant())
      } else {
        Duration.fromInterval(nowInst, nextRun.plusDays(1).toInstant())
      }
      calculatedDelay
    }
    delayEffect

  override def run: ZIO[Any, Any, Any] =
    for
      _     <- intro
      delay <- delay
      hms   <- ZIO.succeed(hms(delay.toSeconds))
      _     <- Console.printLine(s"Calculated delay: ${hms.hours} hours, ${hms.minutes} minutes, ${hms.seconds} seconds")
      _     <- Console.printLine(
        s"Next run scheduled in ${delay.toSeconds} seconds or ${delay.toHours()} hours} at ${targetTime}"
      )
    yield ()

ZioDuration.main(Array.empty)
