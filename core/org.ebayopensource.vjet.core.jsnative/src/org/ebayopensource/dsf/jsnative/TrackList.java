package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;
/*
 *   readonly attribute unsigned long length;
  DOMString getID(in unsigned long index);
  DOMString getKind(in unsigned long index);
  DOMString getLabel(in unsigned long index);
  DOMString getLanguage(in unsigned long index);

           attribute Function onchange;

 */
@Alias("TrackList")
@JsMetatype
public interface TrackList extends IWillBeScriptable {

	@Property long getLength();
	
	@Function String getID(long index);
	@Function String getKind(long index);
	@Function String getLabel(long index);
	@Function String getLanguage(long index);
	
	@Property Object getOnchange();
	@Property void setOnchange(Object functionRef);
}
