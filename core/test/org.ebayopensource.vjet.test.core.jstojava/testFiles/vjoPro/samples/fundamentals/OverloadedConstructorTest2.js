vjo.ctype('vjoPro.samples.fundamentals.OverloadedConstructorTest2')
.needs('vjoPro.samples.fundamentals.OverloadedConstructor')
.props({
//> public void main(String[] args)
main : function(args)
{
var obj = new this.vj$.OverloadedConstructor(10);//<OverloadedConstructor
document.writeln(obj.getX());
document.writeln(obj.getY());
}
})
.endType();
