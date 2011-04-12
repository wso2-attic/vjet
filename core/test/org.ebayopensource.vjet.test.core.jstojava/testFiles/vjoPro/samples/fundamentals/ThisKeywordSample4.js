vjo.ctype("vjoPro.samples.fundamentals.ThisKeywordSample4")
.props({
x : undefined, //< public int

//> public int getX()
getX : function()
{
return this.x;
},

//> public void main(String[] args)
main : function(args)
{
document.writeln(this.getX());
}
})
.inits(
function(){
this.x = 10;
//or you can use this.vj$.ThisKeywordSample4 instead of this.
//this.vj$.ThisKeywordSample4.x = 10;
}
)
.endType();
