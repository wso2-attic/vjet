vjo.ctype("vjoPro.dsf.utils.logging.Handler")
.needs("vjoPro.dsf.utils.logging.Level")
.needs("vjoPro.dsf.utils.logging.SimpleFormatter")
.needs("vjoPro.dsf.utils.logging.LogManager")
//>needs(vjoPro.dsf.utils.logging.Formatter)
.protos({

name : null, //<Object
constructs: function(name){
this.configure(name);
this.name = name;//help to debug
},

configure: function(hName) {
var t = this,
manager = this.vj$.LogManager.getLogManager();

this.setLevel(manager.getLevelProperty(hName + ".level", this.vj$.Level.INFO));
this.setFormatter(manager.getFormatterProperty(hName + ".formatter", new this.vj$.SimpleFormatter()));
},

setLevel: function(level){
this.level = level;
},

//> private void getLevel()
getLevel: function(){
return;
},

setFormatter: function(formatter){
this.formatter = formatter;
},

//> Formatter getFormatter()
getFormatter: function(){
return;
},

//publish
OFFVALUE:	this.vj$.Level.OFF.intValue(),

isLoggable: function(record) {
var levelValue = this.getLevel().intValue();
if (record.getLevel().intValue() < levelValue || levelValue == this.OFFVALUE) {
return false;
}
//extend filter part here
return true;
},

publish: function(lr){
if(this.isLoggable(lr)){
this.innerPublish(lr);
}
},

innerPublish: function(lr){
//to be extended in children
}
})
.endType();
