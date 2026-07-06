//> using file project.scala

import zio.*

object Main extends ZIOAppDefault {
  def takeAndOffer(queue: Queue[String]) = 
    val takeoffer = for {
      elem <- queue.take  //take an element from the queue (only to have it offered back to the queue later)
        _ <- queue.offer(elem)  //offer it back to the queue
        _ <- Console.printLine(s"$elem").orDie
    } yield ()

    takeoffer.repeat(Schedule.spaced(Duration.fromSeconds(1)))



  def run = {

    for {
      _ <- Console.printLine("Say...")
      queue <- Queue.bounded[String](3)
      _ <- queue.offerAll(List("yo!", "hi!", "hello!"))  //note if there are more than 3 elements, the fiber will be suspended until there is space in the queue
      
      takeAndOfferFiber <- takeAndOffer(queue).fork
      _ <- Console.readLine
      _ <- takeAndOfferFiber.interrupt
      _ <- Console.printLine("Interrupted").orDie

      // _ <- takeAndOfferFiber.join

    } yield ()
  }
}

Main.main(args)