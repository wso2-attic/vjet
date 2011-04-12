vjo.ctype('syntax.declare.ctype.CtypeSequence')
.inits(
function(){
this.initialValue = 100;
})
.props({
initialValue : 0,//<Number
getInitialValue: function(){ //< public void getInitialValue()
window.document.writeln('Initial Value : '+this.initialValue);
}
})
.endType();