vjo.ctype('vjoPro.samples.anon.Util')
.props({
//> public String doIt()
doIt : function(args)
{
return 'Util - doIt';
}
})
.protos({

value : 'Util', //< public String

//public void contructs(String val)
constructs : function(val)
{
this.value = val;
},

//> public String getVal()
getVal:function()
{
return this.value;
}
})
.endType();
