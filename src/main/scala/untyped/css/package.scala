package untyped

import org.fusesource.scalate.scuery._

package object css {
  def $(sel: String): Sel = Sel(Selector(sel))
}