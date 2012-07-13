/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.trace;

import org.ebayopensource.dsf.logger.LogLevel;

public class TranslateError {
	
	private String m_msgId;
	private String m_srcName;
	private long m_srcLineNo;
	private String m_msg;
	private LogLevel m_level = LogLevel.ERROR;
	
	public TranslateError(String msgId, String msg){
		this(LogLevel.ERROR, msgId, msg);
	}
	
	public TranslateError(LogLevel level, String msgId, String msg){
		m_msgId = msgId;
		m_msg = msg;
		m_level = level;
	}
	
	public TranslateError(String msgId, String srcName, long srcLineNo, String msg){
		this(LogLevel.ERROR, msgId, srcName, srcLineNo, msg);
	}
	
	public TranslateError(LogLevel level, String msgId, String srcName, long srcLineNo, String msg){
		m_msgId = msgId;
		m_srcName = srcName;
		m_srcLineNo = srcLineNo;
		m_msg = msg;
		m_level = level;
	}
	
	public String getMsgId() {
		return m_msgId;
	}
	
	public String getSrcName() {
		return m_srcName;
	}
	
	public void setSrcLineNo(long srcLineNo){
		m_srcLineNo = srcLineNo;
	}
	
	public long getSrcLineNo() {
		return m_srcLineNo;
	}
	
	public String getMsg() {
		return m_msg;
	}
	
	public void setLevel(LogLevel level){
		m_level = level;
	}
	
	public LogLevel getLevel(){
		return m_level;
	}
	
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		if (getLevel() != null){
			sb.append(getLevel().name()).append(": ");
		}
		if (getMsgId() != null){
			sb.append(getMsgId()).append(", ");
		}
		if (getMsg() != null){
			sb.append(getMsg()).append(", ");
		}
		if (getSrcName() != null){
			sb.append(getSrcName()).append(", ");
		}
		if (getSrcLineNo() > 0){
			sb.append("line ").append(String.valueOf(getSrcLineNo()));
		}
		
		return sb.toString();
	}
}
