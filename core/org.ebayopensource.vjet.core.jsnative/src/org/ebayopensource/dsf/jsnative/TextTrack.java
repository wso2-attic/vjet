package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.events.EventTarget;

/*
 *interface TextTrack {
  readonly attribute DOMString kind;
  readonly attribute DOMString label;
  readonly attribute DOMString language;

  const unsigned short NONE = 0;
  const unsigned short LOADING = 1;
  const unsigned short LOADED = 2;
  const unsigned short ERROR = 3;
  readonly attribute unsigned short readyState;
           attribute Function onload;
           attribute Function onerror;

  const unsigned short OFF = 0;
  const unsigned short HIDDEN = 1;
  const unsigned short SHOWING = 2;
           attribute unsigned short mode;

  readonly attribute TextTrackCueList cues;
  readonly attribute TextTrackCueList activeCues;

           attribute Function oncuechange;
};
TextTrack implements EventTarget; 
 * @author jearly
 *
 */
public interface TextTrack extends EventTarget {

	@Property String getKind();
	@Property String getLabel();
	@Property String getLanguage();
	
	@Property short getShort();
	@Property Object getOnload();
	@Property void setOnload(Object functionRef);
	
	@Property Object getOnerror();
	@Property void setOnerror(Object functionRef);

	/** "NONE" */
	@AJavaOnly
	@ARename(name = "'NONE'")
	short NONE = 0;
	
	/** "LOADING" */
	@AJavaOnly
	@ARename(name = "'LOADING'")
	short LOADING = 1;
	
	/** "LOADED" */
	@AJavaOnly
	@ARename(name = "'LOADED'")
	short LOADED = 2;
	
	/** "ERROR" */
	@AJavaOnly
	@ARename(name = "'ERROR'")
	short ERROR = 3;

}
