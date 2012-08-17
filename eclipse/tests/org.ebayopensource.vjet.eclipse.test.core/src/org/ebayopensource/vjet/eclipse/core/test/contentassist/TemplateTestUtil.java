/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.contentassist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.mod.ui.templates.ScriptTemplateProposal;
import org.eclipse.dltk.mod.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.mod.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.source.ISourceViewer;

import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.core.test.FixtureUtils;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoTemplateCompletionProposalComputer;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;

public class TemplateTestUtil extends AbstractVjoModelTests {
	
	public static final String wrong = "<<Wrong Position>>";
	public static final String correct = "<<Correct Position>>";
	
	public void templateBasedTest(String template, String [] names, 
			String project, String jsFile) throws Exception {
		System.out.println(" ******** Template based test start for template : " + template + " ********");
		List<TestFileInfo> testList = new ArrayList<TestFileInfo>();
		try {
		
		String finalStr = getSourceWorkspacePath().getParent() 
			+ "/projects/" + project + "/" + "src" + "/" + jsFile;

		File baseFile = new File (finalStr);
		
		String baseFilePath = baseFile.getAbsolutePath(); 
		String contents = getFileContents(baseFilePath);
		
		List<String> list = new ArrayList<String>();
		filterWrongPositions(list, contents);
		filterCorrectPositions(list, contents);
		testList = createFiles(list, baseFile, template);
		
		testIt(testList, names, project, template);
		} finally {
			deleteFiles(testList);
			System.out.println(" ******** Template based test end for template : " + template + " ********");
		}
		
	}
	
	private void testIt(List<TestFileInfo> testList, String [] names, 
			String project, String template) throws ModelException{
		FixtureManager m_fixtureManager = FixtureUtils.setUpFixture(this);
		List<TestResultInfo> resultsInfo = new ArrayList<TestResultInfo>();
		try {
			for (TestFileInfo obj : testList) {
				TestResultInfo info = null;
				if (obj.getTestType() != null && obj.getTestType().equals(wrong)){
					info = wrongPositionTestcase(obj.getTestFile(), obj.pos, names, project, template);
				} else if (obj.getTestType() != null && obj.getTestType().equals(correct)){
					info = correctPositionTestcase(obj.getTestFile(), obj.pos, names, project, template);
				}
				if (info != null) {
					resultsInfo.add(info);
				}
			}
			
			if (resultsInfo.size() > 0){
				System.err.println(" The Template Based Tests failed!" );
				StringBuffer sb = new StringBuffer("\nTotal test failures are : " + resultsInfo.size() + "\n");
				for (TestResultInfo info : resultsInfo){
					sb.append(info.toString());
					sb.append("\n");
				}
				throw new AssertionFailedError(sb.toString());
			}
		} finally {
			if (m_fixtureManager != null) {
				m_fixtureManager.tearDown();
			}
		}
	}
	
