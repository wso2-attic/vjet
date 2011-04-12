/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.datatype;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.declaration.JstRefType;

public class JstReservedTypes {
	
	/**
	 * Use JstType from JstCache instead
	 */
	@Deprecated
	public interface JsNative {
		//TODO - We should no longer use these, get these from cache
//		JstRefType STRING = JstFactory.getInstance().createJstRefType("String", false, false, false);
		JstRefType NUMBER = JstFactory.getInstance().createJstRefType("Number", false, false, false);
//		JstRefType BOOLEAN = JstFactory.getInstance().createJstRefType("Boolean", false, false, false);
		JstRefType OBJECT = JstFactory.getInstance().createJstRefType("Object", false, false, false);
		JstRefType ARRAY = JstFactory.getInstance().createJstRefType("Array", false, true, false);
		JstRefType REGEXP = JstFactory.getInstance().createJstRefType("RegExp", false, false, false);
		IJstType[] ALL = new IJstType[]{ NUMBER, OBJECT, ARRAY, REGEXP};
	}
	
	public interface JavaPrimitive {
		JstRefType BOOLEAN = JstFactory.getInstance().createJstRefType("boolean", true, false, false);
		JstRefType BYTE = JstFactory.getInstance().createJstRefType("byte", true, false, false);
		JstRefType SHORT = JstFactory.getInstance().createJstRefType("short", true, false, false);
		JstRefType CHAR = JstFactory.getInstance().createJstRefType("char", true, false, false);
		JstRefType INT = JstFactory.getInstance().createJstRefType("int", true, false, false);
		JstRefType FLOAT = JstFactory.getInstance().createJstRefType("float", true, false, false);
		JstRefType LONG = JstFactory.getInstance().createJstRefType("long", true, false, false);
		JstRefType DOUBLE = JstFactory.getInstance().createJstRefType("double", true, false, false);
		JstRefType[] ALL = new JstRefType[]{BOOLEAN, BYTE, SHORT, CHAR, INT, FLOAT, LONG, DOUBLE};
	}
	
	public interface Other {
		JstRefType VOID = JstFactory.getInstance().createJstRefType("void", true, false, false);
	}
}
