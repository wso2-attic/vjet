/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core;

import java.io.ByteArrayInputStream;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IBuffer;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IModelStatusConstants;
import org.eclipse.dltk.mod.core.IScriptFolder;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.util.Messages;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;

public class VjetRenameResourceElementsOperation extends RenameResourceElementsOperation {


	public VjetRenameResourceElementsOperation(IModelElement[] elements, IModelElement[] destinations, String[] newNames, boolean force) {
		super(elements, destinations, newNames, force);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Adapts an IBuffer to IDocument
	 */
	public class DocumentAdapter extends Document {

		private IBuffer buffer;

		public DocumentAdapter(IBuffer buffer) {
			super(buffer.getContents());
			this.buffer = buffer;
		}

		public void set(String text) {
			super.set(text);
			this.buffer.setContents(text);
		}

		public void replace(int offset, int length, String text) throws BadLocationException {
			super.replace(offset, length, text);
			this.buffer.replace(offset, length, text);
		}

	}

	

	@Override
	protected void processSourceModuleResource(ISourceModule source, ScriptFolder dest) throws ModelException {

		String newCUName = getNewNameFor(source);
		String destName = (newCUName != null) ? newCUName : source.getElementName();
		TextEdit textEdit = updateContent(source, dest, newCUName); // null
		// if unchanged
		// TODO (frederic) remove when bug 67606 will be fixed (bug 67823)
		// store encoding (fix bug 66898)
		IFile sourceResource = (IFile) source.getResource();

		String sourceEncoding = null;
		try {
			if (sourceResource != null) {
				sourceEncoding = sourceResource.getCharset(false);
			}
		} catch (CoreException ce) {
			// no problem, use default encoding
		}
		// end todo
		// copy resource
		IContainer destFolder = (IContainer) dest.getResource(); // can be an
		// IFolder
		// or an
		// IProject
		IFile destFile = destFolder.getFile(new Path(destName));
		// EBAY - START MOD
		// SourceModule destCU = new SourceModule(dest, destName,
		// DefaultWorkingCopyOwner.PRIMARY);
		ISourceModule destCU = dest.createSourceModule(destName, DefaultWorkingCopyOwner.PRIMARY);
		// EBAY -- STOP MOD
		if (sourceResource == null || !destFile.equals(sourceResource)) {
			try {
				if (!destCU.isWorkingCopy()) {
					if (destFile.exists()) {
						if (this.force) {
							// we can remove it
							deleteResource(destFile, IResource.KEEP_HISTORY);
							destCU.close(); // ensure the in-memory buffer for
							// the dest CU is closed
						} else {
							// abort
							throw new ModelException(new ModelStatus(IModelStatusConstants.NAME_COLLISION, Messages.bind(Messages.status_nameCollision,
									destFile.getFullPath().toString())));
						}
					}
					int flags = this.force ? IResource.FORCE : IResource.NONE;
					if (this.isMove()) {
						flags |= IResource.KEEP_HISTORY;
						if (sourceResource != null) {
							sourceResource.move(destFile.getFullPath(), flags, getSubProgressMonitor(1));
						} else {
							if (DLTKCore.DEBUG) {
								System.err.println("TODO: Add correct status message here..."); //$NON-NLS-1$
							}
							throw new ModelException(new ModelStatus(IModelStatusConstants.NAME_COLLISION, Messages.bind(Messages.status_invalidResource,
									destFile.getFullPath().toString())));
						}
					} else {
						// if (rewrite != null) flags |= IResource.KEEP_HISTORY;
						if (sourceResource == null) {
							ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
							destFile.create(bais, IResource.FORCE, getSubProgressMonitor(1));
							destCU.getBuffer().setContents(source.getSourceAsCharArray());
							destCU.save(getSubProgressMonitor(1), true);
						} else {
							sourceResource.copy(destFile.getFullPath(), flags, getSubProgressMonitor(1));
						}
					}
					this.setAttribute(HAS_MODIFIED_RESOURCE_ATTR, TRUE);
				} else {
					destCU.getBuffer().setContents(source.getBuffer().getContents());
				}
			} catch (ModelException e) {
				throw e;
			} catch (CoreException e) {
				throw new ModelException(e);
			}
			// update new resource content
			if (textEdit != null) {
				boolean wasReadOnly = destFile.isReadOnly();
				try {
					saveContent(dest, destName, sourceEncoding, destFile, textEdit);
				} catch (CoreException e) {
					if (e instanceof ModelException)
						throw (ModelException) e;
					throw new ModelException(e);
				} finally {
					Util.setReadOnly(destFile, wasReadOnly);
				}
			}
			// register the correct change deltas
			prepareDeltas(source, destCU, isMove());
			if (newCUName != null) {
				// the main type has been renamed
				if (DLTKCore.DEBUG) {
					System.err.println("TODO: Add remove extensions here..."); //$NON-NLS-1$
				}
				String oldName = /* Util.getNameWithoutScriptLikeExtension( */source.getElementName();// );
				String newName = /* Util.getNameWithoutScriptLikeExtension( */newCUName;// )
				// ;
				prepareDeltas(source.getType(oldName), destCU.getType(newName), isMove());
			}
		} else {
			if (!this.force) {
				throw new ModelException(new ModelStatus(IModelStatusConstants.NAME_COLLISION, Messages.bind(Messages.status_nameCollision, destFile
						.getFullPath().toString())));
			}
			// update new resource content
			// in case we do a saveas on the same resource we have to simply
			// update the contents
			// see http://dev.eclipse.org/bugs/show_bug.cgi?id=9351
			try {
				if (textEdit != null) {
					saveContent(dest, destName, sourceEncoding, destFile, textEdit);
				}
			} catch (CoreException e) {
				if (e instanceof ModelException)
					throw (ModelException) e;
				throw new ModelException(e);
			}
		}
	
	}


	private void saveContent(ScriptFolder dest, String destName, String sourceEncoding, IFile destFile, TextEdit textEdits) throws ModelException {
		try {
			// TODO (frederic) remove when bug 67606 will be fixed (bug 67823)
			// fix bug 66898
			if (sourceEncoding != null)
				destFile.setCharset(sourceEncoding, this.progressMonitor);
			// end todo
		} catch (CoreException ce) {
			// use no encoding
		}
		// when the file was copied, its read-only flag was preserved ->
		// temporary set it to false
		// note this doesn't interfer with repository providers as this is a new
		// resource that cannot be under
		// version control yet
		Util.setReadOnly(destFile, false);
		ISourceModule destCU = dest.getSourceModule(destName);

		IDocument document = getDocument(destCU);
		// TextEdit edits = rewrite.rewriteAST(document, null);
		try {
			textEdits.apply(document);
		} catch (BadLocationException e) {
			throw new ModelException(e, IModelStatusConstants.INVALID_CONTENTS);
		}
		destCU.save(getSubProgressMonitor(1), this.force);
	}

	/*
	 * Returns the existing document for the given cu, or a DocumentAdapter if
	 * none.
	 */
	protected IDocument getDocument(ISourceModule cu) throws ModelException {
		IBuffer buffer = cu.getBuffer();
		if (buffer instanceof IDocument)
			return (IDocument) buffer;
		return new DocumentAdapter(buffer);
	}

	private TextEdit updateContent(ISourceModule cu, ScriptFolder dest, String newName) throws ModelException {
		String packagePattern="\\"+IScriptFolder.PACKAGE_DELIMETER_STR;
		String[] currPackageName = ((IScriptFolder) cu.getParent()).getElementName().split(packagePattern);
		String[] destPackageName = dest.getElementName().split(packagePattern);
		if (cu instanceof VjoSourceModule) {
			VjoSourceModule vjoSourceModule = (VjoSourceModule) cu;
			// vjoSourceModule.getJstType().getSource().getStartOffSet();

			if (equalArraysOrNull(currPackageName, destPackageName) && newName == null) {
				return null; // nothing to change
			} else {
				// ensure cu is consistent (noop if already consistent)
				cu.makeConsistent(this.progressMonitor);
				// this.parser.setSource(cu);
				// CompilationUnit astCU = (CompilationUnit)
				// this.parser.createAST(this.progressMonitor);
				// AST ast = astCU.getAST();
				// ASTRewrite rewrite = ASTRewrite.create(ast);

				return updateTypeName(cu, vjoSourceModule.getJstType(), cu.getElementName(), newName);

				// support when move is supported
				// updatePackageStatement(vjoSourceModule.getJstType(),
				// destPackageName);
			}
		}
		return null;

	}

	private boolean equalArraysOrNull(Object[] a, Object[] b) {
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;

		int len = a.length;
		if (len != b.length)
			return false;
		// walk array from end to beginning as this optimizes package name cases
		// where the first part is always the same (e.g. org.eclipse.jdt)
		for (int i = len - 1; i >= 0; i--) {
			if (a[i] == null) {
				if (b[i] != null)
					return false;
			} else {
				if (!a[i].equals(b[i]))
					return false;
			}
		}
		return true;
	}

	private String getNameWithoutJavaScriptLikeExtension(String name) {
		if (name.lastIndexOf(".") != -1)
			return name.substring(0, name.lastIndexOf("."));
		return name;
	}

	/**
	 * Renames the main type in <code>cu</code>.
	 * 
	 * @param document
	 */
	private TextEdit updateTypeName(ISourceModule cu, IJstType astCU, String oldName, String newName) throws ModelException {
		if (newName != null) {
			String oldTypeName = getNameWithoutJavaScriptLikeExtension(oldName);
			String newTypeName = getNameWithoutJavaScriptLikeExtension(newName);
			// AST ast = astCU.getAST();
			// update main type name
			IType[] types = cu.getTypes();
			for (int i = 0, max = types.length; i < max; i++) {
				IType currentType = types[i];
				if (currentType.getElementName().equals(oldTypeName)) {
					// AbstractTypeDeclaration typeNode =
					// (AbstractTypeDeclaration) ((JavaElement)
					// currentType).findNode(astCU);
					if (astCU != null) {
						// rename type
						int start = astCU.getSource().getStartOffSet();
						int length = astCU.getSource().getLength();
						String matchText = cu.getBuffer().getText(start, length);
						if (oldTypeName.equals(matchText)) {
							ReplaceEdit replaceEdit = new ReplaceEdit(start, length, newTypeName);
							// computation of the new source code
							return replaceEdit;
						}

						// rewriter.replace(typeNode.getName(),
						// ast.newSimpleName(newTypeName), null);
						// do not need to rename constructors
						// Iterator bodyDeclarations =
						// typeNode.bodyDeclarations().iterator();
						// while (bodyDeclarations.hasNext()) {
						// Object bodyDeclaration = bodyDeclarations.next();
						// if (bodyDeclaration instanceof MethodDeclaration)
						// {
						// MethodDeclaration methodDeclaration =
						// (MethodDeclaration) bodyDeclaration;
						// if (methodDeclaration.isConstructor()) {
						// SimpleName methodName =
						// methodDeclaration.getName();
						// if
						// (methodName.getIdentifier().equals(oldTypeName))
						// {
						// rewriter.replace(methodName,
						// ast.newSimpleName(newTypeName), null);
						// }
						// }
						// }
						// }
					}
				}
			}
		}
		return null;
	}

	// private void updatePackageStatement(IJstType astCU, String[] pkgName)
	// throws ModelException {
	// boolean defaultPackage = pkgName.length == 0;
	// AST ast = astCU.getAST();
	// if (defaultPackage) {
	// // remove existing package statement
	// if (astCU.getPackage() != null)
	// rewriter.set(astCU, CompilationUnit.PACKAGE_PROPERTY, null, null);
	// } else {
	// org.eclipse.jdt.core.dom.PackageDeclaration pkg = astCU.getPackage();
	// if (pkg != null) {
	// // rename package statement
	// Name name = ast.newName(pkgName);
	// rewriter.set(pkg, PackageDeclaration.NAME_PROPERTY, name, null);
	// } else {
	// // create new package statement
	// pkg = ast.newPackageDeclaration();
	// pkg.setName(ast.newName(pkgName));
	// rewriter.set(astCU, CompilationUnit.PACKAGE_PROPERTY, pkg, null);
	// }
	// }
	// }

}
