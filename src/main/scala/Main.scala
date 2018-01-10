import bot.actors.Master
import example.Bot
import example.brain.definitions.Definitions

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {
  //Master.tickle().onComplete(s => println(s + "1"))
  //Master.tickle().onComplete(s => println(s + "2"))
 // new Bot().startDemo()
  //Master.kill()
  Definitions.get().foreach(println)
}
