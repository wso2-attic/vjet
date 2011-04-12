vjo.ctype("vjoPro.dsf.utils.jstrace.RemoteFormatter")
.needs("vjoPro.dsf.utils.logging.LogManager")
.inherits("vjoPro.dsf.utils.logging.Formatter")
.protos({
//> private constructs()
constructs: function(){
this.base("vjoPro.dsf.utils.jstrace.RemoteFormatter");
},

format: function(lr){
var ctx = lr.params[0];
if(!ctx){
return;
}
var t = this,
mgr = vjoPro.dsf.utils.logging.LogManager.getLogManager(),
cname = "vjoPro.dsf.utils.jstrace.RemoteFormatter",
guid = mgr.getStringProperty(cname + '.guid', 'default'),
lifespan = mgr.getStringProperty(cname + '.lifespan', '90'),
loc = document.location.href,
jsrId = ctx.jsrId || '?',
evtType = ctx.type || '?',
evtTime = ctx.time || '?',
expr = ctx.expr || '?',
evtFunc = ctx.target || '?',
evtParams = ctx.args ? t.getParamStr(ctx.args):'void',
execTime = ctx.delta || 0,
logSep = '@_@',
logItem =
loc + logSep +
guid + logSep +
evtType + logSep +
evtTime + logSep +
expr + logSep +
evtFunc + logSep +
lifespan + logSep +
jsrId + logSep +
evtParams + logSep +
execTime;
return logItem;
},

getParamStr : function(args){
var str = args[0];
for(var i = 1;i<args.length;i++){
str+=',';
str+=args[i];
}
return str;
}
})
.endType();
