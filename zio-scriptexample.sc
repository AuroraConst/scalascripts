
 import zio.*

object Main extends ZIOAppDefault :
  def takeAndOffer(queue: Queue[String]) = 
    (for {
      request <- queue.take
        _ <- Console.printLine(s"$request").orDie
        _ <- queue.offer(request)
    } yield ()).repeat(Schedule.spaced(Duration.fromSeconds(1)))

  def run = 
    for {
      _ <- Console.printLine("Say...")
      queue <- Queue.bounded[String](10)
      _ <- queue.offerAll(List("yo!","hi"))
      takeAndOfferFiber <- takeAndOffer(queue).fork
      _ <- takeAndOfferFiber.join
    } yield ()


Main.main(args)
