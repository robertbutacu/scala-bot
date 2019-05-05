package bot.connections

import java.io.{File, FileNotFoundException}

import cats.Monad
import example.brain.modules.{AgeAttr, JobAttr, NameAttr, PassionAttr}
import cats.syntax.all._
import scala.language.{higherKinds, postfixOps}
import scala.util.{Failure, Success, Try}
import scala.xml.XML

trait Acquaintances[M[_]] {
  type CharacteristicName = String
  type Weight             = String
  type Value              = String

  def persist(people: List[Person]): M[Unit]

  def forget(person: Map[Attribute, Value]): M[Unit]

  def add(person: Map[Attribute, Value]): M[Unit]

  def remember(): M[Try[List[List[(CharacteristicName, Weight, Value)]]]]

  def tryMatch(person: List[(Attribute, Value)],
               minThreshold: Int): M[List[Map[Attribute, Value]]]
}

object Acquaintances {
  implicit def xmlStorage[F[_]](filename: String)(implicit M: Monad[F]): Acquaintances[F] = new Acquaintances[F] {
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
      M.pure(())
    }

    /**
      * The function is useful when the bot chats with a previously met person and it is discovered, thus it is
      * required to update the info about that person if necessary ( he/she might disclose new details ).
      *
      * @param person - person to forget
      * @return - a list of people traits excluding the person
      */
    def forget(person: Map[Attribute, Value]): F[Unit] = {
      val people: F[List[Map[Attribute, String]]] = M.map(remember()) {
        case Success(p) => p.view.map(translate).map(_.flatten.toMap).toList
        case Failure(e) => println(s"There seem to be a problem loading up my memory: $e ..."); List.empty
      }

      val updatedPeople = M.map(people)(ps => ps.filterNot(_ == person).map(Person))

      M.flatTap(updatedPeople)(people => persist(people)).map(_ => ())
    }


    /** At the end of a convo, it might be required for the bot to persist the person he/she just talked to.
      * @param person - person to remember
      * @return
      */
    def add(person: Map[Attribute, Value]): F[Unit] = {
      val people: F[List[Map[Attribute, String]]] = M.map(remember()) {
        case Success(p) => p.view.map(translate).map(_.flatten.toMap).toList
        case Failure(e) => println(s"There seem to be a problem loading up my memory: $e ..."); List.empty
      }

      people.flatMap(ps => persist((ps :+ person).map(Person)))
    }


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
      M.pure {
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
      * @param person       - current person talking to
      * @param minThreshold - a minimum amount of attribute weights sum
      * @return - a list of possible matching people
      */
    def tryMatch(person:       List[(Attribute, Value)],
                 minThreshold: Int): F[List[Map[Attribute, Value]]] = {
      def isMatch(person: List[(Attribute, Value)]): Boolean =
        sum(person) >= minThreshold

      def sum(person: List[(Attribute, Value)]): Int =
        person.foldLeft(0)((total, curr) => total + curr._1.weight)


      val peopleXML: F[Try[List[List[(CharacteristicName, Weight, Value)]]]] = remember()

      val people = M.map(peopleXML) {
        case Success(p) => p.view.map(translate).map(_.flatten.toMap).toList
        case Failure(e) => println(s"There seem to be a problem loading up my memory: $e ..."); List.empty
      }

      val initialMatches = M.map(people)(p => p filter (p => person.forall(p.toList.contains)))

      val result = M.map(initialMatches)(matches => matches.filter(p => isMatch(p.toList))
        .sortWith((p1, p2) => sum(p1.toList) > sum(p2.toList)))

      result
    }


    /** The triple represents:
      * _1 : Attribute name
      * _2 : Attribute weight
      * _3 : Attribute value
      *
      * @param people - a list where every single element represent a person with all their traits
      * @return - every item from the list converted to a map of Attribute, String
      */
    private def translate(people: List[(String, String, String)]): List[Map[Attribute, String]] = {
      val applier: PartialFunction[(String, String, String), Map[Attribute, String]] = {
        case ("AgeAttr", weight, ageValue)         => Map(Attribute(AgeAttr, weight.toInt) -> ageValue)
        case ("NameAttr", weight, nameValue)       => Map(Attribute(NameAttr, weight.toInt) -> nameValue)
        case ("PassionAttr", weight, passionValue) => Map(Attribute(PassionAttr, weight.toInt) -> passionValue)
        case ("Job", weight, jobValue)             => Map(Attribute(JobAttr, weight.toInt) -> jobValue)
      }

      people collect applier
    }
  }
}
