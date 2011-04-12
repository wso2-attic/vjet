vjo.ctype('vjoPro.samples.forms.FormEx2') //< public
.needs('vjoPro.dsf.document.Text')
.props({
/**
* @return boolean
* @access public
* @param {String} psId
*
*/
//> public boolean clearTextbox(String psId)
clearTextbox:function(psId){
vjoPro.dsf.document.Text.autoClear(psId);
return true;
}

})
.endType();
