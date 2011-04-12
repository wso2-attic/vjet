vjo.ctype("vjoPro.samples.EqualsSample1")
.needs("vjoPro.samples.fundamentals.Employee")
.props({
//> public void main(String[] args)
main:function(args){
var employee1 = new vjoPro.samples.fundamentals.Employee();//<Employee
var employee2 = new vjoPro.samples.fundamentals.Employee();//<Employee
var emp1HashCode = employee1.hashCode();
var emp2HashCode = employee2.hashCode();
alert("Employee1 HashCode = " + emp1HashCode);
alert("Employee2 HashCode = " + emp2HashCode);
alert("employee1.equals(employee2) returns > " + employee1.equals(employee2));
}
})
.endType();
