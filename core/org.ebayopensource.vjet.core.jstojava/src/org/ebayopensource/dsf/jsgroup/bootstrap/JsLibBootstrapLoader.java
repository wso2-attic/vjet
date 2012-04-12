/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgroup.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jstojava.mixer.TypeExtensionRegistry;
import org.ebayopensource.dsf.jstojava.mixer.TypeMixer;
import org.ebayopensource.dsf.jstojava.resolver.FunctionMetaMapping;
import org.ebayopensource.dsf.jstojava.resolver.FunctionMetaRegistry;
import org.ebayopensource.dsf.jstojava.resolver.IMetaExtension;
import org.ebayopensource.dsf.jstojava.resolver.MapBasedTypeResolver;
import org.ebayopensource.dsf.jstojava.resolver.ScriptableBasedTypeResolver;
import org.ebayopensource.dsf.jstojava.resolver.SingleThreadExecutor;
import org.ebayopensource.dsf.jstojava.resolver.TypeResolverRegistry;
import org.mozilla.mod.javascript.Context;
import org.mozilla.mod.javascript.ContextFactory;
import org.mozilla.mod.javascript.Function;
import org.mozilla.mod.javascript.NativeArray;
import org.mozilla.mod.javascript.Scriptable;

/**
 * Utility class to load bootstrap meta JS associated with each JS group
 */
public class JsLibBootstrapLoader {
	
	public static final String factoryFunctionMappings = "factoryFunctionMappings";
	public static final String functionArgMappings = "functionArgMappings";
	public static final String typeExtensions = "typeExtensions";
	
	public static void load(String bootstrapJS, String groupId) {
		SingleThreadExecutor executor = SingleThreadExecutor.getInstance();
		executor.execute(new Runner(bootstrapJS, groupId));
	}
	
	private static class Runner implements Runnable {
		private String m_bootstrapJS;
		private String m_groupId;
		private Runner(String bootstrapJS, String groupId) {
			m_bootstrapJS = bootstrapJS;
			m_groupId = groupId;
		}
		
		@Override
		public void run() {
			Context cx;
			Scriptable scope;
			try {
				cx = ContextFactory.getGlobal().enterContext();
				cx.setLanguageVersion(Context.VERSION_1_5);
				scope = cx.initStandardObjects();
				cx.evaluateString(scope, m_bootstrapJS, "TypeLibBoostrapJS", 1, null);
			} catch (Exception e) {
				e.printStackTrace(); //TODO report error
				return;
			}
			
			try {
				loadMethodReturnTypeResolvers(cx, scope, m_groupId);
			} catch (Exception e) {
				e.printStackTrace(); //TODO report error
			}
			
			try {
				loadFunctionArgMetaExtension(cx, scope, m_groupId);
			} catch (Exception e) {
				e.printStackTrace(); //TODO report error
			}
			
			try {
				loadTypeExtensions(cx, scope, m_groupId);
			} catch (Exception e) {
				e.printStackTrace(); //TODO report error
			}			
		}
		
	}
	
	private static void loadMethodReturnTypeResolvers(Context cx, Scriptable scope, String groupId) {
		TypeResolverRegistry trs = TypeResolverRegistry.getInstance();
		Object fm = scope.get(factoryFunctionMappings, scope);
		if (fm instanceof Scriptable) {
			Scriptable factory = (Scriptable)fm;
			for (Object resKey : factory.getIds()) {
				Object def = factory.get(resKey.toString(), factory);
				if (def instanceof Function) {
					trs.addResolver(resKey.toString(),
						new ScriptableBasedTypeResolver(groupId, cx, scope, (Function)def));
				} else if (def instanceof Scriptable) {
					Scriptable map = (Scriptable)def;
					Object[] keys = map.getIds();
					if (keys != null && keys.length > 0) {
						MapBasedTypeResolver resolver = new MapBasedTypeResolver(groupId);
						for (Object key : keys) {
							resolver.addMapping(key.toString(), map.get(key.toString(), map).toString());
						}
						trs.addResolver(resKey.toString(), resolver);
					}
				}
			}
		}
	}
	
	private static void loadFunctionArgMetaExtension(Context cx, Scriptable scope, String groupId) {
		FunctionMetaRegistry fms = FunctionMetaRegistry.getInstance();
		Object fm = scope.get(functionArgMappings, scope);
		if (fm instanceof Scriptable) {
			Scriptable famMapping = (Scriptable)fm;
			FunctionMetaMapping funcMeta = new FunctionMetaMapping(groupId);
			boolean hasMapping = false;
			for (Object methodId : famMapping.getIds()) {
				Object def = famMapping.get(methodId.toString(), famMapping);
				if (def instanceof Scriptable) {
					Scriptable map = (Scriptable)def;
					Object[] keys = map.getIds();
					if (keys != null && keys.length > 0) {					
						for (Object key : keys) {
							String[] metaList = null;
							Object meta = map.get(key.toString(), map);
							if (meta instanceof String) {
								metaList = new String[] {(String)meta};
							}
							else if (meta instanceof NativeArray) {
								NativeArray metaArr = (NativeArray)meta;
								metaList = new String[(int)metaArr.getLength()];
								for (int i = 0; i < metaArr.getLength(); i++) {
									metaList[i] = metaArr.get(i, metaArr).toString();
								}
							}
							if (metaList != null) {
								funcMeta.addMapping(methodId.toString(),
									key.toString(), metaList);
							}
							hasMapping = true;
						}						
					}
				}
			}
			if (hasMapping) {
				fms.addMapping(funcMeta);
			}
		}
	}
	
