vjo.ctype('access.returnStatement.AccessReturn1Tester')
.protos({

//> public int getIsle(int val1, String val2)
getIsle : function (val1, val2)
{
if (val1 == 0)
{
	return 0;
}
else if (val1 == 1)
{
return 0;
}
else if (val1 == 2)
{
return 0;
}
else if(val2 == "String"){return 0;}
else if(val2 === "string"){return 0;}
else if(val1 > 0){return 0;}
else if(val1 < 30){return 0;}
else if(val1 >= 0){return 0;}
else if(val1 <= 30){return 0;}
else if(val1 == 0){return 0;}
else if(val1 != 0){return 0;}
else return 0;
}
})
.endType();