//> using file "project.scala"

import zio._
import zio.http._
import zio.json._

/**
  * it generally does not make sense to use a reactive, asynchronous HTTP client like ZIO HTTP if your entire application is purely synchronou
  */

object Main extends ZIOAppDefault :
  case class Todo(userId: Int, id: Int, title: String, completed: Boolean)
  object Todo:
    given JsonDecoder[Todo] = DeriveJsonDecoder.gen[Todo]

  def body =
    for {
      r <- Client.batched(Request.get("http://jsonplaceholder.typicode.com/todos"))  
      s <- r.body.asString
    } yield s

  def json = for {
    s <- body
    todos <- ZIO.fromEither(s.fromJson[List[Todo]])
    _ <- Console.printLine(todos).orDie
  } yield (todos)  



  override def run =
    def f = for{
      _ <- Console.printLine("Starting batched request...")
      js <- json
    } yield js 

    f.provide(
      Client.default,
    )

Main.main(Array.empty)
