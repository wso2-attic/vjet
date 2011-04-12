vjo.ctype('vjoPro.samples.server.ServerEx3') //< public
.props({
/**
* @return String
* @access public
*/
//> public String getGreeting()
getGreeting:function(){
var greeting = "Hello VJO";
return greeting;
},

/**
* @access public
* @param {String} psGreeting
*/
//> public void showGreeting(String psGreeting)
showGreeting:function(psGreeting){
alert(psGreeting);
}

})
.endType();
