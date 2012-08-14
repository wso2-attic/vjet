/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.parser.comments;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.meta.IJsCommentMeta;
import org.ebayopensource.dsf.jst.meta.JsAnnotation;
import org.ebayopensource.dsf.jst.meta.JsType;
import org.ebayopensource.dsf.jst.meta.JsTypingMeta;
import org.ebayopensource.dsf.jst.meta.Token;
import org.ebayopensource.dsf.jst.meta.ArgType;
import org.ebayopensource.dsf.jst.meta.ArgType.WildCardType;

public class JsCommentMeta implements IJsCommentMeta {
	DIRECTION m_direction;
	boolean m_cast;
	boolean m_isAnnotation;
	JsTypingMeta m_typingMeta;
	int beginOffset;
	int endOffset;
	String m_name;
	String m_commentSrc;
	List<ArgType> m_args = new ArrayList<ArgType>();
	JstModifiers m_modifiers = new JstModifiers();
	JsAnnotation m_annotation = new JsAnnotation();
	List<String> m_inactiveNeeds = new ArrayList<String>();

	public List<String> getInactiveNeeds() {
		return m_inactiveNeeds;
	}

	public JsAnnotation getAnnotation() {
		return m_annotation;
	}

	public DIRECTION getDirection() {
		return m_direction;
	}

	public boolean isCast() {
		return m_cast;
	}

	public boolean isAnnotation() {
		return m_isAnnotation;
	}

	public JstModifiers getModifiers() {
		return m_modifiers;
	}

	public JsTypingMeta getTyping() {
		return m_typingMeta;
	}

	public String getName() {
		return m_name;
	}

	public List<ArgType> getArgs() {
		return m_args;
	}

	public void setBeginOffset(int beginOffset) {
		this.beginOffset = beginOffset;
	}

	public int getBeginOffset() {
		return beginOffset;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("//");
		if (m_direction.equals(DIRECTION.BACK)) {
			sb.append(m_cast ? "<< " : "< ");
		} else {
			sb.append(m_cast ? ">> " : "> ");
		}
		sb.append(m_modifiers.toString().replace("[", "").replace("]", "")
				.replace(",", " "));
		if (m_typingMeta instanceof JsFuncType) {
			JsFuncType funcType = (JsFuncType)m_typingMeta;
			sb.append(" ").append(funcType.getReturnType().getType());
			sb.append(" ").append(funcType.getFuncName());
			sb.append("(");
			boolean isF = true;
			for (JsParam itm : funcType.getParams()) {
				if (!isF) {
					sb.append(", ");
				}
				sb.append(itm.getType()).append(" ").append(itm.getName());
				isF = false;
			}
			sb.append(")");
		}
		else if (m_typingMeta instanceof JsVariantType) {
			sb.append(" {");
			boolean isF = true;
			for (JsTypingMeta typing: ((JsVariantType)m_typingMeta).getTypes()) {
				if (!isF) {
					sb.append("|");
				}
				sb.append(typing.getType());
				isF = false;
			}
			sb.append(" }");
		} else {
			sb.append(" ").append(m_typingMeta != null ? m_typingMeta.getType() : "");
		}
		return sb.toString();
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}

	public String getCommentSrc() {
		return m_commentSrc;
	}

	public void setCommentSrc(String comment) {
		this.m_commentSrc = comment;
	}

	public boolean isMethod() {
		return (m_typingMeta instanceof JsFuncType);
	}

	static void addTempleteArg(JsCommentMeta meta, JsType baseType, ArgType arg) {
		if (baseType == null && meta != null) {
			meta.m_args.add(arg);
		} else {
			baseType.addArg(arg);
		}		
	}
	
	static void addTempleteArg(JsCommentMeta meta, JsType baseType, JsTypingMeta templateType) throws ParseException {
		if (!(templateType instanceof JsType
				||templateType instanceof JsAttributed
				||templateType instanceof JsFuncType)) {
			throw new ParseException("incorrect templete type: " + templateType.getType());
		}
		addTempleteArg(meta, baseType, new ArgType(templateType));
	}
	
	static void addTempleteArg(JsCommentMeta meta, JsType baseType) throws ParseException {
		addTempleteArg(meta, baseType, new ArgType());
	}
	
