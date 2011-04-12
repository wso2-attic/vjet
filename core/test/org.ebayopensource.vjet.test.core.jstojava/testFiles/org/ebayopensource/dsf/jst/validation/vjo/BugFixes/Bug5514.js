vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.BugFixes.Bug5514")
.props({
	map: {'one':['1', '2']},
	
	foo: function(){
		alert(this.map.one[0]);
		alert(this.map["one"][0]);
	},
	
	bar: function(){
		var map = this.map;
		alert(map.one[0]);
		alert(map['one'][0]);
	}
})
.endType();