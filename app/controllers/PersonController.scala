package controllers

import models.Person
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

class PersonController extends Controller {
  def communicate = Action {
    Ok("It works")
  }

  import formatters.Formatter.PersonJsonFormatter
  def register = Action(parse.json) {
    _.body.validate[Person].map { p =>
      Ok(Json.toJson(p))
    }.recoverTotal {e =>
      BadRequest(JsError.toFlatJson(e))
    }
  }
}
