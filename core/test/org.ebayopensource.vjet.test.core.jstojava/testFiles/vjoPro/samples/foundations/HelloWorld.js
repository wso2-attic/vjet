vjo.ctype('vjoPro.samples.foundations.HelloWorld')
.props({
s_init:false,			//< public
helloWorld:function(){	//< public final void helloWorld()
alert('Hello VjO');
return true;
}
})
.inits(function() {
vjoPro.samples.classes.HelloWorld.s_init = true;
})
.endType();
