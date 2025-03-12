//> using file "project.scala"
//> using toolkit latest
import os.Path
println("deleting relevant scalably typed artifacts")


//scalablytyped library directory
val stDir :os.Path =  os.home / ".ivy2" / "local" / "org.scalablytyped"
os.walk(stDir)
    .filter{f => f.baseName.startsWith("ari")}
    .foreach{os.remove.all(_)}



