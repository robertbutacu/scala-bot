package example.brain

import example.brain.modules.Attributes

import scala.bot.handler.MessageHandler

trait BrainFunctions extends MessageHandler with Attributes {
  def ageReply(): Set[String] = {
    getAttribute(age) match {
      case None => Set("Unknown age", "You havent told me your age")
      case Some(a) => provideReplies(a.toInt)
    }
  }

  private def provideReplies(age: Int): Set[String] =
    age match {
      case _ if age > 50 => Set("quite old", "old afff")
      case _ if age > 24 => Set("you're an adult", "children?")
      case _ if age > 18 => Set("programming", "is", "fun")
      case _ if age > 0 && age < 18  => Set("Underage", "Minor")
      case _ if age < 0  => Set(s"""It appears your age is $age . What a lie. Tell me your real age please.""")
      case _             => Set("got me there", "lost")
    }

  def passionReply(): Set[String] = {
    getAttribute(passion) match {
      case None => Set("No passions")
      case Some(pas) => Set(s"""Passionate about $pas""")
    }
  }

  def passionReplies(): Set[String] =
    getAttribute(passion) match {
      case None          => Set("You're not passionate about anything")
      case Some(p) => Set(s"""You're passionate about $p""")
    }
}
