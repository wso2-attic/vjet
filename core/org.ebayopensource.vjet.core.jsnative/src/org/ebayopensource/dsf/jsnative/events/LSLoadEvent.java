package org.ebayopensource.dsf.jsnative.events;

import org.ebayopensource.dsf.jsnative.Document;
import org.ebayopensource.dsf.jsnative.DomInput;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 * interface LSLoadEvent : events::Event {
  readonly attribute Document        newDocument;
  readonly attribute DOMInput        input;
};
 */
@JsMetatype
public interface LSLoadEvent extends Event {

	@Property Document getNewDocument();
	@Property DomInput getInput();
	
}
