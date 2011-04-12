vjo.ctype("vjoPro.dsf.utils.Css")
.needs("vjoPro.dsf.Element")
.props({
//> public Object apply(String, String);
apply : function(psElementId, psCssText) {
var e = vjoPro.dsf.Element.get(psElementId), c;
if(e && psCssText) {
c = this.createStyle(psCssText);
if(c){
e.appendChild(c);
}
}
return c;
},
//> public Object createStyle(String);
createStyle :function(psCssText){
var c = document.createElement('style'), t;
c.type =  "text/css";
if(psCssText) {
if (c.styleSheet) {
c.styleSheet.cssText = psCssText;
} else {
t = document.createTextNode(psCssText);
c.appendChild(t);
}
}
return c;
}
})
.endType();
