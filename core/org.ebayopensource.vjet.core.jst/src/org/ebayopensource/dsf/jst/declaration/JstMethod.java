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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstDoc;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IJstTypeReference;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstNodeVisitor;
import org.ebayopensource.dsf.jst.util.JstTypeHelper;
import org.ebayopensource.dsf.common.Z;

/**
 * Implementation of <code>IJstMethod</code>
 * 
 * 
 */
public class JstMethod extends BaseJstNode implements IJstMethod {

	private static final long serialVersionUID = 1L;
	private static final String EMPTY = "";
	
	private JstName m_name;
	private JstModifiers m_modifiers;
	private List<JstArg> m_args;
	private IJstTypeReference m_rtnType;
	private JstBlock m_block;
	private Map<String, IJstType> m_varTypes;
	private Map<String,JstParamType> m_paramTypes;
	
	private boolean m_isConstructor = false;
	private boolean m_isDuplicate = false;
	private boolean m_hasAnnotation = false;
	private boolean m_typeFactoryEnabled = false; //for different return type
	
	/**
	 * enable meta look up for the second argument (Function type) base on
	 * first String key, such as 
	 * 		void ^on(String, Function)
	 * The second argument is defined as generic Function, however, based on the value
	 * of the first argument, the extended meta could provide more precise function signature.
	 */
	private boolean m_funcArgMetaExtensionEnabled = false;	
	
	private String m_surffix;
	private IJstDoc m_doc;
	
	private JstFunctionRefType m_oType;
	
	private List<IJstMethod> m_overloaded;
	private boolean m_returnOptional;

	//
	// Constructors
	//
	/**
	 * Constructor
	 * @param args JstArg...
	 */
	public JstMethod(final JstArg... args) {
		this((JstName)null, new JstModifiers(), null, args);
	}

	/**
	 * Constructor
	 * @param name String
	 * @param args JstArg...
	 */
	public JstMethod(final String name, final JstArg... args) {
		this(name, new JstModifiers(), null, args);
	}
	
	/**
	 * Constructor
	 * @param name String
	 * @param modifiers JstModifiers
	 * @param args JstArg...
	 */
	public JstMethod(final String name, final JstModifiers modifiers, final JstArg... args) {
		this(name, modifiers, null, args);
	}

	/**
	 * Constructor
	 * @param name String
	 * @param rtnType JstType
	 * @param args JstArg...
	 */
	public JstMethod(final String name, final IJstType rtnType,
			final JstArg... args) {
		this(name, new JstModifiers(), rtnType, args);
	}

	/**
	 * Constructor
	 * @param name String
	 * @param modifiers JstModifiers
	 * @param rtnType JstType
	 * @param args JstArg...
	 */
	public JstMethod(final String name, final JstModifiers modifiers, final IJstType rtnType, final JstArg... args) {

		this(new JstName(name), modifiers, rtnType, args);
	}
	
	/**
	 * Constructor
	 * @param name JstName
	 * @param modifiers JstModifiers
	 * @param rtnType JstType
	 * @param args JstArg...
	 */
	public JstMethod(final JstName name, final JstModifiers modifiers, final IJstType rtnType, final JstArg... args) {

		setName(name);
		m_modifiers = modifiers;
		if (rtnType != null) {
			m_rtnType = new JstTypeReference(rtnType);
			addChild(m_rtnType);
		}
		
		if (args != null && args.length > 0) {
			m_args = new ArrayList<JstArg>(args.length);
			for (JstArg p : args) {
				addArg(p);
			}
		}
	}
	
	//
	// Satisfy IJstMethod
	//
	/**
	 * @see IJstMethod#getName()
	 */
	public JstName getName() {
		return m_name;
	}

	/**
	 * @see IJstMethod#getArgs()
	 */
	public List<JstArg> getArgs() {
		if (m_args == null) {
			return Collections.emptyList();
		}
		return m_args;
	}
	
	/**
	 * @see IJstMethod#isVarArgs()
	 */
	public boolean isVarArgs(){
		if (getArgs().size() == 0){
			return false;
		}
		List<JstArg> args = getArgs();
		return args.get(args.size()-1).isVariable();
	}

	/**
	 * @see IJstMethod#getRtnType()
	 */
	public IJstType getRtnType() {
		return (m_rtnType != null)? m_rtnType.getReferencedType(): null;
	}
	
