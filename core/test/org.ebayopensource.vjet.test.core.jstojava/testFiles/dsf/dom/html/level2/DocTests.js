vjo.ctype("dsf.dom.html.level2.DocTests")
.protos({

/**
*
Retrieve the title attribute of HTMLDocument and examine it's value.

* @author Netscape
* @author Sivakiran Tummala
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-18446827
*/
//> public void doc01()
doc01: function() {
var vtitle;
var doc= window.document;

vtitle = doc.title;

assertEquals("NIST DOM HTML Test - Anchor",vtitle);

}

})
.endType();
