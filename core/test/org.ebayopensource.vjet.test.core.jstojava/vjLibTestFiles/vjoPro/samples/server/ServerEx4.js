vjo.ctype('vjoPro.samples.server.ServerEx4') //< public
.props({
/**
* @return String
* @access public
* @param {int} piGreetingId
*/
//> public String getGreeting(int piGreetingId)
getGreeting:function(piGreetingId){
var greeting = "Hello VJO";
if (piGreetingId == 1) {
greeting = "Hello VJO";
} else {
greeting = "Hi VJO";
}
return greeting;
},

/**
* @access public
* @param {String} psGreeting
* @param {String} psName
*/
//> public void showGreeting(String psGreeting,String psName)
showGreeting:function(psGreeting, psName){
alert(psGreeting + " and " + psName);
}

})
.endType();
