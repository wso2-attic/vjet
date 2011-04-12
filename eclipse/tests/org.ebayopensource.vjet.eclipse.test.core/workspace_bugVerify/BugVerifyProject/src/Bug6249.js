vjo.ctype('Bug6249') //< public

.props({
	
})
.protos({
	//>public void foo2() 
    foo2 : function(){
            var arr = new Array(2,3,"hi");//<Array
            var i = 0;//<int
            for (i in arr){
                    vjo.sysout.println(arr[i]);
            }
    },

    doit:vjo.ctype()
    

})
.endType();