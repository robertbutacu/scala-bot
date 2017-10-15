package main

import example.Bot

import scala.bot.trie.{Trie, TrieOperations}

object Main extends App
  with Bot
  with TrieOperations {
  //startDemo()
  var trie = add(List(("This".r, None), ("is".r, None), ("a".r, None), ("sentence".r, None)),
    (None, Set("first", "first2")),
    Trie(("".r, None), Set[Trie]().empty, Set[(Option[String], Set[String])]((None, Set("initial", "leafs")))))

  trie = add(List(("This".r, None), ("is".r, None), ("another".r, None), ("sentence".r, None)),
    (None, Set("second", "second2")),
    trie)

  trie = add(List(("This".r, None), ("is".r, None), ("another".r, None), ("sentence".r, None)),
    (Some("previous"), Set("second3", "second4")),
    trie)

  //println(search(List(("This".r, None), ("is".r, None), ("another".r, None), ("sentence".r, None)), trie))
  println(search("previous", trie))
}
