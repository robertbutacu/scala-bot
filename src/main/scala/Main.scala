import akka.actor.ActorSystem
import bot.actors.Master
import example.Bot

object Main extends App {
  println(Master.tickle())
  new Bot().startDemo()
}
