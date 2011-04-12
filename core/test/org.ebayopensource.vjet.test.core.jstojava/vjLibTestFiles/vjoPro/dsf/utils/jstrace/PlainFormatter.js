vjo.ctype("vjoPro.dsf.utils.jstrace.PlainFormatter")
.needs("vjoPro.dsf.utils.logging.Formatter")
.inherits("vjoPro.dsf.utils.logging.Formatter")
.protos({
//> private constructs()
constructs: function(){
this.base("vjoPro.dsf.utils.jstrace.PlainFormatter");
},

format: function(lr){
var ctx = lr.params[0],
target = ctx.target || '?',
txt = ctx.type;
txt += ' ';
txt += target;
txt += ' ';
txt += ctx.jsrId || '?';
txt += ' @ ';
txt += ctx.time || '?';
txt += ' delta= ';
txt += ctx.delta || '0';
return txt;
}
})
.endType();
