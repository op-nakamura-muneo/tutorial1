package models

import message.MessageUtil
import play.api.Logger
import play.api.data.validation.ValidationError
import play.api.i18n.{Lang, MessagesApi, Messages}
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{Format, Reads, _}

case class Person (
  age: Int
  , name: Name
  , blood: Option[String]
  , favoriteNum: Seq[Int]
  )
{
  def validate {
    Logger.debug(">>>>>>>>>>>>>>>>>>>>> Person::validate")
    require(age < 30)
  }
}

