vjo.ctype("vjoPro.dsf.utils.logging.LogRecord")
//>needs(vjoPro.dsf.utils.logging.Level)
.protos({
level: null,//<Level
loggerName: null,//<String
msg: null,//<String
millis: null,//<long
params: null,//<Array
srcClzName: null,//<String
srcFuncName: null,//<String
msgId: null,//<String
	
//> public constructs(Level, String...)
constructs: function(level, msg){
var t = this;
t.bLR = true;//for logger.log() judgement
t.level = level;
t.msg = msg;
t.millis = new Date().getTime();
},

setMsg: function(msg){
this.msg = msg;
},

//> public String getMsg()
getMsg: function(){
return this.msg;
},

setParameters: function(params){ //<public void setParameters(Array)
this.params = params;
},

setSrcClzName: function(name){
this.srcClzName = name;
},

setSrcFuncName: function(name){
this.srcFuncName = name;
},

setMillis: function(millis){
this.millis = millis;
},

setLoggerName: function(name){
this.loggerName = name;
},

setLevel: function(level){
this.level = level;
},

//> public Level getLevel()
getLevel: function(){
return this.level;
},

//> public String getLoggerName()
getLoggerName: function(){
return this.loggerName;
},

//> public long getMillis()
getMillis: function(){
return this.millis;
},

//> public Array getParameters()
getParameters: function(){
return this.params || [];
},

//> public String getSrcClzName()
getSrcClzName: function(){
return this.srcClzName || '';
},

//> public String getSrcFuncName()
getSrcFuncName: function(){
return this.srcFuncName || '';
},

setMsgId: function(msgId){ //<public void setMsgId(String)
this.msgId = msgId;
},

//> public String getMsgId()
getMsgId: function(){
return this.msgId || '';
}

})
.endType();
