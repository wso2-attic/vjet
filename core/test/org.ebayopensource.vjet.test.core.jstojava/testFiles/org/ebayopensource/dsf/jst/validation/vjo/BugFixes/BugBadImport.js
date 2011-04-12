vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.BugBadImport")
.props({

	prop: null,//<NoOneKnows
	
	//>void play(NoOneKnows)
	play: function(who){
		alert(who.abc);
		who.abc();
		new who.what();
	},
	
	foo: function(){
		alert(this.prop.what);
		this.prop.what();
		
		var local = null;//<NoOneKnows
		local.doIt();
		alert(local.sth);
	}
})
.endType();