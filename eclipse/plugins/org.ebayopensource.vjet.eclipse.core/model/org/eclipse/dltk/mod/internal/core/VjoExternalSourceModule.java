/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.internal.compiler.VjoSourceElementParser;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.typespace.SourceTypeName;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.ISourceElementParserExtension;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.core.SourceParserUtil;
import org.eclipse.dltk.mod.core.WorkingCopyOwner;
import org.eclipse.dltk.mod.utils.CorePrinter;

public class VjoExternalSourceModule extends ExternalJSSourceModule implements
		IVjoSourceModule {

	private IJstType m_jstType = null;

	private boolean isConsistent = false;

	private boolean m_typeProcessed;

	private List<IScriptProblem> m_probs;

	private List<JstBlock> m_blockList;

	private JstBlock m_block;

//	private IUnitSource m_unitSource;

	public VjoExternalSourceModule(ScriptFolder parent, String name,
			WorkingCopyOwner owner, boolean readOnly, IStorage storage) {
		super(parent, name, owner, readOnly, storage);
//		this.m_unitSource = new VjoExternalUnitSource();
//		System.out.println("ExternalJSSourceModule created: " + getUnitSource().getUnitId());
	}

	@Override
	protected boolean buildStructure(OpenableElementInfo info,
			IProgressMonitor pm, Map newElements, IResource underlyingResource)
			throws ModelException {
		// Try to build it from JstType
		IJstType jstType = getJstType();
		if (jstType == null) {
			return super.buildStructure(info, pm, newElements,
					underlyingResource);
		} else {
			try {
				final JSSourceModuleElementInfo moduleInfo = (JSSourceModuleElementInfo) info;

				build(newElements, moduleInfo);

				if (DLTKCore.DEBUG_PRINT_MODEL) {
					System.out.println("Source Module Debug print:"); //$NON-NLS-1$

					CorePrinter printer = new CorePrinter(System.out);
					printNode(printer);
					printer.flush();
				}

				return moduleInfo.isStructureKnown();
			} catch (CoreException e) {
				throw new ModelException(e);
			}
		}
	}

	private void build(Map newElements,
			final JSSourceModuleElementInfo moduleInfo) throws CoreException,
			ModelException {
		// generate structure and compute syntax problems if needed
		final VjoSourceModuleStructureRequestor requestor = new VjoSourceModuleStructureRequestor(
				this, moduleInfo, newElements);

		// System.out.println("==> Parsing: " + resource.getName());
		final String natureId = VjoNature.NATURE_ID;
		if (natureId == null) {
			throw new ModelException(new ModelStatus(ModelStatus.INVALID_NAME));
		}

		final VjoSourceElementParser parser = (VjoSourceElementParser) getSourceElementParser(natureId);
		if (!isReadOnly()) {
			((ISourceElementParserExtension) parser).setScriptProject(this
					.getScriptProject());
		}

		parser.setRequestor(requestor);

		final AccumulatingProblemReporter problemReporter = getAccumulatingProblemReporter();
		parser.setReporter(problemReporter);

		SourceParserUtil.parseSourceModule(this, parser);
		if (problemReporter != null) {
			if (!problemReporter.hasErrors()) {
				StructureBuilder.build(natureId, this, problemReporter);
			}
			problemReporter.reportToRequestor();
		}

		/*
		 * ModelManager manager = ModelManager.getModelManager();
		 * manager.putInfos(this, newElements);
		 */
	}

	public IJstType getJstType() {
		if (m_jstType == null) {
			initJstType();
		}
		return m_jstType;
	}

	/**
	 * Initialize the JstType, the type comes from type space
	 */
	private void initJstType() {
		String fullName = getFullName();
		IJstType type = getJstTypeFromTypeSpace(fullName);
		if (type != null) {
			m_jstType = type;
		} else {
			m_jstType = null;
		}
	}

	private IJstType getJstTypeFromTypeSpace(String fullName) {
		SourceTypeName name = new SourceTypeName(getProjectFragment().getPath()
				.lastSegment(), fullName, new String(""));
		if (TypeSpaceMgr.getInstance().existType(name)) {
			return TypeSpaceMgr.getInstance().findType(name);
		} else {
			return null;
		}
	}

	private String getFullName() {
		String fileName = getElementName();
		String name = fileName.substring(0, fileName.indexOf("."));
		String parentName = getParent().getElementName();
		if (StringUtils.isBlankOrEmpty(parentName)) {
			return name;
		} else {
			return parentName + "." + name;
		}
	}

	public void updateScriptUnit(final JstBlock block,
			final List<JstBlock> blockList, final IJstType type,
			final List<IScriptProblem> probs) {
		this.m_block = block;
		this.m_blockList = blockList;
		this.m_jstType = type;
		this.m_probs = probs;
	}
	

	public IJstNode getNode(int startOffset) {
		return JstUtil.getLeafNode(m_jstType, startOffset, startOffset);
	}

	public JstBlock getSyntaxRoot() {
		return m_block;
	}

	public List<JstBlock> getJstBlockList() {
		return m_blockList;
	}

	public IJstType getType() {
		return m_jstType;
	}

	public List<IScriptProblem> getProblems() {
		return m_probs;
	}
	
	@Override
	public IType getType(String typeName) {
		return new VjoSourceType(this, typeName);
	}

	@Override
	public TypeName getTypeName() {
		return CodeassistUtils.getNativeTypeName(getJstType());
	}

//	@Override
//	public IUnitSource getUnitSource() {
//		return this.m_unitSource;
//	}
	
//	class VjoExternalUnitSource implements IUnitSource {
//
//		@Override
//		public String getGroupName() {
//			return ((IProjectFragment)getParent().getParent()).getPath().lastSegment();
//		}
//
//		@Override
//		public String getSource() {
//			return getSourceContents();
//		}
//
//		@Override
//		public String getUnitId() {
//			return getUnitName() + "@" + getGroupName();
//		}
//
//		@Override
//		public String getUnitName() {
//			return VjoExternalSourceModule.this.getFullName();
//		}
//		
//	}
	
}
