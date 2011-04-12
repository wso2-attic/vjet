/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.rt;

import org.ebayopensource.dsf.active.client.WindowFactory;
import org.ebayopensource.dsf.dap.cnr.DapCapture;
import org.ebayopensource.dsf.dap.cnr.DapCaptureData;
import org.ebayopensource.dsf.dap.cnr.DapReplay;
import org.ebayopensource.dsf.dap.cnr.IDapCapture;
import org.ebayopensource.dsf.dap.cnr.ReplaySpeed;
import org.ebayopensource.dsf.liveconnect.client.DLCEvent;

public class DapCaptureReplay {
	
	private DapBrowserEmulator m_emulator;
	private DapSession m_session;
	private IDapCapture m_dapCapture;
	private ReplayControl m_replayControl;
	
	//
	// Constructor
	//
	DapCaptureReplay(DapBrowserEmulator emulator, DapSession session){
		m_emulator = emulator;
		m_session = session;
		m_dapCapture = new DapCapture();
		m_replayControl = new ReplayControl();
	}
	
	//
	// API
	//
	public void startCapture(String captureName){
		m_dapCapture.start(captureName);
	}
	
	public void pauseCapture(){
		m_dapCapture.pause();
	}
	
	public void resumeCapture(){
		m_dapCapture.resume();
	}
	
	public void stopCapture(){
		m_dapCapture.stop();
	}
	
	public void replayCapture(ReplaySpeed speed) {
		replayCapture(m_dapCapture.getCapturedData(), speed);
	}

	public void replayCapture(DapCaptureData data, ReplaySpeed speed) {
		
		m_dapCapture.pause();
		System.err.println("\nReplay ...");
		m_replayControl.m_state = ReplayState.enable;
		
		try {
			m_session.getDLCDispatcher().send("document.location.href='" + m_session.getCurrentView().getUrl() + "'");

			synchronized(m_replayControl) {
				try {
					m_replayControl.wait();
				} catch (InterruptedException e) {
					// KEEPME
				}
			}
			
			m_replayControl.m_state = ReplayState.started;
			
			DapBrowserEngine browserEngine = m_session.getCurrentView().getEngine();
			WindowFactory.contextSwitch(browserEngine.getWindow());
			DapCtx.ctx().setWindow(browserEngine.getWindow());
			
			new DapReplay(browserEngine, m_emulator.getDlcClient())
				.play(data, speed);
		}
		catch (Exception e){
			// TODO
			e.printStackTrace();
		}
		finally {
			m_replayControl.m_state = ReplayState.off;
			System.err.println("\nEnd replay");
			m_dapCapture.resume();
		}
	}
	
	public boolean isReplay(){
		return m_replayControl.m_state != ReplayState.off;
	}
	
	public DapCaptureData getCapturedData(){
		return m_dapCapture.getCapturedData();
	}
	
	public String getCurrentCaptureName(){
		return m_dapCapture.currentCaptureName();
	}

	//
	// Protected
	//
	IDapCapture getCapture(){
		return m_dapCapture;
	}
	
	void connected(){
		if (m_replayControl.m_state == ReplayState.enable) {
			m_replayControl.m_state = ReplayState.ready;
		}
	}
	
	boolean received(DLCEvent event) {
		if (m_replayControl.m_state == ReplayState.ready) {
			synchronized(m_replayControl) {
				m_replayControl.notifyAll();
			}
			return true;
		}
		if (m_replayControl.m_state != ReplayState.off) {
			return true;
		}
		
		m_dapCapture.receiveEvent(event);
		
		return false;
	}

	public boolean executed(String msg) {
		if (m_replayControl.m_state == ReplayState.ready) {
			synchronized(m_replayControl) {
				m_replayControl.notifyAll();
			}
			return true;
		}
		if (m_replayControl.m_state != ReplayState.off) {
			return true;
		}
		
		m_dapCapture.receiveTask(msg);
		
		return false;
	}
	
	//
	// Embedded
	//
	private static class ReplayControl {
		private ReplayState m_state = ReplayState.off;
	}
	
	private static enum ReplayState {
		off,
		enable,
		ready,
		started
	}
}
