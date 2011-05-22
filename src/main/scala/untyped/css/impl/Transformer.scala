package untyped.css.impl

import scala.xml._
import org.fusesource.scalate.scuery._
import org.fusesource.scalate.scuery.support._

trait Trans extends Function1[NodeSeq, NodeSeq] {
  def apply(in: NodeSeq): NodeSeq =
    matchAndTransform(in, Nil)._1
  
  /**
  * Attempt to transform NodeSeq using the selector and transform rule.
  * Return the transformed NodeSeq, and whether or not the rule fired during the transformation.
  */
  def matchAndTransform(in: NodeSeq, ancestors: Seq[Node]): (NodeSeq, Boolean)
  
  def and(trans: Trans): Trans =
    AndTrans(this, trans)
  
  def &(trans: Trans) =
    and(trans)
  
  def or(trans: Trans): Trans =
    OrTrans(this, trans)
  
  def |(trans: Trans) =
    or(trans)
  
  def ?(trans: Trans): CondBuilder =
    CondBuilder(this, trans)
}

case class BaseTrans(val sel: Selector, val rule: Rule) extends Trans {
  def matchAndTransform(in: NodeSeq, ancestors: Seq[Node]): (NodeSeq, Boolean) =
    in.foldLeft((NodeSeq.Empty, false)) { (accum, node) =>
      val (transformed, fired) = matchAndTransformNode(node, ancestors)
      (accum._1 ++ transformed, accum._2 || fired)
    }
  
  def matchAndTransformNode(node: Node, ancestors: Seq[Node]): (NodeSeq, Boolean) =
    if(sel.matches(node, ancestors)) {
      (rule(node), true)
    } else {
      node match {
        case elem: Elem =>
          val (children, fired) = matchAndTransform(elem.child, elem +: ancestors)
          (XmlHelper.replaceContent(elem, children), fired)
          
        case doc: Document =>
          matchAndTransform(doc.child, ancestors)
          
        case other =>
          (other, false)
      }
    }
}

case class AndTrans(val left: Trans, val right: Trans) extends Trans {
  def matchAndTransform(nodes0: NodeSeq, ancestors: Seq[Node]): (NodeSeq, Boolean) = {
    val (nodes1, fired1) = left.matchAndTransform(nodes0, ancestors)
    val (nodes2, fired2) = right.matchAndTransform(nodes1, ancestors)
    (nodes2, fired1 || fired2)
  }
}

case class OrTrans(val left: Trans, val right: Trans) extends Trans {
  def matchAndTransform(in: NodeSeq, ancestors: Seq[Node]): (NodeSeq, Boolean) = {
    val (nodes, fired) = left.matchAndTransform(in, ancestors)
    if(fired) {
      (nodes, fired)
    } else {
      right.matchAndTransform(in, ancestors)
    }
  }
}

case class CondBuilder(ifTrans: Trans, thenTrans: Trans) {
  def !(elseTrans: Trans): CondTrans =
    CondTrans(ifTrans, thenTrans, elseTrans)
}

case class CondTrans(val ifTrans: Trans, val thenTrans: Trans, val elseTrans: Trans) extends Trans {
  def matchAndTransform(nodes0: NodeSeq, ancestors: Seq[Node]): (NodeSeq, Boolean) = {
    val (nodes1, fired1) = ifTrans.matchAndTransform(nodes0, ancestors)
    
    if(fired1) {
      val (nodes2, fired2) = thenTrans.matchAndTransform(nodes1, ancestors)
      (nodes2, fired1 || fired2)
    } else {
      val (nodes3, fired3) = elseTrans.matchAndTransform(nodes1, ancestors)
      (nodes3, fired1 || fired3)
    }
  }
}

