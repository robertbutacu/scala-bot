package bot.learn

trait BotReply {
  def provideReply(): Set[String]
}
