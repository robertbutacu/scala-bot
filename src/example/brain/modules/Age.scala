package example.brain.modules

import scala.bot.Learner

trait Age extends Learner{
  val ages: Templates = learn(Map[String, List[String]]().empty,
    Map[String, List[String]](
      "Im 21 years old" -> List("Young", "Im 0 yo", "Im a bot"),
      "Im 40 years old" -> List("Old boiii", "CAN YOU HEAR ME?", "I love retro")
    ))

}
