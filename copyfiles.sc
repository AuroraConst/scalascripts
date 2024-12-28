
import better.files.Dsl.SymbolicOperations

import better.files._
import File._
import java.io.{File => JFile}

val dir = home  / "Downloads"
val matches: Iterator[File] = dir.glob("*.jpg") ++ dir.glob("*.htm") ++ dir.glob("*.webp") ++ dir.glob("*.png")



matches.foreach(f => f.moveToDirectory(root /"dev"/"AURORA"/"images"))

