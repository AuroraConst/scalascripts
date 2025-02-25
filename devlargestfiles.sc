//> using file "project.scala"
//> using toolkit latest
import os.zip.ZipSource
import os.Path
import me.tongfei.progressbar.ProgressBar
println("Analyzing Dev directory for largest files:")


val devdir :os.Path =  os.root / "dev"

def filter(p:Path) = 
    os.isFile(p) && 
    (p.toString.contains("\\dev\\projects\\") ||     p.toString.contains("\\dev\\scripts\\") )
    &&
    !p.toString.contains("\\node_modules\\") &&
    !p.toString.contains("\\.git\\") &&
    !p.toString.contains("\\.bloop\\") &&
    !p.toString.contains("\\.bsp\\") &&
    !p.toString.contains("\\.metals") &&
    !p.toString.contains("\\.scala-build\\") &&
    !p.toString.contains("\\target\\") &&
    !p.toString.contains("\\cache\\https")



val files = os.walk(devdir).filter(filter)

val progressBar = new ProgressBar("Compressing", files.size)

val sortedFiles = files.map {file => (file, os.size(file))}.sortBy((_,size) => size)

val top100Files = sortedFiles.reverse.take(100)

top100Files.foreach{ println(_)}





