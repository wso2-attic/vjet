vjo.ctype("vjoPro.dsf.utils.logging.RemoteFormatter")
.needs("vjoPro.dsf.Json")
.inherits("vjoPro.dsf.utils.logging.Formatter")
.protos({
//> private constructs()
constructs: function(){
this.base("vjoPro.dsf.utils.logging.RemoteFormatter");
},

format: function(lr){
return JSON.stringify(lr);
}
})
.endType();
