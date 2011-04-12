vjo.ctype("dsf.dom.html.level2.AreaTests")
.protos({

/**
*
*
* @author Netscape
* @author Sivakiran Tummala
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-66021476
*/
//>public void area01()
area01 : function() {
var nodeList;
var testNode;
var vcoords;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vcoords = testNode.coords;

assertEquals("0,2,45,45",vcoords);

},

/**
*
* @author Netscape
* @author Sivakiran Tummala
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-61826871
*/
//>public void area02()
area02 : function() {
var nodeList;
var testNode;
var vnohref;
var doc = window.document;
var assertFalse = null;
var assertEquals = null;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vnohref = testNode.noHref;
assertFalse(vnohref);

},

/**
*
*
* @author Netscape
* @author Sivakiran Tummala
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-8722121
*/
//>public void area03()
area03 : function() {
var nodeList;
var testNode;
var vtabindex;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vtabindex = testNode.tabIndex;

assertEquals(10,vtabindex);

}


})
.endType();
