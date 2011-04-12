vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.with_.NestedWith') //< public
.props({
	a: 1, //<int
	foo: function(){
		var whatever = this;
		with(whatever){
			with(whatever){
				alert(a);
			}
		}
	}
})
.endType();