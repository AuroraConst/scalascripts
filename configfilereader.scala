//> using dep dev.zio::zio-config::4.0.2
//> using dep dev.zio::zio-config-magnolia::4.0.2
//> using dep dev.zio::zio-config-typesafe::4.0.2
//> using dep dev.zio::zio-config-refined::4.0.2
//> using dep com.github.pathikrit::better-files::3.9.2

import zio._
import zio.Console._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigProvider
import better.files._
import File._
import java.io.{File => JFile}

object Main extends ZIOAppDefault:
  case class MyConfig(ab:AB)
  case class AB(a:Int, b:Int)
  val configfilename = "configfilereader.conf"

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    val cfile =  "." / configfilename
    val text =  cfile.contentAsString
    println(text)

    for {
      ab        <- TypesafeConfigProvider.fromHoconString(text).load(deriveConfig[MyConfig])
      _         <- printLine(ab)
  } yield ()
