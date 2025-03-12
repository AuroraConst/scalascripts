//> using file project.scala
//> using toolkit latest

import scala.sys.env
import scala.sys.process._
import pprint.*

/**
  * THIS DOES NOT WORK BECAUSE THE SPAWNED CLI will not accept any user input for some reason
  */

val pathString =env.filter((key,value) => key.toLowerCase().matches ("path")).toList(0)._2.split(";")
// pprint.pprintln(pathString,width=150)

val cmd = s"sbt.bat new yash-a-18/scalajsvscodeext.g8"  


// Convert env to varargs of tuples
val envVarargs: Seq[(String, String)] = env.toSeq

// pprintln(envVarargs, width = 100)
pprint.pprintln(envVarargs.filter((key,v) => key.toLowerCase().startsWith("pa")) ,width=100)
pprint.pprintln("running ... ",width=100)

Process.apply(cmd,cwd = os.pwd.toIO,envVarargs *).!
pprint.pprintln("COMPLETED ... ",width=100)

