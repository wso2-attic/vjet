vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.HelloEmployee")
//snippet.vjoProtype.end
.needs("vjoPro.samples.classes.Employee")
.props({

//> public boolean helloEmployee()
helloEmployee:function(){
var emp = new vjoPro.samples.classes.Employee("John", "100.11");
alert("Hello " + emp.getName() + ", Salary=" + emp.getSalary());
return false;
}
})
.endType();
