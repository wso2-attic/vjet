vjo.etype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugEnumPropertyInit")
.values('Foo, Bar, Zot')
.props({
	F: undefined, //<BugEnumPropertyInit
	B: undefined, //<BugEnumPropertyInit
	Z: undefined //<BugEnumPropertyInit
})
.inits(function(){
	this.F = this.Foo;
	this.B = this.Bar;
	this.Z = this.Zot;
}).endType();