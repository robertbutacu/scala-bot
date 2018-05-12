package example.brain.modules

import bot.learn.{HumanMessage, Message, Reply}
import example.brain.BrainFunctions

trait Greeting extends BrainFunctions {
  val greetings: List[Reply] = List(
    Reply(HumanMessage(None, List(Message("Greetings".r, None))), Set(ageReply _))
  )
}
