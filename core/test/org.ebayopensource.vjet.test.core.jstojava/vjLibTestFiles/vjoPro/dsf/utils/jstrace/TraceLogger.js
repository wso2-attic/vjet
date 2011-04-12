vjo.ctype("vjoPro.dsf.utils.jstrace.TraceLogger","T1")
.needs("vjoPro.dsf.utils.logging.Level","L")
.needs("vjoPro.dsf.utils.logging.Logger","LG")
.needs("vjoPro.dsf.utils.logging.LogManager")
.needs("vjoPro.dsf.utils.logging.LogRecord","LR")
.needs("vjoPro.dsf.utils.jstrace.JSTraceLogConfig")
.needs("vjoPro.dsf.utils.logging.RemoteHandler")
.needs("vjoPro.dsf.utils.jstrace.RemoteFormatter")
.needs("vjoPro.dsf.utils.logging.RemoteFormatter", "LRF")
.needs("vjoPro.dsf.utils.logging.MessageHandler")
.protos({

constructs : function(cfg){
	mgr = this.vj$.LogManager.getLogManager();
	mgr.readConfigure(this.vj$.JSTraceLogConfig);
	var t = this; t.props = [];
	if(cfg.type === 'remote'){
		t.setupRemoteLogger(cfg);
	}else if(cfg.type === 'msg'){
		t.setupMsgLogger(cfg);
	}else if(cfg.type === 'eclipse'){
		t.setupEclipseLogger(cfg);
	}else { //default
		t.setupLocalLogger(cfg);
	}
	mgr.readConfigure(t.getConfig(cfg));
	cfg.beforeAct =function(traceLog){ t.logBef(traceLog);};
	cfg.afterAct = function(traceLog){t.logAft(traceLog);};
},

getConfig: function(cfg){
	return {properties:this.props};
},

setProperty: function(name, value){
	if(value){
		this.props[this.props.length] = [name, value];
	}
},
//>public void log(T1)
log: function(traceLog){
	var lr = new this.vj$.LR(this.L.INFO, "");
	lr.setParameters([traceLog]);
	this.vj$.LG.getLogger("vjoPro.dsf.utils.jstrace.TraceLogger").logRecord(lr);
},

//=============
//	Msg Logging
//=============
setupMsgLogger : function(cfg){
cfg.logHandlers = "vjoPro.dsf.utils.logging.MessageHandler";
var t = this;
t.logBef = t.logMsgBef;
t.logAft = t.logMsgAft;
t.beforeMsgId = cfg.beforeMsgId;
t.afterMsgId = cfg.afterMsgId;
t.setProperty("vjoPro.dsf.utils.jstrace.TraceLogger.handlers", 	cfg.logHandlers);
},

logMsgBef: function(traceLog){
this.logMsg(this.beforeMsgId, traceLog);
},

logMsgAft: function(traceLog){
this.logMsg(this.afterMsgId, traceLog);
},

logMsg : function(svcId, traceLog){
var lr = new this.vj$.LR(this.vj$.L.INFO, "");//<LR
lr.setMsgId(svcId);
lr.setParameters([traceLog]);
this.vj$.LG.getLogger("vjoPro.dsf.utils.jstrace.TraceLogger").logRecord(lr);
},

//=============
//	Local Logging
//=============
setupLocalLogger : function(cfg){
//cfg.logHandlers = null; // use default ConsoleHandler
var t = this;
t.logBef = t.log;
t.logAft = t.log;
//Local
t.setProperty("vjoPro.dsf.utils.jstrace.TraceLogger.formatter",	cfg.fmt);
},

//=============
//	Remote Logging
//=============
setupRemoteLogger : function(cfg){
cfg.logHandlers = "vjoPro.dsf.utils.logging.RemoteHandler";
var t = this;
t.logBef = t.log;
t.logAft = t.log;
t.setProperty("vjoPro.dsf.utils.jstrace.TraceLogger.handlers", 	cfg.logHandlers);
t.setProperty("vjoPro.dsf.utils.logging.RemoteHandler.uri", 		cfg.uri);
t.setProperty("vjoPro.dsf.utils.jstrace.RemoteFormatter.guid", 	cfg.guid);
t.setProperty("vjoPro.dsf.utils.jstrace.RemoteFormatter.lifespan",	cfg.life);
t.setProperty("vjoPro.dsf.utils.logging.RemoteHandler.formatter", 	"vjoPro.dsf.utils.jstrace.RemoteFormatter");
},

//=============
//	Eclipse Logging
//=============
setupEclipseLogger : function(cfg){
cfg.logHandlers = "vjoPro.dsf.utils.logging.RemoteHandler";
var t = this;
t.logBef = t.log;
t.logAft = t.log;
t.setProperty("vjoPro.dsf.utils.jstrace.TraceLogger.handlers", 	cfg.logHandlers);
t.setProperty("vjoPro.dsf.utils.logging.RemoteHandler.uri", 		cfg.uri);
t.setProperty("vjoPro.dsf.utils.logging.RemoteHandler.formatter", 	"vjoPro.dsf.utils.logging.RemoteFormatter");
}
})
.endType();
