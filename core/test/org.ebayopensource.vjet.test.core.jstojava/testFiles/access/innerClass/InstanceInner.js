vjo.ctype('access.innerClass.InstanceInner')
.protos({
	InnerClass : vjo.ctype()
	.protos({
		doIt : function()//< void doIt()
		{
			document.writeln('Instance Inner Class doIt Called');
			this.vj$.outer.doIt2();
    		}
    	})
    	.endType(),
    	
    	
    	//> public void voIt2()
    	doIt2 : function()
    	{
			document.writeln('Instance Outer Class doIt Called');
    	}
})
.props({
	//> public void main(String[] args)
	main : function(args){
		var outerType = new access.innerClass.InstanceInner();
		var innerType = new outerType.InnerClass();
		innerType.doIt();
	}
})
.endType();
