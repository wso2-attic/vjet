/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.cnr;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.common.xml.IIndenter;
import org.ebayopensource.dsf.common.xml.XmlStreamWriter;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.DapHttpCall;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.DlcRnR;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.DlcSend;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.EventCapture;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.IActionInfo;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.IDlcMsg;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.IDomChange;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.IEventCapture;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.TaskCapture;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData.ViewCapture;
import org.ebayopensource.dsf.dap.cnr.DomChangeMessageFormater.NodeAppend;
import org.ebayopensource.dsf.dap.cnr.DomChangeMessageFormater.NodeAttrUpdate;
import org.ebayopensource.dsf.dap.cnr.DomChangeMessageFormater.NodeInsert;
import org.ebayopensource.dsf.dap.cnr.DomChangeMessageFormater.NodeRemove;
import org.ebayopensource.dsf.dap.cnr.DomChangeMessageFormater.NodeUpdate;
import org.ebayopensource.dsf.dap.cnr.DomChangeMessageFormater.NodeValueUpdate;
import org.ebayopensource.dsf.dap.rt.DapCtx;
import org.ebayopensource.dsf.dap.rt.DapHttpRequest;
import org.ebayopensource.dsf.dap.rt.DapHttpResponse;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent.KeyInfo;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent.Position;
import org.ebayopensource.dsf.liveconnect.client.simple.SimpleDLCClient;


/*
 * The XML structure could be like below:
  <DapCaptureData encoding="UTF-8" ver="1">
	<ViewCaptures>
		<ViewCapture url="test">
			<EventCaptureGroup captureName="test">
				<EventCapture interval>
					<DLCEvent type="">
						<Button/>
						<Cancelable/>
						<CancelBubble/>
						<Detail/>
						<KeyInfo>							
							<AltKey/>
							<CharCode/>
							<CtrlKey/>
							<KeyCode/>
							<KeyId/>
							<KeyLocation/>
							<MetaKey/>
							<ShiftKey/>
						</KeyInfo>
						<ModifierState/>
						<Position>
							<ClientX/>
							<ClientY/>
							<ScreenX/>
							<ScreenY/>
							<PageX/>
							<PageY/>
							<MouseX/>
							<MouseY/>
						</Position>
						<RelatedTarget/>
						<RelatedTargetId/>
						<Src id='xx' path='xx'>
							<Property name='xx'>value</Property>
						</Src>
						<TimeStamp/>
						<Value/>
						<Which/>
					</DLCEvent>
					<Actions>
						<!-- IActionInfo -->
						<!-- IDlcMsg-->
						<DlcRnR>
							<Request/>
							<Response/>
						</DlcRnR>
						<DlcSend>
							message
						</DlcSend>
						<!--IDomChange-->
						<NodeAppend type="">
							<NodeHtml/>
							<ParentPath/>
						</NodeAppend>
						<NodeAttrUpdate type="">
							<Name/>
							<Path/>
							<Value/>
						</NodeAttrUpdate>
						<NodeInsert type="">
							<InsertBefore/>
							<NodeHtml/>
							<RefPath/>
						</NodeInsert>
						<NodeRemove type="">						
							<NodeHtml/>
							<Path/>
						</NodeRemove>
						<NodeUpdate type="">		
							<NodeHtml/>
							<Path/>
						</NodeUpdate>
						<NodeValueUpdate type="">	
							<Path/>
							<Value/>
						</NodeValueUpdate>
						<!--IHttpMsg-->
						<HttpReq>
							<Id/>							
						</HttpReq>
						<HttpResp>
							<Id/>
						</HttpResp>						
					</Actions>					
				</EventCapture>
			</EventCaptureGroup>
		</ViewCapture>
	</ViewCaptures>
	<HttpCalls>
        <HttpCall id="xx">
            <Req/>
            <Resp time="xx"/>
        </HttpCall>
    </HttpCalls>
</DapCaptureData>

 *
 * For DapCaptureData->ViewCaptures->ViewCapture->EventCaptureGroup->EventCapture
 * & DapCapture->HttpCallCaptures, serializations are directly done in plain code.
 * For lower levels, serializations are impl with reflection with customized attributes processing.
 */
public class DapCaptureXmlSerializer implements IDapCaptureSerializer {

	
	private IIndenter m_indenter;
	
	//
	// Constructor
	//
	public DapCaptureXmlSerializer(){
		this(null, null);
	}

	public DapCaptureXmlSerializer(final IIndenter indenter){
		this(null, indenter);
	}

	public DapCaptureXmlSerializer(final IDLCClient dlcClient, final IIndenter indenter){
		m_indenter = indenter;
	}

	//
	// For IDapCaptureSerializer
	//
	
