/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.liveconnect.client.simple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.ebayopensource.dsf.common.exceptions.DsfRuntimeException;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;
import org.ebayopensource.dsf.liveconnect.client.IDLCClient;
import org.ebayopensource.dsf.liveconnect.client.NativeEvent;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent.KeyInfo;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent.Position;
import org.ebayopensource.dsf.common.enums.BaseEnum;
import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * A simple implementation of IDLCClient
 */
public class SimpleDLCClient implements IDLCClient {

	public static final byte[] DLC_CLIENT_JS = getData("DLCClient.js");

	public static final String TYPE_SEPARATOR = ":";

	private static SimpleDLCClient s_instance = new SimpleDLCClient();

	private SimpleDLCClient() {
	}

	public static SimpleDLCClient getInstance() {
		return s_instance;
	}

	public byte[] getClientJs() {
		return DLC_CLIENT_JS;
	}

	public DLCEvent parse(final String payload) {
		
		String eventType = null;
		String data = null;
		
		int index = payload.indexOf(TYPE_SEPARATOR);
		if (index < 1) {
			eventType = null;
			data = payload;
		}
		else {
			eventType = payload.substring(0, index).trim();
			if (index < payload.length() - 1) {
				data = payload.substring(index + 1).trim();
			}
		}
		
		DLCEvent event = new DLCEvent(payload);
		event.setType(eventType);
		readMoreInfoFromPayload(event);
		
		String relatedTarget = event.getRelatedTarget();
		if(relatedTarget!=null){
			event.setRelatedTargetId(getSrcId(relatedTarget));
		}
		
		String srcPath = event.getSrcPath();
		if(srcPath==null || srcPath==""){
			String[] params = data.split(":");
			for(int i=0;i<params.length;i++){
				if((params[i].indexOf("document.body")!=-1) || (params[i].indexOf("document.documentElement")!=-1))
					event.setSrcPath(params[i]);	
			}
		}
		
		String srcId = event.getSrcId();
		//only create shortcut when there's no src path but valid id exists
		if((srcPath==null || srcPath=="") && (srcId!="" && srcId!=null)){
			event.setSrcPath("document.getElementById(\""+srcId+"\")");
		}
		
		//replace payload with regenerated one
		event.setGeneratedPayload(getPayload(event));
		
		return event;
	}
	
	public String getSessionId(String payload){
		if (payload == null){
			return null;
		}
		int start = payload.indexOf ("[") + 1;
		int end = payload.indexOf ("]", start);
		return payload.substring(start, end);
	}
	
