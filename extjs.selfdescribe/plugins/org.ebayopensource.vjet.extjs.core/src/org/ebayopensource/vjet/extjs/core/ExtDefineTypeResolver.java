/**
 * 
 */
package org.ebayopensource.vjet.extjs.core;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.expr.FuncExpr;
import org.ebayopensource.dsf.jst.term.JstLiteral;
import org.ebayopensource.dsf.jst.term.NV;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jstojava.resolver.ITypeConstructorResolver;

/**
 * @author paragraval
 * 
 */
public class ExtDefineTypeResolver implements ITypeConstructorResolver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.jstojava.resolver.ITypeConstructorResolver#resolve
	 * (java.util.List)
	 */
	@Override
	public IJstType resolve(List<IExpr> args) {

		// creating JstType
		String typeName = args.get(0).toExprText();
		typeName = trimQuotes(typeName);
		JstType jstType = JstFactory.getInstance()
				.createJstType(typeName, true);
		jstType.addExtend(JstCache.getInstance().getType("Ext.Base"));

		// t.setSource(m_currentType.getSource());
		jstType.getPackage().setGroupName(getGroupId());

		// TODO visit ObjLiteral second arg
		IExpr ol = args.get(1);
		if (ol != null && ol instanceof ObjLiteral) {
			ObjLiteral ol2 = (ObjLiteral) ol;
			for (NV nvs : ol2.getNVs()) {
				String name = nvs.getName();
				Object value = nvs.getValue();

				if (name.equals("extend")) {
					processExtendProperty(jstType, nvs);
				} else if (value instanceof FuncExpr) {
					processFunction(jstType, nvs);
				} else if (value instanceof JstLiteral) {
					processClassProperty(jstType, nvs);
				}

			}
		}

		return jstType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ebayopensource.dsf.jstojava.resolver.ITypeConstructorResolver#getGroupId
	 * ()
	 */
	@Override
	public String getGroupId() {
		return "ExtSelfDescribe";
	}

	private void processExtendProperty(JstType jstType, NV nv) {

		// in case of extend property the expected value is string literal
		// ignore other type of values add to extend

		System.out.println(nv.getValue().getClass());

		String extendValue_ExtTypeName = nv.getValue().toExprText();
		extendValue_ExtTypeName = trimQuotes(extendValue_ExtTypeName);
		System.out.println(extendValue_ExtTypeName);
		jstType.clearExtends();
		jstType.addExtend(JstCache.getInstance().getType(
				extendValue_ExtTypeName));

	}

	private void processClassProperty(JstType jstType, NV nv) {
		String value = nv.getValue().toExprText();
		System.out.println("-ClassProp:" + value);

		// create and add class property JstProperty
		JstProperty jstProperty = new JstProperty(
				((JstLiteral) nv.getValue()).getResultType(), nv.getName(),
				(JstLiteral) nv.getValue(), new JstModifiers());
		jstType.addProperty(jstProperty);
	}

	private void processFunction(JstType jstType, NV nv) {
		FuncExpr func = (FuncExpr) nv.getValue();
		JstMethod jstMethod = func.getFunc();
		jstMethod.getModifiers().setPublic();
		jstMethod.setName(nv.getName());
		jstMethod.setOverloaded(null);
		jstType.addMethod(jstMethod);
	}

	/**
	 * If the given string is prefixed and suffixed with either ' or " then
	 * truncates them.
	 * 
	 * @param input
	 * @return
	 */
	private String trimQuotes(String input) {
		if (input == null) {
			return "";
		}
		if (input.startsWith("'") || input.startsWith("\"")) {
			if (input.length() > 1) {
				input = input.substring(1);
			} else {
				input = "";
			}
		}
		if (input.endsWith("'") || input.endsWith("\"")) {
			if (input.length() > 1) {
				input = input.substring(0, input.length() - 1);
			} else {
				input = "";
			}
		}
		return input;
	}

}
