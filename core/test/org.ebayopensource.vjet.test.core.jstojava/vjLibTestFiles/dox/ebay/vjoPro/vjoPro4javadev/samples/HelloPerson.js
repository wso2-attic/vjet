vjo.ctype("dox.ebay.vjoPro.vjoPro4javadev.samples.HelloPerson")
.needs("vjoPro.samples.classes.Person")
.props({

//> public boolean helloPerson()
helloPerson:function(){
var person1 = new vjoPro.samples.classes.Person();
person1.setName("John");
alert("Hello " + person1.getName());
return false;
}
})
.endType();
