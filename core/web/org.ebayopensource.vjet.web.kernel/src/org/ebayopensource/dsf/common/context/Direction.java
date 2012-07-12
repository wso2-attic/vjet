/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.context;

import java.io.Serializable;
import java.util.ListIterator;

import org.ebayopensource.dsf.common.enums.BaseEnum;

/**
 * Enum used to denote the direction of application processing.  During Request
 * processing the direction is DIRECTION.IN.  During Response processing the
 * direction is DIRECTION.OUT.  If the runtime is not active or we have not
 * specified a Request or Response direction we are processing the value will be
 * Direction.UNDEFINED.
 * <p>
 * During the request/response processing, the framework will change this from 
 * IN to OUT if using the State construct for a flow or the command/application
 * code MUST do it itself if taking on the flow processing.
 */
public final class Direction extends BaseEnum implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Direction(final int id, final String name) {
		super(id, name);
	}
		
	public static final Direction IN = new Direction(1, "IN");
	public static final Direction OUT = new Direction(2, "OUT");
	public static final Direction UNDEFINED = new Direction(3, "UNDEFINED");
		
	//for final class, optionally creates following static method
	public static ListIterator iterator() {
		return getIterator(Direction.class);
	}
		
	public static Direction get(final int id) {
		return (Direction)getEnum(Direction.class, id);
	}
		
	public static Direction getElseReturn(final int id, final Direction elseEnum) {
		return (Direction)getElseReturnEnum(Direction.class, id, elseEnum);
	}
}