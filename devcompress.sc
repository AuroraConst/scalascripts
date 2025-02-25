//> using file "project.scala"
//> using toolkit latest
import os.Path
import me.tongfei.progressbar.ProgressBar
println("Compressing dev directory:")


val devdir :os.Path =  os.root / "dev"


// Compress the directory with a progress bar
val zipFile = os.home / "downloads"/"dev.zip"
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
println("Filtering dev directory completed")
val progressBar = new ProgressBar("Compressing", files.size)


val chunkSize = files.size / 100

def chunk(chunkIndex:Int)  = chunkSize *chunkIndex

val resultList = (0 until 100).foldLeft(List[Seq[Path]]()){
    (acc, i) => 
        val chunkFiles = files.slice(chunk(i), chunk(i+1))
        acc :+ chunkFiles
}

progressBar.stepBy(chunkSize)
resultList.foreach(x => 
    import os.zip.ZipSource.*
    def tupPath(p:Path) = (p, p.relativeTo(devdir).asSubPath)
    os.zip(
        zipFile,
        sources = x .map{p =>fromPathTuple(tupPath(p))}
    )    
    progressBar.stepBy(chunkSize)
)



progressBar.close()
println(s"chunk count: ${resultList.size}")
println(s"Directory compressed to: $zipFile")