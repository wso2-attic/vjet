/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.control;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;

import org.ebayopensource.dsf.dap.api.anno.ADapSvcErrorHandler;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcOnAll;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcOnFailure;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcOnSuccess;
import org.ebayopensource.dsf.dap.api.anno.ADapSvcSuccessHandler;
import org.ebayopensource.dsf.dap.event.listener.DapEventListenerHelper;
import org.ebayopensource.dsf.dap.event.listener.IDapEventListener;
import org.ebayopensource.dsf.dap.event.listener.IJsEventListenerProxy;
import org.ebayopensource.dsf.dap.svc.IJsSvcCallbackProxy;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.html.js.IJsFunc;
import org.ebayopensource.dsf.javatojs.translate.AstBinding;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.generate.IJsrGenListener;
import org.ebayopensource.dsf.jsgen.shared.generate.Indenter;
import org.ebayopensource.dsf.jsgen.shared.generate.SourceGenerator;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstRefType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
import org.ebayopensource.dsf.jst.traversal.JstDepthFirstTraversal;
import org.ebayopensource.dsf.service.ServiceIdHelper;

public class DapJsrGenListener implements IJsrGenListener {
	
	private static final Class<?> EVENT_LISTENER_PROXY = IJsEventListenerProxy.class;
	private static final Class<?> SVC_CALLBACK_PROXY = IJsSvcCallbackProxy.class;
	
	private static final String SVC_CALLBACK_MTD = "public Map<String,IJsFunc> getProxySvcCallbacks(){";
	private static final String SVC_ERROR_HANDLER_MTD = "public Map<String,IJsFunc> getProxySvcErrorHandlers(){";
	
//	private static final String SVC_CALLBACL_FUNC = "map.put({0}, {1}(JsHandlerObjectEnum.msgRespData));";
	private static final String SVC_CALLBACL_FUNC = "map.put({0}, call(\"{1}\").with(JsHandlerObjectEnum.msgRespData));";
//	private static final String SVC_ERROR_HANDLER_FUNC = "map.put({0}, {1}(JsHandlerObjectEnum.msgRespError));";
	private static final String SVC_ERROR_HANDLER_FUNC = "map.put({0}, call(\"{1}\").with(JsHandlerObjectEnum.msgRespError));";
	
	private IJstType m_jstType;
	private List<Class<? extends IDapEventListener>> m_dapInterfaces;
	private List<IJstMethod> m_svcCallback;
	private List<IJstMethod> m_svcSuccessHandler;
	private List<IJstMethod> m_svcErrorHandlers;
	private List<IJstType> m_imports;
	
	public void initialize(final IJstType jstType){
		if (jstType == null || !jstType.isClass()){
			return;
		}
		m_jstType = jstType;
		m_dapInterfaces = new ArrayList<Class<? extends IDapEventListener>>();
		m_svcCallback = new ArrayList<IJstMethod>();
		m_svcSuccessHandler = new ArrayList<IJstMethod>();
		m_svcErrorHandlers = new ArrayList<IJstMethod>();
		m_imports = new ArrayList<IJstType>();
		processEventListenerInterfaces(jstType);
		processSvcHandlers(jstType);
	}
	
	public void preImports(final PrintWriter writer, final CodeStyle style){
	};
	
	public void postImports(final Collection<String> importedTypeNames, final PrintWriter writer, final CodeStyle style){
		if (m_jstType == null){
			return;
		}
		String typeName;
		for (IJstType type: m_imports){
			typeName = AstBindingHelper.getSourceName(type);
			
			if (typeName != null && !importedTypeNames.contains(typeName)){
				writer.append("import ").append(typeName).append(";")
					.append(SourceGenerator.NEWLINE);
			}
		}
		boolean hasEvtHandlers = !getEventListenerInterfaces().isEmpty();
		if (hasEvtHandlers){
			writer.append("import ").append(EVENT_LISTENER_PROXY.getName()).append(";")
				.append(SourceGenerator.NEWLINE);
			writer.append("import ").append(EventType.class.getName()).append(";")
				.append(SourceGenerator.NEWLINE);
		}
		boolean hasSvcCallbacks = !getSvcSuccessHandlers().isEmpty() || !getSvcErrorHandlers().isEmpty();
		if (hasSvcCallbacks){
			writer.append("import ").append(SVC_CALLBACK_PROXY.getName()).append(";")
				.append(SourceGenerator.NEWLINE);

			writer.append("import ").append(ServiceIdHelper.class.getName()).append(";")
				.append(SourceGenerator.NEWLINE);

		}
		if (hasEvtHandlers || hasSvcCallbacks){
			writer.append("import ").append(IJsFunc.class.getName()).append(";")
				.append(SourceGenerator.NEWLINE);
			writer.append("import ").append(Map.class.getName()).append(";")
				.append(SourceGenerator.NEWLINE);
			writer.append("import ").append(HashMap.class.getName()).append(";")
				.append(SourceGenerator.NEWLINE);
//			writer.append("import ").append(IClassR.JsHandlerObjectEnum).append(";")
//				.append(SourceGenerator.NEWLINE);
		}
	};
	
