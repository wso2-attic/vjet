vjo.ctype("Static2")
.props({
s_init:false,

//>public boolean  helloWorld()
helloWorld : function() {
  alert("hello VJO");
  return true;
}
 })
.inits(function(){
 this.vj$.Static2.s_init=true;

 });