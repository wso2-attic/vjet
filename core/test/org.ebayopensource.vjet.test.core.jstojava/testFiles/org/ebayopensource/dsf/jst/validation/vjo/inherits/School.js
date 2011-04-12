vjo.ctype("org.ebayopensource.dsf.jst.validation.vjo.inherits.School")
.needs(["org.ebayopensource.dsf.jst.validation.vjo.inherits.Child","org.ebayopensource.dsf.jst.validation.vjo.inherits.parent"])
.props({
	//> public void main(String... args)
	main: function(){
		var child = new this.vj$.Child(); //<Child
		child.sayIt2();
	}
	
})
.endType();