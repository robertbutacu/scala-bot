package example.brain.modules

import scala.bot.Learner

trait Greeting extends Learner {
  val greetings: Templates = learn(Map[(Option[String], String), List[String]]().empty,
    Map[(Option[String], String), List[String]](
      (None, "Hi") -> List( "Hello", "What's up", "Im a bot"),
      (None, "Greetings") -> List("Greetings", "Hello", "Sup")
    ))
}