	public String getPayload(DLCEvent event){
		//TODO follow the reversed logic in parse(String payload)
		StringBuilder sb = new StringBuilder();
		String eventType = event.getType(); 
		if(eventType != null){
			sb.append(eventType);
			sb.append(TYPE_SEPARATOR);
		}
		if(EventType.BLUR.getName().equals(eventType)
				|| "contextmenu".equals(eventType)
				|| EventType.DBLCLICK.getName().equals(eventType)
				|| EventType.ERROR.getName().equals(eventType)
				|| EventType.FOCUS.getName().equals(eventType)
				|| "linkClick".equals(eventType)
				|| "linkDblClick".equals(eventType)
				|| EventType.MOUSEDOWN.getName().equals(eventType)
				|| EventType.MOUSEMOVE.getName().equals(eventType)
				|| EventType.MOUSEOUT.getName().equals(eventType)
				|| EventType.MOUSEOVER.getName().equals(eventType)
				|| EventType.MOUSEUP.getName().equals(eventType)
				|| "onlive".equals(eventType)
				|| EventType.READYSTATECHANGE.getName().equals(eventType)
				|| EventType.SELECT.getName().equals(eventType)
				|| "submitButtonClick".equals(eventType)
				){
			//Element Ref + Event Info
			String elemRef = getElemRef(event);
			sb.append(elemRef);
			
			sb.append(TYPE_SEPARATOR);
			
			sb.append(getEventInfo(event, elemRef));
		}else if(EventType.CHANGE.getName().equals(eventType)
				|| "radioChange".equals(eventType)){
			/*TODO
			 * 	select-one		change	Element Selected Index + Event Info
				All elements	change	Element value + Event Info
				select-multiple	change	Elements selected index with comman seperated + Event Info
				checkbox		change	Radio checked value + Event Info
				radio			change	Radio checked value + Event Info

			 */
			
			//try using the default format temporarily
			String elemRef = getElemRef(event);
			sb.append(elemRef);
			if(event.getSrcProp() == null){
				sb.append(".value=\"");
				sb.append(event.getValue());
				sb.append("\"");
			}else{
				sb.append(".");
				sb.append(event.getSrcProp());
				sb.append("=");
				sb.append(event.getValue());
			}
			sb.append(TYPE_SEPARATOR);
			sb.append(getEventInfo(event, elemRef));
		}else if(EventType.CLICK.getName().equals(eventType)){
			/*TODO
			 * 	All elements	click	Element Ref + Event Info
				checkbox		click	Radio checked value + Event Info
			 */
			
			//try using the default format temporarily
			String elemRef = getElemRef(event);
			sb.append(elemRef);
			if(event.getSrcProp() != null){
				sb.append(".");
				sb.append(event.getSrcProp());
				sb.append("=");
				sb.append(event.getValue());
			}
			sb.append(TYPE_SEPARATOR);
			sb.append(getEventInfo(event, elemRef));
		}else if("drop".equals(eventType)
				|| "drag".equals(eventType)){
			//Element Ref + X, Y postions
			String elemRef = getElemRef(event);
			sb.append(elemRef);
			
			sb.append(TYPE_SEPARATOR);
			if(event.getPosition() != null){
				sb.append("[");
				sb.append(event.getPosition().getMouseX());
				sb.append(",");
				sb.append(event.getPosition().getMouseX());
				sb.append("]");
			}
		}else if("imageLoad".equals(eventType)
				|| "scriptLoad".equals(eventType)
				|| "scriptReadyStateChange".equals(eventType)
				){
			//Element Ref
			sb.append(getElemRef(event));
		}else if(EventType.KEYDOWN.getName().equals(eventType)
				||EventType.KEYPRESS.getName().equals(eventType)
				||EventType.KEYUP.getName().equals(eventType)
				||EventType.RESET.getName().equals(eventType)
				||EventType.SUBMIT.getName().equals(eventType)
				){
			//Element value + Event Info
			String elemRef = getElemRef(event);
			sb.append(elemRef);
			sb.append(".value=\"");
			sb.append(event.getValue());
			sb.append("\"");
			sb.append(TYPE_SEPARATOR);
			sb.append(getEventInfo(event, elemRef));
		}else if(EventType.RESIZE.getName().equals(eventType)){
			//window width + window height
			if(event.getPosition() != null){
				sb.append("(");
				sb.append(event.getPosition().getScreenX());
				sb.append(",");
				sb.append(event.getPosition().getScreenY());
				sb.append(")");
			}
		}else if("size".equals(eventType)){
			//TODO
			//screen:size --> screen size
			//window:size --> window size
		}else if(EventType.SCROLL.getName().equals(eventType)){
			//body or ElemRef
			sb.append(getElemRef(event));
		}else if(EventType.LOAD.getName().equals(eventType)){
			//original info contains sessionID, location.href, and history in cookie
			//but for now, we can't repro it reversely. just leave it as []
			sb.append("[sessionId][location.href][flg]");
		}else if(EventType.UNLOAD.getName().equals(eventType)){
			//body
			sb.append(getElemRef(event));
		}
		
		return sb.toString();
	}
	
	private static final DLCEvent DEFAULT_DLCEVENT = new DLCEvent("");
	private static final KeyInfo DEFAULT_KEYINFO = new KeyInfo();
	private static final Position DEFAULT_POSITION = new Position();

