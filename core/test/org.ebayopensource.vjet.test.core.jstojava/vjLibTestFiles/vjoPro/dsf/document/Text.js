vjo.ctype("vjoPro.dsf.document.Text")
.needs("vjoPro.dsf.Element")
.props({
/**
* Clears the content in specified textarea.
*
* @param {String} id
*        the id of the textarea to be cleared
*/
//> public void autoClear(String);
autoClear : function(psId) {
var o = vjoPro.dsf.Element.get(psId);
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


