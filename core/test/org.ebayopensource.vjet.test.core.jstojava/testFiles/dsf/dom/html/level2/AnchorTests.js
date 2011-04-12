vjo.ctype("dsf.dom.html.level2.AnchorTests")
.protos({

/**
* A single character access key to give access to the form control.
* The value of attribute accessKey of the anchor element is read and checked against the expected value.
*
* @author Netscape
* @author Sivakiran Tummala
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-89647724
*/
//>public void anchor01()
anchor01 : function() {
var nodeList;
var testNode;
var vaccesskey;
var doc = window.document;
var assertEquals = null; //assertEquals
nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vaccesskey = testNode.accessKey;

assertEquals("g",vaccesskey);

},

/**
* The character encoding of the linked resource.
* The value of attribute charset of the anchor element is read and checked against the expected value.
*
* @author Netscape
* @author Sivakiran Tummala
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-67619266
*/
//>public void anchor02()
anchor02 : function() {
var nodeList;
var testNode;
var vcharset;
var doc = window.document;
var assertEquals = null;//assertEquals;
nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vcharset = testNode.charset;

assertEquals("US-ASCII",vcharset);

},

/**
* Comma-separated list of lengths, defining an active region geometry.
* The value of attribute coords of the anchor element is read and checked against the expected value.
*
* @author Netscape
* @author Sivakiran Tummala
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-92079539
*/
//>public void anchor03()
anchor03 : function() {
var nodeList;
var testNode;
var vcoords;
var doc = window.document;
var assertEquals = null;// assertEquals;
nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vcoords = testNode.coords;

assertEquals("0,0,100,100",vcoords);

}


})
.endType();
