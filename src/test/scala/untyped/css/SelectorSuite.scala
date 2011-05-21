package untyped.css

import scala.xml._
import org.fusesource.scalate.scuery._
import org.fusesource.scalate.scuery.support._
import org.scalatest._
import untyped.css.TestUtil._

class SelectorSuite extends FunSuite {
  val src: NodeSeq =
    <ul>
      <li id="first" class="odd"><a/></li>
      <li class="selected"><b/></li>
      <li class="odd"><c/></li>
    </ul>
  
  test("tag name") {
    val sel = $("li")
    
    val actual = sel(src).normalize
    val expected = src \ "li"
    
    expect(expected)(actual)
  }
  
  test("ID") {
    val sel = $("#first")
    
    val actual = sel(src).normalize
    val expected = <li id="first" class="odd"><a/></li>.normalize
    
    expect(expected)(actual)
  }
  
  test("class") {
    val actual = $(".selected").apply(src)
    val expected = (src \ "li")(1)
    expect(expected)(actual)
  }
  
  // DOM traversal ------------------------------
  
  test("children") {
    val sel = $("li").children
    
    val actual = sel(src).normalize
    val expected = Group(List(<a/>, <b/>, <c/>)).normalize
      
    expect(expected)(actual)
  }
  
  test("children(string)") {
    val sel = $("li").children("a")
    
    val actual = sel(src).normalize
    val expected = <a/>.normalize

    expect(expected)(actual)
  }
  
  test("descendants(string)") {
    val sel = $("ul").descendants("b")
    
    val actual = sel(src).normalize
    val expected = <b/>.normalize
    
    expect(expected)(actual)
  }
  
  test("filter(string)") {
    val sel = $("li").children.filter("c")
    
    val actual = sel(src).normalize
    val expected = <c/>.normalize
    
    expect(expected)(actual)
  }
  
  test("filterNot(string)") {
    val sel = $("li").children.filterNot("a")
    
    val actual = sel(src).normalize
    val expected = Group(List(<b/>, <c/>)).normalize
    
    expect(expected)(actual)
  }
}