vjo.ctype("vjoPro.dsf.utils.logging.Level")
.protos({

name:null,//<String
value:0,//<int

/**
*	@access public
*	@param {String} name
*	@param {int} value
*/
//>public constructs (String name, int value)
constructs: function(name, value){
this.name = name;
this.value = value;
},

getName: function(){
return this.name;
},

intValue: function(){
return this.value;
}
})
.props({
OFF:		null, //new vjoPro.dsf.utils.logging.Level('OFF', 		9999),//< @SUPRESSTYPECHECK
SEVERE:		null, //new vjoPro.dsf.utils.logging.Level('SEVERE', 	1000),//< @SUPRESSTYPECHECK
WARNING:	null, //new vjoPro.dsf.utils.logging.Level('WARNING',	900),//< @SUPRESSTYPECHECK
INFO:		null, //new vjoPro.dsf.utils.logging.Level('INFO',		800),//< @SUPRESSTYPECHECK
CONFIG:		null, //new vjoPro.dsf.utils.logging.Level('CONFIG',	700),//< @SUPRESSTYPECHECK
FINE:		null, //new vjoPro.dsf.utils.logging.Level('FINE',		500),//< @SUPRESSTYPECHECK
FINER:		null, //new vjoPro.dsf.utils.logging.Level('FINER',	400),//< @SUPRESSTYPECHECK
FINEST:		null, //new vjoPro.dsf.utils.logging.Level('FINEST',	300),//< @SUPRESSTYPECHECK
ALL:		null, //new vjoPro.dsf.utils.logging.Level('ALL',		-9999),//< @SUPRESSTYPECHECK

//have to do init for known like this, due to the diference between java & VJO
known:		null,
setKnown: function(){
if(!this.known){
this.known = [
this.OFF,
this.SEVERE,
this.WARNING,
this.INFO,
this.CONFIG,
this.FINE,
this.FINER,
this.FINEST,
this.ALL
];

}
},
/**
*	@access public
*	@param {String} name
*/
//>public Object parse (String name)
parse: function(name){
this.setKnown();
var l = null;
for(var i=0; i<this.known.length; i++){
if(this.known[i].getName() === name){
l = this.known[i];
break;
}
}
if(!l){
var v = parseInt(name, 10);
if(v){
for(var j=0; j<this.known.length; j++){
if(this.known[j].intValue() === v){
l = this.known[j];
break;
}
}
if(!l){
l = new this(name, v);
this.known[this.known.length] = l;
}
}
}
if(!l){
throw new Error("Bad level \"" + name + "\"");
}
return l;
}
})
.endType();
