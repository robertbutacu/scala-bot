package bot.connections

import bot.memory.Attribute

import scala.xml.Elem

class Trait(attribute: Attribute, value: String) {
  def toXml: Elem =
    <attribute type={attribute.characteristic.toString} weight={attribute.weight.toString}>{value}</attribute>

}

class Person(traits: Map[Attribute, String]) {
  val serialized: List[Elem] = traits.toList.map(e => new Trait(e._1, e._2).toXml)

  def toXml: Elem =
    <person>
      {serialized.map(e => e)}
    </person>
}
