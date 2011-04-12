vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.Employee")
.needs("dox.ebay.vjoPro.vjoPro4javadev.samples.Person")
.inherits("dox.ebay.vjoPro.vjoPro4javadev.samples.Person")
.protos({

salary : null,//<float

//>public void constructs(String name, float salary)
constructs:function(name,salary){
this.base(name);
this.salary = salary;
},

//> public void setSalary(float salary)
setSalary:function(salary){
this.salary = salary;
},

//> public float getSalary()
getSalary:function(){
return this.salary;
}
})
.endType();
