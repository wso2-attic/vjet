vjo.ctype('vjoPro.samples.fundamentals.InstanceInner')
.protos({
InnerClass : vjo.ctype()
.protos({
doIt : function()
{
document.writeln('Instance Inner Class doIt Called');
this.vj$.outer.doIt2();
}
})
.endType(),

doIt2 : function()
{
document.writeln('Instance Outer Class doIt Called');
}
})
.props({
//> public void main(String[] args)
main : function(args){
var outerType = new this.vj$.InstanceInner(); //<InstanceInner
var innerType = new outerType.InnerClass();//<InnerClass
innerType.doIt();
}
})
.endType();
