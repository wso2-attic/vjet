vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugVjoMake')
.protos({
	constructs:function(name, value){//<public constructs(String, int)
	},
	
	doIt: function(){//<public void doIt()
	}
})
.props({
    main: function(){
		var t = new this('default', 0);//<BugVjoMake
		
		var obj1 = vjo.make(t, t, 'name', 1)
					.protos({
						foo: function(){
							this.vj$.parent.doIt();
						}
					})
					.endType();
		var obj2 = vjo.make(t, t, 1, 'name')
					.protos({
					})
					.endType();
	}
})
.endType();