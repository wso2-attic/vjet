/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.dap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

import org.ebayopensource.dsf.active.dom.html.AHtmlType;
import org.ebayopensource.dsf.active.dom.html.AHtmlType.Type;
import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcErrorHandler;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcSuccessHandler;
import org.ebayopensource.dsf.dap.api.util.DapDocumentHelper;
import org.ebayopensource.dsf.dap.api.util.DapEventHelper;
import org.ebayopensource.dsf.dap.api.util.DapJsHelper;
import org.ebayopensource.dsf.dap.api.util.DapVariantTypeHelper;
import org.ebayopensource.dsf.dap.api.util.VJ;
import org.ebayopensource.dsf.dap.event.AKeyEvent;
import org.ebayopensource.dsf.dap.event.AMouseEvent;
import org.ebayopensource.dsf.dap.event.DapEvent;
import org.ebayopensource.dsf.dap.event.listener.DapEventListenerHelper;
import org.ebayopensource.dsf.dap.event.listener.IDapEventListener;
import org.ebayopensource.dsf.dap.event.listener.IDapHostEventHandler;
import org.ebayopensource.dsf.dap.event.listener.IJsEventListenerProxy;
import org.ebayopensource.dsf.dap.proxy.Array;
import org.ebayopensource.dsf.dap.proxy.INativeJsFuncProxy;
import org.ebayopensource.dsf.dap.proxy.NativeJsFuncProxy;
import org.ebayopensource.dsf.dap.proxy.NativeJsProxy;
import org.ebayopensource.dsf.dap.proxy.NativeJsTypeRef;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.DapHandlerAdapter;
import org.ebayopensource.dsf.dap.rt.DapHost;
import org.ebayopensource.dsf.dap.rt.DapServiceEngine;
import org.ebayopensource.dsf.dap.rt.JsBase;
import org.ebayopensource.dsf.dap.svc.IDapSvcCallback;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.javatojs.translate.DataTypeTranslator;
import org.ebayopensource.dsf.javatojs.translate.TranslateCtx;
import org.ebayopensource.dsf.javatojs.translate.TranslateHelper;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.BaseCustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.ICustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.PrivilegedProcessorAdapter;
import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;
import org.ebayopensource.dsf.jsnative.HtmlDocument;
import org.ebayopensource.dsf.jsnative.Window;
import org.ebayopensource.dsf.jsnative.events.Event;
import org.ebayopensource.dsf.jsnative.events.KeyboardEvent;
import org.ebayopensource.dsf.jsnative.events.MouseEvent;
import org.ebayopensource.dsf.jsnative.events.UIEvent;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.expr.ArrayAccessExpr;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr;
import org.ebayopensource.dsf.jst.expr.CastExpr;
import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
import org.ebayopensource.dsf.jst.expr.PtyGetter;
import org.ebayopensource.dsf.jst.expr.TextExpr;
import org.ebayopensource.dsf.jst.expr.BoolExpr.Operator;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.service.IServiceSpec;
import org.ebayopensource.vjo.meta.VjoConvention;

/**
 * Meta data provider for DAP custom translation
 */
public class DapMeta extends BaseCustomMetaProvider implements ICustomMetaProvider {
	
	private static final String VJO_ELEMENT = "vjo.dsf.Element";
	private static final String VJO_SELECT = "vjo.dsf.document.Select";
	
	private static final String VJO_DAP_SVC_ENGINE = "vjo.dsf.DapServiceEngine";
	private static final String VJO_I_SVC_SPEC = "vjo.dsf.IServiceSpec";
	private static final String VJO_SVC_SPEC = "vjo.dsf.DefaultServiceSpec";
	
	// 
	// Singleton
	//
	private static DapMeta s_instance = new DapMeta();
	private static boolean m_initialized = false;
	private DapMeta(){}
	public static DapMeta getInstance(){
		if (!m_initialized){
			synchronized (s_instance){
				if (!m_initialized){
					s_instance.init();
					m_initialized = true;
				}
			}
		}
		return s_instance;
	}
	
