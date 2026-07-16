//> using file "project.scala"

import zio._
import zio.http._
import zio.json._

/**
  * it generally does not make sense to use a reactive, asynchronous HTTP client like ZIO HTTP if your entire application is purely synchronous
  * 
  * consider sttpclient-get.sc example for a more synchronous approach to HTTP requests
  */

object Main extends ZIOAppDefault :
  case class Todo(userId: Int, id: Int, title: String, completed: Boolean)
  object Todo:
    given JsonDecoder[Todo] = DeriveJsonDecoder.gen[Todo]

  override def run =
    def program = for {
      _         <- Console.printLine("Starting batched request...")
      response  <- Client.batched(Request.get("http://jsonplaceholder.typicode.com/todos"))  
      s         <- response.body.asString
      todos     <- ZIO.fromEither(s.fromJson[List[Todo]])
      _         <- Console.printLine(todos).orDie
    } yield todos


    program.provide(
      Client.default
    )


Main.main(Array.empty)