	private static void loadTypeExtensions(Context cx, Scriptable scope, String groupId) {
		Object te = scope.get(typeExtensions, scope);
		if (te instanceof Scriptable) {
			Scriptable extensions = (Scriptable)te;
			TypeMixer mixer = new TypeMixer(groupId);
			for (Object typeKey : extensions.getIds()) {
				Object def = extensions.get(typeKey.toString(), extensions);
				if (def instanceof String) {
					mixer.addExtendedType(typeKey.toString(), def.toString());
				}
				else if (def instanceof NativeArray) {
					NativeArray extension = (NativeArray)def;
					for (int i = 0; i < extension.getLength(); i++) {
						Object item = extension.get(i, extension);
						if (item instanceof String) {
							mixer.addExtendedType(typeKey.toString(), item.toString());
						}
					}
				}
			}
			TypeExtensionRegistry.getInstance().addMixer(mixer);
		}
	}
	
	public static void main(String[] args) {
		load("var factoryFunctionMappings = {'a.b.A:f1':{'a':'xxx', 'b':'yyy'}, 'a.d.B::f2':function() {if (arguments[0] == '\"default\"') {return 'zzz'} else return 'uuu'}}", "g1");
		load("var factoryFunctionMappings = {'x.b.A:f1':{'a':'xxx2', 'b':'yyy2'}, 'x.d.B::f2':function() {if (arguments[0] == '\"default\"') {return 'zzz2'} else return 'uuu2'}}", "g2");
		load("var typeExtensions = {'y.b.A':'u.w.Y', 'y.d.B':'u.v.W'}", "g1");
		load("var typeExtensions = {'x.b.A':'u.w.X', 'x.d.B':['u.v.Y', 'u.v.Z']}", "g2");
		load("var functionArgMappings = {'a.b.A:f1':{'open':'void fn(int)', 'click':['boolean fn(String)', 'Date fn(boolean)'],"
			+ "'close':'Window:alert'}}", "g3");
		
		// type resolver 
		//addTypeProcessor()
		
		TypeResolverRegistry trs = TypeResolverRegistry.getInstance();
		System.out.println(trs.resolve("a.b.A:f1", new String[] {"'a'"}));
		System.out.println(trs.resolve("a.b.A:f1", new String[] {"\"b\""}));
		System.out.println(trs.resolve("a.d.B::f2", new String[] {"\"any\""}));
		System.out.println(trs.resolve("a.d.B::f2", new String[] {"\"default\""}));
		
		System.out.println(trs.resolve("x.b.A:f1", new String[] {"'a'"}));
		System.out.println(trs.resolve("x.b.A:f1", new String[] {"\"b\""}));
		System.out.println(trs.resolve("x.d.B::f2", new String[] {"\"any\""}));
		System.out.println(trs.resolve("x.d.B::f2", new String[] {"\"default\""}));
		
		TypeExtensionRegistry ter = TypeExtensionRegistry.getInstance();
		System.out.println(ter.getExtension("x.b.A", null, "g2", null));
		System.out.println(ter.getExtension("y.b.A", null, "g1", null));
		System.out.println(ter.getExtension("y.d.B", null, "g1", null));
		List<String> bases = new ArrayList<String>();
		bases.add("y.d.B");
		List<String> depGrps = new ArrayList<String>();
		depGrps.add("g1");
		System.out.println(ter.getExtension("x.d.B", bases, "g2", depGrps));
		System.out.println(ter.isNonExtendedType("x.d.C", "g2"));
		System.out.println(ter.getExtension("x.d.C", bases, "g2", null));
		System.out.println(ter.isNonExtendedType("x.d.C", "g2"));
		
		FunctionMetaRegistry fmr = FunctionMetaRegistry.getInstance();
		IMetaExtension method = fmr.getExtentedArgBinding("a.b.A:f1", "open", "g3", null);
		System.out.println(method.toString());
		method = fmr.getExtentedArgBinding("a.b.A:f1", "click", "g3", null);
		System.out.println(method.toString());
		method = fmr.getExtentedArgBinding("a.b.A:f1", "close", "g3", null);
		System.out.println(method.toString());
		System.out.println(fmr.isFuncMetaMappingSupported("a.b.A:f1"));
		System.out.println(fmr.isFuncMetaMappingSupported("a.b.A:f2"));
	}
}
