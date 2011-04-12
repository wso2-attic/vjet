/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.ebayopensource.dsf.active.dom.html.AHtmlHelper;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.anno.ProxyFunc;
import org.mozilla.mod.javascript.FunctionObject;
import org.mozilla.mod.javascript.IWillBeScriptable;
import org.mozilla.mod.javascript.ScriptableObject;
import org.mozilla.mod.javascript.Wrapper;

public abstract class ActiveObject extends ScriptableObject implements Wrapper {

	private static final long serialVersionUID = 1L;
	private List<String> m_ptys = new ArrayList<String>(10);
	private List<String> m_funcs = new ArrayList<String>(10);

	@Override
	public String getClassName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

	/**
	 * Unwrap the object by returning the wrapped value.
	 * 
	 * @return a wrapped value
	 */
	public Object unwrap() {
		return this;
	}

	public List<String> getProperties() {
		return Collections.unmodifiableList(m_ptys);
	}

	public List<String> getFunctions() {
		return Collections.unmodifiableList(m_funcs);
	}

	//
	// Protected
	//
	protected void populateScriptable(final Class klass, BrowserType browserType) {
		Set<String> propList = new HashSet<String>(1000);
		Set<String> methodList = new HashSet<String>(1000);
		Map<String, String> proxyMethods = new LinkedHashMap<String, String>(1000);
		extract(getScriptableInterfaces(klass), browserType, propList,
				methodList, proxyMethods);
		String[] propNames = propList.toArray(new String[propList.size()]);
		defineProperties(propNames, klass);
		String[] methodNames = methodList
				.toArray(new String[methodList.size()]);
		defineFunctionProperties(methodNames, klass);
		defineFunctionProperties(proxyMethods, klass);
	}

	private Class[] getScriptableInterfaces(Class klass) {
		ArrayList<Class> intfs = new ArrayList<Class>();
		if (klass == null) {
			throw new DsfRuntimeException("null class passed");
		}
		Class[] interfaces = klass.getInterfaces();
		if (interfaces != null) {
			for (Class i : interfaces) {
				if (IWillBeScriptable.class.isAssignableFrom(i)) {
					intfs.add(i);
				}
			}
		}
		if (intfs.size() == 0) {
			throw new DsfRuntimeException(klass.getName()
					+ " doesn't implement Scriptable interface");
		}
		Class[] out = new Class[intfs.size()];
		intfs.toArray(out);
		return out;
	}

	private void defineFunctionProperties(Map<String, String> proxyMethods,
			final Class klass) {
		for (Map.Entry<String, String> entry : proxyMethods.entrySet()) {
			Method proxyMethod = null;
			try {
				// TODO we need to fix Rhino var arg issues
				// currently only support 5 Object arguments for all proxy methods
				proxyMethod = klass.getMethod(entry.getKey(),Object.class,Object.class,Object.class,Object.class,Object.class);
			} catch (Exception e) {
				throw new DsfRuntimeException(e);
			}
			FunctionObject f = new FunctionObject(entry.getValue(), proxyMethod, this);
			defineProperty(this, entry.getValue(), f, DONTENUM);
		}

	}

	private void defineFunctionProperties(final String[] methodNames,
			final Class klass) {
		if (methodNames == null || methodNames.length == 0) {
			return;
		}
		defineFunctionProperties(methodNames, klass, ScriptableObject.PERMANENT);

		for (int i = 0; i < methodNames.length; i++) {
			m_funcs.add(methodNames[i]);
		}
	}

	private void defineProperties(final String[] propertyNames,
			final Class klass) {
		if (propertyNames == null || propertyNames.length == 0) {
			return;
		}
		for (int i = 0; i < propertyNames.length; i++) {
			// System.out.println("Defining property " + propertyNames[i] +
			// " for " + this.getClass().getSimpleName());
			defineProperty(propertyNames[i], klass, ScriptableObject.PERMANENT);
			m_ptys.add(propertyNames[i]);
		}
	}

	protected void defineProperties(final Set<String> propertyNames,
			final Class klass) {
		if (propertyNames == null || propertyNames.size() == 0) {
			return;
		}
		for (String prop : propertyNames) {
			// System.out.println("Defining property " + prop +
			// " for " + this.getClass().getSimpleName());
			defineProperty(prop, klass, ScriptableObject.PERMANENT);
			m_ptys.add(prop);
		}
	}

