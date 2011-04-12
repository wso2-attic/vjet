/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.util;

import org.ebayopensource.dsf.jsgen.shared.generate.SourceGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;
import org.ebayopensource.dsf.jst.declaration.JstWildcardType;

public class GeneratorJstHelper {

	public static CharSequence getArgsDecoration(IJstType type) {
		if (!(type instanceof JstTypeWithArgs)) {
			//Generate default arguments if not defined
			int size = type.getParamTypes().size();
			if (size == 0) return "";
			StringBuilder sb = new StringBuilder("<");
			for (int i = 0; i < size; i++) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append("Object");
			}
			sb.append(">");
			return sb.toString();
		}
		JstTypeWithArgs templatedType = (JstTypeWithArgs)type;
		StringBuilder sb = new StringBuilder(SourceGenerator.OPEN_ANGLE_BRACKET);
		int i=0;
		for (IJstType p: templatedType.getArgTypes()) {
			if (i++ > 0){
				sb.append(SourceGenerator.COMMA);
			}
			if (p instanceof JstWildcardType){
				sb.append(SourceGenerator.QUESTION_MARK);
				if (!JstWildcardType.DEFAULT_NAME.equals(p.getSimpleName())){
					if (((JstWildcardType)p).isUpperBound()){
						sb.append(" extends ").append(p.getSimpleName());
					}
					else if (((JstWildcardType)p).isLowerBound()){
						sb.append(" super ").append(p.getSimpleName());
					}
				}
			}
			else {
				sb.append(p.getSimpleName());
			}
		}
		sb.append(SourceGenerator.CLOSE_ANGLE_BRACKET);		
		return sb.toString();
	}
}
