package bot.memory

import java.io.{File, FileNotFoundException}

import bot.trie.Attribute

import scala.util.{Failure, Success, Try}
import scala.xml.{Elem, XML}

object Acquaintances {

  /** Receiving a list of people traits and a filename, it will store all the information about them in an XML file.
    *
    * @param people   - all the people from all convo that have been persisted previously +- current session
    * @param filename - the name of the file where the xml will be stored
    */
  def persist(people: List[Person], filename: String): Unit = {
    val file = new File(filename)

    if (file.exists())
      file.delete()

    val peopleXml = people map (_.toXml)

    val serialized =
      <people>
        {peopleXml}
      </people>

    XML.save(filename, serialized, "UTF-8", xmlDecl = true, null)
  }

  /**
    * The function is useful when the bot chats with a previously met person and it is discovered, thus it is
    * required to update the info about that person if necessary ( he/she might disclose new details ).
    *
    * @param people - people details from previous convo
    * @param person - person to forget
    * @return - a list of people traits excluding the person
    */
  def forget(people: List[Map[Attribute, String]],
             person: Map[Attribute, String]): List[Map[Attribute, String]] = people.filterNot(_ == person)


  /** At the end of a convo, it might be required for the bot to persist the person he/she just talked to.
    *
    * @param people - people details from previous convo
    * @param person - person to remember
    * @return
    */
  def add(people: List[Map[Attribute, String]],
          person: Map[Attribute, String]): List[Map[Attribute, String]] = people :+ person


  /**
    * Since reflection doesn't work on traits, it is required to do all the "translation" manually using the
    * translate() method. This function return a triple of Strings, each representing:
    * _1 : Attribute Name
    * _2 : Attribute weight
    * _3 : Attribute value
    *
    * @param filename - path to the XML file containing the people details from previous conversations.
    * @return - a list of lists containing all the information about all the people.
    */
  def remember(filename: String): Try[List[List[(String, String, String)]]] = {
    try {
      if (new File(filename).exists()) {
        val peopleXML = XML.loadFile(filename)

        Success(peopleXML.\\("person").toList
          .view
          .map(node => node.\\("attribute"))
          .map(e =>
            e.toList.map(n => (n.\\("@type").text, n.\\("@weight").text, n.text)))
          .toList)
      }
      else
        Failure(new FileNotFoundException("Inexisting file!"))
    }
  }

  /**
    *
    * @param people       - people from previous conversations
    * @param person       - current person talking to
    * @param minThreshold - a minimum amount of attribute weights sum
    * @return - a list of possible matching people
    */
  def tryMatch(people: List[Map[Attribute, String]],
               person: List[(Attribute, String)],
               minThreshold: Int): List[Map[Attribute, String]] = {
    def isMatch(person: List[(Attribute, String)]): Boolean =
      sum(person) >= minThreshold

    def sum(person: List[(Attribute, String)]): Int =
      person.foldLeft(0)((total, curr) => total + curr._1.weight)

    val initialMatches = people filter (p => person.forall(p.toList.contains))

    initialMatches filter (p => isMatch(p.toList)) sortWith ((p1, p2) => sum(p1.toList) > sum(p2.toList))
  }
}
