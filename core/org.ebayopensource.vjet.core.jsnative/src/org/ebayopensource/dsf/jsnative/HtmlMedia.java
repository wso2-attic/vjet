/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsnative;

import java.util.Date;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.DOMSupport;
import org.ebayopensource.dsf.jsnative.anno.DomLevel;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 *  // error state
 readonly attribute MediaError error;

 // network state
 attribute DOMString src;
 readonly attribute DOMString currentSrc;
 const unsigned short NETWORK_EMPTY = 0;
 const unsigned short NETWORK_IDLE = 1;
 const unsigned short NETWORK_LOADING = 2;
 const unsigned short NETWORK_NO_SOURCE = 3;
 readonly attribute unsigned short networkState;
 attribute DOMString preload;
 readonly attribute TimeRanges buffered;
 void load();
 DOMString canPlayType(in DOMString type);

 // ready state
 const unsigned short HAVE_NOTHING = 0;
 const unsigned short HAVE_METADATA = 1;
 const unsigned short HAVE_CURRENT_DATA = 2;
 const unsigned short HAVE_FUTURE_DATA = 3;
 const unsigned short HAVE_ENOUGH_DATA = 4;
 readonly attribute unsigned short readyState;
 readonly attribute boolean seeking;

 // playback state
 attribute double currentTime;
 readonly attribute double initialTime;
 readonly attribute double duration;
 readonly attribute Date startOffsetTime;
 readonly attribute boolean paused;
 attribute double defaultPlaybackRate;
 attribute double playbackRate;
 readonly attribute TimeRanges played;
 readonly attribute TimeRanges seekable;
 readonly attribute boolean ended;
 attribute boolean autoplay;
 attribute boolean loop;
 void play();
 void pause();

 // media controller
 attribute DOMString mediaGroup;
 attribute MediaController controller;

 // controls
 attribute boolean controls;
 attribute double volume;
 attribute boolean muted;
 attribute boolean defaultMuted;

 // tracks
 readonly attribute MultipleTrackList audioTracks;
 readonly attribute ExclusiveTrackList videoTracks;
 readonly attribute TextTrack[] textTracks;
 MutableTextTrack addTextTrack(in DOMString kind, in optional DOMString label, in optional DOMString language);

 */

@Alias("HTMLMediaElement")
@DOMSupport(DomLevel.ONE)
@JsMetatype
public interface HtmlMedia extends HtmlElement {

	@Property
	MediaError getError();

	@Property
	String getSrc();

	@Property
	void setSrc(String src);

	@Property
	String getCurrentSrc();

	/** "NETWORK_EMPTY" */
	@AJavaOnly
	@ARename(name = "'NETWORK_EMPTY'")
	short NETWORK_EMPTY = 0;

	/** "NETWORK_IDLE" */
	@AJavaOnly
	@ARename(name = "'NETWORK_IDLE'")
	short NETWORK_IDLE = 1;

	/** "NETWORK_LOADING" */
	@AJavaOnly
	@ARename(name = "'NETWORK_LOADING'")
	short NETWORK_LOADING = 2;

	/** "NETWORK_NO_SOURCE" */
	@AJavaOnly
	@ARename(name = "'NETWORK_NO_SOURCE'")
	short NETWORK_NO_SOURCE = 3;

	/** "HAVE_NOTHING" */
	@AJavaOnly
	@ARename(name = "'HAVE_NOTHING'")
	short HAVE_NOTHING = 0;

	/** "HAVE_METADATA" */
	@AJavaOnly
	@ARename(name = "'HAVE_METADATA'")
	short HAVE_METADATA = 1;

	/** "HAVE_CURRENT_DATA" */
	@AJavaOnly
	@ARename(name = "'HAVE_CURRENT_DATA'")
	short HAVE_CURRENT_DATA = 2;

	/** "HAVE_FUTURE_DATA" */
	@AJavaOnly
	@ARename(name = "'HAVE_FUTURE_DATA'")
	short HAVE_FUTURE_DATA = 3;

	/** "HAVE_FUTURE_DATA" */
	@AJavaOnly
	@ARename(name = "'HAVE_FUTURE_DATA'")
	short HAVE_ENOUGH_DATA = 4;

	@Property
	short getReadyState();

	@Property
	short getNetworkState();

	@Property
	String getPreload();

	@Property
	TimeRanges getBuffered();

	@Property
	boolean getSeeking();

	@Function
	void load();

	@Function
	String canPlayType(String type);

	@Property
	double getCurrentTime();

	@Property
	void setCurrentTime(double currentTime);

	@Property
	double getInitialTime();

	@Property
	double getDuration();

	@Property
	Date getStartOffsetTime();

	@Property
	boolean getPaused();

	@Property
	double getDefaultPlaybackRate();

	@Property
	void setDefaultPlaybackRate(double defaultPlaybackRate);

	@Property
	double getPlaybackRate();

	@Property
	void setPlaybackRate(double rate);

	@Property
	TimeRanges getPlayed();

	@Property
	TimeRanges getSeekable();

	@Property
	boolean getEnded();

