vjo.ctype('vjoPro.samples.mtype.Employee5')
.needs('vjoPro.samples.mtype.Employee4')
.needs('vjoPro.samples.mtype.Person4')
.props({
//> public void main(String[] args)
main : function(args){

	this.vj$.Employee4.doIt1();
	var emp = new this.vj$.Employee4();//<Employee4
	vjo.mixin('vjoPro.samples.mtype.Person4', emp);
	
	emp.doIt();//doIt is defined in Person4
	document.writeln('i = ' + emp.i);//i is defined in Person4
	emp.doIt2();//doIt2 is defined in Employee4
}
})
.endType();
