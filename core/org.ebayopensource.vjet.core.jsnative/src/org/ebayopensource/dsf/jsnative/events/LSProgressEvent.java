package org.ebayopensource.dsf.jsnative.events;

import org.ebayopensource.dsf.jsnative.DomInput;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 * LSProgressEvent
 * interface LSProgressEvent : events::Event {
  readonly attribute DOMInput        input;
  readonly attribute unsigned long   position;
  readonly attribute unsigned long   totalSize;
};

 */
@JsMetatype
public interface LSProgressEvent extends Event {

	
	@Property DomInput getInput();
	@Property long getPosition();
	@Property long getTotalSize();
	
}
