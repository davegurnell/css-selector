import sbt._

import java.io.File

class Project(info: ProjectInfo) extends DefaultProject(info) {
  
  val scalate = "org.fusesource.scalate" % "scalate-core" % "1.4.1"
  val scalatest = "org.scalatest" % "scalatest" % "1.3" % "test"
  
  val publishTo = {
    val host = System.getenv("DEFAULT_REPO_HOST")
    val path = System.getenv("DEFAULT_REPO_PATH")
    val user = System.getenv("DEFAULT_REPO_USER")
    val keyfile = new File(System.getenv("DEFAULT_REPO_KEYFILE"))
    
    Resolver.sftp("Default Repo", host, path).as(user, keyfile)
  }
  
}