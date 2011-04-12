/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.datatype;

public class ObjectAndClass {

	public Object getObject(){
		return new Object();
	}
	
	public Class getObjectClass(){
		return Object.class;
	}
	
	public Class getObjectClass(Object obj){
		return obj.getClass();
	}
	
	public Class getOwnerClass(){
		return ObjectAndClass.class;
	}
	
	public Class getThisClass(){
		return this.getClass();
	}
}
