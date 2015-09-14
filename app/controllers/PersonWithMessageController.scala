package controllers

import javax.inject.Inject

import models.{Name, Person}
import message.MessageUtil
import play.api.Logger
import play.api.data.validation.ValidationError
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.{JsError, Json}
import play.api.mvc._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Format, Reads, _}

class PersonWithMessageController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport{
  implicit def message(implicit request: RequestHeader) = messagesApi.preferred(request)

  def communicate = Action {
    Ok("It works")
  }

  def register = Action(parse.json) {
    def age_check(age: Int): Boolean = {
        if(age > 100) false else true
    }

    val under30Validate = Reads.IntReads.filter(ValidationError(MessageUtil.message("error.max")))(age_check)
    implicit val PersonJsonFormatter: Format[Person] = (
          (__ \ "age").format[Int](under30Validate) and
            (__ \ "name").format[Name] and
            (__ \ "blood").formatNullable[String] and
            (__ \ "favoriteNum").format[Seq[Int]]
          )(Person.apply _, unlift(Person.unapply _))

    _.body.validate[Person].map { p =>
      Logger.debug(">>>>>>>>>>>> success")
      try{p.validate} catch {case e:Exception => Logger.error(">>>>>>>>>>>>>>>> validate error")}
      Ok(Json.toJson(p))
    }.recoverTotal {e =>
      Logger.debug(">>>>>>>>>>>> error")
      Logger.info(e.errors.toString())
      BadRequest(JsError.toJson(e))
    }
  }
}
