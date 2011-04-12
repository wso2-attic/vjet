/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdebugger.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Internal cache to keep the break points.
 */
class BreakpointCache {
	
	private static Map<String, Breakpoints> s_cache = new HashMap<String, Breakpoints>();
	
	static {
		sync();
	}
	
	static synchronized Breakpoints getBreakpoints(String url) {
		return s_cache.get(url);
	}
	
	static synchronized Breakpoints createBreakpoints(String url) {
		Breakpoints bp = getBreakpoints(url);
		if (bp == null) {
			bp = new Breakpoints(url);
			s_cache.put(url, bp);
		}
		return bp;
	}
	
	/**
	 * temp solution to persist the data into current working directory.
	 * it needs plug-in support to persist the data under workspace dir.
	 */
	private static synchronized void persist() {
		File file = new File("JS_BreakpointCache");
		FileOutputStream fos = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(s_cache);			
		} catch (Exception e) {
			file.delete();
		}
		finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					//DO NOTHING
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static synchronized void sync() {
		//temp solution to persist the data into current working directory.
		//it needs plug-in support to persist the data under workspace dir.
		File file = new File("JS_BreakpointCache");
		if (!file.exists()) {
			return;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			s_cache = (Map<String, Breakpoints>)ois.readObject();			
		} catch (Exception e) {
			file.delete();
		}
		finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					//DO NOTHING
				}
			}
		}
	}
	
	public static class Breakpoints implements Serializable {
		private static final long serialVersionUID = 1L;
		private final String m_url;
		private Set<Integer> m_breakLines = new HashSet<Integer>();
		
		Breakpoints(String url) {
			m_url = url;
		}
		
		String getUrl() {
			return m_url;
		}
		
		void setBreakpoint(int lineNum, boolean set) {
			if (set) {
				m_breakLines.add(lineNum);
			}
			else {
				m_breakLines.remove(lineNum);
			}
			persist();
		}
		
		void removeAllBreakpoints() {
			m_breakLines.clear();
			persist();
		}
		
		int[] getBreakpoints() {
			if (m_breakLines.isEmpty()) {
				return null;
			}
			int[] breakPoints = new int[m_breakLines.size()];
			Iterator<Integer> itr = m_breakLines.iterator();
			int i = 0;
			while (itr.hasNext()) {
				breakPoints[i++] = itr.next();
			}
			return breakPoints;
		}
	}
}
