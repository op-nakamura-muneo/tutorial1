import org.specs2.mutable._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

class PersonSpec extends Specification {

  "Person Controller" should {
    "play scala communicate test" in new WithApplication {
      val result = "it works"
//      val response = route(FakeRequest("GET", "/api/v1/commu")).get
//
//      status(response) must equalTo((OK))
//      contentAsString(result) must equalTo(response)
//      contentAsString(of = Future[Result]);
    }
    "json parameter(name.first is invalid)" in new WithApplication {
      val post_data = Json.parse(
      """
        |{
        |   "name" : {
        |       "sei" : "Muneo",
        |       "last" : "Nakamura"
        |   },
        |   "age" : 18
        |}
      """.stripMargin)

      val response = route(FakeRequest("POST", "/api/v1/register").withJsonBody(post_data)).get
      status(response) must equalTo(BAD_REQUEST)
      contentAsJson(response) must equalTo(Json.parse(
        """
          |{
          | "obj.name.first":[
          |   {"msg":"error.path.missing",
          |    "args":[]
          |   }
          | ]
          |}
        """.stripMargin)
      )
    }

    "json parameter(name.first and name.last) is invalid" in new WithApplication {
       val post_data = Json.parse(
      """
        |{
        |   "name" : {
        |       "sei" : "Muneo",
        |       "mei" : "Nakamura"
        |   },
        |   "age" : 18
        |}
      """.stripMargin)

      val response = route(FakeRequest("POST", "/api/v1/register").withJsonBody(post_data)).get
        status(response) must equalTo(BAD_REQUEST)
        contentAsJson(response) must equalTo(Json.parse(
        """
          |{
          | "obj.name.last":[
          |   {"msg":"error.path.missing",
          |    "args":[]
          |   }
          | ],
          | "obj.name.first":[
          |   {"msg":"error.path.missing",
          |    "args":[]
          |   }
          | ]
          |}
        """.stripMargin)
        )
      }
    }
}
