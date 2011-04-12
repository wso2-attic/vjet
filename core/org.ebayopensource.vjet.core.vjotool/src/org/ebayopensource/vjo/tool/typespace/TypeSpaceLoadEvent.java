/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.typespace;

import java.util.List;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ts.TypeSpaceLocker;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.EventListenerStatus.ErrorSource;

public class TypeSpaceLoadEvent implements ISourceEventCallback<IJstType> {

	private int m_totalGroupsInTs;
	private ISourceEventCallback<IJstType> m_callback;
	//commenting out as nothing is reading from this var.
//	private TypeSpaceLocker m_locker;
	private TypeLoadMonitor m_monitor;

	public TypeSpaceLoadEvent(int totalGroupsInTS,
			ISourceEventCallback<IJstType> callback, TypeSpaceLocker locker,
			TypeLoadMonitor monitor) {
		m_totalGroupsInTs = totalGroupsInTS;
		m_callback = callback;
//		m_locker = locker;
		m_monitor = monitor;

	}

	public void onComplete(EventListenerStatus status) {
		// TODO Auto-generated method stub
		TypeSpaceMgr instance = TypeSpaceMgr.getInstance();
		boolean loaded = false;
		int size = instance.getTypeSpace().getGroups().size();
		if (size == m_totalGroupsInTs) {
			loaded = true;
		}

		instance.fireLoadTypesFinished();
		/*if (status.getCode() == EventListenerStatus.Code.Successful) {
			instance.setLoaded(true);
		} else {
			instance.setLoaded(true);
//			String errors = getErrorString(status);
//			 DLTKCore.error(errors);
		}*/
		
		//DLTKCore.errors(errors) was commented out which caused if/else branch
		//does same thing.  Commenting out the above else if and adding line
		//below.
		instance.setLoaded(true);

		if (m_callback != null) {
			m_callback.onComplete(status);
		}
	}

	public void onProgress(float percent) {
		if (m_callback != null) {
			m_callback.onProgress(percent);
		}
		if (m_monitor != null) {
			m_monitor.loadTypeStarted(percent);
		}
	}

	public static String getErrorString(EventListenerStatus status) {
		StringBuffer buffer = new StringBuffer();
		List<ErrorSource> list = status.getErrorSourceList();
		if (list != null) {
			for (ErrorSource errorSource : list) {
				buffer.append(errorSource.getSource());
				buffer.append(": ");
				buffer.append(errorSource.getErrMsg());
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}

}
