package bot.clustering

import bot.learn.{MessageTemplate, RepliesLearner}
import bot.memory.Trie
import bot.memory.definition.Definition

object Clusterize {
  type ClusteredReplies = List[List[MessageTemplate]]

  def apply(templates: List[MessageTemplate], numberOfClusters: Int, dictionary: Set[Definition]): List[Trie] = {
    def regexToString(r: MessageTemplate) = r.humanMessage.message.foldRight("")((curr, acc) => acc ++ curr.pattern.toString)

    val sortedTemplates = templates.sortBy(regexToString)
    val grouped = sortedTemplates.grouped(numberOfClusters)

    grouped.map(messages => RepliesLearner.learn(Trie.empty, messages, dictionary)).toList
  }
}
