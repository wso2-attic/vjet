vjo.ctype('syntax.mixintype.Employee7')
.mixin('syntax.mixintype.Person')
.props({
	//> String
	lll:function(){ return ""},
	
	doIt:function(){ 
		var i = new this.vj$.Employee7();
		vjo.isInstanceOf(i, syntax.mixintype.Person);
		return this.lll();
		}
})
.endType();