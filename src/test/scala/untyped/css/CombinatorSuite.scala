package untyped.css

import scala.xml._
import org.fusesource.scalate.scuery._
import org.fusesource.scalate.scuery.support._
import org.scalatest._
import untyped.css.TestUtil._

class CombinatorSuite extends FunSuite {
  val src: NodeSeq =
    <ul>
      <li><a/></li>
      <li><b/></li>
      <li><c/></li>
    </ul>.normalize
  
  test("and") {
    val bind =
      $("a").remove &
      $("b").remove
    
    val actual = bind(src)
    val expected =
      <ul>
        <li></li>
        <li></li>
        <li><c/></li>
      </ul>.normalize
    
    expect(expected)(actual)
  }
  
  test("or - positive result") {
    var fired = false
    def fn(in: NodeSeq): NodeSeq = {
      fired = true
      <replaced/>
    }
    
    val bind =
      $("a").remove |
      $("b").replace(fn _)
    
    val actual = bind(src)
    val expected =
      <ul>
        <li></li>
        <li><b/></li>
        <li><c/></li>
      </ul>.normalize
    
    expect(expected)(actual)
    expect(false)(fired)
  }
  
  test("or - negative result") {
    var fired = false
    def fn(in: NodeSeq): NodeSeq = {
      fired = true
      <replaced/>
    }
    
    val bind =
      $("A").remove |
      $("b").replace(fn _)
    
    val actual = bind(src)
    val expected =
      <ul>
        <li><a/></li>
        <li><replaced/></li>
        <li><c/></li>
      </ul>.normalize
    
    expect(expected)(actual)
    expect(true)(fired)
  }
  
  test("cond - positive result") {
    val bind =
      $("a").replace(<A/>) ?
      $("b").replace(<B/>) !
      $("b").replace(<C/>)
    
    val actual = bind(src)
    val expected =
      <ul>
        <li><A/></li>
        <li><B/></li>
        <li><c/></li>
      </ul>.normalize
    
    expect(expected)(actual)
  }
  
  test("cond - negative result") {
    val bind =
      $("A").replace(<A/>) ?
      $("b").replace(<B/>) !
      $("c").replace(<C/>)
    
    val actual = bind(src)
    val expected =
      <ul>
        <li><a/></li>
        <li><b/></li>
        <li><C/></li>
      </ul>.normalize
    
    expect(expected)(actual)
  }
}