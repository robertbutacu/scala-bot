package bot.actors

import akka.actor.{Actor, Props}
import bot.actors.MemoryHandler._
import bot.connections.Acquaintances._
import bot.connections.{Acquaintances, Attribute, Person}

import scala.util.Try

object MemoryHandler {
  def props() = Props(new MemoryHandler)

  def name() = "memoryHandler"

  case class Save(file: String, people: List[Person])


  case class Load(file: String)


  case class Forget(person: Map[Attribute, String])

  case class Add(person: Map[Attribute, String])

  case class TryAndMatch(person:       List[(Attribute, String)],
                         minThreshold: Int)

  case class LoadedPeople(people: Try[List[List[(String, String, String)]]])

  case class PossibleMatches(people: List[Map[Attribute, String]])

}

class MemoryHandler() extends Actor {
  override def receive: Actor.Receive = {
    case Load(file)             => sender() ! LoadedPeople(Acquaintances.xmlStorage(file).remember())

    case Save(file, people)     => Acquaintances.xmlStorage(file).persist(people)

    case Forget(person) => sender() ! Acquaintances.xmlStorage("").forget(person)

    case Add(person)    => sender() ! Acquaintances.xmlStorage("").add(person)

    case TryAndMatch(person, minThreshold) =>
      sender() ! PossibleMatches(Acquaintances.xmlStorage("").tryMatch(person, minThreshold))
  }
}
