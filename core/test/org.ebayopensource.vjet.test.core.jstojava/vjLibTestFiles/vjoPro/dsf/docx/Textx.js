vjo.ctype("vjoPro.dsf.docx.Textx")
.needs("vjoPro.dsf.docx.Elementx")
.props({
/**
* Clears the content in specified textarea.
*
* @param {String} id
*        the id of the textarea to be cleared
*/
//> public void autoClear(String);
autoClear : function(psId) {
var o = vjoPro.dsf.document.Element.get(psId);
if(o)
{
if(o.defaultValue == o.value)
{
o.value = "";
}
}
}
})
.endType();


