vjo.ctype('vjoPro.samples.fundamentals.ImplementationTest')
.needs('vjoPro.samples.foundations.Employee')
.needs('vjoPro.samples.fundamentals.IEmployer')
.props({
//> public void main(String[] args)
main : function (args)
{
var emp = new this.vj$.Employee('Test_Name',123); //<Employee
document.writeln('Employee Name > ' + emp.getName());
document.writeln('Employee ID > ' + emp.getEmpId());
document.writeln('Employer Name > ' + this.vj$.Employee.employername);

}
})
.endType();
