package untyped

import org.fusesource.scalate.scuery._
import untyped.css.impl._

package object css {
  def $(sel: String): Sel = Sel(Selector(sel))
}