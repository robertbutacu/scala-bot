package main

import example.Bot
import example.brain.modules.AgeAttr

import scala.bot.trie.{Trie, TrieOperations}

object Main extends App
  with Bot
  with TrieOperations {
  //startDemo()
  var trie = add(List(("This".r, None), ("is".r, None), ("a".r, None), ("sentence".r, None)),
    (None, Set("first", "first2")),
    Trie())

  trie = add(List(("This".r, None), ("is".r, None), ("another".r, None), ("sentence".r, None)),
    (None, Set("second", "second2")),
    trie)

  trie = add(List(("This".r, None), ("is".r, None), ("another".r, None), ("sentence".r, None)),
    (Some("previous"), Set("second3", "second4")),
    trie)

  //println(search(List(("This".r, None), ("is".r, None), ("another".r, None), ("sentence".r, None)), trie))
  println(search("previous", trie))
  println(toWords(List(Left("Im "),
    Right("[0-9]+".r, AgeAttr),
    Left(" years old"))))
}
