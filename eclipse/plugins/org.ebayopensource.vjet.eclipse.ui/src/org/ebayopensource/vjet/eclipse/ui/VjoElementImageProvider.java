/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.ui;

import org.ebayopensource.vjet.eclipse.core.ClassFileConstants;
import org.ebayopensource.vjet.eclipse.core.IImportContainer;
import org.ebayopensource.vjet.eclipse.core.IImportDeclaration;
import org.ebayopensource.vjet.eclipse.internal.core.util.Util;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IProjectFragment;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.IJSInitializer;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.ScriptElementImageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * 
 * 
 */
public class VjoElementImageProvider extends ScriptElementImageProvider
		implements ILabelProvider {

	private static boolean confirmAbstract(IMember member) {
		// TODO Auto-generated method stub
		return false;
	}

	public static ImageDescriptor getFieldImageDescriptor(int flags) {
		ImageDescriptor descriptor;
		// add by patrick
		if((flags & Modifiers.AccGlobal) != 0){
			return VjetUIImages.DESC_FIELD_GLOBAL;
		}
		// end add
		
		if (Flags.isPrivate(flags)) {
			descriptor = DLTKUIPlugin
					.getImageDescriptor("icons/full/obj16/field_private_obj.gif");
		} else if (Flags.isPublic(flags)) {
			descriptor = DLTKUIPlugin
					.getImageDescriptor("icons/full/obj16/field_public_obj.gif");
		} else if (Flags.isProtected(flags)) {
			descriptor = DLTKUIPlugin
					.getImageDescriptor("icons/full/obj16/field_protected_obj.gif");
		} else {
			descriptor = DLTKPluginImages.DESC_FIELD_DEFAULT;
		}
		return descriptor;
	}

	private static boolean isEnumConstant(IMember member, int modifiers) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isInterfaceOrAnnotationField(IMember member) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isInterfaceOrAnnotationFieldOrType(IMember member) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean showOverlayIcons(int flags) {
		return (flags & OVERLAY_ICONS) != 0;
	}

	private static boolean useSmallSize(int flags) {
		return (flags & SMALL_ICONS) != 0;
	}

	private int computeVjoAdornmentFlags(IModelElement element, int renderFlags) {
		int flags = 0;
		if (showOverlayIcons(renderFlags) && element instanceof IMember) {
			try {
				IMember member = (IMember) element;

				if (element.getElementType() == IModelElement.METHOD
						&& ((IMethod) element).isConstructor())
					flags |= VjoElementImageDescriptor.CONSTRUCTOR;

				int modifiers = member.getFlags();
				if ((modifiers & ClassFileConstants.AccAbstract) != 0
				/* TODO && confirmAbstract(member) */)
					flags |= VjoElementImageDescriptor.ABSTRACT;
				if ((modifiers & ClassFileConstants.AccFinal) != 0
						|| isInterfaceOrAnnotationField(member)
						|| isEnumConstant(member, modifiers))
					flags |= VjoElementImageDescriptor.FINAL;
				// if (Flags.isSynchronized(modifiers) &&
				// confirmSynchronized(member))
				// flags |= VjoElementImageDescriptor.SYNCHRONIZED;
				if ((modifiers & Modifiers.AccStatic) != 0
						|| isInterfaceOrAnnotationFieldOrType(member)
						|| isEnumConstant(member, modifiers))
					flags |= VjoElementImageDescriptor.STATIC;

				// add by patrick for global support
				if((modifiers & Modifiers.AccGlobal) != 0){
					flags |= VjoElementImageDescriptor.GLOBAL;
				}
				// end add
				
				// Added by Eric.Ma for Otype sub property
				if((modifiers & Modifiers.AccPublic) != 0 && member.getElementType() == IModelElement.FIELD
						&& member.getParent().getElementType() == IModelElement.FIELD){
					flags |= VjoElementImageDescriptor.OPTION_PRO;
				}
				// End of added.

				// if (Flags.isDeprecated(modifiers))
				// flags |= VjoElementImageDescriptor.DEPRECATED;

				// if (member.getElementType() == IModelElement.TYPE) {
				// if (JavaModelUtil.hasMainMethod((IType) member)) {
				// flags |= VjoElementImageDescriptor.RUNNABLE;
				// }
				// }
				if(member.getElementType() == IModelElement.TYPE){
					if(VjetUIUtils.isMixin((IType) member)){
						flags |= VjoElementImageDescriptor.MIXIN;
					}
				}
			} catch (ModelException e) {
				// do nothing. Can't compute runnable adornment or get flags
			}
		}
		return flags;
	}

	@Override
	public ImageDescriptor getBaseImageDescriptor(IModelElement element,
			int renderFlags) {
		if (element instanceof IModelElement) {
			int elementType = (element).getElementType();
			try {
				switch (elementType) {
				case IImportContainer.ELEMENT_TYPE: {
					return DLTKPluginImages.DESC_OBJS_IMPCONT;
				}
				case IImportDeclaration.ELEMENT_TYPE: {
					if (((IImportDeclaration) element).isLibrary()) {
						ImageDescriptor descriptor = VjetUIPlugin
								.getImageDescriptor("icons/full/obj16/imp_lib.gif");
						return descriptor;
					} else {
						return DLTKPluginImages.DESC_OBJS_IMPDECL;
					}
				}
				case IJSInitializer.ELEMENT_TYPE: {
					return DLTKPluginImages.DESC_METHOD_PRIVATE;
				}
				case IModelElement.FIELD: {
					int flags = ((IField) element).getFlags();
					return getFieldImageDescriptor(flags);
				}
				case IModelElement.TYPE: {
					IType type = (IType) element;
					return getTypeImageDescriptor(type.getFlags(),
							useLightIcons(renderFlags));
				}
				// add by patrick
				case IModelElement.METHOD: {
					int flags = ((IMethod)element).getFlags();
					return getMethodImageDescriptor(flags);
				}
				// end add
				// add by Jack
				case IModelElement.PROJECT_FRAGMENT: {
					IProjectFragment root = (IProjectFragment) element;
					if (root.isExternal()) {
						if (root.isArchive() || Util.isNativeCacheDir(root.getPath())) {
							return DLTKPluginImages.DESC_OBJS_JAR_WSRC;
						} else {
							return DLTKPluginImages.DESC_OBJS_PACKFRAG_ROOT;
						}
					} else if(root.isArchive()) {
						return DLTKPluginImages.DESC_OBJS_JAR_WSRC;
					}else {
						return DLTKPluginImages.DESC_OBJS_PACKFRAG_ROOT;
					}
				}
				// end add
				}
			} catch (ModelException e) {
				if (e.isDoesNotExist())
					return DLTKPluginImages.DESC_OBJS_UNKNOWN;

				DLTKUIPlugin.log(e);
				return DLTKPluginImages.DESC_OBJS_GHOST;
			}
		}
		return super.getBaseImageDescriptor(element, renderFlags);
	}

	// add by patrick
	public static ImageDescriptor getMethodImageDescriptor(int flags) {
		if ((flags & Modifiers.AccGlobal) != 0) {
			return VjetUIImages.DESC_METHOD_GLOBAL;
		} else {
			return ScriptElementImageProvider.getMethodImageDescriptor(flags);
		}
	}
	// end add
	
	@Override
	public ImageDescriptor getScriptImageDescriptor(IModelElement element,
			int flags) {
		// int adornmentFlags = computeAdornmentFlags(element, flags);
		Point size = useSmallSize(flags) ? SMALL_SIZE : BIG_SIZE;
		ImageDescriptor descr = getBaseImageDescriptor(element, flags);
		if (descr != null) {
			int adornmentFlags = computeVjoAdornmentFlags(element, flags);
			return new VjoElementImageDescriptor(descr, adornmentFlags, size);
		} else {
			return null;
		}
	}

	@Override
	public ImageDescriptor getWorkbenchImageDescriptor(IAdaptable adaptable,
			int flags) {
		IWorkbenchAdapter wbAdapter = (IWorkbenchAdapter) adaptable
				.getAdapter(IWorkbenchAdapter.class);
		if (wbAdapter == null) {
			return null;
		}
		ImageDescriptor descriptor = wbAdapter.getImageDescriptor(adaptable);
		if (descriptor == null) {
			return null;
		}
		Point size = useSmallSize(flags) ? SMALL_SIZE : BIG_SIZE;
		return new VjoElementImageDescriptor(descriptor, 0, size);
	}

	public static ImageDescriptor getTypeImageDescriptor(int flags,
			boolean useLightIcons) {
		if ((flags & ClassFileConstants.AccInterface) != 0) {
			if (useLightIcons) {
				// return DLTKPluginImages.DESC_OBJS_INTERFACEALT;
				return DLTKUIPlugin
						.getImageDescriptor("icons/full/obj16/intf_obj.gif");
			}
			// if (isInner) {
			// return
			// getInnerInterfaceImageDescriptor(isInInterfaceOrAnnotation,
			// flags);
			// }
			return getInterfaceImageDescriptor(flags);
		}
		if ((flags & ClassFileConstants.AccEnum) != 0) {
			if (useLightIcons) {
				// return JavaPluginImages.DESC_OBJS_ENUM_ALT;
				return DLTKUIPlugin
						.getImageDescriptor("icons/full/obj16/enum_obj.gif");
			}
			// if (isInner) {
			// return getInnerEnumImageDescriptor(isInInterfaceOrAnnotation,
			// flags);
			// }
			return getEnumImageDescriptor(flags);
		}
		
		if ((flags & ClassFileConstants.AccModule) != 0) {
			if (useLightIcons) {
				// return JavaPluginImages.DESC_OBJS_ENUM_ALT;
				return DLTKUIPlugin
						.getImageDescriptor("icons/full/obj16/module_obj.gif");
			}
			// if (isInner) {
			// return getInnerEnumImageDescriptor(isInInterfaceOrAnnotation,
			// flags);
			// }
			return getModuleImageDescriptor(flags);
		}

		if (useLightIcons) {
			return DLTKPluginImages.DESC_OBJS_CLASSALT;
		}

		return getClassImageDescriptor(flags);
	}

	private static ImageDescriptor getClassImageDescriptor(int flags) {
		if ((flags & Modifiers.AccNameSpace) != 0) {
			return DLTKPluginImages.DESC_OBJS_NAMESPACE;
		}

		if ((flags & Modifiers.AccModule) != 0) {
			return DLTKPluginImages.DESC_OBJS_MODULE;
		}

		return DLTKPluginImages.DESC_OBJS_CLASS;
	}

	private static ImageDescriptor getInterfaceImageDescriptor(int flags) {
		if (Flags.isPublic(flags) || Flags.isProtected(flags)
				|| Flags.isPrivate(flags))
			// return DLTKPluginImages.DESC_OBJS_INTERFACE;
			return DLTKUIPlugin
					.getImageDescriptor("icons/full/obj16/int_obj.gif");
		else
			// return DLTKPluginImages.DESC_OBJS_INTERFACE_DEFAULT;
			return DLTKUIPlugin
					.getImageDescriptor("icons/full/obj16/int_default_obj.gif");
	}

	private static ImageDescriptor getEnumImageDescriptor(int flags) {
		if (Flags.isPublic(flags) || Flags.isProtected(flags)
				|| Flags.isPrivate(flags))
			// return JavaPluginImages.DESC_OBJS_ENUM;
			return DLTKUIPlugin
					.getImageDescriptor("icons/full/obj16/enum_obj.gif");
		else
			// return JavaPluginImages.DESC_OBJS_ENUM_DEFAULT;
			return DLTKUIPlugin
					.getImageDescriptor("icons/full/obj16/enum_default_obj.gif");
	}

	private static ImageDescriptor getModuleImageDescriptor(int flags) {
		if (Flags.isPublic(flags) || Flags.isProtected(flags)
				|| Flags.isPrivate(flags))
			// return JavaPluginImages.DESC_OBJS_ENUM;
			return DLTKUIPlugin
					.getImageDescriptor("icons/full/obj16/module_obj.gif");
		else
			// return JavaPluginImages.DESC_OBJS_ENUM_DEFAULT;
			return VjetUIPlugin
					.getImageDescriptor("icons/full/obj16/module_default_obj.gif");
	}
	
	private static boolean useLightIcons(int flags) {
		return (flags & LIGHT_TYPE_ICONS) != 0;
	}

	@Override
	protected ILabelProvider getContributedLabelProvider(IModelElement element) {
		// overridden to prevent endless loop
		return null;
	}

	public Image getImage(Object element) {
		int flags = 0;
		if (element instanceof IMember) {
			try {
				flags = ((IMember) element).getFlags();
			} catch (ModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return getImageLabel(element, flags);
	}

	public String getText(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}

	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
	}
}
