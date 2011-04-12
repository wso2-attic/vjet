vjo.ctype('syntax.methodOverload.construct.ConstructorOverload2')
.needs('syntax.methodOverload.construct.ConstructorOverload1')
.props({
//> public void main(String[] args)
main : function(args)
{
var obj = new this.vj$.OverloadedConstructor();
document.writeln(obj.getX());
document.writeln(obj.getY());

obj = new this.vj$.OverloadedConstructor("String");
document.writeln(obj.getX());
document.writeln(obj.getY());

obj = new this.vj$.OverloadedConstructor('Test', 10);
document.writeln(obj.getX());
document.writeln(obj.getY());
}
})
.endType();