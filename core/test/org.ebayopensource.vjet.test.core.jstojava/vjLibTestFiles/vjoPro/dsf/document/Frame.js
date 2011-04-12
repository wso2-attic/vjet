vjo.ctype("vjoPro.dsf.document.Frame")
.needs(["vjoPro.dsf.Element",
"vjoPro.dsf.client.Browser"])
.props({
/**
* Resizes iframe's size.
*
* @param {String} iframeId
*        the id of the iframe to be resized
*/
//> public void resize(String);
resize : function(psIframeId) {
var E = this.vj$.Browser; //<Type::Browser
var f = E.get(psIframeId);//<HTMLFormElement
if(f && typeof(f.document)!="unknown")
{
var oDoc=f.document ? f.document : f.contentDocument;//<HTMLDocument
var db = oDoc.body,
es = f.style,
c = vjoPro.dsf.client.Browser,
w = "100%",
h = db.offsetHeight,
oh;
if(c.bSafari)
{
oh = db.offsetHeight;
w = oDoc.width;
h = document.doctype !==null ? oDoc.height+15 : oDoc.height+1;

}
else if (c.bFirefox)
{
w =	oDoc.width;
h = oDoc.height;
}
else if(c.bWin || c.bOpera )
{
w = db.scrollWidth;
h = c.bNav && document.doctype !==null ? db.scrollHeight+30 : db.scrollHeight;
}
//es.width = w+"px";
es.height = h+"px";
}
}
})
.endType();

