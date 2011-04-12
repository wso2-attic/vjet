vjo.ctype("vjoPro.dsf.utils.xml.XmlHelper")
.props({
getValue : function(pXml, pXPath)
{
var aX = pXPath.split('/');
var nd = this.getNodeByXpath(pXml,pXPath), rv;
rv = nd?this.getNodeValue(nd):'';
return rv;
},

getNode : function(pXml, pName, pIndex)
{
var nodes = pXml.getElementsByTagName(pName);
if (nodes)
{
if (pIndex >= 0)
{
if (nodes.length >= pIndex)
{
return nodes[pIndex];
}
}
else
{
if (nodes[0])
{
return nodes[0];
}
}
}
return null;
},

getNodes : function(pXml, pName)
{
return pXml.getElementsByTagName(pName);
},

getNodeByXpath : function(pXml, pXPath)
{
var aX = pXPath.split('/');
var nd = null,idx,nm = '';
for(var i = 0;i < aX.length;i++)
{
idx = 0;nm = aX[i];
if (nm.indexOf(']') == nm.length-1)
{
idx = nm.substr(nm.indexOf('[')+1, nm.indexOf(']') - nm.indexOf('[')-1);
nm = nm.substr(0,nm.indexOf('['));
}
nd = this.getNode((nd == null)?pXml:nd, nm, parseInt(idx));

if (nd == null)
{
break;
}
}
return nd;
},

getNodeValue : function(pNode)
{
var rv,pn = pNode;
if (pn.childNodes.length > 1) {
rv = pn.childNodes[1].nodeValue;
}
else
{
rv = pn.firstChild?pn.firstChild.nodeValue:null;
}
return rv;
},

getAttribValue : function(pXml, pXPath, pId)
{
var nd = this.getNodeByXpath(pXml, pXPath);
if (nd)
{
var aA = nd.attributes;
for (var i = 0; i < aA.length; i++)
{
if (aA[i].name == pId)
{
return aA[i].value;
}
}
}
return "";
}

})
.endType();
