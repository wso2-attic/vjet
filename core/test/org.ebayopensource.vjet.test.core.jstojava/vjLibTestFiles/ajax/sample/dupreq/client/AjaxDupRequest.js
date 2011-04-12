vjo.ctype('ajax.sample.dupreq.client.AjaxDupRequest') //< public
.needs('vjoPro.dsf.document.Element')
.needs("vjoPro.dsf.Message")
.protos({

model :null,//<Object
Element :null,//<Object

/**
* @JsJavaAccessToJs public
* @JsParamType poModel AjaxDupRequestModel
*/
//> public constructs(AjaxDupRequestModel poModel)
constructs : function(poModel) {
this.Element = this.vj$.Element;
this.model = poModel || {};
},
/**
* @JsJavaAccessToJs public
* @JsReturnType vjoPro.dsf.MessageJsr
* @JsParamType poEvt com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum
* @JsEventHandler
*/
//> public boolean onClick(Object poEvt)
onClick : function (poEvt) {
var text = this.Element.get(this.model.textBox);
if (text) {
var msg = new this.vj$.Message(this.model.serviceId);
msg.request = {};
msg.request.username = text.value;
msg.clientContext.svcApplier = this;
return msg;
}
return true;
},

/**
* @JsJavaAccessToJs public
* @JsReturnType void
* @JsParamType poMsg com.ebay.dsf.resource.html.event.handler.JsHandlerObjectEnum
* @JsRespHandler
*/
//> public void onResponse(Object poMsg)
onResponse : function (poMsg) {
//handle errors
if (poMsg.response.errors && poMsg.response.errors.length>0) {
var error = poMsg.response.errors;
for (var i = 0; i< error.length;i++) {
alert(error[0].message);
return;
}
}
var data = poMsg.response.data;
var order = data.userNameOrder;
var name = poMsg.request.username;
alert("'" + name + "' " + "is allocated to number " + order);
}
})
.endType();
