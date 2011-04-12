vjo.ctype('syntax.mixintype.Employee3')
.mixin('syntax.mixintype.Person')
.props({
	
	age : 0, //public int
	
	//> public void main(String[] args)
	main : function(args)
	{
	}
})
.protos({
	name : "HA" //public String
})
.endType();