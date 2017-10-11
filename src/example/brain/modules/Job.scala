package example.brain.modules

import scala.bot.learn.Learner

trait Job extends Learner {
  val jobs: Templates = learn(Map[(Option[String], String), List[String]]().empty,
    Map[(Option[String], String), List[String]](
      (None, "I'm a programmer")  -> List("Me too", "I've been created using Scala", "Thats awesome"),
      (None, "I dont have a job") -> List("Broke :/", "Are you searching for a job?", "its okay...")
    ))
}
