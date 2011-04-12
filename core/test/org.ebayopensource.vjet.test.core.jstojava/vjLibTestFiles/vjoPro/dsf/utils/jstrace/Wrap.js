vjo.ctype("vjoPro.dsf.utils.jstrace.Wrap")
.needs("vjoPro.dsf.utils.reflection.Reflection","R")
.props({

//expr could be:
//0. for '.' to be part of regex, use '\.' in url / vm args or use '\\.' in java code
//1. clz#func	here, clz is the full name of a clz, func is represented in regex
//2. clz.func		here, clz is the full name of a clz, func is the full name of a func
wrap : function(expr, cfg){
bf = cfg.beforeAct,
af = cfg.afterAct,
ex = t.R.escape(expr),
clzArr = this.vj$.R.expr2clz(ex), //objs with name matching clz
func = this.vj$.R.expr2func(ex), //str of func regex / name
bFuncRegex =  this.vj$.R.isRegEx(ex),
wl = this.vj$.R.getList(cfg.wl),
bl = this.vj$.R.getList(cfg.bl);
for(var clz in clzArr){
if(bFuncRegex){
//funcArr is an array of func names (str)
var funcArr = this.vj$.R.findFunc(clzArr[clz], func, wl, bl), i=0, L = funcArr.length;
while(i < L){
t.wrapIt(expr, clzArr[clz], funcArr[i++], bf, af);
}
}
else{
//save a loop
t.wrapIt(expr, clzArr[clz], func, bf, af);
}
}
},

/**
* @JsReturnType boolean
* @JsJavaAccessToJs private
* This is the actual wrap util, the outgoing msg will be constructred as the following
* [callee, function full name, arguments, time @ browser]
*/
wrapIt : function(expr, clz, func, before, after){
if(func === '_invalid_'){
return;
}

var f = clz.prototype[func];
if(!f){
return;
}

clz.prototype[func] = function(){
//get ID from registry
if(!this.vjoTraceId){
this.vjoTraceId = 'N/A'; //if the function is executed before the jsr instance is put into Registry
var c = vjo.RegisvjoPro.controls;
for(var i in c){
if(c[i] == this){
this.vjoTraceId = i;
break;
}
}
}

var  j = 0, l = arguments?arguments.length:0,ctx = {};
ctx.jsrId = this.vjoTraceId;
ctx.expr = expr;
ctx.target = clz.vjoClzName + '.' + func + '()';
ctx.type = 'ENTER';
ctx.time = new Date().getTime().toString();
var otime = ctx.time;
ctx.args = [];
while(j<l){ctx.args[j]=arguments[j];j++;}
if(before){
before(ctx);
}
var rv = f.apply(this, arguments);
ctx.type = 'EXIT';
ctx.time = new Date().getTime().toString();
ctx.delta = ctx.time - otime;
if(after){
after(ctx);
}
return rv;
};
}
})
.endType();
