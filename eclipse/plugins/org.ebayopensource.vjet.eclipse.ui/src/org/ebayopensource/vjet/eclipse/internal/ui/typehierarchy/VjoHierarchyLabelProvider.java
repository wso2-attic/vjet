/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.typehierarchy;

import org.ebayopensource.vjet.eclipse.core.ClassFileConstants;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageDescriptor;
import org.ebayopensource.vjet.eclipse.ui.VjoElementImageProvider;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ITypeHierarchy;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.ui.typehierarchy.TypeHierarchyLifeCycle;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.dltk.mod.ui.ScriptElementImageDescriptor;
import org.eclipse.dltk.mod.ui.ScriptElementImageProvider;
import org.eclipse.dltk.mod.ui.ScriptElementLabels;
import org.eclipse.dltk.mod.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

public class VjoHierarchyLabelProvider extends AppearanceAwareLabelProvider {

	/** Flag to render the abstract adornment. */
	public final static int ABSTRACT = 0x001;

	/** Flag to render the final adornment. */
	public final static int FINAL = 0x002;

	/** Flag to render the mixin adorment. */
	public final static int MIXIN = 0x004;
	
	/** Flag to render the static adornment. */
	public final static int STATIC = 0x008;

	private static class FocusDescriptor extends CompositeImageDescriptor {
		private ImageDescriptor fBase;

		public FocusDescriptor(ImageDescriptor base) {
			fBase = base;
		}

		protected void drawCompositeImage(int width, int height) {
			drawImage(getImageData(fBase), 0, 0);
			// drawImage(getImageData(DLTKPluginImages.DESC_OVR_FOCUS), 0, 0);
		}

		private ImageData getImageData(ImageDescriptor descriptor) {
			ImageData data = descriptor.getImageData(); // see bug 51965:
														// getImageData can
														// return null
			if (data == null) {
				data = DEFAULT_IMAGE_DATA;
				DLTKUIPlugin
						.logErrorMessage("Image data not available: " + descriptor.toString()); //$NON-NLS-1$
			}
			return data;
		}

		protected Point getSize() {
			return ScriptElementImageProvider.BIG_SIZE;
		}

		public int hashCode() {
			return fBase.hashCode();
		}

		public boolean equals(Object object) {
			return object != null
					&& FocusDescriptor.class.equals(object.getClass())
					&& ((FocusDescriptor) object).fBase.equals(fBase);
		}
	}

	private Color fGrayedColor;
	private Color fSpecialColor;

	private ViewerFilter fFilter;

	private TypeHierarchyLifeCycle fHierarchy;

	public VjoHierarchyLabelProvider(TypeHierarchyLifeCycle lifeCycle,
			IPreferenceStore store) {
		super(DEFAULT_TEXTFLAGS | ScriptElementLabels.USE_RESOLVED,
				DEFAULT_IMAGEFLAGS, store);
		fHierarchy = lifeCycle;
		fFilter = null;
	}

	/**
	 * @return Returns the filter.
	 */
	public ViewerFilter getFilter() {
		return fFilter;
	}

	/**
	 * @param filter
	 *            The filter to set.
	 */
	public void setFilter(ViewerFilter filter) {
		fFilter = filter;
	}

	protected boolean isDifferentScope(IType type) {
		if (fFilter != null && !fFilter.select(null, null, type)) {
			return true;
		}

		IModelElement input = fHierarchy.getInputElement();
		if (input == null || input.getElementType() == IModelElement.TYPE) {
			return false;
		}

		IModelElement parent = type.getAncestor(input.getElementType());
		if (input.getElementType() == IModelElement.PROJECT_FRAGMENT) {
			if (parent == null
					|| parent.getElementName().equals(input.getElementName())) {
				return false;
			}
		} else if (input.equals(parent)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ILabelProvider#getText
	 */
	public String getText(Object element) {
		String text = super.getText(element);
		return decorateText(text, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ILabelProvider#getImage
	 */
	public Image getImage(Object element) {
		Image result = null;
		if (element instanceof IType) {
			ImageDescriptor desc = getTypeImageDescriptor((IType) element);
			if (desc != null) {
				if (element.equals(fHierarchy.getInputElement())) {
					desc = new FocusDescriptor(desc);
				}
				result = DLTKUIPlugin.getImageDescriptorRegistry().get(desc);
			}
		} else {
			result = fImageLabelProvider.getImageLabel(element,
					evaluateImageFlags(element));
		}
		return decorateImage(result, element);
	}

	private ImageDescriptor getTypeImageDescriptor(IType type) {
		ITypeHierarchy hierarchy = fHierarchy.getHierarchy();
		if (hierarchy == null) {
			return new VjoElementImageDescriptor(
					DLTKPluginImages.DESC_OBJS_CLASS, 0,
					ScriptElementImageProvider.BIG_SIZE);
		}

		int flags = -1;
		try {
			flags = type.getFlags();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		if (flags == -1) {
			return new VjoElementImageDescriptor(
					DLTKPluginImages.DESC_OBJS_CLASS, 0,
					ScriptElementImageProvider.BIG_SIZE);
		}

		boolean isInterface = (flags & ClassFileConstants.AccInterface) != 0;
		;
		boolean isInner = (type.getDeclaringType() != null);

		ImageDescriptor desc = VjoElementImageProvider.getTypeImageDescriptor(
				flags, false);// (isInner, false, flags,
								// isDifferentScope(type));

		int adornmentFlags = 0;
		if ((flags & Modifiers.AccFinal) != 0) {
			adornmentFlags |= FINAL;
		}
		if ((flags & Modifiers.AccAbstract) != 0 && !isInterface) {
			adornmentFlags |= ABSTRACT;
		}
		if ((flags & Modifiers.AccStatic) != 0) {
			adornmentFlags |= STATIC;
		}
		if((flags & Modifiers.AccModule) != 0) {
			adornmentFlags |= MIXIN;
		}
		
		return new ScriptElementImageDescriptor(desc, adornmentFlags,
				ScriptElementImageProvider.BIG_SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		if (element instanceof IMethod) {
			if (fSpecialColor == null) {
				fSpecialColor = Display.getCurrent().getSystemColor(
						SWT.COLOR_DARK_BLUE);
			}
			return fSpecialColor;
		} else if (element instanceof IType
				&& isDifferentScope((IType) element)) {
			if (fGrayedColor == null) {
				fGrayedColor = Display.getCurrent().getSystemColor(
						SWT.COLOR_DARK_GRAY);
			}
			return fGrayedColor;
		}
		return null;
	}

}
