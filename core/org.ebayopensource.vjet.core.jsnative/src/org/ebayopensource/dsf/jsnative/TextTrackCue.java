package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.mozilla.mod.javascript.IWillBeScriptable;
/*
 * interface TextTrackCue {
  readonly attribute TextTrack track;
  readonly attribute DOMString id;

  readonly attribute double startTime;
  readonly attribute double endTime;
  readonly attribute boolean pauseOnExit;


  DOMString getCueAsSource();
  DocumentFragment getCueAsHTML();

           attribute Function onenter;
           attribute Function onexit;
};
TextTrackCue implements EventTarget;
 */
@Alias("TextTrackCue")
@JsMetatype
public interface TextTrackCue extends IWillBeScriptable,EventTarget{

	@Property TextTrack getTrack();
	@Property String getId();
	@Property double getStartTime();
	@Property double getEndTime();
	@Property boolean getPauseOnExit();
	
	@Function String getCueAsSource();
	@Function DocumentFragment getCueAsHTML();
	
	@Property Object getOnenter();
	@Property void setOnender(Object functionRef);
	@Property Object getOnexit();
	@Property void setOnexit(Object functionRef);
	
	
	
	
}
