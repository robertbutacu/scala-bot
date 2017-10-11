package example.brain.modules

import scala.bot.learn.Learner

trait Age extends Learner {
  val ages: Templates = learn(Map[(Option[String], String), List[String]]().empty,
    Map[(Option[String], String), List[String]](
      (None, "Im 21 years old") -> List("Young", "Im 0 yo", "Im a bot"),
      (None, "Im 40 years old") -> List("Old boiii", "CAN YOU HEAR ME?", "I love retro"),
      (Some("How old are you?"), "Im 30 years old") -> List("It works")
    ))
}
