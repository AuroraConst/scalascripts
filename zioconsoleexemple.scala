//> using dep dev.zio::zio-config::4.0.2
//> using dep dev.zio::zio-config-magnolia::4.0.2
//> using dep dev.zio::zio-config-typesafe::4.0.2

//> using dep com.github.pathikrit::better-files::3.9.2
  

import zio.Console._
import zio._
import zio.config.magnolia._
import zio.config.typesafe.TypesafeConfigProvider

object HoconSimple extends ZIOAppDefault {

  final case class Customer(name: String, orderIds: List[Int], address: List[Address])
  final case class Address(unit: Int, street: String, pin: Option[Int])

  val customer: String =
    s"""
     {
        name: Jon
        orderIds: [1,2,3]
        address : [
          {
            unit : 10
            street : Homebush,
            pin: 2135
          },
          {
            unit : 11
            street: Beresford
          }
        ]  
     }
    """

  val invalidCustomer: String =
    s"""
     {
        name: Jon
        orderIds: [1,2,3]
        address : [
          {
            unit : 10
            street : Homebush,
            pin: 2135
          },
          {
            unit : 11
          }
        ]  
     }
    """

  def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    for {
      customer        <- TypesafeConfigProvider.fromHoconString(customer).load(deriveConfig[Customer])
      invalidCustomer <- TypesafeConfigProvider.fromHoconString(invalidCustomer).load(deriveConfig[Customer]).either
      _               <- printLine(customer)
      _               <- printLine(invalidCustomer)
    } yield ()

}