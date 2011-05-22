CSS Selectors
=============

Alternative to Lift's [CSS selectors][CSS], based on Scalate's [Scuery][].

There are several motivations for this code:

 - access to a wider range of combinators;
 - access to a wider range of selectors;
 - avoid accidental binding against a list of items.

Examples
--------
    
    // Wider range of CSS selectors:
    $("#nav li span") replace <foo/>    // replace selected nodes with <foo/>
    
    // Lift-style #> operator:
    $("#nav li span") #> <foo/>         // replace selected nodes with <foo/>
    
    // Traversal operators:
    $(".foo") children ".bar" remove    // remove children of .foo that match the selector .bar
    
    // 'remove' transformer:
    $("[deleteme]") remove              // remove nodes with an attr "deleteme"
    
    // Conjunction combinator:
    $("#name") replace "Dave" &         // replace with literal NodeSeq
    $("#age") replace (in => getAge)    // replace with NodeSeq => NodeSeq function
    
    // Disjunction combinator:
    $(".red") remove |                  // remove .red if it exists
    $(".green") remove                  // otherwise remove .green
    
    // Conditional combinator:
    $(".red") remove ?                  // remove .red if it exists
    $(".green") remove !                // if .red was removed, remove .green as well
    $(".blue") remove                   // otherwise remove .blue
    
[CSS]: http://www.assembla.com/spaces/liftweb/wiki/Binding_via_CSS_Selectors
[Scuery]: http://scalate.fusesource.org/documentation/scuery.html
