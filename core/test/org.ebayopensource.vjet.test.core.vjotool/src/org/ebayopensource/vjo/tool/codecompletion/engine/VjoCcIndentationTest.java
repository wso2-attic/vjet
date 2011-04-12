/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.engine;
import static com.ebay.junitnexgen.category.Category.Groups.FAST;
import static com.ebay.junitnexgen.category.Category.Groups.P1;
import static com.ebay.junitnexgen.category.Category.Groups.UNIT;

import java.net.URL;
import java.util.List;

import org.junit.Test;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import com.ebay.junitnexgen.category.Category;
import com.ebay.junitnexgen.category.ModuleInfo;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;

@Category({P1,FAST,UNIT})
@ModuleInfo(value="DsfPrebuild",subModuleId="VJET")
public class VjoCcIndentationTest extends VjoCcBaseTest{
	
	private VjoCcEngine engine = 
		new VjoCcEngine(CodeCompletionUtil.getJstParseController());
	
	@Test //Bug 5676
	public void testCaseInsensitive(){
		String js = "BugJsFiles.Bug5676";
		
		IJstType jstType = getJstType(CodeCompletionUtil.GROUP_NAME, js);
//		System.out.println(jstType);
		if(jstType != null){
			int position = lastPositionInFile("strin", jstType);
			checkProposals(jstType, position);
		}
		
	}
	
	public void checkProposals(IJstType jstType, int position){
		URL url = getSourceUrl(jstType.getName(), ".js");
		String content = VjoParser.getContent(url);
		List<IVjoCcProposalData> propList = engine.complete(CodeCompletionUtil.GROUP_NAME,jstType.getName(), 
				content, position);
//		System.out.println(propList);
	}
	
}
