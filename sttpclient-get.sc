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

def getrequest(url:String) = basicRequest
    .get(
      uri"$url"
    )


val backend = HttpClientSyncBackend()
val response = getrequest("http://jsonplaceholder.typicode.com/todos").send(backend)


response.body.map { body =>
  val todos = body.fromJson[List[Todo]]
  println(todos)
}

