/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.ebayopensource.vjet.eclipse.internal.ui.editor.semantic.highlighting;

import org.eclipse.dltk.mod.ui.editor.highlighting.SemanticHighlighting;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.PlatformUI;

/**
 * Semantic highlighting
 */
public abstract class VjoSemanticHighlighting extends SemanticHighlighting {



	/**
	 * @return <code>true</code> if the text attribute bold is set by default
	 */
	public abstract boolean isBoldByDefault();

	/**
	 * @return <code>true</code> if the text attribute italic is set by
	 *         default
	 */
	public abstract boolean isItalicByDefault();

	/**
	 * @return <code>true</code> if the text attribute strikethrough is set by
	 *         default
	 * @since 3.1
	 */
	public boolean isStrikethroughByDefault() {
		return false;
	}

	/**
	 * @return <code>true</code> if the text attribute underline is set by
	 *         default
	 * @since 3.1
	 */
	public boolean isUnderlineByDefault() {
		return false;
	}

	/**
	 * @return <code>true</code> if this highlighting should be enabled by
	 *         default
	 */
	public abstract boolean isEnabledByDefault() ;

	

	/**
	 * @return the default default text color
	 * @since 3.3
	 */
	public abstract RGB getDefaultDefaultTextColor();

	/**
	 * @return the default default text color
	 */
	public RGB getDefaultTextColor() {
		return findRGB(getThemeColorKey(), getDefaultDefaultTextColor());
	}

	private String getThemeColorKey() {
		return JavaUI.ID_PLUGIN + "." + getPreferenceKey() + "Highlighting"; //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Returns the RGB for the given key in the given color registry.
	 * 
	 * @param key
	 *            the key for the constant in the registry
	 * @param defaultRGB
	 *            the default RGB if no entry is found
	 * @return RGB the RGB
	 * @since 3.3
	 */
	private static RGB findRGB(String key, RGB defaultRGB) {
		if (!PlatformUI.isWorkbenchRunning())
			return defaultRGB;

		ColorRegistry registry = PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme().getColorRegistry();
		RGB rgb = registry.getRGB(key);
		if (rgb != null)
			return rgb;
		return defaultRGB;
	}

	public abstract boolean consumes(SemanticToken token);
}
