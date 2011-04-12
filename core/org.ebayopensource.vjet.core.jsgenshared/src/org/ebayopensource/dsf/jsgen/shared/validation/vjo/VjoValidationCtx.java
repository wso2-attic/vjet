/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationDriver.VjoValidationMode;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoMethodControlFlowTable;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoMethodReferencesTable;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.VjoPropertyStatesTable;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoScope;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol.VjoSymbolTable;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.term.ObjLiteral;

public class VjoValidationCtx {
	
	/**
	 * file path to check against public none-embedded type name
	 */
	private String m_path;
	
	/**
	 * node being validated/visited
	 */
	private IJstNode m_jstNode;
	
	/**
	 * nodes visited to stop potential bad structured tree traversals
	 */
	private Set<IJstNode> m_visitedNodes;
	
	private Set<String> m_jstTypeNames;
	
	private String m_groupId;
	
	/**
	 * global scope
	 */
	private IJstType m_global;
	
	private IJstType m_package;
	
	/**
	 * jst type associated with all its dependencies, should include its own
	 */
	private Map<IJstType, IVjoDependencyVerifiable> m_type2Dependencies;
	
	private Map<IJstType, List<IScriptProblem>> m_type2SyntaxProblems;
	
	/**
	 * jst type mapped to its corresponding typespace type, needed for unit tests, not for ede runtime
	 */
	private Map<IJstType, IJstType> m_type2TypeSpacedTypeMap;
	
	/**
	 * current scope, chain of method nodes and type nodes
	 */
	private VjoScope m_scope;
	
	/**
	 * vjo.make anonymous type bound with its scope type
	 */
	private Map<IJstType, IJstType> m_makeType2scopeTypeMap;

	/**
	 * vjo.make anonymous type bound with its parent type
	 */
	private Map<IJstType, IJstType> m_makeType2parentTypeMap;

	/**
	 * vjo.make anonymous type bound with its source type
	 */
	private Map<IJstType, IJstType> m_makeType2sourceTypeMap;

	/**
	 * vjo.make anonymous type bound with its obj literal (protos) if available
	 */
	private Map<ObjLiteral, IJstType> m_objLiteral2MakeTypeMap;
	
	/**
	 * bind generics parameter type with runtime resolved type;
	 */
	private Map<JstParamType, IJstType> m_paramType2ResolvedTypeMap;
	
	/**
	 * semantic problems associated with jst nodes
	 */
	private Map<IJstNode, List<VjoSemanticProblem>> m_node2Problems;

	
	/**
	 * inactive needs validation requires IExpr & ObjCreationExpr examination 
	 */
	private Map<IJstType, Set<IJstRefType>> m_knownActivelyNeededTypes;
	private Map<IJstType, Set<IJstRefType>> m_mustActivelyNeededTypes;
	
	/**
	 * symbol table for symbol lookups
	 */
	private VjoSymbolTable m_symbolTable = new VjoSymbolTable();

	/**
	 * return flow table for checkin unreturned flows in none-void methods
	 */
	private VjoMethodControlFlowTable m_methodFlowTable = new VjoMethodControlFlowTable();
	
	/**
	 * method invocation table for identifying unused + unvisible methods
	 */
	private VjoMethodReferencesTable m_methodInvocationTable = new VjoMethodReferencesTable();
	
	/**
	 * property references and assignment state holder
	 */
	private VjoPropertyStatesTable m_propertyStatesTable = new VjoPropertyStatesTable();
	
	/**
	 * Map to keep track of final property initialization
	 * An uninitialized final instance property must be initialized in all
	 * constructors
	 */
	private Map<IJstProperty, List<IJstMethod>> m_finalPropertyInitMap = 
		new HashMap<IJstProperty, List<IJstMethod>>();
	
	/**
	 * types yet initialized for detecting bad properties' initializations
	 */
	private Set<IJstType> m_uninitializedTypes;
	
	private Set<IJstType> m_missingImportTypes;
	
	private Set<String> m_unresolvedTypes;
	
	private VjoValidationMode m_validationMode = VjoValidationMode.validateType;
	
	public Set<IJstType> getUninitializedTypes(){
		if(m_uninitializedTypes == null){
			return Collections.emptySet();
		}
		else{
			return Collections.unmodifiableSet(m_uninitializedTypes);
		}
	}
	
