vjo.ctype("dsf.dom.html.level2.HTMLAnchorElementTests")
.inherits("dsf.dom.html.level2.BaseTest")
.protos({

/**
*
The accessKey attribute is a single character access key to give
access to the form control.

Retrieve the accessKey attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-89647724
*/
//> public void HTMLAnchorElement01()
HTMLAnchorElement01: function() {
var nodeList;
var testNode;
var vaccesskey;
var doc = window.document;var assertEquals = null;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vaccesskey = testNode.accessKey;

assertEquals("g",vaccesskey);

},

/**
*
The charset attribute indicates the character encoding of the linked
resource.

Retrieve the charset attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-67619266
*/
//> public void HTMLAnchorElement02()
HTMLAnchorElement02: function() {
var nodeList;
var testNode;
var vcharset;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vcharset = testNode.charset;

assertEquals("US-ASCII",vcharset);

},

/**
*
The coords attribute is a comma-seperated list of lengths, defining
an active region geometry.

Retrieve the coords attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-92079539
*/
//> public void HTMLAnchorElement03()
HTMLAnchorElement03: function() {
var nodeList;
var testNode;
var vcoords;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vcoords = testNode.coords;

assertEquals("0,0,100,100",vcoords);

},

/**
*
The href attribute contains the URL of the linked resource.

Retrieve the href attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-88517319
*/
//> public void HTMLAnchorElement04()
HTMLAnchorElement04: function() {
var nodeList;
var testNode;
var vhref;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vhref = testNode.href;

vjo.sysout.println("vhref: " + vhref);

this.assertURIEquals(null,null,null,"submit.gif",null,null,null,null,vhref);

},

/**
*
The hreflang attribute contains the language code of the linked resource.

Retrieve the hreflang attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-87358513
*/
//> public void HTMLAnchorElement05()
HTMLAnchorElement05: function() {
var nodeList;
var testNode;
var vhreflink;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vhreflink = testNode.hreflang;

assertEquals("en",vhreflink);

},

/**
*
The name attribute contains the anchor name.

Retrieve the name attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-32783304
*/
//> public void HTMLAnchorElement06()
HTMLAnchorElement06: function() {
var nodeList;
var testNode;
var vname;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vname = testNode.name;

assertEquals("Anchor",vname);

},

/**
*
The rel attribute contains the forward link type.

Retrieve the rel attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-3815891
*/
//> public void HTMLAnchorElement07()
HTMLAnchorElement07: function() {
var nodeList;
var testNode;
var vrel;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vrel = testNode.rel;

assertEquals("GLOSSARY",vrel);

},

/**
*
The rev attribute contains the reverse link type

Retrieve the rev attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-58259771
*/
//> public void HTMLAnchorElement08()
HTMLAnchorElement08: function() {
var nodeList;
var testNode;
var vrev;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vrev = testNode.rev;

assertEquals("STYLESHEET",vrev);

},

/**
*
The shape attribute contains the shape of the active area.

Retrieve the shape attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-49899808
*/
//> public void HTMLAnchorElement09()
HTMLAnchorElement09: function() {
var nodeList;
var testNode;
var vshape;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vshape = testNode.shape;

assertEquals("rect",vshape);

},

/**
*
The tabIndex attribute contains an index that represents the elements
position in the tabbing order.

Retrieve the tabIndex attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-41586466
*/
//> public void HTMLAnchorElement10()
HTMLAnchorElement10: function() {
var nodeList;
var testNode;
var vtabindex;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vtabindex = testNode.tabIndex;

assertEquals(22,vtabindex);

},

/**
*
The target attribute specifies the frame to render the source in.

Retrieve the target attribute and examine it's value.

* @author NIST
* @author Rick Rivello
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-6414197
*/
//> public void HTMLAnchorElement11()
HTMLAnchorElement11: function() {
var nodeList;
var testNode;
var vtarget;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vtarget = testNode.target;

assertEquals("dynamic",vtarget); //vtarget is null since there's no TARGET in <A...>.

},

/**
*
The type attribute contains the advisory content model.

Retrieve the type attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-63938221
*/
//> public void HTMLAnchorElement12()
HTMLAnchorElement12: function() {
var nodeList;
var testNode;
var vtype;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vtype = testNode.type;

assertEquals("image/gif",vtype);

},

/**
*
HTMLAnchorElement.blur should surrender input focus.

* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-65068939
*/
//> public void HTMLAnchorElement13()
HTMLAnchorElement13: function() {
var nodeList;
var testNode;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
testNode.blur();

},

/**
*
HTMLAnchorElement.focus should capture input focus.

* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-47150313
*/
//> public void HTMLAnchorElement14()
HTMLAnchorElement14: function() {
var nodeList;
var testNode;
var doc = window.document;var assertEquals = assertEquals;

nodeList = doc.getElementsByTagName("a");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
testNode.focus();

}

})
.endType();
