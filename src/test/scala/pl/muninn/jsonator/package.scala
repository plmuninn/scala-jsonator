package pl.muninn

import org.scalatest.{FlatSpec, Matchers}


package object jsonator {

  case class SimpleValues(intValue: Int, doubleValue: Double, floatValue: Float, stringValue: String, booleanValue: Boolean)

  case class TestStructure(collection: Iterable[TestStructure], mapValue: Map[String, TestStructure], otherStructure: Option[TestStructure])

  trait TestSpec extends FlatSpec with Matchers

  trait Fixture {
    val simpleValue = SimpleValues(123, 123, 123, "test value", booleanValue = true)

    val testStructure = TestStructure(
      collection = List(
        TestStructure(Nil, Map.empty, None),
        TestStructure(Nil, Map.empty, None),
        TestStructure(Nil, Map.empty, None),
        TestStructure(Nil, Map.empty, None),
      ),
      mapValue = Map(
        "1" -> TestStructure(Nil, Map.empty, None),
        "2" -> TestStructure(Nil, Map.empty, None),
        "x" -> TestStructure(Nil, Map.empty, None),
      ),
      otherStructure = Some(
        TestStructure(Nil, Map.empty, Some(TestStructure(Nil, Map.empty, None)))
      )
    )
  }

}
