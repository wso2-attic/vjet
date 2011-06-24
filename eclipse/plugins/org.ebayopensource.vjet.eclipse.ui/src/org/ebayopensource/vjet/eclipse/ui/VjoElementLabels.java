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
package org.ebayopensource.vjet.eclipse.ui;

import org.ebayopensource.vjet.eclipse.core.IImportContainer;
import org.ebayopensource.vjet.eclipse.core.IImportDeclaration;
import org.ebayopensource.vjet.eclipse.core.IJSMethod;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IPackageDeclaration;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.IJSInitializer;
import org.eclipse.dltk.mod.internal.core.JSSourceFieldElementInfo;
import org.eclipse.dltk.mod.internal.core.JSSourceMethod;
import org.eclipse.dltk.mod.internal.core.JSSourceMethodElementInfo;
import org.eclipse.dltk.mod.internal.core.SourceField;
import org.eclipse.dltk.mod.internal.core.VjoLocalVariable;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;

/**
 * 
 * 
 */
public class VjoElementLabels extends ScriptElementLabels {
	private final static long QUALIFIER_FLAGS = P_COMPRESSED | USE_RESOLVED;
	
	@Override
	public void getElementLabel(IModelElement element, long flags,
			StringBuffer buf) {

		int type = element.getElementType();

		switch (type) {
		case IImportContainer.ELEMENT_TYPE:
			buf.append("import declarations");
			break;
		case IImportDeclaration.ELEMENT_TYPE:
			buf.append(element.getElementName());
			break;
		case IJSInitializer.ELEMENT_TYPE:
			buf.append("{...}");
			break;
		case IModelElement.FIELD:
			if (element instanceof VjoLocalVariable) {
				getLocalVariableLabel((VjoLocalVariable) element, flags, buf);
			} else if (element instanceof SourceField) {
				getJsFieldLabel((SourceField) element,flags, buf);
			}

			break;
			
		case IModelElement.METHOD:
			if (element instanceof IJSMethod) {
				getJsMethodLabel((IJSMethod) element, flags, buf);
			} 

			break;
			// add by Jack
		case IModelElement.PROJECT_FRAGMENT: {
			IProjectFragment root = (IProjectFragment) element;
			if (root.isExternal() && Util.isNativeCacheDir(root.getPath())) {
				if (root.isArchive() || Util.isNativeCacheDir(root.getPath())) {
					buf.append(root.getPath().lastSegment() + "["+ VjetPlugin.DES_VJET_SDK + "]");
					break;
				} 
			}
		}
		// end add
		default:
			super.getElementLabel(element, flags, buf);
		}

//		if (element instanceof IField || element instanceof IMethod) {
//
//			IType declaredType = ((IMember) element).getDeclaringType();
//
//			if (showFullyQualifiedNames()) {
//				buf.append(" - ");
//				buf.append(declaredType.getFullyQualifiedName().replaceAll("/", "."));
//			}
//
//		}

	}
	
	
	private void getJsFieldLabel(SourceField field, long flags, StringBuffer buf) {
		try {

			// qualification
			if (getFlag(flags, F_FULLY_QUALIFIED)) {
				getTypeLabel(field.getDeclaringType(), T_FULLY_QUALIFIED | (flags & QUALIFIER_FLAGS), buf);
				buf.append('.');
			}
			
			getFieldNameAndType(field,buf);

			// post qualification
			if (getFlag(flags, F_POST_QUALIFIED)) {
				buf.append(CONCAT_STRING);
				getTypeLabel(field.getDeclaringType(), T_FULLY_QUALIFIED | (flags & QUALIFIER_FLAGS), buf);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// JavaPlugin.log(e); // NotExistsException will not reach this
			// point
		}
	}
	
	
	private void getFieldNameAndType(SourceField field, StringBuffer buf) {
		buf.append(field.getElementName());
		buf.append(" : ");

		SourceField sourceField = (SourceField) field;
		JSSourceFieldElementInfo elementInfo;
		try {
			elementInfo = (JSSourceFieldElementInfo) sourceField
					.getElementInfo();
			buf.append(elementInfo.getType());
		} catch (ModelException e) {
			buf.append("error determining type");
		}
		
		
		
	}
	
	
	/**
	 * this method is copied from org.eclipse.dltk.mod.ui.ScriptElementLabels.getMethodLabel, and fix for bug 2637
	 * @param method
	 * @param flags
	 * @param buf
	 */
	private void getJsMethodLabel(IJSMethod method, long flags, StringBuffer buf) {

		try {
			// qualification
			if (getFlag(flags, M_FULLY_QUALIFIED)) {
				IType type = method.getDeclaringType();
				if (type != null) {
					getTypeLabel(type, T_FULLY_QUALIFIED
							| (flags & QUALIFIER_FLAGS), buf);
					buf.append(getTypeDelimiter());
				}
			}

			buf.append(method.getElementName());

			// parameters
			buf.append('(');
			if (getFlag(flags, M_PARAMETER_TYPES | M_PARAMETER_NAMES)) {
				// TODO: Add type detection calls from here.
				String[] names = null;
				String[] initializers = null;
				int nParams = 0;
//				if (getFlag(flags, M_PARAMETER_NAMES) && method.exists()) {
//					names = method.getParameters();
//					initializers = method.getParameterInitializers();
//					nParams = names.length;
//				}

				//fix 2637
				if (getFlag(flags, M_PARAMETER_TYPES) && method.exists()) {
					names = method.getParameterTypes();
					initializers = method.getParameterInitializers();
					nParams = names.length;
				}
				
				for (int i = 0; i < nParams; i++) {
					if (i > 0) {
						buf.append(COMMA_STRING);
					}

					if (names != null) {
						buf.append(names[i]);
						
						JSSourceMethodElementInfo elementInfo = (JSSourceMethodElementInfo)((JSSourceMethod)method).getElementInfo();
						if (elementInfo.getIsVariables()[i])
							buf.append("..."); 
					}

					if (getFlag(flags, M_PARAMETER_INITIALIZERS)
							&& initializers != null && initializers[i] != null) {// &&
						// initializers[i].length()
						// > 0
						// ) {
						buf.append("=\"" + initializers[i] + "\""); //$NON-NLS-1$ //$NON-NLS-2$
					}
				}
			} else {
				String[] params = method.getParameters();
				if (params.length > 0) {
					buf.append(ELLIPSIS_STRING);
				}
			}
			buf.append(')');

			// post qualification
			if (getFlag(flags, M_POST_QUALIFIED)) {
				IType declaringType = method.getDeclaringType();
				if (declaringType != null) {
					buf.append(CONCAT_STRING);
					getTypeLabel(declaringType, T_FULLY_QUALIFIED
							| (flags & QUALIFIER_FLAGS), buf);
				}
			}

			// TODO: Add return type method flag detection here,
//			 if( getFlag( flags, M_APP_RETURNTYPE ) && method.exists( ) ) {
			if(method.getReturnType()!=null){
				 buf.append(" : ");
				 buf.append(method.getReturnType());
			}
				 
//			 }
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}




//	protected boolean showFullyQualifiedNames() {
//		return false;
//	}


//	private IProjectFragment findProjectFragment(IModelElement element) {
//		while (element != null
//				&& element.getElementType() != IModelElement.PROJECT_FRAGMENT) {
//			element = element.getParent();
//		}
//		return (IProjectFragment) element;
//	}


	
	@Override
	protected void getTypeLabel(IType type, long flags, StringBuffer buf) {
		if(type==null)
			return;

//		IScriptProject project = type.getScriptProject();

		//ebay comment
//		if (getFlag(flags, T_FULLY_QUALIFIED)) {
//			
//				IResource resource = type.getResource();
//				IProjectFragment pack = null;
//				if (resource != null) {
//					pack = project.getProjectFragment(resource);
//				} else {
//					pack = findProjectFragment(type);
//				}
//				if (pack == null) {
//					pack = findProjectFragment(type);
//				}
//				getScriptFolderLabel(pack, (flags & QUALIFIER_FLAGS), buf);
//				
//			
//		}
		
		//ebay add start, treat like a java package but project fragment
		if (getFlag(flags, T_FULLY_QUALIFIED)) {
			IPackageDeclaration pkg=getPackage(type);
			if(pkg!=null){
				//IPackageFragment pack= type.getPackageFragment();
				if (!pkg.getElementName().equals("")) {
					buf.append(pkg.getElementName());
				}else{
					buf.append(DEFAULT_PACKAGE);
				}
				buf.append('.');
			}		
		}//ebay add end

		if (getFlag(flags, T_FULLY_QUALIFIED | T_CONTAINER_QUALIFIED)) {
			IModelElement elem = type.getParent();
			IType declaringType = (elem instanceof IType) ? (IType) elem : null;
			if (declaringType != null) {
				getTypeLabel(declaringType, T_CONTAINER_QUALIFIED
						| (flags & QUALIFIER_FLAGS), buf);
				buf.append(getTypeDelimiter());
			}else{
				int parentType = type.getParent().getElementType();			
				if (parentType == IModelElement.METHOD
						|| parentType == IModelElement.FIELD) { // anonymous
					// or
					// local
					getElementLabel(type.getParent(),
							(parentType == IModelElement.METHOD ? M_FULLY_QUALIFIED
									: F_FULLY_QUALIFIED)
									| (flags & QUALIFIER_FLAGS), buf);
					buf.append(getTypeDelimiter());
				}
				
			}
		}

		String typeName = type.getElementName();
		if (typeName.length() == 0) { // anonymous
			try {
				if (type.getParent() instanceof IField) {
					typeName = '{' + ELLIPSIS_STRING + '}';
				} else {
					String[] superNames = type.getSuperClasses();
					if (superNames != null) {
						int count = 0;
						typeName += DECL_STRING;
						for (int i = 0; i < superNames.length; ++i) {

							if (count > 0) {
								typeName += COMMA_STRING + " "; //$NON-NLS-1$
							}
							typeName += superNames[i];
							count++;
						}
					}
				}
			} catch (ModelException e) {
				// ignore
				typeName = ""; //$NON-NLS-1$
			}
		}

		buf.append(typeName);

		// post qualification
		if (getFlag(flags, T_POST_QUALIFIED)) {
			IModelElement elem = type.getParent();
			IType declaringType = (elem instanceof IType) ? (IType) elem : null;
			if (declaringType != null) {
				buf.append(CONCAT_STRING);
				getTypeLabel(declaringType, T_FULLY_QUALIFIED | (flags & QUALIFIER_FLAGS), buf);
				int parentType = type.getParent().getElementType();
				if (parentType == IModelElement.METHOD || parentType == IModelElement.FIELD) { // anonymous
					// or
					// local
					buf.append(getTypeDelimiter());
					getElementLabel(type.getParent(), 0, buf);
				}
			} else {
				// ebay change
				int parentType = type.getParent().getElementType();
				if (parentType == IModelElement.METHOD || parentType == IModelElement.FIELD) { // anonymous
					// or
					// local
					buf.append(CONCAT_STRING);
					getElementLabel(type.getParent(), (parentType == IModelElement.METHOD ? M_FULLY_QUALIFIED : F_FULLY_QUALIFIED) | (flags & QUALIFIER_FLAGS),
							buf);
				}

				if (parentType == IModelElement.SOURCE_MODULE || parentType == IModelElement.BINARY_MODULE) {

					IPackageDeclaration pack = getPackage(type);
					if (pack != null) {
						buf.append(CONCAT_STRING);
						if (!pack.getElementName().equals("")) {
							buf.append(pack.getElementName());
						}else{
							buf.append(DEFAULT_PACKAGE);
						}
					}

				}
			}
		}
	}

	private IPackageDeclaration getPackage(IType type) {
		if (type == null)
			return null;

		ISourceModule jsModule = (ISourceModule) type.getAncestor(IModelElement.SOURCE_MODULE);

		if (jsModule == null) {
			return null;
		}

		int parentType = jsModule.getElementType();
		if (parentType == IModelElement.SOURCE_MODULE
				|| parentType == IModelElement.BINARY_MODULE) {

			IPackageDeclaration pack = null;
			try {
				IPackageDeclaration[] pkgs = jsModule.getPackageDeclarations();
				pack = pkgs.length > 0 ? pkgs[0] : null;

				return pack;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}
	
	private static final boolean getFlag(long flags, long flag) {
		return (flags & flag) != 0;
	}

	/**
	 * Appends the label for a local variable to a {@link StringBuffer}.
	 * 
	 * @param localVariable
	 *            The element to render.
	 * @param flags
	 *            The rendering flags. Flags with names starting with 'F_' are
	 *            considered.
	 * @param buf
	 *            The buffer to append the resulting label to.
	 */
	public static void getLocalVariableLabel(VjoLocalVariable localVariable,
			long flags, StringBuffer buf) {
		// if (getFlag(flags, F_PRE_TYPE_SIGNATURE)) {
		// getTypeSignatureLabel(localVariable.getTypeSignature(), flags, buf);
		// buf.append(' ');
		// }
		//		
		// if (getFlag(flags, F_FULLY_QUALIFIED)) {
		// getElementLabel(localVariable.getParent(), M_PARAMETER_TYPES |
		// M_FULLY_QUALIFIED | T_FULLY_QUALIFIED | (flags & QUALIFIER_FLAGS),
		// buf);
		// buf.append('.');
		// }

		buf.append(localVariable.getElementName());
		buf.append(" : ");
		buf.append(localVariable.getTypeSignature());
		// if (getFlag(flags, F_APP_TYPE_SIGNATURE)) {
		// buf.append(DECL_STRING);
		// getTypeSignatureLabel(localVariable.getTypeSignature(), flags, buf);
		// }
		//		
		// // post qualification
		// if (getFlag(flags, F_POST_QUALIFIED)) {
		// buf.append(CONCAT_STRING);
		// getElementLabel(localVariable.getParent(), M_PARAMETER_TYPES |
		// M_FULLY_QUALIFIED | T_FULLY_QUALIFIED | (flags & QUALIFIER_FLAGS),
		// buf);
		// }
	}
}
