package pl.muninn.jsonator

package object core {

  case class ParsingException(error: String, cause: Throwable = None.orNull) extends Exception(error, cause)

  trait From[T] {
    def read(value: T): Intermediate[T]
  }

  trait To[T] {
    def mapToJson(value: Intermediate[_]): Either[ParsingException, T]
  }

  trait Intermediate[T] {

    def original: T

    def value: String

    def isString: Boolean

    def isNumber: Boolean

    def isBoolean: Boolean

    def isObject: Boolean

    def isArray: Boolean

    def isNull: Boolean
  }

  implicit class Ops[T](value: T)(implicit val from: From[T]) {
    def mapToJson[R](implicit to: To[R]): Either[ParsingException, R] = to.mapToJson(from.read(value))
  }

}
