vjo.ctype('vjoPro.samples.fundamentals.CodeSnippetNeeds1')
.needs('vjoPro.samples.fundamentals.etc.MyTest')
.protos({
//> public void doIt()
doIt: function(){
	(new vjoPro.samples.fundamentals.etc.MyTest()).doSomething();
}
})
.endType();
