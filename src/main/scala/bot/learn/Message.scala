package bot.learn

import bot.memory.Attribute

import scala.util.matching.Regex

case class Message(pattern: Regex, attribute: Option[Attribute])
