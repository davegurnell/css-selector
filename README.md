CSS Selectors
=============

Alternative to Lift's [CSS selectors][CSS], based on Scalate's [Scuery][].

There are several motivations for this code:

 - access to a wider range of combinators;
 - access to a wider range of selectors;
 - avoid accidental binding against a list of items.

My intention is shortly to make this library (or its progeny) available
as part of [Bigtop][], Untyped's forthcoming set of extensions to Lift.

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
    
Getting the code
----------------

This code isn't currently hosted on a public repo. However, please feel free to
download it and experiment with it and let me know what you think via my Github mailbox.

If you are an SBT user, simply run the following steps:

    git clone git@github.com:davegurnell/css-selector.git ./css-selector
    cd css-selector
    sbt update
    sbt publish-local

and you'll be able to import the library into your existing SBT projects:

    Group:    bigtop
    Artefact: bigtop-css
    Version:  0.1-SNAPSHOT

If you'd rather grab your artefacts from a proper repository server,
look out for [Bigtop][], coming soon.

[CSS]: http://www.assembla.com/spaces/liftweb/wiki/Binding_via_CSS_Selectors
[Scuery]: http://scalate.fusesource.org/documentation/scuery.html
[Bigtop]: https://github.com/bigtop/bigtop
[SBT]: http://code.google.com/p/simple-build-tool/
