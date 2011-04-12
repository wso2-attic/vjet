vjo.ctype('dsf.jst.validation.vjoPro.BugFixes.CTypeUtil') //< public
.props({
//>public void staticFunc()
staticFunc : function(){
var a = true;
if(a && document){
//document exists
}
}
})
.protos({

list:null,

//>public void instanceFunc()
instanceFunc : function(){
var elem=window.event.target || window.event.srcElement;
this.vj$.CTypeUtil.staticFunc();
var d = new Date();
},

//public int getIsle(int val1, String val2)
getIsle : function(val1, val2){
if (val1 == 0)
{
return 0;
}
else if (val1 == 1)
{
return 0;
}
},

test: function() {
this.list = []; //< Array
this.list.push("");
},

//>public void foo()
foo : function(){
var func = function(){};  //<void function()
}

})
.inits(function(){
eval("alert('a')");
})
.endType();
