//> using file "project.scala"

import zio._
import zio.Console._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigProvider
import better.files._
import File._
import java.io.{File => JFile}

//filename must be .scala because we are running an application note  ascript
//or keep it .sc and call Main.run(args)
object Main extends ZIOAppDefault:
  case class MyConfig(ab:AB)
  case class AB(a:Int, b:Int)
  val configfilename = "configfile.conf"

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    val cfile =  "." / configfilename
    val text =  cfile.contentAsString
    // println(text)

    for {
      ab        <- TypesafeConfigProvider.fromHoconString(text).load(deriveConfig[MyConfig])
      _         <- printLine(s"result: $ab")
  } yield ()