	/**
	 * @see IJstMethod#getRtnTypeRef()
	 */
	public IJstTypeReference getRtnTypeRef() {
		return m_rtnType;
	}	
	
	/**
	 * @see IJstMethod#getModifiers()
	 */
	public JstModifiers getModifiers() {
		return m_modifiers;
	}
	
	/**
	 * @see IJstMethod#isConstructor()
	 */
	public boolean isConstructor(){
		return m_isConstructor;
	}

	/**
	 * @see IJstMethod#isPublic()
	 */
	public boolean isPublic() {
		return m_modifiers.isPublic();
	}

	/**
	 * @see IJstMethod#isProtected()
	 */
	public boolean isProtected() {
		return m_modifiers.isProtected();
	}

	/**
	 * @see IJstMethod#isInternal()
	 */
	public boolean isInternal() {
		return m_modifiers.isInternal();
	}
	
	/**
	 * @see IJstMethod#isPrivate()
	 */
	public boolean isPrivate() {
		return m_modifiers.isPrivate();
	}

	/**
	 * @see IJstMethod#isStatic()
	 */
	public boolean isStatic() {
		return m_modifiers.isStatic();
	}

	/**
	 * @see IJstMethod#isFinal()
	 */
	public boolean isFinal() {
		return m_modifiers.isFinal();
	}

	/**
	 * @see IJstMethod#isAbstract()
	 */
	public boolean isAbstract() {
		return m_modifiers.isAbstract();
	}

	/**
	 * @see IJstMethod#getBlock()
	 */
	public JstBlock getBlock() {
		return getBlock(false);
	}
	
	/**
	 * @see IJstMethod#isDispatcher()
	 */
	public boolean isDispatcher() {
		return m_overloaded != null && m_overloaded.size() > 0;
	}
	
	/**
	 * @see IJstMethod#getOverloaded()
	 */
	public List<IJstMethod> getOverloaded(){
		if (m_overloaded == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_overloaded);
	}
	
	/**
	 * @see IJstMethod#getOriginalName()
	 */
	public String getOriginalName() {
		if (getName() == null || getName().getName() == null) {
			return null;
		}
		String name = getName().getName();		
		if (m_surffix == null) {
			return name;
		}		
		if(name.indexOf(m_surffix)!=-1){
			return name.substring(0, name.indexOf(m_surffix));
		}else{
			return name;
		}
	}
	
	/**
	 * @see IJstMethod#isParamName(String)
	 */
	public boolean isParamName(String name){
		return name != null && getParamNames().contains(name); 
	}
	
	/**
	 * @see IJstMethod#getParamNames()
	 */
	public synchronized List<String> getParamNames(){
		if (m_paramTypes == null){
			return Collections.emptyList();
		}
		else {
			List<String> list = new ArrayList<String>();
			list.addAll(m_paramTypes.keySet());
			return list;
		}
	}
	
	/**
	 * @see IJstMethod#getParamTypes()
	 */
	public synchronized List<JstParamType> getParamTypes(){
		if (m_paramTypes == null){
			return Collections.emptyList();
		}
		else {
			List<JstParamType> list = new ArrayList<JstParamType>();
			list.addAll(m_paramTypes.values());
			return list;
		}
	}

	//
	// API
	//
	/**
	 * Set name for the method
	 * @param name JstName
	 * @return JstMethod
	 */
	public JstMethod setName(final JstName name) {
		m_name = name;
		removeNameNode();	// method can only have one name
		addChild(name);
		return this;
	}

	private void removeNameNode() {
		for (BaseJstNode node : getChildren()) {
			if (node instanceof JstName) {
				removeChild(node);
				break;
			}
		}
		
	}

	/**
	 * Set name for the method
	 * @param name String
	 * @return JstMethod
	 */
	public JstMethod setName(final String name) {
		return setName(new JstName(name));
	}

	/**
	 * Add parameter to the mthod signiture
	 * @param arg JstArg
	 * @return JstMethod
	 */
	public JstMethod addArg(final JstArg arg) {
		assert arg != null : "arg is null";
		if (m_args == null) {
			m_args = new ArrayList<JstArg>(2);
		}
		m_args.add(arg);
		addChild(arg);
		return this;
	}

	/**
	 * Remove parameters of the mthod signiture
	 * @return JstMethod
	 */
	public JstMethod removeArgs(){		
		if (m_args != null){
			removeChildren(m_args);
			m_args.clear();
		}
		return this;
	}

