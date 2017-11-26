package bot.memory

import bot.trie.Attribute

trait BotMemory {
  def persist(people: List[Person], filename: String): Unit = {
  }

  def forget(people: List[Map[Attribute, String]],
             person: Map[Attribute, String]): List[Map[Attribute, String]] =
    people.filterNot(_ == person)

  def add(people: List[Map[Attribute, String]],
          person: Map[Attribute, String]): List[Map[Attribute, String]] =
    people :+ person

  def remember(filename: String): List[Person] = {
    List.empty
  }

  def translate(people: List[Person]): List[Map[Attribute, String]] = ???

  def tryMatch(people: List[Map[Attribute, String]],
               person: Map[Attribute, String],
               minThreshold: Int): List[Map[Attribute, String]] = ???
}
