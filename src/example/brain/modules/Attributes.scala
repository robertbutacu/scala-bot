package example.brain.modules

import scala.bot.trie.Attribute

trait Attributes {
  val age     = Attribute(AgeAttr, 10)
  val name    = Attribute(NameAttr, 10)
  val passion = Attribute(PassionAttr, 15)
  val job     = Attribute(JobAttr, 5)
}
