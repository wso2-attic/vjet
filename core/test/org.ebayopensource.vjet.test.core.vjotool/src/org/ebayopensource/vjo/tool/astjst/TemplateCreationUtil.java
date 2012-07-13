/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.astjst;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.Assert;

import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.dsf.util.JavaSourceLocator;
import org.ebayopensource.vjo.tool.astjst.TypeConstants.StatementSections;
import org.ebayopensource.vjo.tool.astjst.TypeConstants.TypeSections;
import org.ebayopensource.vjo.tool.astjst.TypeConstants.Types;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcBaseTest;
import org.ebayopensource.vjo.tool.codecompletion.jsresource.CodeCompletionUtil;

import org.ebayopensource.dsf.common.FileUtils;


public class TemplateCreationUtil extends VjoCcBaseTest {
	
	boolean deleteOnExit = true;
	
	public File createAndLoadType(String statement, Types type, 
			StatementSections statementSection){
		File testFile = checkAndCreateFile();
		StringBuffer sb = new StringBuffer();
		sb.append(getTypeString(type, testFile));
		if (statementSection.equals(StatementSections.PROPS_PROPERTY)){
			sb.append(TypeConstants.propsStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.propsEnd);
		} else if (statementSection.equals(StatementSections.PROPS_METHOD)){
			sb.append(TypeConstants.propsStart);
			sb.append(TypeConstants.method1Start);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.method1End);
			sb.append(TypeConstants.propsEnd);
		} else if (statementSection.equals(StatementSections.PROTOS_PROPERTY)){
			sb.append(TypeConstants.protosStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.protosEnd);
		} else if (statementSection.equals(StatementSections.PROTOS_METHOD)){
			sb.append(TypeConstants.protosStart);
			sb.append(TypeConstants.method1Start);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.method1End);
			sb.append(TypeConstants.protosEnd);
		} else if (statementSection.equals(StatementSections.INITS)){
			sb.append(TypeConstants.propsStart);
			sb.append(TypeConstants.stringProperty);
			sb.append(TypeConstants.propsEnd);
			sb.append(TypeConstants.initsStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.initsEnd);
		} else if (statementSection.equals(StatementSections.DEFS)){
			sb.append(TypeConstants.defsStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.defsEnd);
		} else if (statementSection.equals(StatementSections.VALUES)){
			sb.append(TypeConstants.valuesStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.valuesEnd);
		} 
		
		if (statementSection.equals(StatementSections.NONE)){
			sb.append(statement);
		} else {
			sb.append("\n");
			sb.append(TypeConstants.endType);
		}
		populateAndLoadFile(sb.toString(), testFile, statementSection);
//		System.out.println(sb.toString());
		return testFile;
	}
	
	public File createAndLoadType(String statement, Types type, 
			TypeSections section){
		File testFile = checkAndCreateFile();
		StringBuffer sb = new StringBuffer();
		sb.append(getTypeString(type, testFile));
		if (section.equals(TypeSections.PROPS)){
			sb.append(TypeConstants.propsStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.propsEnd);
		} else if (section.equals(TypeSections.PROTOS)){
			sb.append(TypeConstants.protosStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.protosEnd);
		} else if (section.equals(TypeSections.INITS)){
			sb.append(TypeConstants.propsStart);
			sb.append(TypeConstants.propsEnd);
			sb.append(TypeConstants.initsStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.initsEnd);
		} else if (section.equals(TypeSections.DEFS)){
			sb.append(TypeConstants.defsStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.defsEnd);
		} else if (section.equals(TypeSections.VALUES)){
			sb.append(TypeConstants.valuesStart);
			sb.append(statement);
			sb.append("\n");
			sb.append(TypeConstants.valuesEnd);
		} 
		
		sb.append("\n");
		sb.append(TypeConstants.endType);
		
		populateAndLoadFile(sb.toString(), testFile, StatementSections.PROPS_METHOD);
//		System.out.println(sb.toString());
		return testFile;
	}

	private void populateAndLoadFile(String string, File testFile, 
			StatementSections statementSection) {
		try {
			FileUtils.writeFile(testFile.getAbsolutePath(), string, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadSingleJs(testFile.getName().replace(".js", ""), ".js");
		TypeName typeName = new TypeName(CodeCompletionUtil.GROUP_NAME,  
				testFile.getName().replace(".js", ""));
		if (!statementSection.equals(StatementSections.NONE)){
			Assert.assertTrue(getJstType(typeName) != null);
		}
	}

	private String getTypeString(Types type, File testFile) {
		StringBuffer typeStr = new StringBuffer("");
		
		if (type.toString().equals(Types.NONE.toString())){
			return typeStr.toString();
		}
		
		if (type.toString().equals(Types.CTYPE.toString())){
			typeStr.append(TypeConstants.ctypeStart);
		} else if (type.toString().equals(Types.ITYPE.toString())){
			typeStr.append(TypeConstants.itypeStart);
		} else if (type.toString().equals(Types.ETYPE.toString())){
			typeStr.append(TypeConstants.etypeStart);
		} else if (type.toString().equals(Types.ATYPE.toString())){
			typeStr.append(TypeConstants.ctypeStart);
		} else if (type.toString().equals(Types.OTYPE.toString())){
			typeStr.append(TypeConstants.ctypeStart);
		}
		
		typeStr.append(testFile.getName().replace(".js", ""));
		typeStr.append(TypeConstants.typeEnd);
		
		if (type.toString().equals(Types.ATYPE.toString())){
			typeStr.append(" abstract");
		}
		typeStr.append("\n");
		
		return typeStr.toString();
	}

	private File checkAndCreateFile() {
		File testFile;
		URL u = JavaSourceLocator.getInstance().getSourceUrl(this.getClass());
		String fileLoc = u.getFile().substring(0, u.getFile().indexOf("src/"));
		fileLoc = fileLoc + CodeCompletionUtil.ARTIFACT_FOLDER + "/" 
			+ TypeConstants.typeName;
		if (new File(fileLoc + ".js").exists()){
			fileLoc = checkFileExistance(fileLoc, 1);
		}
		testFile = new File(fileLoc+".js");
		try {
			Assert.assertTrue(testFile.createNewFile());
			if (deleteOnExit) {
				testFile.deleteOnExit();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return testFile;
	}
	
	private String checkFileExistance(String fileLoc, int counter){
		if (new File(fileLoc+counter+".js").exists()){
			counter = counter + 1;
			return checkFileExistance(fileLoc, counter);
		} else {
			return fileLoc + counter;
		}
	}

	public void setDeleteOnExit(boolean deleteOnExit) {
		this.deleteOnExit = deleteOnExit;
	}
	
}
