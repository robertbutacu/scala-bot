package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.learn.{HumanMessage, Reply}

trait Job extends BrainFunctions {
  val jobs: List[Reply] = List(
      Reply(HumanMessage(None, List(("I'm a programmer".r, None))), Set(passionReply _)),
      Reply(HumanMessage(None, List(("I dont have a job".r, None))), Set(ageReply _))
    )
}
