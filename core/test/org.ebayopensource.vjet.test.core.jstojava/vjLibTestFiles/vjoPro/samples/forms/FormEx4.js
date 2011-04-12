vjo.ctype('vjoPro.samples.forms.FormEx4') //< public
.needs('vjoPro.dsf.document.Element')
.props({
/**
* @return boolean
* @access public
* @param {String} psId
*
*/
//> public boolean selectTextbox(String psId)
selectTextbox:function(psId){
vjoPro.dsf.document.Element.get(psId).select();
return true;
}

})
.endType();
