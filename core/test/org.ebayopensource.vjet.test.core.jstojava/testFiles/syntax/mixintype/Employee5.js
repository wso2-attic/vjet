vjo.ctype('syntax.mixintype.Employee5')
.mixin('syntax.mixintype.Person')
.props({
    //> public void main(String[] args)
    main : function(args)
    {
    	this.vj$.Employee5.doIt1();
    	var emp = new this.vj$.Employee5();//<Employee5
		//snippet.mixin.begin
		//snippet.mixin.end
    	emp.doIt2();//doIt is defined in Person
    }
})
.endType();