	private TestResultInfo wrongPositionTestcase(File f, int pos, String [] names, 
			String project, String template)throws ModelException {
		
		List<ScriptTemplateProposal> completions = findCompletions(f, pos, project);
		Collections.sort(completions, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				ScriptTemplateProposal pr = (ScriptTemplateProposal) arg0;
				ScriptTemplateProposal pr1 = (ScriptTemplateProposal) arg1;
				return new String(pr.getPattern()).compareTo(new String(pr1
						.getPattern()));
			}
		});
		
		LinkedList<String> resultNames = new LinkedList<String>();
		for (ScriptTemplateProposal proposal : completions) {
			resultNames.add(String.valueOf(proposal.getDisplayString()).trim());
		}
		
		TestResultInfo info = null;
		for (String string : names) {
			if (resultNames.contains(string)) {
				String js = getFileContents(f.getAbsolutePath());
				js = js.substring(0, pos+1) + "<<******************** I FAILED HERE ******************** >>" 
					+ js.substring(pos+1);
				String msg = "Results should not contain " + string + 
					" at position " + pos	+ " in proposals in list : " + resultNames;
				info = new TestResultInfo(pos, js, wrong, template, msg, resultNames);
			}
//			assertFalse("Results should not contain " + string + " at position " + pos 
//					+ " in proposals in list : " + resultNames, resultNames.contains(string));
		}
		return info;
	}
	
	private TestResultInfo correctPositionTestcase(File f, int pos, String [] names, 
			String project, String template)throws ModelException {
		
		List<ScriptTemplateProposal> completions = findCompletions(f, pos, project);
		Collections.sort(completions, new Comparator() {
			public int compare(Object arg0, Object arg1) {
				ScriptTemplateProposal pr = (ScriptTemplateProposal) arg0;
				ScriptTemplateProposal pr1 = (ScriptTemplateProposal) arg1;
				return new String(pr.getPattern()).compareTo(new String(pr1
						.getPattern()));
			}
		});
		
		LinkedList<String> resultNames = new LinkedList<String>();
		for (ScriptTemplateProposal proposal : completions) {
			resultNames.add(String.valueOf(proposal.getDisplayString()).trim());
		}
		TestResultInfo info = null;
		for (String string : names) {
			if (!resultNames.contains(string)) {
				String js = getFileContents(f.getAbsolutePath());
				js = js.substring(0, pos) + "<<******************** I FAILED HERE ******************** >>" 
					+ js.substring(pos);
				String msg = "Results should contain " + string + 
					" at position " + pos	+ " in proposals in list : " + resultNames;
				info = new TestResultInfo(pos, js, correct, template, msg, resultNames);
			}
//			assertTrue("Results should contains " + string + " at position " + pos 
//					+ " in proposals in list : " + resultNames,	resultNames.contains(string));
		}
		
		return info;
		
	}
	
	private List<ScriptTemplateProposal> findCompletions(File f, int pos, 
			String project) throws ModelException {
		
		String testJsName = f.getParentFile().getName() + "/" + f.getName();
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				project, "src", new Path(testJsName));
//		System.out.println("**********  ++++++++++++ source start **********  ++++++++++++ ");
//		System.out.println(module.getSource());
//		System.out.println("**********  ++++++++++++ source end **********  ++++++++++++ ");
		assertNotSame("Invalid file content, cant find position", -1, pos);
		VjoTemplateCompletionProposalComputer computer = 
			new VjoTemplateCompletionProposalComputer();
		ScriptEditor vEditor = (ScriptEditor) getEditor(module);
		ISourceViewer viewer = vEditor.getViewer();

		ScriptContentAssistInvocationContext context = new ScriptContentAssistInvocationContext(
				viewer, pos, vEditor, VjoNature.NATURE_ID) {
			protected CompletionProposalLabelProvider createLabelProvider() {
				return null;
			}
		};

		List<ScriptTemplateProposal> completions = computer
				.computeCompletionProposals(context, null);
		
		return completions;
	}
	
	private List<TestFileInfo> createFiles(List<String> list, 
			File baseFile, String template) throws Exception {
		List<TestFileInfo> testFileInfoList = new ArrayList<TestFileInfo>();
		String name = baseFile.getName();
		String typeName = name.substring(0, name.lastIndexOf("."));
		int counter = 1;
		for (String content : list){
			char [] chars = template.toCharArray();
			int position = getPosition(content);
			File newFile = null;
			String tempString = "";
			for (char c : chars){
				tempString = tempString + String.valueOf(c);
				String newString = getNewString(content, tempString, typeName, counter, template);
				newFile = createFile(baseFile.getParentFile(), typeName+template+counter, newString);
				int occur = findOccuranceOfType(content, typeName, position, counter, template);
				
				TestFileInfo testFileInfo = null;
				if (content.contains(wrong)){
					testFileInfo = new TestFileInfo(
							position + tempString.length() + occur, newFile, wrong);
				} else if (content.contains(correct)){
					testFileInfo = new TestFileInfo(
							position + tempString.length() + occur, newFile, correct);
				}
				testFileInfoList.add(testFileInfo);
				counter = counter + 1;
			}
		}
		
		return testFileInfoList;
	}
	
	private void deleteFiles(List<TestFileInfo> testFiles){
		for (TestFileInfo obj : testFiles){
			File f = obj.getTestFile();
			if (f != null && f.exists())
				f.delete();
		}
	}
	
	private String getNewString(String content, String tempStr, 
			String type, int counter, String template){
		String newString = "";
		if (content.contains(wrong)){
			newString = content.replace(wrong, tempStr + " ");
		} else if (content.contains(correct)){
			newString = content.replace(correct, tempStr + " ");
		}
		newString = newString.replace(type, type+template+counter);
		return newString;
	}
	
	private int getPosition(String content){
		int position = -1;
		if (content.contains(wrong)){
			position = content.indexOf(wrong);
		} else if (content.contains(correct)){
			position = content.indexOf(correct);
		}
		return position;
	}
	
	private int findOccuranceOfType(String content, String typeName, 
			int position, int counter, String template) {
		content = content.substring(0, position);
		int typeOccur = content.split(typeName).length - 1;
		if (counter < 10)
			return typeOccur * (template.length() + 1);
		else if (counter < 100 && counter >=10)
			return typeOccur * (template.length() + 2);
		else if (counter < 1000 && counter >= 100 )
			return typeOccur * (template.length() + 3);
		else
			return typeOccur * (template.length() + 4);
	}
	
	private File createFile(File parent, 
			String child, String contents) throws Exception {
		File newFile = new File (parent, child+".js");
		if (newFile.exists())
			newFile.delete();
		
		newFile.createNewFile();
//		newFile.deleteOnExit();
		PrintWriter outputStream 
			= new PrintWriter(new FileWriter(newFile.getAbsolutePath()));
		outputStream.println(contents);
		outputStream.close();
		outputStream = null;
//		addPath(newFile.getAbsolutePath());
		return newFile;
	}
	
	private static void addPath(String s) throws Exception {
		File f = new File(s);
		URL u = f.toURL();
		URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader
				.getSystemClassLoader();
		Class urlClass = URLClassLoader.class;
		Method method = urlClass.getDeclaredMethod("addURL",
				new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(urlClassLoader, new Object[] { u });
	}  
	
	private String getFileContents(String file){
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader inputStream = new BufferedReader(new FileReader(file));
			String inLine = "";
			while ((inLine = inputStream.readLine()) != null){
				sb.append(inLine);
				sb.append("\n");
			}
			inputStream.close();
		} catch (Exception ex){
			ex.printStackTrace();
		}
//		System.out.println(sb.toString());
		return sb.toString();
	}
	
	private void filterWrongPositions(List<String> list, String content){
		content = content.replace(correct, "");
		
		String [] str = content.split(wrong);
		String tempStr = "";
		for (String st : str){
			if (content.endsWith(st))
				continue;
			else
				tempStr = tempStr + st + wrong;
			String finalStr = content.substring(tempStr.length());
			finalStr = tempStr.replace(wrong, "") + wrong + finalStr.replace(wrong, "");
			list.add(finalStr);
//			System.out.println("************************** Start ************************");
//			System.out.println(finalStr);
//			System.out.println("************************** End ************************");
		}
	}
	
	private void filterCorrectPositions(List<String>list, String content){
		content = content.replace(wrong, "");
		
		String [] str = content.split(correct);
		String tempStr = "";
		for (String st : str){
			if (content.endsWith(st)) 
				continue;
			else
				tempStr = tempStr + st + correct;
			String finalStr = content.substring(tempStr.length());
			finalStr = tempStr.replace(correct, "") + correct + finalStr.replace(correct, "");
			list.add(finalStr);
//			System.out.println("************************** Start ************************");
//			System.out.println(finalStr);
//			System.out.println("************************** End ************************");
		}
	}
	
	private class TestFileInfo {
		private int pos = -1;
		private File testFile = null;
		private String testType = "";
		
		public TestFileInfo(int pos, File testFile, String testType) {
			this.pos = pos;
			this.testFile = testFile;
			this.testType = testType;
		}
		
		public int getPos() {
			return pos;
		}
		public File getTestFile() {
			return testFile;
		}
		public String getTestType() {
			return testType;
		}
	}
	
	private class TestResultInfo {
		private int pos = -1;
		private String testFile = "";
		private String testType = "";
		private String template = "";
		private String msg = "";
		LinkedList<String> resultNames;
		
		public TestResultInfo(int pos, String testFile, String testType,
				String template, String msg, LinkedList<String> resultNames) {
			super();
			this.pos = pos;
			this.testFile = testFile;
			this.testType = testType;
			this.template = template;
			this.msg = msg;
			this.resultNames = resultNames;
		}
		public int getPos() {
			return pos;
		}
		public String getTestFile() {
			return testFile;
		}
		public String getTestType() {
			return testType;
		}
		public String getTemplate() {
			return template;
		}
		public String getMsg() {
			return msg;
		}
		public LinkedList<String> getResultNames() {
			return resultNames;
		}
		public String toString() {
			return " Message : " + msg + "\n" 
				 + " Template : " + template + "\n"
				 + " Test Type : " + testType + "\n"
				 + " Position : " + pos + "\n"
				 + " Code Proposals : " + resultNames + "\n"
				 + " File Contents : " + testFile;
		}
	}
}
