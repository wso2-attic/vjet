package vjo.java.text;

/*
 * @(#)src/classes/sov/java/text/DontCareFieldPosition.java, i18n, asdev, 20070119 1.4
 * ===========================================================================
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 *
 * IBM SDK, Java(tm) 2 Technology Edition, v5.0
 * (C) Copyright IBM Corp. 1998, 2005. All Rights Reserved
 * ===========================================================================
 */

/*
 * ===========================================================================
 (C) Copyright Sun Microsystems Inc, 1992, 2004. All rights reserved.
 * ===========================================================================
 */




/*
 * Change activity:
 *
 * Reason  Date    Origin  Description
 * ------  ----    ------  ----------------------------------------------------
 * 64296  150903   corbin  New File as part of 1.4.2 Code merge
 */ 
 

import vjo.java.lang.* ;

/**
 * DontCareFieldPosition defines no-op FieldDelegate. Its
 * singleton is used for the format methods that don't take a
 * FieldPosition.
 */
class DontCareFieldPosition extends FieldPosition {
    // The singleton of DontCareFieldPosition.
    static final FieldPosition INSTANCE = new DontCareFieldPosition();

    private final Format.FieldDelegate noDelegate = new Format.FieldDelegate() {
	public void formatted(Format.Field attr, Object value, int start,
			      int end, vjo.lang.StringBuffer buffer) {
	}
	public void formatted(int fieldID, Format.Field attr, Object value,
			      int start, int end, vjo.lang.StringBuffer buffer) {
	}
    };

    private DontCareFieldPosition() {
	super(0);
    }

    Format.FieldDelegate getFieldDelegate() {
	return noDelegate;
    }
}

