/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.client;

import org.ebayopensource.dsf.active.event.IBrowserBinding;
import org.ebayopensource.dsf.jsnative.Screen;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;


public class AScreen extends ActiveObject implements Screen {
	
	private static final long serialVersionUID = 1L;
		
	private int m_availHeight = -1;  
	private int m_availWidth = -1;  
	private int m_bufferDepth = -1;
	private int m_colorDepth = -1;
	private int m_deviceXDPI = -1;
	private int m_deviceYDPI = -1;
	private boolean m_fontSmoothingEnabled = false;
	private int m_height = -1;  
	private int m_logicalXDPI = -1;
	private int m_logicalYDPI = -1;
	private int m_pixelDepth = -1; 
	private int m_updateInterval = -1; 
	private int m_width = -1;
	
	private IBrowserBinding m_browserBinding;

	public AScreen(BrowserType browserType) {
		populateScriptable(AScreen.class, browserType);
	}

	public int getAvailHeight() {
		if (m_availHeight == -1) {
			m_availHeight = getIntValue("availHeight", 600);
		}
		return m_availHeight;
	}

	public int getAvailWidth() {
		if (m_availWidth == -1) {
			m_availWidth = getIntValue("availWidth", 800);
		}
		return m_availWidth;
	}

	public int getColorDepth() {
		if (m_colorDepth == -1) {
			m_colorDepth = getIntValue("colorDepth", 8);
		}
		return m_colorDepth;
	}

	public int getHeight() {
		if (m_height == -1) {
			m_height = getIntValue("height", 600);
		}
		return m_height;
	}
	
	public void setHeight(int height){
		m_height = height;
	}

	public int getPixelDepth() {
		if (m_pixelDepth == -1) {
			m_pixelDepth = getIntValue("pixelDepth", 8);
		}
		return m_pixelDepth;
	}

	public int getWidth() {
		if (m_width == -1) {
			m_width = getIntValue("width", 800);
		}
		return m_width;
	}
	
	public void setWidth(int width){
		m_width = width;
	}

	public int getBufferDepth() {
		if (m_bufferDepth == -1) {
			m_bufferDepth = getIntValue("bufferDepth", 0);
		}
		return m_bufferDepth;
	}

	public int getDeviceXDPI() {
		if (m_deviceXDPI == -1) {
			m_deviceXDPI = getIntValue("deviceXDPI", 96);
		}
		return m_deviceXDPI;
	}

	public int getDeviceYDPI() {
		if (m_deviceYDPI == -1) {
			m_deviceYDPI = getIntValue("deviceYDPI", 96);
		}
		return m_deviceYDPI;
	}

	public boolean getFontSmoothingEnabled() {
		return m_fontSmoothingEnabled;
	}

	public int getLogicalXDPI() {
		if (m_logicalXDPI == -1) {
			m_logicalXDPI = getIntValue("logicalXDPI", 96);
		}
		return m_logicalXDPI;
	}

	public int getLogicalYDPI() {
		if (m_logicalYDPI == -1) {
			m_logicalYDPI = getIntValue("logicalYDPI", 96);
		}
		return m_logicalYDPI;
	}

	public int getUpdateInterval() {
		if (m_updateInterval == -1) {
			m_updateInterval = getIntValue("logicalYDPI", 0);
		}
		return m_updateInterval;
	}

	public void setBufferDepth(int depth) {
		m_bufferDepth = depth;
	}

	public void setUpdateInterval(int interval) {
		m_updateInterval = interval;
	}

	public void setBrowserBinding(IBrowserBinding binding)	{
		m_browserBinding = binding;
	}
	
	private int getIntValue(String name, int fallback) {
		if (m_browserBinding != null) {
			String value = m_browserBinding.getScreenProperty(name);
			if (value != null && !value.equals("null")) {
				return Integer.parseInt(value);
			}
		}
		
		return fallback;
	}
}