	private String getEventInfo(DLCEvent event, String elemRef) {
		StringBuilder sb = new StringBuilder();
		boolean isMouseEvent = isMouseEvent(event);
		boolean isKeyEvent = false;
		if(!isMouseEvent){
			isKeyEvent = isKeyEvent(event);
		}
		
		//for mouse event
		int screenX = DEFAULT_POSITION.getScreenX();
		int screenY = DEFAULT_POSITION.getScreenY();
		int pageX = DEFAULT_POSITION.getPageX();
		int pageY = DEFAULT_POSITION.getPageY();
		int clientX = DEFAULT_POSITION.getClientX();
		int clientY = DEFAULT_POSITION.getClientY();
		int mouseX = DEFAULT_POSITION.getMouseX();
		int mouseY = DEFAULT_POSITION.getMouseY();
		short button = DEFAULT_DLCEVENT.getButton();
		String relatedTarget = DEFAULT_DLCEVENT.getRelatedTarget();
		
		//for key event
		int keyCode = DEFAULT_KEYINFO.getKeyCode();
		int charCode = DEFAULT_KEYINFO.getCharCode();
		String keyIdentifier = DEFAULT_KEYINFO.getKeyIdentifier();
		int keyLocation = DEFAULT_KEYINFO.getKeyLocation();
		int which = DEFAULT_DLCEVENT.getWhich();
		boolean modifierState = DEFAULT_DLCEVENT.isModifierState();
		
		//for both
		boolean altKey = DEFAULT_KEYINFO.isAltKey();
		boolean ctrlKey = DEFAULT_KEYINFO.isCtrlKey();
		boolean shiftKey = DEFAULT_KEYINFO.isShiftKey();
		boolean metaKey = DEFAULT_KEYINFO.isMetaKey();
		
		//for ui event
		boolean cancelable = event.isCancelable();
		long timeStamp = event.getTimeStamp();
		int detail = event.getDetail();
		boolean cancelBubble = event.isCancelBubble();
		
		if(isMouseEvent){
			if(event.getPosition() != null){
				screenX = event.getPosition().getScreenX();
				screenY = event.getPosition().getScreenY();
				pageX = event.getPosition().getPageX();
				pageY = event.getPosition().getPageY();
				clientX = event.getPosition().getClientX();	
				clientY = event.getPosition().getClientY();	
				mouseX = event.getPosition().getMouseX();
				mouseY = event.getPosition().getMouseY();
			}
			button = event.getButton();
			relatedTarget = event.getRelatedTarget();
			
			if(event.getKeyInfo() != null){
				if(altKey == event.getKeyInfo().isAltKey() &&
					ctrlKey == event.getKeyInfo().isCtrlKey() &&
					shiftKey == event.getKeyInfo().isShiftKey() &&
					metaKey == event.getKeyInfo().isMetaKey()){
					event.setKeyInfo(null);
				}else{
					altKey = event.getKeyInfo().isAltKey();
					ctrlKey = event.getKeyInfo().isCtrlKey();
					shiftKey = event.getKeyInfo().isShiftKey();
					metaKey = event.getKeyInfo().isMetaKey();
				}
			}
		}else{
			//clear mouse event fields
			event.setPosition(null);
			event.setButton(button);
			event.setRelatedTarget(relatedTarget);
		}
		
		if(isKeyEvent){
			if(event.getKeyInfo() != null){
				keyCode = event.getKeyInfo().getKeyCode(); 
				charCode = event.getKeyInfo().getCharCode();
				keyIdentifier = event.getKeyInfo().getKeyIdentifier();
				keyLocation = event.getKeyInfo().getKeyLocation();
				altKey = event.getKeyInfo().isAltKey();
				ctrlKey = event.getKeyInfo().isCtrlKey();
				shiftKey = event.getKeyInfo().isShiftKey();
				metaKey = event.getKeyInfo().isMetaKey();
			}
			which = event.getWhich();
			modifierState = event.isModifierState();
		}else{
			//clear key event fields

			//here, if isMouseEvent, the key info should be null or with value in altKey, ctrlKey, shiftKey and metaKey
			if(isMouseEvent){
				if(event.getKeyInfo() != null){
					event.getKeyInfo().setKeyCode(keyCode); 
					event.getKeyInfo().setCharCode(charCode);
					event.getKeyInfo().setKeyIdentifier(keyIdentifier);
					event.getKeyInfo().setKeyLocation(keyLocation);
				}
			}else{
				//not mouse event neither key event
				event.setKeyInfo(null);
			}
			event.setWhich(which);
			event.setModifierState(modifierState);
		}
			

		sb.append("[");
		sb.append(mouseX);
		sb.append(",");
		sb.append(mouseY);
		sb.append(",");
		sb.append(screenX);
		sb.append(",");
		sb.append(screenY);
		sb.append(",");
		sb.append(altKey);
		sb.append(",");
		sb.append(shiftKey);
		sb.append(",");
		sb.append(ctrlKey);
		sb.append(",");
		sb.append(cancelBubble);
		sb.append(",");
		sb.append(button); 
		sb.append(",");
		sb.append(relatedTarget); 
		sb.append(",");
		sb.append(elemRef);
		sb.append(",");
		sb.append(metaKey);
		sb.append(",");
		sb.append(keyCode);
		sb.append(",");
		sb.append(keyIdentifier); 
		sb.append(",");
		sb.append(keyLocation); 
		sb.append(",");
		sb.append(modifierState);
		sb.append(",");
		sb.append(cancelable);
		sb.append(",");
		sb.append(timeStamp);
		sb.append(",");
		sb.append(detail);
		sb.append(",");
		sb.append(pageX);
		sb.append(",");
		sb.append(pageY);
		sb.append(",");
		sb.append(clientX);
		sb.append(",");
		sb.append(clientY);
		sb.append(",");
		sb.append(which);
		sb.append(",");
		sb.append(charCode);
		sb.append("]");
		
		return sb.toString();
	}

