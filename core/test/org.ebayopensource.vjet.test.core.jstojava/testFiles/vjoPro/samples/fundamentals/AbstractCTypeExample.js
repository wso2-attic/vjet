vjo.ctype('vjoPro.samples.fundamentals.AbstractCTypeExample')
.props({
initialValue: undefined, //< public int

getInitialValue: function(){	//< public void getInitialValue()
window.document.writeln('Initial Value : '+initialValue);
}
})
.protos({
message: undefined, 		//< protected String

//> public void setMessage(String msg)
setMessage:function(msg){ },

//> public void showMessage()
showMessage:function(){
window.document.writeln('Message ' + this.message);
}
})
.inits(
function(){
/* use this section for static initialization */
this.initialValue = 100;
}
)
.endType();
