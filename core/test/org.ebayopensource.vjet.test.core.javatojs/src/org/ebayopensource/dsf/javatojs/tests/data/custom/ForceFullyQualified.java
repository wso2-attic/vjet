/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom;

import org.ebayopensource.dsf.javatojs.anno.AForceFullyQualified;

public class ForceFullyQualified {
	static ForceFullyQualified s_self = new ForceFullyQualified();
	ForceFullyQualified m_self = s_self;
	
	static StaticMembers s_others = new StaticMembers();
	StaticMembers m_others = s_others;
	
	@AForceFullyQualified
	private static void staticMtd() {
		String x;
		
		ForceFullyQualified l_self = s_self;
		x = zeroValue(1);	
		x = s_self.zeroValue(2);
		x = l_self.zeroValue(3);
		x = ForceFullyQualified.zeroValue(4);
		
		StaticMembers l_others = s_others;
		x = s_others.NAME;
		x = l_others.NAME;
		x = StaticMembers.NAME;
		x = s_others.getName();
		x = l_others.getName();
		x = StaticMembers.getName();
		
		x = s_others.SUPER_NAME;
		x = l_others.SUPER_NAME;
		x = StaticMembers.SUPER_NAME;
		x = s_others.getSuperName();
		x = l_others.getSuperName();
		x = StaticMembers.getSuperName();
	}
	
	@AForceFullyQualified
	private void instanceMtd() {
		String x;
		
		ForceFullyQualified l_self = s_self;
		x = zeroValue(1);	
		x = l_self.zeroValue(2);
		x = s_self.zeroValue(3);
		x = m_self.zeroValue(4);
		x = ForceFullyQualified.zeroValue(5);
		
		StaticMembers l_others = m_others;
		x = l_others.NAME;
		x = s_others.NAME;
		x = m_others.NAME;
		x = StaticMembers.NAME;
		x = l_others.getName();
		x = s_others.getName();
		x = m_others.getName();
		x = StaticMembers.getName();
		
		x = l_others.SUPER_NAME;
		x = s_others.SUPER_NAME;
		x = m_others.SUPER_NAME;
		x = StaticMembers.SUPER_NAME;
		x = l_others.getSuperName();
		x = s_others.getSuperName();
		x = m_others.getSuperName();
		x = StaticMembers.getSuperName();
	}
	
	private static String zeroValue(int t) {
		return (t < 10) ? "0" + t : "" + t ;
	}
}
