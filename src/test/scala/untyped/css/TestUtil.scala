package untyped.css

import scala.xml._

object TestUtil {
  implicit def wrapXml(in: NodeSeq): XmlWrapper =
    XmlWrapper(in)
  
  case class XmlWrapper(val in: NodeSeq) {
    def normalize: NodeSeq =
      normalizeSeq(in.toList)
  }
  
  def normalizeNode(in: Node): Node =
    in match {
      case pcdata: PCData =>
        pcdata
  
      case Text(data) =>
        Text(normalizeString(data))

      case unparsed: Unparsed =>
        unparsed
  
      case Comment(text) =>
        Comment(normalizeString(text))
  
      case doc: Document =>
        Group(normalizeSeq(doc.children.toList))
  
      case Elem(prefix, label, attrs, scope, children @ _*) =>
        Elem(prefix, label, attrs, scope, normalizeSeq(children.toList) : _*)
  
      case ref: EntityRef =>
        ref
    
      case Group(children) =>
        Group(normalizeSeq(children.toList))
  
      case instr: ProcInstr =>
        instr
    }
  
  def normalizeSeq(in: List[Node]): List[Node] =
    in match {
      case Text(a) :: Text(b) :: rest =>
        normalizeSeq(Text(a + b) :: rest)
      
      case head :: rest =>
        normalizeNode(head) :: normalizeSeq(rest)
      
      case Nil => Nil
    }
    

  def normalizeString(in: String) =
    in.trim.replace("""[ \t\r\n]+""", " ")
}