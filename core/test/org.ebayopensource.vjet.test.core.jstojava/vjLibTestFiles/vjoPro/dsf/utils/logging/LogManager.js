vjo.ctype("vjoPro.dsf.utils.logging.LogManager")
.needs("vjoPro.dsf.utils.logging.LogNode")
.needs("vjoPro.dsf.utils.logging.Level","Level")
.needs("vjoPro.dsf.utils.logging.DefaultConfig")
.needs("vjoPro.dsf.utils.logging.RootLogger")
.needs("vjoPro.dsf.utils.logging.Logger","Logger")
.needs("vjoPro.dsf.utils.reflection.Reflection","Ref")
.protos({

L:				vjoPro.dsf.utils.logging.Level,
R:				vjoPro.dsf.utils.reflection.Reflection,
LG:				vjoPro.dsf.utils.logging.Logger,
LN:				vjoPro.dsf.utils.logging.LogNode,

//> constructs()
constructs: function(){
var t = this;
t.root = new t.LN(null);//root node of tree
t.rootLogger = new vjoPro.dsf.utils.logging.RootLogger(1);//root logger
t.addLogger(t.rootLogger);
},

//==========
//Logger manamgement
//==========

loggers:		[],//idx--logger name, value--logger instance

getLoggerNames: function(){
var names = [];
for(var p in this.loggers){
names[names.length] = p;
}
return names;
},

getLogger: function(name){
return this.loggers[name];
},

addLogger: function(logger){
var t = this,
lgName = logger.getName();
if(lgName === null){//don't use if(!lgName) here, the rootLogger named ''
throw new Error("Bad logger name: " + name + " for LogManager to handle");
}
var old = t.loggers[lgName];
if (old) {
// We already have a registered logger with the given name.
return false;
}

//add
t.loggers[lgName] = logger;

// Apply any initial level defined for the new logger.
var level = t.getLevelProperty(lgName + ".level", null);
if (level) {
logger.setLevel(level);
}

// Do we have a per logger handler?
t.setHandler(lgName, true); // bind handler if found
// do we have per logger handlers

// If any of the logger's parents have levels defined,
// make sure they are instantiated.
var ix = 1;
while(lgName && lgName.length > ix) { //in most cases, it should be true, leaving false to rootLogger
var ix2 = lgName.indexOf(".", ix);
if (ix2 < 0) {
break;
}
var pname = lgName.substring(0, ix2);
if (t.getProperty(pname + ".level")) {
// This pname has a level definition. Make sure it exists.
t.LG.getLogger(pname);
}
// While we are walking up the tree I can check for our
// own root logger and get its handlers initialized too with
// the same code
t.setHandler(pname, false);//will not bind handler here, leave it to the call to the addLogger(pname)
ix = ix2 + 1;
}

// Find the new node and its parent.
var node = t.findNode(lgName),
parent = null,
nodep = node.parent;
node.logger = logger;
while (nodep) {
if (nodep.logger) {
parent = nodep.logger;
break;
}
nodep = nodep.parent;
}

if (parent) {
t.doSetParent(logger, parent);
}
// Walk over the children and tell them we are their new parent.
node.walkAndSetParent(logger);

return true;
},

//
//1. check whether lgName.handler exists in props
//2. parse the text to name array
//3. get handler inst from name
//4. check handler.level from props and set
//5. make sure a logger named lgName exists
//6. check useParent for lgName
//7. bind the handlers to logger
//
setHandler: function(lgName, bBindHandler){
var t = this,
hTxt = lgName + ".handlers";
if (t.getProperty(hTxt)) {
var hNames = t.parseClassNames(hTxt);
for (var i = 0; i < hNames.length; i++) {
var hName = hNames[i];
try {
var h = t.R.getVJOInstance(hName);
try {
// Check if there is a property defining the
// handler's level.
var levs = t.getProperty(hName + ".level");
if (levs) {
h.setLevel(t.L.parse(levs));
}
} catch (ex) {
throw new Error("Can't set level for "
+ hName);
// Probably a bad level. Drop through.
}
var lgr = t.LG.getLogger(lgName),//make sure it exists
useParent = t.getBooleanProperty(lgName
+ ".useParentHandlers", true);
lgr.setUseParentHandlers(useParent);
// Add this Handler to the logger
if(bBindHandler){
lgr.addHandler(h);
}
} catch (ex) {
throw new Error("Can't load log handler \""
+ hName + "\"" + ex);
}
}
}
},

findNode: function(name) {
var t = this;
if (!name || name === "") {
return t.root;
}
var node = t.root;
while (name.length > 0) {
var ix = name.indexOf("."),
head;
if (ix > 0) {
head = name.substring(0, ix);
name = name.substring(ix + 1);
} else {
head = name;
name = "";
}
if (!node.children) {
node.children = [];
}
var child = node.children[head];
if (!child) {
child = new t.LN(node);
node.children[head] = child;
}
node = child;
}
return node;
},

doSetParent: function(logger, parent) {
logger.setParent(parent);
},

initializeGlobalHandlers: function() {
var t = this;
if (t.bInitializedGlobalHandlers) {
return;
}

t.bInitializedGlobalHandlers = true;
var hNames = t.parseClassNames("handlers");
for (var i = 0; i < hNames.length; i++) {
var hName = hNames[i];
try {
var h = t.R.getVJOInstance(hName);
try {
// Check if there is a property defining the
// handler's level.
var levs = t.getProperty(hName + ".level");
if (levs) {
h.setLevel(this.L.parse(levs));
}
} catch (ex) {
throw new Error("Can't set level for " + hName);
// Probably a bad level. Drop through.
}
t.rootLogger.addHandler(h);
} catch (ex) {
throw new Error("Can't load log handler \"" + hName
+ "\"" + ex);
}
}
},

parseClassNames: function(propertyName) {
var hands = this.getProperty(propertyName);
if (!hands) {
return [];
}
hands = this.trimStr(hands);
var ix = 0,
result = [];
while (ix < hands.length) {
var end = ix;
while (end < hands.length) {
if (hands.charAt(end) === ' ') {
break;
}
if (hands.charAt(end) === '&') {
break;
}
end++;
}
var hand = hands.substring(ix, end);
ix = end + 1;
hand = this.trimStr(hand);
if (hand.length === 0) {
continue;
}
result[result.length] = hand;
}
return result;
},

//=========
//configure
//=========

props:	[],

readConfigure: function(config){
if(!config){
return;
}
for(var i=0; i<config.properties.length; i++){
var p = config.properties[i],
key = p[0],
val = p[1];
this.props[key] = val;
}
this.setLevelsOnExistingLoggers();
this.bInitializedGlobalHandlers = false;
},

setLevelsOnExistingLoggers: function() {
for(var key in this.props){
if (key.length < 6 || key.substr(key.length - 6, 6) !== ".level") {
// Not a level definition.
continue;
}
var ix = key.length - 6,
name = key.substring(0, ix);
var level = this.getLevelProperty(key, null);
if (!level) {
throw new Error("Bad level value for property: " + key);
}
var l = this.getLogger(name);
if (!l) {
continue;
}
l.setLevel(level);
}
},

trimStr: function(str){
return str.replace(/' '/g, '');
},

getProperty: function(name) {
return this.props[name];
},

getStringProperty: function(name, defaultValue) {
var val = this.getProperty(name);
if (!val) {
return defaultValue;
}
return this.trimStr(val);
},

getBooleanProperty: function(name, defaultValue) {
var val = this.getProperty(name);
if (!val) {
return defaultValue;
}
val = val.toLowerCase();
if (val === "true" || val === "1") {
return true;
} else if (val === "false" || val === "0") {
return false;
}
return defaultValue;
},

getLevelProperty: function(name, defaultValue) {
var val = this.getProperty(name);
if (!val) {
return defaultValue;
}
try {
return this.L.parse(this.trimStr(val));
} catch (ex) {
}
return defaultValue;
},

getIntProperty: function(name, defaultValue) {
var val = this.getProperty(name);
if (!val) {
return defaultValue;
}
try {
return parseInt(this.trimStr(val), 10);
} catch (ex) {
}
return defaultValue;
},

getFormatterProperty: function(name, defaultValue) {
var val = this.getProperty(name);
try {
if (val) {
return this.R.getVJOInstance(val);
}
} catch (ex) {
}
return defaultValue;
}
})
.props({
defaultLevel:	vjoPro.dsf.utils.logging.Level.INFO,
manager:		null, //new vjoPro.dsf.utils.logging.LogManager(),//< @SUPRESSTYPECHECK
bConfigured:	false,

configure: function(){
if(this.bConfigured){
return;
}
//read & do configuration
this.manager.readConfigure(vjoPro.dsf.utils.logging.DefaultConfig);
this.bConfigured = true;
},

getLogManager: function(){//<public LogManager getLogManager()
this.configure();
return this.manager;
}
})
.endType();
