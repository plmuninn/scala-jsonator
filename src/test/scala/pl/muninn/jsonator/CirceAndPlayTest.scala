package pl.muninn.jsonator

import io.circe.syntax._
import io.circe.generic.auto._
import _root_.play.api.libs.json.{Json, JsValue}

class CirceAndPlayTest extends TestSpec {

  import pl.muninn.jsonator.core._
  import pl.muninn.jsonator.circe._
  import pl.muninn.jsonator.play._

  "circe" should "decode to play simple values" in new Fixture {
    implicit val simpleValuesReads = Json.reads[SimpleValues]

    val json = simpleValue.asJson

    val result = json.mapToJson[JsValue]
    result.isLeft shouldBe false
    val regainValue = result.right.get.as[SimpleValues](simpleValuesReads)

    regainValue shouldBe simpleValue
  }

  "circe" should "decode to play test structure" in new Fixture {
    implicit val testStructureReads = Json.reads[TestStructure]

    val json = testStructure.asJson

    val result = json.mapToJson[JsValue]
    result.isLeft shouldBe false
    val regainValue = result.right.get.as[TestStructure](testStructureReads)

    regainValue shouldBe testStructure
  }
}
