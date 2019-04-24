import cats.Id
import example.Bot
import cats.instances.all._

object Main extends App {
  val bot = Bot[Id](10)

  bot.startDemo
}
