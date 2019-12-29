package pl.muninn.jsonator

import pl.muninn.jsonator.core.{From, Intermediate, ParsingException, To}
import _root_.play.api.libs.json.{JsArray, JsBoolean, JsNull, JsNumber, JsObject, JsString, JsValue, Json}

import scala.util.{Failure, Success, Try}

package object play {

  case class PlayIntermediate(json: JsValue) extends Intermediate[JsValue] {
    val original: JsValue = json

    val value: String = Json.stringify(json)

    lazy val isString: Boolean = json match {
      case _: JsString => true
      case _ => false
    }

    lazy val isNumber: Boolean = json match {
      case _: JsNumber => true
      case _ => false
    }

    lazy val isBoolean: Boolean = json match {
      case _: JsBoolean => true
      case _ => false
    }

    lazy val isObject: Boolean = json match {
      case _: JsObject => true
      case _ => false
    }

    lazy val isArray: Boolean = json match {
      case _: JsArray => true
      case _ => false
    }

    lazy val isNull: Boolean = json match {
      case JsNull => true
      case _ => false
    }
  }

  implicit val fromPlay: From[JsValue] = new From[JsValue] {
    override def read(value: JsValue): Intermediate[JsValue] = new PlayIntermediate(value)
  }

  implicit val toPlay: To[JsValue] = new To[JsValue] {
    override def mapToJson(intermediate: Intermediate[_]): Either[ParsingException, JsValue] =
      Try(Json.parse(intermediate.value)) match {
        case Failure(exception) => Left(ParsingException("Error during parsing intermediate json value", exception))
        case Success(value) => Right(value)
      }
  }

}
