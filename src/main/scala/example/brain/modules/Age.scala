package example.brain.modules

import bot.learn.{HumanMessage, Reply}
import example.brain.BrainFunctions


trait Age extends BrainFunctions with Attributes {
  val ages: List[Reply] = List(
    Reply(
      HumanMessage(None,
        List(("Im ".r, None),
          ("[0-9]+".r, Some(age)),
          (" years old".r, None))),
      Set(ageReply _)
    ),
    Reply(
      HumanMessage(None,
        List(("Im passionate about".r, None),
          ("[a-zA-Z]+".r, Some(passion)))),
      Set(passionReply _)
    ),
    Reply(
      HumanMessage(Some(passionReply _),
        List(("What am i passionate about".r, None))),
      Set(passionReplies _)
    )
  )
}
