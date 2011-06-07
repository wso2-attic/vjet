/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.active.dom.html;

import java.util.Date;

import org.ebayopensource.dsf.html.dom.DAudio;
import org.ebayopensource.dsf.html.dom.EHtmlAttr;
import org.ebayopensource.dsf.jsnative.ExclusiveTrackList;
import org.ebayopensource.dsf.jsnative.HtmlAudio;
import org.ebayopensource.dsf.jsnative.MediaController;
import org.ebayopensource.dsf.jsnative.MediaError;
import org.ebayopensource.dsf.jsnative.MultipleTrackList;
import org.ebayopensource.dsf.jsnative.MutableTextTrack;
import org.ebayopensource.dsf.jsnative.TextTrack;
import org.ebayopensource.dsf.jsnative.TimeRanges;
import org.ebayopensource.dsf.jsnative.anno.BrowserType;

public class AHtmlAudio extends AHtmlElement implements HtmlAudio {

	private static final long serialVersionUID = 1L;
	
	//
	// Constructor(s)
	//
	protected AHtmlAudio(AHtmlDocument doc, DAudio node) {
		super(doc, node);
		populateScriptable(AHtmlAudio.class, doc == null ? BrowserType.IE_6P : doc.getBrowserType());
	}

	//
	// API
	//	
	public String getSrc() {
		return getDAudio().getHtmlSrc();
	}
	public void setSrc(String src) {
		getDAudio().setHtmlSrc(src);
		onAttrChange(EHtmlAttr.src, src);
	}
	
	public boolean getAutoBuffer() {
		return getDAudio().getHtmlAutoBuffer();
	}
	public void setAutoBuffer(boolean autoBuffer) {
		setHtmlAttribute(EHtmlAttr.autobuffer, autoBuffer);
		onAttrChange(EHtmlAttr.autobuffer, autoBuffer);
	}
	
	public boolean getAutoPlay() {
		return getDAudio().getHtmlAutoPlay();
	}
	public void setAutoPlay(boolean autoPlay) {
		setHtmlAttribute(EHtmlAttr.autoplay, autoPlay);
		onAttrChange(EHtmlAttr.autoplay, autoPlay);
	}
	
	public boolean getLoop() {
		return getDAudio().getHtmlLoop();
	}
	public void setLoop(boolean loop) {
		setHtmlAttribute(EHtmlAttr.loop, loop);
		onAttrChange(EHtmlAttr.loop, loop);
	}
	
	public boolean getControls() {
		return getDAudio().getHtmlControls();
	}
	public void setControls(boolean controls) {
		setHtmlAttribute(EHtmlAttr.controls, controls);
		onAttrChange(EHtmlAttr.controls, controls);
	}
	
	//
	// Events
	//

	// Since property name is 'onblur', Rhino invokes this method.
	public Object getOnblur() {
		return getOnBlur();
	}
	
	// Since property name is 'onfocus', Rhino invokes this method.
	public Object getOnfocus() {
		return getOnFocus();
	}
	
	// For Rhino
	public void setOnblur(Object functionRef) {
		setOnBlur(functionRef);
	}
	
	// For Rhino
	public void setOnfocus(Object functionRef) {
		setOnFocus(functionRef);
	}
	
	// Since property name is 'onkeydown', Rhino invokes this method.
	public Object getOnkeydown() {
		return getOnKeyDown();
	}
	
	// For Rhino
	public void setOnkeydown(Object functionRef) {
		setOnKeyDown(functionRef);
	}
	
	// Since property name is 'onkeypress', Rhino invokes this method.
	public Object getOnkeypress() {
		return getOnKeyPress();
	}
	
	// For Rhino
	public void setOnkeypress(Object functionRef) {
		setOnKeyPress(functionRef);
	}
	
	// Since property name is 'onkeyup', Rhino invokes this method.
	public Object getOnkeyup() {
		return getOnKeyUp();
	}
	
	// For Rhino
	public void setOnkeyup(Object functionRef) {
		setOnKeyUp(functionRef);
	}
	
	// Since property name is 'onresize', Rhino invokes this method.
	public Object getOnresize() {
		return getOnResize();
	}
	
	// For Rhino
	public void setOnresize(Object functionRef) {
		setOnResize(functionRef);
	}
	
	// Since property name is 'onclick', Rhino invokes this method.
	public Object getOnclick() {
		return getOnClick();
	}
	
	// For Rhino
	public void setOnclick(Object functionRef) {
		setOnClick(functionRef);
	}
	
	// Since property name is 'ondblclick', Rhino invokes this method.
	public Object getOndblclick() {
		return getOnDblClick();
	}
	
	// For Rhino
	public void setOndblclick(Object functionRef) {
		setOnDblClick(functionRef);
	}
	
	// Since property name is 'onmousedown', Rhino invokes this method.
	public Object getOnmousedown() {
		return getOnMouseDown();
	}
	
	// For Rhino
	public void setOnmousedown(Object functionRef) {
		setOnMouseDown(functionRef);
	}
	
	// Since property name is 'onmousemove', Rhino invokes this method.
	public Object getOnmousemove() {
		return getOnMouseMove();
	}
	
	// For Rhino
	public void setOnmousemove(Object functionRef) {
		setOnMouseMove(functionRef);
	}
	
	// Since property name is 'onmouseout', Rhino invokes this method.
	public Object getOnmouseout() {
		return getOnMouseOut();
	}
	
	// For Rhino
	public void setOnmouseout(Object functionRef) {
		setOnMouseOut(functionRef);
	}
	
	// Since property name is 'onmouseover', Rhino invokes this method.
	public Object getOnmouseover() {
		return getOnMouseOver();
	}
	
	// For Rhino
	public void setOnmouseover(Object functionRef) {
		setOnMouseOver(functionRef);
	}
	
	// Since property name is 'onmouseup', Rhino invokes this method.
	public Object getOnmouseup() {
		return getOnMouseUp();
	}
	
	// For Rhino
	public void setOnmouseup(Object functionRef) {
		setOnMouseUp(functionRef);
	}
	
	//
	// Private
	//
	private DAudio getDAudio() {
		return (DAudio) getDNode();
	}

	@Override
	public MediaError getError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentSrc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getReadyState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getNetworkState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getPreload() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeRanges getBuffered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getSeeking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String canPlayType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentTime(double currentTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getInitialTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getStartOffsetTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getPaused() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getDefaultPlaybackRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDefaultPlaybackRate(double defaultPlaybackRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getPlaybackRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPlaybackRate(double rate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TimeRanges getPlayed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TimeRanges getSeekable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getEnded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAutoplay() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutoplay(boolean autoPlay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getMediaGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMediaGroup(String mediaGroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MediaController getController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setController(MediaController controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getVolume() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setVolume(double volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getMuted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMuted(boolean muted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getDefaultMuted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDefaultMuted(boolean defaultMuted) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MultipleTrackList getAudioTracks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExclusiveTrackList getVideoTracks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TextTrack[] getTextTracks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableTextTrack addTextTrack(String kind, String label,
			String language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableTextTrack addTextTrack(String kind, String label) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MutableTextTrack addTextTrack(String kind) {
		// TODO Auto-generated method stub
		return null;
	}
}
