package bot.learn

import bot.memory.Attribute

import scala.util.matching.Regex

case class PartOfMessage(pattern: Regex, attribute: Option[Attribute]) {

}
