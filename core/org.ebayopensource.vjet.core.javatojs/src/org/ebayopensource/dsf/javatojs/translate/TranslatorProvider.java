/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.javatojs.translate.custom.CustomTranslateDelegator;

public class TranslatorProvider {

	private Map<Class<?>, BaseTranslator> m_translators = new HashMap<Class<?>, BaseTranslator>(5);
	
	// 
	// Constructor
	//
	public TranslatorProvider(){
		m_translators.put(PackageTranslator.class, new PackageTranslator());
		m_translators.put(UnitTranslator.class, new UnitTranslator());
		m_translators.put(TypeTranslator.class, new TypeTranslator());
		m_translators.put(LiteralTranslator.class, new LiteralTranslator());
		m_translators.put(NameTranslator.class, new NameTranslator());
		m_translators.put(FieldTranslator.class, new FieldTranslator());
		m_translators.put(MethodTranslator.class, new MethodTranslator());
		m_translators.put(ExpressionTranslator.class, new ExpressionTranslator());
		m_translators.put(StatementTranslator.class, new StatementTranslator());
		m_translators.put(DataTypeTranslator.class, new DataTypeTranslator());
		m_translators.put(CustomTranslateDelegator.class, new CustomTranslateDelegator());
		m_translators.put(OtherTranslator.class, new OtherTranslator());
	}
	
	//
	// API
	//
	public BaseTranslator getTranslator(final Class<?> srcType){
		assert srcType != null : "srcType cannot be null";
		return m_translators.get(srcType);
	}
	
	public UnitTranslator getUnitTranslator(){
		return (UnitTranslator)getTranslator(UnitTranslator.class);
	}
	
	public TypeTranslator getTypeTranslator(){
		return (TypeTranslator)getTranslator(TypeTranslator.class);
	}
	
	public PackageTranslator getPackageTranslator(){
		return (PackageTranslator)getTranslator(PackageTranslator.class);
	}
	
	public LiteralTranslator getLiteralTranslator(){
		return (LiteralTranslator)getTranslator(LiteralTranslator.class);
	}
	
	public NameTranslator getNameTranslator(){
		return (NameTranslator)getTranslator(NameTranslator.class);
	}
	
	public FieldTranslator getFieldTranslator(){
		return (FieldTranslator)getTranslator(FieldTranslator.class);
	}
	
	public MethodTranslator getMethodTranslator(){
		return (MethodTranslator)getTranslator(MethodTranslator.class);
	}
	
	public ExpressionTranslator getExprTranslator(){
		return (ExpressionTranslator)getTranslator(ExpressionTranslator.class);
	}
	
	public StatementTranslator getStmtTranslator(){
		return (StatementTranslator)getTranslator(StatementTranslator.class);
	}
	
	public DataTypeTranslator getDataTypeTranslator(){
		return (DataTypeTranslator)getTranslator(DataTypeTranslator.class);
	}
	
	public CustomTranslateDelegator getCustomTranslator(){
		return (CustomTranslateDelegator)getTranslator(CustomTranslateDelegator.class);
	}
	
	public OtherTranslator getOtherTranslator(){
		return (OtherTranslator)getTranslator(OtherTranslator.class);
	}
}
