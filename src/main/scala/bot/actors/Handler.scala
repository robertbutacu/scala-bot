package bot.actors

import bot.trie.Trie

object Handler {

  case class Handle(trie: Trie,
                    msg: String,
                    humanLog: List[String],
                    botLog: List[String])

  case class BotResponse(msg: String)

}

class Handler {

}
