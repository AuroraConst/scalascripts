
//> using file "project.scala"
//> using javaProp ConfigDir=HelloArnolx

import better.files.Dsl.SymbolicOperations
import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

val conf:Config = ConfigFactory.load("configfilereader.conf");
import scala.jdk.CollectionConverters.*
val r = conf.entrySet().asScala.foldLeft(List[String]()){(a,b ) => a :+ b.getKey() + " : " + b.getValue().unwrapped().toString()}

r.filter{s => s.toLowerCase().contains("java")} foreach(println)

// val strResult = r.foldLeft(""){(s, value) => s + value + "\r\n"}

import better.files._
import File._

val f = "."/"output.txt"


    