package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/*
 * interface TimeRanges {
  readonly attribute unsigned long length;
  double start(in unsigned long index);
  double end(in unsigned long index);
};
 */
@Alias("TimeRanges")
@JsMetatype
public interface TimeRanges extends IWillBeScriptable {

	@Property long getLength();
	@Function double start(long index);
	@Function double end(long index);
}
