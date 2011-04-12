vjo.ctype('syntax.declare.otype.OTypeTarget')
//> needs(syntax.declare.otype.Defbug1)
.props({
})
.protos({
	//>public void doIt(Defbug1.foo) 
	doIt : function(x1){  
		x1(3);
		x1("D");
		var s = null;//<Function
		s(2);       
	}
})
.endType();