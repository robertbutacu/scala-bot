package scala.bot.memory

import scala.bot.trie.Attribute

class Person(traits: Map[Attribute, String]) {
  val serialized: List[(Attribute, String)] = traits.toList
}
