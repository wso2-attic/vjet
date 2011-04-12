vjo.ctype('vjoPro.samples.forms.FormEx5') //< public
.needs('vjoPro.dsf.document.Select')
.props({
/**
* @return boolean
* @access public
* @param {String} psId
* @param {String} psValId
*
*/
//> public boolean addToSelect(String psId,String psValId)
addToSelect:function(psId, psValId){
var e = vjoPro.dsf.document.Element.get(psId);
var sVal = vjoPro.dsf.document.Element.get(psValId).value;
vjoPro.dsf.document.Select.addOption(e, sVal, sVal);
return true;
}

})
.endType();
