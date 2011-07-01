package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.JsMetatype;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.mozilla.mod.javascript.IWillBeScriptable;

/*
 * interface DOMParserFilter {

  // Constants returned by startElement and acceptNode
  const short               FILTER_ACCEPT                  = 1;
  const short               FILTER_REJECT                  = 2;
  const short               FILTER_SKIP                    = 3;
  const short               FILTER_INTERRUPT               = 4;

  unsigned short     startElement(in Element element);
  unsigned short     acceptNode(in Node node);
  readonly attribute unsigned long   whatToShow;
};

 */
@Alias("DOMParseFilter")
@JsMetatype
public interface DomParseFilter extends IWillBeScriptable{

	@Function short startElement(Element element);
	@Function short acceptNode(Node node);
	@Property long getWhatToShow();
	
}
