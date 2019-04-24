package bot.connections

import java.io.{File, FileNotFoundException}

import cats.Applicative

import scala.language.{higherKinds, postfixOps}
import scala.util.{Failure, Try}
import scala.xml.XML

trait Acquaintances[M[_]] {
  type CharacteristicName = String
  type Weight             = String
  type Value              = String

  def persist(people: List[Person]): M[Unit]

  def forget(people: List[Map[Attribute, Value]],
             person: Map[Attribute, Value]): M[List[Map[Attribute, Value]]]

  def add(people: List[Map[Attribute, Value]],
          person: Map[Attribute, Value]): M[List[Map[Attribute, Value]]]

  def remember(): M[Try[List[List[(CharacteristicName, Weight, Value)]]]]

  def tryMatch(people: List[Map[Attribute, Value]],
               person: List[(Attribute, Value)],
               minThreshold: Int): M[List[Map[Attribute, Value]]]
}

object Acquaintances {

  implicit class xmlStorage[F[_]](filename: String)(implicit A: Applicative[F]) extends Acquaintances[F] {
    /** Receiving a list of people traits and a filename, it will store all the information about them in an XML file.
      *
      * @param people - all the people from all convo that have been persisted previously +- current session
      */
    def persist(people: List[Person]): F[Unit] = {
      val file = new File(filename)

      if (file.exists())
        file.delete()

      val peopleXml = people map (_.toXml)

      val serialized =
        <people>
          {peopleXml}
        </people>

      XML.save(filename, serialized, "UTF-8", xmlDecl = true, null)
      A.pure(())
    }

    /**
      * The function is useful when the bot chats with a previously met person and it is discovered, thus it is
      * required to update the info about that person if necessary ( he/she might disclose new details ).
      *
      * @param people - people details from previous convo
      * @param person - person to forget
      * @return - a list of people traits excluding the person
      */
    def forget(people: List[Map[Attribute, Value]],
               person: Map[Attribute, Value]): F[List[Map[Attribute, Value]]] = A.pure(people.filterNot(_ == person))


    /** At the end of a convo, it might be required for the bot to persist the person he/she just talked to.
      *
      * @param people - people details from previous convo
      * @param person - person to remember
      * @return
      */
    def add(people: List[Map[Attribute, Value]],
            person: Map[Attribute, Value]): F[List[Map[Attribute, Value]]] = A.pure(people :+ person)


    /**
      * Since reflection doesn't work on traits, it is required to do all the "translation" manually using the
      * translate() method. This function return a triple of Strings, each representing:
      * _1 : Attribute Name
      * _2 : Attribute weight
      * _3 : Attribute value
      *
      * @return - a list of lists containing all the information about all the people.
      */
    def remember(): F[Try[List[List[(CharacteristicName, Weight, Value)]]]] = {
      A.pure {
        Try {
          val peopleXML = XML.loadFile(filename)

          (peopleXML \\ "person").toList
            .view
            .map(node => node \\ "attribute")
            .map(e =>
              e.toList.map(n => (n \\ "@type" text: CharacteristicName, n \\ "@weight" text: Weight, n.text: Value)))
            .toList
        }.orElse(Failure(new FileNotFoundException("Inexisting file!")))
      }
    }

    /**
      *
      * @param people       - people from previous conversations
      * @param person       - current person talking to
      * @param minThreshold - a minimum amount of attribute weights sum
      * @return - a list of possible matching people
      */
    def tryMatch(people: List[Map[Attribute, Value]],
                 person: List[(Attribute, Value)],
                 minThreshold: Int): F[List[Map[Attribute, Value]]] = {
      def isMatch(person: List[(Attribute, Value)]): Boolean =
        sum(person) >= minThreshold

      def sum(person: List[(Attribute, Value)]): Int =
        person.foldLeft(0)((total, curr) => total + curr._1.weight)

      val initialMatches = people filter (p => person.forall(p.toList.contains))

      val result = initialMatches.filter(p => isMatch(p.toList))
        .sortWith((p1, p2) => sum(p1.toList) > sum(p2.toList))

      A.pure(result)
    }
  }
}
