//> using file "project.scala"
import zio._
import zio.stream.ZStream

object ZStreamAsyncExample extends ZIOAppDefault {
  

  // A mock callback-based API (e.g., an external event listener)
  // It emits an integer every second and stops after 5 events.
  def registerCallback(onNext: Int => Unit, onComplete: () => Unit): Unit = {
    var count = 0
    val timer = new java.util.Timer()

    val timerTask = new java.util.TimerTask {
      def run(): Unit = {
        count += 1
        if (count <= 5) {
          onNext(count)
        } else {
          onComplete()
          timer.cancel()
        }
      }
    } 
    timer.scheduleAtFixedRate(timerTask, 1000, 1000)
  }

  // Create an asynchronous ZStream from the callback API
  val asyncStream: ZStream[Any, Nothing, Int] = {
    ZStream.async[Any, Nothing, Int] { emit =>
      registerCallback(
        onNext = event => emit.single(event),          // Emits a single element
        onComplete = () => emit.end                    // Closes the stream successfully
      )
    }
  }

  // Run the stream and print incoming events
  def run: ZIO[Any, Nothing, Unit] = {
    asyncStream
      .tap(event => Console.printLine(s"Received event: $event").orDie)
      .runDrain
  }
}


ZStreamAsyncExample.main(Array.empty)
