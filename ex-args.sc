//> using file project.scala


import scala.sys.process._

//remember args are passed like this
// scala-cli.bat .\filesc -- arg1 arg2
val argString = args.mkString(" ")
println(args.toList)
