vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.vjlib.FormSuppressSubmission")
.needs('vjoPro.dsf.Element')
.props({
//> public boolean validateAndSubmitForm(String psChkBoxId)
validateAndSubmitForm:function(psChkBoxId){
var chk = this.vj$.Element.get(psChkBoxId).checked;
return chk;
}

}).endType();
