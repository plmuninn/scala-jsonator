package pl.muninn.jsonator.json4s

import pl.muninn.jsonator.core.{From, Intermediate, ParsingException, To}
import org.json4s._
import org.json4s.jackson.JsonMethods._

import scala.util.{Failure, Success, Try}

package object jackson {

  case class Json4SIntermediate(json: JValue) extends Intermediate[JValue] {
    val original: JValue = json

    val value: String = compact(render(json))

    lazy val isString: Boolean = json match {
      case _: JsonAST.JString => true
      case _ => false
    }

    lazy val isNumber: Boolean = json match {
      case _: JsonAST.JDouble => true
      case _: JsonAST.JDecimal => true
      case _: JsonAST.JLong => true
      case _: JsonAST.JInt => true
      case _ => false
    }

    lazy val isBoolean: Boolean = json match {
      case _: JsonAST.JBool => true
      case _ => false
    }

    lazy val isObject: Boolean = json match {
      case _: JsonAST.JObject => true
      case _ => false
    }

    lazy val isArray: Boolean = json match {
      case _: JsonAST.JArray => true
      case _: JsonAST.JSet => true
      case _ => false
    }

    lazy val isNull: Boolean = json match {
      case JsonAST.JNothing => true
      case JsonAST.JNull => true
      case _ => false
    }
  }


  implicit val fromJson4sJackson: From[JValue] = new From[JValue] {
    override def read(value: JValue): Intermediate[JValue] = new Json4SIntermediate(value)
  }

  implicit val toJson4sJackson: To[JValue] = new To[JValue] {
    override def mapToJson(intermediate: Intermediate[_]): Either[ParsingException, JValue] =
      Try(parse(intermediate.value)) match {
        case Failure(exception) => Left(ParsingException("Error during parsing intermediate json value", exception))
        case Success(value) => Right(value)
      }
  }
}