	//
	// Private
	//
	private void init(){
		addRtMeta();
		addApiMeta();
		addEventType();
		addEventMeta();
		addListenerMeta();
		addServiceMeta();
		addEventDispatcherXMeta();
		addObjectLiteralMeta();
		addArrayProxyMeta();
		addJTypeMeta();
		addJFunctionMeta();
		addJsxMeta() ;
		addFuncXMeta();
		addOtherMeta();
		
		//tmp
		addJqMeta();
	}
	
	
	private void addRtMeta(){
		
		// DapCtx
		Class<?> type = DapCtx.class;
		addCustomType(type.getName(), new CustomType(type)
			.setAttr(CustomAttr.EXCLUDED));
		
		// DapHandlerAdapter
		type = DapHandlerAdapter.class;
		addCustomType(type.getName(), new CustomType(type)
			.setAttr(CustomAttr.JAVA_ONLY));
		
		type = DapHost.class;
		addCustomType(type.getName(), new CustomType(type)
			.setAttr(CustomAttr.EXCLUDED));
		
		// JsBase
		type = JsBase.class;
		addCustomType(type.getName(), 
			new CustomType(type)
				.setAttr(CustomAttr.JAVA_ONLY)
				.addCustomMethod(new CustomMethod("window"))
				.addCustomMethod(new CustomMethod("document")));
		addPrivilegedTypeProcessor(type.getName(), new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,	
					final JstIdentifier identifier, 
					final IExpr optionalExpr, 
					final List<IExpr> args, 
					final boolean isSuper,
					final BaseJstNode jstNode,
					final CustomType cType,
					final CustomMethod cMtd){

				if (cMtd == null){
					return null;
				}
				return new JstIdentifier(cMtd.getJavaName())
					.setType(identifier.getType());
			}
		});
	}
	
	private void addApiMeta(){
		
		// VJ
		Class<?> type = VJ.class;
		addCustomType(type.getName(), 
			new CustomType(type)
				.setAttr(CustomAttr.JAVA_ONLY)
				.addCustomMethod(
					new CustomMethod("win", "window").setIsProperty(true)
						.setJstReturnTypeName(Window.class.getName())
						.removeQualifier(true))
				.addCustomMethod(
					new CustomMethod("doc", "document").setIsProperty(true)
						.setJstReturnTypeName(HtmlDocument.class.getName())
						.removeQualifier(true))
				.addCustomMethod(
					new CustomMethod("this_", "this").setIsProperty(true)
						.setJstReturnTypeName(Object.class.getName())
						.removeQualifier(true)));		
		
		// DapDocumentHelper
		type = DapDocumentHelper.class;
		CustomType doc = new CustomType(type)
			.setAttr(CustomAttr.JAVA_ONLY)
			.addCustomMethod("createElement", "createElement", "document")
			.addCustomMethod("createTextNode", "createTextNode", "document")
			.addCustomMethod("getElement", "get", VJO_ELEMENT)
			.addCustomMethod("clearOptions", "clear", VJO_SELECT)
			.addCustomMethod("createOption", "createOption", VJO_SELECT);
		addCustomType(type.getName(), doc);
		addPrivilegedTypeProcessor(type.getName(), new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,
					final JstIdentifier identifier, 
					final IExpr optionalExpr, 
					final List<IExpr> args, 
					final boolean isSuper,
					final BaseJstNode jstNode,
					final CustomType cType,
					final CustomMethod cMtd){

				String mtdName = identifier.getName();
				// var() gets translated to var_ by the jst method processor
				if(mtdName.equals("var_")){
					mtdName = "var";
				}
				// shorthand for createElement
				AHtmlType<?> hType = AHtmlType.Helper.get(mtdName);
				if (hType != null) 
				{
					String rtnTypeName = hType.getJsNativeClass().getName();
					IJstType rtnType = JstCache.getInstance().getType(rtnTypeName);
					
					if (args.size() == 0) { // simple create element			
						return new CastExpr(new TextExpr("document.createElement('" + mtdName + "')", rtnType));
					}
					else { // create element and set innerHTML
						String innerHTML = args.get(0).toExprText() ;
						return new TextExpr(
// TODO: MrP - We need to properly encode innerHTML value so we don't
// have issues with quoting
							"vjo.create('" + mtdName + "', " + innerHTML + ")", rtnType);
					}
				}
				// shorthand for getElementById
				if (mtdName.startsWith("get")){
					if(args.size() == 1){
						String eleName = mtdName.substring(3);
						hType = AHtmlType.Helper.get(eleName);
						if (hType != null){
							String rtnTypeName = hType.getJsNativeClass().getName();
							IJstType rtnType = JstCache.getInstance().getType(rtnTypeName);
							return new CastExpr(new TextExpr("document.getElementById(" + args.get(0).toExprText() + ")"), rtnType);
						}
					}
					else if(mtdName.equals("getBody")){
						IJstType rtnType = JstCache.getInstance().getType(AHtmlType.BODY.getJsNativeClass().getName());
						return new TextExpr("document.body", rtnType);
					}
					else if(mtdName.equals("getHead")){
						IJstType rtnType = JstCache.getInstance().getType(AHtmlType.HEAD.getJsNativeClass().getName());
						return new TextExpr("document.getElementsByTagName('head')[0]", rtnType);
					}
					else if(mtdName.equals("getTitle")){
						IJstType rtnType = JstCache.getInstance().getType(AHtmlType.TITLE.getJsNativeClass().getName());
						return new TextExpr("document.getElementsByTagName('title')[0]", rtnType);
					}
				}
				
				if (cMtd == null){
					return null;
				}
				
				int jsParamsCount = 0;
				if ("getElement".equals(cMtd.getJavaName())){
					jsParamsCount = 1;
				}
				
				String ownerTypeName = cMtd.getJstOwnerTypeName();
				
				DataTypeTranslator dataTypeTranslator = TranslateCtx.ctx().getProvider().getDataTypeTranslator();
				
				if (ownerTypeName != null){
					IJstType toOwnerType = dataTypeTranslator.toJstType(cMtd.getJstOwnerTypeName(), jstNode.getOwnerType());
					dataTypeTranslator.addImport(toOwnerType, jstNode.getOwnerType(), toOwnerType.getSimpleName());
				}

				MtdInvocationExpr mtdCall = new MtdInvocationExpr(new JstIdentifier(cMtd.getJstName()));
				mtdCall.setQualifyExpr(new JstIdentifier(ownerTypeName));
				if(jsParamsCount==0){
					jsParamsCount = args.size(); 
				}
				for(int i=0;i<jsParamsCount;i++){
					if(args.get(i)!=null){
						mtdCall.addArg(new JstIdentifier(args.get(i).toExprText()));
					}
				}
				
				if ("getElement".equals(cMtd.getJavaName())){
					String argName = args.get(args.size()-1).toExprText();
					argName = argName.substring(1, argName.length()-1);
					String tagName;
					Iterable<AHtmlType<?>> iter = Type.valueIterable();
					for (AHtmlType<?> htmlType : iter) {
						tagName = htmlType.getTagName();
						if (!tagName.equalsIgnoreCase(argName)){
							continue;
						}
						IJstType type = dataTypeTranslator.toJstType(htmlType.getJsNativeClass().getName(), jstNode.getOwnerType());
						mtdCall.setResultType(type);
						break;
					}
				}
				return mtdCall;
			}
		});
		
		// DapEventHelper
		type = DapEventHelper.class;
		addCustomType(type.getName(), 
			new CustomType(type)
				.setAttr(CustomAttr.JAVA_ONLY));
		
		// DapJsHelper
		type = DapJsHelper.class;
		addCustomType(type.getName(), 
			new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		addPrivilegedTypeProcessor(type.getName(), new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,
					final JstIdentifier identifier, 
					final IExpr optionalExpr, 
					final List<IExpr> args, 
					final boolean isSuper,
					final BaseJstNode jstNode,
					final CustomType cType,
					final CustomMethod cMtd){
				
				String methodName = identifier.getName();
				if (methodName.equals("isDefined")){
					IExpr type = args.get(0);
					IExpr left;
					String typeOf = "typeof ";
					if (args.size() == 1) { 
						left = new TextExpr(typeOf + type.toExprText());
					} else {
						left = new TextExpr(typeOf + type.toExprText()
								+ "[" + args.get(1).toExprText() + "]");
					}
					
					return new BoolExpr(left,
							new SimpleLiteral(String.class, JstCache
									.getInstance().getType("String"),"undefined"),
							Operator.NOT_EQUALS);
				}
				else {
					JstIdentifier newIdentifier = new JstIdentifier(methodName);
					newIdentifier.setType(identifier.getResultType());
					MtdInvocationExpr mtdCall = new MtdInvocationExpr(newIdentifier);
					for(IExpr arg : args){
						mtdCall.addArg(arg);
					}
					return mtdCall;
				}
			}
		});
		
		// DapVariantTypeHelper
		type = DapVariantTypeHelper.class;
		addCustomType(type.getName(), 
			new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		addPrivilegedTypeProcessor(type.getName(), new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,
					final JstIdentifier identifier, 
					final IExpr optionalExpr, 
					final List<IExpr> args, 
					final boolean isSuper,
					final BaseJstNode jstNode,
					final CustomType cType,
					final CustomMethod cMtd){
				
				if (args == null || args.size() != 1){
					return null;
				}
				IExpr arg = args.get(0);
				IJstType returnType = identifier.getResultType();
				if (returnType != null) {
					//TODO set correct return type to arg
				}
				return arg;
			}
		});
	}
	
	private void addEventType(){
		Class<?> type = EventType.class;
		CustomType cType = new CustomType(type)
			.setAttr(CustomAttr.JAVA_ONLY)
			.addCustomMethod("getName", null, false);
		for (Field f: type.getDeclaredFields()){
			cType.addCustomField(f.getName(), "\"" + f.getName().toLowerCase() + "\"", type.getName());
		}
		addCustomType(type.getName(), cType);
		
		type = Event.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = UIEvent.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = MouseEvent.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = KeyboardEvent.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = DapEvent.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = AMouseEvent.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = AKeyEvent.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
	}
	
	private void addEventMeta(){
		List<Class<? extends DapEvent>> list = DapEventListenerHelper.getSupportedEvents();
		for (Class<? extends DapEvent> type: list){
			CustomType cType = new CustomType(type)
				.setAttr(CustomAttr.JAVA_ONLY);
			cType.addCustomMethod("getSource", "src", true);

			addCustomType(type.getName(), cType);
		}
	}
	
	private void addListenerMeta(){
		for (Class<? extends IDapEventListener> lType: DapEventListenerHelper.getAllEventListeners()){
			addCustomType(lType.getName(), new CustomType(lType).setAttr(CustomAttr.JAVA_ONLY));
		}
	}
	
	private void addServiceMeta(){
		
		Class<?> type = DapServiceEngine.class;
		CustomType svcEngine = new CustomType(type, VJO_DAP_SVC_ENGINE)
			.setAttr(CustomAttr.MAPPED_TO_VJO)
			.addCustomMethod(new CustomMethod("publish"));
		addCustomType(type.getName(), svcEngine);
//		addCustomType(VJO_DAP_SVC_ENGINE, svcEngine);
		addPrivilegedTypeProcessor(type.getName(), new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,
					final JstIdentifier identifier, 
					final IExpr optionalExpr, 
					final List<IExpr> args, 
					final boolean isSuper,
					final BaseJstNode jstNode,
					final CustomType cType,
					final CustomMethod cMtd){
				
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
				mtdCall.setQualifyExpr(optionalExpr);
				for(int i=0;i<args.size();i++){
					if(args.get(i) == null){
						continue;
					}
					if (args.get(i) instanceof ObjCreationExpr){
						ObjCreationExpr oExpr = (ObjCreationExpr)args.get(i);
						StringBuilder sb = new StringBuilder("{");
						String argName;
						List<IExpr> oArgs = oExpr.getInvocationExpr().getArgs();
						for (int j=0; j<oArgs.size(); j++){
							argName = oArgs.get(j).toExprText();
							sb.append("\"").append(argName).append("\":").append(argName);
							if (j < oArgs.size()-1){
								sb.append(",");
							}
						}
						sb.append("}");
						mtdCall.addArg(new JstIdentifier(sb.toString()));
					}
					else {
						mtdCall.addArg(new JstIdentifier(args.get(i).toExprText()));
					}
				}
				return mtdCall;
			}
		});
		
		type = IServiceSpec.class;
		CustomType iSvcSpec = new CustomType(type, VJO_I_SVC_SPEC);
		addCustomType(type.getName(), iSvcSpec);
		addCustomType(VJO_I_SVC_SPEC, iSvcSpec);
		
		// HZ: DefaultServiceSpec translation is not supported
