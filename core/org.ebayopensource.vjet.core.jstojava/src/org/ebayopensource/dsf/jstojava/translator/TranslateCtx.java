/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jsgen.shared.validation.common.ScopeId;
import org.ebayopensource.dsf.jst.IJstOType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstFunctionRefType;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jstojava.parser.comments.CommentCollector;
import org.ebayopensource.dsf.jstojava.report.DefaultErrorReporter;
import org.ebayopensource.dsf.jstojava.report.ErrorReporter;
import org.ebayopensource.dsf.jstojava.translator.robust.IRobustTranslator;
import org.ebayopensource.dsf.jstojava.translator.robust.JstSourceUtil;
import org.ebayopensource.dsf.jstojava.translator.robust.VjoSectionTranlationProvider;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.vjo.meta.VjoConvention;
import org.eclipse.mod.wst.jsdt.internal.compiler.ast.CompilationUnitDeclaration;

public class TranslateCtx implements IFindTypeSupport{

	private TranslatorProvider m_provider;
	private CommentCollector m_comments;
	private VjoConvention m_convention;
	private boolean m_allowStatementsProcessing = true;
	private int completionPos = -1; // without completion
	private boolean isCreatedCompletion = false;
	private Stack<SourceOffset> currentBlock = new Stack<SourceOffset>();
	{// add inital block
		currentBlock.push(new SourceOffset());
	}
	private Stack<ScopeId> blockStack = new Stack<ScopeId>();
	private JstType m_currentType;
	private HashMap<String, IJstType> m_typeSymbolMap = null;
	private CompilationUnitDeclaration m_ast;
	private String m_originalSource;
	private String group;
	private ErrorReporter m_errorReporter;
	private List<JstCompletion> m_syntaxErrors;
	private JstSourceUtil m_sourceUtil;
	private TranslateConfig m_config;
	
	private List<JstBlock> m_listBlocks = null; // JstBlock list in IScriptUnit
	private Map<IJstType, JstFunctionRefType> m_functionRefReplacement;
	private ISectionTranslatorProvider m_sectionTranslatorProvider;
	private String m_scopeForGlobals = null;
	private JstSourceUtil m_lineProvider;
	private List<JstCompletion> m_getBlockCompletions;

	public TranslateCtx() {
		m_config = new TranslateConfig();
	}
	public TranslateCtx(TranslateConfig config) {
		if (config == null) {
			config = new TranslateConfig();
		}
		m_config = config;
	}

	public static class SourceOffset {
		int previousNodeSourceEnd = 0;
		int nextNodeSourceStart = 0;
	}

	//
	// API
	//

	public void setCompletionPos(int completionPos) {
		this.completionPos = completionPos;
	}

	public int getCompletionPos() {
		return completionPos;
	}

	public TranslatorProvider getProvider() {
		if (m_provider == null) {
			return m_provider = new TranslatorProvider(this);
		}
		return m_provider;
	}

	public void setProvider(TranslatorProvider provider) {
		m_provider = provider;
	}

	public CommentCollector getCommentCollector() {
		if (m_comments == null) {
			m_comments = new CommentCollector();
		}
		return m_comments;
	}

	public void setCommentCollector(CommentCollector comments) {
		this.m_comments = comments;
	}

	public boolean isAllowPartialJST() {
		return m_config.isAllowPartialJST();
	}

	public ErrorReporter getErrorReporter() {
		if (m_errorReporter == null) {
			m_errorReporter = new DefaultErrorReporter();
		}
		return m_errorReporter;
	}

	protected VjoConvention getVjoConvention() {
		if (m_convention == null) {
			m_convention = new VjoConvention();
		}
		return m_convention;
	}

	public void setAllowStatementsProcessing(boolean allow) {
		this.m_allowStatementsProcessing = allow;
	}

	public boolean isAllowStatementsProcesing() {
		return m_allowStatementsProcessing;
	}

	public char[] getOriginalSource() {
		return m_ast.compilationResult.compilationUnit.getContents();
	}

	public String getOriginalSourceAsString() {
		if (m_originalSource == null && getOriginalSource() != null) {
			m_originalSource = String.valueOf(getOriginalSource());
		}
		return m_originalSource;
	}

