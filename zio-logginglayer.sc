//> using file "project.scala"
import zio.* 

object Main extends ZIOAppDefault :
  val addSimpleLogger: ZLayer[Any, Nothing, Unit] =
    Runtime.addLogger((_, _, _, message: () => Any, _, _, _, _) => println(message()))

  val app =  for {
      _ <- ZIO.log("Application started!")
      _ <- ZIO.log("Press Enter to Exit")
      _ <- Console.readLine
      _ <- ZIO.log("Application is about to exit!")
    } yield ()


  def run = 
   app.provide(Runtime.removeDefaultLoggers ++ addSimpleLogger)
end Main   


Main.main(args)