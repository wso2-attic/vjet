vjo.ctype('syntax.methodOverload.construct.ConstructorOverload1')
.protos({
x : undefined, //< public int
y : undefined, //< public String

//> public void constructs()
//> public void constructs(int val1, String val2)
constructs : function (val1, val2)
{
var args = arguments.length;
if (args == 0)
{
this.constructs0();
}
else if (args == 1)
{
this.constructs1(val1);
}
else if (args == 2)
{
this.constructs2(val1, val2);
}
},
//> public void constructs0()
constructs0 : function ()
{
this.x = 0;
this.y = 'Something';
},
//> public void constructs1(int val1)
constructs1 : function (val1)
{
this.x = val1;
this.y = 'Something';
},
//> public void constructs2(int val1, String val2)
constructs2 : function (val1, val2)
{
this.x = val1;
this.y = val2;
},
//> public int getX()
getX : function()
{
return this.x;
},
//> public String getY()
getY : function()
{
return this.y;
}
})
.endType();