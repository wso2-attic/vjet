/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst;


/**
 * Used where syntactic construct is a type reference.
 * The IJstTypeReference is used to wrap type references (needs/extends/satisfies) while 
 * carrying the regular source node info and parenting
 *
 */
public interface IJstTypeReference extends IJstNode {
	IJstType getReferencedType();
}