	/**
	 * TODO - overload functions should be ignored use the proxy function
	 * instead there will 3 lists properties, methods, proxy methods. methods
	 * will no longer include overloaded methods.
	 * 
	 * @param typeList
	 * @param browserType
	 * @param propList
	 * @param methodList
	 * @param proxyMethodList
	 */
	private void extract(Class[] typeList, BrowserType browserType,
			Set<String> propList, Set<String> methodList,
			Map<String, String> proxyMethodList) {
		
		for (Class t : typeList) {
			TypeKey key =  new TypeKey(t, browserType);
			TypeInfo info = typeCache.get(key);
			if(info == null){	
				info = new TypeInfo();
				Method[] mtds = t.getDeclaredMethods();
				String mtdName;
				for (Method m : mtds) {
					if (m.getDeclaringClass().equals(IWillBeScriptable.class)) {
						continue;
					}
					if (!Modifier.isPublic(m.getModifiers())) {
						continue;
					}
					if (browserType != null
							&& !(BrowserSupport.support(m, browserType) || BrowserSupport
									.support(m, BrowserType.RHINO_1P))) {
						continue;
					}
					mtdName = m.getName();
					String probName = null;
					Property p = m.getAnnotation(Property.class);
					if (p != null) {
						// Must be a getter/setter to be a property
						if (!(mtdName.startsWith("get") || mtdName
								.startsWith("set"))) {
							throw new DsfRuntimeException(t.getName() + ":"
									+ mtdName + " is not a proper getter/setter.");
						}
						// If a name is provided by Property annotation, use it for
						// property name.					
						probName = p.name();
						if (probName == null || probName.length() == 0) {
							probName = AHtmlHelper.getOriginalPropertyName(mtdName
									.substring(3));
						}
						info.props.add(probName);
						if(!propList.contains(mtdName)){
							propList.add(probName);
						}
					} else if (m.getAnnotation(Function.class) != null) {
						info.methods.add(mtdName);
						if(!methodList.contains(mtdName)){
							methodList.add(mtdName);
						}
					} else {
						ProxyFunc func = m.getAnnotation(ProxyFunc.class);
						if (func != null) {
							if (!info.proxyMethods.containsKey(mtdName)) {
								info.proxyMethods.put(mtdName, func.value());
							}
							if (!proxyMethodList.containsKey(mtdName)) {
								proxyMethodList.put(mtdName, func.value());
							}
						}
					}	
				}
				typeCache.put(key, info);
			}//end of creating cache
			else{
				propList.addAll(info.props);
				methodList.addAll(info.methods);
				mergeMap(proxyMethodList, info.proxyMethods);
			}
		}
	}	
	
	//Ignore overloading proxy methods
	private static void mergeMap(Map<String, String> origin, Map<String, String> delta){
		for (Entry<String, String> entry : delta.entrySet()) {
			if(!origin.containsKey(entry.getKey())){
				origin.put(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Holding info to cache per class & browser type
	 *
	 */
	private static class TypeInfo{
		Set<String> props = new HashSet<String>(150);
		Set<String> methods = new HashSet<String>(150);
		Map<String, String> proxyMethods = new HashMap<String, String>(150);
	}
	
	/**
	 * Stands for a clz & browser type combination 
	 *
	 */
	private static class TypeKey{
		public TypeKey(Class<?> clz, BrowserType browser) {
			this.clz = clz;
			this.browser = browser;
		}
		
		Class<?> clz;
		BrowserType browser;
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof TypeKey){
				TypeKey key = (TypeKey)o;
				return compareObj(this.clz, key.clz) && compareObj(this.browser, key.browser);
			}
			return false;
		}
		
		private boolean compareObj(Object obj1, Object obj2){
			if(obj1 == null && obj2 == null){
				return true;
			}
			if(obj1 != null && obj2 != null){
				return obj1.equals(obj2);
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			int hash = 0;
			if(this.clz != null){
				hash += this.clz.hashCode() * 31;
			}
			if(this.browser != null){
				hash += this.browser.hashCode();
			}
			return hash;
		}
	}
	/**
	 * A global cache for ActiveObject meta data during extract().
	 * This will help in performance.	
	 *
	 */
	static final Map<TypeKey, TypeInfo> typeCache = new ConcurrentHashMap<TypeKey, TypeInfo>();
	
}
