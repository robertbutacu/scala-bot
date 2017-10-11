package scala.bot

trait Learner {
  def learn(old: Map[String, List[String]], acquired: Map[String, List[String]]): Map[String, List[String]] =
    old ++ acquired
}
