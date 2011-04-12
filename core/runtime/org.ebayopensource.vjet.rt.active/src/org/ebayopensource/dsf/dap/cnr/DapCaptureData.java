/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.cnr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.dap.rt.DapHttpRequest;
import org.ebayopensource.dsf.dap.rt.DapHttpResponse;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;

public class DapCaptureData implements Serializable {
	
	public static final String VERSION_1_0_0 = "1.0.0";

	private static final long serialVersionUID = 1L;

	private static final List<IEventCapture> EMPTY_LIST = Collections.emptyList();
	
	private transient int m_httpCounter = 0;
	private transient Map<DapHttpRequest,Integer> m_httpCallIds = 
		new LinkedHashMap<DapHttpRequest,Integer>();
	
	private String m_version;
	private String m_userAgent;
	
	private Map<Integer,DapHttpCall> m_httpCalls = 
		new LinkedHashMap<Integer,DapHttpCall>();
	private List<ViewCapture> m_viewCaptures = 
		new ArrayList<ViewCapture>();
	
	private EventCapture m_initEventCapture;
	
	//
	// API
	//
	public String getVersion() {
		return m_version;
	}

	public DapCaptureData setVersion(String version) {
		m_version = version;
		return this;
	}

	public String getUserAgent() {
		return m_userAgent;
	}

	public DapCaptureData setUserAgent(String userAgent) {
		m_userAgent = userAgent;
		return this;
	}
	
	public void addViewCapture(final ViewCapture viewCapture){
		m_viewCaptures.add(viewCapture);
	}
	
	public List<ViewCapture> getViewCaptures(){
		return Collections.unmodifiableList(m_viewCaptures);
	}
	
	public ViewCapture getCurrentViewCapture(){
		if (m_viewCaptures.isEmpty()){
			return null;
		}
		return m_viewCaptures.get(m_viewCaptures.size()-1);
	}
	
	public Map<Integer, DapHttpCall> getHttpCallCaptures(){
		return Collections.unmodifiableMap(m_httpCalls);
	}
	
	public List<IEventCapture> getAllEventCaptures() {
		List<IEventCapture> captures = new ArrayList<IEventCapture>();
		if (m_initEventCapture != null){
			captures.add(m_initEventCapture);
		}
		for (ViewCapture vc: m_viewCaptures){
			captures.addAll(vc.getAllEventCaptures());
		}
		return captures;
	}

	public IEventCapture getCurrentEventCapture(){
		ViewCapture curViewCapture = getCurrentViewCapture();
		if (curViewCapture == null){
			return null;
		}
		List<IEventCapture> list = curViewCapture.getAllEventCaptures();
		if (!list.isEmpty()){
			return list.get(list.size()-1);
		}
		return null;
	}
	
	public void setInitEventCapture(final EventCapture initEventCapture) {
		m_initEventCapture = initEventCapture;
	}
	
	public EventCapture getInitEventCapture(){
		return m_initEventCapture;
	}

	//
	// Embedded
	//
	public static interface IEventCapture{
		public List<IActionInfo> getActions();
		public Iterator<IActionInfo> getActionsIter();
		public String getInfo();
		public void addDomChange(IDomChange data);
		public int getDomChangeSize();
		public void addDlcSend(String message);
		public void addDlcRequest(String message);
		public void addDlcResponse(String message);
		public void addHttpReq(final DapHttpRequest req);
		public void addHttpResp(final DapHttpRequest req, final DapHttpResponse resp);	
	}

	public static abstract class AbstractEventCapture implements IEventCapture {
		public Iterator<IActionInfo> getActionsIter() {
			return getActions().iterator();
		}
		
		public int getActionsSize() {
			return getActions().size();
		}
		
		public void addDlcSend(String message) {
			if (message != null) {
				getActions().add(new DlcSend(message));
			}
		}
		
		public void addDlcRequest(String message) {
			if (message != null) {
				getActions().add(new DlcRnR(message));
			}
		}
		
		public void addDlcResponse(String message) {
			List<IActionInfo> actions = getActions();
			if (actions.isEmpty()) {
				return;
			}
			IActionInfo info = actions.get(actions.size() - 1);
			if (!(info instanceof DlcRnR)) {
				throw new DsfRuntimeException("there is no matching request for the response : " + message);
			}
			((DlcRnR)info).m_response = message;
		}
	}
	
	public class ViewCapture implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String m_url;
		
		private Map<String,List<IEventCapture>> m_IEventCaptures = 
			new LinkedHashMap<String,List<IEventCapture>>();
		
		//
		// Constructor
		//
		public ViewCapture(final String url){
			m_url = url;
		}
		
		//
		// API
		//
		public String getUrl(){
			return m_url;
		}
		
		public void addEventCapture(final String captureName, final IEventCapture IEventCapture){
			List<IEventCapture> list = m_IEventCaptures.get(captureName);
			if (list == null){
				list = new ArrayList<IEventCapture>();
				m_IEventCaptures.put(captureName, list);
			}
			list.add(IEventCapture);
		}
		
		public Map<String,List<IEventCapture>> getEventCaptures(){
			return Collections.unmodifiableMap(m_IEventCaptures);
		}
		
		public List<IEventCapture> getEventCapture(String captureName){
			return m_IEventCaptures.get(captureName);
		}
		
