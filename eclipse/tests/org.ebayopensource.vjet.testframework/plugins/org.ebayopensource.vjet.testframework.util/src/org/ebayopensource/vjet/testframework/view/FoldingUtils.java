/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.testframework.view;

import java.util.Iterator;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

/**
 * @author ddodd
 *
 */
public final class FoldingUtils {
	
	private FoldingUtils(){;}
	
	/**
	 * @param sourceViewer
	 * @param position
	 * @return
	 */
	public static ProjectionAnnotation getProjectionAnnotation(ISourceViewer sourceViewer,Position position){
		if(sourceViewer instanceof ProjectionViewer){
			ProjectionViewer pv = (ProjectionViewer)sourceViewer;
			ProjectionAnnotationModel pam = pv.getProjectionAnnotationModel();
			Iterator iterator = pam.getAnnotationIterator();
			while(iterator.hasNext()){
				ProjectionAnnotation annotation= (ProjectionAnnotation) iterator.next();
				Position tmpp = pam.getPosition(annotation);
				if(position.overlapsWith(tmpp.offset, tmpp.length)){
					return annotation;
				}
			}
		}
		return null;
	}
	
	public static boolean isCollapsed(ISourceViewer sourceViewer,Position position){
		ProjectionAnnotation annotation = getProjectionAnnotation(sourceViewer,position);
		if(annotation != null && annotation.isCollapsed())
			return true;
		return false;
	}
	

}
