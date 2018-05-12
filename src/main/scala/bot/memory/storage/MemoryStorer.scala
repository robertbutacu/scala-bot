package bot.memory.storage

import bot.learn.PossibleReply
import bot.memory.Trie
import bot.memory.definition.{NodeSimpleWord, PartOfSentence}

import scala.annotation.tailrec

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

    // TODO state monad => prev and curr held
    override final def add(message: List[PartOfSentence],
                           replies: PossibleReply): Trie = {

      //TODO make this a future => a lot of possible parallel operations
      @tailrec
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
            go(curr.addValue(currWord))
          else {
            Trie(curr.information, curr.children -- next ++ next.map(t => t.add(words.tail)), trie.replies)
          }
        }
      }

      go(trie, message)
    }

    /**
      * @param replies - replies that are to be added
      * @return     - new leafs which also contain the new replies
      *             There are 2 cases:
      *             1. when they depend on a previous bot message ( or lack of) also stored:
      *             the replies are appended to the already existing replies.
      *             2. when they aren't stored at all:
      *             they are registered as new replies with their attribute.
      *
      */
    private def addReplies(trie: Trie, replies: PossibleReply): Trie =
      trie.replies.find(l => l.previousBotMessage == replies.previousBotMessage) match {
        case None => Trie(trie.information, trie.children, trie.replies ++ Set(replies))
        case Some(rep) => addReplies(trie, rep, replies)
      }

    private def addReplies(trie: Trie, to: PossibleReply, newReplies: PossibleReply) =
      Trie(trie.information, trie.children,
        trie.replies -- Set(to) + PossibleReply(to.previousBotMessage, to.possibleReply ++ newReplies.possibleReply))

  }
}
