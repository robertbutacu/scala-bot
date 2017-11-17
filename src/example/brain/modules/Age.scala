package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.learn.{HumanMessage, Reply}

trait Age extends BrainFunctions with Attributes {
  val ages: List[Reply] = List(
    Reply(
      HumanMessage(None,
        List(Left("Im "),
          Right("[0-9]+".r, age),
          Left(" years old"))),
      Set(ageReply _)
    ),
    Reply(
      HumanMessage(None,
        List(Left("Im passionate about"),
          Right("[a-zA-Z]+".r, passion))),
      Set(passionReply _)
    ),
    Reply(
      HumanMessage(Some(passionReply _),
        List(Left("What am i passionate about"))),
      Set(passionReplies _)
    )
  )
}
