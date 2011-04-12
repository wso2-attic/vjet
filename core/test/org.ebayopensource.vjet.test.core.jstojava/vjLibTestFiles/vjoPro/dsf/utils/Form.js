vjo.ctype("vjoPro.dsf.utils.Form")
.needs("vjoPro.dsf.document.Form","")
.props({
sNVPAIR : "NVPAIR",
sJSON : "JSON",

/**
* Retrieve the data from a form to a name-value pair string. The values
* are combined with '&' in the string.
*
* @param {String} formId
*        a string id of the form
* @param {String}
*        the string contains with all values of the form, which is combined
*        with '&'
*/
//> public String getDataAsNVPairStr(String);
getDataAsNVPairStr : function(psFormId) {
return this.getData(psFormId,this.sNVPAIR);
},

/**
* This function does not yet implemented. Keep it as the 'private'.
*/
//> public String getDataAsJSONStr(String);
getDataAsJSONStr : function(psFormId) {
alert('getDataAsJSONStr not yet implemented');
//return this.getData(psFormId,this.sJSON);
return 'getDataAsJSONStr not yet implemented';
},

//> private String getData(String,String);
getData : function(psFormId, psPayloadType)
{
var F = vjoPro.dsf.document.Form,f = F.get(psFormId),es,e,d="",t,v,n;
if(f) {
es = f.elements;
for(var i=0;i<es.length;i++){
e = es[i];
t = e.type.toLowerCase();
v = e.value;
//v = encodeURIComponent(v);
if(((t=="radio"||t=="checkbox") && !e.checked)||t=="button"){
}else {
n = e.name||e.id;
if(t=="select-multiple"){
for(var j=0;j<e.options.length;j++){
if(e.options[j].selected){
if(d) d += "&";
d += n + "=" + e.options[j].value;
}
}
} else {
if(d) d += "&";
d += n + "=" + v;
}
}
}
}
return d;
}
})
.endType();
