vjo.ctype("dsf.dom.W3cDomLevel1CoreTests")
.protos({

/**
*  The "getDoctype()" method returns null for XML documents
*  without a document type declaration.
* Retrieve the XML document without a DTD and invoke the
* "getDoctype()" method.  It should return null.
* @author NIST
* @author Mary Brady
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-B63ED1A31
*/
//>public void documentgetdoctypenodtd()
documentgetdoctypenodtd : function() {
var doc = window.document;
var docType = doc.doctype;
var assertEquals = null;
assertEquals(null,docType);

},

/**
* Creating an entity reference with an empty name should cause an INVALID_CHARACTER_ERR.
*
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-258A00AF')/constant[@name='INVALID_CHARACTER_ERR'])
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-392B75AE
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#xpointer(id('ID-392B75AE')/raises/exception[@name='DOMException']/descr/p[substring-before(.,':')='INVALID_CHARACTER_ERR'])
* @see http://www.w3.org/Bugs/Public/show_bug.cgi?id=525
*/
//>public void documentgetdoctypenodtd()
documentinvalidcharacterexceptioncreateentref1 : function() {
var doc = window.document;
var badEntityRef;

success = false;
try {
badEntityRef = doc.createEntityReference("");
}
catch(ex) {
var e = ex;
var code = ex.code;
success = (typeof(ex.code) != 'undefined' && ex.code == 5);
}
var assertTrue = null;
assertTrue(success);
},

/**
* Appends a text node to an attribute and checks if the value of
* the attribute is changed.
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107
*/
//>public void hc_attrappendchild1()
hc_attrappendchild1 : function() {
var acronymList;
var testNode;
var attributes;
var titleAttr;
var value;
var textNode;
var retval;
var lastChild;
var doc = window.document;

acronymList = doc.getElementsByTagName("acronym");
testNode = acronymList.item(3);
attributes = testNode.attributes;

titleAttr = attributes.getNamedItem("title");
textNode = doc.createTextNode("terday");
retval = titleAttr.appendChild(textNode);
value = titleAttr.value;
var assertEquals = null;
assertEquals("Yesterday",value);
value = titleAttr.nodeValue;

assertEquals("Yesterday",value);
value = retval.nodeValue;

assertEquals("terday",value);
lastChild = titleAttr.lastChild;

value = lastChild.nodeValue;

assertEquals("terday",value);
},

/**
* Attempts to append an element to the child nodes of an attribute.
*
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-184E7107
*/
//>public void hc_attrappendchild2()
hc_attrappendchild2 : function() {
var doc = window.document;
var success;
var acronymList;
var testNode;
var attributes;
var titleAttr;
var value;
var newChild;
var retval;
var lastChild;

acronymList = doc.getElementsByTagName("acronym");
testNode = acronymList.item(3);
attributes = testNode.attributes;

titleAttr = attributes.getNamedItem("title");
newChild = doc.createElement("terday");

success = false;
try {
retval = titleAttr.appendChild(newChild);
}
catch(ex) {
success = (typeof(ex.code) != 'undefined' && ex.code == 3);
}
var assertTrue = null;
assertTrue(success);
},

/**
* Checks that Node.childNodes for an attribute node contains the expected text node.
*
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-637646024
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1451460987
*/
//>public void hc_attrchildnodes1()
hc_attrchildnodes1 : function() {
var doc = window.document;
var acronymList;
var testNode;
var attributes;
var titleAttr;
var value;
var textNode;
var childNodes;

acronymList = doc.getElementsByTagName("acronym");
testNode = acronymList.item(3);
attributes = testNode.attributes;

titleAttr = attributes.getNamedItem("title");
childNodes = titleAttr.childNodes;
var assertTrue = null;
var assertEquals = null;
assertTrue(childNodes.length == 1);

textNode = childNodes.item(0);
value = textNode.nodeValue;

assertEquals("Yes",value);

textNode = childNodes.item(1);
assertEquals(null,textNode);

},

/**
*  Retrieve the entire DOM document and invoke its
* "createAttribute(name)" method.  It should create a
* new Attribute node with the given name. The name, value
* and type of the newly created object are retrieved and
* output.
*
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1084891198
* @see http://www.w3.org/Bugs/Public/show_bug.cgi?id=236
* @see http://lists.w3.org/Archives/Public/www-dom-ts/2003Jun/0011.html
* @see http://www.w3.org/Bugs/Public/show_bug.cgi?id=243
*/
//>public void hc_documentcreateattribute()
hc_documentcreateattribute : function() {
var doc = window.document;
var newAttrNode;
var attrValue;
var attrName;
var attrType;

newAttrNode = doc.createAttribute("title");
attrValue = newAttrNode.nodeValue;
var assertEquals = null;
assertEquals("",attrValue);
attrName = newAttrNode.nodeName;

assertEquals("title",attrName);
attrType = newAttrNode.nodeType;

assertEquals(2,attrType);

},

/**
*
* The "createComment(data)" method creates a new Comment
* node given the specified string.
* Retrieve the entire DOM document and invoke its
* "createComment(data)" method.  It should create a new
* Comment node whose "data" is the specified string.
* The content, name and type are retrieved and output.
*
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-1334481328
*/
//>public void hc_documentcreatecomment()
hc_documentcreatecomment : function() {
var doc = window.document;
var newCommentNode;
var newCommentValue;
var newCommentName;
var newCommentType;

newCommentNode = doc.createComment("This is a new Comment node");
newCommentValue = newCommentNode.nodeValue;
var assertEquals = null;
assertEquals("This is a new Comment node",newCommentValue);
newCommentName = newCommentNode.nodeName;

assertEquals("#comment",newCommentName);
newCommentType = newCommentNode.nodeType;

assertEquals(8,newCommentType);

},

/**
*
* The "createDocumentFragment()" method creates an empty DocumentFragment object.
* Retrieve the entire DOM document and invoke its
* "createDocumentFragment()" method.  The content, name,
* type and value of the newly created object are output.
*
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-35CB04B5
*/
//>public void hc_documentcreatedocumentfragment()
hc_documentcreatedocumentfragment : function() {
var doc = window.document;
var newDocFragment;
var children;
var length;
var newDocFragmentName;
var newDocFragmentType;
var newDocFragmentValue;

newDocFragment = doc.createDocumentFragment();
children = newDocFragment.childNodes;

length = children.length;
var assertEquals = null;
assertEquals(0,length);
newDocFragmentName = newDocFragment.nodeName;

assertEquals("#document-fragment",newDocFragmentName);
newDocFragmentType = newDocFragment.nodeType;

assertEquals(11,newDocFragmentType);
newDocFragmentValue = newDocFragment.nodeValue;

assertEquals(null,newDocFragmentValue);

},

/**
* The "createElement(tagName)" method creates an Element of the type specified.
*  Retrieve the entire DOM document and invoke its
*  "createElement(tagName)" method with tagName="acronym".
*  The method should create an instance of an Element node
* whose tagName is "acronym".  The NodeName, NodeType
* and NodeValue are returned.
*
* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-2141741547
*/
//>public void hc_documentcreateelement()
hc_documentcreateelement : function() {
var doc = window.document;
var newElement;
var newElementName;
var newElementType;
var newElementValue;

newElement = doc.createElement("acronym");
newElementName = newElement.nodeName;
var assertEquals = null;
assertEquals("ACRONYM",newElementName);
newElementType = newElement.nodeType;

assertEquals(1,newElementType);
newElementValue = newElement.nodeValue;

assertEquals(null,newElementValue);

}


})
.endType();
