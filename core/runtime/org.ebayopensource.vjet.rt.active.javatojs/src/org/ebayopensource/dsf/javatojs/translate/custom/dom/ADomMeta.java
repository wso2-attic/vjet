/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.dom;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.event.DsfEvent;
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.BaseCustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.ICustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.PrivilegedProcessorAdapter;
import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.GlobalProperty;
import org.ebayopensource.dsf.jsnative.anno.JsNativeMeta;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.eclipse.jdt.core.dom.ASTNode;
import org.mozilla.mod.javascript.Scriptable;

public class ADomMeta extends BaseCustomMetaProvider implements ICustomMetaProvider {
	
	private static final String GET = "get";
	private static final String SET = "set";
	private static final char ARG_SEPERATOR = '#';

	// List of JsNative property names with first letter already capitalized
	private static List<String> s_jsNativeSpecialProps = new ArrayList<String>();
	
	private boolean m_lookupMtdBySigniture = false;
	
	public ADomMeta(){
		this(false);
	}
	
	
	
	public ADomMeta(boolean lookupMtdBySigniture){
		m_lookupMtdBySigniture = lookupMtdBySigniture;
		init();
	}
	
	//
	// Private
	//
	private void init(){
		
		// Must initialize first 
		s_jsNativeSpecialProps.add("NaN");
		s_jsNativeSpecialProps.add("Infinity");
		
		// add all JsNative classes
		for (Class c : JsNativeMeta.getAllClasses()) {
			add(c);
		}
		
		loadWindowCustomMeta();
		loadAHtmlDocument();
		loadHtmlElementMeta() ;
		loadJsNativeNodeMeta();
		
		Class type = DsfEvent.class;
		addCustomType(type.getName(), new CustomType(type)
			.setAttr(CustomAttr.JAVA_ONLY));
		
		type = BrowserType.class;
		CustomType cType = new CustomType(type, type.getSimpleName());
		addCustomType(type.getName(), cType);
		addCustomType(type.getSimpleName(), cType);
	}
	
