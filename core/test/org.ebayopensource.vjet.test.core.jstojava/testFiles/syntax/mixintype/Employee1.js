vjo.ctype('syntax.mixintype.Employee1')
.mixin('syntax.mixintype.Person')
.props({
	//> public void main(String[] args)
	main : function(args)
	{
		this.doIt1();
	}
})
.endType();