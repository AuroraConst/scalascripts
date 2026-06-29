//> using file "project.scala"

import scala.sys.process._

def download = 
    val ytdlp = "C:\\Dev\\tools\\yt-dlp\\yt-dlp.exe" 
    val myplaylist = "https://www.youtube.com/playlist?list=PLxDF0sT61eCDL0MlH3TsZnfkGQRY6Wruw"
    val yturl = "https://www.youtube.com/watch?v=d95J8yzvjbQ"
    val shellcmd = Seq(ytdlp,yturl)
    shellcmd.!



def mvmp4 = 
    val wd = os.pwd
    os.list(wd).filter(x => x.ext == "mp4").foreach{
        p => os.move(p, os.home / "downloads" / "youtube" / p.last, replaceExisting = true, createFolders = true)
    }

// download
mvmp4
