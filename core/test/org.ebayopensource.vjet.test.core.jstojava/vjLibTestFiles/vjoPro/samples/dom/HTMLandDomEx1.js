vjo.ctype('vjoPro.samples.dom.HTMLandDomEx1') //< public
.needs('vjoPro.dsf.document.Element')
.props({
/**
* @return boolean
* @access public
* @param {String} psId
*
*/
//> public boolean getElem(String psId)
getElem:function(psId){
var elem = vjoPro.dsf.document.Element.get(psId);
alert(elem);
}

})
.endType();
