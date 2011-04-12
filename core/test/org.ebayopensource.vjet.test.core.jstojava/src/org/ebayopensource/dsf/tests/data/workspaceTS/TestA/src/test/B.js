vjo.needs("test.A");
vjo.needs("test.I");

vjo.ctype("test.B").inherits("test.A").satisfies("test.I").protos({

 
 hju : function() {
 
   this.base.gh();	
 }
 
}).endType();