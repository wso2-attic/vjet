/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.node;


public class Initializer {
//	public static class IAttributeMapHandler implements DynamicPropertyHandler {
//		private static final String [] EMPTY = new String[0];
//		
//		public Object getProperty(
//			final Object object, final String propertyName)
//		{
//			final IAttributeMap attrs = (IAttributeMap)object;
//			final Object value = attrs.get(propertyName);
//			return value;
//		}
//		
//		public String[] getPropertyNames(final Object object) {
//			final IAttributeMap attrs = (IAttributeMap)object;
//			if (attrs.size() <= 0) {
//				return EMPTY;
//			}
//			
//			final String [] names = new String [attrs.size()];
//			final Iterator<String> itr = attrs.getKeys();
//			int i = 0;
//			while (itr.hasNext()) {
//				names[i++] = itr.next();
//			}
//			return names;
//		}
//		
//		public void setProperty(
//			final Object object, final String propertyName, final Object value)
//		{
//			final IAttributeMap attrs = (IAttributeMap)object;
//			attrs.put(propertyName, value);
//		}
//	}
	
	private static boolean inited=false;
	static {
		init();
	}
	
	public static void init() {
		if (inited) {
			return ;
		}
//		JXPathContextReferenceImpl.addNodePointerFactory(
//			new IDsfComponentPointerFactory());
//		JXPathContextReferenceImpl.addNodePointerFactory(
//			new IDsfComponentPointerFactory());
//		JXPathContextReferenceImpl.addNodePointerFactory(
//			new BeanPointerFactory());
//		JXPathContextReferenceImpl.addNodePointerFactory(
//			new IAttributeMapPointerFactory());
//		JXPathContextReferenceImpl.addNodePointerFactory(
//			new IChildrenPointerFactory());
//		JXPathIntrospector.registerDynamicClass(IAttributeMap.class,
//			IAttributeMapHandler.class);
		inited = true;
	}
}
