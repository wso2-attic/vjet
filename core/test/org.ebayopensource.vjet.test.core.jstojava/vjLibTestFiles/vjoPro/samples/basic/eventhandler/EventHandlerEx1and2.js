vjo.ctype('vjoPro.samples.basic.eventhandler.EventHandlerEx1and2') //< public
.protos({
/**
* @return void
* @access public
* @param {String} psMessage
*/
//> public constructs(String psMessage)
constructs : function(psMessage){
this.sMessage = psMessage;
},

/**
* @return boolean
* @access public
* @param {String} psName
*/
//> public boolean helloWorld(String psName)
helloWorld : function(psName){
alert(this.sMessage + " " + psName);
return false;
}

})
.endType();