	public boolean isCreatedCompletion() {
		return isCreatedCompletion;
	}

	public void setCreatedCompletion(boolean isCreatedCompletion) {
		this.isCreatedCompletion = isCreatedCompletion;
	}

	public int getPreviousNodeSourceEnd() {
		// return previousNodeSourceEnd;
		return getCurrentSourceOffset().previousNodeSourceEnd;
	}

	public void setPreviousNodeSourceEnd(int previousNodeSourceEnd) {
		// this.previousNodeSourceEnd = previousNodeSourceEnd;
		getCurrentSourceOffset().previousNodeSourceEnd = previousNodeSourceEnd;
	}

	public int getNextNodeSourceStart() {
		return getCurrentSourceOffset().nextNodeSourceStart;
		// return nextNodeSourceStart;
	}

	public void setNextNodeSourceStart(int nextNodeSourceStart) {
		// this.nextNodeSourceStart = nextNodeSourceStart;
		getCurrentSourceOffset().nextNodeSourceStart = nextNodeSourceStart;
	}

	public void enterBlock(ScopeId scope) {
		blockStack.push(scope);
		currentBlock.push(new SourceOffset());
	}

	public void exitBlock() {
		try {
			blockStack.pop();
			currentBlock.pop();
		} catch (EmptyStackException e) {
			// just ignore
		}
	}

	public ScopeId getCurrentScope() {
		try {
			if(blockStack.isEmpty()){
				return ScopeIds.GLOBAL;
			}
			return blockStack.peek();
		} catch (EmptyStackException e) {
			return ScopeIds.GLOBAL;
		}
	}

	public void setCurrentType(JstType type) {
		this.m_currentType = type;
	}

	public JstType getCurrentType() {
		return m_currentType;
	}

	public void setAST(CompilationUnitDeclaration ast) {
		this.m_ast = ast;
	}

	public CompilationUnitDeclaration getAST() {
		return m_ast;
	}

	public Stack<ScopeId> getScopeStack() {
		return blockStack;
	}

