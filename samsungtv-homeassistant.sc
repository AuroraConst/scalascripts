//> using file "project.scala"

import sttp.client3._
import sttp.model.StatusCode

/**
  * https://www.home-assistant.io/integrations/media_player/
  */

object HomeAssistantTvController :
  val haUrl = "http://homeassistant.local:8123/api/services"
  lazy val haToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIzOGM0OGNlM2YzYjU0ZjdhOWIxOGYyN2YwZGFhNjU3NSIsImlhdCI6MTc4MzM2MTM0MiwiZXhwIjoyMDk4NzIxMzQyfQ.KKIISdh1PzRDk_pU2tY8MEDN6njB5obXj2DsSqjPk2o"
  lazy val tvEntityId = "media_player.65_qled_qn65q8faafxzc" // Replace with your TV's exact entity ID
  lazy  val backend = HttpClientSyncBackend()

  def main(args: Array[String]): Unit = {

    // Example 1: Turn the TV On
    callHaService("media_player", "turn_on")

    // Example 2: Turn the Volume Up
    callHaService("media_player", "volume_up")
    // selectHdmi1Request

//   Example 3: Turn the TV Off
    // callHaService("media_player", "turn_off")
  }

  def callHaService(domain: String, service: String): Unit = {
    val url = uri"$haUrl/$domain/$service"
    val jsonBody = s"""{"entity_id": "$tvEntityId"}"""

    val response = basicRequest
      .post(url)
      .header("Authorization", s"Bearer $haToken")
      .header("Content-Type", "application/json")
      .body(jsonBody)
      .send(backend)

    response.code match {
      case StatusCode.Ok => println(s"Successfully called $service on $tvEntityId")
      case code => println(s"Failed with code $code. Check your token and entity ID.")
    }
  }


// I could nto make this work
  // Switch TV to HDMI 4
  // def selectHdmi1Request = 
  //   val url = uri"$haUrl/media_player/select_source"
  //   val jsonBody = s"""{"entity_id": "$tvEntityId", "data": {"source": "HDMI 4"}}"""
  
  //   basicRequest
  //     .post(url)
  //     .header("Authorization", s"Bearer $haToken")
  //     .header("Content-Type", "application/json")
  //     .body(jsonBody)
  //     .send(backend) match {
  //       case response if response.code == StatusCode.Ok =>
  //         println("Successfully switched to HDMI 4")
  //       case response =>
  //         println(s"Failed to switch to HDMI 4. Status code: ${response.code}")
  //     }

// }

