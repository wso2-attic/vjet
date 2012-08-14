/**
 * 
 */
package org.ebayopensource.vjet.eclipse.core.typeconstruct;

import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.CastExpr;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.resolver.ITypeConstructContext;
import org.ebayopensource.dsf.jstojava.resolver.ITypeConstructorResolver;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;

/**
 * @author paragraval
 * 
 */
public class NoOpTypeConstructorResolver implements ITypeConstructorResolver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.jstojava.resolver.ITypeConstructorResolver#resolve
	 * (org.ebayopensource.dsf.jstojava.resolver.ITypeConstructContext)
	 */
	@Override
	public void resolve(ITypeConstructContext constrCtx) {
		// creating JstType
		String typeName = constrCtx.getArgsExpr().get(0).toExprText();
		typeName = JstUtil.getCorrectName(typeName);
		JstType jstType = JstFactory.getInstance()
				.createJstType(typeName, true);
		constrCtx.addType(jstType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.jstojava.resolver.ITypeConstructorResolver#getGroupId
	 * ()
	 */
	@Override
	public String[] getGroupIds() {
		return new String[]{"test"};
	}

	@Override
	public Class<? extends IExpr> getExprClass() {
		return CastExpr.class;
	}

}
