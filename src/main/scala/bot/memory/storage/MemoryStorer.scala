package bot.memory.storage

import bot.learn.PossibleReply
import bot.memory.Trie
import bot.memory.definition.PartOfSentence

trait MemoryStorer {
  def add(message: List[PartOfSentence], replies: PossibleReply): Trie
}

object MemoryStorer {

  implicit class TrieMemoryStorer(trie: Trie) extends MemoryStorer {
    /**
      * For a current Trie, the algorithm returns another trie with the message added.
      * The pattern matching does the following:
      *   1. in case of None => current word isn't in the set => add it, and call the function with the same node
      *   2. in case of Some(next) => next node has been found => remove it from the Set, since its gonna be different 
      * it would double stack otherwise
      *
      * @param message - list of words to be added into the trie
      * @param replies - a set of functions which return a set of possible replies
      * @return - a new trie with the new message included
      */
    override final def add(message: List[PartOfSentence],
                           replies: PossibleReply): Trie = {
      def go(curr: Trie, words: List[PartOfSentence]): Trie = {
        if (words.isEmpty) //went through all the list
          this.addReplies(curr, replies) // adding the replies to the Set
        else {
          val currWord = words.head

          val next = for {
            child <- curr.children
            if child.information.exists(currWord)
          } yield child

          if (next.isEmpty)
            go(this.addValue(curr, currWord), words.tail)
          else {
            trie.copy(children = curr.children -- next ++ next.map(t => go(t, words.tail)))
          }
        }
      }

      go(trie, message)
    }

    /**
      * As part of the "storing" journey, this is at the very end - where the replies have to be added.
      *
      * @param replies - replies that are to be added
      * @return     - new leafs which also contain the new replies
      *             There are 2 cases:
      *             1. when the replies depend on a previous bot message ( or lack of ) also stored:
      *             the replies are appended to the already existing replies.
      *             2. when they aren't stored at all:
      *             they are registered as new replies with their attribute.
      */
    private def addReplies(trie: Trie, replies: PossibleReply): Trie =
      trie.replies.find(_.previousBotMessage == replies.previousBotMessage) match {
        case None      => trie.copy(replies = trie.replies + replies)
        case Some(rep) => updateReplies(trie, rep, replies)
      }

    private def updateReplies(trie:       Trie,
                              to:         PossibleReply,
                              newReplies: PossibleReply): Trie =
      trie.copy(replies = trie.replies - to + to.copy(possibleReply = to.possibleReply ++ newReplies.possibleReply))

    private def addValue(trie: Trie, node: PartOfSentence): Trie = trie.copy(children = trie.children + Trie(node))
  }
}
