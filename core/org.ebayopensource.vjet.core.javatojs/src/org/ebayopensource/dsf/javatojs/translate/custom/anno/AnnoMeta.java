/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom.anno;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;

import org.ebayopensource.dsf.javatojs.translate.custom.meta.PrivilegedProcessorAdapter;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.IPrivilegedProcessor;
import org.ebayopensource.dsf.jsgen.shared.classref.IClassR;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.IJstAnnotation;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.expr.JstArrayInitializer;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.term.ObjLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;

public class AnnoMeta implements IAnnoCustomMetaProvider{

	private Map<String,IPrivilegedProcessor> m_processors = 
		new HashMap<String,IPrivilegedProcessor>();
	
	// 
	// Singleton
	//
	private static AnnoMeta s_instance = new AnnoMeta();
	private static boolean m_initialized = false;
	private AnnoMeta(){}
	public static AnnoMeta getInstance(){
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

	private void init() {
		initOl();
	}
	
	private void initOl() {
		m_processors.put(IClassR.NativeOlKeysAnnoSimpleName,
			new PrivilegedProcessorAdapter() {
				public IExpr processMtdInvocation(ASTNode astNode,
						JstIdentifier identifier, IExpr optionalExpr,
						List<IExpr> args, boolean isSuper,
						BaseJstNode jstNode, CustomType clientType,
						CustomMethod clientMethod) {

					IJstType type = null;
					List<String> keys = new ArrayList<String>();
					JstIdentifier ident = identifier;
					if (optionalExpr instanceof JstIdentifier) { 
						ident = (JstIdentifier)optionalExpr;
					}
					type = ident.getType();
					if (type != null) {
						IJstMethod method = type.getMethod(identifier
							.getName());
						IJstAnnotation anno = method
							.getAnnotation(IClassR.NativeOlKeysAnnoSimpleName);
						IExpr value = getValue("values", anno);
						if (value != null
								&& value instanceof JstArrayInitializer) {

							JstArrayInitializer arr = (JstArrayInitializer) value;
							for (IExpr key : arr.getExprs()) {
								keys.add(key.toExprText());
							}
						}
					}

					ObjLiteral ol = new ObjLiteral();
					int size = args.size();
					if (keys.size() > 0) {
						if (keys.size() != size) {
							throw new RuntimeException(type.getName()
									+ " key value mismatch.");
						}
						for (int i = 0; i < size; i++) {
							ol.add(keys.get(i), args.get(i));
						}
					}
					return ol;
				}
			});
	}
	
	private IExpr getValue(String key, IJstAnnotation annotation) {
		List<IExpr> annos = annotation.values();
		for (IExpr anno : annos) {
			if (anno instanceof AssignExpr) {
				AssignExpr a = (AssignExpr)anno;
				if (key.equals(a.getLHS().toString())) {
					return a.getExpr();
				}
			}
		}
		return null;
	}
	
	public IPrivilegedProcessor getPrivilegedProcessor(String anno) {
		return m_processors.get(anno);
	}
}
