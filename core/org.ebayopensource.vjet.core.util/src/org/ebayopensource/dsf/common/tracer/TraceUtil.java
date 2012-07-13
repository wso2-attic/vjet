/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.tracer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.ebayopensource.dsf.common.xml.XmlEncoder;

public class TraceUtil {
	
	public static String getClassName(final Object caller) {
		String name = caller.getClass().getName();
		int start = name.lastIndexOf('.');
		if (start != -1) {
			name = name.substring(start+1);
		}
		return name;
	}
	
	public static String getMethodName(final Object caller, final Throwable t){
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		t.printStackTrace(new PrintStream(stream));

		Class cls = caller.getClass();
		String clsName = cls.getName();
		String stackTrace = stream.toString();
		int index = stackTrace.indexOf(clsName);
		while (index < 1){
			cls = cls.getSuperclass();
			if (cls == null){
				return "NotFound";
			}
			clsName = cls.getName();
			index = stackTrace.indexOf(clsName);;
		}
		final int start = stackTrace.indexOf(clsName) + clsName.length() + 1;
		final int end = stackTrace.indexOf("(", start);
		return XmlEncoder.encode(stackTrace.substring(start, end));
	}
	
	public static String getType(Object obj){
		if (obj == null){
			return "Null";
		}
		
		String typeName = obj.getClass().getSimpleName();
		if (typeName.length() == 0){
			typeName = obj.getClass().getName();
			int start = typeName.lastIndexOf(".");
			int end = typeName.lastIndexOf("$");
			typeName = typeName.substring(start+1, end) + "_Inner";
		}
		
		return typeName;
	}
}
