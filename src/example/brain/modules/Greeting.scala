package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.trie.{HumanMessage, PartOfMessage, Reply}

trait Greeting extends BrainFunctions {
  val greetings: List[Reply] = List(
    Reply(HumanMessage(None, List(PartOfMessage(Left("Hi")))), Set(ageReply _)),
    Reply(HumanMessage(None, List(PartOfMessage(Left("Test")))), Set(ageReply _)),
    Reply(HumanMessage(None, List(PartOfMessage(Left("Greetings")))), Set(ageReply _))
  )
}
