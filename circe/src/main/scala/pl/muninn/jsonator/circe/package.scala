package pl.muninn.jsonator

import io.circe.Json
import io.circe.parser.parse
import pl.muninn.jsonator.core.{From, Intermediate, ParsingException, To}

package object circe {

  case class CirceIntermediate(json: Json) extends Intermediate[Json] {
    val original: Json = json

    val value: String = json.noSpaces

    lazy val isString: Boolean = json.isString

    lazy val isNumber: Boolean = json.isNumber

    lazy val isBoolean: Boolean = json.isBoolean

    lazy val isObject: Boolean = json.isObject

    lazy val isArray: Boolean = json.isArray

    lazy val isNull: Boolean = json.isNull
  }


  implicit val fromCirce: From[Json] = new From[Json] {
    override def read(value: Json): Intermediate[Json] = new CirceIntermediate(value)
  }

  implicit val toCirce: To[Json] = new To[Json] {
    override def mapToJson(intermediate: Intermediate[_]): Either[ParsingException, Json] =
      parse(intermediate.value) match {
        case Left(value) => Left(ParsingException("Error during parsing intermediate json value", value.underlying))
        case Right(value) => Right(value)
      }
  }

}
