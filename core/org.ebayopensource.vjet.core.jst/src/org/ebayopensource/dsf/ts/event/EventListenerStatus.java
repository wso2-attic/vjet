/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.ts.event;

import java.util.ArrayList;
import java.util.List;
import org.ebayopensource.dsf.common.Z;


public class EventListenerStatus<D> {
	private final Code m_code;
	private final String m_msg;
	private final D m_data;
	private List<ErrorSource> m_errSrcList;
	
	static public class ErrorSource {
		private final String m_source;
		private final String m_errMsg;
		private final Throwable m_exception;
		
		public ErrorSource(String source, String error, Throwable e) {
			m_source = source;
			m_errMsg = error;
			m_exception = e;
		}
		
		public String getSource() {
			return m_source;
		}
		
		public String getErrMsg() {
			return m_errMsg;
		}
		
		public Throwable getException() {
			return m_exception;
		}
		
		public String toString() {
			Z z = new Z();
			z.format("m_source", m_source);
			z.format("m_error", m_errMsg);
			
			if (m_exception != null)
				z.format("m_exception", m_exception.getMessage());
			
			return z.toString();
		}
	}
	
	public EventListenerStatus(Code code){
		this(code,null,null);
	}
	public EventListenerStatus(Code code, String msg){
		this(code,msg,null);
	}
	public EventListenerStatus(Code code, D data){
		this(code,null,data);
	}
	public EventListenerStatus(Code code, String msg, D data){
		m_code = code;
		m_msg = msg;
		m_data = data;
	}
	
	public Code getCode() {
		return m_code;
	}

	public String getMsg() {
		return m_msg;
	}
	
	public D getData(){
		return m_data;
	}
	
	public void addErrorSource(ErrorSource source) {
		if (m_errSrcList == null) 
			m_errSrcList = new ArrayList<ErrorSource>();
		
		m_errSrcList.add(source);
	}
	
	public List<ErrorSource> getErrorSourceList() {
		return m_errSrcList;
	}
	
	public static enum Code {
		NoOp, 
		Started, 
		Successful, 
		Incomplete, 
		Failed, 
		Exception, 
		Interrupted
	}
}
