package example.brain.modules

import bot.learn.{HumanMessage, Message, MessageTemplate}
import example.brain.BrainFunctions


trait Age extends BrainFunctions with Attributes {
  val ages: List[MessageTemplate] = List(
    MessageTemplate(
      HumanMessage(None,
        List(Message("Im ".r, None),
          Message("[0-9]+".r, Some(age)),
          Message(" years old".r, None))),
      Set(ageReply())
    ),
    MessageTemplate(
      HumanMessage(None,
        List(Message("Im passionate about".r, None),
          Message("[a-zA-Z]+".r, Some(passion)))),
      Set(passionReply())
    ),
    MessageTemplate(
      HumanMessage(Some(passionReply()),
        List(Message("What am i passionate about".r, None))),
      Set(passionReplies())
    )
  )
}