//		type = DefaultServiceSpec.class;
//		CustomType defaultSvcSpec = new CustomType(type, VJO_SVC_SPEC);
//		addCustomType(type.getName(), defaultSvcSpec);
//		addCustomType(VJO_SVC_SPEC, defaultSvcSpec);
	}
	
	private void addEventDispatcherXMeta(){
	
		CustomType dispatcher = new CustomType(IClassR.EventDispatcherXName, IClassR.EventDispatcherName)
			.setAttr(CustomAttr.MAPPED_TO_VJO)
			.addCustomMethod(new CustomMethod("addEventListener"));
		addCustomType(IClassR.EventDispatcherXName, dispatcher);
//		addCustomType(IClassR.EventDispatcherName, dispatcher);
		addPrivilegedTypeProcessor(IClassR.EventDispatcherXName, new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
					final ASTNode astNode,
					final JstIdentifier identifier, 
					final IExpr optionalExpr, 
					final List<IExpr> args, 
					final boolean isSuper,
					final BaseJstNode jstNode,
					final CustomType cType,
					final CustomMethod cMtd){
				
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
				mtdCall.setQualifyExpr(optionalExpr);
				boolean isEventListener = (args.size() == 4);
				String onEventMethodName = null;
				int argIndex = 1;
				for(IExpr arg : args){
					String argValue = arg.toExprText();
					if (argIndex == 2 && isEventListener) {
						onEventMethodName = getOnEventMethodExpr(argValue);
					}
					if (argIndex == 3 && isEventListener) {
						mtdCall.addArg(new JstIdentifier(argValue + onEventMethodName));
					}
					if (argIndex == 4 && isPrototype(argValue)) {
						argValue = getType(argValue);
					}
					mtdCall.addArg(new JstIdentifier(argValue));
					argIndex++;
				}
				return mtdCall;
			}
		});
	}
	
	private static String getOnEventMethodExpr(String eventStr) {
		//remove "", and toUpperCase the leading char
		int length = eventStr.length();
		char[] chars = new char[length - 2];
		for (int i = 1; i < length -1; i++) {
			chars[i-1] = eventStr.charAt(i);
			if (i == 1) {
				chars[i-1] = Character.toUpperCase(chars[i-1]);
			}
		}
		return ".on" + String.valueOf(chars);
	}
	
	private static final String PROTOTYPE = ".prototype";
	private static boolean isPrototype(String argValue) {
		return (argValue != null && argValue.endsWith(PROTOTYPE));
	}
	
	private static String getType(String argValue) {
		return argValue.substring(0, argValue.length() - PROTOTYPE.length());
	}
	
	private void addOtherMeta(){
		
		Class<?> type = IJsEventListenerProxy.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = IDapEventListener.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = IDapHostEventHandler.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));

		type = IJsEventListenerProxy.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = IDapEventListener.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = IDapHostEventHandler.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));

		type = ADapSvcSuccessHandler.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = ADapSvcErrorHandler.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = IDapSvcCallback.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
				
		type = NativeJsProxy.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = INativeJsFuncProxy.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = NativeJsFuncProxy.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
		
		type = NativeJsTypeRef.class;
		addCustomType(type.getName(), new CustomType(type).setAttr(CustomAttr.JAVA_ONLY));
	}
	
	private void addObjectLiteralMeta(){
		
		CustomType objLiteralType = new CustomType(IClassR.OlName, IClassR.ObjLiteralName)
			.setAttr(CustomAttr.JS_PROXY);
		addCustomType(IClassR.OlName, objLiteralType);
		addPrivilegedMethodProcessor(IClassR.OlName, "obj", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				
				ObjLiteral ol = new ObjLiteral();
				int size = args.size();
				
				if (size %2 != 0) {
					throw new RuntimeException(IClassR.OlName + " should take name and value pairs.");
				}
				for(int i = 0; i < size; i += 2) {
					String name = args.get(i).toExprText();
					ol.add(name, args.get(i + 1));					
				}
				
				return ol;
			}
		});
		addPrivilegedMethodProcessor(IClassR.OlName, "get", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				if (args.size() != 1) {
					throw new RuntimeException(IClassR.OlName + ".get needs one argument");
				}
				return new ArrayAccessExpr(optionalExpr, args.get(0));
			}
		});
		addPrivilegedMethodProcessor(IClassR.OlName, "put", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				if (args.size() != 2) {
					throw new RuntimeException(IClassR.OlName + ".put needs two arguments");
				}
				ArrayAccessExpr left = new ArrayAccessExpr(optionalExpr, args.get(0));
				return new AssignExpr(left, args.get(1));
			}
		});
		addPrivilegedMethodProcessor(IClassR.OlName, "remove", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				if (args.size() != 1) {
					throw new RuntimeException(IClassR.OlName + ".remove needs one argument");
				}
				ArrayAccessExpr right = new ArrayAccessExpr(optionalExpr, args.get(0));
				return new TextExpr("delete " + right.toExprText());
			}
		});
	}
	
	private void addArrayProxyMeta(){
		
		CustomType arrayType = new CustomType(IClassR.ArrayProxyName, IClassR.NativeArrayName)
//			.addCustomMethod(new CustomMethod("get"))
//			.addCustomMethod(new CustomMethod("put"))
//			.addCustomMethod(new CustomMethod("getLength"))
//			.addCustomMethod(new CustomMethod("make"))
			.setAttr(CustomAttr.JS_PROXY)
			.setRemoveTypeQualifier(true);
		
		Map<String,String> specialMethods = new HashMap<String, String>();
		specialMethods.put("get", "");
		specialMethods.put("put", "");
		specialMethods.put("getLength", "");
		specialMethods.put("make", "");
		
		for(Method method : Array.class.getMethods()){
			CustomMethod customMethod = new CustomMethod(method.getName());
			
			arrayType.addCustomMethod(customMethod);
			
			
			
			if(specialMethods.get(method.getName())==null){
				
				
				customMethod.setJstReturnTypeName(method.getReturnType().getName());
				
				
				addPrivilegedMethodProcessor(IClassR.ArrayProxyName, method.getName(), 
						new PrivilegedProcessorAdapter(){
						@Override
						public IExpr processMtdInvocation(ASTNode astNode,
								JstIdentifier identifier,
								IExpr optionalExpr, List<IExpr> jstArgs,
								boolean isSuper, BaseJstNode jstNode,
								CustomType clientType,
								CustomMethod clientMethod) {
							// TODO look at different approach to this
							identifier.setJstBinding(null);
							
							
							
							return super.processMtdInvocation(astNode, identifier, optionalExpr, jstArgs,
									isSuper, jstNode, clientType, clientMethod);
						}
					
				});
			}
			
		}
		
		
		
		
		
		
		addCustomType(IClassR.ArrayProxyName, arrayType);
		addPrivilegedMethodProcessor(IClassR.ArrayProxyName, "get", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				if (args.size() != 1) {
					throw new RuntimeException(IClassR.ArrayProxyName + ".get needs one argument");
				}
				return new ArrayAccessExpr(optionalExpr, args.get(0));
			}
		});
		addPrivilegedMethodProcessor(IClassR.ArrayProxyName, "put", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				if (args.size() != 2) {
					throw new RuntimeException(IClassR.ArrayProxyName + ".put needs two arguments");
				}
				ArrayAccessExpr left = new ArrayAccessExpr(optionalExpr, args.get(0));
				return new AssignExpr(left, args.get(1));
			}
		});
		addPrivilegedMethodProcessor(IClassR.ArrayProxyName, "getLength", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				return new PtyGetter(new JstIdentifier("length"), optionalExpr);
			}
		});
		addPrivilegedMethodProcessor(IClassR.ArrayProxyName, "make", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				return args.get(0);
			}
		});
		addPrivilegedConstructorProcessor(IClassR.ArrayProxyName, new PrivilegedProcessorAdapter(){
			public IExpr processInstanceCreation(
					final ClassInstanceCreation cic,
					final BaseJstNode jstNode,
					final List<IExpr> argExprList,
					final CustomType clientType){
				
				List<?> args = cic.arguments();
				if (args.size() == 1) {
					IExpr literal = argExprList.get(0);
					if (literal instanceof SimpleLiteral) {
						SimpleLiteral simple = ((SimpleLiteral)literal);
						if (simple.getResultType()!=null && "int".equals(simple.getResultType().getName())) {
							return TranslateHelper.Expression
								.createObjCreationExpr(IClassR.ArrayProxySimapleName, argExprList.get(0));
						}
					}
					
				}
				JstArrayInitializer array = new JstArrayInitializer();
				for (IExpr elem: argExprList){
					array.add(elem);
				}
				return array;
			}
		});
	}
	
	private void addJTypeMeta(){
		
		CustomType jtypeType = new CustomType(IClassR.JTypeName, org.ebayopensource.dsf.jsnative.global.Object.class.getName())
			.addCustomMethod(new CustomMethod("def"))
			.setAttr(CustomAttr.JS_PROXY);
		addCustomType(IClassR.JTypeName, jtypeType);
		addPrivilegedMethodProcessor(IClassR.JTypeName, "def", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				if (args.size() != 1) {
					throw new RuntimeException(IClassR.JTypeName + ".def needs one argument");
				}
				FieldAccessExpr arg = (FieldAccessExpr)args.get(0);				
				return arg.getExpr();
			}
		});
	}
	
	private void addJFunctionMeta(){
		
		CustomType rhinFunc = new CustomType(IClassR.JFunctionName, IClassR.NativeFunctionName);
		rhinFunc.setAttr(CustomAttr.MAPPED_TO_JS);
		
		addCustomType(IClassR.JFunctionName, rhinFunc);
		
		
		/* TODO
		 * Function should map to js
		 * JFunciton utilities move them out fix up tests
		 * apply and call map to js
		 * def is utility
		 * see if there are any test cases that utilize this code now
		 * 
		 */
		CustomType jfuncType = new CustomType(IClassR.JFunctionName, IClassR.NativeFunctionName)
			
			.addCustomMethod(new CustomMethod("def"))
			.addCustomMethod(new CustomMethod("call"))
			.setAttr(CustomAttr.MAPPED_TO_JS);
		addCustomType(IClassR.JFunctionName, jfuncType);
		
		addPrivilegedMethodProcessor(IClassR.JFunctionName, "call", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				// Remove ".clazz" in first arg (X.class)
				IExpr firstArg = args.get(0);
				if (!(firstArg instanceof FieldAccessExpr)){
					return null;
				}
				
				FieldAccessExpr argClazz = (FieldAccessExpr)firstArg;
				VjoConvention conv = TranslateCtx.ctx().getConfig().getVjoConvention();
				if (conv.getClassKeyword().equals(argClazz.getName().getName())){
					args.set(0, argClazz.getExpr());
				}
				return null;
			}
		});
		
		
		
		CustomType jfuncXType = new CustomType(IClassR.JFunctionNameX, IClassR.NativeFunctionName)
		
		.addCustomMethod(new CustomMethod("def"))
