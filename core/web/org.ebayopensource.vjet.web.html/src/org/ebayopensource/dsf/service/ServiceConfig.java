/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.service;

import java.io.Serializable;

import org.ebayopensource.dsf.services.ConnectionProtocolEnum;
import org.ebayopensource.dsf.services.IRequestValidator;
import org.ebayopensource.dsf.common.Z;

public class ServiceConfig implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String m_svcType;
	private ConnectionProtocolEnum m_connectionProtocol;
	private String m_uri;
	private TransportTypeEnum m_transportType = TransportTypeEnum.XHR;
	private PayloadTypeEnum m_requestPayloadType = PayloadTypeEnum.RAW;
	private PayloadTypeEnum m_responsePayloadType = PayloadTypeEnum.RAW;	
	private IRequestValidator m_requestValidator;
	private String m_jsCallBackFunction;
	private int m_timeOut = 0;
	private boolean m_async = true;

	/**
	 * 
	 */
	public ServiceConfig() {
		super();
	}
	
	public void setServiceType(final String svcType){
		m_svcType = svcType;
	}
	
	public String getServiceType(){
		return m_svcType;
	}

	/**
	 * @return
	 */
	public ConnectionProtocolEnum getConnectionProtocol() {
		return m_connectionProtocol;
	}
	
	public void setTransportType(TransportTypeEnum transportType){
		m_transportType = transportType;
	}
	
	public TransportTypeEnum getTransportType(){
		return m_transportType;
	}

	/**
	 * @return
	 */
	public PayloadTypeEnum getRequestPayloadType() {
		return m_requestPayloadType;
	}

	/**
	 * @return
	 */
	public PayloadTypeEnum getResponsePayloadType() {
		return m_responsePayloadType;
	}

	/**
	 * @return
	 */
	public String getUri() {
		return m_uri;
	}

	/**
	 * @param aEnum
	 */
	public void setConnectionProtocol(final ConnectionProtocolEnum aEnum) {
		m_connectionProtocol = aEnum;
	}

	/**
	 * @param aEnum
	 */
	public void setRequestPayloadType(final PayloadTypeEnum aEnum) {
		m_requestPayloadType = aEnum;
	}

	/**
	 * @param aEnum
	 */
	public void setResponsePayloadType(final PayloadTypeEnum aEnum) {
		m_responsePayloadType = aEnum;
	}

	/**
	 * @param uri
	 */
	public void setUri(final String uri) {
		m_uri = uri;
	}

	public IRequestValidator getRequestValidator() {
		return m_requestValidator;
	}

	public void setRequestValidator(final IRequestValidator requestValidator) {
		m_requestValidator = requestValidator;
	}

	public String getJsCallBackFunction() {
		return m_jsCallBackFunction;
	}

	public void setJsCallBackFunction(String callback) {
		m_jsCallBackFunction = callback;
	}
	
	public boolean getAsync() {
		return m_async;
	}

	public void setAsync(boolean async) {
		m_async = async;
	}
	

	//
	// Override(s) from Object
	//
	@Override
	public String toString() {
		Z z = new Z() ;
        z.format("Connection protocol", m_connectionProtocol) ;
        z.format("URI", m_uri) ;
        z.format("Request Marshall Type", m_requestPayloadType) ;
        z.format("Response Marshall Type", m_responsePayloadType) ;
		return z.toString() ;		
	}

	public int getTimeOut() {
		return m_timeOut;
	}

	public void setTimeOut(int timeOut) {
		m_timeOut = timeOut;
	}

}
