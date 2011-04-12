vjo.ctype('vjoPro.samples.mtype.Employee6')
.mixin('vjoPro.samples.mtype.Person6')
.props({
//> public void main(String[] args)
main : function(args)
{
var emp = new this.vj$.Employee6();//<Employee6
emp.printGender();//printGender is defined in mixin Person6
}
})
.endType();
