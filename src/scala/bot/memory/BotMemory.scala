package scala.bot.memory

import scala.bot.trie.Attribute

trait BotMemory {
  def persist(people: List[Person]): Unit = ???

  def forget(people: List[Map[Attribute, String]],
             person: Map[Attribute, String]): List[Map[Attribute, String]] = ???

  def add(people: List[Map[Attribute, String]],
          person: Map[Attribute, String]): List[Map[Attribute, String]] = ???

  def remember(filename: String): List[Person] = ???

  def translate(people: List[Person]): List[Map[Attribute, String]] = ???

  def tryMatch(people: List[Map[Attribute, String]],
               person: Map[Attribute, String],
               minThreshold: Int): List[Map[Attribute, String]] = ???
}
