package example.brain.modules

import bot.learn.{HumanMessage, Reply}
import example.brain.BrainFunctions

trait Greeting extends BrainFunctions {
  val greetings: List[Reply] = List(
    Reply(HumanMessage(None, List(("Hi".r, None))), Set(ageReply _)),
    Reply(HumanMessage(None, List(("Test".r, None))), Set(ageReply _)),
    Reply(HumanMessage(None, List(("Greetings".r, None))), Set(ageReply _))
  )
}
