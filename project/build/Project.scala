import sbt._

import java.io.File

class Project(info: ProjectInfo) extends DefaultProject(info) {
  
  val scalate = "org.fusesource.scalate" % "scalate-core" % "1.4.1"
  val scalatest = "org.scalatest" % "scalatest" % "1.3" % "test"
  
  def optionOf[T](x: T) = {
    val ans = if(x == null) None else Some(x)
    println(ans)
    ans
  }

  val publishTo = {
    val option =
      for {
        host    <- optionOf(System.getenv("DEFAULT_REPO_HOST"))
        path    <- optionOf(System.getenv("DEFAULT_REPO_PATH"))
        user    <- optionOf(System.getenv("DEFAULT_REPO_USER"))
        keypath <- optionOf(System.getenv("DEFAULT_REPO_KEYFILE"))
        keyfile <- optionOf(new java.io.File(keypath))
      } yield Resolver.sftp("Default Repo", host, path).as(user, keyfile)
    
    option.getOrElse(null.asInstanceOf[Resolver])
  }
  
}