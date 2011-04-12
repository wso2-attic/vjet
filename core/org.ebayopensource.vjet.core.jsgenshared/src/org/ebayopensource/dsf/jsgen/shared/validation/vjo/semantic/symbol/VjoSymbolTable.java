/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstRefType;
import org.ebayopensource.dsf.jst.IJstType;

/**
 * 
 * 
 *	<p>
 *		symbol table associates scope with all available symbols in it;
 *		all available symbols are kept in a map between {name, type} and actual symbol;
 *		used to use List<IVjoSymbol>, updated to Map<VjoSymbolTableKey, List<IVjoSymbol>>, performance gain obviously
 *
 *		symbol table is not thread-safe, each thread should use an individual symbol table (validation ctx in fact)
 *	</p>
 */

public class VjoSymbolTable {

	private Map<IJstNode, Map<VjoSymbolTableKey, List<IVjoSymbol>>> m_scopeToSymbols = 
		new HashMap<IJstNode, Map<VjoSymbolTableKey, List<IVjoSymbol>>>();

	public void addSymbolInScope(IJstNode scope, IVjoSymbol symbol){
		if(scope instanceof IJstRefType){
			scope = ((IJstRefType)scope).getReferencedNode();
		}
		
		Map<VjoSymbolTableKey, List<IVjoSymbol>> symbolMap = m_scopeToSymbols.get(scope);
		if(symbolMap == null){
			symbolMap = new LinkedHashMap<VjoSymbolTableKey, List<IVjoSymbol>>();
			m_scopeToSymbols.put(scope, symbolMap);
		}
		
		final VjoSymbolTableKey key = new VjoSymbolTableKey(symbol.getName(), symbol.getSymbolType());
		List<IVjoSymbol> symbols = symbolMap.get(key);
		if(symbols == null){
			symbols = new ArrayList<IVjoSymbol>();
			symbolMap.put(key, symbols);
		}
		symbols.add(symbol);
	}
	
	public void removeSymbolInScope(IJstNode scope, IVjoSymbol symbol){
		if(scope instanceof IJstRefType){
			scope = ((IJstRefType)scope).getReferencedNode();
		}
		
		final Map<VjoSymbolTableKey, List<IVjoSymbol>> symbolMap = m_scopeToSymbols.get(scope);
		if(symbolMap != null){
			final VjoSymbolTableKey key = new VjoSymbolTableKey(symbol.getName(), symbol.getSymbolType());
			final List<IVjoSymbol> symbols = symbolMap.get(key);
			for(int i = symbols != null ? symbols.size() - 1 : -1; i >= 0; i--){
				if(symbol.equals(symbols.get(i))){
					symbols.remove(i);
					break;
				}
			}
		}
	}
	
	public void removeSymbolsInScope(IJstNode scope){
		m_scopeToSymbols.remove(scope);
	}
	
	public void removeSymbolsInScope(IJstType scope, boolean removePublic){
		if(scope instanceof IJstRefType){
			scope = ((IJstRefType)scope).getReferencedNode();
		}
		
		List<IVjoSymbol> symbols = getSymbolsInScope(scope);
		for(IVjoSymbol symbol: symbols){
			final String symbolName = symbol.getName();
			final EVjoSymbolType symbolType = symbol.getSymbolType();
			
			if(EVjoSymbolType.STATIC_FUNCTION.equals(symbolType) || EVjoSymbolType.INSTANCE_FUNCTION.equals(symbolType)){
				final IJstMethod method = scope.getMethod(symbolName);
				if(removePublic || method != null && !method.getModifiers().isPublic()){
					symbol.setVisible(false);
				}
			}
			else{
				final IJstProperty property = scope.getProperty(symbolName);
				if(removePublic || property != null && !property.getModifiers().isPublic()){
					symbol.setVisible(false);
				}
			}
		}
	}
	
	public List<IVjoSymbol> getSymbolsInScope(IJstNode node){
		return getSymbolsInScope(node, EVjoSymbolType.INSTANCE_VARIABLE);
	}
	
	public List<IVjoSymbol> getSymbolsInScope(IJstNode node, EVjoSymbolType symbolType){
		if(node instanceof IJstRefType){
			node = ((IJstRefType)node).getReferencedNode();
		}
		
		final Map<VjoSymbolTableKey, List<IVjoSymbol>> symbolMap = m_scopeToSymbols.get(node);
		final List<IVjoSymbol> toReturn = new ArrayList<IVjoSymbol>();
		
		if(symbolMap != null){
			for(List<IVjoSymbol> symbols: symbolMap.values()){
				for(IVjoSymbol symbol : symbols){
					if(!symbol.isVisible()){
						continue;
					}
						
					if(symbolType.equals(symbol.getSymbolType())){
						toReturn.add(symbol);
						continue;
					}
					else{//function is also a variable
						if(EVjoSymbolType.INSTANCE_VARIABLE.equals(symbolType)
								&& EVjoSymbolType.INSTANCE_FUNCTION.equals(symbol.getSymbolType())){
							toReturn.add(symbol);
							continue;
						}
						else if(EVjoSymbolType.STATIC_VARIABLE.equals(symbolType)
								&& EVjoSymbolType.STATIC_FUNCTION.equals(symbol.getSymbolType())){
							toReturn.add(symbol);
							continue;
						}
						else if(EVjoSymbolType.LOCAL_VARIABLE.equals(symbolType)
								&& EVjoSymbolType.LOCAL_FUNCTION.equals(symbol.getSymbolType())){
							toReturn.add(symbol);
							continue;
						}
					}
				}
			}
		}
		return toReturn;
	}
	
