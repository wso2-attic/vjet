vjo.ctype("vjoPro.dsf.utils.logging.RemoteHandler") //<public dynamic
.needs("vjoPro.dsf.utils.logging.LogManager")
.inherits("vjoPro.dsf.utils.logging.Handler")
.protos({
M:	vjoPro.dsf.utils.logging.LogManager,
ifrm: null, //<HTMLIFrameElement

//> constructs()
constructs: function(){
var t = this;
var mgr = t.M.getLogManager(),
cname = "vjoPro.dsf.utils.logging.RemoteHandler";
t.base(cname);

t.setupIfrm();

t.uri = mgr.getStringProperty(cname + '.uri', '');
t.writeBuf = '';
t.readBuf = '';
},

//> void setupIfrm()
setupIfrm: function(){
var t = this;
t.ifrm = document.createElement('iframe'); //<<HTMLIFrameElement
var s = t.ifrm.style;
s.width = '1px';
s.height = '1px';
s.visibility = 'hidden';
s.position = 'absolute';
s.left = '-1600px';

document.body.appendChild(t.ifrm);
},

//> void removeIfrm()
removeIfrm: function(){
document.body.removeChild(this.ifrm);
},

innerPublish: function(lr){
var t = this;
t.writeBuf += t.getFormatter().format(lr)
+ t.M.getLogManager().getStringProperty("vjoPro.dsf.utils.logging.RemoteHandler.lineSeparater");
if(!t.logTimer){
t.logTimer = window.setInterval(function(){t.post2Remote();}, 5000);
}
},

//> HTMLIFrameElement getIfrmDoc()
getIfrmDoc: function(){
var t = this,
ifrmDoc;
if(t.ifrm.contentDocument){
ifrmDoc = t.ifrm.contentDocument;
}else{
ifrmDoc = t.ifrm.contentWindow.document; //<@SUPRESSTYPECHECK
}
return ifrmDoc;
},

//> void post2Remote()
post2Remote : function(){
var t = this;
if(t.writeBuf.length > 0 || t.readBuf.length > 0){
//Anyway, we have sth to send

var ifrmDoc = null;
var noErr = false;
try{
ifrmDoc = t.getIfrmDoc();
ifrmDoc.open();
noErr = true;
t.readBuf = ''; // last send is done without err
}catch(ex){
//a send action is failed, we can't open the ifrm
//try again, if still fail, ignore...
if(ex.description && ex.description.indexOf('Access is denied') >= 0){
//IE, local file cannot access changed iframe content
//in this case, we should clear the buf
t.readBuf = '';
}
try{
t.removeIfrm();
t.setupIfrm();
ifrmDoc = t.getIfrmDoc();
ifrmDoc.open();
noErr = true;
}catch(x){
noErr = false;
}
}
if(noErr && ifrmDoc){
t.readBuf += t.writeBuf;
t.writeBuf = '';
if(t.readBuf.length === 0){
//this hit may be a check after a successful post, no need to post another time.
ifrmDoc.close();
t.clearTimer();
return;
}
var form = '<form id="rhForm" method="post" action="'
+ t.uri+'"><textarea name="'
+ t.M.getLogManager().getStringProperty("vjoPro.dsf.utils.logging.RemoteHandler.contentTag") + '">'
+ t.readBuf + '</textarea></form>';
ifrmDoc.write(form);
ifrmDoc.close();
window.setTimeout(
function(){
ifrmDoc.getElementById("rhForm").submit();
},
1000);
}
}else{
//Nothing to post, stop the timer
t.clearTimer();
}
},

//> void clearTimer()
clearTimer: function(){
window.clearInterval(this.logTimer);
this.logTimer = null;
}
})
.endType();
