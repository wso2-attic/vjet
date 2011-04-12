vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.vjlib.FormSuppressSubmission")
.needs("vjoPro.dsf.document.Element")
.props({

//>public boolean validateAndSubmitForm(String psChkBoxId)
validateAndSubmitForm:function(psChkBoxId){
var chk = this.vj$.Element.get(psChkBoxId).checked;//<boolean
return !chk;
}

})
.endType();
