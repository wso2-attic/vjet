/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.completion;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.vjet.eclipse.ui.VjetUIImages;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageDescriptor;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageProvider;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.dltk.mod.ui.ScriptElementImageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class VjoProposalLabelProvider {

	public static ImageDescriptor getScriptImageDescriptor(IJstNode node) {

		String nodeType = node.getClass().getName();
		ImageDescriptor descriptor = getBaseImage(node);
		int adornmentFlags = 0;
		JstModifiers modifies = VjoProposalLabelUtil.getModifiers(node);
		adornmentFlags = VjoProposalLabelUtil.getVjoModifierForImage(modifies);
		String flagsStr = modifies.toString();
		String key = nodeType + ":" + flagsStr;
		if (VjetUIImages.getImage(key) != null) {
			return VjetUIImages.getImageDescriptor(key);
		} else {

			if (modifies != null && adornmentFlags != 0) {
				descriptor = new VjoElementImageDescriptor(descriptor,
						adornmentFlags, VjoElementImageProvider.SMALL_SIZE);
				VjetUIImages.put(key, descriptor);
			}
			VjetUIImages.put(key, descriptor);
			return VjetUIImages.getImageDescriptor(key);
		}

	}

	public static Image getScriptImage(IJstNode node) {
		String key = node.getClass().getName();
		ImageDescriptor descriptor = getBaseImage(node);
		int adornmentFlags = 0;
		JstModifiers modifies = VjoProposalLabelUtil.getModifiers(node);
		adornmentFlags = VjoProposalLabelUtil.getVjoModifierForImage(modifies);
		if (modifies != null) {
			key = key + ":" + modifies.toString();
		}
		if (VjetUIImages.getImage(key) != null) {
			return VjetUIImages.getImage(key);
		} else {

			if (modifies != null && adornmentFlags != 0) {
				descriptor = new VjoElementImageDescriptor(descriptor,
						adornmentFlags, VjoElementImageProvider.SMALL_SIZE);
				VjetUIImages.put(key, descriptor);
			}
			VjetUIImages.put(key, descriptor);
			return VjetUIImages.getImage(key);
		}

	}

	public static Image getMethodImage(int flags) {
		if ((flags & Modifiers.AccPrivate) != 0) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PRIVATE);
		}

		if ((flags & Modifiers.AccProtected) != 0) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PROTECTED);
		}

		if ((flags & Modifiers.AccPublic) != 0) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PUBLIC);
		}

		return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_DEFAULT);
	}

	public static Image getTypeImageDescriptor(int flags, boolean useLightIcons) {
		if (Flags.isInterface(flags)) {
			if (useLightIcons) {
				return DLTKPluginImages
						.get(DLTKPluginImages.IMG_OBJS_INTERFACEALT);
			}
		} else if (useLightIcons) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASSALT);
		}
		return getClassImageDescriptor(flags);
	}

	private static Image getClassImageDescriptor(int flags) {
		if ((flags & Modifiers.AccTest) != 0) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_TEST);
		}
		if ((flags & Modifiers.AccTestCase) != 0) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_TESTCASE);
		}
		if ((flags & Modifiers.AccNameSpace) != 0) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_NAMESPACE);
		}

		if ((flags & Modifiers.AccModule) != 0) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_MODULE);
		}
		if ((flags & Modifiers.AccInterface) != 0) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_INTERFACE);
		}
		return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
	}

	private static ImageDescriptor getBaseImage(IJstNode node) {
		if (node instanceof IJstMethod) {
			IJstMethod method = (IJstMethod) node;
			JstModifiers modifies = method.getModifiers();
			int flag = VjoProposalLabelUtil.getDltkModifyFlag(modifies);
			return ScriptElementImageProvider.getMethodImageDescriptor(flag);
		} else if (node instanceof IJstProperty) {
			IJstProperty property = (IJstProperty) node;
			JstModifiers modifies = property.getModifiers();
			int flag = VjoProposalLabelUtil.getDltkModifyFlag(modifies);
			return ScriptElementImageProvider.getFieldImageDescriptor(flag);
		} else if (node instanceof IJstType) {
			IJstType type = (IJstType) node;
			JstModifiers modifies = type.getModifiers();
			int flag = VjoProposalLabelUtil.getDltkModifyFlag(modifies);
			return ScriptElementImageProvider.getTypeImageDescriptor(flag,
					false);
		} else if (node instanceof JstArg) {
			return DLTKPluginImages
					.getDescriptor(DLTKPluginImages.IMG_OBJS_LOCAL_VARIABLE);
		} else if (node instanceof ILHS) {
			return DLTKPluginImages
					.getDescriptor(DLTKPluginImages.IMG_OBJS_LOCAL_VARIABLE);
		} else if (node instanceof JstPackage) {
			return DLTKPluginImages
					.getDescriptor(DLTKPluginImages.IMG_OBJS_PACKAGE);
		} else {
			return null;
		}
	}

}
