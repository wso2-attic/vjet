vjo.ctype("vjoPro.dsf.utils.logging.Logger")
.needs("vjoPro.dsf.utils.logging.Level","L")
.needs("vjoPro.dsf.utils.logging.LogRecord","LR")
.needs("vjoPro.dsf.utils.logging.LogManager")
.protos({
parent: null,//<Logger
kids: null,//<Array
levelValue:0, //<int
constructs: function(name){
var t = this;
t.name = name;
t.levelValue = t.L.INFO.intValue();
t.levelObj = null;
t.kids = [];
},

//> public void getName()
getName: function(){return;
},

//===================
//	Handlers
//===================

handlers:		null, //leave it null here, or else the init [] will be shared in any logger & root logger...
handlersCopy: 	null,

addHandler: function(handler){
var t = this;
if(!handler){
return;
}
if (!t.handlers) {
t.handlers = [];
}
t.handlers[t.handlers.length] = handler;
t.makeCopy();
},

//> private void makeCopy()
makeCopy: function(){
var t = this;
t.handlersCopy = [];
for(var h in t.handlers){
t.handlersCopy[t.handlersCopy.length] = t.handlers[h];
}
},

//> protected Array getHandlers()
getHandlers: function() {
if (!this.handlers) {
return [];
}
// we already have the copy made for us
return this.handlersCopy;
},

removeHandler: function(handler){
if (!handler) {
return;
}
var t = this;
if (!t.handlers) {
return;
}
for(var i=0; i<t.handlers.length; i++){
if(t.handlers[i] === handler){
t.handlers.splice(i, 1);
break;
}
}
t.makeCopy();
},

//===============
//	Parent
//===============

//> private Logger getParent()
getParent: function(){
return this.parent;
},

setParent: function(parent) {
if (!parent) {
throw new Error("Parent for logger is null");
}
this.doSetParent(parent);
},

//standalone method for future anonymous logger
doSetParent: function(nParent){
var t = this;
if (t.parent) {
// assert parent.kids != null;
for (var i=0;i<t.parent.kids.length;i++) {
var kid = t.parent.kids[i];
if (kid === t) {
t.parent.kids.splice(i, 1);
break;
}
}
// We have now removed ourself from our parents' kids.
}

// Set our new parent.
t.parent = nParent;
if (!t.parent.kids) {
t.parent.kids = [];
}
t.parent.kids[t.parent.kids.length] = this;

// As a result of the reparenting, the effective level
// may have changed for us and our children.
t.updateEffectiveLevel();
},

//===================
//	Level
//===================

//> public void getLevel()
getLevel: function() {

},

setLevel: function(level){
this.levelObj = level;
this.updateEffectiveLevel();
},

//> private void updateEffectiveLevel()
updateEffectiveLevel: function() {
// Figure out our current effective level.
var t = this,
newLevelValue;
if (t.levelObj) {
newLevelValue = t.levelObj.intValue();
} else {
if (t.parent) {
newLevelValue = t.parent.levelValue;
} else {
// This may happen during initialization.
newLevelValue = t.L.INFO.intValue();
}
}

// If our effective value hasn't changed, we're done.
if (t.levelValue === newLevelValue) {
return;
}

t.levelValue = newLevelValue;

// Recursively update the level on each of our kids.
if (t.kids) {
for (var i = 0; i < t.kids.length; i++) {
var kid = t.kids[i];
if (kid) {
kid.updateEffectiveLevel();
}
}
}
},

useParentHandlers: true,

setUseParentHandlers: function(useParentHandlers) {
this.useParentHandlers = useParentHandlers;
},

//> private boolean getUseParentHandlers()
getUseParentHandlers: function() {
return this.useParentHandlers;
},

//==logging
OFFVALUE:	vjoPro.dsf.utils.logging.Level.OFF.intValue(),

//Lowest level
//>public void logRecord(LR)
logRecord: function(lr) {
var t = this;
if (lr.getLevel().intValue() < t.levelValue || t.levelValue == t.OFFVALUE) {
return;
}

lr.setLoggerName(t.name);
// Post the LogRecord to all our Handlers, and then to
// our parents' handlers, all the way up the tree.

var logger = t;
while (logger) {

// getHandlers is now much quicker since we already
// have
// the copy made for us

var targets = logger.getHandlers();

if (targets) {
for (var i = 0; i < targets.length; i++) {
targets[i].publish(lr);
}
}

if (!logger.getUseParentHandlers()) {
break;
}

logger = logger.getParent();
}
},

//> private void log()
//> private void log(LR)
//> private void log(L, String, Object...)
log: function(){
var args = arguments,
len = args.length,
lr = null;
if(len === 1 && args[0].bLR){
lr = args[0];
}
if(len >= 2){
lr = new this.vj$.LR(args[0], args[1]);
}
if(len === 3){
this.checkVariant(lr, args[2]);
}
//ignore all invalid cases...
if(lr){
this.logRecord(lr);
}
},

checkVariant: function(lr, arg){
var type = typeof(arg);
if(type === 'string'){
//string
lr.setParameters([arg]);
}else{// if(type === 'object'){
if(arg instanceof Array){
//array
lr.setParameters(arg);
}else if(arg instanceof Error){
lr.setError(arg);
}
}
},

//> private void logP()
//> private void logP(L, Object, Object, String, Object...)
logP: function(){
var args = arguments,
len = args.length,
lr = null;
if(len >= 4){
lr = new this.vj$.LR(args[0], args[3]);
lr.setSrcClzName(args[1]);
lr.setSrcFuncName(args[2]);
}
if(len === 5){
this.checkVariant(lr, args[4]);
}
//ignore all invalid cases...
if(lr){
this.logRecord(lr);
}
},

//config(msg)
//fine(msg)
//finer(msg)
//finest(msg)
//info(msg)
//severe(msg)
//warning(msg)
//error(clz, func, err)
config: function(msg){
this.log(this.vj$.L.CONFIG, msg);
},

fine: function(msg){
this.log(this.vj$.L.FINE, msg);
},

finer: function(msg){
this.log(this.vj$.L.FINER, msg);
},

finest: function(msg){
this.log(this.vj$.L.FINEST, msg);
},

info: function(msg){
this.log(this.vj$.L.INFO, msg);
},

severe: function(msg){
this.log(this.vj$.L.SEVERE, msg);
},

warning: function(msg){
this.log(this.vj$.L.WARNING, msg);
},

error: function(clz, func, err){
this.logP(this.vj$.L.FINER, clz, func, 'Error', err);
},


//entering(clz, func)
//entering(clz, func, param)
//entering(clz, func, params)
//> public void entering()
entering: function(){
var args = arguments,
len = args.length,
lr = null;
if(len >= 2){
lr = new this.vj$.LR(this.vj$.L.FINER, 'Entry');
lr.setSrcClzName(args[0]);
lr.setSrcFuncName(args[1]);
}
if(len === 3){
this.checkVariant(lr, args[2]);
}
if(lr){
this.logRecord(lr);
}
},

//exiting(clz, func)
//exiting(clz, func, result)
//> public void exiting()
exiting: function(){
var args = arguments,
len = args.length,
lr = null;
if(len >= 2){
lr = new this.vj$.LR(this.vj$.L.FINER, 'Exit');
lr.setSrcClzName(args[0]);
lr.setSrcFuncName(args[1]);
}
if(len === 3){
lr.setParameters([args[2]]);
}
if(lr){
this.logRecord(lr);
}
}
})
.props({
getLogger: function(name) { //<public Logger getLogger(String)
name = name || ''; //by default, return root logger
var manager = vjoPro.dsf.utils.logging.LogManager.getLogManager(),
result = manager.getLogger(name);
if (!result) {
result = new vjoPro.dsf.utils.logging.Logger(name);
manager.addLogger(result);
result = manager.getLogger(name);
}
return result;
}
})
.endType();