	private boolean isMouseEvent(DLCEvent event) {
		if(EventType.CLICK.getName().equals(event.getType())
			||EventType.DBLCLICK.getName().equals(event.getType())
			||EventType.DOUBLECLICK.getName().equals(event.getType())
			||EventType.MOUSEDOWN.getName().equals(event.getType())
			||EventType.MOUSEMOVE.getName().equals(event.getType())
			||EventType.MOUSEOUT.getName().equals(event.getType())
			||EventType.MOUSEOVER.getName().equals(event.getType())
			||EventType.MOUSEUP.getName().equals(event.getType())
			||EventType.DRAG.getName().equals(event.getType())
			||EventType.DRAGEND.getName().equals(event.getType())
			||EventType.DRAGENTER.getName().equals(event.getType())
			||EventType.DRAGLEAVE.getName().equals(event.getType())
			||EventType.DRAGOVER.getName().equals(event.getType())
			||EventType.DRAGSTART.getName().equals(event.getType())
			||EventType.DROP.getName().equals(event.getType())
			||EventType.MOUSEENTER.getName().equals(event.getType())
			||EventType.MOUSELEAVE.getName().equals(event.getType())
			||EventType.MOUSEWHEEL.getName().equals(event.getType())
			||EventType.MOVE.getName().equals(event.getType())
			||EventType.MOVEEND.getName().equals(event.getType())
			||EventType.MOVESTART.getName().equals(event.getType())
				){
			return true;
		}
		return false;
	}

	private boolean isKeyEvent(DLCEvent event) {
		if(EventType.KEYDOWN.getName().equals(event.getType())
			||EventType.KEYPRESS.getName().equals(event.getType())
			||EventType.KEYUP.getName().equals(event.getType())
				){
			return true;
		}
		return false;
	}

	private String getElemRef(DLCEvent event) {
		return event.getSrcPath();//TODO: need test
	}

	public String getReqId(final String payload){
		// TODO
		return null;
	}

