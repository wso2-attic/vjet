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
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;

public class JstPotentialOtypeMethod extends JstSynthesizedMethod{
	private static final long serialVersionUID = 1L;

	private IJstType m_potentialOtypeJstFunctionRefType;
	private IJstMethod m_resolvedOtypeMethod;
	
	public JstPotentialOtypeMethod(final String name, 
			final IJstType potentialOtypeJstFunctionRefType, 
			final JstArg... args) {
		super(name, new JstModifiers(), null, args);
		
		m_potentialOtypeJstFunctionRefType = potentialOtypeJstFunctionRefType;
	}
	
	public IJstType getPotentialOtypeJstFunctionRefType(){
		return m_potentialOtypeJstFunctionRefType;
	}

	public void setResolvedOtypeMethod(IJstMethod m_resolvedOtypeMethod) {
		this.m_resolvedOtypeMethod = m_resolvedOtypeMethod;
	}

	public IJstMethod getResolvedOtypeMethod() {
		return m_resolvedOtypeMethod;
	}
	
	@Override
	public JstName getName() {
		return super.getName();
	}

	@Override
	public List<JstArg> getArgs() {
		final List<JstArg> parameters = new ArrayList<JstArg>(super.getArgs());
		if(m_resolvedOtypeMethod != null){
			if(!m_resolvedOtypeMethod.isDispatcher()){
				final List<JstArg> resolvedParameters = m_resolvedOtypeMethod.getArgs();
				for(int i = 0, len = parameters.size(); i < len && i < resolvedParameters.size(); i++){
					parameters.get(i).clearTypes();
					parameters.get(i).addTypes(resolvedParameters.get(i).getTypes());
				}
			}
		}
		return parameters;
	}
	
	@Override
	public IJstType getRtnType() {
		return m_resolvedOtypeMethod != null ? m_resolvedOtypeMethod.getRtnType() : super.getRtnType();
	}
	
	@Override
	public IJstTypeReference getRtnTypeRef() {
		return m_resolvedOtypeMethod != null ? m_resolvedOtypeMethod.getRtnTypeRef() : super.getRtnTypeRef();
	}	
	
	@Override
	public JstModifiers getModifiers() {
		return super.getModifiers();
	}
	
	@Override
	public boolean isConstructor(){
		return super.isConstructor();
	}

	@Override
	public boolean isPublic() {
		return super.isPublic();
	}

	@Override
	public boolean isProtected() {
		return super.isProtected();
	}

	@Override
	public boolean isInternal() {
		return super.isInternal();
	}
	
	@Override
	public boolean isPrivate() {
		return super.isPrivate();
	}

	@Override
	public boolean isStatic() {
		return super.isStatic();
	}

	@Override
	public boolean isFinal() {
		return super.isFinal();
	}

	@Override
	public boolean isAbstract() {
		return super.isAbstract();
	}

	@Override
	public boolean isDispatcher() {
		return m_resolvedOtypeMethod != null ? m_resolvedOtypeMethod.isDispatcher() : super.isDispatcher();
	}
	
	@Override
	public List<IJstMethod> getOverloaded(){
		if(m_resolvedOtypeMethod == null){
			return super.getOverloaded();
		}
		final List<IJstMethod> resolvedOverloads = m_resolvedOtypeMethod.getOverloaded();
		final List<IJstMethod> overloads = new ArrayList<IJstMethod>(resolvedOverloads.size());
		for(IJstMethod resolvedOverload: resolvedOverloads){
			overloads.add(new JstSynthesizedMethod(getName().getName(), getModifiers(), resolvedOverload.getRtnType(), resolvedOverload.getArgs().toArray(new JstArg[resolvedOverload.getArgs().size()])));
		}
		return overloads;
	}
	
	@Override
	public boolean isTypeFactoryEnabled() {
		return m_resolvedOtypeMethod != null ? m_resolvedOtypeMethod.isTypeFactoryEnabled() : super.isTypeFactoryEnabled();
	}
}
