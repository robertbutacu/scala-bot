package scala.bot

trait Learner {
  type Templates = Map[String, List[String]]
  def learn(old: Map[String, List[String]], acquired: Map[String, List[String]]): Templates =
    old ++ acquired
}
