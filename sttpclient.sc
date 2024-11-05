import sttp.client3._


val query = "http language:scala"
val sort: Option[String] = None

// the `query` parameter is automatically url-encoded
// `sort` is removed, as the value is not defined
// val request = basicRequest.get(
//   uri"https://api.github.com/search/repositories?q=$query&sort=$sort")

// val request = basicRequest.get(
//   uri"http://localhost:8080/simple")

val requestFormPost = basicRequest
    .body(Map("title" ->"my main man! or person!", "uri" ->"myuri","company"->"mycompany") )  //formbody
    .post(
      uri"http://localhost:8080/jobs"
    )


val backend = HttpClientSyncBackend()
val response = requestFormPost.send(backend)

// response.header(...): Option[String]
println(response.header("Content-Length"))

// response.body: by default read into an Either[String, String] 
// to indicate failure or success 
println(response.body)