	public void preInterfaces(final PrintWriter writer, final CodeStyle style){
	}
	
	public void postInterfaces(final PrintWriter writer, final CodeStyle style){
		if (m_jstType == null){
			return;
		}
		boolean hasImplements = !m_jstType.getSatisfies().isEmpty();
		if (!getEventListenerInterfaces().isEmpty()){
			if (hasImplements){
				writer.append(",");
			}
			else {
				writer.append(" implements ");
				hasImplements = true;
			}
			writer.append(EVENT_LISTENER_PROXY.getSimpleName());
		}
		
		if (!getSvcSuccessHandlers().isEmpty() || !getSvcErrorHandlers().isEmpty()){
			if (hasImplements){
				writer.append(",");
			}
			else {
				writer.append(" implements ");
				hasImplements = true;
			}
			writer.append(SVC_CALLBACK_PROXY.getSimpleName());
		}
	}

	public void preConstructors(final PrintWriter writer, final CodeStyle style){
	}
	
	public void postConstructors(final PrintWriter writer, final CodeStyle style){
		
		List<Class<? extends IDapEventListener>> interfaces = getEventListenerInterfaces();
		if (!interfaces.isEmpty()){
			genEventProxyMtd(interfaces, writer);
		}
		
		List<IJstMethod> svcCallbacks = getSvcSuccessHandlers();
		if (!svcCallbacks.isEmpty()){
			genSvcHandlerProxy(svcCallbacks, SVC_CALLBACK_MTD, SVC_CALLBACL_FUNC, writer);
		}
		
		List<IJstMethod> svcErrorHandlers = getSvcErrorHandlers();
		if (!svcErrorHandlers.isEmpty()){
			genSvcHandlerProxy(svcErrorHandlers, SVC_ERROR_HANDLER_MTD, SVC_ERROR_HANDLER_FUNC, writer);
		}
	}
	
	//
	// Private
	//
	private List<Class<? extends IDapEventListener>> getEventListenerInterfaces(){
		if (m_jstType == null || m_dapInterfaces == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_dapInterfaces);
	}
	
	private List<IJstMethod> getSvcCallbacks(){
		if (m_jstType == null || m_svcCallback == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_svcCallback);
	}
	
	private List<IJstMethod> getSvcSuccessHandlers(){
		if (m_jstType == null || m_svcSuccessHandler == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_svcSuccessHandler);
	}
	
	private List<IJstMethod> getSvcErrorHandlers(){
		if (m_jstType == null || m_svcErrorHandlers == null){
			return Collections.emptyList();
		}
		return Collections.unmodifiableList(m_svcErrorHandlers);
	}
	
	private void processEventListenerInterfaces(IJstType jstType){
		AstBinding binding = AstBindingHelper.getAstSrcBinding(jstType);
		if (binding == null){
			return; 
		}
		Collection<Class<? extends IDapEventListener>> dapListeners = DapEventListenerHelper.getAllEventListeners();
		Map<String,String> iNames = binding.getInterfaceNames();
		for (Class<? extends IDapEventListener> listener: dapListeners){
			if (iNames.containsValue(listener.getName())){
				m_dapInterfaces.add(listener);
			}
		}
	}
	
	private void processSvcHandlers(IJstType jstType){
		boolean isSvcHandler;
		for (IJstType itf: jstType.getSatisfies()){
			if (itf.getAnnotation(ADapSvcSuccessHandler.class.getSimpleName()) != null){
				for (IJstMethod mtd: itf.getMethods()){
					isSvcHandler = false;
					for (IJstAnnotation anno: mtd.getAnnotations()){
						if (ADapSvcOnSuccess.class.getSimpleName().equals(anno.getName().getName())){
							m_svcSuccessHandler.add(mtd);
							isSvcHandler = true;
						}
						if (ADapSvcOnFailure.class.getSimpleName().equals(anno.getName().getName())){
							m_svcErrorHandlers.add(mtd);
							isSvcHandler = true;
						}
						if (ADapSvcOnAll.class.getSimpleName().equals(anno.getName().getName())){
							m_svcCallback.add(mtd);
							isSvcHandler = true;
						}
						if (isSvcHandler){
							addToImport(anno, m_imports);
						}
					}
				}
			}
		}
		for (IJstMethod mtd: jstType.getMethods()){
			isSvcHandler = false;
			for (IJstAnnotation anno: mtd.getAnnotations()){
				if (ADapSvcSuccessHandler.class.getSimpleName().equals(anno.getName().getName())){
					m_svcSuccessHandler.add(mtd);
					isSvcHandler = true;
				}
				if (ADapSvcErrorHandler.class.getSimpleName().equals(anno.getName().getName())){
					m_svcErrorHandlers.add(mtd);
					isSvcHandler = true;
				}
				if (isSvcHandler){
					addToImport(anno, m_imports);
				}
			}
		}
	}
	
