vjo.ctype('access.scope.defaultModifier.StaticDefaultUser5')
.needs('access.scope.defaultModifier.DefaultPerson1')
.props({
	//> public void main(String[] args)
	main : function(args)
	{
		vjo.sysout.println(this.vj$.DefaultPerson1.getX());
		vjo.sysout.println(this.vj$.DefaultPerson1.x);
	}
})
.endType();