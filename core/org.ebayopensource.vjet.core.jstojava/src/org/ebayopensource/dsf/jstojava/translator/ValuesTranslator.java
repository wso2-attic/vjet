/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jstojava.translator.robust.JstSourceUtil;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CharLiteral;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.Literal;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.StringLiteral;

public class ValuesTranslator extends BasePropsProtosTranslator {

	public ValuesTranslator(TranslateCtx ctx) {
		super(ctx);
		type = ScopeIds.VALUES;
	}
	
	public void processValues(Literal literal, JstType type) {
		if (literal instanceof StringLiteral || literal instanceof CharLiteral) {
			String s = JstUtil.getCorrectName(literal);
			String[] names = s.split(",");
			int index = literal.sourceStart + 1;
			int ordinal = 0; 
			for (String fullname : names) {
				int fullLength = fullname.length();
				String trimFullname = fullname.trim();
				if(trimFullname.length()>0){
					if (Character.isJavaIdentifierStart(trimFullname.charAt(0))) {								
						int indent =  fullname.indexOf(trimFullname); 
						type.addEnumValue(createEnumProperty(type, index + indent, trimFullname, ordinal++));
					}
				}
				index = index + fullLength+1;
			}
		}
	}

	private JstProperty createEnumProperty(JstType type, int index, String name, int ordinal) {
		JstProperty property = new JstProperty(type, name.trim());
		property.setParent(type);
		property.getModifiers().setFinal();
		property.getModifiers().setPublic();
		property.getModifiers().setStatic(true);
		JstSource source = createSource(index, index + name.trim().length() - 1, m_ctx.getSourceUtil());
		property.setSource(source);
		property.getName().setSource(source);
		if (ordinal != -1) {
			property.setValue(SimpleLiteral.getIntLiteral(ordinal));
		}
		return property;
	}
	
	public static JstSource createSource(int start, int end, JstSourceUtil util) {
		return TranslateHelper.createJstSource(util, end - start, start, end);
	}

}
