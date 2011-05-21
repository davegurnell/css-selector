package untyped.css

import scala.xml._
import org.fusesource.scalate.scuery._
import org.fusesource.scalate.scuery.support._
import org.scalatest._
import TestUtil._

class TransformerSuite extends FunSuite {
  val src: NodeSeq =
    <ul>
      <li><a/></li>
      <li><b/></li>
      <li><c/></li>
    </ul>.normalize
  
  test("replace(string)") {
    val bind = $("a").replace("foo")
    
    val actual = bind(src).normalize
    val expected =
      <ul>
        <li>foo</li>
        <li><b/></li>
        <li><c/></li>
      </ul>.normalize
    
    expect(expected)(actual)
  }
  
  test("replace(nodeseq)") {
    val bind = $("a").replace(<hr/>)
    
    val actual = bind(src).normalize
    val expected =
      <ul>
        <li><hr/></li>
        <li><b/></li>
        <li><c/></li>
      </ul>.normalize
    
    expect(expected)(actual)
  }
  
  test("replace(nodeseq => nodeseq)") {
    val bind = $("a").replace(in => in ++ Text("!"))
    
    val actual = bind(src).normalize
    val expected =
      <ul>
        <li><a/>!</li>
        <li><b/></li>
        <li><c/></li>
      </ul>.normalize
    
    expect(expected)(actual)
  }
  
  test("remove") {
    val bind = $("a").remove
    
    val actual = bind(src).normalize
    val expected =
      <ul>
        <li/>
        <li><b/></li>
        <li><c/></li>
      </ul>.normalize
    
    expect(expected)(actual)
  }
}