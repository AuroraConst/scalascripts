//> using file "project.scala"

import zio._
import zio.Console._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigProvider
import better.files._
import File._
import java.io.{File => JFile}
import scala.sys.process.*

object Main extends ZIOAppDefault:
  val configfilename = "configfilereader.conf"
  val currentDir = "." / ""

  def exec(pshellcmd:String, baseDir:File = currentDir) =
    s"powershell.exe $pshellcmd".!

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    exec("notepad zioconfig.sc")
    
    val filecount = currentDir.list.size

    def result = currentDir.list.foldLeft(""){
      (f,s) => f + s.name + "\r\n"
    }




    for {
      _         <- printLine(result)
  } yield ()
