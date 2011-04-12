vjo.ctype('vjoPro.samples.classes.HelloWorld') //< public
.props({
s_init:false,
/**
* @return boolean
* @access public
*/
//> public boolean helloWorld()
helloWorld:function(){
alert("hello VJO");
return true;
}
})
.inits(function() {
vjoPro.samples.classes.HelloWorld.s_init = true;
})
.endType();
