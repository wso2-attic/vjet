vjo.ctype('org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.Employee1')
.mixin('org.ebayopensource.dsf.jst.validation.vjo.rt.mtype.Person1')
.props({
	//> public void main(String[] args)
	main : function(args)
	{
		this.doIt1();
		var emp = new this.vj$.Employee1();//<Employee1
		emp.doIt2();
		emp.drag();
		emp.drop();
		emp.foo();
	}
})
.protos({
	foo: function() {

    }
})
.endType();