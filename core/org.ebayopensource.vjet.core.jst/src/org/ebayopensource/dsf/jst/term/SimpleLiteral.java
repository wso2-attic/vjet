/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.term;

import org.ebayopensource.dsf.jsnative.global.Number;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public final class SimpleLiteral extends JstLiteral {
	
	private static final long serialVersionUID = 1L;
	
	private Class<?> m_type;
	private IJstType m_javaType;
	private String m_value;
//	private boolean m_useJstTypeAsResultType;
	
	//
	// Constructor
	//
	public SimpleLiteral (final Class<?> type, final IJstType jstType, final String value){
		m_type = type;
		m_jstType = jstType;
		m_value = value;
		m_javaType = getJavaType(type);
		
		if (m_value != null) {
			if (m_type != String.class && m_type != CharSequence.class && m_type != Object.class
				&& (m_value.charAt(m_value.length()-1) == 'L' || m_value.charAt(m_value.length()-1) == 'l')){
				m_value = m_value.substring(0, m_value.length()-1);
			}
			//Only convert negative number since it will be different in javascript
			if (m_type == int.class || m_type == Integer.class) {
				Long l;
				try {
					l = Long.decode(m_value);
					if (l > Integer.MAX_VALUE) {
						m_value = Integer.toString(l.intValue());
					}
				} catch (Exception e) {//use existing m_value if we cannot create long
				}
			}
			if (m_type == short.class || m_type == Short.class) {
				Integer l = Integer.decode(m_value);
				if (l > Short.MAX_VALUE) {
					m_value = Short.toString(l.shortValue());
				}
			}
			if (m_type == byte.class || m_type == Byte.class) {
				Integer l = Integer.decode(m_value);
				if (l > Byte.MAX_VALUE) {
					m_value = Byte.toString(l.byteValue());
				}
			}
		}
	}

	private static final String SINGLE_QUOTE = "'";
	private static final String DOUBLE_QUOTE = "\"";
	
	public String toValueText(){
		if (m_value == null){
			return "null";
		} else if (m_type == String.class && m_value != null){
			return DOUBLE_QUOTE + m_value + DOUBLE_QUOTE;
		}
		else if (m_type == char.class && m_value != null) {
			return SINGLE_QUOTE + m_value + SINGLE_QUOTE;
		}
 		else if ((m_type == Double.class || m_type == double.class || m_type == Float.class || m_type == float.class) 
 				&& (m_value.endsWith("d") || m_value.endsWith("f") || m_value.endsWith("D") || m_value.endsWith("F")) && (!m_value.startsWith("0x"))) {
			return m_value.substring(0,m_value.length()-1);
		}
		return m_value;
	}
	
	public String toParamText(){
		return toValueText();
	}
	
	public String toTermText(){
		return toValueText();
	}
	
	public String toSimpleTermText(){
		return toValueText();
	}
	
	public String toRHSText(){
		return toValueText();
	}
	
	public String toExprText(){
		return toValueText();
	}
	
	public void setResultType(IJstType type) {
		m_javaType = type;
		m_jstType = type;
	}
	
	
	// TODO make this more clear - this method is ambiguous
	// in the case where precision is required we 
	// need to give Java impl wrapper / primitive
	// otherwise we give js type String literals should return JS string not java.util.String
	
	public IJstType getResultType(){		
		return m_javaType != null ? m_javaType : m_jstType;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString(){
		return toValueText();
	}
	
	public String getValue() {
		return m_value;
	}
	
	//
	// API
	//
	public static SimpleLiteral getCharLiteral(String literal){
		return new SimpleLiteral(char.class, getJsType(org.ebayopensource.dsf.jsnative.global.String.class), literal);
	}
	
	public static SimpleLiteral getStringLiteral(final String literal){
		return new SimpleLiteral(String.class, getJsType(org.ebayopensource.dsf.jsnative.global.String.class), literal);
	}


	public static SimpleLiteral getBooleanLiteral(String literal){
		return new SimpleLiteral(boolean.class, getJsType(org.ebayopensource.dsf.jsnative.global.PrimitiveBoolean.class), literal);
	}
	public static SimpleLiteral getBooleanLiteral(boolean literal){
		return new SimpleLiteral(boolean.class, getJsType(org.ebayopensource.dsf.jsnative.global.PrimitiveBoolean.class), Boolean.toString(literal));
	}
	public static SimpleLiteral getIntLiteral(int literal){
		return new SimpleLiteral(int.class, getJsType(Number.class), String.valueOf(literal));
	}
	public static SimpleLiteral getIntLiteral(String literal){
		return new SimpleLiteral(int.class, getJsType(Number.class), literal);
	}
	public static SimpleLiteral getShortLiteral(String literal){
		return new SimpleLiteral(short.class, getJsType(Number.class), literal);
	}
	public static SimpleLiteral getByteLiteral(String literal){
		return new SimpleLiteral(byte.class, getJsType(Number.class), literal);
	}
	
	public static SimpleLiteral getIntegerLiteral(String literal){
		return new SimpleLiteral(int.class, getJsType(Number.class), literal);
	}
	
	public static SimpleLiteral getFloatLiteral(String literal) {
		return new SimpleLiteral(float.class, getJsType(Number.class), literal);
	}
	public static SimpleLiteral getLongLiteral(String literal) {
		return new SimpleLiteral(long.class, getJsType(Number.class), literal);
	}
	
	
	public static SimpleLiteral getDoubleLiteral(String literal){
		return new SimpleLiteral(double.class, getJsType(Number.class), literal);
	}
	
	public static SimpleLiteral getNullLiteral() {
		return new SimpleLiteral(null, null, null);
	}
	
	public static SimpleLiteral getUndefinedLiteral() {
		return new SimpleLiteral(null, null, "undefined");
	}
	
	private static JstType getJsType(Class<?> jsnative){
		JstType type = JstCache.getInstance().getType(jsnative.getName());
		if(type!=null){
			return type;
		}
		return JstCache.getInstance().getType(jsnative.getSimpleName());		
	}
	
	private static JstType getJavaType(Class<?> javaWrapper){
		if (javaWrapper == null) {
			return null;
		}
		JstType type = JstCache.getInstance().getType("vjo." +javaWrapper.getName());
		if(type!=null){
			return type;
		}
		return JstCache.getInstance().getType(javaWrapper.getSimpleName());
		
		
	}

}
