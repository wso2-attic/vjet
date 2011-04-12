vjo.ctype('vjoPro.samples.basic.eventhandler.EventHandlerEx9') //< public
.props({
/**
* @return void
* @access public
* @param {String} psId
*/
//> public void bindEventsInJS(String psId)
bindEventsInJS : function(psId){
vjoPro.dsf.EventDispatcher.addEventListener(psId, 'click', function(event) { return vjoPro.samples.basic.eventhandler.EventHandlerEx9.helloWorldInJS();});
},

helloWorldInJS : function(){
alert("Hello World in JS");
return false;
}

})
.endType();
