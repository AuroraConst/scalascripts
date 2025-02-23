//> using toolkit latest
println("h  e  l  l  o  w  o  r  l  d")
val r = os.list(os.pwd)
case class FSize(fname:String,fsize:Long)
val data = r.map{file => FSize(file.toString,os.size(file))}
data foreach println