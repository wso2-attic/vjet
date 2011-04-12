vjo.ctype("vjoPro.dsf.utils.logging.SimpleFormatter")
.inherits("vjoPro.dsf.utils.logging.Formatter")
.protos({
//> public constructs()
constructs: function(){
this.base("vjoPro.dsf.utils.logging.SimpleFormatter");
},

format: function(lr){
if(!lr){
return 'Null LogRecord!';
}
var txt,
sep = '\n',
indent = '          '; //10 chars
txt =
'Level:    ' + lr.level.name + sep +
'Msg:      ' + lr.msg + sep +
'Src Clz:  ' + lr.srcClzName + sep +
'Src Func: ' + lr.src + sep +
'Millis:   ' + lr.millis + sep +
'Params:   ' + this.getParamsTxt(lr.params, indent) + sep +
'Error:    ' + this.getErrorTxt(lr.Error, indent);
return txt;
},

getParamsTxt: function(params, indent){
var txt;
if(params){
for(var p in params){
txt = '\n';
txt += indent;
txt += p;
txt += ' : ';
txt += params[p];
}
}else{
txt = 'Null';
}
return txt;
},

getErrorTxt: function(err, indent){
var txt;
if(err){
txt = '\n' + indent;
txt += 'Name    : ' + err.name;
txt += '\n' + indent;
txt += 'Msg     : ' + err.message;
txt += '\n' + indent;
txt += 'FileName: ' + err.fileName;
txt += '\n' + indent;
txt += 'Line    : ' + err.lineNumber;
txt += '\n' + indent;
txt += 'Stack   : ' + err.stack;
}else{
txt = 'Null';
}
return txt;
}
})
.endType();
