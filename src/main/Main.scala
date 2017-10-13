package main

import example.Bot

import scala.bot.trie.{Leaf, Node, TrieOperations}

object Main extends App
  with Bot
  with TrieOperations {
  //startDemo()
  var trie = add(List(("This".r, None), ("is".r, None), ("a".r, None), ("sentence".r, None)), Set("first", "first"),
    Node(("".r, None), Set[Node]().empty, Leaf(Set[String]("initial", "leafs"))))

  println()

  trie = add(List(("This".r, None), ("is".r, None), ("another".r, None), ("sentence".r, None)), Set("second", "second"),
    trie)

  printTrie(trie)
}
