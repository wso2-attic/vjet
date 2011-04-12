vjo.ctype('vjoPro.samples.forms.FormEx6') //< public
.needs('vjoPro.dsf.document.Element')
.props({
/**
* @return boolean
* @access public
* @param {String} psChkBoxId
*
*/
//> public boolean validateAndSubmitForm(String psChkBoxId)
validateAndSubmitForm:function(psChkBoxId){
var chk = vjoPro.dsf.document.Element.get(psChkBoxId).checked;//<boolean
return chk;
}

})
.endType();
