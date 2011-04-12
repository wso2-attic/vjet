vjo.ctype("dsf.dom.html.level2.HTMLBodyElementTests")
.protos({

/**
*
The aLink attribute specifies the color of active links.

Retrieve the aLink attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-59424581
*/
//> public void HTMLBodyElement01()
HTMLBodyElement01: function() {
var nodeList;
var testNode;
var valink;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("body");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
valink = testNode.aLink;

assertEquals("#0000ff",valink);

},

/**
*
The background attribute specifies the URI fo the background texture
tile image.

Retrieve the background attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-37574810
*/
//> public void HTMLBodyElement02()
HTMLBodyElement02: function() {
var nodeList;
var testNode;
var vbackground;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("body");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vbackground = testNode.background;

assertEquals("./pix/back1.gif",vbackground);

},

/**
*
The bgColor attribute specifies the document background color.

Retrieve the bgColor attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-24940084
*/
//> public void HTMLBodyElement03()
HTMLBodyElement03: function() {
var nodeList;
var testNode;
var vbgcolor;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("body");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vbgcolor = testNode.bgColor;

assertEquals("#ffff00",vbgcolor);

},

/**
*
The link attribute specifies the color of links that are not active
and unvisited.

Retrieve the link attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-7662206
*/
//> public void HTMLBodyElement04()
HTMLBodyElement04: function() {
var nodeList;
var testNode;
var vlink;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("body");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vlink = testNode.link;

assertEquals("#ff0000",vlink);

},

/**
*
The text attribute specifies the document text color.

Retrieve the text attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-73714763
*/
//> public void HTMLBodyElement05()
HTMLBodyElement05: function() {
var nodeList;
var testNode;
var vtext;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("body");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vtext = testNode.text;

assertEquals("#000000",vtext);

},

/**
*
The vLink attribute specifies the color of links that have been
visited by the user.

Retrieve the vLink attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-83224305
*/
//> public void HTMLBodyElement06()
HTMLBodyElement06: function() {
var nodeList;
var testNode;
var vvlink;
var doc = window.document;
var assertEquals = assertEquals;
nodeList = doc.getElementsByTagName("body");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vvlink = testNode.vLink;

assertEquals("#00ffff",vvlink);

},

/**
*
Checks that Node.isSupported("hTmL", null) returns true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports
* @see http://www.w3.org/TR/DOM-Level-2-HTML/html#ID-62018039
*/
//> public void HTMLBodyElement07()
HTMLBodyElement07: function() {
var doc = window.document;
var body;
var state;
var version = null;

var assertTrue = assertTrue;
body = doc.body;

state = body.isSupported("hTmL",version); //no isSupported
assertTrue(state);

},

/**
*
Checks that Node.isSupported("hTmL", "2.0") returns true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports
* @see http://www.w3.org/TR/DOM-Level-2-HTML/html#ID-62018039
*/
//> public void HTMLBodyElement08()
HTMLBodyElement08: function() {
var doc = window.document;
var body;
var state;
var version = "2.0";

body = doc.body;
var assertTrue = assertTrue;
state = body.isSupported("hTmL",version); //no isSupported
assertTrue(state);

},

/**
*
Checks that Node.isSupported("xhTmL", null) returns true if hasFeature("XML", null) is true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports
* @see http://www.w3.org/TR/DOM-Level-2-HTML/html#ID-62018039
*/
//> public void HTMLBodyElement09()
HTMLBodyElement09: function() {
var doc = window.document;
var body;
var state;
var hasXML;
var version = null;


body = doc.body;
var assertTrue = assertTrue;
hasXML = body.isSupported("XML",version); //no isSupported
state = body.isSupported("xhTmL",version);
var assertEquals = assertEquals;
assertEquals(hasXML,state);

},

/**
*
Checks that Node.isSupported("xhTmL", "2.0") returns true if hasFeature("XML", "2.0") is true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports
* @see http://www.w3.org/TR/DOM-Level-2-HTML/html#ID-62018039
*/
//> public void HTMLBodyElement10()
HTMLBodyElement10: function()  {
var doc = window.document;;
var body;
var state;
var hasXML;
var version = "2.0";


body = doc.body;
var assertTrue = assertTrue;
hasXML = body.isSupported("XML",version); // no isSupported
state = body.isSupported("xhTmL",version);
var assertEquals = assertEquals;
assertEquals("isSupportedXHTML",hasXML,state);

},

/**
*
Checks that Node.isSupported("cOrE", null) returns true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports
* @see http://www.w3.org/TR/DOM-Level-2-HTML/html#ID-62018039
*/
//> public void HTMLBodyElement11()
HTMLBodyElement11: function() {

var doc = window.document;
var body;
var state;
var version = null;

body = doc.body;
var assertTrue = assertTrue;
state = body.isSupported("cOrE",version); // no isSupported
assertTrue("isSupportedCore",state);

},

/**
*
Checks that Node.isSupported("cOrE", "2.0") returns true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#Level-2-Core-Node-supports
* @see http://www.w3.org/TR/DOM-Level-2-HTML/html#ID-62018039
*/
//> public void HTMLBodyElement12()
HTMLBodyElement12: function() {
var doc = window.document;
var body;
var state;
var version = "2.0";

body = doc.body;
var assertTrue = assertTrue;
state = body.isSupported("cOrE",version); //no isSupported
assertTrue("isSupportedCore",state);

}


})
.endType();
