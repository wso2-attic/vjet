vjo.ctype('vjoPro.samples.fundamentals.HelloWorldClient') //defines a class
.needs('vjoPro.samples.fundamentals.HelloWorld') //.needs resolve dependency
.props({
//> public void main(String[] args)
main: function(args)
{
this.vj$.HelloWorld.getName1(); // this calls the static method getName1()
var helloWorld = new this.vj$.HelloWorld("VjO"); //< HelloWorld
helloWorld.getName2();// this calls the instance method getName2()
}
})
.endType(); //Every VjO Type must end with a call to endType()
