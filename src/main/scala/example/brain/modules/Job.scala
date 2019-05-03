package example.brain.modules

import bot.learn.{HumanMessage, Message, MessageTemplate}
import example.brain.BrainFunctions

trait Job extends BrainFunctions {
  val jobs: List[MessageTemplate] = List(
    MessageTemplate(HumanMessage(None, List(Message("I'm a programmer".r, None))), Set(passionReply())),
    MessageTemplate(HumanMessage(None, List(Message("I dont have a job".r, None))), Set(ageReply()))
  )
}
