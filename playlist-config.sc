//> using file "project.scala"

import zio._
import zio.Console._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigProvider
import better.files._
import File._
import java.io.{File => JFile}

import scala.io.AnsiColor

//filename must be .scala because we are running an application note  ascript
//or keep it .sc and call Main.run(args)
object Main extends ZIOAppDefault:
  case class MyConfig(playList:Seq[PlayList])
  case class PlayList(name:String,url:String,description:String)
  val configfilename = "playlist.conf"

  val addSimpleLogger: ZLayer[Any, Nothing, Unit] =
    Runtime.addLogger((_, _, _, message: () => Any, _, _, _, _) => println(message()))

    

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    val cfile =  "." / configfilename
    val text =  cfile.contentAsString

    for {
      ab        <- TypesafeConfigProvider.fromHoconString(text).load(deriveConfig[MyConfig])
      _         <- printLine(s"${AnsiColor.CYAN}result: $ab")
      _         <- ZIO.logInfo(s"result: $ab")
      _         <- printLine(s"${AnsiColor.GREEN}Press Enter to Exit ${AnsiColor.RESET}" )
      _         <- Console.readLine
      _         <- ZIO.log("Application is about to exit!")

  } yield ()

Main.main(args)