//> using file "project.scala"
println("h  e  l  l  o    w  o  r  l  d")
val r = os.list(os.pwd)
case class FSize(fname:String,fsize:Long)
val data = r.map{file => FSize(file.toString,os.size(file))}
data foreach println



val devdir :os.Path =  os.root / "dev"
import java.text.NumberFormat
import java.util.Locale
// / Format the totalSize with commas
val formatter = NumberFormat.getNumberInstance(Locale.US)

val totalSize = os.walk.stream.attrs(devdir)
 .collect { case (path, attrs) if attrs.isFile => attrs.size }
 .sum

val formattedTotalSize = formatter.format(totalSize) 
println(f"$formattedTotalSize Bytes")



