/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.tests.jsast.parser;

import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P4;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;
import com.ebay.junitnexgen.category.ModuleInfo;
import org.ebayopensource.vjo.lib.LibManager;


@ModuleInfo(value="DsfPrebuild",subModuleId="JsToJava")

public class JSTToolableAPITesting {


	@Test
	@Category({P4,UNIT,FAST})
	@Description("Displays Type detials on console")
	public void main() {
		URL simple1 = JSTToolableAPITesting.class.getResource("simplevjo.js.txt");
		IJstType type = new VjoParser().addLib(LibManager.getInstance().getJsNativeGlobalLib())
			.parse(null, simple1).getType();
		//type.dump();
		
		// API to acces type information
		List<? extends IJstType> imports = type.getImports();
		// import declarations
		for(IJstType t:imports){
			System.out.println("[import/vjo.needs]" +t.getName());
		}
		//TODO imports.getLibs()
		//TODO - for(String libName:)
		
		// CLASS NAME
		System.out.println("[class local name] "+ type.getAlias());
		System.out.println("[class name] "+ type.getName());
		System.out.println("[class short name] "+ type.getSimpleName());
		System.out.println("[class simple name] "+ type.getSimpleName());
	
		// Package
		System.out.println("[package] "+type.getPackage());
		
		// STATIC PROPERTIES
		List<IJstProperty> sprops = type.getStaticProperties();
		for(IJstProperty pty: sprops){
			System.out.println("[static prop] "+pty.getName().getName()  +  "  " + pty);					
		}
	}
}
