vjo.ctype('vjoPro.samples.server.ServerEx5') //< public
.props({
/**
* @access public
* @param {String} psGreeting
* @param {vjoPro.samples.server.ServerEx5JsModel} poConfig
*/
//> public void showGreeting(String psGreeting,vjoPro.samples.server.ServerEx5JsModel poConfig)
showGreeting:function(psGreeting, poConfig){
alert(psGreeting + " " + poConfig.firstName + " " + poConfig.lastName);
}

})
.endType();
