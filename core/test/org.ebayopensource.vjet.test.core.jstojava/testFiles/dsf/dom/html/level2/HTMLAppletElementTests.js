vjo.ctype("dsf.dom.html.level2.HTMLAppletElementTests")
.protos({


/**
*
The align attribute specifies the alignment of the object(Vertically
or Horizontally) with respect to its surrounding text.

Retrieve the align attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-8049912
*/
//> public void HTMLAppletElement01()
HTMLAppletElement01: function() {
var nodeList;
var testNode;
var valign;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
valign = testNode.align;

assertEquals("bottom".toLowerCase(),valign.toLowerCase());

},

/**
*
The alt attribute specifies the alternate text for user agents not
rendering the normal context of this element.

Retrieve the alt attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-58610064
*/
//> public void HTMLAppletElement02()
HTMLAppletElement02: function() {
var nodeList;
var testNode;
var valt;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
valt = testNode.alt;

assertEquals("Applet Number 1",valt);

},

/**
*
The archive attribute specifies a comma-seperated archive list.

Retrieve the archive attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-14476360
*/
//> public void HTMLAppletElement03()
HTMLAppletElement03: function() {
var nodeList;
var testNode;
var varchive;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
varchive = testNode.archive;

assertEquals("",varchive);

},

/**
*
The code attribute specifies the applet class file.

Retrieve the code attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-61509645
*/
//> public void HTMLAppletElement04()
HTMLAppletElement04: function() {
var nodeList;
var testNode;
var vcode;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vcode = testNode.code;

assertEquals("org/w3c/domts/DOMTSApplet.class",vcode);

},

/**
*
The codeBase attribute specifies an optional base URI for the applet.

Retrieve the codeBase attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-6581160
*/
//> public void HTMLAppletElement05()
HTMLAppletElement05: function() {
var nodeList;
var testNode;
var vcodebase;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vcodebase = testNode.codeBase;

assertEquals("applets",vcodebase);

},

/**
*
The height attribute overrides the height.

Retrieve the height attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-90184867
*/
//> public void HTMLAppletElement06()
HTMLAppletElement06: function() {
var nodeList;
var testNode;
var vheight;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vheight = testNode.height;

assertEquals("306",vheight);

},

/**
*
The hspace attribute specifies the horizontal space to the left
and right of this image, applet, or object.

Retrieve the hspace attribute and examine it's value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/DOM-Level-2-HTML/html#ID-1567197
*/
//> public void HTMLAppletElement07()
HTMLAppletElement07: function() {
var nodeList;
var testNode;
var vhspace;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vhspace = testNode.hspace;

assertEquals(0,vhspace);

},

/**
*
The name attribute specifies the name of the applet.

Retrieve the name attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-39843695
*/
//> public void HTMLAppletElement08()
HTMLAppletElement08: function() {
var nodeList;
var testNode;
var vname;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vname = testNode.name;

assertEquals("applet1",vname);

},

/**
*
The vspace attribute specifies the vertical space above and below
this image, applet or object.

Retrieve the vspace attribute and examine it's value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/DOM-Level-2-HTML/html#ID-22637173
*/
//> public void HTMLAppletElement09()
HTMLAppletElement09: function() {
var nodeList;
var testNode;
var vvspace;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vvspace = testNode.vspace;

assertEquals(0,vvspace);

},

/**
*
The width attribute overrides the regular width.

Retrieve the width attribute and examine its value.

* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-16526327
*/
//> public void HTMLAppletElement10()
HTMLAppletElement10: function() {
var nodeList;
var testNode;
var vwidth;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vwidth = testNode.width;

assertEquals("301",vwidth);

},

/**
*
The object attribute specifies the serialized applet file.

Retrieve the object attribute and examine its value.

* @author NIST
* @author Rick Rivello
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-html#ID-93681523
*/
//> public void HTMLAppletElement11()
HTMLAppletElement11: function() {
var nodeList;
var testNode;
var vobject;
var doc = window.document;
var assertEquals = null;
nodeList = doc.getElementsByTagName("applet");
assertEquals(1,nodeList.length);
testNode = nodeList.item(0);
vobject = testNode.object;

assertEquals("DOMTSApplet.dat",vobject);  //vobject == ''

}

})
.endType();
