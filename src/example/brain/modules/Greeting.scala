package example.brain.modules

import scala.bot.Learner

trait Greeting extends Learner{
  val greetings: Templates = learn(Map[String, List[String]]().empty,
    Map[String, List[String]](
      "Hi" -> List("Hello", "What's up", "Im a bot"),
      "Greetings" -> List("Greetings", "Hello", "Sup")
    ))
}
