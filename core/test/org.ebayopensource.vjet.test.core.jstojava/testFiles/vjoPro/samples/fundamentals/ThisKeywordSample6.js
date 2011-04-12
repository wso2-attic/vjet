vjo.ctype("vjoPro.samples.fundamentals.ThisKeywordSample6")
.needs("vjoPro.samples.fundamentals.ThisKeywordSample2")
.protos({
x : 0, //< public int

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
var obj = new this.vj$.ThisKeywordSample6();
vjo.sysout.println(obj.getX());
vjo.syserr.println(this.vj$.ThisKeywordSample2.x);
}
})
.endType();
