vjo.ctype("vjoPro.dsf.utils.Handlers")
.needs(["vjoPro.dsf.EventDispatcher",
"vjoPro.dsf.Message",
"vjoPro.dsf.ServiceEngine"])
.props({

ED : vjoPro.dsf.EventDispatcher,

SE : vjoPro.dsf.ServiceEngine,

/**
* Attaches some event handler to a DOM element on some event type.
*
* @param {Object} target
*        A DOM element which the handler attached to
* @param {String} type
*        An event type that the handler is going to handle
* @param {Object} func
*        A handler function to be attached
* @param {Object} scope
*        A scope that the handler belongs to
* @return {Object}
*        the handler bound, which will be used when detaches the handler
* @see   #detachEvt(Object, String, Object)
*/
//> public boolean attachEvt(Object, String, Object, Object);
attachEvt : function(target, type, func, scope){
return this.ED.addEventListener(target, type, func, scope);
},

/**
* Detaches a given handler from a DOM element.
*
* @param {Object} target
*        A DOM element which the handler attached to
* @param {String} type
*        An event type that the handler handles
* @param {Object} hdl
*        a handler bound that is returned from <code>attachEvt()</code>
* @return {boolean}
*        The boolean value to show whether this action is successful
* @see   #attachEvt(Object, String, Object, Object)
*/
//> public boolean detachEvt(Object, String, Object);
detachEvt : function(target, type, hdl){
return this.ED.removeEventListener(target, type, hdl);
},

/**
* Creates a new message for given service name.
*
* @param {String} svcName A name of service name this message sends to
* @return {vjoPro.dsf.Message} The new message for the given service name
*/
//> public vjoPro.dsf.Message newMsg(String);
newMsg : function(svcName){
return new vjoPro.dsf.Message(svcName);
},

/**
* Puts the giving message into the ServiceEngine. ServiceEngine calls all
* registered handlers in sequence to handle the message
*
* @param {vjoPro.dsf.Message} msg
*        A service message to be processed
*/
//> public void handle(Object);
handle : function(msg){
this.SE.handleRequest(msg);
},

//> private Object createHdl(Object, Object, String);
createHdl : function(scope, func, name){
var scp = scope,
hdl = {};
hdl[name] = function(){func.apply(scp, arguments);};
return hdl;
},

/**
* Attaches a handler to a service. Only one handler could be attached per
* a service id
*
* @param {String} svcName
*        A name of service to associate the handler with
* @param {Object} func
*        A handler that represents the target service.
* @param {Object} scope
*        A scope that the handler belongs to
*/
//> public void attachSvc(String, Object, Object);
attachSvc : function(svcName, func, scope){
var t = this,
hdl = t.createHdl(scope, func, 'invoke');
if(t.SE && hdl){
t.SE.registerSvcHdl(svcName, hdl);
}
},

/**
* Attaches a request handler to a service.There can be multiple service
* request handlers for a given service id.
*
* @param {String} svcName
*        A name of service to associate the handler with
* @param {Object} func
*        A function that represents the target service.
* @param {Object} scope
*        A scope that the handler belongs to
*/
//> public void attachSvcReqt(String, Object, Object);
attachSvcReqt : function(svcName, func, scope){
var t = this,
hdl = t.createHdl(scope, func, 'handleRequest');
if(t.SE && hdl){
t.SE.registerSvcReqtHdl(svcName, hdl);
}
},

/**
* Attaches a response handler to a service. These handlers should be able to
* use the data provided by the service. There can be multiple service
* response handlers for a given service id. In an Ajax call, these handlers
* would be equivalent to a callback function. When the response gets back
* to the client, these handlers are notified.
*
* @param {String} svcName
*        A name of service to associate the handler with
* @param {Object} func
*        A function that represents the target service.
* @param {Object} scope
*        A scope that the handler belongs to
*/
//> public void attachSvcResp(String, Object, Object);
attachSvcResp : function(svcName, func, scope){
var t = this,
hdl = t.createHdl(scope, func, 'handleResponse');
if(t.SE && hdl){
t.SE.registerSvcRespHdl(svcName, hdl);
}
},

/**
* Detaches the handler from a specified service.
* @param {String} svcName
*        A name of service to detach the handler from
*/
//> public void resetSvc(String);
resetSvc : function(svcName){
this.SE.inProcHdl.svcHdls[svcName] = [];
},

/**
* Detaches all request handlers from a specified service.
* @param {String} svcName
*        A name of service to detach the handler from
*/
//> public void resetSvcReqt(String);
resetSvcReqt : function(svcName){
this.SE.svcReqtHdls[svcName] = [];
},

/**
* Detaches all response handlers from a specified service.
* @param {String} svcName
*        A name of service to detach the handler from
*/
//> public void resetSvcResp(String);
resetSvcResp : function(svcName){
this.SE.svcRespHdls[svcName] = [];
}

})
.endType();
