vjo.ctype('vjoPro.Test')
.needs('vjoPro.Test2')
.props({
	
	E : vjoPro.Test2,  //< type::Test2
	
	//> public Object doIt(String)
	doIt:function(p){
		
		var x = new this.vj$.Test();
		
		var E = {};

		// good cases
		x.oImg = E.getId() || null;
		var x = E.getId() || null;
		var y = p || null;
		
		// negative cases
//		x.oImg = E.getId() || true;
//		x.oImg = x.get(x.sImgId) || new Date();
		
		return null;
		
	}
	
	
})
.protos({
	oImg : null//<HTMLImageElement
})
.endType();