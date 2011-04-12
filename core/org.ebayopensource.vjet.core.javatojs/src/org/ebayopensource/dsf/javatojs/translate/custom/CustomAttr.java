/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.custom;

public enum CustomAttr {
	EXCLUDED,
	JAVA_ONLY,
	JS_PROXY,
	MAPPED_TO_JS,
	MAPPED_TO_VJO,
	NONE;
	
	public boolean isExcluded(){
		return this == EXCLUDED;
	}
	
	public boolean isJavaOnly(){
		return this == JAVA_ONLY;
	}
	
	public boolean isJsProxy(){
		return this == JS_PROXY;
	}
	
	public boolean isMappedToJS(){
		return this == MAPPED_TO_JS;
	}
	
	public boolean isMappedToVJO(){
		return this == MAPPED_TO_VJO;
	}
	
	public boolean isNone(){
		return this == NONE;
	}
	
	public static CustomAttr get(String name){
		if (EXCLUDED.name().equals(name)){
			return EXCLUDED;
		}
		else if (JAVA_ONLY.name().equals(name)){
			return JAVA_ONLY;
		}
		else if (JS_PROXY.name().equals(name)){
			return JS_PROXY;
		}
		else if (MAPPED_TO_JS.name().equals(name)){
			return MAPPED_TO_JS;
		}
		else if (MAPPED_TO_VJO.name().equals(name)){
			return MAPPED_TO_VJO;
		}
		return NONE;
	}
}
