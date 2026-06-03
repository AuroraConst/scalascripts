import scala.sys.process._

object Temp:

  def main(args: Array[String]): Unit = {
    runPowerShell()
  }

  def runPowerShell(): Unit =
    // Use -NoProfile to speed up execution and -Command to pass the script execution
    // -ExecutionPolicy Bypass ensures the script isn't blocked by local safety policies
    val scriptPath = ".\\ps-exec.ps1"
    val command = Seq("powershell.exe", "-NoProfile", "-ExecutionPolicy", "Bypass", "-File", scriptPath)
    
    // .!! executes the process and returns the output as a String
    try {
      val output = command.!!
      println(s"Script Output:\n$output")
    } catch {
      case e: Exception => println(s"Failed to run script: ${e.getMessage}")
    }