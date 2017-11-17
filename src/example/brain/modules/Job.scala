package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.learn.{HumanMessage, Reply}

trait Job extends BrainFunctions {
  val jobs: List[Reply] = List(
      Reply(HumanMessage(None, List(Left("I'm a programmer"))), Set(passionReply _)),
      Reply(HumanMessage(None, List(Left("I dont have a job"))), Set(ageReply _))
    )
}
