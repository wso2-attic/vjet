vjo.ctype("dsf.dom.XMLDomTests")
.props({
//>public void main(String[] args)
main : function(args) {
var tests = new this.vj$.XMLDomTests();
tests.runTests();

}
})
.protos({



//>public constructs()
constructs : function() {

//this.xmldoc = xmlhttp.responseXML;

},

/**
Create a new Document object. If no arguments are specified,
the document will be empty. If a root tag is specified, the document
will contain that single root tag. If the root tag has a namespace
prefix, the second argument must specify the URL that identifies the
namespace.
*/
//private Document newDocument(String,String)
newDocument : function(rootTagName, namespaceURL) {
if (!rootTagName) rootTagName = "";
if (!namespaceURL) namespaceURL = "";

if (document.implementation && document.implementation.createDocument) {
// This is the W3C standard way to do it
return document.implementation.createDocument(namespaceURL,
rootTagName, null);
}
else { // This is the IE way to do it
// Create an empty document as an ActiveX object
// If there is no root element, this is all we have to do
var doc = new ActiveXObject("MSXML2.DOMDocument");

// If there is a root tag, initialize the document
if (rootTagName) {
// Look for a namespace prefix
var prefix = "";
var tagname = rootTagName;
var p = rootTagName.indexOf(':');
if (p != -1) {
prefix = rootTagName.substring(0, p);
tagname = rootTagName.substring(p+1);
}

// If we have a namespace, we must have a namespace prefix
// If we don't have a namespace, we discard any prefix
if (namespaceURL) {
if (!prefix) prefix = "a0"; // What Firefox uses
}
else prefix = "";

// Create the root element (with optional namespace) as a
// string of text
var text = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
"<g:foo foo=\"bar\" g:foo=\"g-bar\" g:foofoo=\"barbar\" bar=\"foo\" xml:lang=\"en\""+
"xmlns=\"http://www.w3.org/1999/xhtml\\"+
"xmlns:g=\"http://www.grauw.nl/g\">"+
"<g:bar id=\"foobar\">"+
"<div/><p>♦</p>"+
"</g:bar>"+
"<![CDATA[ ]]>&amp;&#64;&#x23;<?g ?><!-- -->"+
"</g:foo>";
// And parse that text into the empty document
doc.loadXML(text);
}
return doc;
}
},

getData:function(){
return testsuite = {
name: 'Your browser',
tests: [
{
id: 'bytagname_div',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div').length;
},
expected: '1'
},
{
id: 'bytagname_DIV',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('DIV').length;
},
expected: '0'
},
{
id: 'bytagname_gbar',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('g:bar').length;
},
expected: '1'
},
{
id: 'bytagname_bar',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('bar').length;
},
expected: '0'
},
{
id: 'bytagnamens_gbar',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.getElementsByTagNameNS(NS_G,'bar').length;
},
expected: '1'
},
{
id: 'bytagname_star',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('*').length;
},
expected: '4'
},
{
id: 'bytagnamens_gstar',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.getElementsByTagNameNS(NS_G,'*').length;
},
expected: '2'
},
{
id: 'bytagnamens_starbar',
test: function(xmldoc) {
return xmldoc.getElementsByTagNameNS('*','bar').length;
},
expected: '1'
},
{
id: 'nselement_nodename',
test: function(xmldoc) {
return xmldoc.documentElement.nodeName;
},
expected: 'g:foo'
},
{
id: 'nselement_prefix',
test: function(xmldoc) {
return xmldoc.documentElement.prefix;
},
expected: 'g'
},
{
id: 'nselement_namespace',
test: function(xmldoc) {
return xmldoc.documentElement.namespaceURI;
},
expected: 'http://www.grauw.nl/g'
},
{
id: 'nselement_localname',
test: function(xmldoc) {
return xmldoc.documentElement.localName;
},
expected: 'foo'
},
{
id: 'element_nodename',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].nodeName;
},
expected: 'div'
},
{
id: 'element_prefix',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].prefix;
},
expected: '(null)'
},
{
id: 'element_nsuri',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].namespaceURI;
},
expected: 'http://www.w3.org/1999/xhtml'
},
{
id: 'element_localname',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].localName;
},
expected: 'div'
},
{
id: 'hasattributes',
test: function(xmldoc) {
return xmldoc.documentElement.hasAttributes();
},
expected: 'true'
},
{
id: 'getattr_foo',
test: function(xmldoc) {
return xmldoc.documentElement.getAttribute('foo');
},
expected: 'bar'
},
{
id: 'getattr_gfoo',
test: function(xmldoc) {
return xmldoc.documentElement.getAttribute('g:foo');
},
expected: 'g-bar'
},
{
id: 'getattr_gfoofoo',
test: function(xmldoc) {
return xmldoc.documentElement.getAttribute('g:foofoo');
},
expected: 'barbar'
},
{
id: 'getattr_xmllang',
test: function(xmldoc) {
return xmldoc.documentElement.getAttribute('xml:lang');
},
expected: 'en'
},
{
id: 'getattrns_gfoo',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.documentElement.getAttributeNS(NS_G,'foo');
},
expected: 'g-bar'
},
{
id: 'getattrns_gfoofoo',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.documentElement.getAttributeNS(NS_G,'foofoo');
},
expected: 'barbar'
},
{
id: 'getattrns_xmlfoo',
test: function(xmldoc) {
var NS_XML = null;
return xmldoc.documentElement.getAttributeNS(NS_XML,'lang');
},
expected: 'en'
},
{
id: 'getattrnode_foo',
test: function(xmldoc) {
return xmldoc.documentElement.getAttributeNode('foo').nodeValue;
},
expected: 'bar'
},
{
id: 'getattrnodens_gfoo',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.documentElement.getAttributeNodeNS(NS_G,'foo').nodeValue;
},
expected: 'g-bar'
},
{
id: 'attrs_foo',
test: function(xmldoc) {
return xmldoc.documentElement.attributes['foo'].nodeValue;
}
},
{
id: 'attrs_gfoo',
test: function(xmldoc) {
return xmldoc.documentElement.attributes['g:foo'].nodeValue;
}
},
{
id: 'attrs_gfoofoo',
test: function(xmldoc) {
return xmldoc.documentElement.attributes['g:foofoo'].nodeValue;
}
},
{
id: 'attrs_xmllang',
test: function(xmldoc) {
return xmldoc.documentElement.attributes['xml:lang'].nodeValue;
}
},
{
id: 'attrs_gni_foo',
test: function(xmldoc) {
return xmldoc.documentElement.attributes.getNamedItem('foo').nodeValue;
},
expected: 'bar'
},
{
id: 'attrs_gni_gfoo',
test: function(xmldoc) {
return xmldoc.documentElement.attributes.getNamedItem('g:foo').nodeValue;
},
expected: 'g-bar'
},
{
id: 'attrs_gni_gfoofoo',
test: function(xmldoc) {
return xmldoc.documentElement.attributes.getNamedItem('g:foofoo').nodeValue;
},
expected: 'barbar'
},
{
id: 'attrs_gni_xmllang',
test: function(xmldoc) {
return xmldoc.documentElement.attributes.getNamedItem('xml:lang').nodeValue;
},
expected: 'en'
},
{
id: 'attrs_gnins_nullfoo',
test: function(xmldoc) {
return xmldoc.documentElement.attributes.getNamedItemNS(null,'foo').nodeValue;
},
expected: 'bar'
},
{
id: 'attrs_gnins_aafoo',
test: function(xmldoc) {
return xmldoc.documentElement.attributes.getNamedItemNS('','foo').nodeValue;
},
expected: "(null)"
},
{
id: 'attrs_gnins_gfoo',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.documentElement.attributes.getNamedItemNS(NS_G,'foo').nodeValue;
},
expected: 'g-bar'
},
{
id: 'attrs_gnins_gfoofoo',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.documentElement.attributes.getNamedItemNS(NS_G,'foofoo').nodeValue;
},
expected: 'barbar'
},
{
id: 'attrs_gnins_xmllang',
test: function(xmldoc) {
var NS_XML = null;
return xmldoc.documentElement.attributes.getNamedItemNS(NS_XML,'lang').nodeValue;
},
expected: 'en'
},
{
id: 'hasattr_bar',
test: function(xmldoc) {
return xmldoc.documentElement.hasAttribute('bar');
},
expected: 'true'
},
{
id: 'hasattrns_gbar',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.documentElement.hasAttributeNS(NS_G,'bar');
},
expected: 'false'
},
{
id: 'getattr_foofoo',
test: function(xmldoc) {
return xmldoc.documentElement.getAttribute('foofoo');
},
expected: "('')"
},
{
id: 'getattrns_gbar',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.documentElement.getAttributeNS(NS_G,'bar');
},
expected: "('')"
},
{
id: 'attr_length',
test: function(xmldoc) {
return xmldoc.documentElement.attributes.length;
},
expected: '7'
},
{
id: 'attrnames',
test: function(xmldoc) {
var atts = xmldoc.documentElement.attributes, names = [];
for (var i=0; i<atts.length; i++) names.push(atts.item(i).nodeName);
return names.join(',');
},
expected: 'foo,g:foo,g:foofoo,bar,xml:lang,xmlns,xmlns:g'
},
{
id: 'attr0_name',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[0].nodeName;
},
expected: 'foo'
},
{
id: 'attr0_prefix',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[0].prefix;
},
expected: '(null)'
},
{
id: 'attr0_nsuri',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[0].namespaceURI;
},
expected: '(null)'
},
{
id: 'attr0_localname',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[0].localName;
},
expected: 'foo'
},
{
id: 'attr1_name',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[1].nodeName;
},
expected: 'g:foo'
},
{
id: 'attr1_prefix',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[1].prefix;
},
expected: 'g'
},
{
id: 'attr1_nsuri',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[1].namespaceURI;
},
expected: 'http://www.grauw.nl/g'
},
{
id: 'attr1_localname',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[1].localName;
},
expected: 'foo'
},
{
id: 'attr4_name',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[4].nodeName;
},
expected: 'xml:lang'
},
{
id: 'attr4_prefix',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[4].prefix;
},
expected: 'xml'
},
{
id: 'attr4_nsuri',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[4].namespaceURI;
},
expected: 'http://www.w3.org/XML/1998/namespace'
},
{
id: 'attr4_localname',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[4].localName;
},
expected: 'lang'
},
{
id: 'attr5_name',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[5].nodeName;
},
expected: 'xmlns'
},
{
id: 'attr5_prefix',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[5].prefix;
},
expected: '(null)'
},
{
id: 'attr5_nsuri',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[5].namespaceURI;
},
expected: 'http://www.w3.org/2000/xmlns/'
},
{
id: 'attr5_localname',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[5].localName;
},
expected: 'xmlns'
},
{
id: 'attr6_name',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[6].nodeName;
},
expected: 'xmlns:g'
},
{
id: 'attr6_prefix',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[6].prefix;
},
expected: 'xmlns'
},
{
id: 'attr6_nsuri',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[6].namespaceURI;
},
expected: 'http://www.w3.org/2000/xmlns/'
},
{
id: 'attr6_localname',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[6].localName;
},
expected: 'g'
},
{
id: 'haschildnodes',
test: function(xmldoc) {
return xmldoc.documentElement.hasChildNodes();
},
expected: 'true'
},
{
id: 'childnodeslength',
test: function(xmldoc) {
return xmldoc.documentElement.childNodes.length;
},
expected: '8'
},
{
id: 'childnode1_name',
test: function(xmldoc) {
return xmldoc.documentElement.childNodes[1].nodeName;
},
expected: 'g:bar'
},
{
id: 'childnode1_type',
test: function(xmldoc) {
return xmldoc.documentElement.childNodes[0].nodeType;
},
expected: '3'
},
{
id: 'childnodes_types',
test: function(xmldoc) {
var nodes = xmldoc.documentElement.childNodes, types = [];
for (var i=0; i<nodes.length; i++) types.push(nodes.item(i).nodeType);
return types.join(',');
},
expected: '3,1,3,4,3,7,8,3'
},
{
id: 'childnode2_value',
test: function(xmldoc) {
return encodeURIComponent(xmldoc.documentElement.childNodes[2].nodeValue);
},
expected: '%0A%09'
},
{
id: 'childnode3_value',
test: function(xmldoc) {
return encodeURIComponent(xmldoc.documentElement.childNodes[3].nodeValue);
},
expected: '%20'
},
{
id: 'div_nextsibling',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].nextSibling.nodeName;
},
expected: 'p'
},
{
id: 'div_ownerdoc',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].ownerDocument;
},
expected: ['[object XMLDocument]','[object Document]']
},
{
id: 'div_ownerdoc_rootelm',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].ownerDocument.documentElement.nodeName;
},
expected: 'g:foo'
},
{
id: 'div_parentname',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].parentNode.nodeName;
},
expected: 'g:bar'
},
{
id: 'innerhtml',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('div')[0].parentNode.innerHTML;
}
},
{
id: 'doc_textcontent',
test: function(xmldoc) {
return xmldoc.textContent;
},
expected: '(null)'
},
{
id: 'elm_tagname',
test: function(xmldoc) {
return xmldoc.documentElement.tagName;
},
expected: 'g:foo'
},
{
id: 'attr_tagname',
test: function(xmldoc) {
return xmldoc.documentElement.attributes[1].tagName;
},
expected: '(null)'
},
{
id: 'createattr',
test: function(xmldoc) {
return xmldoc.createAttribute('bla').nodeName;
},
expected: 'bla'
},
{
id: 'createattr_nsuri',
test: function(xmldoc) {
return xmldoc.createAttribute('bla').namespaceURI;
},
expected: '(null)'
},
{
id: 'createattrns',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.createAttributeNS(NS_G, 'bla').nodeName;
},
expected: 'bla'
},
{
id: 'createattrns_nsuri',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.createAttributeNS(NS_G, 'bla').namespaceURI;
},
expected: 'http://www.grauw.nl/g'
},
{
id: 'now_modify',
test: function(xmldoc) {
return 'From here on, these modify the original document.';
}
},
{
id: 'setattr_nsuri',
test: function(xmldoc) {
xmldoc.documentElement.setAttribute('g:bla', 'test');
return xmldoc.documentElement.getAttributeNode('g:bla').namespaceURI;
},
expected: '(null)'
},
{
id: 'nr_of_p_elms',
test: function(xmldoc) {
return xmldoc.getElementsByTagName('p').length;
},
expected: '1'
},
{
id: 'p_elms_replaced',
test: function(xmldoc) {
var p = xmldoc.getElementsByTagName('p')[0];
var newp = xmldoc.createElement('p');
p.parentNode.replaceChild(newp, p);
return xmldoc.getElementsByTagName('p').length;
},
expected: '1'
},
{
id: 'p_elms_removed',
test: function(xmldoc) {
var p = xmldoc.getElementsByTagName('p')[0];
p.parentNode.removeChild(p);
return xmldoc.getElementsByTagName('p').length;
},
expected: '0'
},
{
id: 'get_attr_foo',
test: function(xmldoc) {
return xmldoc.documentElement.getAttribute('foo');
},
expected: 'bar'
},
{
id: 'remove_attr_foo',
test: function(xmldoc) {
xmldoc.documentElement.removeAttribute('foo');
return xmldoc.documentElement.getAttribute('foo');
},
expected: "('')"
},
{
id: 'get_attrnode_gfoo',
test: function(xmldoc) {
var NS_G = null;
return xmldoc.documentElement.getAttributeNodeNS(NS_G,'foo');
},
expected: "[object Attr]"
},
{
id: 'remove_attrnode_gfoo',
test: function(xmldoc) {
var NS_G = null;
var gfoo = xmldoc.documentElement.getAttributeNodeNS(NS_G,'foo');
xmldoc.documentElement.removeAttributeNode(gfoo);
return xmldoc.documentElement.getAttributeNodeNS(NS_G,'foo');
},
expected: "(null)"
},
{
id: 'replace_textcontent',
test: function(xmldoc) {
var elm = xmldoc.getElementsByTagName('div')[0].parentNode;
elm.textContent = 'replaced';
return elm.childNodes.length + ' - ' + elm.textContent;
},
expected: "1 - replaced"
},
{
id: 'done',
test: function(xmldoc) {
return 'Done.';
}
}
]
};


},

runTests:function(){
var xmldoc = document; //this.newDocument("g",null);


var testsuite = this.getData();
for (var i in testsuite.tests) {
vjo.sysout.println("num of tests  =" + i);
try {
var result = testsuite.tests[i].test(xmldoc);
if (result == null) result = '(null)';
if (result == '') result = "('')";
if (result == []) result = "([])";
if (result == {}) result = "({})";
result = result.toString();
} catch(e) {
result = 'Error ('+e.name+')';
testsuite.tests[i].error = e.name;
testsuite.tests[i].errormessage = e.message;
vjo.sysout.println("ERROR ID: "+ testsuite.tests[i].id +" result:"  + result + " e.message = " + e.message);
}
testsuite.tests[i].result = result;


}
}




})
.endType();
