package bot.memory

import bot.trie.Attribute

import scala.xml.XML

trait BotMemory {
  def persist(people: List[Person], filename: String): Unit = {
    val peopleXml = people map ( _.toXml)

    val serialized =
    <people>
      {peopleXml}
    </people>

    XML.save(filename, serialized, "UTF-8", xmlDecl = true, null)
  }

  def forget(people: List[Map[Attribute, String]],
             person: Map[Attribute, String]): List[Map[Attribute, String]] = people.filterNot(_ == person)


  def add(people: List[Map[Attribute, String]],
          person: Map[Attribute, String]): List[Map[Attribute, String]] = people :+ person


  def remember(filename: String): List[Person] = {
    val people = XML.loadFile(filename)

    print(people.\\("person").toList)

    List.empty
  }

  def translate(people: List[Person]): List[Map[Attribute, String]] = ???

  def tryMatch(people: List[Map[Attribute, String]],
               person: Map[Attribute, String],
               minThreshold: Int): List[Map[Attribute, String]] = {
    val initialMatches = people filter (p => p.toList.exists(e => person.toList.contains(e)))

    initialMatches filter (e =>
      e.toList.foldLeft(0)((total, curr) => total + curr._1.weigh) >= minThreshold)
  }
}
