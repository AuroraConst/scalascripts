//> using file "project.scala"

import scala.sys.process._

val ytdlp = "C:\\Dev\\tools\\yt-dlp\\yt-dlp.exe" 

def download = 
    val mysleepplaylist = "https://www.youtube.com/playlist?list=PLxDF0sT61eCDL0MlH3TsZnfkGQRY6Wruw"
    val yturl = "https://www.youtube.com/watch?v=0YVvcTIGy40"
    val shellcmd = Seq(ytdlp,yturl)
    shellcmd.!

def download(url:String) = 
     Seq(ytdlp,url).!

def mvmp4 = 
    val wd = os.pwd
    os.list(wd).filter(x => x.ext == "mp4").foreach{
        p => os.move(p, os.home / "downloads" / "youtube" / p.last, replaceExisting = true, createFolders = true)
    }

//cli:
//scala ytdownloader.sc -- https://www.youtube.com/url   

if (args.isEmpty) {
  println("Please provide a url!")
  sys.exit(1)
}

val url = args(0)

download(url)
mvmp4
