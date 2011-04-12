vjo.ctype("vjoPro.example.VjCompInnerFunction")
.protos({
constructs:function(pHello){

alert("constructed with " + pHello);
/**
* @return void
* @access public
* @JsEventHandler
*/
this.innerFuncOne = function(){
alert("innerFunctionOne");

};

}
})
.endType();