	public IVjoSymbol getSymbolInScope(IJstNode scope, String name){
		return getSymbolInScope(scope, name, EVjoSymbolType.INSTANCE_VARIABLE);
	}
	
	public IVjoSymbol getSymbolInScope(IJstNode scope, String name, EVjoSymbolType symbolType){
		return getSymbolInScope(scope, name, symbolType, false);
	}
	
	public IVjoSymbol getSymbolInScope(IJstNode scope, String name, EVjoSymbolType symbolType, boolean alwaysVisible){
		if(scope instanceof IJstRefType){
			scope = ((IJstRefType)scope).getReferencedNode();
		}
		
		Map<VjoSymbolTableKey, List<IVjoSymbol>> symbolMap = m_scopeToSymbols.get(scope);
		if(symbolMap == null || name == null){
			return null;
		}
		IVjoSymbol found = null;
		final VjoSymbolTableKey key = new VjoSymbolTableKey(name, symbolType);
		
		List<IVjoSymbol> symbols = symbolMap.get(key);
		for(int i = symbols != null ? symbols.size() - 1 : -1; i >= 0; i--){
			final IVjoSymbol candidate = symbols.get(i);
			if(candidate == null || (!alwaysVisible && !candidate.isVisible())){
				continue;
			}
			else{
				found = candidate;
				break;
			}
		}
		
		return found;
	}
	
	public IVjoSymbol getSymbol(VjoScope scope, String name){
		return getSymbol(scope, name, EVjoSymbolType.LOCAL_VARIABLE);
	}
	
	public IVjoSymbol getSymbol(VjoScope scope, String name, EVjoSymbolType symbolType){
		IVjoSymbol foundSymbol = null;
		//bugfix, scope should be looked up bottom up
		for(int i = scope.getScopeNodes().size() - 1; i >= 0; i--){
			foundSymbol = getSymbolInScope(scope.getScopeNodes().get(i), name, symbolType);
			if(foundSymbol != null){
				break;
			}
		}
		return foundSymbol;
	}
	
	private static final class VjoSymbolTableKey{
		private String m_keyName;
		private EVjoSymbolType m_keyType;
		
		public VjoSymbolTableKey(final String keyName, final EVjoSymbolType keyType){
			m_keyName = keyName;
			m_keyType = keyType;
		}
		
		public String getKeyName(){
			return m_keyName;
		}
		
		public EVjoSymbolType getKeyType(){
			return m_keyType;
		}
		
		public boolean equals(Object obj){
			if(obj == null || !(obj instanceof VjoSymbolTableKey)){
				return false;
			}
			final VjoSymbolTableKey instance = (VjoSymbolTableKey)obj;
			if(getKeyName()!= null){
				if(!getKeyName().equals(instance.getKeyName())){
					return false;
				}
			}
			else if(instance.getKeyName() != null){
				return false;
			}
			
			if(getKeyType() != null){
				if(EVjoSymbolType.LOCAL_VARIABLE.equals(getKeyType())
					&& (EVjoSymbolType.LOCAL_VARIABLE.equals(instance.getKeyType()) || EVjoSymbolType.LOCAL_FUNCTION.equals(instance.getKeyType()))){
					return true;
				}
				else if(EVjoSymbolType.INSTANCE_VARIABLE.equals(getKeyType())
					&& (EVjoSymbolType.INSTANCE_VARIABLE.equals(instance.getKeyType()) || EVjoSymbolType.INSTANCE_FUNCTION.equals(instance.getKeyType()))){
					return true;
				}
				else if(EVjoSymbolType.STATIC_VARIABLE.equals(getKeyType())
					&& (EVjoSymbolType.STATIC_VARIABLE.equals(instance.getKeyType()) || EVjoSymbolType.STATIC_FUNCTION.equals(instance.getKeyType()))){
					return true;
				}
			}
			else if(instance.getKeyType() != null){
				return false;
			}
			
			return true;
		}
		
		public int hashCode(){
			int hash = 0;
			if(getKeyName() != null){
				hash = getKeyName().hashCode() << 5;
			}
			if(EVjoSymbolType.LOCAL_VARIABLE.equals(getKeyType())
				|| EVjoSymbolType.LOCAL_FUNCTION.equals(getKeyType())){
				hash += EVjoSymbolType.LOCAL_VARIABLE.hashCode();
			}
			else if(EVjoSymbolType.INSTANCE_VARIABLE.equals(getKeyType())
				|| EVjoSymbolType.INSTANCE_FUNCTION.equals(getKeyType())){
				hash += EVjoSymbolType.INSTANCE_VARIABLE.hashCode();
			}
			else if(EVjoSymbolType.STATIC_VARIABLE.equals(getKeyType())
				|| EVjoSymbolType.STATIC_FUNCTION.equals(getKeyType())){
				hash += EVjoSymbolType.STATIC_VARIABLE.hashCode();
			}
			return hash;
		}
	}
}
