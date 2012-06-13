/**
 * 
 */
package org.ebayopensource.dsf.jstojava.resolver;

import java.util.Set;


/**
 * Defines resolver for 'this' object based on the scope.
 * 
 * @author paragraval
 * 
 */
public interface IThisObjScopeResolver {

	/**
	 * The groupId for which this resolver will create the types
	 * 
	 * @return
	 */
	public String[] getGroupIds();
	
	// TODO add extendType here or in extension schema? 

	/**
	 * Resolves the 'this' object from {@link IThisScopeContext}.
	 * 
	 * @param constrCtx
	 *            reference to {@link IThisScopeContext} with input expressions
	 */
	public void resolve(IThisScopeContext context);
	
}