	private SourceOffset getCurrentSourceOffset() {
		int size = currentBlock.size();
		if (size == 0) {
			return null;// throw exception
		}
		return currentBlock.get(size - 1);

	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public boolean isSkiptImplementation() {
		return m_config.isSkiptImplementation();
	}
	
	public void setScriptUnitBlockList(List<JstBlock> blockList) {
		m_listBlocks = blockList;
	}
	
	public List<JstBlock> getScriptUnitBlockList() {
		return m_listBlocks;
	}

	public void addSyntaxError(JstCompletion completion) {
		getSyntaxErrors().add(completion);
	}

	public List<JstCompletion> getJstErrors() {
		return Collections.unmodifiableList(getSyntaxErrors());
	}

	private List<JstCompletion> getSyntaxErrors() {
		if (m_syntaxErrors == null) {
			m_syntaxErrors = new ArrayList<JstCompletion>();
		}
		return m_syntaxErrors;
	}

	/**
	 * added because AST parser doesn't provide line number or column for tokens
	 * only offset values
	 * 
	 * @return
	 */
	public JstSourceUtil getSourceUtil() {
		if (m_sourceUtil == null && getAST()!=null) {
			m_sourceUtil = new JstSourceUtil(getOriginalSource());
		}
		return m_sourceUtil;
	}

	public boolean isSkipJsExtSyntaxArgs(){
		return m_config.isSkipJsExtSyntaxArgs();
	}
	
	public synchronized HashMap<String, IJstType> getTypeSymbolMap() {
		
		ScopeId scope = getCurrentScope();
		
		JstType type = m_currentType;
		
		if (type == null || scope.equals(ScopeIds.GLOBAL)) {
			return null;
		}
		
		if (type instanceof IJstOType) {
			type = (JstType) type.getParentNode();
		}
		
		while (type != null && type.isEmbededType()) {
			type = type.getOuterType();
		}
		
		if (m_typeSymbolMap == null && type != null) {
			m_typeSymbolMap = new LinkedHashMap<String, IJstType>();
					
			// add itself
			m_typeSymbolMap.put(type.getSimpleName(), type);
			m_typeSymbolMap.put(type.getName(), type);
			
			addToTypeSymbolMap(type.getImportsMap());
			addToTypeSymbolMap(type.getInactiveImportsMap());
			addToTypeSymbolMap(type.getSatisfies());
			addToTypeSymbolMap(type.getExtends());
			
			final List<IJstType> paramTypes = new LinkedList<IJstType>();
			for(JstParamType paramType : type.getParamTypes()){
				paramTypes.add(paramType);
			}
			addToTypeSymbolMap(paramTypes);
		}		
		
		return m_typeSymbolMap;
	}
	
	private void addToTypeSymbolMap(Map<String, ? extends IJstType> map) {
		
		if (m_typeSymbolMap != null) {
			for (Map.Entry<String, ? extends IJstType> entry : map.entrySet()) {
				String key = entry.getKey();
				IJstType value = entry.getValue();
				m_typeSymbolMap.put(key, value); // alias or simple name
				m_typeSymbolMap.put(value.getName(), value); // full name
			}	
		}
	}
	
	private void addToTypeSymbolMap(List<IJstType> list) {
		if (m_typeSymbolMap != null) {
			for (IJstType type : list) {
				m_typeSymbolMap.put(type.getSimpleName(), type);
			}
		}
	}
	public void addTypeReplacement(JstType typeToReplace, JstFunctionRefType ref) {
		if(m_functionRefReplacement == null){
			m_functionRefReplacement = new LinkedHashMap<IJstType, JstFunctionRefType>();
		}
		m_functionRefReplacement.put(typeToReplace, ref);
	}
	public boolean hasFunctionTypeRefReplacements() {
		if(m_functionRefReplacement!=null && m_functionRefReplacement.size()>0){
			return true;
		}
		return false;
	}
	public Map<IJstType, JstFunctionRefType> getFunctionTypeRefReplacements() {
		return m_functionRefReplacement;
	}
	public Class<? extends IRobustTranslator> getSectionTranslator(String token) {
		
		if(m_sectionTranslatorProvider==null){
			m_sectionTranslatorProvider = new VjoSectionTranlationProvider();
		}
		return m_sectionTranslatorProvider.getTranslator(token);
		

	}
	public String[] getSections() {
		return m_sectionTranslatorProvider.getSections();
	}
	
	public void setScopeForGlobals(String scopeForGlobals) {
		m_scopeForGlobals = scopeForGlobals;
	}
	
	public String getScopeForGlobals() {
		return m_scopeForGlobals;
	}
	
	@Override
	public ILineInfoProvider getLineInfoProvider() {
		if(m_lineProvider==null){
			m_lineProvider = new JstSourceUtil(getOriginalSource());
		}
		return m_lineProvider;
	
	}
//	@Override
//	public Map<String, IJstType> getSymbol2TypeMap() {
//		return getTypeSymbolMap();
//	}
	@Override
	public IJstType findTypeByName(String longname) {
		final Map<String, IJstType> typeNameMap = getTypeSymbolMap();
		if (typeNameMap != null) {
			longname = longname.trim();

			for (Map.Entry<String, ? extends IJstType> entry : typeNameMap
					.entrySet()) {
				if (entry.getKey() == null) {
					return null;
				}
				String typeName = entry.getKey().trim();

				if (longname.startsWith(typeName)) {
					IJstType type = entry.getValue();
					int shortLen = typeName.length();
					int longLen = longname.length();

					if (shortLen == longLen) { // exact match
						return type;
					} else if (longname.charAt(shortLen) == '.') {
						if (shortLen + 1 < longLen) {
							type = TranslateHelper.findInnerOrOType(type, longname
									.substring(shortLen + 1));

							if (type != null) {
								return type;
							}
						}
					}
				}
			}
		}
		else{
			if (ScopeIds.GLOBAL.equals(getCurrentScope()) && m_currentType != null) {
				final IJstType paramType = m_currentType.getParamType(longname);
				return paramType;
			}
		}
		return null;
	}
	public void addBlockCompletion(JstCompletion cmp) {
		getBlockCompletions().add(cmp);
	}
	
	
	public List<JstCompletion> getBlockCompletions() {
		if (m_getBlockCompletions == null) {
			m_getBlockCompletions = new ArrayList<JstCompletion>();
		}
		return m_getBlockCompletions;
	}

	
	
}
