vjo.ctype('vjoPro.samples.basic.eventhandler.EventHandlerEx8') //< public
.props({
/**
* @return void
* @access public
* @param {String} psId
*/
//> public void bindEventsInJS(String psId)
bindEventsInJS : function(psId){
vjoPro.dsf.EventDispatcher.add(psId, 'click', function(event) { return vjoPro.samples.basic.eventhandler.EventHandlerEx8.helloWorldInJS();});
},

helloWorldInJS : function(){
alert("Hello World in JS");
return false;
}

})
.endType();
