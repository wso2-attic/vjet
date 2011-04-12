vjo.ctype('vjoPro.samples.fundamentals.OverloadedConstructorTest1')
.needs('vjoPro.samples.fundamentals.OverloadedConstructor')
.props({
//> public void main(String[] args)
main : function(args)
{
var obj = new this.vj$.OverloadedConstructor();//<OverloadedConstructor
document.writeln(obj.getX());
document.writeln(obj.getY());
}
})
.endType();
