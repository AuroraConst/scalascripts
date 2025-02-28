//> using file project.scala

/**
  * packages a script into a bat file that can of course is executable
  */

import scala.sys.process._

val filename = args.mkString(" ")
val filenameNewBatExtension = filename.replaceAll("\\.[^.]+$", ".bat")

//-f forces the file to be written even if it already exists
val cmd = s"scala-cli.bat --power package $filename -o .\\$filenameNewBatExtension -f" 

//by using process, I get all the environmental variables from where it was executed
println("Command line to be exected:")
println(s"$cmd")
val process = Process(cmd)
println("press enter to exit")
scala.io.StdIn.readLine()

val exitCode = process.! 