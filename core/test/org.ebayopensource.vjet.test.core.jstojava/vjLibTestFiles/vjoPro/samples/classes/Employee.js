vjo.ctype('vjoPro.samples.classes.Employee') //< public
.inherits('vjoPro.samples.classes.Person')
.protos({
salary : 20, //<float

	//> public constructs(String name, float salary)
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
