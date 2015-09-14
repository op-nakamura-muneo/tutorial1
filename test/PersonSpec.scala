import org.specs2.mutable._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

class PersonSpec extends Specification {

  "Person Controller" should {
    "json parameter(name.first is invalid)" in new WithApplication {
      val post_data = Json.parse(
        """
        |{
        |   "name" : {
        |       "sei" : "Muneo",
        |       "last" : "Nakamura"
        |   },
        |   "age" : 18,
        |   "blood" : "O",
        |   "favoriteNum" : [5,6]
        |}
      """.
          stripMargin)

      val response = route(FakeRequest("POST", "/api/v1/register").withJsonBody(post_data)).get
      status(response) must equalTo(BAD_REQUEST)
      contentAsJson(response) must equalTo(Json.parse(
        """
          |{
          | "obj.name.first":[
          |   {"msg":["error.path.missing"],
          |    "args":[]
          |   }
          | ]
          |}
        """.stripMargin)
      )
    }

    "json parameter(name.first and name.last) is invalid" in new WithApplication {
       val
       post_data = Json.parse(
         """
        |{
        |   "name" : {
        |       "sei" : "Muneo",
        |       "mei" : "Nakamura"
        |   },
        |   "age" : 18,
        |   "blood" : "O",
        |   "favoriteNum" : [5,6]
        |}
      """.
           stripMargin)

      val response = route(FakeRequest("POST", "/api/v1/register").withJsonBody(post_data)).get
        status(
          response) must equalTo(BAD_REQUEST)
      contentAsJson(response) must equalTo(Json.parse(
        """
          |{
          | "obj.name.last":[
          |   {"msg":["error.path.missing"],
          |    "args":[]
          |   }
          | ],
          | "obj.name.first":[
          |   {"msg":["error.path.missing"],
          |    "args":[]
          |   }
          | ]
          |}
        """.stripMargin)
        )
      }

    "json parameter(age over with original message) is invalid" in new WithApplication {
      val post_data = Json.parse(
        """
        |{
        |   "name" : {
        |       "first" : "Muneo",
        |       "last" : "Nakamura"
        |   },
        |   "age" : 180,
        |   "blood" : "O",
        |   "favoriteNum" : [1,2,3]
        |}
      """.
          stripMargin)
      val response = route(FakeRequest("POST", "/api/v1/register").withJsonBody(post_data)).get
      //status(response) must equalTo(BAD_REQUEST)
      contentAsJson(response) must equalTo(Json.parse(
        """
          |{
          | "obj.age":[
          |   {
          |     "msg":["年齢は100歳以下で入力してください"],
          |     "args":[]
          |   }
          | ]
          |}
        """.stripMargin)
        )
      }

    "json parameter success" in new WithApplication {
       val
       post_data = Json.parse(
         """
        |{
        |   "name" : {
        |       "first" : "Muneo",
        |       "last" : "Nakamura"
        |   },
        |   "age" : 80,
        |   "blood" : "O",
        |   "favoriteNum" : [1,2,3]
        |}
      """.
           stripMargin)
      val response = route(FakeRequest("POST", "/api/v1/register").withJsonBody(post_data)).get
      status(response) must equalTo(OK)
    }
  }
}
