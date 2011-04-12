vjo.ctype('syntax.label.NoneExistLabel')
.props({
	main: function(){
		var i = 0, //<Number
		j = 0, //<Number
		len = 10; //<Number
			
		LABEL_1:
		while(i < len){
			if(i % 2 == 0){
				break;
			}
			else{
				break LABEL_1;
			}
		}
		
		LABEL_2:
		while(i < len){
			break LABEL_1;
		}
		
		LABEL_3:
		for(i = 0; i < len; i++){
			if(i % 2 == 0){
				continue;
			}
			else{
				continue LABEL_3;
			}
		}
		
		LABEL_4:
		for(i = 0; i < len; i++){
			if(i % 2 == 0){
				continue LABEL_2;
			}
		}
	}
})
.endType();