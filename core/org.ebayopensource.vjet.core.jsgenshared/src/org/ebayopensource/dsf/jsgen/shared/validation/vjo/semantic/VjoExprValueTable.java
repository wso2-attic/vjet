/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.token.IExpr;

@Deprecated
public class VjoExprValueTable {

	/* [Sep.23.2009] by huzhou
	 * in order to support the rule refactoring + the opening of validation rules for cc/ui usages
	 * jstTypeValue should be removed
	 * the original purpose of the JstTypeValue is to diff the type reference from the instance reference
	 * which is now doable via the use of JstTypeRefType
	 * we'll need to modify the logic for symbol lookup
	 * type assignability check etc. after the removal of the JstTypeValue
	public static final class JstTypeValue{
		@Deprecated
		private VjoPackage m_vjoPackage;
		private IJstType m_jstType;
		private boolean m_typeRef;
		
		public JstTypeValue(){
			this(null, false);
		}
		
		public JstTypeValue(IJstType jstType){
			this(jstType, false);
		}
		
		public JstTypeValue(IJstType jstType, boolean typeRef){
			m_jstType = jstType;
			m_typeRef = typeRef;
		}
		
		@Deprecated
		public VjoPackage getVjoPackage(){
			return m_vjoPackage;
		}
		
		@Deprecated
		public void setVjoPackage(VjoPackage vjoPackage){
			m_vjoPackage = vjoPackage;
		}
		
		public IJstType getJstType(){
			return m_jstType;
		}
		
		public void setJstType(IJstType jstType){
			m_jstType = jstType;
		}
		
		public boolean isTypeReference(){
			return m_typeRef;
		}
		
		public void setTypeReference(boolean typeRef){
			m_typeRef = typeRef;
		}
		
		public String toString(){
			final StringBuilder sb = new StringBuilder();
			sb.append("{type: ").append(getJstType()).append(", staticRef: ").append(isTypeReference()).append("}");
			return sb.toString();
		}
	}
	*/
	
	private Map<IExpr, IJstType> m_expr2RefTypeMap;
	
	public IJstType getExprValue(IExpr expr){
		if(m_expr2RefTypeMap == null){
			return null;
		}
		return m_expr2RefTypeMap.get(expr);
	}
	
	public void addExprValue(IExpr expr, IJstType value){
//		addExprValue(expr, value);
		if(m_expr2RefTypeMap == null){
			m_expr2RefTypeMap = new HashMap<IExpr, IJstType>();
		}
		m_expr2RefTypeMap.put(expr, value);
	}
	
//	public void addExprValue(IExpr expr, IJstType jstType, boolean staticReference){
//		if(m_expr2RefTypeMap == null){
//			m_expr2RefTypeMap = new HashMap<IExpr, IJstType>();
//		}
//		
//		//bugfix meaningless jstType value
//		if(jstType == null){
//			return;
//		}
//		
//		IJstType refType = m_expr2RefTypeMap.get(expr);
//		if(refType == null){
//			refType = new JstTypeValue();
//			m_expr2RefTypeMap.put(expr, refType);
//		}
				
//		refType.setJstType(jstType);
//		refType.setTypeReference(staticReference);
//	}
	
	@Deprecated
	public void addExprValue(IExpr expr, VjoPackage vjoPackage){
//		if(m_expr2RefTypeMap == null){
//			m_expr2RefTypeMap = new HashMap<IExpr, JstTypeValue>();
//		}
//		
//		JstTypeValue refType = m_expr2RefTypeMap.get(expr);
//		if(refType == null){
//			refType = new JstTypeValue();
//			m_expr2RefTypeMap.put(expr, refType);
//		}
//		
//		refType.setVjoPackage(vjoPackage);
	}
	
	public void cleanUp(){
		if(m_expr2RefTypeMap != null){
			m_expr2RefTypeMap.clear();
		}
	}
}
