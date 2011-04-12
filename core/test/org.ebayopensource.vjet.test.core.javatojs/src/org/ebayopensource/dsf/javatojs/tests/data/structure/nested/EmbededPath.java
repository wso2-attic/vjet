/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure.nested;

public class EmbededPath {
	public class InnerClassA {
		public class InnerClass {
			public void foo(){
				InnerClassC c = new InnerClassC();
				InnerClassC.InnerClass inner = c.new InnerClass();
			}
		}
	}
	
	public class InnerClassB {
		public class InnerClass {
		}
	}
	
	public static class InnerClassC {
		public class InnerClass {
		}
	}

	public void javaTest() {
		EmbededPath outer = new EmbededPath();
		EmbededPath.InnerClassA inner = outer.new InnerClassA();
		EmbededPath.InnerClassA.InnerClass inner2 = inner.new InnerClass();
	}
}
