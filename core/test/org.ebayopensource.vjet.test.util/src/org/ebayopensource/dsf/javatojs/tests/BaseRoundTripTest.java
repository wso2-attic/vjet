/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests;

import static com.ebay.junitnexgen.category.Category.Groups.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;

import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.Description;
import org.ebayopensource.dsf.common.resource.ResourceUtil;
import org.ebayopensource.dsf.common.FileUtils;

public class BaseRoundTripTest {

	protected String fileName = null;
	protected String goldString = null;

	protected IJstType jstType = null;

	public BaseRoundTripTest(String inputFileName) {
		this.fileName = inputFileName;
	}

	@Before
	public void setUp() throws Exception {

		System.out.println();
		System.out.println("If *.js files are missing, ");
		System.out.println("then run this test's test suite to have them ");
		System.out.println("generated. Then hit F5 to refresh the package.");
		System.out.println("Then you should see *.js files in this package.");
		// get file
		File simple1 = new File(ResourceUtil.getResource(this.getClass(),
				fileName).getFile());
		FileInputStream fis = new FileInputStream(simple1);
		goldString = FileUtils.readStream(fis);
		jstType = new VjoParser().parse(null, simple1).getType();
	}

	@Test
	@Category( { P1, FUNCTIONAL })
	@Description("Compare vjo generated from various jst types with expected outcomes")
	public void verifyGold() {
		GeneratorCtx ctx = new GeneratorCtx(CodeStyle.PRETTY);
		ctx.getConfig().setAddCodeGenAnnotation(false);
		VjoGenerator writer = new VjoGenerator(ctx);
		writer.writeVjo(jstType);
		String generatedVjo = writer.getGeneratedText();
		System.out.println("\n===== Vjo =====\n" + generatedVjo);
		assertEquals(goldString, generatedVjo);
	}
}
