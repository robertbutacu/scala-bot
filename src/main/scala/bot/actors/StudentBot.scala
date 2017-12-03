package bot.actors

import bot.learn.Reply
import bot.trie.Trie

object StudentBot {
  case class LearnReply(trie: Trie, acquired: List[Reply])

}

class StudentBot {

}
