vjo.ctype('Bug5009') //< public

.props({
	
	//> public void main(String... args)
	main: function(args) {
		var bool = new Boolean(); //< Boolean
		bool.toSource();
	}
})
.protos({
	
})
.endType();