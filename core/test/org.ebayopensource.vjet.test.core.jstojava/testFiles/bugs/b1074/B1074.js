vjo.ctype('bugs.b1074.B1074') //< public

.props({
  
})
.protos({
  //>public void foo() 
  foo : function(){
  	vjo.Enum.boo="boo";
  	for(var key in vjo.Enum){
         	 vjo.sysout.println(key);
  	}
  }
})
.endType();