	/**
	 * Set the return type of the method
	 * @param rtnType JstType
	 * @return JstMethod
	 */
	public JstMethod setRtnType(final IJstType rtnType) {
		if (m_rtnType != null) {
			removeChild(m_rtnType);
			m_rtnType = null;
		}
		if (rtnType != null) {
			m_rtnType = new JstTypeReference(rtnType);
			addChild(m_rtnType);
		}		
		return this;
	}
	
	public JstMethod setReturnOptional(boolean isReturnOptional){
		m_returnOptional = isReturnOptional;
		return this;
	}
	
	public boolean isReturnTypeOptional(){
		return m_returnOptional;
	}

	public JstMethod setRtnRefType(final IJstTypeReference rtnType) {
		if (m_rtnType != null) {
			removeChild(m_rtnType);
			m_rtnType = null;
		}
		if (rtnType != null) {
			m_rtnType = rtnType;
			addChild(m_rtnType);
		}		
		return this;
	}
	/**
	 * Set body for the method
	 * @param block JstBlock
	 * @return JstMethod
	 */
	public JstMethod setBlock(final JstBlock block) {
		removeChild(m_block);
		addChild(block);
		m_block = block;
		if (block == null){
			removeChild(m_block);
		}
		else {
			addChild(block);
		}
		return this;
	}
	
	/**
	 * Answer the body of the method. If body not exist and create
	 * param is true, create and return the body
	 * @param create boolean
	 * @return JstBlock
	 */
	public JstBlock getBlock(boolean create) {
		if (m_block == null && create) {
			setBlock(new JstBlock());
		}
		return m_block;
	}

	/**
	 * Add statement to the body of the method
	 * @param stmt IStmt
	 * @return JstMethod
	 */
	public JstMethod addStmt(final IStmt stmt) {
		assert stmt != null : "stmt cannot be null";
		getBlock(true).addStmt(stmt);
		return this;
	}

	/**
	 * Add varible to the local variable table of the method
	 * @param name String
	 * @param type JstType
	 * @return JstMethod
	 */
	public JstMethod addVarType(final String name, final IJstType type) {
		if (m_varTypes == null) {
			m_varTypes = new LinkedHashMap<String, IJstType>(2);
		}
		m_varTypes.put(name, type);
		return this;
	}
	
	/**
	 * Answer the type of the variable with given name
	 * @param name String
	 * @return JstType
	 */
	public IJstType getVarType(final String name) {
		if (m_varTypes == null) {
			return null;
		}
		return m_varTypes.get(name);
	}

	/**
	 * Answer the access type of the method
	 * @return
	 */
	public String getAccessScope() {
		return m_modifiers.getAccessScope();
	}
	
	/**
	 * {@link IJstMethod#getDoc()}
	 */
	public IJstDoc getDoc() {
		return m_doc;
	}

	public void setOType(JstFunctionRefType funcRef) {
		m_oType = funcRef;
		IJstMethod meth = funcRef.getMethodRef();
		JstTypeHelper.populateMethod(this, meth);
		for (IJstMethod over : meth.getOverloaded()) {
			JstMethod o = new JstMethod(this.getOriginalName());
			
			//set modifiers
			if (getModifiers().isPublic()) {
				o.getModifiers().setPublic();
			} else if (getModifiers().isPrivate()) {
				o.getModifiers().setPrivate();
			} else if (getModifiers().isProtected()) {
				o.getModifiers().setProtected();
			}
			o.getModifiers().setStatic(getModifiers().isStatic());
			
			JstTypeHelper.populateMethod(o, over);
			addOverloaded(o);
		}
		
	}
	
	
	public JstFunctionRefType getOType() {
		return m_oType;
	}
	
	/**
	 * Set JstDoc for this method
	 * @param jstDoc IJstDoc
	 */
	public void setDoc(IJstDoc jstDoc) {
		m_doc = jstDoc;
	}

