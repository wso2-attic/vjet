vjo.ctype("dsf.dom.html.level2.HTMLAreaElementTests")
.inherits("dsf.dom.html.level2.BaseTest")
.protos({


/**
*
The accessKey attribute specifies a single character access key to
give access to the control form.

Retrieve the accessKey attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-57944457
*/
//> public void HTMLAreaElement01()
HTMLAreaElement01: function() {
var nodeList;
var testNode;
var vaccesskey;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vaccesskey = testNode.accessKey;

assertEquals("a",vaccesskey);

},

/**
*
The alt attribute specifies an alternate text for user agents not
rendering the normal content of this element.

Retrieve the alt attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-39775416
*/
//> public void HTMLAreaElement02()
HTMLAreaElement02: function() {
var nodeList;
var testNode;
var valt;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
valt = testNode.alt;

assertEquals("Domain",valt);

},

/**
*
The coords attribute specifies a comma-seperated list of lengths,
defining an active region geometry.

Retrieve the coords attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-66021476
*/
//> public void HTMLAreaElement03()
HTMLAreaElement03: function() {
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
The href attribute specifies the URI of the linked resource.

Retrieve the href attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-34672936
*/
//> public void HTMLAreaElement04()
HTMLAreaElement04: function() {
var nodeList;
var testNode;
var vhref;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vhref = testNode.href;

this.assertURIEquals(null,null,null,"dletter.html",null,null,null,null,vhref);

},

/**
*
The noHref attribute specifies that this area is inactive.

Retrieve the noHref attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-61826871
*/
//> public void HTMLAreaElement05()
HTMLAreaElement05: function() {
var nodeList;
var testNode;
var vnohref;
var doc = window.document;
var assertEquals = assertEquals;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vnohref = testNode.noHref;
var assertFalse = null;
assertFalse(vnohref);

},

/**
*
The shape attribute specifies the shape of the active area.

Retrieve the shape attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-85683271
*/
//> public void HTMLAreaElement06()
HTMLAreaElement06: function() {
var nodeList;
var testNode;
var vshape;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vshape = testNode.shape;

assertEquals("rect".toLowerCase(),vshape.toLowerCase());

},

/**
*
The tabIndex attribute specifies an index that represents the element's
position in the tabbing order.

Retrieve the tabIndex attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-8722121
*/
//> public void HTMLAreaElement07()
HTMLAreaElement07: function() {
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

},

/**
*
The target specifies the frame to render the resource in.

Retrieve the target attribute and examine it's value.

* @author NIST
* @author Rick Rivello
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-46054682
*/
//> public void HTMLAreaElement08()
HTMLAreaElement08: function() {
var nodeList;
var testNode;
var vtarget;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("area");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vtarget = testNode.target;

assertEquals("dynamic",vtarget); //no TARGET

}

})
.endType();
