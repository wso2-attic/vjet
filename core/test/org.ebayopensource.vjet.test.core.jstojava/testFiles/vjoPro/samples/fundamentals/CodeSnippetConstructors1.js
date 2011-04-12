vjo.ctype('vjoPro.samples.fundamentals.CodeSnippetConstructors1')
.protos({
x : 0, //< public int
y : undefined, //< public String

//> public void constructs(int val1, String val2)
constructs : function (val1, val2)
{
this.x = val1;
this.y = val2;
},

//> public int getX()
getX : function()
{
return this.x;
},

//> public int getY()
getY : function()
{
return this.y;
}
})
.endType();
