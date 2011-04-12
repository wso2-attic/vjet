vjo.ctype("vjoPro.dsf.utils.reflection.Reflection")
.props({

getVJOType: function(typeName){
return this.innerGetVJOType(window, typeName);
},

innerGetVJOType: function(scope, relName){
var i = relName.indexOf('.');
if(i < 0){
return scope[relName];
}
var	current = relName.substring(0, i),
todo = relName.substring(i+1, relName.length);
return this.innerGetVJOType(scope[current], todo);
},

getVJOInstance: function(typeName){
return this.innerGetVJOInstance(window, typeName);
},

innerGetVJOInstance: function(scope, relName){
var i = relName.indexOf('.');
if(i < 0){
return new scope[relName]();
}
var	current = relName.substring(0, i),
todo = relName.substring(i+1, relName.length);
return this.innerGetVJOInstance(scope[current], todo);
},

/*
* Regex defined in JS Trace:
*	'#' will separate the clz clause & the func clause if the func is represented in regex, or else '.' will take that responsibility

*
*	For func, the clause will always represent a normal string
*		1)		*									all func
*		2)		funcName					a normal regex string for func name
*	For clz, the clause could be one of below (1-3):
*		0)		ClzName						a normal regex string for clz name, without package;
*				PkgName						a normal regex string for pkg name
*		1)		*.*.ClzName				each '*' will represent the name of a package
*		2)		**.ClzName					the '**' will represent any level of package
*		3)		(PkgName.)*ClzName	the full name of the clz
*/

/*
*	cache content
*/
oCache : [],

//============
//	Class reflection
//============

/*
* This calc the expr to clz, root from window
*/
expr2clz : function(expr){//<public Array expr2clz(String)
var vjoClzName = 'vjo';
var t = this, clzArr = [];
if(expr && t.isTypeStr(expr)){
var sep = t.isRegEx(expr) ? '#' : '.',
clz = expr.substring(0, expr.lastIndexOf(sep));
if(clz.indexOf('**') < 0){
//without '**', we could enjoy the cache to the maxium
clzArr = t.findDef(window, clz, true);
}else{
//with '**', only one chance to work with cache
clzArr = t.quickFindDef(window, clz, 1); //only need to take 1st check in quick
if(clzArr && clzArr.length === 0){
//quick find failed, try normal flow
var steps = clz.split('.');
clzArr = t.DFS4Def(window, steps, 0);
}
}
}
return clzArr;
},

//============
//	Quick find in cache
//============

/*
*	try to find clz in cache or directly children of window
*/
quickFindDef : function(glob, clz, iLast){
var t = this,
defArr = [];
//if cached, return directly
if(t.oCache[clz]){
defArr = t.oCache[clz];
}else if(iLast < 0){
//if no '.', means the direct child  to glob
//could be both regex or plain string
t.oCache[clz] = t.getClzArr(glob, clz, null);
defArr = t.oCache[clz];
}
return defArr;
},

//============
//	Find in cache for non multi level wildcast pattern
//============

/*
*	try to find whole clz in cache,
*	if failed, try to find prefix in cache then
*/
findDef : function(glob, clz, bType){
if(clz === ''){
return [];
}
var t = this,
iLast = clz.lastIndexOf('.'),
clzArr=[];

clzArr = t.quickFindDef(glob, clz, iLast);
if(clzArr && clzArr.length > 0){
return clzArr;
}

//with '.', recursively find collection of matching objects
var sClz = clz.substring(iLast+1),
sPre = clz.substring(0, iLast),
oPreClzArr = t.findDef(glob, sPre, false);
if(oPreClzArr){
//oPreClzArr is now collection of objects matching sPre
//next step is to find sCache.sClz
for(var oPreClz in oPreClzArr){
//exception, vjo.global should be ignored
var obj = oPreClzArr[oPreClz];
if(obj === window || obj === document) {continue;}
t.appendArr(t.getClzArr(obj, sClz, bType), clzArr, obj.vjoClzName);
}
t.oCache[clz] = clzArr;
}
return clzArr;
},

getClzArr : function(glob, clz, bFunc){
if(clz === ''){
return [];
}
var t=this,
clzArr = [],
regex = t.getRegex(clz);
//IE bug, 'for' to window could not find 'vjo'
if(clz === 'vjo' && glob === window){
return [glob[clz]];
}
for(var oC in glob){
//IE bug, some oCs will have a leading white space
oC = oC.replace(' ', '');
if(t.isTypeStr(oC) && oC.match(regex)){
var obj = glob[oC];
if(obj === window || obj === document) {continue;}
//exception, vjo.global should be ignored
if(bFunc ? t.isTypeFunc(obj) : t.isTypeObj(obj)){
obj.vjoClzName = oC;
clzArr[clzArr.length] = obj;
}
}
}
return clzArr;
},

//============
//	Depth first search for multi level wildcast pattern
//============

blackList: {
prototype:	1,
props:			1,
protos:			1,
inherits:		1,
singleton:		1,
satisfies:		1,
satisfiers:		1,
makeFinal:	1,
inits:				1,
createPkg:	1,
needs:			1,
needsLib:		1,
type:			1,
base:			1,
parent:		1
},

bInBlackList: function(name){
//for known vjo extension, ignore them to improve performance
if(this.blackList[name]){
return true;
}
return false;
},

DFS4Def : function(glob, steps, index){
if(index === steps.length){
return [];
}
var t = this,
clzArr = [],
bFunc = (index === steps.length -1),
regex = t.getRegex(steps[index]);

if(glob === window && steps[index] === '**'){
//steps[0] = '**', supposed to match 'window.vjo'
//so, continue with (window.vjo, step[0])
t.appendArr(t.DFS4Def(vjo, steps, index), clzArr, 'vjo');
}else{
try{
for(var oC in glob){
//only if glob[oC] matches steps[index], go next step
if(t.bInBlackList(oC)) {continue;}
if(t.isTypeStr(oC) && oC.match(regex)){
var obj = glob[oC];
if(obj === window || obj === document) {continue;}
if((bFunc ? t.isTypeFunc(obj) : t.isTypeObj(obj))){
if(bFunc){
//last step, post content to clzArr
obj.vjoClzName = oC;
clzArr[clzArr.length] = obj;
}else{
t.appendArr(t.DFS4Def(obj, steps, index + 1), clzArr, oC);
}
}
}

if(steps[index] === '**'){
t.appendArr(t.DFS4Def(obj, steps, index), clzArr, oC);
}
}
}catch(ex){
//IE issue, when runing 'for' on [], will throw ex
}
}
return clzArr;
},

//============
//	Function reflection
//============

/*
* This calc the expr to function name, if input is incorrect, will terminate the wrapIt in wrap.js
*/
expr2func : function(expr){ //<public String expr2func(String)
if(expr && this.isTypeStr(expr)){
var sep =  this.isRegEx(expr) ? '#' : '.';
return expr.substring(expr.lastIndexOf(sep)+1);
}
else{
return '_invalid_';
}
},

/*
*	Find func name array matching expr
*/
findFunc : function(type, func, wl, bl){ //<public Array findFunc(Object, Object, Object, Object)
var t = this, funcArr = [];
for(var oF in type.prototype){
if(t.isTypeStr(oF) && t.isTypeFunc(type.prototype[oF])&&!t.bFiltered(type.vjoClzName, oF, wl, bl) && oF.match(t.getRegex(func))){
funcArr[funcArr.length] = oF;
}
}
return funcArr;
},

bFiltered: function(cName, fName, wl, bl){
if(this.bInList(wl, cName, fName)){
return false;
}else if(this.bShortcut(fName) || this.bInList(bl, cName, fName)){
return true;
}
return false;
},

bInList: function(list, cName, fName){
for(var p in list){
var pair = list[p];
if(pair.length === 2 && pair[0] === cName && pair[1] === fName){
return true;
}
}
return false;
},

getList: function(str){ //<public Array getList(String)
if(!str){
return [];
}
var l = [],
pairs = str.split(',');
for(var p in pairs){
var pair = pairs[p].split('#');
l[l.length] = pair;
}
return l;
},

/*
*To filter the shortcut variables in VJO types
*/
bShortcut: function(name){
return name.match('^[A-Z]+');
},

//============
//	Utility functions
//============
appendArr : function(src, target, prefix){
var i=0, L= src ? src.length : 0;
while(i<L){
src[i].vjoClzName = prefix + '.' + src[i].vjoClzName;
target[target.length]=src[i];
i++;
}
},

isRegEx : function(expr){//<public boolean isRegEx(String)
return expr.indexOf('#') >= 0;
},

//Standard js identifier
IDN : '[a-zA-Z_$][a-zA-Z_$0-9]*',

getRegex : function(str){
if(str === '*' || str === '**'){
return this.IDN;
}else{
return str.replace(/%/g, '.');
}
},

isTypeStr : function(any){
return this.checkType(any, 'string');
},

isTypeFunc : function(any){
return this.checkType(any, 'function');
},

isTypeObj : function(any){
return this.checkType(any, 'object');
},

checkType : function(any, typeStr){
return any?(typeof(any)).toLowerCase() === typeStr:false;
},

escape : function(expr){
return expr.replace(/\\./g, '%');
}
})
.endType();
