package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.javatojs.anno.AJavaOnly;
import org.ebayopensource.dsf.javatojs.anno.ARename;
import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;
/*
 * interface MediaError {
  const unsigned short MEDIA_ERR_ABORTED = 1;
  const unsigned short MEDIA_ERR_NETWORK = 2;
  const unsigned short MEDIA_ERR_DECODE = 3;
  const unsigned short MEDIA_ERR_SRC_NOT_SUPPORTED = 4;
  readonly attribute unsigned short code;
};
 */
@Alias("MediaError")
@JsMetatype
public interface MediaError extends IWillBeScriptable{

	@AJavaOnly @ARename(name="'MEDIA_ERR_ABORTED'")
	short MEDIA_ERR_ABORTED = 1 ;
	@AJavaOnly @ARename(name="'MEDIA_ERR_NETWORK'")
	short MEDIA_ERR_NETWORK = 2 ;
	@AJavaOnly @ARename(name="'MEDIA_ERR_DECODE'")
	short MEDIA_ERR_DECODE = 3 ;
	@AJavaOnly @ARename(name="'MEDIA_ERR_SRC_NOT_SUPPORTED'")
	short MEDIA_ERR_SRC_NOT_SUPPORTED = 4 ;
	
	@Property short getCode();
}
