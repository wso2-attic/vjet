vjo.ctype('vjoPro.samples.fundamentals.ThisKeywordSample3')
.needs('vjoPro.samples.fundamentals.ThisKeywordSample2')
.props({
x : 10, //< public int

//> public int getX()
getX : function()
{
return this.vj$.ThisKeywordSample3.x;
},

//> public void main(String[] args)
main : function(args)
{
vjo.sysout.println(this.vj$.ThisKeywordSample2.getX());
vjo.sysout.println(this.getX());
}
})
.endType();
