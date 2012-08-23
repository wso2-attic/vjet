vjo.ctype('defect.Bug6472') //< public
.needs('defect.Bug6472_Enum')
.props({

   //>public void main(String... args) 
   main : function(args){
         var out = vjo.sysout.println ;
         out(this.vj$.Bug6472_Enum.THU instanceof vjo.Enum) ;  
   }
})
.protos({

})
.endType();
