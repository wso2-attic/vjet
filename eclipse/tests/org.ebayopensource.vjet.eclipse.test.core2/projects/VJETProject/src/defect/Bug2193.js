vjo.ctype('defect.Bug2193') //< public
.props({
	aaStaticProp1 : 10,
	aaStaticProp2 : undefined, //<String
	aaStaticProp3 : false, //<Boolean
	//>public void aaStaticMethod() 
	aaStaticMethod : function(){
		
	}
})
.protos({
	aaPprop1 : 10,
	aaProp2 : undefined, //<String
	aaProp3 : false, //<Boolean
	//>public void aaMethod() 
	aaMethod : function(){
		var i = 10;
		if (i == 10){
			var arr = [10, "hi"]; //<Array
			for (i in arr){
				alert(arr[i]);
			}
		}
	}
})
.endType();