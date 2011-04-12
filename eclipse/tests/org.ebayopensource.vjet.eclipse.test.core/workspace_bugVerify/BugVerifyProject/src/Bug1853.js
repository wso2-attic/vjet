vjo.ctype('Bug1853') //< public

.props({
	abc:10,

	//> public void main(String... args)
	main: function(args) {
		var abc;
        this.abc=100;
        this.abc=3;
        this.abc=12;
        abc = 12;
	}
})
.protos({
	
})
.endType();