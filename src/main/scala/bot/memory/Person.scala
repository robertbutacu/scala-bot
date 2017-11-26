package bot.memory

import bot.trie.Attribute

class Person(traits: Map[Attribute, String]) {
  val serialized: List[(Attribute, String)] = traits.toList

  def toXml =
    <person>
      {serialized.map(e =>
      "<{e._1}>\n" +
      e._2 +
      "</{e._2}>")}
    </person>
}