	/**
	 * Serialize <code>DapCaptureData</code> to output stream in XML
	 * For null obj, output empty string.
	 * @param capture DapCaptureData
	 * @param out OutputStream
	 */
	public void serialize(DapCaptureData capture, OutputStream out){
		Writer pw;
		try {
			pw = new OutputStreamWriter(out, "UTF-8");
			XmlStreamWriter writer = new XmlStreamWriter(pw, getIndenter());
			writer.writeStartDocument();
			new DapCaptureDataRootSerializer().serialize(capture, writer);
			writer.writeEndDocument();
			writer.flush();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * De-serialize XML input stream to <code>DapCaptureData</code>
	 * Use SAX parser
	 * @param in InputStream
	 * @return DapCaptureData
	 */
	public DapCaptureData deserialize(InputStream in){
		DapCaptureData data = null;
		try {
			if(in.available() == 0){
				return null;
			}
			DapCaptureDataRootHandler handler = new DapCaptureDataRootHandler();
			SAXParserFactory.newInstance().newSAXParser().parse(new InputSource(new InputStreamReader(in, "UTF-8")), handler);
			data = handler.getData();
		}catch (SAXException e) {
			throw new DsfRuntimeException(e);
		} catch (IOException e) {
			throw new DsfRuntimeException(e);
		} catch (ParserConfigurationException e) {
			throw new DsfRuntimeException(e);
		}
		return data;
	}
	
	//
	// API
	//
	public void setIndenter(final IIndenter indenter){
		m_indenter = indenter;
	}

	public IIndenter getIndenter(){
		if (m_indenter == null){
			m_indenter = IIndenter.COMPACT;
		}
		return m_indenter;
	}
	
	//
	// Name constants
	//
	
	private static final String ATTRNAME_ALT = "alt";
	private static final String ATTRNAME_ASYNC = "async";
	private static final String ATTRNAME_CTRL = "ctrl";
	private static final String ATTRNAME_ID = "id";
	private static final String ATTRNAME_INTERVAL = "interval";
	private static final String ATTRNAME_KEY = "key";
	private static final String ATTRNAME_META = "meta";
	private static final String ATTRNAME_METHOD = "method";
	private static final String ATTRNAME_MSG = "msg";
	private static final String ATTRNAME_NAME = "name";
	private static final String ATTRNAME_PATH = "path";
	private static final String ATTRNAME_SHIFT = "shift";
	private static final String ATTRNAME_TIME = "time";
	private static final String ATTRNAME_TYPE = "type";
	private static final String ATTRNAME_URL = "url";
	private static final String ATTRNAME_VALUE = "value";
	private static final String ATTRNAME_VER = "ver";
	private static final String ATTRNAME_X = "x";
	private static final String ATTRNAME_Y = "y";
	private static final String ATTR_NAME_ENCODING = "encoding";
	
	private static final String CLZNAME_DAP_CAPTURE_DATA = "DapCaptureData";
	private static final String CLZNAME_DAP_HTTP_REQUEST = "DapHttpRequest";
	private static final String CLZNAME_DAP_HTTP_RESPONSE = "DapHttpResponse";
	private static final String CLZNAME_DLC_RNR = "DlcRnR";
	private static final String CLZNAME_DLC_SEND = "DlcSend";
	private static final String CLZNAME_DLCEVENT = "DLCEvent";
	private static final String CLZNAME_EVENT_CAPTURE = "EventCapture";
	private static final String CLZNAME_HTTP_REQ = "HttpReq";
	private static final String CLZNAME_HTTP_RESP = "HttpResp";
	private static final String CLZNAME_KEY_INFO = "KeyInfo";
	private static final String CLZNAME_NODE_APPEND = "NodeAppend";
	private static final String CLZNAME_NODE_ATTR_UPDATE = "NodeAttrUpdate";
	private static final String CLZNAME_NODE_INSERT = "NodeInsert";
	private static final String CLZNAME_NODE_REMOVE = "NodeRemove";
	private static final String CLZNAME_NODE_UPDATE = "NodeUpdate";
	private static final String CLZNAME_NODE_VALUE_UPDATE = "NodeValueUpdate";
	private static final String CLZNAME_TASK_CAPTURE = "TaskCapture";
	private static final String CLZNAME_VIEW_CAPTURE = "ViewCapture";
	
	private static final String DOT = ".";
	private static final String M_ = "m_";
	private static final String ENCODING_UTF_8 = "UTF-8";
	
	private static final String FLDNAME_ACTIONS = "m_actions";
	private static final String FLDNAME_ASYNC = "m_async";
	private static final String FLDNAME_BUTTON = "m_button";
	private static final String FLDNAME_CANCEL_BUBBLE = "m_cancelBubble";
	private static final String FLDNAME_CANCELABLE = "m_cancelable";
	private static final String FLDNAME_CHAR_CODE = "m_charCode";
	private static final String FLDNAME_CLIENT_X = "m_clientX";
	private static final String FLDNAME_CLIENT_Y = "m_clientY";
	private static final String FLDNAME_DETAIL = "m_detail";
	private static final String FLDNAME_EVENT = "m_event";
	private static final String FLDNAME_EVENT_TIME_INTERVAL = "m_eventTimeInterval";
	private static final String FLDNAME_HOST = "m_host";
	private static final String FLDNAME_HTTP_CALLS = "m_httpCalls";
	private static final String FLDNAME_ID = "m_id";
	private static final String FLDNAME_IEVENT_CAPTURES = "m_IEventCaptures";
	private static final String FLDNAME_INIT_EVENT_CAPTURE = "m_initEventCapture";
	private static final String FLDNAME_INSERT_BEFORE = "m_insertBefore";
	private static final String FLDNAME_KEY_CODE = "m_keyCode";
	private static final String FLDNAME_KEY_IDENTIFIER = "m_keyIdentifier";
	private static final String FLDNAME_KEY_INFO = "m_keyInfo";
	private static final String FLDNAME_KEY_LOCATION = "m_keyLocation";
	private static final String FLDNAME_MESSAGE = "m_message";
	private static final String FLDNAME_MSG = "m_msg";
	private static final String FLDNAME_METHOD = "m_method";
	private static final String FLDNAME_MODIFIER_STATE = "m_modifierState";
	private static final String FLDNAME_MOUSE_X = "m_mouseX";
	private static final String FLDNAME_MOUSE_Y = "m_mouseY";
	private static final String FLDNAME_NAME = "m_name";
	private static final String FLDNAME_NODE_HTML = "m_nodeHtml";
	private static final String FLDNAME_PAGE_X = "m_pageX";
	private static final String FLDNAME_PAGE_Y = "m_pageY";
	private static final String FLDNAME_PARENT_PATH = "m_parentPath";
	private static final String FLDNAME_PATH = "m_path";
	private static final String FLDNAME_PAYLOAD = "m_payload";
	private static final String FLDNAME_POSITION = "m_position";
	private static final String FLDNAME_RAW_DATA = "m_rawData";
	private static final String FLDNAME_REF_PATH = "m_refPath";
	private static final String FLDNAME_RELATED_TARGET = "m_relatedTarget";
	private static final String FLDNAME_RELATED_TARGET_ID = "m_relatedTargetId";
	private static final String FLDNAME_REQUEST = "m_request";
	private static final String FLDNAME_REQUEST_HEADERS = "m_requestHeaders";
	private static final String FLDNAME_RESPONSE = "m_response";
	private static final String FLDNAME_RESPONSE_HEADERS = "m_responseHeaders";
	private static final String FLDNAME_RESPONSE_TEXT = "m_responseText";
	private static final String FLDNAME_SCREEN_X = "m_screenX";
	private static final String FLDNAME_SCREEN_Y = "m_screenY";
	private static final String FLDNAME_STATUS_CODE = "m_statusCode";
	private static final String FLDNAME_STATUS_TEXT = "m_statusText";
	private static final String FLDNAME_TIME_STAMP = "m_timeStamp";
	private static final String FLDNAME_TIMEOUT = "m_timeout";
	private static final String FLDNAME_TYPE = "m_type";
	private static final String FLDNAME_URL = "m_url";
	private static final String FLDNAME_USER_AGENT = "m_userAgent";
	private static final String FLDNAME_VALUE = "m_value";
	private static final String FLDNAME_VERSION = "m_version";
	private static final String FLDNAME_VIEW_CAPTURES = "m_viewCaptures";
	private static final String FLDNAME_WHICH = "m_which";
		
	private static final String TAGNAME_BUTTON = "Button";
	private static final String TAGNAME_CANCEL_BUBBLE = "CancelBubble";
	private static final String TAGNAME_CANCELABLE = "Cancelable";
	private static final String TAGNAME_CHAR_CODE = "CharCode";
	private static final String TAGNAME_CLIENT = "Client";
	private static final String TAGNAME_DAP_CAPTURE_DATA = "DapCaptureData";
	private static final String TAGNAME_DETAIL = "Detail";
	private static final String TAGNAME_DLC_RNR = "DlcRnR";
	private static final String TAGNAME_DLC_SEND = "DlcSend";
	private static final String TAGNAME_ENTRY = "Entry";
	private static final String TAGNAME_EVENT = "Event";
	private static final String TAGNAME_EVENT_CAPTURE = "EventCapture";
	private static final String TAGNAME_EVENT_CAPTURE_GROUP = "EventCaptureGroup";
	private static final String TAGNAME_HOST = "Host";
	private static final String TAGNAME_HTTP_CALL = "HttpCall";
	private static final String TAGNAME_HTTP_REQ = "HttpReq";
	private static final String TAGNAME_HTTP_RESP = "HttpResp";
	private static final String TAGNAME_INSERT_BEFORE = "InsertBefore";
	private static final String TAGNAME_KEY_CODE = "KeyCode";
	private static final String TAGNAME_KEY_IDENTIFIER = "KeyIdentifier";
	private static final String TAGNAME_KEY_INFO = "KeyInfo";
	private static final String TAGNAME_KEY_LOCATION = "KeyLocation";
	private static final String TAGNAME_MESSAGE = "Message";
	private static final String TAGNAME_MODIFIER_STATE = "ModifierState";
	private static final String TAGNAME_MOUSE = "Mouse";
	private static final String TAGNAME_NAME = "Name";
	private static final String TAGNAME_NODE_APPEND = "NodeAppend";
	private static final String TAGNAME_NODE_ATTR_UPDATE = "NodeAttrUpdate";
	private static final String TAGNAME_NODE_HTML = "NodeHtml";
	private static final String TAGNAME_NODE_INSERT = "NodeInsert";
	private static final String TAGNAME_NODE_REMOVE = "NodeRemove";
	private static final String TAGNAME_NODE_UPDATE = "NodeUpdate";
	private static final String TAGNAME_NODE_VALUE_UPDATE = "NodeValueUpdate";
	private static final String TAGNAME_PAGE = "Page";
	private static final String TAGNAME_PARENT_PATH = "ParentPath";
	private static final String TAGNAME_PATH = "Path";
	private static final String TAGNAME_POSITION = "Position";
	private static final String TAGNAME_PROPERTY = "Property";
	private static final String TAGNAME_RAW_DATA = "RawData";
	private static final String TAGNAME_REF_PATH = "RefPath";
	private static final String TAGNAME_RELATED_TARGET = "RelatedTarget";
	private static final String TAGNAME_RELATED_TARGET_ID = "RelatedTargetId";
	private static final String TAGNAME_REQ = "Req";
	private static final String TAGNAME_REQUEST = "Request";
	private static final String TAGNAME_REQUEST_HEADERS = "RequestHeaders";
	private static final String TAGNAME_RESP = "Resp";
	private static final String TAGNAME_RESPONSE = "Response";
	private static final String TAGNAME_RESPONSE_HEADERS = "ResponseHeaders";
	private static final String TAGNAME_RESPONSE_TEXT = "ResponseText";
	private static final String TAGNAME_SCREEN = "Screen";
	private static final String TAGNAME_SRC = "Src";
	private static final String TAGNAME_STATUS_CODE = "StatusCode";
	private static final String TAGNAME_STATUS_TEXT = "StatusText";
	private static final String TAGNAME_TASK_CAPTURE = "TaskCapture";
	private static final String TAGNAME_TIME_STAMP = "TimeStamp";
	private static final String TAGNAME_TIMEOUT = "Timeout";
	private static final String TAGNAME_USER_AGENT = "UserAgent";
	private static final String TAGNAME_VALUE = "Value";
	private static final String TAGNAME_VIEW_CAPTURE = "ViewCapture";
	private static final String TAGNAME_WHICH = "Which";
	

	//
	// Default values
	//
	
	private static final Integer DEFAULT_INT = new Integer(0);
	private static final Integer INT_MINUS_1 = new Integer(-1);
	private static final Long DEFAULT_LONG = new Long(0);
	private static final Double DEFAULT_DOUBLE = new Double(0);
	private static final Boolean DEFAULT_BOOLEAN = Boolean.FALSE;
	private static final Short DEFAULT_SHORT = new Short((short)0);
	private static final Short SHORT_MINUS_1 = new Short((short)-1);
	private static final Float DEFAULT_FLOAT = new Float(0);
	
	//
	// Utils
	//
	
	private static final String EMPTY_STR = "";
	private static final String EMPTY_STR_WRAPPER = "@#$"; // for wrapping trimable strings
	private static final String BLANK_CHAR = " ";
	private static final String BLANK_CHAR_REPLACEMENT = ".@."; // for " "
	private static final int EMPTY_STR_WRAPPER_LENGTH = EMPTY_STR_WRAPPER.length();
	private static final int EMPTY_STR_WRAPPER_LENGTH_DOUBLE = EMPTY_STR_WRAPPER_LENGTH * 2;
	
	private static String wrapEmptyString(String str) {
		String trim = str.trim();
		if(trim.length() != str.length() || trim.length() == 0){
			//If a string has leading/trailing spaces, we wrap it with our chars.
			return  EMPTY_STR_WRAPPER
				+str.replaceAll(BLANK_CHAR, BLANK_CHAR_REPLACEMENT)
				+EMPTY_STR_WRAPPER;
		}
		return str;
	}
	
	private static String unwrapEmptyString(String str){
		//DO trim() it here 
		//if the original string has leading/trailing spaces, 
		//we should have deal with that in wrapEmptyString()
		String unwrap = str.trim(); 
		
		if(unwrap.startsWith(EMPTY_STR_WRAPPER) && unwrap.endsWith(EMPTY_STR_WRAPPER)){
			int strLength = unwrap.length();
			if(strLength == EMPTY_STR_WRAPPER_LENGTH_DOUBLE){
				unwrap = EMPTY_STR;
			}else if(strLength > EMPTY_STR_WRAPPER_LENGTH_DOUBLE){
				unwrap = unwrap.substring(
						EMPTY_STR_WRAPPER_LENGTH,
						strLength - EMPTY_STR_WRAPPER_LENGTH);
				unwrap = unwrap.replaceAll(BLANK_CHAR_REPLACEMENT, BLANK_CHAR);
			}
		}
		return unwrap;
	}
	
	private static void setFieldValue(Field field, Object bean, Object value){
		if(value == null || field == null){
			return;
		}
		try {
			boolean flg = field.isAccessible();
			field.setAccessible(true);
			Class<?> fieldType = field.getType();
			String simpleValue = null;
			if(String.class.equals(value.getClass())){
				simpleValue = (String)value;//DON'T trim() it here!!
			}
			if(simpleValue != null){
				if(boolean.class.equals(fieldType)){
					field.setBoolean(bean, Boolean.parseBoolean(simpleValue));
				}else if(int.class.equals(fieldType)){
					field.setInt(bean, Integer.parseInt(simpleValue));
				}else if(short.class.equals(fieldType)){
					field.setShort(bean, Short.parseShort(simpleValue));
				}else if(float.class.equals(fieldType)){
					field.setFloat(bean, Float.parseFloat(simpleValue));
				}else if(double.class.equals(fieldType)){
					field.setDouble(bean, Double.parseDouble(simpleValue));
				}else if(long.class.equals(fieldType)){
					field.setLong(bean, Long.parseLong(simpleValue));
				}else if(String.class.equals(fieldType)){
					field.set(bean, simpleValue);
				}
			}else{
				field.set(bean, value);
			}
			field.setAccessible(flg);
		} catch (IllegalArgumentException e) {
			throw new DsfRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new DsfRuntimeException(e);
		}
	}
	
	private static Object getFieldValue(Field field, Object bean){
		Object obj = null;
		try {
			boolean flg = field.isAccessible();
			field.setAccessible(true);
			obj = field.get(bean);
			field.setAccessible(flg);
		} catch (IllegalArgumentException e) {
			throw new DsfRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new DsfRuntimeException(e);
		}
		return obj;
	}
	
	private static Field getField(String fieldName, Class<?> clz) {
		Field field = null;
		try {
			field = clz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			throw new DsfRuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new DsfRuntimeException(e);
		}
		return field;
	}

	//
	// Helper classes
	//
	
	//>>>>>> Deserializer

	public static abstract class DapCaptureDataSubHandler extends DefaultHandler{
		private ValueHolder<DapCaptureData> m_holder;
		
		private Stack<Object> m_objStack;
		
		private Map<String, DapCaptureDataSubHandler> m_registry;
		
		protected ValueHolder<DapCaptureData> getHolder(){
			return m_holder;
		}

		protected Stack<Object> getObjStack() {
			return m_objStack;
		}

		public Map<String, DapCaptureDataSubHandler> getRegistry() {
			return m_registry;
		}

		public void initHandler(
				ValueHolder<DapCaptureData> holder,
				Stack<Object> objStack,
				Map<String, DapCaptureDataSubHandler> registry) {
			m_holder = holder;
			m_objStack = objStack;
			m_registry = registry;
		}
	}
	
	public static class ValueHolder<T>{
		private T m_value;
		
		public void setValue(T value){
			m_value = value;
		}
		
		public T getValue(){
			return m_value;
		}
		
	}
	
	private static class DapCaptureDataRootHandler extends DefaultHandler{

		public DapCaptureData getData(){
			return m_holder.getValue();
		}
		
		private ValueHolder<DapCaptureData> m_holder = new ValueHolder<DapCaptureData>();
		
		private Stack<Object> m_objStack = new Stack<Object>(); //holding the parent obj for current tag
		
		private Stack<String> m_tagStack = new Stack<String>(); //Holding current tag path
		
		private Stack<Boolean> m_skipStack = new Stack<Boolean>(); //to indicate whether current tag is skipped
		
		//Key: tagName, Value:Handler for this tag
		private Map<String, DapCaptureDataSubHandler> m_handlerRegistry = 
			initHandlerRegistry(); 
		
		private Map<String, DapCaptureDataSubHandler> initHandlerRegistry(){
			Map<String, Class<? extends DapCaptureDataSubHandler>> info = 
				new HashMap<String, Class<? extends DapCaptureDataSubHandler>>();
			
			//add standard config
			initStandardHandlers(info);
			info.putAll(DapCtx.ctx().getDapConfig()
					.getDapCaptureDataDeserializerRegistry());
			
			Map<String, DapCaptureDataSubHandler> map = 
				new HashMap<String, DapCaptureDataSubHandler>();
			for (Entry<String, Class<? extends DapCaptureDataSubHandler>> entry 
					: info.entrySet()) {
				DapCaptureDataSubHandler subHandler = null;
				try {
					subHandler = entry.getValue().newInstance();
				} catch (IllegalAccessException e) {
					throw new DsfRuntimeException(e);
				} catch (InstantiationException e) {
					throw new DsfRuntimeException(e);
				}
				if(subHandler != null){
					subHandler.initHandler(m_holder, m_objStack, map);
					map.put(entry.getKey(), subHandler);
				}
			}
			
			return map;
		}

		private void initStandardHandlers(
				Map<String, Class<? extends DapCaptureDataSubHandler>> info) {
			info.put(TAGNAME_DAP_CAPTURE_DATA, DapCaptureDataHandler.class);
			info.put(TAGNAME_USER_AGENT, PrimitiveHandler.class);
			
			info.put(TAGNAME_EVENT_CAPTURE, EventCaptureHandler.class);
			info.put(TAGNAME_TASK_CAPTURE, TaskCaptureHandler.class);
			
			info.put(TAGNAME_VIEW_CAPTURE, ViewCaptureHandler.class);
			
			info.put(TAGNAME_EVENT_CAPTURE_GROUP, EventCaptureGroupHandler.class);
			
			info.put(TAGNAME_EVENT, DLCEventHandler.class);
			info.put(TAGNAME_CANCEL_BUBBLE, PrimitiveHandler.class);
			info.put(TAGNAME_CANCELABLE, PrimitiveHandler.class);
			info.put(TAGNAME_BUTTON, PrimitiveHandler.class);
			info.put(TAGNAME_RELATED_TARGET, PrimitiveHandler.class);
			info.put(TAGNAME_RELATED_TARGET_ID, PrimitiveHandler.class);
			info.put(TAGNAME_MODIFIER_STATE, PrimitiveHandler.class);
			info.put(TAGNAME_TIME_STAMP, PrimitiveHandler.class);
			info.put(TAGNAME_DETAIL, PrimitiveHandler.class);
			info.put(TAGNAME_WHICH, PrimitiveHandler.class);
			info.put(TAGNAME_SRC, DLCEventSrcHandler.class);
			info.put(TAGNAME_PROPERTY, DLCEventPropertyHandler.class);
			info.put(TAGNAME_POSITION, PositionHandler.class);
			info.put(TAGNAME_CLIENT, PositionHandler.class);
			info.put(TAGNAME_MOUSE, PositionHandler.class);
			info.put(TAGNAME_PAGE, PositionHandler.class);
			info.put(TAGNAME_SCREEN, PositionHandler.class);
			info.put(TAGNAME_KEY_INFO, KeyInfoHandler.class);
			info.put(TAGNAME_CHAR_CODE, PrimitiveHandler.class);
			info.put(TAGNAME_KEY_CODE, PrimitiveHandler.class);
			info.put(TAGNAME_KEY_LOCATION, PrimitiveHandler.class);
			info.put(TAGNAME_KEY_IDENTIFIER, PrimitiveHandler.class);
			
			//IDlcMsg
			info.put(TAGNAME_DLC_RNR, DlcMsgHandler.class);
			info.put(TAGNAME_REQUEST, PrimitiveHandler.class);
			info.put(TAGNAME_RESPONSE, PrimitiveHandler.class);
			
			info.put(TAGNAME_DLC_SEND, DlcSendHandler.class);
			
			//IDomChange
			info.put(TAGNAME_NODE_APPEND, DomChangeHandler.class);
			info.put(TAGNAME_PARENT_PATH, PrimitiveHandler.class);
			info.put(TAGNAME_NODE_HTML, PrimitiveHandler.class);
			
			info.put(TAGNAME_NODE_ATTR_UPDATE, DomChangeHandler.class);
			info.put(TAGNAME_PATH, PrimitiveHandler.class);
			info.put(TAGNAME_NAME, PrimitiveHandler.class);
			info.put(TAGNAME_VALUE, PrimitiveHandler.class);
			
			info.put(TAGNAME_NODE_INSERT, DomChangeHandler.class);
			info.put(TAGNAME_REF_PATH, PrimitiveHandler.class);
			info.put(TAGNAME_NODE_HTML, PrimitiveHandler.class);
			info.put(TAGNAME_INSERT_BEFORE, PrimitiveHandler.class);
			
			info.put(TAGNAME_NODE_REMOVE, DomChangeHandler.class);
//			info.put(TAGNAME_PATH, PrimitiveHandler.class);
//			info.put(TAGNAME_NODE_HTML, PrimitiveHandler.class);
			
			info.put(TAGNAME_NODE_UPDATE, DomChangeHandler.class);
//			info.put(TAGNAME_PATH, PrimitiveHandler.class);
//			info.put(TAGNAME_NODE_HTML, PrimitiveHandler.class);
			
			info.put(TAGNAME_NODE_VALUE_UPDATE, DomChangeHandler.class);
//			info.put(TAGNAME_PATH, PrimitiveHandler.class);
//			info.put(TAGNAME_VALUE, PrimitiveHandler.class);

			info.put(TAGNAME_HTTP_REQ, HttpCallsHandlerWrapper.class);
			info.put(TAGNAME_HTTP_RESP, HttpCallsHandlerWrapper.class);
			info.put(TAGNAME_HTTP_CALL, HttpCallsHandlerWrapper.class);
			info.put(TAGNAME_REQ, HttpCallsHandlerWrapper.class);
			info.put(TAGNAME_HOST, PrimitiveHandler.class);
			info.put(TAGNAME_TIMEOUT, PrimitiveHandler.class);
			info.put(TAGNAME_RAW_DATA, PrimitiveHandler.class);
			info.put(TAGNAME_REQUEST_HEADERS, HttpCallsHandlerWrapper.class);
			info.put(TAGNAME_RESP, HttpCallsHandlerWrapper.class);
			info.put(TAGNAME_STATUS_CODE, PrimitiveHandler.class);
			info.put(TAGNAME_STATUS_TEXT, PrimitiveHandler.class);
			info.put(TAGNAME_RESPONSE_TEXT, PrimitiveHandler.class);
			info.put(TAGNAME_RAW_DATA, PrimitiveHandler.class);
			info.put(TAGNAME_RESPONSE_HEADERS, HttpCallsHandlerWrapper.class);
		}
	
		//
		// Override DefaultHandler
		//
		
		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			m_tagStack.push(name);
			DefaultHandler handler = m_handlerRegistry.get(name);
			if(handler == null){
				m_skipStack.push(Boolean.TRUE); //Just skip it if no handlers found
			}else{
				m_skipStack.push(Boolean.FALSE);
				handler.startElement(uri, localName, name, attributes);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if(m_skipStack.peek()){
				return;
			}
			DefaultHandler handler = m_handlerRegistry.get(m_tagStack.peek());
			if(handler != null){
				handler.characters(ch, start, length);
			}else{
				//Exception!
				//should have been skipped in startElement()
				throw new DsfRuntimeException(
						"Handler is null for '"+m_tagStack.peek()+"''s characters()");
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			String tagName = m_tagStack.pop();
			if(m_skipStack.pop()){
				return;
			}
			if(!tagName.equals(name)){
				throw new DsfRuntimeException(
						"Tag name mismatched. Expecting '"+tagName+"' but got '"+name+"'");
			}
			DefaultHandler handler = m_handlerRegistry.get(name);
			if(handler != null){
				handler.endElement(uri, localName, name);
			}else{
				//Exception!
				//should have been skipped in startElement()
				throw new DsfRuntimeException(
						"Handler is null for '"+name+"''s endElement()"); 
			}
		}
	}
	
	public static class DapCaptureDataHandler extends DapCaptureDataSubHandler{


		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			DapCaptureData data = new DapCaptureData();
			data.setVersion(attributes.getValue(ATTRNAME_VER));
			getHolder().setValue(data);
			getObjStack().push(data);
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			getObjStack().pop();
		}
	}

	public static class PrimitiveHandler extends DapCaptureDataSubHandler {
		
		private boolean m_newChar;
		
		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			getObjStack().push(new ValueHolder<String>());
			m_newChar = true;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String newChars = new String(ch, start, length);
			//DON'T check empty strings here, 
			//we will deal with that in wrapEmptyString()/unwrapEmptyString()
//			if(EMPTY_STR.equals(newChars.trim())){
//				return;
//			}
			ValueHolder<String> valueHolder = 
				(ValueHolder<String>)getObjStack().peek();
			if(m_newChar){
				valueHolder.setValue(newChars);
				m_newChar = false;
			}else{
				String chars = valueHolder.getValue();
				chars += newChars;
				valueHolder.setValue(chars);
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			ValueHolder<String> valueHolder = 
				(ValueHolder<String>)getObjStack().pop(); 
			String value = unwrapEmptyString(valueHolder.getValue());
			Object parentObj = getObjStack().peek();
			if(parentObj instanceof Collection){
				Collection<? super Object> c = 
					(Collection<? super Object>)parentObj;
				c.add(value);
			}else{
				setFieldValue(
						getField(getFldName(name), parentObj.getClass()), 
						parentObj, 
						value);
			}
		}

		protected String getFldName(String tagName){
			//Currently in the whole tree, fields are named with "m_" prefix.
			String fldName = tagName;
			if(tagName != null){
				char first = fldName.charAt(0);
				if(Character.isUpperCase(first)){
					fldName = fldName.replaceFirst(
							first+EMPTY_STR, 
							Character.toLowerCase(first)+EMPTY_STR);
				}
				fldName = M_ + fldName;
			}
			return fldName;
		}
	}
	
	public static class EventCaptureHandler extends DapCaptureDataSubHandler{


		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			DapCaptureData data = getHolder().getValue();
			String intStr = attributes.getValue(ATTRNAME_INTERVAL);
			int intVal = 0;
			if(intStr != null){
				intVal = Integer.parseInt(intStr);
			}
			EventCapture ec = data.new EventCapture(null, intVal);
			getObjStack().push(ec);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			EventCapture ec = (EventCapture)getObjStack().pop();
			Object parent = getObjStack().peek();
			if(parent instanceof String){
				String grpName = (String)parent;
				getObjStack().pop();//name
				ViewCapture vc = (ViewCapture)getObjStack().peek();//ViewCapture
				getObjStack().push(grpName);
				vc.addEventCapture(grpName, ec);
			}else if(parent instanceof DapCaptureData){
				DapCaptureData data = (DapCaptureData)parent;
				data.setInitEventCapture(ec);
			}
		}
	}

	public static class TaskCaptureHandler extends DapCaptureDataSubHandler{
		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			DapCaptureData data = getHolder().getValue();
			String msgStr = attributes.getValue(ATTRNAME_MSG);
			TaskCapture tc = data.new TaskCapture(msgStr);
			getObjStack().push(tc);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			TaskCapture tc = (TaskCapture)getObjStack().pop();
			Object parent = getObjStack().peek();
			if(parent instanceof String){
				String grpName = (String)parent;
				getObjStack().pop();//name
				ViewCapture vc = (ViewCapture)getObjStack().peek();//ViewCapture
				getObjStack().push(grpName);
				vc.addEventCapture(grpName, tc);
			}
		}
	}

