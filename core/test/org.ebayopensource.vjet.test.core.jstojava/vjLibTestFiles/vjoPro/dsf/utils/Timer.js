vjo.ctype("vjoPro.dsf.utils.Timer")
.needs("vjoPro.dsf.utils.Object")
.protos({
timer : null,
isRunning : false,
interval : null,

onTick : function(){},
onStart : null, //<Function
onStop : null, //<Function

/**
* Constructs a Timer object with default interval time.
*
* @constructor
* @param {int} interval
*        a default interval time
*/
//> public void constructs(int);
constructs : function(intvl) {
this.interval = intvl;
},

/**
* Re-sets the interval time of the timer
*
* @param {int} interval
*        an interval time to be set
*/
//> public void setInterval(int);
setInterval : function(ms) {
var t = this;
if (t.isRunning){
window.clearInterval(t.timer);
}
t.interval = ms;
// TODO -- adapt hitch function .. think ej2 has utility for this as well
if(t.isRunning){
t.setInt();
//t.timer = window.setInterval(vjoPro.dsf.utils.Object.hitch(t, "onTick"), t.interval);
}
//if (t.isRunning) timer = window.setInterval(dojo.lang.hitch(t, "onTick"), t.interval);
},

/**
* Invokes onStart event handler and Starts the timer.
*/
//> public void start();
start : function() {
var t = this;
if (typeof t.onStart == "function"){
t.onStart();
}
t.isRunning = true;
t.setInt();
//t.timer = window.setInterval(vjoPro.dsf.utils.Object.hitch(t, "onTick"), t.interval);
},

/**
* Stops the timer and invokes onStop handler.
*/
//> public void stop();
stop : function(){
var t = this;
if (typeof t.onStop == "function"){
t.onStop();
}
t.isRunning = false;
window.clearInterval(t.timer);
},

//> public void setInt();
setInt : function(){
var t = this;
t.timer = window.setInterval(vjoPro.dsf.utils.Object.hitch(t, "onTick"), t.interval);
}
})
.endType();

