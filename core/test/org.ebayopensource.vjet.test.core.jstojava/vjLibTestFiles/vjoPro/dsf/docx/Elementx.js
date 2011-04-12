vjo.ctype("vjoPro.dsf.docx.Elementx")
.props({
/**
* Gets the DOM element object with a given id. Similar to
* <code>document.getElementById</code>.
*
* @param {String} id
*        A string id of the DOM element
* @return {HTMLElement}
*        The DOM element with the given id
*/
//> public HTMLElement get(String);
get : function(psId) {
var d = document, e = null;
if (typeof(d.getElementById) != "undefined")
e = d.getElementById(psId);
else if (!e && d.all)
e = d.all[psId];
return e;
},

/**
* Creates a DOM element with the given name.
*
* @param {String} name
*        A String type name of the created element
* @return {HTMLElement}
*        Newly created DOM element
*/
//> public HTMLElement createElement(String);
createElement : function(name) {
return document.createAttribute?document.createAttribute(name):document.createElement(name);
},

/**
* Checks whether a DOM element is in the specified container.
*
* @param {HTMLElement} container
*        A DOM element as the container
* @param {HTMLElement} element
*        The DOM element to be checked
* @return {boolean}
*        True if the element's parent is the specified container.
*/
//> public boolean createElement(HTMLElement, HTMLElement);
containsElement : function(container,element) {
while ((element != null) && (element != container) && (element.parentNode != null)) { element = element.parentNode; }
return (element == container);
},

/**
* Returns a DOM elements with the specified tag name and class style. If
* multiple elements are matched, returns the first element. If no such
* element is found, returns <code>null</code>.
*
* @param {HTMLElement} element
*        An element from where the search should start. Only the descendants
*        of this element are included in the search, but not the element
*        itself
* @param {String} tagName
*        A tag name to be searched
* @param {String} className
*        A class name the element to be applied
* @return {HTMLElement}
*        The first matched element. if nothing is found, returns
*        <code>null</code>
* @see   #getElementsByTagClass
*/
//> public HTMLElement getElementByTagClass(HTMLElement, String, String);
getElementByTagClass : function(element,tag,name) {
var tags = element.getElementsByTagName(tag);
for (var ndx = 0;((ndx < tags.length) && (tags[ndx].className.match(name) == null));ndx++);
return (ndx < tags.length)?tags[ndx]:null;
},

/**
* Returns a collection of DOM elements with the specified tag name and class
* style. If no such element are found, returns an empty collection.
*
* @param {HTMLElement} element
*        An element from where the search should start. Only the descendants
*        of this element are included in the search, but not the element
*        itself
* @param {String} tagName
*        A tag name to be searched
* @param {String} className
*        A class name the element to be applied
* @return {Array}
*        The array containing all found object elements. If nothing is
*        found, returns an empty array.
* @see   #getElementByTagClass
*/
//> public Array getElementsByTagClass(HTMLElement, String, String);
getElementsByTagClass : function(element,tag,name) {
var elements = [];
var tags = element.getElementsByTagName(tag);
for (var ndx = 0;(ndx < tags.length);ndx++) {
if (tags[ndx].className.match(name)) elements.push(tags[ndx]);
}
return elements;
},
/**
* A helper function used by other library classes for getting the reference to an HTML element.
* If a string id is passed, an element with the id is returned.
* If an element is passed, the element itself is returned.
* Not really for public use.  the get() method is sufficient for most general usages
*
* @param {String or HTMLElement} ref
*        Either a String or HTMLElement
* @return {HTMLeElement}
* 	  The element with the id if ref is of String type.  otherwise, the ref itself is returned
*
*/
//> public HTMLElement get(String);
//> public HTMLElement get(HTMLElement);
getx : function(ref) {
var e = ref;
if(typeof(ref)=="string"){
e = this.get(ref);
}
return e;
}

})
.endType();
