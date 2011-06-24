/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/**
 * 
 */
package org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter;

import java.util.HashMap;

import org.ebayopensource.dsf.jst.declaration.JstArray;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.JstProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.AstNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.BeanStyleNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.DefaultNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstArrayNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstBlockNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstConstructorNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstExprCommonNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstIdentifierNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstMethodNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstModifiersNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstNameNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstPackageNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstPropertyNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstStmtCommonNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstTypeNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstTypeReferenceNodePrinter;
import org.ebayopensource.vjet.eclipse.internal.ui.nodeprinter.impl.JstVarsNodePrinter;
import org.eclipse.mod.wst.jsdt.core.ast.IASTNode;

/**
 * INodePrinter static factory
 * 
 * 
 * 
 */
public class NodePrinterFactory {
	private static INodePrinter defaultNodePrinter = new DefaultNodePrinter();
	private static HashMap<Class, INodePrinter> map = new HashMap<Class, INodePrinter>();

	static {
		// concrete node printer
		map.put(JstType.class, new JstTypeNodePrinter());
		map.put(JstMethod.class, new JstMethodNodePrinter());
		map.put(JstProperty.class, new JstPropertyNodePrinter());
		map.put(JstName.class, new JstNameNodePrinter());
		map.put(JstTypeReference.class, new JstTypeReferenceNodePrinter());
		map.put(JstIdentifier.class, new JstIdentifierNodePrinter());
		map.put(JstBlock.class, new JstBlockNodePrinter());
		map.put(JstVars.class, new JstVarsNodePrinter());
		map.put(JstConstructor.class, new JstConstructorNodePrinter());
		map.put(JstModifiers.class, new JstModifiersNodePrinter());
		map.put(JstPackage.class, new JstPackageNodePrinter());
		map.put(JstArray.class, new JstArrayNodePrinter());

		// common node printer
		map.put(IStmt.class, new JstStmtCommonNodePrinter());
		map.put(IExpr.class, new JstExprCommonNodePrinter());
	}

	/**
	 * static factory
	 */
	private NodePrinterFactory() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * get node printer instance regarding for the given jst node instance
	 * 
	 * @param node
	 * @return
	 */
	public static INodePrinter getNodePrinter(Object node) {
		// JUST FOR TEST
		//modify by patrick
		INodePrinter printer = new BeanStyleNodePrinter();
		if (node instanceof IASTNode) {
			printer = new AstNodePrinter(printer);
		}
		return printer;
		//end modify
		
		// //first, directly fetch the concrete node printer
		// INodePrinter nodePrinter = map.get(node.getClass());
		// if (nodePrinter != null)
		// return nodePrinter;
		//		
		// //second, if no concrete node printer, attend to feth the
		// corresponding common node printer
		// for (Iterator iterator = map.keySet().iterator();
		// iterator.hasNext();) {
		// Class type = (Class) iterator.next();
		//			
		// if (type.isAssignableFrom(node.getClass()))
		// return map.get(type);
		// }
		//		
		// //at end, the default node printer
		// return defaultNodePrinter;
	}

}
