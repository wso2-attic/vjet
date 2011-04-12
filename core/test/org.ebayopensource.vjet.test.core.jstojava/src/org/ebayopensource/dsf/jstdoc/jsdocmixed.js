//> public;
/** 
 *  type comment
 */
vjo.ctype("jsdocmixed")
.props({
	/**
	 * static method
	 */
	//> public void doIt()
	doIt:function(){},
	//> Number ;
	 /** 
	 * static property
	 */
	myprop:10
})
.protos({
	//> public void doIt(); 
	 /** 
	 * instance method
	 */
	doIt:function(){},
	//> Number ;
	 /** 
	 * instance property
	 */
	myprop:10
})
.endType();
