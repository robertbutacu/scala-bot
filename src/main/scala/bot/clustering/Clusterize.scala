package bot.clustering

import bot.learn.Reply
import bot.memory.Trie

import scala.annotation.tailrec

object Clusterize {
  type ClusteredReplies = List[List[Reply]]

  def apply(templates: List[Reply], numberOfClusters: Int): List[Trie] = {
    def regexToString(r: Reply) = r.humanMessage.message.foldRight("")((curr, acc) => acc ++ curr.pattern.toString)

    val sortedTemplates = templates.sortBy(regexToString)

    val divided = divide(sortedTemplates, numberOfClusters)

    List.empty
  }

  private def divide(templates: List[Reply], numberOfClusters: Int): ClusteredReplies = {
    def optimum = Math.ceil(templates.size.toDouble / numberOfClusters).toInt

    @tailrec
    def go(remaining: List[Reply], results: ClusteredReplies, sliceSize: Int): List[List[Reply]] = {
      if(remaining.length <= sliceSize)
        results :+ remaining
      else{
        val updatedRemaining = remaining.drop(sliceSize)
        val updatedResults = results :+ remaining.slice(0, sliceSize)

        go(updatedRemaining, updatedResults, sliceSize)
      }
    }

    go(templates, List.empty, optimum)
  }
}