	public String getDlcEventHandler(NativeEvent event) {
		switch (event) {
		case click:
			return DlcJsFunctionEnum.CLICK.getName();
		case dblclick:
			return DlcJsFunctionEnum.DBLCLICK.getName();
		case load:
			return DlcJsFunctionEnum.LOAD.getName();
		case readystatechange:
			return DlcJsFunctionEnum.READYSTATECHANGE.getName();
		case DOMContentLoaded:
			return null;
		case unload:
			return DlcJsFunctionEnum.UNLOAD.getName();
		case change:
			return DlcJsFunctionEnum.CHANGE.getName();
		case select:
			return DlcJsFunctionEnum.SELECT.getName();
		case keydown:
			return DlcJsFunctionEnum.KEYDOWN.getName();
		case keyup:
			return DlcJsFunctionEnum.KEYUP.getName();
		case keypress:
			return DlcJsFunctionEnum.KEYPRESS.getName();
		case mouseover:
			return DlcJsFunctionEnum.MOUSEOVER.getName();
		case mousemove:
			return DlcJsFunctionEnum.MOUSEMOVE.getName();
		case mousedown:
			return DlcJsFunctionEnum.MOUSEDOWN.getName();
		case mouseout:
			return DlcJsFunctionEnum.MOUSEOUT.getName();
		case mouseup:
			return DlcJsFunctionEnum.MOUSEUP.getName();
		case focus:
			return DlcJsFunctionEnum.FOCUS.getName();
		case blur:
			return DlcJsFunctionEnum.BLUR.getName();
		case scroll:
			return DlcJsFunctionEnum.SCROLL.getName();
		case resize:
			return DlcJsFunctionEnum.RESIZE.getName();
		case submit:
			return DlcJsFunctionEnum.SUBMIT.getName();
		case reset:
			return DlcJsFunctionEnum.RESET.getName();
		case selectstart:
			return DlcJsFunctionEnum.SELECTSTART.getName();
		case live:
			return DlcJsFunctionEnum.LIVE.getName();
		case error:
			return DlcJsFunctionEnum.ERROR.getName();
		case contextmenu:
			return DlcJsFunctionEnum.CONTEXTMENU.getName();
		default:
			throw new DsfRuntimeException("unhandled event: " + event.name());
		}
	}
	
