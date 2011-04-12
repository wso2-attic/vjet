/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.dap.api.util;

import org.ebayopensource.dsf.dap.proxy.NativeJsHelper;
import org.mozilla.mod.javascript.NativeJavaObject;

/**
 * This helper class is to provide conversion from "variant" JS type which
 * could represent both "Object" and JS primitives, such as, "String", "boolean" and "number".
 * 
 * These conversions are only necessary in java programming and active-mode execution.
 * 
 * The Java2Js translation will remove these explicit conversion operations.
 */
public class DapVariantTypeHelper {
	
	private static final DapVariantTypeHelper s_instance = new DapVariantTypeHelper();
	private DapVariantTypeHelper(){}
	public static DapVariantTypeHelper getInstance(){
		return s_instance;
	}
	
	public String string_(Object variant) {
		if (variant instanceof NativeJavaObject){
			variant = ((NativeJavaObject)variant).unwrap();
		}
		if (variant instanceof String) {
			return (String)variant;
		}
		return variant == null ? null : variant.toString();
	}
	
	public boolean boolean_(Object variant) {
		return NativeJsHelper.convert(boolean.class, variant);
	}
	
	public int int_(Object variant) {
		if (variant == null) {
			throw new RuntimeException("null can't be converted into int value.");
		}
		return NativeJsHelper.convert(int.class, variant);
	}
	
	public double double_(Object variant) {
		if (variant == null) {
			throw new RuntimeException("null can't be converted into double value.");
		}
		return NativeJsHelper.convert(double.class, variant);
	}
}
