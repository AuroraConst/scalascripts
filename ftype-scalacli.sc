//> using file project.scala

import scala.sys.process._

/**
  * remember this script needs to run with administrative rights
  */
def setAssoc = {
  val assoc = "cmd /c assoc .sc=ScalaScript"
  
  Process(assoc).!
}  

def setFtype = 
  val ftype =    "cmd /c ftype ScalaScript=c:\\dev\\cs\\scala-cli.bat run %1 %*"  
  Process(ftype).!    


println(s"setAssoc exit code: ${setAssoc}")  
println(s"setAssoc exit code: ${setFtype}")    