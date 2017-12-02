package bot.memory

import bot.trie.Attribute

object MemoryHandler {

  case class Save(file: String, people: List[Person])

  case class Load(file: String)

  case class Forget(people: List[Map[Attribute, String]],
                    person: Map[Attribute, String])

  case class Add(people: List[Map[Attribute, String]],
                 person: Map[Attribute, String])

  case class TryAndMatch(people: List[Map[Attribute, String]],
                         person: List[(Attribute, String)],
                         minThreshold: Int)
}

class MemoryHandler {

}