	public void addUnintializedType(IJstType type){
		if(m_uninitializedTypes == null){
			m_uninitializedTypes = new HashSet<IJstType>();
		}
		if(!m_uninitializedTypes.contains(type)){
			if(!"Object".equals(type.getName())
					&& !"vjo.Object".equals(type.getName())){
				m_uninitializedTypes.add(type);
			}
		}
	}
	
	public void removeUnintializedType(IJstType type){
		if(m_uninitializedTypes != null){
			m_uninitializedTypes.remove(type);
		}
	}
	
	public void clearUnintializedTypes(){
		if(m_uninitializedTypes != null){
			m_uninitializedTypes.clear();
		}
	}
	
	public Set<IJstType> getMissingImportTypes(){
		if(m_missingImportTypes == null){
			return Collections.emptySet();
		}
		else{
			return Collections.unmodifiableSet(m_missingImportTypes);
		}
	}
	
	public Set<String> getUnresolvedTypes(){
		if(m_unresolvedTypes == null){
			return Collections.emptySet();
		}
		else{
			return Collections.unmodifiableSet(m_unresolvedTypes);
		}
	}
	
	public void addMissingImportType(IJstType type){
		if(m_missingImportTypes == null){
			m_missingImportTypes = new HashSet<IJstType>();
		}
		if(!m_missingImportTypes.contains(type)){
			m_missingImportTypes.add(type);
		}
	}
	
	public void addUnresolvedType(String type){
		if(m_unresolvedTypes == null){
			m_unresolvedTypes = new HashSet<String>();
		}
		if(!m_unresolvedTypes.contains(type)){
			m_unresolvedTypes.add(type);
		}
	}
	
	public void removeMissingImportType(IJstType type){
		if(m_missingImportTypes != null){
			m_missingImportTypes.remove(type);
		}
	}
	
	public void removeUnrsolvedType(String type){
		if(m_unresolvedTypes != null){
			m_unresolvedTypes.remove(type);
		}
	}
	
	public void clearMissingImportTypes(){
		if(m_missingImportTypes != null){
			m_missingImportTypes.clear();
		}
	}
	
	public void clearUnresolvedTypes(){
		if(m_unresolvedTypes != null){
			m_unresolvedTypes.clear();
		}
	}
	
	public IJstType getMakeScopeType(IJstType makeType){
		if(m_makeType2scopeTypeMap == null){
			return null;
		}
		return m_makeType2scopeTypeMap.get(makeType);
	}
	
	public VjoValidationCtx setMakeScopeType(IJstType makeType, IJstType scopeType){
		if(m_makeType2scopeTypeMap == null){
			m_makeType2scopeTypeMap = new HashMap<IJstType, IJstType>();
		}
		m_makeType2scopeTypeMap.put(makeType, scopeType);
		getDependencyVerifier(makeType).addDependency(scopeType);
		return this;
	}
	
	public IJstType getMakeParentType(IJstType makeType){
		if(m_makeType2parentTypeMap == null){
			return null;
		}
		return m_makeType2parentTypeMap.get(makeType);
	}
	
	public VjoValidationCtx setMakeParentType(IJstType makeType, IJstType parentType){
		if(m_makeType2parentTypeMap == null){
			m_makeType2parentTypeMap = new HashMap<IJstType, IJstType>();
		}
		m_makeType2parentTypeMap.put(makeType, parentType);
		
		getDependencyVerifier(makeType).addDependency(parentType);
		return this;
	}

	public IJstType getMakeSourceType(IJstType makeType){
		if(m_makeType2sourceTypeMap == null){
			return null;
		}
		return m_makeType2sourceTypeMap.get(makeType);
	}
	
	public VjoValidationCtx setMakeSourceType(IJstType makeType, IJstType sourceType){
		if(m_makeType2sourceTypeMap == null){
			m_makeType2sourceTypeMap = new HashMap<IJstType, IJstType>();
		}
		
		m_makeType2sourceTypeMap.put(makeType, sourceType);
		getDependencyVerifier(makeType).addDependency(sourceType);
		return this;
	}
	
