//> using file project.scala


import scala.sys.process._

//remember args are passed like this
// scala-cli.bat .\ex-args.sc -- arg1 arg2
val argString = args.mkString(" ")
println(args.toList)
