vjo.ctype('vjoPro.samples.basic.vjoProtype.Employee') //< public
.inherits('vjoPro.samples.basic.vjoProtype.Person')
.protos({
/**
* @return void
* @access public
* @param {String} name
* @param {int} age
* @param {float} salary
*/
//> public constructs(String name,int age,float salary)
constructs:function(name,age,salary){
this.base(name,age);
this.salary = salary;
},

/**
* @return void
* @access public
* @param {float} salary
*/
//> public void setSalary(float salary)
setSalary:function(salary){
this.salary = salary;
},
/**
* @return float
* @access public
*/
//> public float getSalary()
getSalary:function(){
return this.salary;
}
})
.endType();
