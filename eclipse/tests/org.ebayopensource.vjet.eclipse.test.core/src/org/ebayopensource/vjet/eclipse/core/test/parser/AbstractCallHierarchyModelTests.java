/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test.parser;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.ast.references.SimpleReference;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.search.DLTKWorkspaceScope;

import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.IJSSourceModule;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.ebayopensource.vjet.eclipse.core.test.contentassist.TestConstants;
import org.ebayopensource.vjet.eclipse.internal.core.VjoCallHierarchyFactory;
import org.ebayopensource.vjet.eclipse.internal.core.VjoCallProcessor;

public class AbstractCallHierarchyModelTests extends AbstractVjoModelTests {
	
	protected void basicTest(String moduleName, String methodName, String[] compRes, int scope) throws ModelException {
		IJSSourceModule module = (IJSSourceModule) getSourceModule(
				getProjectName(), "src", new Path(moduleName));
		IJSType type = (IJSType) module.getTypes()[0];
		assertNotNull("Type is null", type);
		IJSMethod method = findMethodByName(type.getMethods(), methodName);
		assertNotNull("Method is null", method);
		
		HashMap<SimpleReference, IMethod> results = new HashMap<SimpleReference, IMethod>();
		VjoCallHierarchyFactory factory = new VjoCallHierarchyFactory();
		
		// add declaration of the method
//		VjoCalleeProcessor proc = (VjoCalleeProcessor) factory.createCalleeProcessor(method, null, new DLTKWorkspaceScope(module.getScriptProject().getLanguageToolkit()));
//		IMethod[] methods = proc.findMethods(methodName, 0, findLastMethodPosition(methodName, module));
//		assertNotNull("Cant find method declaration", methods);
//		assertEquals(1, methods.length);
//		putMethodDeclaration(results, methods[0]);
		
		// add references of the method
		VjoCallProcessor processor = (VjoCallProcessor) factory.createCallProcessor();
		results.putAll(processor.process(method, method, new DLTKWorkspaceScope(module.getScriptProject().getLanguageToolkit()), null));
		
		compareResults(compRes, sortByValue(results));
	}
	
	protected void compareResults(String[] compRes, Map results) {
		IMethod mtd;
		
		assertNotNull(results);
		assertEquals("Wrong result", compRes.length, results.size());
		
		Iterator it = results.entrySet().iterator();
	    for(int i=0; i<compRes.length && it.hasNext(); i++) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        mtd = (IMethod) pairs.getValue();
	        assertNotNull(mtd);
	        assertEquals("Wrong method name", compRes[i], mtd.getElementName());
	    }
	}
	
	protected void putMethodDeclaration(Map<SimpleReference, IMethod> results, IMethod method) throws ModelException {
		ISourceRange range = method.getSourceRange();
		results.put(new SimpleReference(range.getOffset(), range.getOffset()
					+ range.getLength(), ""), method);
	}
	
	 
 	
	
	public static Map sortByValue(Map map) {
		 List list = new LinkedList(map.entrySet());
		 Collections.sort(list, new Comparator() {
		      public int compare(Object o1, Object o2) {
		    	  IModelElement pr = (IModelElement) ((Map.Entry)o1).getValue();
				  IModelElement pr1 = (IModelElement) ((Map.Entry)o2).getValue();
				  return new String(pr.getElementName()).compareTo(new String(pr1
							.getElementName()));
		      }
		 });
	    
		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
		     Map.Entry entry = (Map.Entry)it.next();
		     result.put(entry.getKey(), entry.getValue());
		     }
		return result;
	}
		
	protected static int findLastMethodPosition(String methodName, IJSSourceModule module) throws ModelException {
		String content = module.getSource();

		int position = content.lastIndexOf(methodName);
		if (position >= 0) {
			return position;
		}
		return -1;

	}
	
	protected String getProjectName() {			
		return TestConstants.PROJECT_NAME_VJETPROJECT;
	}
	
}