	static void addTempleteArg(
		JsCommentMeta meta,
		JsType baseType,
		JsType templeteType,
		WildCardType wildcardType,
		JsTypingMeta boundedType) throws ParseException {
		
		if (!(boundedType instanceof JsType)) {
			throw new ParseException("incorrect templete bounded type: " + boundedType.getType());
		}
		addTempleteArg(meta, baseType, new ArgType(templeteType, wildcardType, (JsType)boundedType));
	}
	
	static void addTempleteArg(
		JsCommentMeta meta,
		JsType baseType,
		WildCardType wildcardType,
		JsTypingMeta boundedType) throws ParseException {
		
		if (!(boundedType instanceof JsType)) {
			throw new ParseException("incorrect templete bounded type: " + boundedType.getType());
		}
		addTempleteArg(meta, baseType, new ArgType(wildcardType, (JsType)boundedType));
	}
	
	void setDirection(boolean forward, boolean cast) {
		m_direction = (forward) ? DIRECTION.FORWARD : DIRECTION.BACK;
		if (cast) {
			m_cast = true;
		}
	}
	
	void setAnnotation(String annotation) {
		m_isAnnotation = true;
		m_annotation.setAnnotation(annotation);
	}
	
	void setAccessModifier(Token t) throws ParseException {
		if (m_modifiers.isAbstract()) {
			throw new ParseException("Access control cannot be set after abstract keyword.");
		} else if (m_modifiers.isStatic()) {
			throw new ParseException("Access control cannot be set after static keyword.");
		} else {
			m_modifiers.merge(JstModifiers.getFlag(t.image));
		}
	}
	
	void setFinal() throws ParseException {
		if (m_modifiers.isFinal()) {
			throw new ParseException("Duplicate modifier final not allowed.");
		} else if(m_modifiers.isAbstract()) {
			throw new ParseException("can be either abstract or final, not both.");
		} else {
			m_modifiers.setFinal();
		}
	}
	
	void setAbstract() throws ParseException {
		if (m_modifiers.isAbstract()) {
			throw new ParseException("Duplicate modifier abstract not allowed.");
		} else if (m_modifiers.isFinal()) {
			throw new ParseException("can be either abstract or final, not both.");
		} else {
			m_modifiers.setAbstract();
		}
	}
	
	void setStatic() throws ParseException {
		if (m_modifiers.isStatic()) {
			throw new ParseException("Duplicate modifier static not allowed.");
		} else {
			m_modifiers.setStatic(true);
		}
	}
	
	void setDynamic() throws ParseException {
		if (m_modifiers.isDynamic()) {
			throw new ParseException("Duplicate modifier dynamic not allowed.");
		} else {
			m_modifiers.setDynamic();
		}
	}
	
	void setTyping(JsTypingMeta typingMeta) {
		m_typingMeta = typingMeta;
	}
	
	void setOptional(boolean isOptional) {
		if (isOptional) {
			m_typingMeta.setOptional(isOptional);
		}
	}
	
	void setName(String name) {
		m_name = name;
		if (m_typingMeta instanceof JsFuncType) {
			((JsFuncType)m_typingMeta).setFuncName(name, null);
		}
	}
	
	JsCommentMeta setMethod() {
		m_typingMeta = new JsFuncType(m_typingMeta);
		return this;
	}
	
	void addNeedsAnnotation(Token t) {
		m_inactiveNeeds.add(t.image);
		m_isAnnotation = true;
        m_annotation.setAnnotation(t.toString());
	}
	
	void addParam(String name, JsTypingMeta typing, boolean isFinal,
		boolean isOptional, boolean isVariable) throws ParseException {
		((JsFuncType)m_typingMeta).addParam(name, typing, isFinal, isOptional, isVariable);
	}
	
	void setTypeFactoryEnabled(boolean set) {
		if (m_typingMeta instanceof JsFuncType) {
			((JsFuncType)m_typingMeta).setTypeFactoryEnabled(set);
		}
	}
	
	void setFuncArgMetaExtensionEnabled(boolean set) {
		if (m_typingMeta instanceof JsFuncType) {
			((JsFuncType)m_typingMeta).setFuncArgMetaExtensionEnabled(set);
		}
	}
}