	//
	// Private
	//
	static final String SEARCH_STR = ":[";
	private void readMoreInfoFromPayload(final DLCEvent event){
		
		String payload = event.getPayload();
		event.setSrcId(getSrcId(payload));
		event.setValue(getValue(payload));
		event.setSrcProp(getSrcProp(payload));//to record any special value is set
		
//		Position position = event.getPosition();
//		KeyInfo keyInfo = event.getKeyInfo();

//		String pl = event.getPayload();
		int li = payload.indexOf(SEARCH_STR);
		if(li!=-1){
			String params = payload.substring(li+SEARCH_STR.length(),payload.length()-1);
			String[] paramsArr = params.split(",");
			if(paramsArr.length>=7){
				//mouseX
				getPosition(event, true).setMouseX(Integer.parseInt(paramsArr[0]));
				//mouseY 
				getPosition(event, true).setMouseY(Integer.parseInt(paramsArr[1]));
				//screenX 
				getPosition(event, true).setScreenX(Integer.parseInt(paramsArr[2]));
				//screenY 
				getPosition(event, true).setScreenY(Integer.parseInt(paramsArr[3]));
				//altKey
				getKeyInfo(event, true).setAltKey(Boolean.valueOf(paramsArr[4]));
				//shiftKey
				getKeyInfo(event, true).setShiftKey(Boolean.valueOf(paramsArr[5]));
				//ctrlKey
				getKeyInfo(event, true).setCtrlKey(Boolean.valueOf(paramsArr[6]));
			}
			//For JUnit backward compatabilty
			if(paramsArr.length>=8){
				event.setCancelBubble(Boolean.valueOf(paramsArr[7]));
			}
			if(paramsArr.length>=9){
				event.setButton(Short.parseShort(paramsArr[8]));
			}
			if(paramsArr.length>=10){
				event.setRelatedTarget(paramsArr[9]);
			}
			if(paramsArr.length>=11){
				event.setSrcPath(paramsArr[10]);
			}
			if(paramsArr.length>=12){
				getKeyInfo(event, true).setMetaKey(Boolean.valueOf(paramsArr[11]));
			}
			if(paramsArr.length>=13){
				getKeyInfo(event, true).setKeyCode(Integer.parseInt(paramsArr[12]));
			}
			if(paramsArr.length>=14){
				getKeyInfo(event, true).setKeyIdentifier(paramsArr[13]);
			}
			if(paramsArr.length>=15){
				getKeyInfo(event, true).setKeyLocation(Integer.parseInt(paramsArr[14]));
			}
			if(paramsArr.length>=16){
				event.setModifierState(Boolean.valueOf(paramsArr[15]));
			}
			if(paramsArr.length>=17){
				event.setCancelable(Boolean.valueOf(paramsArr[16]));
			}
			if(paramsArr.length>=18){
				event.setTimeStamp(Long.parseLong(paramsArr[17]));
			}
			if(paramsArr.length>=19){
				event.setDetail(Integer.parseInt(paramsArr[18]));
			}
			if(paramsArr.length>=20){
				getPosition(event, true).setPageX(Integer.parseInt(paramsArr[19]));
			}
			if(paramsArr.length>=21){
				getPosition(event, true).setPageY(Integer.parseInt(paramsArr[20]));
			}
			if(paramsArr.length>=22){
				getPosition(event, true).setClientX(Integer.parseInt(paramsArr[21]));
			}
			if(paramsArr.length>=23){
				getPosition(event, true).setClientY(Integer.parseInt(paramsArr[22]));
			}
			if(paramsArr.length>=24){
				event.setWhich(Integer.parseInt(paramsArr[23]));
			}
			if(paramsArr.length>=25){
				getKeyInfo(event, true).setCharCode(Integer.parseInt(paramsArr[24]));
			}
		}
	}
	
	private Position getPosition(DLCEvent event, boolean create){
		if (event.getPosition() == null && create){
			event.setPosition(new Position());
		}
		return event.getPosition();
	}
	
	private KeyInfo getKeyInfo(DLCEvent event, boolean create){
		if (event.getKeyInfo() == null && create){
			event.setKeyInfo(new KeyInfo());
		}
		return event.getKeyInfo();
	}
	
	static final String SRC = "getElementById(\"";
	private String getSrcId(final String payload){
		int index = payload.indexOf(SRC);
		if (index > 0){
			int start = index + SRC.length();
			return payload.substring(start, payload.indexOf("\"", start));
		}
		return null;
	}

	private static final String STR_VALUE = "=\"";
	private static final String BOOL_VALUE = "=";
	private String getValue(final String payload){
		int index = payload.indexOf(STR_VALUE);
		if (index > 0){
			int start = index + STR_VALUE.length();
			return payload.substring(start, payload.indexOf("\"", start));
		}
		index = payload.indexOf(BOOL_VALUE);
		if (index > 0){
			int start = index + BOOL_VALUE.length();
			String out = payload.substring(start);
			int end = out.indexOf(":"); 
			if(end!=-1){
				out = out.substring(0,end); 
			}
			return out ;
		}
		return null;
	}
	
	private String getSrcProp(final String payload){
		int endIdx = payload.indexOf(STR_VALUE);
		if(endIdx < 0){
			endIdx = payload.indexOf(BOOL_VALUE);
		}
		if (endIdx > 0){
			//find src
			String prefix = payload.substring(0, endIdx);
			int startIdx = prefix.lastIndexOf('.');
			return prefix.substring(startIdx + 1);
		}
		return null;
	}

