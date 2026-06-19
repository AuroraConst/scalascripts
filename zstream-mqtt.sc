//> using file "project.scala"
import zio.*
import zio.stream._
import org.eclipse.paho.client.mqttv3._
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

object Main extends ZIOAppDefault {
    // Configuration
  val brokerUrl = "tcp://localhost:1883"
  val clientId  = "zio-mqtt-client"
  val subTopic  = "sensors/data"
  val pubTopic  = "sensors/alerts"


  // Create a managed MQTT client that handles connect/disconnect lifecycle
  def makeMqttClient(url: String, id: String): ZIO[Scope, MqttException, MqttClient] = {
    ZIO.acquireRelease {
      ZIO.attempt {
        val client = new MqttClient(url, id, new MemoryPersistence)
        val options = new MqttConnectOptions()
        options.setCleanSession(true)
        client.connect(options)
        client
      }.refineToOrDie[MqttException]
    }(client => ZIO.attempt(if (client.isConnected) client.disconnect()).ignoreLogged)
  }

  // Create a ZStream that subscribes to an MQTT topic
  def subscribeStream(client: MqttClient): ZStream[Any, MqttException, (String, String)] = {
    ZStream.async[Any, MqttException, (String, String)] { emit =>
        client.subscribe(subTopic, (topic: String, message: MqttMessage) => {
          val payload = new String(message.getPayload)
          // Emit the incoming message to the ZIO Stream
          emit.single((topic, payload))
        })
    }
  }  


  val program = subscribeStream(makeMqttClient(brokerUrl, clientId)).mapZIO { case (topic, payload) =>
    // Process the message and publish an alert if necessary
    ZIO.attempt {
      println(s"Received message on topic '$topic': $payload")
      if (payload.contains("alert")) {
        val alertMessage = new MqttMessage(s"Alert: $payload".getBytes)
        makeMqttClient(brokerUrl, clientId).flatMap { client =>
          ZIO.attempt(client.publish(pubTopic, alertMessage)).as(client)
        }
      } else ZIO.unit
    }.refineToOrDie[MqttException]
  }.runDrain


  // val program = ZStream
  //   .fromIterable(1 to 10)            // 1. Create a stream from a collection
  //   .filter(_ % 2 == 0)              // 2. Filter for even numbers
  //   .map(n => n * n)                 // 3. Square each number
  //   .run(ZSink.foreach(Console.printLine(_))) // 4. Consume and print


  def run = {

    for {

      _ <- program


    } yield ()
  }
}

Main.main(args)