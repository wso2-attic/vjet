package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;
@Alias("DOMInput")
@JsMetatype
public interface DomInput extends IWillBeScriptable {

//	@Property DomReader getCharacterStream();
//	@Property void setCharacterStream(DomReader charStream);
	@Property DomInputStream getByteStream();
	@Property void setByteStream(DomInputStream byteStream);
	@Property String getStringData();
	@Property void setStringData(String stringData);	
	@Property String getSystemId();
	@Property void setSystemId(String systemId);	
	@Property String getEncoding();
	@Property void setEncoding(String encoding);
	@Property String getPublicId();
	@Property void setPublicId(String publicId);	
	@Property String getBaseURI();
	@Property void setBaseURI(String baseURI);	
	@Property boolean getCertified();
	@Property void setCertified(boolean certified);	
}