//		.addCustomMethod(new CustomMethod("call"))
		.setAttr(CustomAttr.JS_PROXY);
		addCustomType(IClassR.JFunctionNameX, jfuncXType);
		
		
		
		addPrivilegedMethodProcessor(IClassR.JFunctionNameX, "def", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				if (args.size() < 2) {
					throw new RuntimeException(IClassR.JFunctionName + ".def needs at least two arguments");
				}
				IExpr arg0 = args.get(0);
				JstIdentifier qualifier = null;
				if (arg0 instanceof JstIdentifier) {
					qualifier = (JstIdentifier)arg0;
				}
				else if (arg0 instanceof FieldAccessExpr) {
					qualifier = (JstIdentifier)((FieldAccessExpr)arg0).getExpr();
				}
				IExpr argFuncName = args.get(1);
				if (argFuncName instanceof SimpleLiteral){
					String funcName = ((SimpleLiteral)argFuncName).getValue();
					return new JstIdentifier(funcName, qualifier);
				}
				else if (argFuncName instanceof JstIdentifier) {
					return new ArrayAccessExpr(qualifier, argFuncName);
				}
				return null;
			}
		});

	}
	private IExpr fieldOrProp(String target, IExpr expr) {
		String exprText = expr.toExprText();

		if (expr instanceof SimpleLiteral) {
			exprText = exprText.replaceAll("\"", "");

			return new FieldAccessExpr(new JstIdentifier(exprText),
					new TextExpr(target));
		}

		return new ArrayAccessExpr(new TextExpr(target), expr);
	}

	private IExpr from(String target, IExpr expr) {
//		String exprText = expr.toExprText();

		if (expr instanceof SimpleLiteral) {
//			exprText = exprText.replaceAll("\"", "");
			MtdInvocationExpr mtdCall = new MtdInvocationExpr(target);
			mtdCall.addArg(new TextExpr(target));
			mtdCall.addArg(expr);
			return mtdCall; // target.exprText style
		}

		// target[expr] ala property style
		return new ArrayAccessExpr(new TextExpr(target), expr);
	}

	private void addJsxMeta() {
		final String javaTypeName = IClassR.VJJsxHelperName;

		CustomType funcXType = new CustomType(javaTypeName, javaTypeName)
				.addCustomMethod(new CustomMethod("isDefined"))
				.addCustomMethod(new CustomMethod("boolExpr"))
				.addCustomMethod(new CustomMethod("ref")).addCustomMethod(
						new CustomMethod("hitch")).addCustomMethod(
						new CustomMethod("curry")).addCustomMethod(
						new CustomMethod("bind")).setAttr(CustomAttr.JAVA_ONLY);
		addCustomType(javaTypeName, funcXType);

		addPrivilegedMethodProcessor(javaTypeName, "isDefined",
				new PrivilegedProcessorAdapter() {
					public IExpr processMtdInvocation(final ASTNode astNode,
							final JstIdentifier identifier,
							final IExpr optionalExpr, final List<IExpr> args,
							final boolean isSuper, final BaseJstNode jstNode,
							final CustomType cType, final CustomMethod cMtd) {
						IExpr type = args.get(0);
						IExpr left;
						String typeOf = "typeof ";
						if (args.size() == 1) { // ex: if (someExpr) ...
							left = new TextExpr(typeOf + type.toExprText());
						} else {
							left = new TextExpr(typeOf + type.toExprText()
									+ "[" + args.get(1).toExprText() + "]");
						}
						
						return new BoolExpr(left,
								new SimpleLiteral(String.class, JstCache
										.getInstance().getType("String"),"undefined"),
								Operator.NOT_EQUALS);
					}
				});

		addPrivilegedMethodProcessor(javaTypeName, "boolExpr",
				new PrivilegedProcessorAdapter() {
					public IExpr processMtdInvocation(final ASTNode astNode,
							final JstIdentifier identifier,
							final IExpr optionalExpr, final List<IExpr> args,
							final boolean isSuper, final BaseJstNode jstNode,
							final CustomType cType, final CustomMethod cMtd) {
						IExpr type = args.get(0);
						if (args.size() == 1) { // ex: if (someExpr) ...
							return type;
						}
						// ex: if (target.prop) ... prop can be literal and then
						// we use the target.prop format but otherwise we need
						// to
						// use the target[expr] format.
//						IExpr expr = args.get(1);
//						if (expr.getClass().isAssignableFrom(
//								SimpleLiteral.class)) {
//							SimpleLiteral propName = (SimpleLiteral) expr;
//							JstIdentifier idf = new JstIdentifier(propName
//									.getValue());
//							return new FieldAccessExpr(idf, type);
//						}

						//use array access notation, as literal may start with
						//non supported characters, ex: foo.1abc will throw js error
						return new ArrayAccessExpr(type, args.get(1));
					}
				});
		
		addPrivilegedMethodProcessor(javaTypeName, "ref",
				new PrivilegedProcessorAdapter() {
					public IExpr processMtdInvocation(final ASTNode astNode,
							final JstIdentifier identifier,
							final IExpr optionalExpr, final List<IExpr> args,
							final boolean isSuper, final BaseJstNode jstNode,
							final CustomType cType, final CustomMethod cMtd) {

						if (args.size() < 1) {
							throw new RuntimeException(javaTypeName
									+ ".ref needs at least 1 argument");
						}

						// Handle simple case of VJ.jsx.ref("someFunc")
						IExpr fref = args.get(0);
						if (args.size() == 1) {
							return fieldOrProp("this.vj$.type", fref);
						}

						// Handle cases of two arguments
						// VJ.jsx.ref(this, "foo") -> this.foo
						// VJ.jsx.ref(this, expr2) -> this[expr2]
						//
						// VJ.jsx.ref(A.class, "zot") -> this.vj$.A.zot
						// VJ.jsx.ref(A.class, expr2) -> this.vj$.A[expr2]

						String target = args.get(0).toExprText();
						if (target.endsWith(".clazz")) {
							target = target.substring(0, target
									.indexOf(".clazz"));
						}

						if (args.size() == 2) { // funcRef is 2nd arg
							return fieldOrProp(target, args.get(1));
						} else if (args.size() == 3) {
							return fieldOrProp(target, args.get(2));
						}

						throw new DsfRuntimeException(
							"jsx.ref(...) should not have 4 or more args");
					}
				});

		addPrivilegedMethodProcessor(javaTypeName, "hitch",
				new PrivilegedProcessorAdapter() {
					public IExpr processMtdInvocation(final ASTNode astNode,
							final JstIdentifier identifier,
							final IExpr optionalExpr, final List<IExpr> args,
							final boolean isSuper, final BaseJstNode jstNode,
							final CustomType cType, final CustomMethod cMtd) {

						// reset qualifier
						identifier.setQualifier(new JstIdentifier("vjo"));
						identifier.setJstBinding(null);
						MtdInvocationExpr mtdCall = new MtdInvocationExpr(
								identifier);
						IExpr ctx = args.get(0);
						IExpr func = args.get(1);

						ctx = processForClazzRef(ctx);

						mtdCall.addArg(ctx);
						mtdCall.addArg(func);

						return mtdCall;
					}
				});

		/**
		 * Bind will minimally do: 1. get the function ref 2. hitch that ref to
		 * the object it was defined on. This could be a static or an instance.
		 */
		addPrivilegedMethodProcessor(javaTypeName, "bind",
				new PrivilegedProcessorAdapter() {
					public IExpr processMtdInvocation(final ASTNode astNode,
							final JstIdentifier identifier,
							final IExpr optionalExpr, final List<IExpr> args,
							final boolean isSuper, final BaseJstNode jstNode,
							final CustomType cType, final CustomMethod cMtd) {

						identifier.setQualifier(new JstIdentifier("vjo"));
						identifier.setJstBinding(null);
						MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
						
						IExpr firstExpr = args.get(0);
						IJstType type = firstExpr.getResultType();
						if (type != null && "String".equals(type.getName())) {
							//add scope object as caller type
							mtdCall.addArg(new TextExpr("this.vj$.type"));
							mtdCall.addArg(firstExpr);
						}
						else {
							mtdCall.addArg(processForClazzRef(firstExpr));
						}
						if (args.size() >= 2) {
							IExpr secondExpr = args.get(1);
							//ignore the return class type
							if (!isClassRef(secondExpr)) {
								mtdCall.addArg(secondExpr);
							}
						}
						if (args.size() > 2) {
							for (int i = 2; i < args.size(); i++) {
								IExpr arg = args.get(i);
								mtdCall.addArg(arg);
							}
						}
						return mtdCall;
					}
				});

		addPrivilegedMethodProcessor(javaTypeName, "curry",
				new PrivilegedProcessorAdapter() {
					public IExpr processMtdInvocation(final ASTNode astNode,
							final JstIdentifier identifier,
							final IExpr optionalExpr, final List<IExpr> args,
							final boolean isSuper, final BaseJstNode jstNode,
							final CustomType cType, final CustomMethod cMtd) {
						identifier.setQualifier(new JstIdentifier("vjo"));
						MtdInvocationExpr mtdCall = new MtdInvocationExpr(
								identifier);
						identifier.setJstBinding(null);
						for (IExpr arg : args) {
							mtdCall.addArg(arg);
						}
						return mtdCall;
					}
				});
	}

	// MrP - need to remove this since FuncX is proxied via VJ.jsx...
	
	private void addFuncXMeta(){
		
		CustomType funcXType = new CustomType(IClassR.FuncXName, IClassR.FuncXName)
			.addCustomMethod(new CustomMethod("hitch"))
			.addCustomMethod(new CustomMethod("curry"))
			.setAttr(CustomAttr.JAVA_ONLY);
		addCustomType(IClassR.FuncXName, funcXType);
		addPrivilegedMethodProcessor(IClassR.FuncXName, "hitch", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				
				//reset qualifier
				identifier.setQualifier(new JstIdentifier("vjo"));
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
				for(IExpr arg : args){
					mtdCall.addArg(arg);
				}
				return mtdCall;
			}
		});
		addPrivilegedMethodProcessor(IClassR.FuncXName, "curry", new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
				identifier.setJstBinding(null);
				identifier.setQualifier(new JstIdentifier("vjo"));
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(identifier);
				for(IExpr arg : args){
					mtdCall.addArg(arg);
				}
				return mtdCall;
			}
		});
	}
	
	private static final String JqName = "vjo.dsf.jqueryx.Jq";
	private void addJqMeta(){
		CustomType jqType = new CustomType(JqName, JqName);
		addCustomType(JqName, jqType);
		addPrivilegedTypeProcessor(JqName, new PrivilegedProcessorAdapter(){
			public IExpr processMtdInvocation(
				final ASTNode astNode,
				final JstIdentifier identifier, 
				final IExpr optionalExpr, 
				final List<IExpr> args, 
				final boolean isSuper,
				final BaseJstNode jstNode,
				final CustomType cType,
				final CustomMethod cMtd){
							
				IJstNode binding = identifier.getJstBinding();
				if (!(binding instanceof IJstMethod)) {
					return null;
				}
				if (!((IJstMethod)binding).isStatic()) {
					return null;
				}
							
				JstIdentifier newIdentifier = identifier;
				if ("$".equals(identifier.getName())) {
					newIdentifier = new JstIdentifier("$");
					newIdentifier.setType(identifier.getResultType());
				}
				else {
					newIdentifier.setQualifier(new JstIdentifier("jQuery"));					
				}
				
				MtdInvocationExpr mtdCall = new MtdInvocationExpr(newIdentifier);
				for(IExpr arg : args){
					mtdCall.addArg(arg);
				}
				return mtdCall;
			}
		});
	}
	private static IExpr processForClazzRef(IExpr expr) {
		if (expr instanceof FieldAccessExpr) {
			FieldAccessExpr fieldIdentifier = (FieldAccessExpr)expr;
			if ("clazz".equals(fieldIdentifier.getName().getName())) {
				expr = fieldIdentifier.getExpr();
			}
		}
		return expr;
	}
	private static boolean isClassRef(IExpr expr) {
		if (expr instanceof FieldAccessExpr) {
			FieldAccessExpr fieldIdentifier = (FieldAccessExpr)expr;
			return "clazz".equals(fieldIdentifier.getName().getName());
		}
		return false;
	}
}