/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

public class LocalType {
	public String method(int x, int y) {
        class Point {

                int x;
                int y;

                public Point(int x, int y) {
                        this.x = x;
                        this.y = y;

                }

                public String getCordinates() {
                        return this.x + "," + this.y;
                }
        }

        Point p = new Point(5, 6);
        return p.getCordinates();
	}
}
