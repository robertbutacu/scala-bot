package bot.memory

import bot.trie.Attribute

class Person(traits: Map[Attribute, String]) {
  val serialized: List[String] = traits.toList.map(e =>
    s"<${e._1.characteristic} weight=${'"'}${e._1.weigh}${'"'}>" +
      s"${e._2}" +
      s"</${e._1.characteristic}>\n")

  def toXml: String = serialized.foldLeft("")((total, curr) => total + curr)
}
