package models
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class Name(
  first: String
  , last: String
)

object Name {
 implicit val NameJsonFormatter: Format[Name] = (
   (JsPath \ "first").format[String](minLength[String](1)) and
     (__ \ "last").format[String]
 )(Name.apply, unlift(Name.unapply))
}
