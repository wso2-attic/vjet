vjo.ctype("vjoPro.dsf.utils.logging.ConsoleHandler")
.needs("vjoPro.dsf.utils.logging.Handler")
.needs("vjoPro.dsf.EventDispatcher")
.needs("vjoPro.dsf.window.utils.VjWindowUtils")
.inherits("vjoPro.dsf.utils.logging.Handler")
.protos({

ED: vjoPro.dsf.EventDispatcher,
W: vjoPro.dsf.window.utils.VjWindowUtils,

//> private constructs()
constructs: function(){
var t = this;
t.base("vjoPro.dsf.utils.logging.ConsoleHandler");
t.pane = document.createElement('div');
document.body.appendChild(t.pane);
t.pane.id = 'tracePane';
var s = t.pane.style;
s.width = '400px';
s.height = '250px';
s.position = 'absolute';
s.zIndex = '1000';
s.right ='0';
s.bottom = '0';
s.border = '2px dashed gray';
s.opacity = ".5";
s.filter = "alpha(opacity=50)";

t.bar = document.createElement('div');
t.bar.innerHTML = 'Drag here to move';
var bs = t.bar.style;
bs.cursor = 'move';
bs.backgroundColor = 'lightblue';
bs.height = '20px';
t.pane.appendChild(t.bar);

t.cnt = document.createElement('div');
var cs = t.cnt.style;
cs.backgroundColor = 'black';
cs.overflow = 'scroll';
cs.height = '230px';
t.pane.appendChild(t.cnt);

window.onscroll = function(event) {
return t.resetConsolePos();
};

t.movable = false;
t.ED.addEventListener(document.body, 'mousedown', 	t.beginDrag,		t);
t.ED.addEventListener(document.body, 'mouseup',		t.endDrag,			t);
t.ED.addEventListener(document.body, 'mousemove', 	t.doDrag, 			t);
},

beginDrag: function(e){
var t = this,
handle = e.target || e.srcElement;
while (handle !== document.body && handle !== t.bar) {
handle = handle.parentNode;
}
if (handle === t.bar) {
t.movable = true;
t.offsetX = t.W.eventLeft(e) - t.pane.offsetLeft;
t.offsetY = t.W.eventTop(e) - t.pane.offsetTop;
return false;
}
},

//> private void endDrag()
endDrag: function(){
this.movable = false;
},

doDrag: function(e) {
var t = this;
if (t.movable) {
var s = t.pane.style;
s.left = t.W.eventLeft(e) -  t.offsetX + "px";
s.top = t.W.eventTop(e)  - t.offsetY + "px";
return false;
}
},

//> private void resetConsolePos()
resetConsolePos : function(){
var s = this.pane.style;
s.top = document.documentElement.scrollTop
+ vjoPro.dsf.window.utils.VjWindowUtils.getBrowserWindowHeight()
- this.pane.offsetHeight
+ 'px';
},


innerPublish: function(lr){
var line = '{',
div = document.createElement('div'),
s = div.style;
s.borderBottom = '1px dashed gray';
s.fontSize = 'x-small';
s.color = 'white';

line +=  this.getFormatter().format(lr);
line += '}';
div.innerHTML = line;

this.cnt.appendChild(div);
}
})
.endType();
