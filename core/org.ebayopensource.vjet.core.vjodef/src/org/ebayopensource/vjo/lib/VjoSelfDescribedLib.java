/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.lib;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.util.JstTypeSerializer;
import org.ebayopensource.dsf.jst.IJstSerializer;

public class VjoSelfDescribedLib {
	
	private static final String VJO_CLASS = "vjo.Class";
	private static final String VJO_OBJECT = "vjo.Object";
	private static final String VJO_ENUM = "vjo.Enum";
	private static final VjoSelfDescribedLib s_instance = new VjoSelfDescribedLib();
	private static JstType m_class;
	private static JstType m_object;
	private static JstType m_enum;
	
	private IResourceResolver m_resourceResolver;
	
	public static VjoSelfDescribedLib getInstance(){
		return s_instance;
	}
	
	public VjoSelfDescribedLib setResourceResolver(final IResourceResolver resourceResolver){
		m_resourceResolver = resourceResolver;
		return this;
	}
	
	public List<IJstType> getTypes(){
		clear();
		
		List<IJstType> l = new ArrayList<IJstType>();
		
		l.addAll(getVjoJst());
		
		fixVjoLib(l);
		
		return l;
	}
	
	private static void fixVjoLib(List<IJstType> types) {
		
		
		for(IJstType type: types){
	
			if(type instanceof JstType){
				JstType jstType = (JstType)type;
				JstCache.getInstance().addType(jstType);
			}
		}
		
		JstType OBJECT = getObject();
		JstType CLASS = getVjoClass();
		JstType ENUM = JstCache.getInstance().getType(VJO_ENUM);
		
		JstType[] types2 = new JstType[]{OBJECT,CLASS,ENUM};
		
		for(JstType jstType: types2){
		        if(jstType == null){
				continue;
			}	
			
			
			IJstType extend = jstType.getExtend();
			if(extend==null){
				jstType.addExtend(OBJECT);
			}
			
			for(IJstMethod mtd: jstType.getMethods()){
				JstMethod mtd2 = (JstMethod)mtd;
				IJstType t = mtd2.getRtnType();
				if(t==null){
					continue;
				}
				
				mtd2.setRtnType(fixType(mtd2.getRtnType()));
			}
			
			for(IJstProperty pty: jstType.getProperties()){
				JstProperty pty2 = (JstProperty)pty;
				IJstType t = pty2.getType();
				if(t==null){
					continue;
				}
				
				pty2.setType(fixType( pty.getType()));
			}
			
			
			
			
		}
		
	}


	private static JstType getVjoClass() {
		if(m_class==null){
			m_class =  JstCache.getInstance().getType(VJO_CLASS);
		}
		
		return m_class;
	}


	private static JstType getObject() {
		if(m_object==null){
			m_object =  JstCache.getInstance().getType(VJO_OBJECT);
		}
		return m_object;
	}


	private static JstType getVjoEnum() {
		if(m_enum==null){
			m_enum = JstCache.getInstance().getType(VJO_ENUM);
		}
		return m_enum;
	}
	
	private static IJstType fixType(IJstType t) {
		
		JstType OBJECT = getObject();
		JstType CLASS = getVjoClass();
		JstType ENUM = getVjoEnum();
		
		if(t.getName().contains(VJO_OBJECT)){
			return OBJECT;
		}
		if(t.getName().contains(VJO_CLASS)){
			return CLASS;
		}
		if(t.getName().contains(VJO_ENUM)){
			return ENUM;
		}
		return t;
	}

	



	public List<IJstType> getVjoJst(){
		if (m_resourceResolver == null){
			return Collections.emptyList();
		}
		try {
			IJstSerializer serializer = m_resourceResolver.getJstSerializer();
			if (serializer == null){
				serializer = JstTypeSerializer.getInstance();
			}
			InputStream fio = m_resourceResolver.getVjoLibSerializedStream();
			List<IJstType> deserialize = serializer.deserialize(fio);
			return deserialize;
		} catch (RuntimeException e) {
			e.printStackTrace();	//KEEPME
		}
		return Collections.EMPTY_LIST;
	}


	private void clear() {
		m_class= null;
		m_enum = null;
		m_object = null;
		
	}
	
	
	
}
