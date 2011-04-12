vjo.ctype('syntax.label.DuplicateLabel')
.props({
	main: function(){
		var i = 0; //<Number
		var j = 0; //<Number
		var len = 10; //<Number
		
		LABEL_1:
		while(i < len){
			LABEL_1:
			while(j < len){
				vjo.sysout.println(i + ', ' + j);
			}
		}
		
		LABEL_2:
		for(i = 0; i < len; i++){
			
		}
	}
})
.endType();