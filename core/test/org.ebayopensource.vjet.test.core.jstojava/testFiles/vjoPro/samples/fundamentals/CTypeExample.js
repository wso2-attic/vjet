vjo.ctype('vjoPro.samples.fundamentals.CTypeExample')
//snippet.protos.end
.props({
initialValue: undefined, //< public int

getInitialValue: function(){	//< public void getInitialValue()
window.document.writeln('Initial Value : '+this.initialValue);
}
})
//snippet.staticinitialization.end
//snippet.props.end
//snippet.protos.begin
.protos({
message: undefined, 		//< protected String

//> public void constructs(String msg)
constructs: function(msg) {
this.message = msg;
},

//> public void showMessage()
showMessage:function(){
window.document.writeln('Message ' + this.message);
}
})
//snippet.protos.end
//snippet.staticinitialization.begin
.inits(
function(){
/* use this section for static initialization */
this.initialValue = 100;
}
)
//snippet.props.begin
//snippet.protos.begin
.endType();
//snippet.protos.end
//snippet.props.end
//snippet.staticinitialization.end
//snippet.implementation.end
