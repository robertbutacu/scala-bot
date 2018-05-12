package example.brain.modules

import bot.learn.{HumanMessage, Message, Reply}
import example.brain.BrainFunctions


trait Age extends BrainFunctions with Attributes {
  val ages: List[Reply] = List(
    Reply(
      HumanMessage(None,
        List(Message("Im ".r, None),
          Message("[0-9]+".r, Some(age)),
          Message(" years old".r, None))),
      Set(ageReply _)
    ),
    Reply(
      HumanMessage(None,
        List(Message("Im passionate about".r, None),
          Message("[a-zA-Z]+".r, Some(passion)))),
      Set(passionReply _)
    ),
    Reply(
      HumanMessage(Some(passionReply _),
        List(Message("What am i passionate about".r, None))),
      Set(passionReplies _)
    )
  )
}