		public List<IEventCapture> getAllEventCaptures() {
			if (getEventCaptures().isEmpty()){
				return EMPTY_LIST;
			}
			List<IEventCapture> captures = new ArrayList<IEventCapture>();
			for (List<IEventCapture> list: getEventCaptures().values()){
				captures.addAll(list);
			}
			return captures;
		}
	}
	
	public class EventCapture extends AbstractEventCapture implements Serializable, IEventCapture {

		private static final long serialVersionUID = 1L;
		
		private final long m_eventTimeInterval;
		private final DLCEvent m_event;
		private List<IActionInfo> m_actions = new ArrayList<IActionInfo>();


		//
		// Constructor
		//
		EventCapture() {
			m_event = null;
			m_eventTimeInterval = 0;
		}
		
		EventCapture(DLCEvent event, long eventTimeInterval) {
			m_event = event;
			m_eventTimeInterval = eventTimeInterval;
		}
		
		//
		// API
		//
		public DLCEvent getEvent() {
			return m_event;
		}
		
		public long getEventTimeInterval() {
			return m_eventTimeInterval;
		}
		
		public List<IActionInfo> getActions() {
			return m_actions;
		}
		
		public int getDomChangeSize() {
			int size = 0;
			for (IActionInfo action: m_actions){
				if (action instanceof IDomChange){
					size++;
				}
			}
			return size;
		}
		
		public void addDomChange(IDomChange data) {
			if (data != null) {
				m_actions.add(data);
			}
		}
		
		public void addHttpReq(final DapHttpRequest req){
			int count = ++m_httpCounter;
			Integer id = new Integer(count);
			DapHttpCall httpCall = new DapHttpCall(id, req);
			m_httpCallIds.put(req, count);
			m_httpCalls.put(count, httpCall);
			m_actions.add(new HttpReq(httpCall.getId()));
		}
		
		public void addHttpResp(final DapHttpRequest req, final DapHttpResponse resp){	
			Integer id = m_httpCallIds.get(req);
			DapHttpCall httpCall = m_httpCalls.get(id);
			if (httpCall != null){
				httpCall.setResponse(resp);
				m_actions.add(new HttpResp(httpCall.getId()));
			}
		}

		public String getInfo() {
			return "event-" + m_event;
		}

	}
	
	public class TaskCapture extends AbstractEventCapture implements Serializable, IEventCapture {

		private static final long serialVersionUID = 1L;

		private String m_msg;
		private List<IActionInfo> m_actions = new ArrayList<IActionInfo>();

		public TaskCapture(String msg) {
			m_msg = msg;
		}
	
		public List<IActionInfo> getActions() {
			return m_actions;
		}
		
		public int getDomChangeSize() {
			int size = 0;
			for (IActionInfo action: m_actions){
				if (action instanceof IDomChange){
					size++;
				}
			}
			return size;
		}
		
		public void addDomChange(IDomChange data) {
			if (data != null) {
				m_actions.add(data);
			}
		}
		
		public void addHttpReq(final DapHttpRequest req){
			int count = ++m_httpCounter;
			Integer id = new Integer(count);
			DapHttpCall httpCall = new DapHttpCall(id, req);
			m_httpCallIds.put(req, count);
			m_httpCalls.put(count, httpCall);
			m_actions.add(new HttpReq(httpCall.getId()));
		}
		
		public void addHttpResp(final DapHttpRequest req, final DapHttpResponse resp){	
			Integer id = m_httpCallIds.get(req);
			DapHttpCall httpCall = m_httpCalls.get(id);
			if (httpCall != null){
				httpCall.setResponse(resp);
				m_actions.add(new HttpResp(httpCall.getId()));
			}
		}

		@Override
		public String getInfo() {
			return m_msg;
		}

	}
	
	public static interface IActionInfo extends Serializable {}
	
	public static interface IDomChange extends IActionInfo {}
	
	public static interface IDlcMsg extends IActionInfo {}
	
	public static interface IHttpMsg extends IActionInfo {}
	
	public static class DlcSend implements IDlcMsg {
		private static final long serialVersionUID = 1L;
		private String m_message;
		
		DlcSend(String message) {
			m_message = message;
		}
		
		public String getMessage() {
			return m_message;
		}
		
		public String toString() {
			return m_message;
		}
	}
	
	public static class DlcRnR implements IDlcMsg {
		
		private static final long serialVersionUID = 1L;
		private String m_request;
		private String m_response;
		
		DlcRnR(String request) {
			m_request = request;
		}
		
		public String getRequest() {
			return m_request;
		}
		
		public String getResponse() {
			return m_response;
		}
		
		public String toString() {
			return "[" + m_request + "]-[" + m_response + "]";
		}
	}
	
	public static class HttpReq implements IHttpMsg {
		private static final long serialVersionUID = 1;
		private int m_id;
		public HttpReq(int id){
			m_id = id;
		}
		public int getId(){
			return m_id;
		}
		@Override
		public String toString(){
			return "HttpReq " + String.valueOf(m_id);
		}
	}
	
	public static class HttpResp implements IHttpMsg {
		private static final long serialVersionUID = 1;
		private int m_id;
		public HttpResp(int id){
			m_id = id;
		}
		public int getId(){
			return m_id;
		}
		@Override
		public String toString(){
			return "HttpResp " + String.valueOf(m_id);
		}
	}
	
	public static class DapHttpCall {

		private static final long serialVersionUID = 1L;
		
		private int m_id;
		private DapHttpRequest m_req;
		private DapHttpResponse m_resp;
		private long m_respTime;
		
		//
		// Constructor
		//
		public DapHttpCall(int id, final DapHttpRequest req){
			m_id = id;
			m_req = req;
			m_respTime = System.currentTimeMillis();
		}
		
		//
		// API
		//
		public void setResponse(final DapHttpResponse resp){
			m_resp = resp;
			m_respTime = System.currentTimeMillis() - m_respTime;
		}
		
		public int getId(){
			return m_id;
		}
		
		public DapHttpRequest getRequest(){
			return m_req;
		}
		
		public DapHttpResponse getResponse(){
			return m_resp;
		}
		
		public long getResponseTime(){
			return m_respTime;
		}
	}
}
