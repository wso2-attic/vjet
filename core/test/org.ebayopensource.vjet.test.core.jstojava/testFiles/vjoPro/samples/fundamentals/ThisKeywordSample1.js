vjo.ctype('vjoPro.samples.fundamentals.ThisKeywordSample1')
.protos({
x : 0, //< public int

//> public void constructs(int val)
constructs : function (val)
{
this.x = val;
},

//> public int getX()
getX : function()
{
return this.x;
}
})
.props({
//> public void main(String[] args)
main : function(args)
{
var obj = new this.vj$.ThisKeywordSample1(10);
vjo.sysout.println(obj.getX());
}
})
.endType();
