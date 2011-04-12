vjo.ctype('syntax.ifstatement.if1')
.protos({

x : 0, //< public int
//> public Number
y : undefined, 

z : undefined,//<public String

//> public void getIsle(int val1, String val2)
getIsle : function (val1, val2)
{
if (val1 == 0)
{
}
else if (val1 == 1)
{
}
else if (val1 == 2)
{
}
else if(val2 == "String"){}
else if(val2 === "string"){}
else if(val1 > 0){}
else if(val1 < 30){}
else if(val1 >= 0){}
else if(val1 <= 30){}
else if(val1 == 0){}
else if(val1 != 0){}
},
//> public int getX()
getX : function()
{
return this.x;
},
//> public Number getY()
getY : function()
{
return this.y;
},


//> public String getZ()
getZ : function()
{
return this.z;
}
})
.endType();