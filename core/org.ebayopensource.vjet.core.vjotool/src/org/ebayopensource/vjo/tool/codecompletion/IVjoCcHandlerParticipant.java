/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion;

/**
 * During getting all enable advisor, IVjoCcHandlerParticipant can make a judgement that if the adivisor
 * is fit for current ctx
 * 
 *
 */
public interface IVjoCcHandlerParticipant {
	/**
	 * check if the advisor is fit for the ctx
	 * @param ctx
	 * @param advisor
	 * @return
	 */
	boolean accept(VjoCcCtx ctx, IVjoCcAdvisor advisor);
}
