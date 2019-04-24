package bot.actors

import akka.actor.{ActorRef, ActorSystem, PoisonPill}
import akka.pattern.ask
import akka.util.Timeout
import bot.actors.Handler.Hello

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

object Master {

  implicit lazy private val timeout: Timeout = Timeout(5 seconds)
  lazy private val system:        ActorSystem = ActorSystem("ScalaBot")
  lazy private val handler:       ActorRef = system.actorOf(Handler.props(), Handler.name())
  lazy private val memoryHandler: ActorRef = system.actorOf(MemoryHandler.props(), MemoryHandler.name())
  lazy private val studentBot:    ActorRef = system.actorOf(StudentBot.props(), StudentBot.name())
  lazy private val trieCreator:   ActorRef = system.actorOf(TrieCreator.props(), TrieCreator.name())

  def tickle(): Future[Any] = handler ? Hello

  def kill(): Future[Any] = {
    handler       ! PoisonPill
    memoryHandler ! PoisonPill
    studentBot    ! PoisonPill
    trieCreator   ! PoisonPill
    system.terminate()
  }

}
