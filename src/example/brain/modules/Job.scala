package example.brain.modules

import scala.bot.Learner

trait Job extends Learner {
  val jobs: Templates = learn(Map[String, List[String]]().empty,
    Map[String, List[String]](
      "I'm a programmer" -> List("Me too", "I've been created using Scala", "Thats awesome"),
      "I dont have a job" -> List("Broke :/", "Are you searching for a job?", "its okay...")
    ))
}
