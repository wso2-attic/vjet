/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.ebayopensource.dsf.dap.cnr.IEventFilter;
import org.ebayopensource.dsf.html.events.EventType;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;

public class DapConsoleCaptureHandler implements IDapConsoleHandler {

	public static final String START = "CAPTURE_START";
	public static final String PAUSE = "CAPTURE_PAUSE";
	public static final String RESUME = "CAPTURE_RESUME";
	public static final String STOP = "CAPTURE_STOP";
	
	public static final String FILTER_EVENTS = "FILTER_EVENTS";
	
	private static final String[] INPUTS = {START, PAUSE, RESUME, STOP, FILTER_EVENTS};
	
	//
	// Singleton
	//
	private static DapConsoleCaptureHandler s_instance = new DapConsoleCaptureHandler();
	private DapConsoleCaptureHandler(){}
	public static DapConsoleCaptureHandler getInstance(){
		return s_instance;
	}
	
	//
	// Satisfy IDapConsoleHandler
	//
	public String[] getSupportedInputs(){
		return INPUTS;
	}
	
	public void handle(String input){
		if (input == null){
			return;
		}
		if (input.startsWith(START)) {
			StringTokenizer tokenizer = new StringTokenizer(input, " ");
			tokenizer.nextToken();
			String name = null;
			if (tokenizer.hasMoreTokens()){
				name = tokenizer.nextToken();
			}
			getCaptureReplay().startCapture(name);
		}
		else if (PAUSE.equalsIgnoreCase(input)) {
			getCaptureReplay().pauseCapture();
		}
		else if (RESUME.equalsIgnoreCase(input)) {
			getCaptureReplay().resumeCapture();
		}
		else if (STOP.equalsIgnoreCase(input)) {
			getCaptureReplay().stopCapture();
		}
		else if (input.startsWith(FILTER_EVENTS)) {
			int index = input.indexOf(FILTER_EVENTS);
			filterEvents(input.substring(index + FILTER_EVENTS.length()));
		}
	}
	
	//
	// Private
	//
	private DapCaptureReplay getCaptureReplay(){
		return DapCtx.ctx().getSession().getCaptureReplay();
	}
	
	private void filterEvents(final String options){
		if (options == null){
			return;
		}
		
		StringTokenizer tokenizer = new StringTokenizer(options, " ");
		String token;
		EventType eventType = null;
		final Set<String> eventTypes = new HashSet<String>();
		while (tokenizer.hasMoreTokens()){
			token = tokenizer.nextToken();
			if (token == null || token.trim().length() == 0){
				continue;
			}
			eventType = EventType.get(token.trim());
			if (eventType == null){
				continue;
			}
			eventTypes.add(eventType.getName());
		}
		
		if (!eventTypes.isEmpty()){
			DapCtx.ctx().getSession().getCaptureReplay().getCapture()
				.addEventFilter(new IEventFilter(){
					@Override
					public boolean filter(DLCEvent event) {
						return eventTypes.contains(event.getType());
					}
				});
		}
	}
}
