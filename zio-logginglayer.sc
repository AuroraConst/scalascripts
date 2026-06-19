//> using file "project.scala"
import zio.* 

object Main extends ZIOAppDefault :
  import scala.Console._

  val addSimpleLogger: ZLayer[Any, Nothing, Unit] =
    Runtime.addLogger((_, _, _, message: () => Any, _, _, _, _) => println(s"${GREEN}${message()}${RESET}"))

  val app =  for {
      _ <- ZIO.log("Application started!")
      _ <- ZIO.log("Press Enter to Exit")
      _ <- Console.readLine
      _ <- ZIO.log(s"${RED}Application is about to exit!${RESET}")
    } yield ()


  def run = 
   app.provide(Runtime.removeDefaultLoggers ++ addSimpleLogger)
end Main   


Main.main(args)