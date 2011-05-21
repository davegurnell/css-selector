import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  
  val scalate = "org.fusesource.scalate" % "scalate-core" % "1.4.1"
  val scalatest = "org.scalatest" % "scalatest" % "1.3" % "test"

}