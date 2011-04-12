vjo.ctype("org.ebayopensource.dsf.vjolang.feature.tests.nestedScriptUnit")
.props({
StaticInnerType : vjo.ctype()
.protos({
	innerFunc: function() {
	vjo.sysout.println('StaticInnerType function called');
		this.vj$.OuterType.outerFunc(); // Fully qualified reference to the outer type class function
	}
	})
	.endType(),
	outerFunc : function() {
		vjo.sysout.println('OuterType function called');
	},
	 makeAnonType : function () {
		var anon = vjo.make(this, this.vj$.SourceType, 'Anonymous Type Property') // vjo.make()
		.protos({
		getAnonTypeProp : function () {
		vjo.sysout.println(this.getProp()); // get the Anonymous Type property
		},
		getSourceTypeProp : function () {
		vjo.sysout.println(this.vj$.parent.getProp()); // get the Outer Type property
		}
		})
		.endType();
		anon.getAnonTypeProp();
		anon.getSourceTypeProp();
}
})
.endType();