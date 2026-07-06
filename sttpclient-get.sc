//> using file project.scala

import sttp.client3._
import zio.json._

/**
  * this for synchronouc HTTP client like sttp client, if your entire application is purely synchronou
  *use asynchronous HTTP client like ZIO HTTP if your entire application is purely synchronou
*/

case class Todo(userId: Int, id: Int, title: String, completed: Boolean)
object Todo:
  given JsonCodec[Todo] = DeriveJsonCodec.gen[Todo]

val backend = HttpClientSyncBackend()
def response(url:String) = basicRequest
    .get(
      uri"$url"
    ).send(backend)


val responseBody = response("http://jsonplaceholder.typicode.com/todos")
  .body


val result  = responseBody.flatMap(s => s.fromJson[List[Todo]]) match
  case Left(error) => println(s"Error parsing JSON: $error"); List.empty[Todo]
  case Right(todo) => todo


println(s"$result")

