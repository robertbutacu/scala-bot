package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.trie.{HumanMessage, Reply}

trait Greeting extends BrainFunctions {
  val greetings: List[Reply] = List(
    Reply(HumanMessage(None, List(Left("Hi"))), Set(ageReply _)),
    Reply(HumanMessage(None, List(Left("Test"))), Set(ageReply _)),
    Reply(HumanMessage(None, List(Left("Greetings"))), Set(ageReply _))
  )
}
