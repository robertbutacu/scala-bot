package main

import example.Bot

import scala.bot.trie.{Trie, TrieOperations}

object Main extends App
  with Bot
  with TrieOperations {
  //startDemo()
  var trie = add(List(("This".r, None), ("is".r, None), ("a".r, None), ("sentence".r, None)), Set("first", "first2"),
    Trie(("".r, None), Set[Trie]().empty, Set[String]("initial", "leafs")))

  trie = add(List(("This".r, None), ("is".r, None), ("another".r, None), ("sentence".r, None)), Set("second", "second2"),
    trie)

  println(search(List(("This".r, None), ("is".r, None), ("a".r, None), ("sentence".r, None)), trie))
}
