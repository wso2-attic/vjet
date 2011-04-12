vjo.ctype('syntax.mixintype.Employee')
.mixin('syntax.mixintype.Person')
.props({
	//> public void main(String[] args)
	main : function(args)
	{
		this.doIt1();
		var emp = new this.vj$.Employee(); //<Employee
		this.vj$.Employee.doIt1();
		emp.doIt2();
	}
})
.endType();