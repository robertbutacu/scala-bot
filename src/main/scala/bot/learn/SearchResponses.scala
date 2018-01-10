package bot.learn

import bot.memory.Attribute

case class SearchResponses(attributesFound: Map[Attribute, String],
                           possibleReplies: Set[PossibleReply])
