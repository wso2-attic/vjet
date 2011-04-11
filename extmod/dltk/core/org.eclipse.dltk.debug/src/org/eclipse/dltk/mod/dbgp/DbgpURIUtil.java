package org.eclipse.dltk.mod.dbgp;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.mod.debug.core.DLTKDebugConstants;

/**
 * EBAY ADD
 * 
 * Utility class is to handle dbgp uri.
 * 
 * @author Patrick Ouyang
 * 
 */
public final class DbgpURIUtil {

	public static boolean isDBGPURI(URI uri) {
		if (uri == null) {
			return false;
		}
		String scheme = uri.getScheme();

		return scheme != null && DLTKDebugConstants.DBGP_SCHEME.equals(scheme);
	}

	public static boolean isDBGPPath(IPath path) {
		if (path == null) {
			return false;
		}
		return path.toString().startsWith(DLTKDebugConstants.DBGP_SCHEME);
	}

	public static URI convert2DBGPURIFromPath(IPath path)
			throws URISyntaxException {
		if (path == null || !isDBGPPath(path)) {
			return null;
		}
		// remove scheme for get the path part
		path = path.setDevice(null);
		return new URI(DLTKDebugConstants.DBGP_SCHEME, "", path.toString(),
				null);
	}
}
