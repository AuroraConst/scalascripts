//> using file project.scala
//> using toolkit latest


 // Get the value of a specific environment variable
  val homeDir = sys.env.get("HOME")
  val dev = sys.env.get("DevDirectory")

  println( s"dev: $dev")