	@Override
	public void accept(IJstNodeVisitor visitor){
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		Z z = new Z();
		if (m_name != null) {
			z.format("m_name", m_name == null ? null : m_name.getName());
		}
		if (m_modifiers != null) {
			z.format("m_modifiers", m_modifiers);
		}
		if (m_args != null && m_args.size() > 0) {
			StringBuilder sb = new StringBuilder("(");
			for (JstArg a : m_args) {
				if(a.getType()!=null){
					sb.append(a.getType().getName()).append(" ")
							.append(a.getName()).append(",");
				}
			}
			sb.append(")");
			z.format("m_args", sb.toString());
		}
		if (m_varTypes != null && m_varTypes.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (Entry<String, IJstType> entry : m_varTypes.entrySet()) {
				sb.append("\n\t").append(entry.getKey()).append(" ").append(
						entry.getValue().getName());
			}
			z.format("m_varTypes", sb.toString());
		}
		if (getRtnType() != null) {
			z.format("m_rtnType", getRtnType().getName());
		}
		if (m_overloaded != null && m_overloaded.size() > 0) {
			for (IJstMethod overloadedMth : m_overloaded) {
				z.format("overloaded", overloadedMth.toString());
			}
		}
		return z.toString();
	}
	
	public JstMethod setIsConstructor(boolean isConstructor) {
		m_isConstructor = isConstructor;
		return this;
	}
	
	public JstMethod addOverloaded(JstMethod jstMethod) {
		if (m_overloaded == null){
			m_overloaded = new ArrayList<IJstMethod>();
		}
		m_overloaded.add(jstMethod);
		return this;
	}
	
	public JstMethod setOverloaded(List<IJstMethod> jstMethods) {
		if(jstMethods!=null){
			m_overloaded = new ArrayList<IJstMethod>();
			m_overloaded.addAll(jstMethods);
		}else{
			m_overloaded = jstMethods;
		}
		return this;
	}
	
	public String getSurffix() {
		return m_surffix;
	}
	
	public void setSurffix(String surffix) {
		m_surffix = surffix;
	}
	
	public JstParamType addParam(final String paramName){
		if (paramName == null){
			return null;
		}
		synchronized (this){
			if (m_paramTypes == null){
				m_paramTypes = new LinkedHashMap<String,JstParamType>();
			}
			JstParamType pType = m_paramTypes.get(paramName);
			if (pType != null){
				return pType;
			}
			pType = new JstParamType(paramName);
			m_paramTypes.put(paramName,pType);
			return pType;
		}
	}
	
	public synchronized JstParamType getParamType(String name){
		if (m_paramTypes == null){
			return null;
		}
		return m_paramTypes.get(name);
	}
	
	public String getParamsDecoration(){
		if (getParamNames().isEmpty()){
			return EMPTY;
		}
		StringBuilder sb = new StringBuilder("<");
		int i=0;
		IJstType bType;
		for (JstParamType p: getParamTypes()){
			if (i++ > 0){
				sb.append(",");
			}
			sb.append(p.getSimpleName());
			if (!p.getBounds().isEmpty()){
				bType = p.getBounds().get(0);
				sb.append(" extends ").append(bType.getSimpleName());
				if (bType instanceof JstTypeWithArgs){
					sb.append(((JstTypeWithArgs)bType).getArgsDecoration());
				}
			}
		}
		sb.append(">");
		
		return sb.toString();
	}
	
	public boolean isOType() {
		return m_oType!=null;
	}
	
	public boolean isDuplicate() {
		return m_isDuplicate;
	}
	
	public void setIsDuplicate(boolean isDuplicate) {
		m_isDuplicate = isDuplicate;
	}
	
	public boolean isTypeFactoryEnabled() {
		return m_typeFactoryEnabled;
	}
	
	public void setTypeFactoryEnabled(boolean set) {
		m_typeFactoryEnabled = set;
	}
	
	public boolean isFuncArgMetaExtensionEnabled() {
		return m_funcArgMetaExtensionEnabled;
	}
	
	public void setFuncArgMetaExtensionEnabled(boolean set) {
		m_funcArgMetaExtensionEnabled = set;
	}
	
	public void updateArgType(int index, IJstType newType) {
		JstArg arg = m_args.get(index);
		IJstType type = arg.getType();
		if (type == null) return;
		
		arg.updateType(type.getName(), newType);
	}
	
	public static final String OVLD = "_ovld";
	public static String getOverloadSuffix(final IJstType type) {
		if (type == null) {
			return null;
		} else {
			return "_" + type.getSimpleName() + OVLD;
		}
	}

	@Override
	public boolean hasJsAnnotation() {
		return m_hasAnnotation;
	}

	public void setHasJsAnnotation(boolean hasAnnotation) {
		m_hasAnnotation = hasAnnotation;
	}
}
