vjo.ctype('vjoPro.samples.forms.FormEx3') //< public
.needs('vjoPro.dsf.document.Element')
.props({
/**
* @return boolean
* @access public
* @param {String} psId
*
*/
//> public boolean focusTextbox(String psId)
focusTextbox:function(psId){
vjoPro.dsf.document.Element.get(psId).focus();
return true;
}

})
.endType();