	private NormalAnnotation getAnnotation(IJstMethod jstMtd){
		MethodDeclaration astMtd = AstBindingHelper.getAstMethod(jstMtd);
		for (Object m: astMtd.modifiers()){
			if (m instanceof NormalAnnotation) {
				return (NormalAnnotation)m;
			}
		}
		AbstractTypeDeclaration astType = AstBindingHelper.getAstType(jstMtd.getOwnerType());
		for (Object m: astType.modifiers()){
			if (m instanceof NormalAnnotation) {
				return (NormalAnnotation)m;
			}
		}
		return null;
	}
	
	private void addToImport(IJstAnnotation anno, List<IJstType> imports){
		TypeVisitor visitor = new TypeVisitor(imports);
		JstDepthFirstTraversal.accept(anno, visitor);
	}
	
	private void genEventProxyMtd(List<Class<? extends IDapEventListener>> interfaces, PrintWriter writer){
		writer.append(SourceGenerator.NEWLINE);
		writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB);
		writer.append("public Map<EventType,IJsFunc> getProxyEventHandlers(){");
		writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB).append(Indenter.TAB);
		writer.append("Map<EventType,IJsFunc> map = new HashMap<EventType,IJsFunc>();");
		
		Method[] mtds;
		String mtdName;
		String evtName;
		for (Class<? extends IDapEventListener> evtListener: interfaces){
			mtds = evtListener.getDeclaredMethods();
			for (Method mtd: mtds){
				mtdName = mtd.getName();
				evtName = mtdName.substring(2).toUpperCase();
				writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB).append(Indenter.TAB);
				writer.append("map.put(EventType." + evtName + ", " + mtdName + "());");
			}
		}
		
		writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB).append(Indenter.TAB);
		writer.append("return map;");
		writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB);
		writer.append("}");
	}
	
	private void genSvcHandlerProxy(List<IJstMethod> svcHandlers, String mtdSigniture, String jsFuncPattern, PrintWriter writer){
		writer.append(SourceGenerator.NEWLINE);
		writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB);
		writer.append(mtdSigniture);
		writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB).append(Indenter.TAB);
		writer.append("Map<String,IJsFunc> map = new HashMap<String,IJsFunc>();");
		if (svcHandlers.size() > 1){
			writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB).append(Indenter.TAB);
			writer.append("String svcId;");
		}
		
		for (IJstMethod callback: svcHandlers){
			writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB).append(Indenter.TAB);
			if (svcHandlers.size() == 1){
				writer.append("String ");
			}
			writer.append("svcId = ").append(genSvcId(callback)).append(";");
			writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB).append(Indenter.TAB);
			writer.append(MessageFormat.format(jsFuncPattern, "svcId", callback.getName().getName()));
		}
		
		writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB).append(Indenter.TAB);
		writer.append("return map;");
		writer.append(SourceGenerator.NEWLINE).append(Indenter.TAB);
		writer.append("}");
	}
	
	private String genSvcId(IJstMethod jstMtd){
		NormalAnnotation anno = getAnnotation(jstMtd);
		if (anno == null){
			return null;
		}
		List<MemberValuePair> values = anno.values();
		if (values.size() < 2){
			return null;
		}
		MemberValuePair svcName = (MemberValuePair)values.get(1);
		if (values.size() < 3){
			return svcName.getValue().toString();
		}
		MemberValuePair opName = (MemberValuePair)values.get(2);
		return "ServiceIdHelper.createServiceId(" + svcName.getValue().toString() + "," + opName.getValue().toString() + ")";
	}
	
	//
	// Embeded
	//
	private static class TypeVisitor implements IJstVisitor {

		private List<IJstType> m_types = 
			new ArrayList<IJstType>();
		
		private TypeVisitor(List<IJstType> types){
			m_types = types;
		}
		
		//
		// Satisfy IJstVisitor
		//
		public void preVisit(IJstNode node){
		}
		
		public boolean visit(IJstNode node){
			if (node instanceof JstIdentifier){
				addType(((JstIdentifier)node).getType());
			}
			else if (node instanceof IExpr){
				addType(((IExpr)node).getResultType());
			}
			return true;
		}
		
		public void endVisit(IJstNode node){
		}
		
		public void postVisit(IJstNode node){
			
		}
		
		//
		// Private
		//
		private void addType(IJstType type){
			if (type == null || m_types.contains(type) || type instanceof JstRefType || !(type instanceof JstType)){
				return;
			}

			m_types.add(type);
		}
	}
}