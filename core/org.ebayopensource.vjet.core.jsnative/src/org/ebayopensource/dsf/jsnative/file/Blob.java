package org.ebayopensource.dsf.jsnative.file;

import org.ebayopensource.dsf.jsnative.anno.OverLoadFunc;
import org.ebayopensource.dsf.jsnative.anno.Property;

/*
 *   interface Blob {
      
      readonly attribute unsigned long long size;
      readonly attribute DOMString type;
      
      //slice Blob into byte-ranged chunks
      
      Blob slice(in optional long long start,
                 in optional long long end,
                 in [TreatUndefinedAs=EmptyString] optional DOMString contentType); 
    
    };
    
 */
public interface Blob {

	@Property long getSize();
	@Property String getType();
	@OverLoadFunc Blob slice(long start, long end, String content);
	@OverLoadFunc Blob slice(long start, long end);
	@OverLoadFunc Blob slice(long start);
}
