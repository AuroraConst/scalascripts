
//> using file "project.scala"
//> using javaProp ConfigDir=HelloArnolx

import better.files.Dsl.SymbolicOperations
import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

val conf:Config = ConfigFactory.load("configfilereader.conf");
import scala.converters._
val r = conf.entrySet().map{x => x.}
val strResult = r.foldLeft(""){(s, value) => s + value + "\r\n"}

import better.files._
import File._

val f = "."/"output.txt"
f << strResult


    