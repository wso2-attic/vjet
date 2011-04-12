vjo.ctype('vjoPro.samples.classes.HelloPerson') //< public
.needs('vjoPro.samples.classes.Person')
.props({
/**
* @return boolean
* @access public
*/
//> public boolean helloPerson()
helloPerson:function(){
var person1 = new vjoPro.samples.classes.Person();
person1.setName("John");
alert("Hello " + person1.getName());
return false;
}
})
.endType();
