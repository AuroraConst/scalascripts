//> using file project.scala
/**
  * this is very rough and not completely correct implmementation
  */

import zio.Console.printLine
import zio.cli.HelpDoc.Span.text
import zio.cli._

import java.nio.file.{Path => JPath}

object GitExample extends ZIOCliDefault :
  import java.nio.file.Path

  sealed trait Subcommand extends Product with Serializable
  object Subcommand {
    final case class Add(modified: Boolean, directory: JPath) extends Subcommand
  }

  val modifiedFlag: Options[Boolean] = Options.boolean("m")

  val addHelp: HelpDoc = HelpDoc.p("Add subcommand description")
  val add =
    Command("add", modifiedFlag, Args.directory("directory")).withHelp(addHelp).map { case (modified, directory) =>
      Subcommand.Add(modified, directory)
    }



  val git: Command[Subcommand] =
    Command("git", Options.none, Args.none).subcommands(add)

  val cliApp = CliApp.make(
    name = "Git Version Control",
    version = "0.9.2",
    summary = text("a client for the git dvcs protocol"),
    command = git
  ) {
    case Subcommand.Add(modified, directory) =>
      printLine(s"Executing `git add $directory` with modified flag set to $modified")

  }

    
    import zio.{ExitCode, Scope, ZIOApp, ZIOAppArgs}
    import zio.Console._
    override def run =
    for {
      args   <- ZIOAppArgs.getArgs
      _      <-  printLine(s"Running with args: ${args.toList}")
      result <- cliApp.run(args.toList).catchSome[Environment & ZIOAppArgs & Scope, CliError[Any], Any] {
                  case CliError.Parsing(_) =>
                    // Validation errors are pretty printed by clipApp.run
                    exit(ExitCode.failure)
                }
    } yield result


GitExample.main(args:Array[String]) // This will run the CLI application




