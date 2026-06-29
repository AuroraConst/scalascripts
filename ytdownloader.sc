//> using file "project.scala"

import scala.sys.process._

def download = 
    val ytdlp = "C:\\Dev\\tools\\yt-dlp\\yt-dlp.exe" 
    val yturl = "https://www.youtube.com/playlist?list=PLxDF0sT61eCDL0MlH3TsZnfkGQRY6Wruw"
    val shellcmd = Seq(ytdlp,yturl)
    shellcmd.!


// download

def mvmp4 = 
    val wd = os.pwd
    os.list(wd).filter(x => x.ext == "mp4").foreach{
        p => os.move(p, os.home / "downloads" /   p.last, replaceExisting = true)
    }


def mvmp4f1 = 
    val src = os.home / "downloads"
    val result = os.list(src).filter(_.ext == "mp4").foreach{
        p => os.move(p, os.home / "downloads" / "youtube" / p.last, replaceExisting = true, createFolders = true)
    }
    println(result)

mvmp4f1