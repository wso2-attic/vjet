package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Alias;
import org.ebayopensource.dsf.jsnative.anno.Constructor;
import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;
import org.ebayopensource.dsf.jsnative.events.EventTarget;
import org.mozilla.mod.javascript.IWillBeScriptable;

/*
 * interface DOMParser {
  readonly attribute DOMConfiguration config;
           attribute DOMParserFilter filter;
  readonly attribute boolean         async;
  readonly attribute boolean         busy;
  Document           parse(in DOMInput is)
                                        raises(DOMException);
  Document           parseURI(in DOMString uri)
                                        raises(DOMException);

  // ACTION_TYPES
  const unsigned short      ACTION_APPEND_AS_CHILDREN      = 1;
  const unsigned short      ACTION_REPLACE_CHILDREN        = 2;
  const unsigned short      ACTION_INSERT_BEFORE           = 3;
  const unsigned short      ACTION_INSERT_AFTER            = 4;
  const unsigned short      ACTION_REPLACE                 = 5;

  Node               parseWithContext(in DOMInput input, 
                                      in Node context, 
                                      in unsigned short action)
                                        raises(DOMException);
  void               abort();
};

 */
@Alias("DOMParser")
public interface DomParser extends EventTarget, IWillBeScriptable {
	
	public static final short ACTION_APPEND_AS_CHILDREN            = 1;
	public static final short ACTION_REPLACE_CHILDREN            = 2;
	public static final short ACTION_INSERT_BEFORE           = 3;
	public static final short ACTION_INSERT_AFTER           = 4;
	public static final short ACTION_REPLACE           = 5;
	

	@Constructor void DomParser();
	@Property DOMConfiguration getConfig();
	@Property DomParseFilter getFiler();
	@Property boolean getAsync();
	@Property boolean getBusy();
	@Function Document parse(DomInput is );
	@Function Document parseURI(String uri);
	@Function Node parseWithContext(DomInput input, Node context, short action);
	
}
