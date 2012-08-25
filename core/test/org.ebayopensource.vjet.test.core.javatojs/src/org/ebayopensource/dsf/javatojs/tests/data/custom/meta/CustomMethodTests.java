/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.data.custom.meta;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.javatojs.translate.config.MethodKey;
import org.ebayopensource.dsf.javatojs.translate.custom.CustomAttr;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.BaseCustomMetaProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomMethod;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.CustomType;
import org.ebayopensource.dsf.javatojs.translate.custom.meta.MetaDrivenCustomTranslator;
import org.ebayopensource.dsf.jst.datatype.JstReservedTypes;
import org.ebayopensource.dsf.jst.declaration.JstFactory;
import org.ebayopensource.dsf.jst.term.SimpleLiteral;
import org.ebayopensource.dsf.jst.token.IExpr;


//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class CustomMethodTests {

	@Test
	//@Category( { P1, FUNCTIONAL })
	//@Description("Test custom method translations")
	public void testGetMethod(){
		
		
		Class<?> type = A.class;
		String mtdFoo = "foo";
		String[] mtd2Params = {"int"};
		String stringName = org.ebayopensource.dsf.jsnative.global.String.class.getName();
		String[] mtd3Params = {stringName};
		String[] mtd4Params = {"int", stringName};
		String[] mtd5Params = {"int", stringName};
		JstFactory.getInstance().createJstType(stringName, true);
		
		CustomMethod cMtd1 = new CustomMethod(new MethodKey(mtdFoo, false, false)).setAttr(CustomAttr.EXCLUDED);
		CustomMethod cMtd2 = new CustomMethod(new MethodKey(mtdFoo, true, false, mtd2Params)).setAttr(CustomAttr.EXCLUDED);
		CustomMethod cMtd3 = new CustomMethod(new MethodKey(mtdFoo, true, false, mtd3Params)).setAttr(CustomAttr.EXCLUDED);
		CustomMethod cMtd4 = new CustomMethod(new MethodKey(mtdFoo, false, false, mtd4Params)).setAttr(CustomAttr.EXCLUDED);
		CustomMethod cMtd5 = new CustomMethod(new MethodKey(mtdFoo, false, true, mtd5Params)).setAttr(CustomAttr.EXCLUDED);
						
		CustomType cType = new CustomType(type)
			.addCustomMethod(cMtd1)
			.addCustomMethod(cMtd2)
			.addCustomMethod(cMtd3)
			.addCustomMethod(cMtd4)
			.addCustomMethod(cMtd5);
		
		IExpr intArg = new SimpleLiteral(int.class, JstReservedTypes.JavaPrimitive.INT, "1");
		IExpr strArg = SimpleLiteral.getStringLiteral("A");
		
		List<IExpr> args1 = Collections.emptyList();
		
		List<IExpr> args2 = new ArrayList<IExpr>();
		args2.add(intArg);
		
		List<IExpr> args3 = new ArrayList<IExpr>();
		args3.add(strArg);
		
		List<IExpr> args4 = new ArrayList<IExpr>();
		args4.add(intArg);
		args4.add(strArg);
		
		List<IExpr> args5 = new ArrayList<IExpr>();
		args5.add(intArg);
		args5.add(strArg);
		args5.add(strArg);
		
		List<IExpr> args6 = new ArrayList<IExpr>();
		args6.add(intArg);
		args6.add(strArg);
		args6.add(intArg);
		
		CustomTranslator ct = new CustomTranslator();
		
		assertEquals(null, ct.getCustomMethod(null, args1, cType.getCustomMethods(null)));
		assertEquals(null, ct.getCustomMethod("bar", args1, cType.getCustomMethods("bar")));
		assertEquals(null, ct.getCustomMethod(mtdFoo, null, cType.getCustomMethods(mtdFoo)));
		assertEquals(null, ct.getCustomMethod(mtdFoo, args6, cType.getCustomMethods(mtdFoo)));
		
		assertEquals(cMtd1, ct.getCustomMethod(mtdFoo, args1, cType.getCustomMethods(mtdFoo)));
		assertEquals(cMtd2, ct.getCustomMethod(mtdFoo, args2, cType.getCustomMethods(mtdFoo)));
//		assertEquals(cMtd3, ct.getCustomMethod(mtdFoo, args3, cType.getCustomMethods(mtdFoo)));
//		assertEquals(cMtd4, ct.getCustomMethod(mtdFoo, args4, cType.getCustomMethods(mtdFoo)));
//		assertEquals(cMtd5, ct.getCustomMethod(mtdFoo, args5, cType.getCustomMethods(mtdFoo)));
	}
	
	private static class CustomTranslator extends MetaDrivenCustomTranslator {

		public CustomTranslator(){
			super(new BaseCustomMetaProvider(){});
		}
		
		public CustomMethod getCustomMethod(
				final String mtdName, 
				final List<IExpr> args, 
				final Collection<CustomMethod> cMtds){
			return super.getCustomMethod(mtdName, args, cMtds);
		}
	}
}
