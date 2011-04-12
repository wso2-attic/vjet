vjo.ctype('vjoPro.samples.classes.HelloEmployee') //< public
.needs('vjoPro.samples.classes.Employee')
.props({
/**
* @return boolean
* @access public
*/
//> public boolean helloEmployee()
helloEmployee:function(){
var emp = new vjoPro.samples.classes.Employee("John", "100.11");
alert("Hello " + emp.getName() + ", Salary=" + emp.getSalary());
return false;
}
})
.endType();
