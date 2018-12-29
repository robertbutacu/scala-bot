package example.brain.modules

import bot.learn.{HumanMessage, Message, MessageTemplate}
import example.brain.BrainFunctions

trait Greeting extends BrainFunctions {
  val greetings: List[MessageTemplate] = List(
    MessageTemplate(HumanMessage(None, List(Message("Greetings".r, None))), Set(ageReply _))
  )
}
