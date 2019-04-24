package bot

package object types {
  type BotMessage    = () => Set[String]
  type PossibleReply = () => Set[String]
}
