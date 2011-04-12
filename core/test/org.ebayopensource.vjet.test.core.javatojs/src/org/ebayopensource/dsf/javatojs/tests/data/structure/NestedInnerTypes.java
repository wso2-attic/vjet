/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.structure;

public class NestedInnerTypes {
	public static Base getBase(int i) {
		return new Base(i) {
			{
				System.out.println("Inside instance initializer");
			}
	
			public void doIt() {
				System.out.println("In Base Class  doIt()");
	
			}
			public void  testMe(){
				 BaseA b = new BaseA(){
					 public void doIt() {
							System.out.println("In Base Class  doIt()");
					 }
				 };
			}
		};
	}

	public static void main(String[] args) {
		Base base = getBase(47);
		base.doIt();
		BaseA b = new BaseA(){
			 public void doIt() {
					System.out.println("In Base Class  doIt()");
			 }
		 };
	}

}