	public IJstType getMakeTypeByObjLiteral(ObjLiteral objLiteral){
		if(m_objLiteral2MakeTypeMap == null){
			return null;
		}
		return m_objLiteral2MakeTypeMap.get(objLiteral);
	}
	
	public VjoValidationCtx setMakeTypeForObjLiteral(ObjLiteral objLiteral, IJstType makeType){
		if(m_objLiteral2MakeTypeMap == null){
			m_objLiteral2MakeTypeMap = new HashMap<ObjLiteral, IJstType>();
		}
		m_objLiteral2MakeTypeMap.put(objLiteral, makeType);
		return this;
	}
	
	public IJstType getResolvedTypeByParamType(JstParamType paramType){
		if(m_paramType2ResolvedTypeMap == null || paramType == null){
			return null;
		}
		return m_paramType2ResolvedTypeMap.get(paramType);
	}
	
	public VjoValidationCtx setResolvedTypeForParamType(JstParamType paramType, IJstType resolvedType){
		if(m_paramType2ResolvedTypeMap == null){
			m_paramType2ResolvedTypeMap = new HashMap<JstParamType, IJstType>();
		}
		m_paramType2ResolvedTypeMap.put(paramType, resolvedType);
		return this;
	}
	
	public VjoValidationCtx resetResolvedTypeByParamType(JstParamType paramType){
		if(m_paramType2ResolvedTypeMap != null && paramType != null){
			m_paramType2ResolvedTypeMap.remove(paramType);
		}
		return this;
	}
	
	public String getPath(){
		return m_path;
	}
	
	public VjoValidationCtx setPath(String path){
		m_path = path;
		return this;
	}
	
	public IJstNode getJstNode(){
		return m_jstNode;
	}
	
	public VjoValidationCtx setJstNode(IJstNode node){
		m_jstNode = node;
//		getVisitedJstNodes().add(node);
		return this;
	}
	
	public Set<IJstNode> getVisitedJstNodes(){
		if(m_visitedNodes == null){
			m_visitedNodes = new HashSet<IJstNode>();
		}
		
		return m_visitedNodes;
	}
	
	public Set<String> getJstTypeNames(){
		if(m_jstTypeNames == null){
			m_jstTypeNames = new HashSet<String>();
		}
		return m_jstTypeNames;
	}
	
	public VjoValidationCtx addJstTypeName(String name){
		if(m_jstTypeNames == null){
			m_jstTypeNames = new HashSet<String>();
		}
		m_jstTypeNames.add(name);
		return this;
	}
	
	public String getGroupId(){
		return m_groupId;
	}
	
	public VjoValidationCtx setGroupId(String groupId){
		m_groupId = groupId;
		return this;
	}
	
	public IJstType getGlobal(){
		return m_global;
	}
	
	public VjoValidationCtx setGlobal(IJstType global){
		m_global = global;
		return this;
	}
	
	public IJstType getPackage(){
		return m_package;
	}
	
	public VjoValidationCtx setPackage(IJstType vjoPackage){
		m_package = vjoPackage;
		return this;
	}
	
	/**
	 * <p>
	 * 	type's dependencies are important, dependencies must be resolved before validation could be performed correctly
	 *  one type's normal dependencies are as listed below:
	 *  	<ol>
	 *  		<li>imported types (needs)</li>
	 *  		<li>inherited types (extends)</li>
	 *  		<li>implemented types (satisfies)</li>
	 *  		<li>mixin types (mixin)</li>
	 *  		<li>outer type (inherent)</li>
	 *  		<li>containing types (inherent)</li>
	 *  		<li>inner types (excluded)
	 *  			inner types are excluded from dependencies to avoid circular dependencies chains
	 *  		</li>
	 *  	</ol>
	 *  
	 *  type's dependencies are to resolved on the very entry of validation, {@link VjoValidationDriver#calculateDependencies}
	 * </p>
	 * @param type
	 * @param dependencies
	 */
	public void setDependencyVerifier(IJstType type, IVjoDependencyVerifiable verifier){
		if(m_type2Dependencies == null){
			m_type2Dependencies = new HashMap<IJstType, IVjoDependencyVerifiable>();
		}
		m_type2Dependencies.put(type, verifier);
	}
	
	public IVjoDependencyVerifiable getDependencyVerifier(IJstType type){
		final IVjoDependencyVerifiable verifier = m_type2Dependencies.get(type);
		return verifier == null ? IVjoDependencyVerifiable.FALSE_VERIFIER : verifier;
	}
	
