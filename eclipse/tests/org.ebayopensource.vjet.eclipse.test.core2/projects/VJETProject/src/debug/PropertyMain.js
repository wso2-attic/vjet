vjo.ctype('debug.PropertyMain') //< public
.needs('debug.type.TypeA')
.props({
	
	//> public void main(String... args)
	main: function(args) {
		var t = new this.vj$.TypeA();//TypeA
		var s = "StringValue";//<String
		t.name = "David";
		vjo.sysout.println(t.name);
	}
})
.protos({
	
})
.endType();