	public static class ViewCaptureHandler extends DapCaptureDataSubHandler{

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			DapCaptureData data = getHolder().getValue();
			ViewCapture vc = data.new ViewCapture(attributes.getValue(ATTRNAME_URL));
			data.addViewCapture(vc);
			getObjStack().push(vc);
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			getObjStack().pop();
		}
	}
	
	public static class EventCaptureGroupHandler extends DapCaptureDataSubHandler{

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			String key = attributes.getValue(ATTRNAME_NAME);
			getObjStack().push(key);
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			getObjStack().pop();
		}
	}
	
	public static class DLCEventHandler extends DapCaptureDataSubHandler{

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			DLCEvent e = new DLCEvent(EMPTY_STR); //payload will be built in 'endElement()'
			e.setType(attributes.getValue(ATTRNAME_TYPE));
			getObjStack().push(e);
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			DLCEvent e = (DLCEvent)getObjStack().pop();
			String payload = SimpleDLCClient.getInstance().getPayload(e);
			setFieldValue(getField(FLDNAME_PAYLOAD, DLCEvent.class), e, payload);
			
			EventCapture ec = (EventCapture)getObjStack().peek();
			setFieldValue(getField(FLDNAME_EVENT, EventCapture.class), ec, e);
		}
	}
	
	public static class DLCEventSrcHandler extends DapCaptureDataSubHandler{


		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			String id = attributes.getValue(ATTRNAME_ID);
			String path = attributes.getValue(ATTRNAME_PATH);
			DLCEvent e = (DLCEvent)getObjStack().peek();
			if(path != null){
				e.setSrcPath(path);
			}else if(id != null){
				e.setSrcId(id);
				e.setSrcPath("document.getElementById(\""+id+"\")");
			}
		}
	}
	
	public static class DLCEventPropertyHandler extends PrimitiveHandler{

		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			DLCEvent e = (DLCEvent)getObjStack().peek();
			e.setSrcProp(attributes.getValue(ATTRNAME_NAME));
			super.startElement(uri, localName, name, attributes);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			ValueHolder<String> valueHolder = (ValueHolder<String>)getObjStack().pop(); 
			String value = unwrapEmptyString(valueHolder.getValue());
			DLCEvent e = (DLCEvent)getObjStack().peek();
			e.setValue(value);
		}
	}
	
	public static class PositionHandler extends DapCaptureDataSubHandler{


		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			if(TAGNAME_POSITION.equals(name)){
				Position pos = new Position();
				DLCEvent e = (DLCEvent)getObjStack().peek();
				e.setPosition(pos);
				getObjStack().push(pos);
			}else if(TAGNAME_CLIENT.equals(name)){
				parsePositionXY(attributes.getValue(ATTRNAME_X), FLDNAME_CLIENT_X);
				parsePositionXY(attributes.getValue(ATTRNAME_Y), FLDNAME_CLIENT_Y);
			}else if(TAGNAME_MOUSE.equals(name)){
				parsePositionXY(attributes.getValue(ATTRNAME_X), FLDNAME_MOUSE_X);
				parsePositionXY(attributes.getValue(ATTRNAME_Y), FLDNAME_MOUSE_Y);
			}else if(TAGNAME_PAGE.equals(name)){
				parsePositionXY(attributes.getValue(ATTRNAME_X), FLDNAME_PAGE_X);
				parsePositionXY(attributes.getValue(ATTRNAME_Y), FLDNAME_PAGE_Y);
			}else if(TAGNAME_SCREEN.equals(name)){
				parsePositionXY(attributes.getValue(ATTRNAME_X), FLDNAME_SCREEN_X);
				parsePositionXY(attributes.getValue(ATTRNAME_Y), FLDNAME_SCREEN_Y);
			}
		}
		
		private void parsePositionXY(String y, String fldName) {
			if(y != null && !EMPTY_STR.equals(y)){
				setFieldValue(
						getField(fldName, Position.class), 
						getObjStack().peek(), 
						Integer.parseInt(y));
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			if(TAGNAME_POSITION.equals(name)){
				getObjStack().pop();
			}
		}
	}
	
	public static class KeyInfoHandler extends DapCaptureDataSubHandler{


		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			KeyInfo ki = new KeyInfo();
			DLCEvent e = (DLCEvent)getObjStack().peek();
			e.setKeyInfo(ki);
			if(attributes.getValue(ATTRNAME_ALT) != null){
				ki.setAltKey(true);
			}
			if(attributes.getValue(ATTRNAME_CTRL) != null){
				ki.setCtrlKey(true);
			}
			if(attributes.getValue(ATTRNAME_META) != null){
				ki.setMetaKey(true);
			}
			if(attributes.getValue(ATTRNAME_SHIFT) != null){
				ki.setShiftKey(true);
			}
			getObjStack().push(ki);
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			getObjStack().pop();
		}
	}
	
	public static abstract class ActionInfoHandler extends DapCaptureDataSubHandler{
		@SuppressWarnings("unchecked")
		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			IActionInfo action = constructAction(name);
			if(action == null){
				return;
			}
			getObjStack().push(action);
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			IActionInfo action = (IActionInfo)getObjStack().pop();
			IEventCapture ec = (IEventCapture)getObjStack().peek();
			addToEc(ec, action);
		}

		protected abstract void addToEc(IEventCapture ec, IActionInfo action);

		protected abstract IActionInfo constructAction(String name);
	}
	
	public static class DomChangeHandler extends ActionInfoHandler{


		@Override
		protected void addToEc(IEventCapture ec, IActionInfo action) {
			ec.addDomChange((IDomChange)action);			
		}

		@Override
		protected IActionInfo constructAction(String name) {
			IDomChange domChg = null;
			if(CLZNAME_NODE_APPEND.equals(name)){
				domChg = new NodeAppend();
			}else if(CLZNAME_NODE_APPEND.equals(name)){
				domChg = new NodeAppend();
			}else if(CLZNAME_NODE_ATTR_UPDATE.equals(name)){
				domChg = new NodeAttrUpdate();
			}else if(CLZNAME_NODE_INSERT.equals(name)){
				domChg = new NodeInsert();
			}else if(CLZNAME_NODE_REMOVE.equals(name)){
				domChg = new NodeRemove();
			}else if(CLZNAME_NODE_UPDATE.equals(name)){
				domChg = new NodeUpdate();
			}else if(CLZNAME_NODE_VALUE_UPDATE.equals(name)){
				domChg = new NodeValueUpdate();
			}
			return domChg;
		}
	}
	
	public static class DlcMsgHandler extends ActionInfoHandler{


		@Override
		protected void addToEc(IEventCapture ec, IActionInfo action) {
			if(action instanceof DlcRnR){
				DlcRnR rnr = (DlcRnR)action;
				ec.addDlcRequest(rnr.getRequest());
				ec.addDlcResponse(rnr.getResponse());
			}else if(action instanceof DlcSend){
				DlcSend send = (DlcSend)action;
				ec.addDlcSend(send.getMessage());
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		protected IActionInfo constructAction(String name) {
			IDlcMsg msg = null;
			if(CLZNAME_DLC_RNR.equals(name)){
				msg = new DlcRnR(EMPTY_STR);
			}else if(CLZNAME_DLC_SEND.equals(name)){
				msg = new DlcSend(EMPTY_STR);
			}
			return msg;
		}
		
	}
	
	public static class DlcSendHandler extends PrimitiveHandler{
		@SuppressWarnings("unchecked")
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			ValueHolder<String> valueHolder = 
				(ValueHolder<String>)getObjStack().pop(); 
			String value = unwrapEmptyString(valueHolder.getValue());
			IEventCapture ec = (IEventCapture)getObjStack().peek();
			ec.addDlcSend(value);
		}
	}
	
	public static class HttpCallsHandlerWrapper extends DapCaptureDataSubHandler{
		private static ThreadLocal<HttpCallsHandler> m_handler = 
			new ThreadLocal<HttpCallsHandler>();
		
		private HttpCallsHandler getHandler(){
			if(m_handler.get() == null){
				m_handler.set(new HttpCallsHandler());
			}
			HttpCallsHandler handler = m_handler.get();
			
			//keep this line here to avoid ThreadLocal causing mistake.
			handler.initHandler(getHolder(), getObjStack(), getRegistry());
			
			return handler;
		}
		
		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			getHandler().startElement(uri, localName, name, attributes);
		}
		
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			getHandler().endElement(uri, localName, name);
		}
	}
		
	public static class HttpCallsHandler extends DapCaptureDataSubHandler{

		private Map<String, EventCapture> m_reqId2EC = 
			new HashMap<String, EventCapture>();
		
		private Map<String, EventCapture> m_respId2EC = 
			new HashMap<String, EventCapture>();
		
		private Map<String, DapHttpRequest> m_reqId2Req = 
			new HashMap<String, DapHttpRequest>();
		
		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			if(TAGNAME_HTTP_REQ.equals(name)){
				EventCapture ec = (EventCapture)getObjStack().peek();
				String id = attributes.getValue(ATTRNAME_ID);
				m_reqId2EC.put(id, ec);
			}else if(TAGNAME_HTTP_RESP.equals(name)){
				EventCapture ec = (EventCapture)getObjStack().peek();
				String id = attributes.getValue(ATTRNAME_ID);
				m_respId2EC.put(id, ec);
			}else if(TAGNAME_HTTP_CALL.equals(name)){
				String id = attributes.getValue(ATTRNAME_ID);
				if(id == null){
					throw new DsfRuntimeException("HttpCall's id could never be null!");
				}
				getObjStack().push(id);
			}else if(TAGNAME_REQ.equals(name)){
				String url = attributes.getValue(ATTRNAME_URL);
				String method = attributes.getValue(ATTRNAME_METHOD);
				boolean isAsync = (attributes.getValue(ATTRNAME_ASYNC) != null);
				DapHttpRequest req = new DapHttpRequest(method, url, isAsync);
				getObjStack().push(req);
			}else if(TAGNAME_RESP.equals(name)){
				getObjStack().push(attributes.getValue(ATTRNAME_TIME));
				DapHttpResponse resp = new DapHttpResponse();
				getObjStack().push(resp);				
			}else if(TAGNAME_REQUEST_HEADERS.equals(name)){
				HttpHeaderEntryHandler handler = new HttpHeaderEntryHandler();
				handler.initHandler(getHolder(), getObjStack(), getRegistry());
				getRegistry().put(TAGNAME_ENTRY, handler);
			}else if(TAGNAME_RESPONSE_HEADERS.equals(name)){
				HttpHeaderEntryHandler handler = new HttpHeaderEntryHandler();
				handler.initHandler(getHolder(), getObjStack(), getRegistry());
				getRegistry().put(TAGNAME_ENTRY, handler);
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void endElement(String uri, String localName, String name)
				throws SAXException {
			if(TAGNAME_HTTP_CALL.equals(name)){
				getObjStack().pop();
			}else if(TAGNAME_REQ.equals(name)){
				DapHttpRequest req = (DapHttpRequest)getObjStack().pop();
				String id = (String)getObjStack().peek();
				EventCapture ec = m_reqId2EC.get(id);
				ec.addHttpReq(req);
				m_reqId2Req.put(id, req);
			}else if(TAGNAME_RESP.equals(name)){
				DapHttpResponse resp = (DapHttpResponse)getObjStack().pop();
				String timeStr = (String)getObjStack().pop();
				String id = (String)getObjStack().peek();
				EventCapture ec = m_respId2EC.get(id);
				DapHttpRequest req = m_reqId2Req.get(id);
				ec.addHttpResp(req, resp);
				//Update resp time!!!
				Map<Integer,DapHttpCall> httpCalls = 
					(Map<Integer, DapHttpCall>)getFieldValue(
						getField("m_httpCalls", DapCaptureData.class), 
						getHolder().getValue());
				DapHttpCall httpCall = httpCalls.get(new Integer(id));
				if(httpCall == null){
					throw new DsfRuntimeException("Can't not found HttpCall with ID: '"+id+"'");
				}
				setFieldValue(
						getField("m_respTime", DapHttpCall.class), 
						httpCall, 
						Long.parseLong(timeStr));
			}else if(TAGNAME_REQUEST_HEADERS.equals(name)){
				getRegistry().remove(TAGNAME_ENTRY);
			}else if(TAGNAME_RESPONSE_HEADERS.equals(name)){
				getRegistry().remove(TAGNAME_ENTRY);
			}
		}
	}
	
	public static abstract class MapEntryHandler extends DapCaptureDataSubHandler{


		@Override
		public void startElement(String uri, String localName, String name,
				Attributes attributes) throws SAXException {
			String key = attributes.getValue(ATTRNAME_KEY);
			String value = attributes.getValue(ATTRNAME_VALUE);
			onEntry(key, value);
		}
		
		protected abstract void onEntry(String key, String value);
	}
	
	public static class HttpHeaderEntryHandler extends MapEntryHandler{

		@Override
		protected void onEntry(String key, String value) {
			Object obj = getObjStack().peek();
			if(obj instanceof DapHttpRequest){
				DapHttpRequest req = (DapHttpRequest)obj;
				req.setRequestHeader(key, value);
			}else if(obj instanceof DapHttpResponse){
				DapHttpResponse resp = (DapHttpResponse)obj;
				if(resp.getResponseHeaders() == null){
					resp.setResponseHeaders(new HashMap<String, String>());
				}
				resp.getResponseHeaders().put(key, value);
			}
		}
		
	}
	
	//>>>>>> Serializer
	
	public static abstract class DapCaptureDataSubSerializer{

		private Map<String, DapCaptureDataSubSerializer> m_serializerRegistry;
		
		public void setRegistry(Map<String, DapCaptureDataSubSerializer> registry){
			m_serializerRegistry = registry;
		}
		
		protected DapCaptureDataSubSerializer getSubSerializer(String path){
			return m_serializerRegistry.get(path);
		}
		
		public abstract void serialize(
				String currentPath, 
				Object obj, 
				XmlStreamWriter writer, 
				String tagName);

	}
	
	public static class ObjectSubSerializer extends DapCaptureDataSubSerializer{
		

		//tag name for current object
		protected String getDefaultTagName(Object obj){
			return obj.getClass().getSimpleName();
		}
		
		protected String[] getAttrFields(){
			return null;
		}
		
		protected String[] getNodeFields(){
			return null;
		}
		
		protected boolean isDefaultValue(String fldName, Object fldValue){
			boolean isDefault = false;
			if(fldValue == null){
				isDefault = true;
			}else{
				Class<?> fldClz = fldValue.getClass();
				if((String.class.isAssignableFrom(fldClz) && (fldValue == null))
					||(Integer.class.isAssignableFrom(fldClz) && DEFAULT_INT.equals(fldValue))
					||(Long.class.isAssignableFrom(fldClz) && DEFAULT_LONG.equals(fldValue))
					||(Double.class.isAssignableFrom(fldClz) && DEFAULT_DOUBLE.equals(fldValue))
					||(Boolean.class.isAssignableFrom(fldClz) && DEFAULT_BOOLEAN.equals(fldValue))
					||(Short.class.isAssignableFrom(fldClz) && DEFAULT_SHORT.equals(fldValue))
					||(Float.class.isAssignableFrom(fldClz) && DEFAULT_FLOAT.equals(fldValue))
					||(short.class.equals(fldClz) && Short.parseShort(fldValue.toString()) == (short)0)
					||(int.class.equals(fldClz) && Integer.parseInt(fldValue.toString()) == (int)0)
					||(float.class.equals(fldClz) && Float.parseFloat(fldValue.toString()) == (float)0)
					||(double.class.equals(fldClz) && Double.parseDouble(fldValue.toString()) == (double)0)
					||(boolean.class.equals(fldClz) && Boolean.parseBoolean(fldValue.toString()) == false)
					||(long.class.equals(fldClz) && Long.parseLong(fldValue.toString()) == 0L)
					){
					isDefault = true;
				}
			}
			return isDefault;
		}
		
		protected String getDisplayName(String fldName, boolean capitalizeFirst){
			String dsplName = fldName;
			if(dsplName != null && dsplName.startsWith(M_)){
				dsplName = fldName.substring(2);
				if(capitalizeFirst){
					char first = dsplName.charAt(0);
					if(Character.isLowerCase(first)){
						dsplName = dsplName.replaceFirst(
								first+EMPTY_STR, 
								Character.toUpperCase(first)+EMPTY_STR);
					}
				}
			}
			return dsplName;
		}
		
		public void serialize(
				String currentPath, 
				Object obj, 
				XmlStreamWriter writer, 
				String tagName){
			if(obj == null){
				return;
			}
			String dsplName = tagName;
			if(dsplName == null){
				dsplName = getDefaultTagName(obj);
			}
			Class<?> ctnClz = obj.getClass();
			String ctnClzName = ctnClz.getSimpleName();
			writer.writeStartElement(dsplName);
			serializeAttrs(obj, writer, ctnClz);
			serializeNodes(obj, writer, ctnClz, ctnClzName);
			writer.writeEndElement();
		}

		protected void serializeNodes(
				Object obj, 
				XmlStreamWriter writer,
				Class<?> ctnClz, 
				String ctnClzName) {
			String[] nodeFlds = getNodeFields();
			if(nodeFlds != null){
				for (String nodeFld : nodeFlds) {
					Object value = getFieldValue(getField(nodeFld, ctnClz), obj);
					if(!isDefaultValue(nodeFld, value)){
						String path = ctnClzName + DOT + nodeFld;
						DapCaptureDataSubSerializer subSerializer = getSubSerializer(path);
						if(subSerializer != null){
							subSerializer.serialize(
									path, 
									value, 
									writer, 
									getDisplayName(nodeFld, true));
						}
					}
				}
			}
		}

		protected void serializeAttrs(Object obj, XmlStreamWriter writer,
				Class<?> ctnClz) {
			String[] attrFlds = getAttrFields();
			if(attrFlds != null){
				for (String attrFld : attrFlds) {
					Object value = getFieldValue(
							getField(attrFld, ctnClz), 
							obj);
					if(!isDefaultValue(attrFld, value)){
						writer.writeAttribute(
								getDisplayName(attrFld, false), 
								value.toString());
					}
				}
			}
		}
	}
	
	public static class CollectionSubSerializer extends DapCaptureDataSubSerializer {

		@Override
		public void serialize(String currentPath, Object obj,
				XmlStreamWriter writer, String tagName) {
			if(tagName == null){
				throw new DsfRuntimeException(
						"CollectionSubSerializer needs tagName input!");
			}
			if(obj == null){
				return;
			}
			if(!(obj instanceof Collection)){
				return;
			}
			Collection<?> c = (Collection<?>)obj;
			if(c.size() == 0){
				return;
			}
			writer.writeStartElement(tagName);
			for (Object elm : c) {
				String clzName = elm.getClass().getSimpleName();
				DapCaptureDataSubSerializer subSerializer = getSubSerializer(clzName);
				if(subSerializer != null){
					subSerializer.serialize(clzName, elm, writer, clzName);
				}
			}
			writer.writeEndElement();
		}
		
	}
	
	public static abstract class MapSubSerializer extends DapCaptureDataSubSerializer{

		@Override
		public void serialize(String currentPath, Object obj,
				XmlStreamWriter writer, String tagName) {
			if(tagName == null){
				throw new DsfRuntimeException(
						"MapSubSerializer needs tagName input!");
			}
			if(obj == null){
				return;
			}
			if(!(obj instanceof Map)){
				return;
			}
			Map<?, ?> map = (Map<?, ?>)obj;
			if(map.size() == 0){
				return;
			}
			writer.writeStartElement(tagName);
			for (Entry<?, ?> entry : map.entrySet()) {
				serializeEntry(entry, writer);
			}
			writer.writeEndElement();
		}

		protected abstract void serializeEntry(Entry<?, ?> entry, XmlStreamWriter writer);
		
	}
	
	private static class DapCaptureDataRootSerializer{

		private Map<String, DapCaptureDataSubSerializer> m_serializerRegistry = 
			initSerializerRegistry();

		private Map<String, DapCaptureDataSubSerializer> initSerializerRegistry() {
			Map<String, Class<? extends DapCaptureDataSubSerializer>> info = 
				new HashMap<String, Class<? extends DapCaptureDataSubSerializer>>();
			
			//add standard config
			initStandardSubSerializer(info);
			info.putAll(DapCtx.ctx().getDapConfig().getDapCaptureDataSerializerRegistry());
			
			Map<String, DapCaptureDataSubSerializer> map = 
				new HashMap<String, DapCaptureDataSubSerializer>();
			
			for (Entry<String, Class<? extends DapCaptureDataSubSerializer>> entry 
					: info.entrySet()) {
				DapCaptureDataSubSerializer subSerializer = null;
				try {
					subSerializer = entry.getValue().newInstance();
				} catch (IllegalAccessException e) {
					throw new DsfRuntimeException(e);
				} catch (InstantiationException e) {
					throw new DsfRuntimeException(e);
				}
				if(subSerializer != null){
					subSerializer.setRegistry(map);
					map.put(entry.getKey(), subSerializer);
				}
			}
			return map;
		}

		private void initStandardSubSerializer(
				Map<String, 
				Class<? extends DapCaptureDataSubSerializer>> info) {
			
			info.put(CLZNAME_DAP_CAPTURE_DATA, 
					DapCaptureDataSerializer.class);
			info.put(CLZNAME_DAP_CAPTURE_DATA + DOT + FLDNAME_USER_AGENT, 
					CharactersSerializer.class);
			info.put(CLZNAME_DAP_CAPTURE_DATA + DOT + FLDNAME_INIT_EVENT_CAPTURE, 
					EventCaptureSerializer.class);
			info.put(CLZNAME_DAP_CAPTURE_DATA + DOT + FLDNAME_VIEW_CAPTURES, 
					CollectionSubSerializer.class);
			info.put(CLZNAME_DAP_CAPTURE_DATA + DOT + FLDNAME_HTTP_CALLS, 
					HttpCallsSerializer.class);
			
			info.put(CLZNAME_DAP_HTTP_REQUEST + DOT + FLDNAME_HOST, 
					CharactersSerializer.class);
			info.put(CLZNAME_DAP_HTTP_REQUEST + DOT + FLDNAME_RAW_DATA, 
					CDataSerializer.class);
			info.put(CLZNAME_DAP_HTTP_REQUEST + DOT + FLDNAME_TIMEOUT, 
					CharactersSerializer.class);
			info.put(CLZNAME_DAP_HTTP_REQUEST + DOT + FLDNAME_REQUEST_HEADERS, 
					HttpHeaderSerializer.class);
			
			info.put(CLZNAME_DAP_HTTP_RESPONSE + DOT + FLDNAME_RAW_DATA, 
					CDataSerializer.class);
			info.put(CLZNAME_DAP_HTTP_RESPONSE + DOT + FLDNAME_RESPONSE_TEXT, 
					CDataSerializer.class);
			info.put(CLZNAME_DAP_HTTP_RESPONSE + DOT + FLDNAME_STATUS_CODE, 
					CharactersSerializer.class);
			info.put(CLZNAME_DAP_HTTP_RESPONSE + DOT + FLDNAME_STATUS_TEXT, 
					CharactersSerializer.class);
			info.put(CLZNAME_DAP_HTTP_RESPONSE + DOT + FLDNAME_RESPONSE_HEADERS, 
					HttpHeaderSerializer.class);

			info.put(CLZNAME_TASK_CAPTURE, 
					TaskCaptureSerializer.class);
			info.put(CLZNAME_TASK_CAPTURE + DOT + FLDNAME_ACTIONS, 
					CollectionSubSerializer.class);
			
			info.put(CLZNAME_EVENT_CAPTURE, 
					EventCaptureSerializer.class);
			info.put(CLZNAME_EVENT_CAPTURE + DOT + FLDNAME_ACTIONS, 
					CollectionSubSerializer.class);
			info.put(CLZNAME_EVENT_CAPTURE + DOT + FLDNAME_EVENT, 
					DLCEventSerializer.class);
			
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_POSITION, 
					PositionSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_CANCEL_BUBBLE, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_CANCELABLE, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_BUTTON, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_RELATED_TARGET, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_RELATED_TARGET_ID, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_MODIFIER_STATE, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_TIME_STAMP, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_DETAIL, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_WHICH, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLCEVENT + DOT + FLDNAME_KEY_INFO, 
					KeyInfoSerializer.class);
			info.put(CLZNAME_KEY_INFO + DOT + FLDNAME_CHAR_CODE, 
					CharactersSerializer.class);
			info.put(CLZNAME_KEY_INFO + DOT + FLDNAME_KEY_CODE, 
					CharactersSerializer.class);
			info.put(CLZNAME_KEY_INFO + DOT + FLDNAME_KEY_LOCATION, 
					CharactersSerializer.class);
			info.put(CLZNAME_KEY_INFO + DOT + FLDNAME_KEY_IDENTIFIER, 
					CharactersSerializer.class);
			
			info.put(CLZNAME_VIEW_CAPTURE, 
					ViewCaptureSerializer.class);
			info.put(CLZNAME_VIEW_CAPTURE + DOT + FLDNAME_IEVENT_CAPTURES, 
					EventCaptureGroupSerializer.class);
						
			//IDlcMsg
			info.put(CLZNAME_DLC_RNR, 
					DlcRnRSerializer.class);
			info.put(CLZNAME_DLC_RNR + DOT + FLDNAME_REQUEST, 
					CharactersSerializer.class);
			info.put(CLZNAME_DLC_RNR + DOT + FLDNAME_RESPONSE, 
					CharactersSerializer.class);
			
			info.put(CLZNAME_DLC_SEND, 
					DlcSendSerializer.class);
			info.put(CLZNAME_DLC_SEND + DOT + FLDNAME_MESSAGE, 
					RawCDataSerializer.class);
			
			//IDomChange
			info.put(CLZNAME_NODE_APPEND, 
					NodeAppendSerializer.class);
			info.put(CLZNAME_NODE_APPEND + DOT + FLDNAME_PARENT_PATH, 
					CharactersSerializer.class);
			info.put(CLZNAME_NODE_APPEND + DOT + FLDNAME_NODE_HTML, 
					CDataSerializer.class);
			
			info.put(CLZNAME_NODE_ATTR_UPDATE, 
					NodeAttrUpdateSerializer.class);
			info.put(CLZNAME_NODE_ATTR_UPDATE + DOT + FLDNAME_PATH, 
					CharactersSerializer.class);
			info.put(CLZNAME_NODE_ATTR_UPDATE + DOT + FLDNAME_NAME, 
					CharactersSerializer.class);
			info.put(CLZNAME_NODE_ATTR_UPDATE + DOT + FLDNAME_VALUE, 
					CharactersSerializer.class);
			
			info.put(CLZNAME_NODE_INSERT, 
					NodeInsertSerializer.class);
			info.put(CLZNAME_NODE_INSERT + DOT + FLDNAME_REF_PATH, 
					CharactersSerializer.class);
			info.put(CLZNAME_NODE_INSERT + DOT + FLDNAME_NODE_HTML, 
					CDataSerializer.class);
			info.put(CLZNAME_NODE_INSERT + DOT + FLDNAME_INSERT_BEFORE, 
					CharactersSerializer.class);
			
			info.put(CLZNAME_NODE_REMOVE, 
					NodeRemoveSerializer.class);
			info.put(CLZNAME_NODE_REMOVE + DOT + FLDNAME_PATH, 
					CharactersSerializer.class);
			info.put(CLZNAME_NODE_REMOVE + DOT + FLDNAME_NODE_HTML, 
					CDataSerializer.class);
			
			info.put(CLZNAME_NODE_UPDATE, 
					NodeUpdateSerializer.class);
			info.put(CLZNAME_NODE_UPDATE + DOT + FLDNAME_PATH, 
					CharactersSerializer.class);
			info.put(CLZNAME_NODE_UPDATE + DOT + FLDNAME_NODE_HTML, 
					CDataSerializer.class);
			
			info.put(CLZNAME_NODE_VALUE_UPDATE, 
					NodeValueUpdateSerializer.class);
			info.put(CLZNAME_NODE_VALUE_UPDATE + DOT + FLDNAME_PATH, 
					CharactersSerializer.class);
			info.put(CLZNAME_NODE_VALUE_UPDATE + DOT + FLDNAME_VALUE, 
					CharactersSerializer.class);

			//IHttpMsg
			info.put(CLZNAME_HTTP_REQ, 
					HttpMsgSerializer.class);
			info.put(CLZNAME_HTTP_RESP, 
					HttpMsgSerializer.class);
			
			
		}
		
		public void serialize(DapCaptureData data, XmlStreamWriter writer){
			if(data == null){
				return;
			}
			String path = data.getClass().getSimpleName();
			DapCaptureDataSubSerializer subSerializer = 
				m_serializerRegistry.get(path);
			if(subSerializer != null){
				subSerializer.serialize(path, data, writer, path);
			}else{
				throw new DsfRuntimeException(
						"No serializer defined for '"+path+"'");
			}
		}
	}
	
	public static class DapCaptureDataSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getAttrFields() {
			return new String[]{FLDNAME_VERSION};
		}
		
		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_USER_AGENT, 
					FLDNAME_INIT_EVENT_CAPTURE, 
					FLDNAME_VIEW_CAPTURES,
					FLDNAME_HTTP_CALLS};
		}
		
		@Override
		protected String getDisplayName(String fldName,
				boolean capitalizeFirst) {
			if(FLDNAME_VERSION.equals(fldName)){
				return ATTRNAME_VER;
			}
			if(FLDNAME_INIT_EVENT_CAPTURE.equals(fldName)){
				return TAGNAME_EVENT_CAPTURE;
			}
			return super.getDisplayName(fldName, capitalizeFirst);
		}
		
		@Override
		protected void serializeAttrs(Object obj, XmlStreamWriter writer,
				Class<?> ctnClz) {
			writer.writeAttribute(ATTR_NAME_ENCODING, ENCODING_UTF_8);
			super.serializeAttrs(obj, writer, ctnClz);
		}
	
	}
	
	public static class CharactersSerializer extends DapCaptureDataSubSerializer{

		@Override
		public void serialize(String currentPath, Object obj,
				XmlStreamWriter writer, String tagName) {
			if(tagName == null){
				throw new DsfRuntimeException(
						"CharactersSerializer needs a tagName input!");
			}
			writer.writeStartElement(tagName);
			writer.writeCharacters(wrapEmptyString(obj.toString()));
			writer.writeEndElement();
		}
		
	}

	public static class CDataSerializer extends DapCaptureDataSubSerializer{

		@Override
		public void serialize(String currentPath, Object obj,
				XmlStreamWriter writer, String tagName) {
			if(tagName == null){
				throw new DsfRuntimeException(
						"CDataSerializer needs a tagName input!");
			}
			writer.writeStartElement(tagName);
			writer.writeCData(wrapEmptyString(obj.toString()));
			writer.writeEndElement();
		}
		
	}
	
	public static class EventCaptureSerializer extends ObjectSubSerializer{

		@Override
		protected String getDisplayName(String fldName,
				boolean capitalizeFirst) {
			if(FLDNAME_EVENT_TIME_INTERVAL.equals(fldName)){
				return ATTRNAME_INTERVAL;
			}
			return super.getDisplayName(fldName, capitalizeFirst);
		}
		
		@Override
		protected String[] getAttrFields() {
			return new String[]{FLDNAME_EVENT_TIME_INTERVAL};
		}
		
		@Override
		protected String[] getNodeFields() {
			return new String[]{FLDNAME_EVENT, FLDNAME_ACTIONS};
		}
	}
	
	public static class TaskCaptureSerializer extends ObjectSubSerializer{
		@Override
		protected String getDisplayName(String fldName,
				boolean capitalizeFirst) {
			if(FLDNAME_MSG.equals(fldName)){
				return ATTRNAME_MSG;
			}
			return super.getDisplayName(fldName, capitalizeFirst);
		}
		
		@Override
		protected String[] getAttrFields() {
			return new String[]{FLDNAME_MSG};
		}
		
		@Override
		protected String[] getNodeFields() {
			return new String[]{FLDNAME_ACTIONS};
		}
	}

	public static class ViewCaptureSerializer extends ObjectSubSerializer{
		

		@Override
		protected String[] getAttrFields() {
			return new String[]{FLDNAME_URL};
		}
		
		@Override
		protected String[] getNodeFields() {
			return new String[]{FLDNAME_IEVENT_CAPTURES};
		}
	}

	public static class EventCaptureGroupSerializer extends DapCaptureDataSubSerializer{

		@SuppressWarnings("unchecked")
		@Override
		public void serialize(String currentPath, Object obj,
				XmlStreamWriter writer, String tagName) {
			if(obj == null){
				return;
			}
			Map<String,List<IEventCapture>> groups = 
				(Map<String,List<IEventCapture>>)obj;
			if(groups.size() == 0){
				return;
			}
			for (Entry<String, List<IEventCapture>> group : groups.entrySet()) {
				writer.writeStartElement(TAGNAME_EVENT_CAPTURE_GROUP);
				writer.writeAttribute(ATTRNAME_NAME, group.getKey());
				List<IEventCapture> ecs = group.getValue();
				for (IEventCapture ec : ecs) {
					String clzName = ec.getClass().getSimpleName();
					DapCaptureDataSubSerializer subSerializer = 
						getSubSerializer(clzName);
					if(subSerializer != null){
						subSerializer.serialize(clzName, ec, writer, clzName);
					}
				}
				writer.writeEndElement();
			}
			
			
		}
		
	}

	public static class DLCEventSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getAttrFields() {
			return new String[]{FLDNAME_TYPE};
		}
		
		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_POSITION, 
					FLDNAME_KEY_INFO,
					FLDNAME_CANCEL_BUBBLE,
					FLDNAME_CANCELABLE,
					FLDNAME_BUTTON,
					FLDNAME_RELATED_TARGET,
					FLDNAME_RELATED_TARGET_ID,
					FLDNAME_MODIFIER_STATE,
					FLDNAME_TIME_STAMP,
					FLDNAME_DETAIL,
					FLDNAME_WHICH};
		}
		
		@Override
		protected void serializeNodes(Object obj, XmlStreamWriter writer,
				Class<?> ctnClz, String ctnClzName) {
			//deal with src related fields here
			DLCEvent e = (DLCEvent)obj;
			if(!EventType.LOAD.getName().equals(e.getType())){
				String id = e.getSrcId();
				String path = e.getSrcPath();
				if(id != null || path != null){
					writer.writeStartElement(TAGNAME_SRC);
					if(id != null 
							&& path!= null 
							&& path.startsWith("document.getElementById(")){
						writer.writeAttribute(ATTRNAME_ID, id);
					}else if(path != null){
						writer.writeAttribute(ATTRNAME_PATH, path);
					}
					if(e.getSrcProp() != null){
						writer.writeStartElement(TAGNAME_PROPERTY);
						writer.writeAttribute(ATTRNAME_NAME, e.getSrcProp());
						writer.writeCharacters(wrapEmptyString(e.getValue()));
						writer.writeEndElement();
					}
					writer.writeEndElement();
				}else if(e.getValue() != null){
					writer.writeStartElement(TAGNAME_VALUE);
					writer.writeCharacters(wrapEmptyString(e.getValue()));
					writer.writeEndElement();
				}	
			}
			
			super.serializeNodes(obj, writer, ctnClz, ctnClzName);
		}
		
		@Override
		protected boolean isDefaultValue(String fldName, Object fldValue) {
			boolean isDefault = super.isDefaultValue(fldName, fldValue);
			if (FLDNAME_BUTTON.equals(fldName)){
				isDefault = SHORT_MINUS_1.equals(fldValue);
			}else if (FLDNAME_CANCELABLE.equals(fldName)){
				isDefault = Boolean.TRUE.equals(fldValue);
			}else if (FLDNAME_WHICH.equals(fldName)){
				isDefault = INT_MINUS_1.equals(fldValue);
			}
			return isDefault;
		}
	}

	public static class PositionSerializer extends ObjectSubSerializer{


		@Override
		protected void serializeNodes(Object obj, XmlStreamWriter writer,
				Class<?> ctnClz, String ctnClzName) {
			Position pos = (Position)obj;				
			writePositionXY(writer, TAGNAME_CLIENT, pos.getClientX(), pos.getClientY());
			writePositionXY(writer, TAGNAME_MOUSE, pos.getMouseX(), pos.getMouseY());
			writePositionXY(writer, TAGNAME_PAGE, pos.getPageX(), pos.getPageY());
			writePositionXY(writer, TAGNAME_SCREEN, pos.getScreenX(), pos.getScreenY());
		}
		
		private void writePositionXY(
				XmlStreamWriter writer, 
				String tagName, 
				int x,
				int y) {
			boolean needX = !isDefaultValue(null, x);
			boolean needY = !isDefaultValue(null, y);
			if(needX || needY){
				writer.writeStartElement(tagName);
				if(needX){
					writer.writeAttribute(ATTRNAME_X, x+EMPTY_STR);
				}
				if(needY){
					writer.writeAttribute(ATTRNAME_Y, y+EMPTY_STR);
				}
				writer.writeEndElement();
			}
		}
	}
	
	public static class KeyInfoSerializer extends ObjectSubSerializer{


		@Override
		protected void serializeAttrs(Object obj, XmlStreamWriter writer,
				Class<?> ctnClz) {
			KeyInfo ki = (KeyInfo)obj;		
			if(ki.isAltKey()){
				writer.writeAttribute(ATTRNAME_ALT, EMPTY_STR);
			}
			if(ki.isCtrlKey()){
				writer.writeAttribute(ATTRNAME_CTRL, EMPTY_STR);
			}
			if(ki.isMetaKey()){
				writer.writeAttribute(ATTRNAME_META, EMPTY_STR);
			}
			if(ki.isShiftKey()){
				writer.writeAttribute(ATTRNAME_SHIFT, EMPTY_STR);
			}
		}
		
		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_CHAR_CODE,
					FLDNAME_KEY_CODE,
					FLDNAME_KEY_LOCATION,
					FLDNAME_KEY_IDENTIFIER
			};
		}
	}
	
	public static class HttpCallsSerializer extends MapSubSerializer{

		private DapHttpRequestSerializer m_reqSerializer = 
			new DapHttpRequestSerializer();
		
		private DapHttpResponseSerializer m_respSerializer = 
			new DapHttpResponseSerializer();
		
		@Override
		public void setRegistry(
				Map<String, DapCaptureDataSubSerializer> registry) {
			super.setRegistry(registry);
			m_reqSerializer.setRegistry(registry);
			m_respSerializer.setRegistry(registry);
		}
		
		@Override
		protected void serializeEntry(Entry<?, ?> entry, XmlStreamWriter writer) {
			
			Integer id = (Integer)entry.getKey();
			DapHttpCall call = (DapHttpCall)entry.getValue();
			//TODO:
			//in deserialize(), will add both req & resp to capture
			//after that, use reflection to update the resp time
			//or else we will be losing this info
			writer.writeStartElement(TAGNAME_HTTP_CALL);
			
			writer.writeAttribute(ATTRNAME_ID, id.toString());
			
			m_reqSerializer.serialize(
					CLZNAME_DAP_HTTP_REQUEST, 
					call.getRequest(), 
					writer, 
					TAGNAME_REQ);
			
			m_respSerializer.m_respTime = 
				Long.toString(call.getResponseTime());
			
			m_respSerializer.serialize(
					CLZNAME_DAP_HTTP_RESPONSE, 
					call.getResponse(), 
					writer, 
					TAGNAME_RESP);
			
			writer.writeEndElement();
		}
		
	}
	
	public static class DapHttpRequestSerializer extends ObjectSubSerializer{


		@Override
		protected String[] getAttrFields() {
			return new String[]{
					FLDNAME_URL, 
					FLDNAME_METHOD, 
					FLDNAME_ASYNC};
		}
		
		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_HOST, 
					FLDNAME_TIMEOUT, 
					FLDNAME_RAW_DATA, 
					FLDNAME_REQUEST_HEADERS};
		}
	}
	
	public static class DapHttpResponseSerializer extends ObjectSubSerializer{

		private String m_respTime;
		
		@Override
		protected void serializeAttrs(Object obj, XmlStreamWriter writer,
				Class<?> ctnClz) {
			if(m_respTime != null){
				writer.writeAttribute(ATTRNAME_TIME, m_respTime);
			}
			super.serializeAttrs(obj, writer, ctnClz);
		}
		
		@Override
		protected String[] getNodeFields() {
			return new String[]{
				FLDNAME_STATUS_CODE,
				FLDNAME_STATUS_TEXT,
				FLDNAME_RESPONSE_TEXT,
				FLDNAME_RESPONSE_HEADERS,
				FLDNAME_RAW_DATA};
		}
	}
	
	public static class HttpHeaderSerializer extends MapSubSerializer{



		@Override
		protected void serializeEntry(Entry<?, ?> entry, XmlStreamWriter writer) {
			writer.writeStartElement(TAGNAME_ENTRY);
			writer.writeAttribute(ATTRNAME_KEY, entry.getKey().toString());
			writer.writeAttribute(ATTRNAME_VALUE, entry.getValue().toString());
			writer.writeEndElement();
		}
		
	}
	
	//IDlcMsg
	
	public static class DlcRnRSerializer extends ObjectSubSerializer{


		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_REQUEST, 
					FLDNAME_RESPONSE};
		}
	}
	
	public static class DlcSendSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getNodeFields() {
			return new String[]{FLDNAME_MESSAGE};
		}
	}
	
	public static class RawCDataSerializer extends DapCaptureDataSubSerializer{

		@Override
		public void serialize(String currentPath, Object obj,
				XmlStreamWriter writer, String tagName) {
			writer.writeCData(wrapEmptyString(obj.toString()));
		}
		
	}
	
	//IDomChange
	
	public static class NodeAppendSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_PARENT_PATH, 
					FLDNAME_NODE_HTML};
		}
	}
	
	public static class NodeAttrUpdateSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_PATH, 
					FLDNAME_NAME, 
					FLDNAME_VALUE};
		}
	}
	
	public static class NodeInsertSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_REF_PATH, 
					FLDNAME_NODE_HTML, 
					FLDNAME_INSERT_BEFORE};
		}
	}
	
	public static class NodeRemoveSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_PATH, 
					FLDNAME_NODE_HTML};
		}
	}
	
	public static class NodeUpdateSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_PATH, 
					FLDNAME_NODE_HTML};
		}
	}
	
	public static class NodeValueUpdateSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getNodeFields() {
			return new String[]{
					FLDNAME_PATH, 
					FLDNAME_VALUE};
		}
	}
	
	//IHttpMsg
	
	public static class HttpMsgSerializer extends ObjectSubSerializer{

		@Override
		protected String[] getAttrFields() {
			return new String[]{FLDNAME_ID};
		}
	}
	
}