	public void addSyntaxProblems(IJstType type, Collection<IScriptProblem> syntaxProblems){
		if(m_type2SyntaxProblems == null){
			m_type2SyntaxProblems = new HashMap<IJstType, List<IScriptProblem>>();
		}
		
		List<IScriptProblem> problems = m_type2SyntaxProblems.get(type);
		if(problems == null){
			problems = new ArrayList<IScriptProblem>();
			m_type2SyntaxProblems.put(type, problems);
		}
		
		if(syntaxProblems != null){
			problems.addAll(syntaxProblems);
		}
	}
	
	public List<IScriptProblem> getSyntaxProblems(IJstType type){
		if(m_type2SyntaxProblems == null){
			return Collections.emptyList();
		}
		List<IScriptProblem> toReturn = m_type2SyntaxProblems.get(type);
		if(toReturn == null){
			return Collections.emptyList();
		}
		else{
			return Collections.unmodifiableList(toReturn);
		}
	}
	
	public void addTypeSpaceType(IJstType type, IJstType typeSpaceType){
		if(m_type2TypeSpacedTypeMap == null){
			m_type2TypeSpacedTypeMap = new HashMap<IJstType, IJstType>();
		}
		m_type2TypeSpacedTypeMap.put(type, typeSpaceType);
	}
	
	public IJstType getTypeSpaceType(IJstType type){
		if(m_type2TypeSpacedTypeMap == null){
			return type;
		}
		else{
			IJstType typeSpaceType = m_type2TypeSpacedTypeMap.get(type);
			if(typeSpaceType == null){
				typeSpaceType = type;
			}
			return typeSpaceType;
		}
	}
	
