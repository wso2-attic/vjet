vjo.ctype('multipledocmixed') //< public
.props({

	/**
	 * <p>
	 * doIt Js doc
	 * </p>
	 * <code>
	 * mytypes.mytypelib.doIt("foobar");<br>
	 * mytypes.mytypelib.doIt(new Date());<br>
	 * mytypes.mytypelib.doIt();<br>
	 * </code>
	 * 
	 * 
	 * @author jearly@ebay.com
	 */
		
	//> public void doIt(String);test1
	//> public boolean doIt(Date?);test2
	doIt:function(){
	},
	/*> public void foobar(String); 
	 * 
	 * doIt2 Js doc
	 * 
	 * @author syogi@ebay.com
	 * 
	 * 
	 */
	foobar:function(arg){
		
		 
	},
	// internal documentation
	// my comment
	 /*
	  * 
	  */
	//> public void doIt3(Number)
	doIt3:function(){
	
	}
})
.inits(function(){
	this.doIt("");
	this.foobar("");
	this.doIt(new Date());
	
})
.endType();