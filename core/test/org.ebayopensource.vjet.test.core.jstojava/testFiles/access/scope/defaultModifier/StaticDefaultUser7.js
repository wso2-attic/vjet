vjo.ctype('access.scope.defaultModifier.StaticDefaultUser7')
.needs('access.scope.protectedModifier.ProtectedPerson1')
.props({
	//> public void main(String[] args)
	main : function(args)
	{
		vjo.sysout.println(this.vj$.ProtectedPerson1.getX());
		vjo.sysout.println(this.vj$.ProtectedPerson1.x);
	}
})
.endType();