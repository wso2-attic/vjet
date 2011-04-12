vjo.ctype('syntax.methodOverload.construct.ConstructorOverload2')
.needs('syntax.methodOverload.construct.ConstructorOverload1')
.props({
//> public void main(String[] args)
main : function(args)
{
var obj = new this.vj$.ConstructorOverload1();//<ConstructorOverload1
document.writeln(obj.getX());
document.writeln(obj.getY());

obj = new this.vj$.ConstructorOverload1(10, "Test");//<ConstructorOverload1
document.writeln(obj.getX());
document.writeln(obj.getY());

obj = new this.vj$.ConstructorOverload1(10, 'Test');//<ConstructorOverload1
document.writeln(obj.getX());
document.writeln(obj.getY());
}
})
.endType();