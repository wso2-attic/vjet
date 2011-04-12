/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.declaration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;

public class JstTypeWithArgs extends JstProxyType {

	private static final long serialVersionUID = 1L;
	
	private List<IJstType> m_argTypes = new ArrayList<IJstType>();
	private transient HashMap<JstParamType, IJstType> m_argMap = new HashMap<JstParamType, IJstType>();
	
	//
	// Constructor
	//
	public JstTypeWithArgs(IJstType type){
		super(type);
		getModifiers().setPublic();
	}
	
	//
	// API
	//
	public synchronized void addArgType(final IJstType argType){
		if (argType == null){
			return;
		}
		m_argTypes.add(argType); 
	}
	
	public synchronized List<IJstType> getArgTypes(){
		return Collections.unmodifiableList(m_argTypes);
	}
	
	public IJstType getArgType(){
		List<IJstType> argTypes = getArgTypes();
		if (argTypes.isEmpty()){
			return null;
		}
		return argTypes.get(0);
	}
	
	public String getArgsDecoration(){
		StringBuilder sb = new StringBuilder("<");
		int i=0;
		for (IJstType p: getArgTypes()){
			if (i++ > 0){
				sb.append(",");
			}
			if (p instanceof JstWildcardType){
				sb.append("?");
				if (((JstWildcardType)p).isUpperBound()){
					sb.append(" extends ").append(p.getSimpleName());
				}
				else if (((JstWildcardType)p).isLowerBound()){
					sb.append(" super ").append(p.getSimpleName());
				}
			}
			else {
				sb.append(p.getSimpleName());
			}
			if (p instanceof JstTypeWithArgs){
				sb.append(((JstTypeWithArgs)p).getArgsDecoration());
			}
		}
		sb.append(">");
		
		return sb.toString();
	}

	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	private IJstType getArgTypeFromParam(JstParamType paramType) {
		
		List<JstParamType> paramTypes = getOwnerType().getParamTypes();
		
		if (!m_argMap.isEmpty()) { // map is not empty, check if entry is up to date
			if (paramTypes != null && !paramTypes.isEmpty()) {
				// if parameter type is not found in map, rebuild it
				if (m_argMap.get(paramTypes.get(0)) == null) {
					m_argMap.clear();
				}
			}
		}
		
		if (m_argMap.isEmpty()) {
			List<IJstType> argTypes = getArgTypes();
			
			if (argTypes == null || paramType == null) {
				return null;
			}
			
			int argSize = argTypes.size();	
			int paramSize = paramTypes.size();			
			int size = (argSize < paramSize) ? argSize : paramSize;
			
			for (int i = 0; i < size; i++) {
				
				m_argMap.put(paramTypes.get(i), argTypes.get(i)); // map paramType to argType e.g. T to String
			}			
		}
		
		return m_argMap.get(paramType);
	}
	
	public IJstType getParamArgType(JstParamType paramType) {
		
		IJstType argType = getArgTypeFromParam(paramType);
		
		if (argType == null) {
			
			IJstType parent = getExtend();
			
			if (parent != null && parent instanceof JstTypeWithArgs){
				argType = ((JstTypeWithArgs)parent).getParamArgType(paramType);
			}
			
			if (argType == null) {
			
				List<? extends IJstType> listInterfaces = getSatisfies();
				
				if (listInterfaces != null && !listInterfaces.isEmpty()) {
					for (IJstType itype : listInterfaces) {
						if (itype != null && itype instanceof JstTypeWithArgs) {
							argType = ((JstTypeWithArgs)itype).getParamArgType(paramType);
							
							if (argType != null) {
								break;
							}
						}
					}
				}
			}
			
			if (argType != null) {
				
				IJstType paramArgType = argType;
			
				while (paramArgType != null && paramArgType instanceof JstParamType) {
					paramArgType = getParamArgType((JstParamType)paramArgType);
					
					if (paramArgType != null) {
						if (paramArgType == argType) {
							break;
						}
						else {
							argType = paramArgType;
						}
					}
				}					
			}	
		}
		
		return argType;
	}
	
}
