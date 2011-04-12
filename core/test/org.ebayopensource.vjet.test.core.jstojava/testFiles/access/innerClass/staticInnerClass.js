vjo.ctype('access.innerClass.staticInnerClass')
.props({
//> public InnerClass
InnerClass : vjo.ctype()
.protos({

//> void doIt1()
doIt1 : function()
{
document.writeln('Static InnerClass doIt Called');
this.vj$.staticInnerClass.doIt2();
//You can also use the next statement to call doIt2()
//vjo.samples.fundamentals.StaticInner.doIt2()
}
})
.endType(),

//> public void doIt2()
doIt2 : function()
{
document.writeln('OuterClass doIt Called');
},
//> public void main(String[] args)
main:function(args){
var innerType1 = new this.InnerClass();
var innerType2 = new this.vj$.staticInnerClass.InnerClass();
var innerType3 = new access.innerClass.staticInnerClass.InnerClass();
innerType1.doIt1();
innerType2.doIt1();
innerType3.doIt1();
}
})
.endType();