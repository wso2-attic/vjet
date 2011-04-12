/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.service;

public class ServiceIdHelper {
	
	private static final String SVC_ID_DELIMITER = ".";
	
	public static String createServiceId(final String svcName, final String opName){
		if (opName == null){
			return svcName;
		}
		return svcName + SVC_ID_DELIMITER + opName;
	}
	
	public static String getServiceName(final String svcId){
		if (svcId == null){
			return null;
		}
		int index = svcId.indexOf(SVC_ID_DELIMITER);
		return (index > 0) ? svcId.substring(0, index) : svcId;
	}
	
	public static String getOperationName(final String svcId){
		if (svcId == null){
			return null;
		}
		int index = svcId.indexOf(SVC_ID_DELIMITER);
		return (index > 0) ? svcId.substring(index+1) : null;
	}
}
