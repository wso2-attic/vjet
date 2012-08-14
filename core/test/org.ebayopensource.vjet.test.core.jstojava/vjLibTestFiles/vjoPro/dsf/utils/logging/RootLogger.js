//< needs (vjoPro.dsf.utils.logging.LogRecord)
vjo.ctype("vjoPro.dsf.utils.logging.RootLogger")
.needs("vjoPro.dsf.utils.logging.LogManager")

.inherits("vjoPro.dsf.utils.logging.Logger")
.protos({
constructs: function(defaultLevel){
this.base("");
this.setLevel(defaultLevel);
},
//>public void logRecord(LogRecord)
logRecord: function(lr) {
// Make sure that the global handlers have been instantiated.
this.initializeGlobalHandlers();
this.base.logRecord(lr);
},

addHandler: function(h) {
this.initializeGlobalHandlers();
this.base.addHandler(h);
},

removeHandler: function(h) {
this.initializeGlobalHandlers();
this.base.removeHandler(h);
},

//> public Array getHandlers()
getHandlers: function() {
this.initializeGlobalHandlers();
return this.base.getHandlers();
},

//> public void initializeGlobalHandlers()
initializeGlobalHandlers: function(){
vjoPro.dsf.utils.logging.LogManager.getLogManager().initializeGlobalHandlers();
}
})
.endType();
