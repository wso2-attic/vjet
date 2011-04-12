vjo.ctype('org.ebayopensource.vjo.runtime.tests.section.globals.Foo')
.props({
		//>public String bar(String)
		bar:function(){
			return this.doIt();
		},

		doIt:function(){
			return "testing";
		}
})
.protos({
	//>public String bar(String)
	bar2:function(){
		return this.doIt2();
	},
	
	doIt2:function(){
		return "testing2";
	}

	
})