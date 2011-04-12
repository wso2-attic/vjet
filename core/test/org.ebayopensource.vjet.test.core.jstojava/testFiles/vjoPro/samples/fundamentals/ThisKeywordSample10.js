vjo.ctype("vjoPro.samples.fundamentals.ThisKeywordSample10")
.protos({
x : undefined, //< public String[]

//> public void constructs(String val1)
constructs:function(val1)
{
this.x = [];
var args = arguments.length;
if (args == 1)
{
this.x[0] = val1;
}
}

})
.props({
//> public void main(String[] args)
main:function(args)
{
var obj1 = new this.vj$.ThisKeywordSample10();//<ThisKeywordSample10
document.writeln('obj1 Array : ' + obj1.x);
document.writeln('obj1 Array Size : ' + obj1.x.length);
var obj2 = new this.vj$.ThisKeywordSample10('A');//<ThisKeywordSample10
document.writeln('obj2 Array : ' + obj2.x);
document.writeln('obj2 Array Size : ' + obj2.x.length);
}
})
.endType();
