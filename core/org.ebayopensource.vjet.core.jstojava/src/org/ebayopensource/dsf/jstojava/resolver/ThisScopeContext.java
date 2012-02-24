/**
 * 
 */
package org.ebayopensource.dsf.jstojava.resolver;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;

/**
 * Default implementation of {@link IThisScopeContext}
 * 
 * @author paragraval
 * 
 */
public class ThisScopeContext implements IThisScopeContext {

	private final IJstType currentType;
	private final IJstMethod currentMethod;
	private IJstType thisType;

	/**
	 * Default constructor
	 */
	public ThisScopeContext(IJstType currentType, IJstMethod currentMethod) {
		this.currentType = currentType;
		this.currentMethod = currentMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.jstojava.resolver.IThisScopeContext#getCurrentJstType
	 * ()
	 */
	@Override
	public IJstType getCurrentJstType() {
		return currentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.jstojava.resolver.IThisScopeContext#getCurrentMethod
	 * ()
	 */
	@Override
	public IJstMethod getCurrentMethod() {
		return currentMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.jstojava.resolver.IThisScopeContext#getThisType()
	 */
	@Override
	public IJstType getThisType() {
		return thisType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.jstojava.resolver.IThisScopeContext#setThisType
	 * (org.ebayopensource.dsf.jst.IJstType)
	 */
	@Override
	public void setThisType(IJstType thisType) {
		this.thisType = thisType;
	}

}
