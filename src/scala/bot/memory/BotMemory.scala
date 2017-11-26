package scala.bot.memory

import scala.bot.trie.Attribute

trait BotMemory {
  def remember(filename: String): List[Person] = ???

  def translate(people: List[Person]): List[Map[Attribute, String]] = ???

  def tryMatch(people: List[Map[Attribute, String]], minThreshold : Int): List[Map[Attribute, String]] = ???
}
