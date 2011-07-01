/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.parser;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ResolutionResult;
import org.ebayopensource.dsf.jstojava.controller.JstExpressionBindingResolver;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.eclipse.dltk.mod.core.DLTKCore;

public class VjoSourceElementResolver extends JstExpressionBindingResolver {


	public VjoSourceElementResolver(JstParseController controller) {
		super(controller);
	}

	@Override
	public ResolutionResult resolve(IJstType type) {
		// TODO Auto-generated method stub
		try{
			return super.resolve(type);
		}
		catch(Exception e){
			DLTKCore.error(e.toString(), e);
		}
		reportErrors(type);
		return new ResolutionResult();
	}

	// TODO fix this
	private void reportErrors(IJstType type) {

		// if(type==null || type.getSource()==null ||
		// type.getSource().getBinding()==null){
		// return;
		// }
		// IBinding binding = type.getSource().getBinding();
		// FileBinding fbinding = null;
//		URI file = TypeSpaceMgr.getInstance().getTypeToFileMap().get(
//				type.getName());

//		if (file != null) {

//			IResource resource = DLTKTypeAdaptor.getResource(file);
//			try {
//				resource.deleteMarkers(RESOLVER_PROBLEM, true,
//						IResource.DEPTH_INFINITE);
//			} catch (CoreException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			ProblemUtility.reportErrors(resource, RESOLVER_PROBLEM,
//					getErrorReporter().getErrors());
//			ProblemUtility.reportWarnings(resource, RESOLVER_PROBLEM,
//					getErrorReporter().getWarnings());
//		}
	}

}
