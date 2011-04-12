vjo.etype('a.E') //< public
.values('Foo, Bar, Zot' 
)
.props({
	main: function(args) { //< public void main(String[])
		var o = vjo.sysout.println ;
		o(this.F) ;
		o(this.B) ;
		o(this.
    },
	F: undefined, //< E
	B: undefined, //< E
	Z: undefined  //< E
})
.inits(function() {
	this.F = this.Foo ;
	this.B = this.Bar ;
	this.Z = this.Zot ;
})
.endType();