	/**
	 * get data in byte[] from specified resource
	 */
	private static byte[] getData(String resourceName) {
		byte[] buffer = new byte[1024];
		int numRead = 0;
		try {
			InputStream is = ResourceUtil.getResourceAsStream(
					SimpleDLCClient.class, resourceName);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			while ((numRead = is.read(buffer)) > 0) {
				os.write(buffer, 0, numRead);
			}
			is.close();
			return os.toByteArray();
		} catch (IOException e) {
			throw new DsfRuntimeException(e);
		}
	}

	public static class DlcJsFunctionEnum extends BaseEnum {
		public static final DlcJsFunctionEnum CLICK = new DlcJsFunctionEnum(
				"DLC_onClick", 0);

		public static final DlcJsFunctionEnum DBLCLICK = new DlcJsFunctionEnum(
				"DLC_onDblClick", 1);

		public static final DlcJsFunctionEnum LOAD = new DlcJsFunctionEnum(
				"DLC_onLoad", 2);

		public static final DlcJsFunctionEnum UNLOAD = new DlcJsFunctionEnum(
				"DLC_onUnLoad", 3);

		public static final DlcJsFunctionEnum CHANGE = new DlcJsFunctionEnum(
				"DLC_onChange", 4);

		public static final DlcJsFunctionEnum SELECT = new DlcJsFunctionEnum(
				"DLC_onSelect", 5);

		public static final DlcJsFunctionEnum KEYDOWN = new DlcJsFunctionEnum(
				"DLC_onKeyDown", 6);

		public static final DlcJsFunctionEnum KEYUP = new DlcJsFunctionEnum(
				"DLC_onKeyUp", 7);

		public static final DlcJsFunctionEnum KEYPRESS = new DlcJsFunctionEnum(
				"DLC_onKeyPress", 8);

		public static final DlcJsFunctionEnum MOUSEOVER = new DlcJsFunctionEnum(
				"DLC_onMouseOver", 9);

		public static final DlcJsFunctionEnum MOUSEOUT = new DlcJsFunctionEnum(
				"DLC_onMouseOut", 10);

		public static final DlcJsFunctionEnum MOUSEMOVE = new DlcJsFunctionEnum(
				"DLC_mouseMove", 11);

		public static final DlcJsFunctionEnum MOUSEDOWN = new DlcJsFunctionEnum(
				"DLC_mouseDown", 12);

		public static final DlcJsFunctionEnum MOUSEUP = new DlcJsFunctionEnum(
				"DLC_mouseUp", 13);

		public static final DlcJsFunctionEnum FOCUS = new DlcJsFunctionEnum(
				"DLC_onFocus", 14);

		public static final DlcJsFunctionEnum SCROLL = new DlcJsFunctionEnum(
				"DLC_onScroll", 15);

		public static final DlcJsFunctionEnum RESIZE = new DlcJsFunctionEnum(
				"DLC_onResize", 16);

		public static final DlcJsFunctionEnum SUBMIT = new DlcJsFunctionEnum(
				"DLC_onSubmit", 17);

		public static final DlcJsFunctionEnum RESET = new DlcJsFunctionEnum(
				"DLC_onReset", 18);

		public static final DlcJsFunctionEnum SELECTSTART = new DlcJsFunctionEnum(
				"DLC_selectStart", 19);

		public static final DlcJsFunctionEnum BLUR = new DlcJsFunctionEnum(
				"DLC_onBlur", 20);

		public static final DlcJsFunctionEnum ERROR = new DlcJsFunctionEnum(
				"DLC_onError", 21);

		public static final DlcJsFunctionEnum READYSTATECHANGE = new DlcJsFunctionEnum(
				"DLC_onReady", 22);

		public static final DlcJsFunctionEnum LIVE = new DlcJsFunctionEnum(
				"DLC_onLive", 23);

		public static final DlcJsFunctionEnum CONTEXTMENU = new DlcJsFunctionEnum(
				"DLC_onContextMenu", 24);

		private DlcJsFunctionEnum(String name, int intValue) {
			super(intValue, name);
		}
	}

}
