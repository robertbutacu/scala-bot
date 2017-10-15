package example.brain.modules

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.bot.trie.Trie
import scala.util.matching.Regex

trait Age extends Learner {
  val ages: Trie = learn(Trie(),
    Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("Im "),
        Right("[0-9]+".r, AgeAttr),
        Left(" years old"))) -> Set("Young", "Im 0 yo", "Im a bot"),
      (None, List(Left("Im 40 years old"))) -> Set("Old boiii", "CAN YOU HEAR ME?", "I love retro"),
      (Some("How old are you?"), List(Left("Im 30 years old"))) -> Set("It works")
    ))
}
