package bot.memory

import bot.learn.{PossibleReply, SearchResponses}
import bot.memory.definition.{NodeInformation, NodeSimpleWord, NodeUserInformation, PartOfSentence}
import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}
import bot.memory.storage.Printer.TriePrinter

import scala.util.matching.Regex

/**
  *
  * @param information - current node, containing a word( or a regex), and an optional attribute
  *                    which is to be stored in map if necessary
  *                    Regex             - current word , part of the message
  *                    Option[Attribute] - in case it is something to be remembered
  * @param children    - next parts of a possible message from a client.
  * @param replies     - replies to the message starting from the top of the trie all the way down to curr.
  *                    Option[() => Set[String]    - a function returning a possible last bot message
  *                    Set[f: Any => Set[String] ] - a set of functions returning a set of possible replies
  *                    => It is done that way so that the replies are generated dynamically,
  *                    depending on the already existing/non-existing attributes.
  **/
case class Trie(information: NodeInformation,
                children: Set[Trie] = Set[Trie]().empty,
                replies: Set[PossibleReply] = Set.empty)
  extends TrieOperations {

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

    def go(curr: Trie, words: List[PartOfSentence]): Trie = {
      if (words.isEmpty) //went through all the list
        curr.addReplies(replies) // adding the replies to the Set
      else {
        val currWord = words.head

        val next = for {
          child <- this.children
          if child.information.exists(currWord)
        } yield child

        //if(next.isEmpty)
        //go(curr.addValue(currWord))

        /*curr.children.find(n => isMatching(n, words.head)) match {
          case None => go(curr.addValue(SpeakingKnowledge(words.head)), words)
          case Some(next) => SpeakingKnowledge(curr.curr,
            curr.children - next ++ Set(go(next, words.tail)),
            curr.replies)
        }*/
        Trie(NodeSimpleWord("rda".r))
      }
    }

    go(this, message)
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
  private def addReplies(replies: PossibleReply): Trie =
    this.replies.find(l => l.previousBotMessage == replies.previousBotMessage) match {
      case None => Trie(this.information, this.children, this.replies ++ Set(replies))
      case Some(rep) => addReplies(rep, replies)
    }


  private def addReplies(to: PossibleReply, newReplies: PossibleReply) =
    Trie(this.information, this.children,
      this.replies -- Set(to) + PossibleReply(to.previousBotMessage, to.possibleReply ++ newReplies.possibleReply))
}

object Trie {
  def apply(node: PartOfSentence): Trie = {
    Trie(NodeSimpleWord())
  }
}

