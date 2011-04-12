vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.bad.Bug6555MainBad")
.needs("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug6555CType")
.props({

	main: function(){ //< public void main()
		var _6555 = new this.vj$.Bug6555CType();//<Bug6555CType
		_6555.bar();
		_6555.bar2();
	}
})
.endType();