/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.vjo;

import java.util.HashMap;
import java.util.Map;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.generate.SourceGenerator;


public class GeneratorProvider {
	private Map<Class, SourceGenerator> m_generators = new HashMap<Class, SourceGenerator>(5);
	
	// 
	// Constructor
	//
	public GeneratorProvider(final GeneratorCtx ctx){
		assert ctx != null : "ctx cannot be null";
		m_generators.put(JsCoreGenerator.class, new JsCoreGenerator(ctx));
		m_generators.put(JsClientGenerator.class, new JsClientGenerator(ctx));
		m_generators.put(VjoGenerator.class, new VjoGenerator(ctx));
		m_generators.put(MtdGenerator.class, new MtdGenerator(ctx));
		m_generators.put(BodyGenerator.class, new BodyGenerator(ctx));
		m_generators.put(StmtGenerator.class, new StmtGenerator(ctx));
		m_generators.put(ExprGenerator.class, new ExprGenerator(ctx));
		m_generators.put(FragmentGenerator.class, new FragmentGenerator(ctx));
		m_generators.put(JsDocGenerator.class, new JsDocGenerator(ctx));
	}
	
	//
	// API
	//
	public void setStyle(CodeStyle style) {
		for (SourceGenerator generator: m_generators.values()){
			generator.setStyle(style);
		}
	}
	
	public void setNewline(String newline) {
		for (SourceGenerator generator: m_generators.values()){
			generator.setNewline(newline);
		}
	}
	
	public SourceGenerator getGenerator(final Class srcType){
		assert srcType != null : "srcType cannot be null";
		return m_generators.get(srcType);
	}
	
	public JsCoreGenerator getJsCoreGenerator(){
		return (JsCoreGenerator)getGenerator(JsCoreGenerator.class);
	}
	
	public JsClientGenerator getJsClientGenerator(){
		return (JsClientGenerator)getGenerator(JsClientGenerator.class);
	}
	
	public VjoGenerator getTypeGenerator(){
		return (VjoGenerator)getGenerator(VjoGenerator.class);
	}
	
	public MtdGenerator getMtdGenerator(){
		return (MtdGenerator)getGenerator(MtdGenerator.class);
	}
	
	public BodyGenerator getBodyGenerator(){
		return (BodyGenerator)getGenerator(BodyGenerator.class);
	}
	
	public StmtGenerator getStmtGenerator(){
		return (StmtGenerator)getGenerator(StmtGenerator.class);
	}
	
	public ExprGenerator getExprGenerator(){
		return (ExprGenerator)getGenerator(ExprGenerator.class);
	}
	
	public FragmentGenerator getFragmentGenerator(){
		return (FragmentGenerator)getGenerator(FragmentGenerator.class);
	}
	
	public JsDocGenerator getJsDocGenerator(){
		return (JsDocGenerator)getGenerator(JsDocGenerator.class);
	}
}
