vjo.ctype("dsf.dom.html.level2.HasFeatureTests")
.protos({

/**
*
hasFeature("hTmL", null) should return true.

* @author Curt Arnold
* @see http://www.w3.org/TR/1998/REC-DOM-Level-1-19981001/level-one-core#ID-5CED94D7
*/
//> public void hasFeature01()
hasFeature01: function() {
var domImpl;
var version = null;

var state;
var assertTrue = null;
//domImpl = getImplementation();
domImpl = document.implementation;
state = domImpl.hasFeature("hTmL",version);
assertTrue(state);

},

/**
*
hasFeature("hTmL", "2.0") should return true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7
*/
//> public void hasFeature02()
hasFeature02: function() {
var domImpl;
var version = "2.0";
var state;
var assertTrue = assertTrue;

//domImpl = getImplementation();
domImpl = document.implementation;
state = domImpl.hasFeature("hTmL",version);
assertTrue(state);

},

/**
*
hasFeature("xhTmL", null) should return true if hasFeature("XML", null) returns true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7
*/
//> public void hasFeature03()
hasFeature03: function() {
var domImpl;
var version = null;

var state;
var hasXML;
var assertTrue = assertTrue;
var assertEquals = assertEquals;
//domImpl = getImplementation();
domImpl = document.implementation;
hasXML = domImpl.hasFeature("XML",version);
state = domImpl.hasFeature("xhTmL",version);
assertEquals(hasXML,state);

},

/**
*
hasFeature("xhTmL", "2.0") should return true if hasFeature("XML", "2.0") returns true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7
*/
//> public void hasFeature04()
hasFeature04: function() {
var domImpl;
var version = "2.0";
var state;
var hasXML;
var assertTrue = assertTrue;
//domImpl = getImplementation();
var assertEquals = assertEquals;
domImpl = document.implementation;
hasXML = domImpl.hasFeature("XML",version);
state = domImpl.hasFeature("xhTmL",version);
assertEquals(hasXML,state);

},

/**
*
hasFeature("cOrE", null) should return true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7
*/
//> public void hasFeature05()
hasFeature05: function() {
var domImpl;
var version = null;

var state;
var assertTrue = assertTrue;
//domImpl = getImplementation();
domImpl = document.implementation;
state = domImpl.hasFeature("cOrE",version);
assertTrue(state);

},

/**
*
hasFeature("cOrE", "2.0") should return true.

* @author Curt Arnold
* @see http://www.w3.org/TR/DOM-Level-2-Core/core#ID-5CED94D7
*/
//> public void hasFeature06()
hasFeature06: function() {
var domImpl;
var version = "2.0";
var state;
var assertTrue = assertTrue;
//domImpl = getImplementation();
domImpl = document.implementation;
state = domImpl.hasFeature("cOrE",version);
assertTrue(state);

}


})
.endType();
