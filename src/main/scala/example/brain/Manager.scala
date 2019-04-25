package example.brain

import bot.clustering.Clusterize
import bot.learn.RepliesLearner._
import bot.memory.Trie
import bot.memory.definition.NodeSimpleWord
import example.brain.definitions.Definitions
import example.brain.modules.MasterModule

trait Manager extends MasterModule {
  val masterBrain:                 Trie       = learn(Trie(NodeSimpleWord("".r)), List(jobs, ages, greetings).flatten, Definitions.get())
  lazy val clusterizedMasterBrain: List[Trie] = Clusterize.apply(List(jobs, ages, greetings).flatten, 2, Definitions.get())
}
