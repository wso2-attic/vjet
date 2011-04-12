vjo.ctype('syntax.exception.MtdThrowsException1')
.props({
	sayEx: function(){//<public void sayEx throws Error
		//valid method declaration
	},
	
	main: function(){
		this.sayEx();
		//throws exception, should be captured or main should throws as well
		
		try{
			this.sayEx();
		}
		catch(err){//<Error
			
		}
	}
})