	private void loadWindowCustomMeta() {
		final String javaTypeName = "org.ebayopensource.dsf.jsnative.Window";
		addPrivilegedMethodProcessor(javaTypeName, "newImage", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd)
			{
				IExpr[] argArr;
				if (args != null) {
					argArr = new IExpr[args.size()];
					args.toArray(argArr);
				}
				else {
					argArr = new IExpr[0];
				}
				return TranslateHelper.Expression
					.createObjCreationExpr("Image", argArr);
			}
		});
		
		addPrivilegedMethodProcessor(javaTypeName, "newOption", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd)
			{
				IExpr[] argArr;
				if (args != null) {
					argArr = new IExpr[args.size()];
					args.toArray(argArr);
				}
				else {
					argArr = new IExpr[0];
				}
				return TranslateHelper.Expression
					.createObjCreationExpr("Option", argArr);
			}
		});
		
		addPrivilegedMethodProcessor(javaTypeName, "newXmlHttpReq", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd)
			{
				return new TextExpr("window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject('MSXML2.XMLHTTP')");
			}
		});
	}
	
	private void loadJsNativeNodeMeta() {
		final String javaTypeName = IClassR.JsNativeNodeName ; //"org.ebayopensource.dsf.jsnative.Node"
		
//		CustomType funcXType = new CustomType(javaTypeName, javaTypeName);
//		addCustomType(javaTypeName, funcXType);
		
		addPrivilegedMethodProcessor(javaTypeName, "addt", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd)
			{		
				// we want something like: 
				// target.appendChild(document.createTextNode(textValueExpr))
				JstIdentifier myidentifier = new JstIdentifier(
					"createTextNode", new JstIdentifier("document")) ;
				MtdInvocationExpr createMtdExpr = new MtdInvocationExpr(myidentifier) ;
				createMtdExpr.addArg(args.get(0)) ;
				
				identifier.setName("appendChild") ;
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
				mtdCall.setQualifyExpr(optionalExpr);
				mtdCall.addArg(createMtdExpr) ;
				
				return mtdCall ;
			}
		});			
	}
	
	private void loadHtmlElementMeta() {
		final String javaTypeName = IClassR.HtmlElementName ; //"org.ebayopensource.dsf.jsnative.HtmlElement"
		
//		CustomType funcXType = new CustomType(javaTypeName, javaTypeName) ;
////			.addCustomMethod(new CustomMethod("addBr")) ;
//		addCustomType(javaTypeName, funcXType);
		
		addPrivilegedMethodProcessor(javaTypeName, "addBr", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd)
			{		
				// we want something like: 
				// target.appendChild(document.createTextNode('addBr'))
				
				// build the document.createTextNode('addBr')) expression
				JstIdentifier myidentifier = new JstIdentifier(
					"createElement", new JstIdentifier("document")) ;
				MtdInvocationExpr createMtdExpr = new MtdInvocationExpr(myidentifier) ;
				TextExpr br = new TextExpr("'br'") ;
				createMtdExpr.addArg(br) ;
				
				// build the target.appendChild(createMtdExpr) expression
				identifier.setName("appendChild") ;
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
				mtdCall.setQualifyExpr(optionalExpr);
				mtdCall.addArg(createMtdExpr) ;		
				
				return mtdCall ;
			}
		});		
	}

	private void loadAHtmlDocument(){
		Class type = HtmlDocument.class;
		CustomType customType = createCustomType(type, 
				getProperties(HtmlDocument.class), 
				getFunctions(HtmlDocument.class));

		addCustomType(type.getName(), customType);
	}
	
	private void add(Class type) {
		CustomType customType = createCustomType(type, getProperties(type), 
				getFunctions(type)).setAttr(CustomAttr.MAPPED_TO_JS);
		addCustomType(type.getName(), customType);
		Alias alias = (Alias) type.getAnnotation(Alias.class);
		if (alias != null) {
			addCustomType(alias.value(), customType);
		}
	}
	
	private Map<String, String> getFunctions(Class type) {
		Map<String, String> funcs = new HashMap<String, String>();
		Method[] mtds = type.getMethods();
		String mtdName, mtdJsNative = null;
		for (Method m: mtds){
			if (m.getDeclaringClass().equals(Scriptable.class)) {
				continue;
			}
			if (!Modifier.isPublic(m.getModifiers())){
				continue;
			}
			mtdName = m.getName();
			if (lookupMtdBySigniture()) {
				for (Class c : m.getParameterTypes()) {
					mtdName += ARG_SEPERATOR+c.getName();
				}
			}
			mtdJsNative = null;
			boolean isFunction = false;
			Annotation[] annotations = m.getAnnotations();
			for (Annotation annot : annotations) {
				// Check method if explicitly annonated as Property
				if (annot.annotationType().equals(Property.class)) {
					break;
				}
				// Check if method explicitly annonated as Function
				// should not use both ARename and OverLoadFunc
				if (annot.annotationType().equals(Function.class) ||
					annot.annotationType().equals(OverLoadFunc.class) ||
					annot.annotationType().equals(Constructor.class)) {
					isFunction = true;
					continue;
				}
				if (annot.annotationType().equals(ARename.class)) {
					ARename rename = (ARename) annot;
					mtdJsNative = rename.name();
				}
			}
			
//			boolean isGetOrSet = mtdName.startsWith(GET) || mtdName.startsWith(SET);
//			// If method is not a getter/setter, then it is considered a function
//			if (!isGetOrSet) {
//				isFunction = true;
//			}
			if (isFunction && !funcs.containsKey(mtdName)) {
//				System.out.println("Type=" + type.getName() + ", mtdName=" + mtdName + ", mtdJsNative=" + mtdJsNative);
				funcs.put(mtdName, mtdJsNative);
			}
		}
		
		return funcs;
	}
	
	private Map<String, String> getProperties(Class type) {
		Map<String, String> props = new HashMap<String, String>();
		Method[] mtds = type.getMethods();
		String mtdName, jsNativeName = null;
		for (Method m: mtds){
			if (m.getDeclaringClass().equals(Scriptable.class)) {
				continue;
			}
			if (!Modifier.isPublic(m.getModifiers())){
				continue;
			}
			mtdName = m.getName();
			if (lookupMtdBySigniture()) {
				for (Class c : m.getParameterTypes()) {
					mtdName += ARG_SEPERATOR+c.getName();
				}
			}
			jsNativeName = null;
			boolean isProperty = false;
			Annotation[] annotations = m.getAnnotations();
			for (Annotation annot : annotations) {
				if (annot.annotationType().equals(Property.class)||
						annot.annotationType().equals(GlobalProperty.class)) {
					isProperty = true;
				}
				else if (annot.annotationType().equals(ARename.class)) {
					ARename rename = (ARename) annot;
					jsNativeName = rename.name();
				}
			}
			if (!isProperty) {
				continue;
			}
			if (!props.containsKey(mtdName)) {
				props.put(mtdName, jsNativeName);
			}
		}
		return props;
	}
	
	/**
	 * Constructor
	 * @param javaType Class
	 * @param ptys Map<String, String>
	 * @param funcs Map<String, String>
	 */
	private CustomType createCustomType(final Class javaType, final Map<String, String> ptys, final Map<String, String> funcs){
		
		CustomType cType = new CustomType(javaType, javaType.getName());
		
		String ptyName;
		String javaMtdName;
		String jstMtdName;
		String jstOrigMtdName;
		
		if (ptys != null){
			for(String pty: ptys.keySet()){
				if (pty.length() == 0) {
					continue;
				}
				ptyName = pty;
				int indx = ptyName.indexOf(ARG_SEPERATOR);
				if (indx != -1) {
					ptyName = ptyName.substring(0, indx);
				}
				javaMtdName = ptyName;
				jstOrigMtdName = getOriginalPropertyName(javaType, ptyName);
				jstMtdName = ptys.get(pty);
				if (jstMtdName == null) {
					jstMtdName = jstOrigMtdName;
				}
				MethodKey key;
				if (indx != -1) {
					key = new MethodKey(javaMtdName, false, pty.substring(indx+1));
				} else {
					key = new MethodKey(javaMtdName);
				}
				cType.addCustomMethod(new CustomMethod(key, jstMtdName)
					.setJstOrigName(jstOrigMtdName)
					.setIsProperty(true)
					.setLookupBySignature(lookupMtdBySigniture()));
			}
		}
		
		if (funcs != null){
			for(String f: funcs.keySet()){		
				jstMtdName = funcs.get(f);
				javaMtdName = f;
				int indx = javaMtdName.indexOf(ARG_SEPERATOR);
				if (indx != -1) {
					javaMtdName = javaMtdName.substring(0, indx);
				}
				jstOrigMtdName = javaMtdName;
				if (jstMtdName == null) {
					jstMtdName = jstOrigMtdName;
				}
				MethodKey key;
				if (indx != -1) {
					key = new MethodKey(javaMtdName, false, f.substring(indx+1));
				} else {
					key = new MethodKey(javaMtdName);
				}
				cType.addCustomMethod(new CustomMethod(key, jstMtdName)
					.setJstOrigName(jstOrigMtdName)
					.setLookupBySignature(lookupMtdBySigniture()));
			}
		}
		
		return cType;
	}

	private String getOriginalPropertyName(Class javaType, String ptyName) {
		boolean isGetOrSet = ptyName.startsWith(GET) || ptyName.startsWith(SET);
		if (!isGetOrSet) {
			return ptyName;
		}
		String propName = null;
		try {
			Class[] parameterTypes = null;
			if (ptyName.startsWith(SET)) {
				parameterTypes = new Class[] {Object.class};
			}
			Method mtd = javaType.getMethod(ptyName, parameterTypes);
			Property p = mtd.getAnnotation(Property.class);
			// If Property provides a name, use it
			if (p != null && p.name() != null && p.name().length() > 0) {
				propName = p.name();
			}
		} catch (Exception e) {
			//NOPMD
		}
		if (propName == null) {
			propName = getOriginalPropertyName(ptyName.substring(3));
		}
	
		return propName;
	}
	
	private static List<String> m_jsNativeSpecialProps = new ArrayList<String>();
	static{
		m_jsNativeSpecialProps.add("NaN");
		m_jsNativeSpecialProps.add("Infinity");
		m_jsNativeSpecialProps.add("URLUnencoded");
	}

	private String getOriginalPropertyName(String propName) {
		if (m_jsNativeSpecialProps.contains(propName)) {
			return propName;
		}
		if (isNameAllCapitalized(propName)) {
			return propName;
		}
		return propName.substring(0, 1).toLowerCase() + propName.substring(1);
	}
	
	private boolean isNameAllCapitalized(String name) {
		char [] chars = name.toCharArray();
		for (char ch : chars) {
			if (Character.isLetter(ch) && !Character.isUpperCase(ch)) {
				return false;
			}
		}
		return true;
	}

	public boolean lookupMtdBySigniture() {
		return m_lookupMtdBySigniture;
	}
}