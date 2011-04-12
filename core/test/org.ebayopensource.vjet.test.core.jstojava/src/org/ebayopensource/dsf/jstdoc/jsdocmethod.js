/*> public;
 * 
 * type comment
 * 
*/
vjo.ctype("jsdocmethod")
.props({
	/**> public void doIt(); 
	 * 
	 * static method
	 */
	doIt:function(){},
	/**> Number ;
	 * 
	 * static property
	 */
	myprop:10
})
.protos({
	/**> public void doIt(); 
	 * 
	 * instance method
	 */
	doIt:function(){},
	/**> Number ;
	 * 
	 * instance property
	 */
	myprop:10
})
.endType();