	public IJstType getCacheType(String typeName){
		if(typeName == null){
			return null;
		}
		
		IJstType cacheType = getScope().getClosestScopeNode().getOwnerType();
		for(IJstType depType: getDependencyVerifier(cacheType).getDirectDependenciesFilteredByGroup(cacheType)){
			if(typeName.equals(depType.getName())){
				return getTypeSpaceType(depType);
			}
		}

		if(cacheType == null){
			cacheType = JstCache.getInstance().getType(typeName);
			if(cacheType != null){
				return cacheType;
			}
		}
		else{
			return cacheType;
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * 	record semantic problem bound with its source node
	 * </p>
	 * @param node
	 * @param problem
	 */
	public void addProblem(IJstNode node, VjoSemanticProblem problem){
		if(m_node2Problems == null){
			m_node2Problems = new HashMap<IJstNode, List<VjoSemanticProblem>>();
		}
		else if(problem == null){
			return;
		}
		else if(problem.getSourceLineNumber() < 0){
			return;
		}
		
		List<VjoSemanticProblem> problems4Node = m_node2Problems.get(node);
		if(problems4Node == null){
			problems4Node = new ArrayList<VjoSemanticProblem>();
			if(node != null){
				m_node2Problems.put(node, problems4Node);
			}
		}
		
		//bugfix, eliminate duplicate problems 5614 bugfix 
//		if(!problems4Node.contains(problem)){
			problems4Node.add(problem);
//		}
	}
	
	public void addProblems(IJstNode node, List<VjoSemanticProblem> problems){
		if(m_node2Problems == null){
			m_node2Problems = new HashMap<IJstNode, List<VjoSemanticProblem>>();
		}
		List<VjoSemanticProblem> problems4Validator = m_node2Problems.get(node);
		if(problems4Validator == null){
			problems4Validator = new ArrayList<VjoSemanticProblem>();
			if(node != null){
				m_node2Problems.put(node, problems4Validator);
			}
		}
		
		problems4Validator.addAll(problems);
	}
	
	public List<VjoSemanticProblem> getProblems(IJstNode node){
		return getProblems(node, false);
	}
	
	public List<VjoSemanticProblem> getProblems(IJstNode node, boolean recursive){
		return getProblems(node, recursive, null);
	}
	
	public List<VjoSemanticProblem> getProblems(IJstNode node, boolean recursive, List<IJstNode> stopSigns){
		if(m_node2Problems == null || node == null){
			return Collections.emptyList();
		}
		
		List<VjoSemanticProblem> problems4Validator = m_node2Problems.get(node);
		if(problems4Validator == null){
			problems4Validator = new ArrayList<VjoSemanticProblem>();
		}
		
		if(recursive){
			for(IJstNode child: node.getChildren()){
				//skip
				if(stopSigns != null && stopSigns.contains(child)){
					continue;
				}
				
				problems4Validator.addAll(getProblems(child, true, stopSigns));
			}
			
			if(node instanceof JstMethod){
				problems4Validator.addAll(getProblems(((JstMethod)node).getName(), true, stopSigns));
			}
		}
		
		return Collections.unmodifiableList(problems4Validator);
	}
	
	public boolean removeProblem(IJstNode node, VjoSemanticProblem problem){
		return removeProblem(node, problem, false);
	}
	
	public void removeProblems(IJstNode node){
		removeProblems(node, false);
	}
	
	public boolean removeProblem(IJstNode node, VjoSemanticProblem problem, boolean recursive){
		return removeProblem(node, problem, recursive, null);
	}
	
	public void removeProblems(IJstNode node, boolean recursive){
		removeProblems(node, recursive, new ArrayList<IJstNode>());
	}
	
	public boolean removeProblem(IJstNode node, VjoSemanticProblem problem, boolean recursive, List<IJstNode> stopSigns){
		return removeProblem(node, problem, recursive, stopSigns, new HashSet<IJstNode>());
	}
	
	public void removeProblems(IJstNode node, boolean recursive, List<IJstNode> stopSigns){
		removeProblems(node, recursive, stopSigns, new HashSet<IJstNode>());
	}
	
	/**
	 * <p>
	 * 	not all the semantic problems will be eventually surfaced to the user, due to following reasons:
	 *  <ol>
	 *  	<li>for overloading methods, some validation errors are caused by the particular parameter type, we have to tolerate that</li>
	 *  	<li>for SUPRESSTYPECHECK annotation support, some validation errors should be hidden</li>
	 *  </ol>
	 * </p>
	 * @param node [root node of problems]
	 * @param problem [specific problem to be removed]
	 * @param recursive [recursive flag]
	 * @param stopSigns [recursion break signs]
	 * @param visited [circular path protection]
	 * @return
	 */
	public boolean removeProblem(IJstNode node, VjoSemanticProblem problem, boolean recursive, List<IJstNode> stopSigns, Set<IJstNode> visited){
		if(m_node2Problems == null || node == null){
			return false;
		}
		if(visited.contains(node)){
			return false;
		}
		else{
			visited.add(node);
		}
		
		List<VjoSemanticProblem> node2Problems = m_node2Problems.get(node);
		if((node2Problems == null || !node2Problems.remove(problem)) 
				&& recursive){
			for(IJstNode child : node.getChildren()){
				if(stopSigns != null && stopSigns.contains(child)){
					continue;
				}
				if(removeProblem(child, problem, true, stopSigns, visited)){
					return true;
				}
			}
			
			if(node instanceof IJstMethod){
				for(IJstMethod child: ((IJstMethod)node).getOverloaded()){
					if(stopSigns != null && stopSigns.contains(child)){
						continue;
					}
					if(removeProblem(child, problem, true, stopSigns, visited)){
						return true;
					}
				}
				if(removeProblem(((IJstMethod)node).getName(), problem, true, stopSigns, visited)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void removeProblems(IJstNode node, boolean recursive, List<IJstNode> stopSigns, Set<IJstNode> visited){
		if(m_node2Problems == null || node == null){
			return;
		}
		if(visited.contains(node)){
			return;
		}
		else{
			visited.add(node);
		}
		
		List<VjoSemanticProblem> node2Problems = m_node2Problems.get(node);
		if(node2Problems != null){
			node2Problems.clear();
		}
			
		if(recursive){
			for(IJstNode child : node.getChildren()){
				if(stopSigns != null && stopSigns.contains(child)){
					continue;
				}
				removeProblems(child, true, stopSigns, visited);
			}
			
			if(node instanceof IJstMethod){
				for(IJstMethod child: ((IJstMethod)node).getOverloaded()){
					if(stopSigns != null && stopSigns.contains(child)){
						continue;
					}
					removeProblems(child, true, stopSigns, visited);
				}
				removeProblems(((IJstMethod)node).getName(), true, stopSigns, visited);
			}
		}
	}
	
	public List<VjoSemanticProblem> getAllProblems(){
		if(m_node2Problems == null){
			return Collections.emptyList();
		}
		List<VjoSemanticProblem> problems = new ArrayList<VjoSemanticProblem>();
		for(List<VjoSemanticProblem> problems4Validator : m_node2Problems.values()){
			problems.addAll(problems4Validator);
		}
		return problems;
	}
	
	public void resetProblems(){
		if(m_node2Problems != null){
			m_node2Problems.clear();
		}
	}
	
	public Set<IJstRefType> getKnownActivelyNeededTypes(final IJstType jstType){
		if(m_knownActivelyNeededTypes == null){
			return Collections.emptySet();
		}
		final Set<IJstRefType> exprs = m_knownActivelyNeededTypes.get(jstType);
		if(exprs == null){
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(exprs);
	}
	
	public void addKnownActivelyNeededTypes(final IJstType jstType, final IJstRefType neededType){
		if(m_knownActivelyNeededTypes == null){
			m_knownActivelyNeededTypes = new HashMap<IJstType, Set<IJstRefType>>();
		}
		
		Set<IJstRefType> exprs = m_knownActivelyNeededTypes.get(jstType);
		if(exprs == null){
			exprs = new HashSet<IJstRefType>();
			m_knownActivelyNeededTypes.put(jstType, exprs);
		}
		exprs.add(neededType);
	}
	
	public void resetKnownActilveyNeededTypes(final IJstType jstType){
		if(m_knownActivelyNeededTypes != null){
			m_knownActivelyNeededTypes.clear();
		}
	}
	
	public Set<IJstRefType> getMustActivelyNeededTypes(final IJstType jstType){
		if(m_mustActivelyNeededTypes == null){
			return Collections.emptySet();
		}
		final Set<IJstRefType> exprs = m_mustActivelyNeededTypes.get(jstType);
		if(exprs == null){
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(exprs);
	}
	
	public void addMustActivelyNeededTypes(final IJstType jstType, final IJstRefType neededType){
		if(m_mustActivelyNeededTypes == null){
			m_mustActivelyNeededTypes = new HashMap<IJstType, Set<IJstRefType>>();
		}
		
		Set<IJstRefType> exprs = m_mustActivelyNeededTypes.get(jstType);
		if(exprs == null){
			exprs = new HashSet<IJstRefType>();
			m_mustActivelyNeededTypes.put(jstType, exprs);
		}
		exprs.add(neededType);
	}
	
	public void resetMustActilveyNeededTypes(final IJstType jstType){
		if(m_mustActivelyNeededTypes != null){
			m_mustActivelyNeededTypes.clear();
		}
	}
	
	public VjoSymbolTable getSymbolTable(){
		return m_symbolTable;
	}
	
	public VjoMethodControlFlowTable getMethodControlFlowTable(){
		return m_methodFlowTable;
	}
	
	public VjoMethodReferencesTable getMethodInvocationTable(){
		return m_methodInvocationTable;
	}
	
	public VjoPropertyStatesTable getPropertyStatesTable(){
		return m_propertyStatesTable;
	}
	
	public VjoScope getScope(){
		if(m_scope == null){
			m_scope = new VjoScope();
		}
		return m_scope;
	}
	
	public IJstNode getClosestScope(){
		return getScope().getClosestScopeNode();
	}
	
	public void addFinalProperty(IJstProperty pty, IJstMethod mtd) {
		List<IJstMethod> methods = m_finalPropertyInitMap.get(pty);
		if (methods == null) {
			methods = new ArrayList<IJstMethod>();
			synchronized (m_finalPropertyInitMap) {
				m_finalPropertyInitMap.put(pty, methods);
			}
		}
		methods.add(mtd);
	}
	
	public List<IJstMethod> getFinalPropertyInitConstructors(IJstProperty pty) {
		return m_finalPropertyInitMap.get(pty);
	}

	public void setValidationMode(VjoValidationMode m_validationMode) {
		this.m_validationMode = m_validationMode;
	}

	public VjoValidationMode getValidationMode() {
		return m_validationMode;
	}
}
