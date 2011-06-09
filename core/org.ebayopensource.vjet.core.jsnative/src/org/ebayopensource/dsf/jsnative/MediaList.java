package org.ebayopensource.dsf.jsnative;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;

public interface MediaList {
/*
 *            
 * attribute DOMString        mediaText;
                                        // raises(DOMException) on setting

  readonly attribute unsigned long    length;
  DOMString          item(in unsigned long index);
  void               deleteMedium(in DOMString oldMedium)
                                        raises(DOMException);
  void               appendMedium(in DOMString newMedium)
                                        raises(DOMException);

 */

	@Property String getMediaText();
	@Property long getLength();
	
	@Function long item(long index);
	
	@Function void deleteMedium(String oldMedium);
	
	@Function void appendMedium(String newMedium);
	
   	
}
