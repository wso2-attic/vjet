package org.ebayopensource.dsf.jstojava.resolver;

import org.ebayopensource.dsf.jst.term.NV;

public interface IOTypeResolver {
	String resolve(NV field);
	String[] getGroupIds();
}
