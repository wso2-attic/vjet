//package org.ebayopensource.dsf.jstojava.resolver;
//
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * This resolver will take an object literal passed into a method invocation expression
// * and resolve to an otype. 
// * 
// * doIt({
// *  type: 'foo'
// *  
// * })
// * 
// * The object literal will be resolved based on the literal field key (in the example above the key is type)
// * 
// * This will able validation and code assist for the object literal. 
// *
// *
// */
//public class ObjectLiteralResolverRegistry {
//
//	
//	private static ObjectLiteralResolverRegistry s_instance = new ObjectLiteralResolverRegistry();
//	private Map<String, IObjectLiteralMapping> m_objLitMetaMappings = new LinkedHashMap<String, IObjectLiteralMapping>();
//	private Set<String> m_tergetFuncs = new HashSet<String>();
//	
//	public static ObjectLiteralResolverRegistry getInstance() {
//		return s_instance;
//	}
//	
//	public void addMapping(IObjectLiteralMapping mapping) {
//		m_objLitMetaMappings.put(mapping.getGroupId(), mapping);
//		m_tergetFuncs.addAll(mapping.getSupportedTargetFuncs());
//	}
//	
//	public boolean isFuncMetaMappingSupported(String targetFunc) {
//		return m_tergetFuncs.contains(targetFunc);
//	}
//	
//	public IObjectLiteralMapping getExtentedArgBinding(
//		String targetFunc, String key, String groupId, List<String> dependentGroupIds) {
//		
//		IMetaExtension method = getExtentedArgBinding(targetFunc, key, groupId);
//		if (method == null && dependentGroupIds != null) {
//			for (int i = 0; i < dependentGroupIds.size(); i++) {
//				method = getExtentedArgBinding(targetFunc, key, dependentGroupIds.get(i));
//				if (method != null) {
//					break;
//				}
//			}
//		}		
//		return method;
//	}
//		
//	public void clear(String groupId) {
//		m_objLitMetaMappings.remove(groupId);
//		m_tergetFuncs.clear();
//		for (IObjectLiteralMapping mapping : m_objLitMetaMappings.values()) {
//			m_tergetFuncs.addAll(mapping.getSupportedTargetFuncs());
//		}
//	}
//	
//	public void clearAll() {
//		m_objLitMetaMappings.clear();
//		m_tergetFuncs.clear();
//	}
//	
//	private IObjectLiteralMapping getObjectLiteralBinding(
//		String targetFunc, String key, String groupId) {
//		IObjectLiteralMapping mapping = m_objLitMetaMappings.get(groupId);
//		return (mapping == null) ? null : mapping.getExtentedArgBinding(targetFunc, key);
//	}
//}
