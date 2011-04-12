vjo.ctype("vjoPro.dsf.utils.logging.MessageHandler")
.needs("vjoPro.dsf.utils.Handlers")
.needs("vjoPro.dsf.utils.logging.Handler")
.inherits("vjoPro.dsf.utils.logging.Handler")
.protos({
H:	vjoPro.dsf.utils.Handlers,

//> private constructs()
constructs: function(){
var t = this;
t.base("vjoPro.dsf.utils.logging.MessageHandler");
},

innerPublish: function(lr){
var msg = this.H.newMsg(lr.getMsgId());
msg.clientContext = {'logRecord': lr};
this.H.handle(msg);
}
})
.endType();
