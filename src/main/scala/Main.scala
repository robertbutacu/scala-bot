import cats.Id
import example.Bot
import cats.instances.all._
import scala.util.Try

object Main extends App {
  val bot = Bot[Try](10)

  bot.startDemo
}
