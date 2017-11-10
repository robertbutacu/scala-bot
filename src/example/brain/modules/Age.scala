package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.trie.{HumanMessage, PartOfMessage, Reply}

trait Age extends BrainFunctions with Attributes{
  val ages: List[Reply] = List(
    Reply(HumanMessage(None, List(PartOfMessage(Left("Im ")),
      PartOfMessage(Right("[0-9]+".r, age)),
      PartOfMessage(Left(" years old")))), Set(ageReply _)),
    Reply(HumanMessage(None, List(PartOfMessage(Left("Im passionate about")),
      PartOfMessage(Right("[a-zA-Z]+".r, passion)))),
      Set(passionReply _)),
    Reply(HumanMessage(Some(passionReply _),
      List(PartOfMessage(Left("What am i passionate about")))),
      Set(passionReplies _))
  )
}
