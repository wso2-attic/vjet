/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;

/**
 * This factory provides methods to dynamically create mocked object that
 * implements a given bean-style interface as well as IMockBean interface.
 * 
 * The bean properties can be pre-populated by passed-in sample object.
 * 
 * The getters and possibly setters from the bean interface will access the
 * same set of properties as those accessors from IMockBean interface.
 * 
 * The generated object can be casted to both IMockBean and that specific
 * bean interface.
 * 
 * The IMockBean interface is mainly for tools/scripts to get/set the bean
 * properties, even though the bean interface itself might not have setters
 * for some of the bean properties.
 */
public abstract class MockBeanFactory {
	
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<T> type, T sample)	{
		try {
			return (T) Proxy.newProxyInstance(type.getClassLoader(),
				new Class[] { type, IMockBean.class },
				new MockInvocationHandler(type, sample));
		} catch (Exception e) {
			throw new DsfRuntimeException(e);
		}
	}
	@SuppressWarnings("unchecked")
	public static <T,U> T create(Class<T> types[], T sample)	{
		try {
			return (T) Proxy.newProxyInstance(types[0].getClassLoader(),
				new Class[] { types[0], IMockBean.class },
				new MockInvocationHandler(types, sample));
		} catch (Exception e) {
			throw new DsfRuntimeException(e);
		}
	}
	
	
	public static <T> T create(Class<T> type) {
		return create(type, null);
	}
	
	public static <T> IMockBean createMock(Class<T> type, T sample) {
		return (IMockBean) create(type, sample);
	}
	
	public static <T> IMockBean createMock(Class<T> type) {
		return (IMockBean) create(type, null);
	}
	
	private static final Object[] ARGS = new Object[] {};
	private static Method MOCK_GET = null;
	private static Method MOCK_SET = null;
	private static Method MOCK_GET_WITH_PROPERTY = null;
	private static Method MOCK_SET_WITH_PROPERTY = null;
	private static Method MOCK_BEAN_INFO = null;
	static {
		try {
			MOCK_GET = IMockBean.class.getDeclaredMethod("getMockValue", String.class);
			MOCK_SET = IMockBean.class.getDeclaredMethod("setMockValue", String.class, Object.class);
			MOCK_GET_WITH_PROPERTY = IMockBean.class.getDeclaredMethod("getMockValue", PropertyDescriptor.class);
			MOCK_SET_WITH_PROPERTY = IMockBean.class.getDeclaredMethod("setMockValue", PropertyDescriptor.class, Object.class);
			MOCK_BEAN_INFO = IMockBean.class.getDeclaredMethod("getBeanInfo");
		} catch (Exception e) {
			e.printStackTrace(); // KEEPME
		}
	}
	
	private static class MockInvocationHandler implements InvocationHandler {
		private Map<String, Object> m_propertyValues = new HashMap<String, Object>();
		private Map<Method, String> m_readPropertyMapping = new HashMap<Method, String>();
		private Map<Method, String> m_setPropertyMapping = new HashMap<Method, String>();
		private BeanInfo m_infos[];
		
		MockInvocationHandler(Class<?> type, Object sample) throws IntrospectionException {
			init(new BeanInfo[]{Introspector.getBeanInfo(type)}, sample);
		}
		MockInvocationHandler(Class<?> types[], Object sample) throws IntrospectionException  {
			List<BeanInfo> beanInfoList = new ArrayList<BeanInfo>();
			for(int i = 0; i < types.length; ++i) {
				Class<?> type = types[i];
				beanInfoList.add(Introspector.getBeanInfo(type));
			}
			init(beanInfoList.toArray(new BeanInfo[0]), sample);
		}
		void init(BeanInfo infos[], Object sample) {
			try {
				m_infos = infos;
				for(int i = 0; i < m_infos.length; ++i) {
					BeanInfo info = m_infos[i];
					for (PropertyDescriptor descriptor : info.getPropertyDescriptors()) {
						String name = descriptor.getName();
						Method read = descriptor.getReadMethod();
						if (read != null) {
							m_readPropertyMapping.put(read, name);
							m_propertyValues.put(name, read.invoke(sample, ARGS));
						}
						Method write = descriptor.getReadMethod();
						m_setPropertyMapping.put(write, name);
					}
				}	
			} catch (Exception e) {
				throw new DsfRuntimeException(e);
			}
		}
		
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String name = m_readPropertyMapping.get(method);
			if (name != null) {
				return m_propertyValues.get(name);
			}
			if (method.equals(MOCK_GET)) {
				return m_propertyValues.get(args[0]);
			}
			if (method.equals(MOCK_GET_WITH_PROPERTY)) {
				return m_propertyValues.get(((PropertyDescriptor)args[0]).getName());
			}
			if (method.equals(MOCK_SET)) {
				m_propertyValues.put((String)args[0], args[1]);
				return null;
			}
			if (method.equals(MOCK_SET_WITH_PROPERTY)) {
				m_propertyValues.put(((PropertyDescriptor)args[0]).getName(), args[1]);
				return null;
			}
			if (method.equals(MOCK_BEAN_INFO)) {
				return m_infos[0];
			}
			name = m_setPropertyMapping.get(method);
			if (name != null) {
				m_propertyValues.put(name, args[0]);
				return null;
			}
			return null;
		}		
	}
}
