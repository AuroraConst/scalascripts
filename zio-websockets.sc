//> using file "project.scala"
//TODO  THIS NEEDS TO COMMUNICATE WITH A SERVER, iDEALLY i WANT THOS TO CONNECT TO wss://echo.websocket.org (SECURE) OR ws://echo.websocket.org (NOT SECURE)
import zio._
import zio.http._
import zio.http.ChannelEvent.Read
import zio.http.WebSocketFrame._


import zio._

import zio.http.ChannelEvent.Read
import zio.http._

object WebSocketSimpleClientAdvanced extends ZIOAppDefault {

  def sendChatMessage(message: String): ZIO[Queue[String], Throwable, Unit] =
    ZIO.serviceWithZIO[Queue[String]](_.offer(message).unit)

  def processQueue(channel: WebSocketChannel): ZIO[Queue[String], Throwable, Unit] = {
    for {
      queue <- ZIO.service[Queue[String]]
      msg   <- queue.take
      _     <- channel.send(Read(WebSocketFrame.Text(msg)))
    } yield ()
  }.forever.forkDaemon.unit

  private def webSocketHandler: ZIO[Queue[String] &  Client & Scope, Throwable, Response] =
    Handler.webSocket { channel =>
      for {
        _ <- processQueue(channel)
        _ <- channel.receiveAll {
          case Read(WebSocketFrame.Text(text)) =>
            Console.printLine(s"Server: $text")
          case _                               =>
            ZIO.unit
        }
      } yield ()
    }.connect("ws://localhost:8080/subscriptions")

  override val run = {
    ZIO.scoped(webSocketHandler) *>
      Console.readLine.flatMap(sendChatMessage).forever.forkDaemon *>
      ZIO.never
  }.provide(
    Client.default,
    ZLayer(Queue.bounded[String](100)),
  )

}