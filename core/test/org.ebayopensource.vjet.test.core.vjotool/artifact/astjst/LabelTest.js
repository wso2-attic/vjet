vjo.ctype('astjst.LabelTest') //< public
.protos({
	//>public void func1() 
	func1 : function(){
		yoohoo:{
	        alert("before");
	        for (var i = 0; i < 2; i++) {
	            for (var j = 0; j < 2; j ++) {
	                alert(i);
	                break yoohoo;
	            }
	        }
        	alert(2);
		}
	}
})
.endType();