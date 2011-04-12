vjo.ctype("vjoPro.dsf.utils.Html")
.needs("vjoPro.dsf.typeextensions.string.Comparison")
.props({
/**
* Encodes the HTML source to raw string. This will convert the "&lt;"/"&gt;"
* to "\&lt;"/"\&gt;".
*
* @param {String} str
*        the HTML source
* @return {String}
*        the converted raw string
*/
//> public String encode(String);
encode : function(psStr) {
var v = new String();
if (v.hasAny("<", ">"))
{
var re = new RegExp("<", "gi");
v = v.replace(re, "&lt;");
re = new RegExp(">", "gi");
v = v.replace(re, "&gt;");
}
return v;
},

/**
* Strips all html tags in a HTML source.
*
* @param {String} str
*        The HTML source
* @return {String}
*        The string without HTML tags
*/
//> public String strip(String);
strip : function(psStr) {
//function to strip all html tags in a string
var s = psStr.replace(/(<([^>]+)>)/ig,"");
//replace carriage returns and line feeds
s = s.replace(/\r\n/g," ");
s = s.replace(/\n/g," ");
s = s.replace(/\r/g," ");
return s;
}
})
.endType();

