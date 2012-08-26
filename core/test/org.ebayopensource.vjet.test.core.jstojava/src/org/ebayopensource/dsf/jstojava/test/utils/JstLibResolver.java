/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstSerializer;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.ResourceHelper;

public class JstLibResolver extends ResourceHelper implements IResourceResolver {
	
	//
	// Singleton
	//
	private static JstLibResolver s_instance = new JstLibResolver();
	private JstLibResolver(){}
	public static JstLibResolver getInstance(){
		return s_instance;
		
	}
	
	/**
	 * Returns the anchor class
	 * @return Class
	 */
	@Override
	protected Class<?> loadClass(final String clsName) throws ClassNotFoundException{
		return Class.forName(clsName);
	}
	
	@Override
	public IJstSerializer getJstSerializer(){
		return JstSerializer.getInstance();
	}
	
	public static class JstSerializer implements IJstSerializer {
		
		// Singleton
		private static JstSerializer s_instance = new JstSerializer();
		protected JstSerializer(){};
		public static JstSerializer getInstance(){
			return s_instance;
		}

		/**
		 * Deserialize a List of JstTypes from a given input stream.
		 * @param is InputStream
		 * @return list of JstTypes or <code>null</code> if any errors
		 */
		@Override
		public List<IJstType> deserialize(InputStream is) {
			List<IJstType> list = null;
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(is);
				list = (List<IJstType>) ois.readObject();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				if (ois != null) {
					try {
						ois.close();
					} catch (IOException e) {
						// NOPMD - ignore
					}
				}
			}
			return list;
		}
		
		/**
		 * Serializes a list of JstType objects and writes to given output stream
		 * @param jstTypes List of JstType objects
		 * @param os OutputStream
		 */
		@Override
		public void serialize(List<IJstType> jstTypes, OutputStream os) {
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(os);
				out.writeObject(jstTypes);
			} catch(IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						// NOPMD - ignore
					}
				}
			}		
		}
	}
}
