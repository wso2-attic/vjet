vjo.ctype("dox.ebay.vjoPro.vjoPro4jsdev.samples.foundations.HelloWorld")
.needs('vjoPro.samples.classes.HelloWorld','C')
.props({
s_init:false,			//< public boolean
helloWorld:function(){	//< public final Boolean
alert("Hello VjO");
return true;
}
})
.inits(function() {
C.s_init = true;
})
.endType();
