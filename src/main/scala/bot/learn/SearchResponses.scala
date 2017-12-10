package bot.learn

import bot.trie.Attribute

case class SearchResponses(attributesFound: Map[Attribute, String],
                           possibleReplies: Set[PossibleReply])
