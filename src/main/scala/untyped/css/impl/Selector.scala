package untyped.css.impl

import scala.xml._
import org.fusesource.scalate.scuery._
import org.fusesource.scalate.scuery.support._

case class Sel(val inner: Selector) {
  
  def apply(in: NodeSeq): NodeSeq =
    inner.filter(in)
  
  // Selectors ----------------------------------
  
  def children: Sel =
    Sel(Selector(inner, List(ChildCombinator(AnyElementSelector()))))
  
  def children(sel: String): Sel =
    Sel(Selector(inner, List(ChildCombinator(Selector(sel)))))
  
  def descendants(sel: String): Sel =
    Sel(Selector(inner, List(DescendantCombinator(Selector(sel)))))
  
  def filter(sel: String): Sel =
    Sel(CompositeSelector(List(inner, Selector(sel))))
  
  def filterNot(sel: String): Sel =
    Sel(CompositeSelector(List(inner, NotSelector(Selector(sel)))))
  
  def \(sel: String) = children(sel)
  
  def \\(sel: String) = descendants(sel)
  
  // Transformers -------------------------------
  
  def replace(fn: NodeSeq => NodeSeq): Trans =
    BaseTrans(inner, ReplaceRule(fn))
  
  def replace(out: NodeSeq): Trans =
    BaseTrans(inner, ReplaceRule(in => out))
  
  def replace(out: String): Trans =
    replace(Text(out))
  
  def replaceStar(nodes: List[NodeSeq]): Trans =
    BaseTrans(inner, ReplaceRule(in => nodes.flatMap(x => x)))
  
  def remove: Trans =
    BaseTrans(inner, ReplaceRule(in => NodeSeq.Empty))
  
  def #>(fn: NodeSeq => NodeSeq): Trans = replace(fn)
  def #>(out: NodeSeq): Trans = replace(out)
  def #>(out: String): Trans = replace(out)
  def #>(out: List[NodeSeq]): Trans = replaceStar(out)
  
}