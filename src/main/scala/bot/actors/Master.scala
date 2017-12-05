package bot.actors

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import bot.actors.Handler.Hello


import scala.concurrent.Future
import scala.concurrent.duration._

object Master {
  implicit private val timeout: Timeout = Timeout(5 seconds)
  private val system: ActorSystem = ActorSystem("ScalaBot")
  private val handler: ActorRef = system.actorOf(Handler.props(), Handler.name())
  private val memoryHanlder: ActorRef = system.actorOf(MemoryHandler.props(), MemoryHandler.name())
  private val studentBot: ActorRef = system.actorOf(StudentBot.props(), StudentBot.name())
  private val trieCreator: ActorRef = system.actorOf(TrieCreator.props(), TrieCreator.name())

  def tickle(): Future[Any] = handler ? Hello

}
