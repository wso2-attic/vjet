vjo.ctype('vjoPro.samples.fundamentals.OverloadedConstructorTest3')
.needs('vjoPro.samples.fundamentals.OverloadedConstructor')
.props({
//> public void main(String[] args)
main : function(args)
{
var obj = new this.vj$.OverloadedConstructor(10, 'Test');//<OverloadedConstructor
document.writeln(obj.getX());
document.writeln(obj.getY());
}
})
.endType();
