package example.brain

import example.brain.modules.MasterModule

import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait Manager extends MasterModule {
  lazy val masterBrain: Templates = learn(Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses]().empty,
    List(ages, greetings))
}
