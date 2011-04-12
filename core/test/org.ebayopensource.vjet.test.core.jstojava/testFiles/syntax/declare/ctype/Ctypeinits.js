vjo.ctype('syntax.declare.ctype.Ctypeinits')
.props({
initialValue: undefined, //< public int
getInitialValue: function(){ //< public void getInitialValue()
window.document.writeln('Initial Value : '+this.initialValue);
}
})
.inits(
function(){
/* use this section for static initialization */
this.initialValue = 100;
}
)
.endType();