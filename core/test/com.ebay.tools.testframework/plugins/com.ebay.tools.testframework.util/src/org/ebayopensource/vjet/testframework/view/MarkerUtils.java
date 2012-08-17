/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.view;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * 
 * @author ddodd
 * 
 * eclipse Marker utilities
 * 
 */
public final class MarkerUtils {
	
	private MarkerUtils(){;}
	/**
	 * @param resource eclipse resouce
	 * @param markerId makerid of resouce 
	 * @param startLine 
	 * @param endLine
	 * @return
	 */
	public static Set<IMarker> getAllMarkersInRange(IResource resource,String markerId, int startLine, int endLine) {
		Set<IMarker> foundMarkers = new HashSet<IMarker>();
		if (resource != null) {
			try {
				IMarker[] markers = resource.findMarkers(markerId, true,IResource.DEPTH_INFINITE);
				for (IMarker marker : markers) {
					if (startLine == -1 || isMarkerInRange(marker, startLine, endLine)) {
						foundMarkers.add(marker);
					}
				}
			}
			catch (CoreException e) {
				// ignore
			}
		}
		return foundMarkers;
	}

	/**
	 * @param marker
	 * @param startLine
	 * @param endLine
	 * @return
	 * @throws CoreException
	 */
	public static boolean isMarkerInRange(IMarker marker, int startLine, int endLine)
			throws CoreException {
		if (startLine >= 0 && endLine >= startLine) {
			int line = marker.getAttribute(IMarker.LINE_NUMBER, -1);
			return (line >= startLine && line <= endLine);
		}
		return false;
	}
	
	
	/**
	 * @param resource
	 * @param markerId
	 */
	public static void deleteMarkers(IResource resource, String markerId) {
		if (resource != null && resource.isAccessible()) {
			try {
				resource.deleteMarkers(markerId, true, IResource.DEPTH_INFINITE);
			}
			catch (CoreException e) {
//				 ignore
			}
		}
	}
	

}
