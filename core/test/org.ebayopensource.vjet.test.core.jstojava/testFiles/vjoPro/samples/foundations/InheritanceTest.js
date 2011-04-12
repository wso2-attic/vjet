vjo.ctype('vjoPro.samples.foundations.InheritanceTest')
.needs('vjoPro.samples.foundations.Manager')
.props({
//> public void main(String[] args)
main : function(args)
{
var emp = new this.vj$.Manager('V4');
document.writeln('Emp Name > ' + emp.name);
document.writeln('Emp Id > ' + emp.empId);
document.writeln('Emp Project > ' + emp.project);
}
})
.endType();