	@Property
	boolean getAutoplay();
	@Property
	void setAutoplay(boolean autoPlay);

	@Function
	void play();

	@Function
	void pause();

	@Property
	String getMediaGroup();
	@Property
	void setMediaGroup(String mediaGroup);

	@Property
	MediaController getController();
	
	@Property
	void setController(MediaController controller);

	@Property
	double getVolume();
	@Property
	void setVolume(double volume);

	@Property
	boolean getMuted();
	@Property
	void setMuted(boolean muted);

	@Property
	boolean getDefaultMuted();
	@Property
	void setDefaultMuted(boolean defaultMuted);
//
//	@Property
//	boolean getAutoBuffer();
//
//	@Property
//	void setAutoBuffer(boolean autoBuffer);

//	@Property
//	boolean getAutoPlay();
//
//	@Property
//	void setAutoPlay(boolean autoPlay);

//	@Property
//	boolean getLoop();
//
//	@Property
//	void setLoop(boolean loop);

	@Property
	boolean getControls();

	@Property
	void setControls(boolean controls);
	
	@Property MultipleTrackList getAudioTracks();
	
	@Property ExclusiveTrackList getVideoTracks();
	@Property TextTrack[] getTextTracks();
	
	@OverLoadFunc MutableTextTrack addTextTrack(String kind, String label, String language);
	@OverLoadFunc MutableTextTrack addTextTrack(String kind, String label);
	@OverLoadFunc MutableTextTrack addTextTrack(String kind);

//	/**
//	 * Returns the onblur event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onblur")
//	Object getOnBlur();
//
//	/**
//	 * Sets the onblur event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onblur.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onblur")
//	void setOnBlur(Object functionRef);
//
//	/**
//	 * Returns the onfocus event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onfocus")
//	Object getOnFocus();
//
//	/**
//	 * Sets the onfocus event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onfocus.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onfocus")
//	void setOnFocus(Object functionRef);
//
//	/**
//	 * Returns the onkeydown event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onkeydown.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onkeydown")
//	Object getOnKeyDown();
//
//	/**
//	 * Sets the onkeydown event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onkeydown.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onkeydown")
//	void setOnKeyDown(Object functionRef);
//
//	/**
//	 * Returns the onkeypress event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onkeypress.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onkeypress")
//	Object getOnKeyPress();
//
//	/**
//	 * Sets the onkeypress event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onkeypress.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onkeypress")
//	void setOnKeyPress(Object functionRef);
//
//	/**
//	 * Returns the onkeyup event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onkeyup.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onkeyup")
//	Object getOnKeyUp();
//
//	/**
//	 * Sets the onkeyup event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onkeyup.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onkeyup")
//	void setOnKeyUp(Object functionRef);
//
//	/**
//	 * Returns the onresize event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onresize.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onresize")
//	Object getOnResize();
//
//	/**
//	 * Sets the onresize event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onresize.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onresize")
//	void setOnResize(Object functionRef);
//
//	/**
//	 * Returns the onclick event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onclick.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onclick")
//	Object getOnClick();
//
//	/**
//	 * Sets the onclick event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onclick.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onclick")
//	void setOnClick(Object functionRef);
//
//	/**
//	 * Returns the ondblclick event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_ondblclick.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "ondblclick")
//	Object getOnDblClick();
//
//	/**
//	 * Sets the ondblclick event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_ondblclick.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "ondblclick")
//	void setOnDblClick(Object functionRef);
//
//	/**
//	 * Returns the onmousedown event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onmousedown.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmousedown")
//	Object getOnMouseDown();
//
//	/**
//	 * Sets the onmousedown event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onmousedown.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmousedown")
//	void setOnMouseDown(Object functionRef);
//
//	/**
//	 * Returns the onmouseup event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onmouseup.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmouseup")
//	Object getOnMouseUp();
//
//	/**
//	 * Sets the onmouseup event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onmouseup.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmouseup")
//	void setOnMouseUp(Object functionRef);
//
//	/**
//	 * Returns the onmousemove event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onmousemove.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmousemove")
//	Object getOnMouseMove();
//
//	/**
//	 * Sets the onmousemove event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onmousemove.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmousemove")
//	void setOnMouseMove(Object functionRef);
//
//	/**
//	 * Returns the onmouseout event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onmouseout.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmouseout")
//	Object getOnMouseOut();
//
//	/**
//	 * Sets the onmouseout event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onmouseout.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmouseout")
//	void setOnMouseOut(Object functionRef);
//
//	/**
//	 * Returns the onmouseover event handler code on the current element.
//	 * 
//	 * @see http://www.w3schools.com/jsref/jsref_onmouseover.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmouseover")
//	Object getOnMouseOver();
//
//	/**
//	 * Sets the onmouseover event handler code on the current element.
//	 * 
//	 * @param functionRef
//	 * @see http://www.w3schools.com/jsref/jsref_onmouseover.asp
//	 */
//	@DOMSupport(DomLevel.ZERO)
//	@Property(name = "onmouseover")
//	void setOnMouseOver(Object functionRef);

}
