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
package org.ebayopensource.vjet.eclipse.internal.ui.view.typespace;

import java.util.Iterator;
import java.util.Map.Entry;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.ide.IDE;

/**
 * 
 * 
 */
class TypespaceTreeLabelProvider extends LabelProvider {
	static ISharedImages s_images = DLTKUIPlugin.getDefault().getWorkbench()
			.getSharedImages();
	static ImageDescriptorRegistry s_fRegistry = DLTKUIPlugin
			.getImageDescriptorRegistry();;

	@Override
	public String getText(Object element) {
		if (element instanceof IGroup) {
			int size = ((IGroup) element).getEntities().values().size();
			return ((IGroup) element).getName() + "(" + size + ")";
		}

		// for IJstType, also show the entity name
		if (element instanceof IJstType) {
			IJstType jstType = (IJstType) element;
			return this.getJstNodeLabel(jstType) + " "
					+ this.getEntityKey(jstType);
		}

		if (element instanceof IJstNode)
			return this.getJstNodeLabel((IJstNode) element);

		return element.toString();
	}

	private String getEntityKey(IJstType jstType) {
		IGroup group = TypeSpaceMgr.getInstance().getController()
				.getJstTypeSpaceMgr().getTypeSpace().getGroup(jstType);
		for (Iterator iterator = group.getEntities().entrySet().iterator(); iterator
				.hasNext();) {
			Entry entry = (Entry) iterator.next();
			if (entry.getValue() == jstType)
				return "[entity key = " + entry.getKey() + "]";
		}

		return "";
	}

	private String getJstNodeLabel(IJstNode jstNode) {
		return getName(jstNode) + getSourceRange(jstNode);
	}

	private String getName(IJstNode jstNode) {
		String simpleName = jstNode.getClass().getSimpleName();

		if (jstNode instanceof IJstType)
			return simpleName + ":" + ((IJstType) jstNode).getName();

		if (jstNode instanceof IJstMethod)
			return simpleName + ":" + ((IJstMethod) jstNode).getName();

		if (jstNode instanceof IJstProperty)
			return simpleName + ":" + ((IJstProperty) jstNode).getName();

		if (jstNode instanceof JstVar)
			return simpleName + ":" + ((JstVar) jstNode).getName();

		if (jstNode instanceof JstIdentifier)
			return simpleName + ":" + ((JstIdentifier) jstNode).getName();

		return simpleName;
	}

	private String getSourceRange(IJstNode jstNode) {
		JstSource jstSource = jstNode.getSource();
		if (jstSource != null)
			return "[" + jstSource.getStartOffSet() + ","
					+ jstSource.getEndOffSet() + "]";

		return "";
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof IGroup) {
			IGroup group = (IGroup) element;
			String groupName = group.getName();
			if (CodeassistUtils.isBinaryPath(groupName)) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_LIBRARY);
			} else if (CodeassistUtils.isNativeGroup(groupName)) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_LIBRARY);
			} else {
				return getImageFromIDEImage(IDE.SharedImages.IMG_OBJ_PROJECT);

			}
		}

		if (element instanceof IJstNode)
			return this.getJstNodeImage((IJstNode) element);
		return super.getImage(element);
	}

	private Image getJstNodeImage(IJstNode jstNode) {

		if (jstNode instanceof IJstType) {
			IJstType type = (IJstType) jstNode;
			if (type.isInterface()) {
				return DLTKPluginImages
						.get(DLTKPluginImages.IMG_OBJS_INTERFACE);
			} else {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_CLASS);
			}
		} else if (jstNode instanceof IJstMethod) {
			IJstMethod method = (IJstMethod) jstNode;
			JstModifiers modifiers = method.getModifiers();
			if (modifiers.isPrivate()) {
				return DLTKPluginImages
						.get(DLTKPluginImages.IMG_METHOD_PRIVATE);
			}

			if (modifiers.isProtected()) {
				return DLTKPluginImages
						.get(DLTKPluginImages.IMG_METHOD_PROTECTED);
			}

			if (modifiers.isPublic()) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PUBLIC);
			}

			return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_DEFAULT);
		}

		if (jstNode instanceof IJstProperty) {

			IJstProperty property = (IJstProperty) jstNode;
			if (property.getModifiers().isPrivate()) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_FIELD_PRIVATE);
			}

			if (property.getModifiers().isProtected()) {
				return DLTKPluginImages
						.get(DLTKPluginImages.IMG_FIELD_PROTECTED);
			}

			if (property.getModifiers().isPublic()) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_FIELD_PUBLIC);
			}

			return DLTKPluginImages.get(DLTKPluginImages.IMG_FIELD_DEFAULT);

		} else if (jstNode instanceof JstVar) {
			return DLTKPluginImages
					.get(DLTKPluginImages.IMG_OBJS_LOCAL_VARIABLE);
		} else if (jstNode instanceof JstIdentifier) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_FIELD);
		} else if (jstNode instanceof JstTypeReference) {
			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_IMPDECL);
		}

		return null;
	}

	static Image getImageFromIDEImage(String imageId) {
		return s_fRegistry.get(s_images.getImageDescriptor(imageId));

	}

}
