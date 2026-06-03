
import zio.*
import zio.stream._


object Main extends ZIOAppDefault {
  val program = ZStream
    .fromIterable(1 to 10)            // 1. Create a stream from a collection
    .filter(_ % 2 == 0)              // 2. Filter for even numbers
    .map(n => n * n)                 // 3. Square each number
    .run(ZSink.foreach(Console.printLine(_))) // 4. Consume and print


  def run = {

    for {

      _ <- program


    } yield ()
  }
}

Main.main(args)