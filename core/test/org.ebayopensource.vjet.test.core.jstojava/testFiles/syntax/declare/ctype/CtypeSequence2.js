vjo.ctype('syntax.declare.ctype.CtypeSequence2')
.inits(
function(){
/* use this section for static initialization */
this.initialValue = 100;
})
.endType()
.props({
initialValue : 0, //<Number
getInitialValue: function(){ //< public void getInitialValue()
window.document.writeln('Initial Value : '+this.initialValue);
}
})
