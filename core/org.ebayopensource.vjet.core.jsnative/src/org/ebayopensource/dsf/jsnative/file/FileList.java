package org.ebayopensource.dsf.jsnative.file;

import org.ebayopensource.dsf.jsnative.anno.Function;
import org.ebayopensource.dsf.jsnative.anno.Property;

/**
 * interface FileList {
      getter File item(unsigned long index);
      readonly attribute unsigned long length;
    }
 *
 */
public interface FileList {
	
	@Function File item(long index);
	@Property long getLength();
	
}
