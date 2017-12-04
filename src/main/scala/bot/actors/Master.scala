package bot.actors

import akka.actor.{ActorRef, ActorSystem}
import bot.actors.Handler.Hello
import akka.pattern.ask

import scala.concurrent.Future

object Master {
  private val system: ActorSystem = ActorSystem("ScalaBot")
  private val handler: ActorRef = system.actorOf(Handler.props(), Handler.name())
  private val memoryHanlder: ActorRef = system.actorOf(MemoryHandler.props(), MemoryHandler.name())
  private val studentBot: ActorRef = system.actorOf(StudentBot.props(), StudentBot.name())
  private val trieCreator: ActorRef = system.actorOf(TrieCreator.props(), TrieCreator.name())

  def tickle(): Unit = handler ! Hello

}
