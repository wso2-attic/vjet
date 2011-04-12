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

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.mod.ui.DLTKPluginImages;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;

/**
 * 
 * 
 */
public class VjoElementImageDescriptor extends CompositeImageDescriptor {
	/**
	 * Flag to render the abstract adornment.
	 */
	public final static int ABSTRACT = 0x001;

	/** Flag to render the 'constructor' adornment. */
	public final static int CONSTRUCTOR = 0x200;

	/**
	 * Flag to render the 'deprecated' adornment.
	 * 
	 * @since 3.0
	 */
	public final static int DEPRECATED = 0x400;

	/** Flag to render the error adornment. */
	public final static int ERROR = 0x040;

	/** Flag to render the final adornment. */
	public final static int FINAL = 0x002;

	/** Flag to render the 'implements' adornment. */
	public final static int IMPLEMENTS = 0x100;

	/** Flag to render the 'override' adornment. */
	public final static int OVERRIDES = 0x080;

	/** Flag to render the runnable adornment. */
	public final static int RUNNABLE = 0x010;

	/** Flag to render the static adornment. */
	public final static int STATIC = 0x008;

	/** Flag to render the synchronized adornment. */
	public final static int SYNCHRONIZED = 0x004;

	/** Flag to render the warning adornment. */
	public final static int WARNING = 0x020;

	/*** Flag to render the mixin adorment. */
	public final static int MIXIN = 0x800;
	
	/*** Flag to render the global adorment. */
	public final static int GLOBAL = 0x1000;
	
	/*** Flag to render the optinoal property. */
	public final static int OPTION_PRO = 0x10000;
	
	private ImageDescriptor m_baseImage;
	private int m_flags;
	private Point m_size;

	/**
	 * Creates a new JavaElementImageDescriptor.
	 * 
	 * @param baseImage
	 *            an image descriptor used as the base image
	 * @param flags
	 *            flags indicating which adornments are to be rendered. See
	 *            <code>setAdornments</code> for valid values.
	 * @param size
	 *            the size of the resulting image
	 * @see #setAdornments(int)
	 */
	public VjoElementImageDescriptor(ImageDescriptor baseImage, int flags,
			Point size) {
		m_baseImage = baseImage;
		Assert.isNotNull(m_baseImage);
		m_flags = flags;
		Assert.isTrue(m_flags >= 0);
		m_size = size;
		Assert.isNotNull(m_size);
	}

	private void drawBottomLeft() {
		Point size = getSize();
		int x = 0;
		if ((m_flags & ERROR) != 0) {
			ImageData data = getImageData(DLTKPluginImages.DESC_OVR_ERROR);
			drawImage(data, x, size.y - data.height);
			x += data.width;
		}
		if ((m_flags & WARNING) != 0) {
			ImageData data = getImageData(DLTKPluginImages.DESC_OVR_WARNING);
			drawImage(data, x, size.y - data.height);
			x += data.width;
		}
	}

	private void drawBottomRight() {
		Point size = getSize();
		int x = size.x;
		int flags = m_flags;

		int syncAndOver = SYNCHRONIZED | OVERRIDES;
		int syncAndImpl = SYNCHRONIZED | IMPLEMENTS;

		if ((flags & syncAndOver) == syncAndOver) { // both flags set: merged
			// overlay image
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/sync_over.gif"));
			x -= data.width;
			drawImage(data, x, size.y - data.height);
			flags &= ~syncAndOver; // clear to not render again
		} else if ((flags & syncAndImpl) == syncAndImpl) { // both flags set:
			// merged overlay image
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/sync_impl.gif"));
			x -= data.width;
			drawImage(data, x, size.y - data.height);
			flags &= ~syncAndImpl; // clear to not render again
		}
		if ((flags & OVERRIDES) != 0) {
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/over_co.gif"));
			x -= data.width;
			drawImage(data, x, size.y - data.height);
		}
		if ((flags & IMPLEMENTS) != 0) {
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/implm_co.gif"));
			x -= data.width;
			drawImage(data, x, size.y - data.height);
		}
		if ((flags & SYNCHRONIZED) != 0) {
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/synch_co.gif"));
			x -= data.width;
			drawImage(data, x, size.y - data.height);
		}
		if ((flags & RUNNABLE) != 0) {
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/run_co.gif"));
			x -= data.width;
			drawImage(data, x, size.y - data.height);
		}
	}

	/*
	 * (non-Javadoc) Method declared in CompositeImageDescriptor
	 */
	@Override
	protected void drawCompositeImage(int width, int height) {
		ImageData bg = getImageData(m_baseImage);

		if ((m_flags & DEPRECATED) != 0) { // over the full image
			Point size = getSize();
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/deprecated.gif"));
			drawImage(data, 0, size.y - data.height);
		}
		drawImage(bg, 0, 0);

		drawTopRight();
		drawBottomRight();
		drawBottomLeft();

	}
	
	private void drawTopRight() {
		int x = getSize().x;
		if ((m_flags & ABSTRACT) != 0) {
			ImageData data = getImageData(DLTKPluginImages.DESC_OVR_ABSTRACT);
			x -= data.width;
			drawImage(data, x, 0);
		}
		if ((m_flags & CONSTRUCTOR) != 0) {
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/constr_ovr.gif"));
			x -= data.width;
			drawImage(data, x, 0);
		}
		if ((m_flags & FINAL) != 0) {
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/final_co.gif"));
			x -= data.width;
			drawImage(data, x, 0);
		}
		if ((m_flags & STATIC) != 0) {
			ImageData data = getImageData(getDescriptor("icons/full/ovr16/static_co.gif"));
			x -= data.width;
			drawImage(data, x, 0);
		}
		if ((m_flags & MIXIN) != 0) {
			ImageData data = getImageData(VjetUIPlugin.getImageDescriptor("icons/full/ovr16/module_co.gif"));
			x -= data.width;
			drawImage(data, x, 0);
		}
		// add by patrick
		if ((m_flags & GLOBAL) != 0){
			ImageData data = getImageData(DLTKPluginImages.DESC_OVR_FIELD_GLOBAL);
			x -= data.width;
			drawImage(data, x, 0);
		}
		// end add
		
		// Added by Eric.Ma on 20100528 for otype frontend
		if ((m_flags & OPTION_PRO) != 0){
			ImageData data = getImageData(DLTKPluginImages.DESC_OVR_FIELD_INDEX);
			x -= data.width;
			drawImage(data, x, 0);
		}
		// end of added
	}

	/*
	 * (non-Javadoc) Method declared on Object.
	 */
	@Override
	public boolean equals(Object object) {
		if (object == null
				|| !VjoElementImageDescriptor.class.equals(object.getClass()))
			return false;

		VjoElementImageDescriptor other = (VjoElementImageDescriptor) object;
		return (m_baseImage.equals(other.m_baseImage)
				&& m_flags == other.m_flags && m_size.equals(other.m_size));
	}

	/**
	 * Returns the current adornments.
	 * 
	 * @return the current adornments
	 */
	public int getAdronments() {
		return m_flags;
	}

	private ImageDescriptor getDescriptor(String path) {
		return DLTKUIPlugin.getImageDescriptor(path);
	}

	private ImageData getImageData(ImageDescriptor descriptor) {
		ImageData data = descriptor.getImageData(); // see bug 51965:
		// getImageData
		// can return null
		if (data == null) {
			data = DEFAULT_IMAGE_DATA;
			DLTKUIPlugin
					.logErrorMessage("Image data not available: " + descriptor.toString()); //$NON-NLS-1$
		}
		return data;
	}

	/**
	 * Returns the size of the image created by calling
	 * <code>createImage()</code>.
	 * 
	 * @return the size of the image created by calling
	 *         <code>createImage()</code>
	 * @see ImageDescriptor#createImage()
	 */
	public Point getImageSize() {
		return new Point(m_size.x, m_size.y);
	}

	/*
	 * (non-Javadoc) Method declared in CompositeImageDescriptor
	 */
	@Override
	protected Point getSize() {
		return m_size;
	}

	/*
	 * (non-Javadoc) Method declared on Object.
	 */
	@Override
	public int hashCode() {
		return m_baseImage.hashCode() | m_flags | m_size.hashCode();
	}

	/**
	 * Sets the descriptors adornments. Valid values are: <code>ABSTRACT</code>,
	 * <code>FINAL</code>, <code>SYNCHRONIZED</code>, </code>STATIC<code>,
	 * </code>RUNNABLE<code>, </code>WARNING<code>, </code>ERROR<code>,
	 * </code>OVERRIDDES<code>, <code>IMPLEMENTS</code>,
	 * <code>CONSTRUCTOR</code>, <code>DEPRECATED</code>, or any
	 * combination of those.
	 * 
	 * @param adornments
	 *            the image descriptors adornments
	 */
	public void setAdornments(int adornments) {
		Assert.isTrue(adornments >= 0);
		m_flags = adornments;
	}

	/**
	 * Sets the size of the image created by calling <code>createImage()</code>.
	 * 
	 * @param size
	 *            the size of the image returned from calling
	 *            <code>createImage()</code>
	 * @see ImageDescriptor#createImage()
	 */
	public void setImageSize(Point size) {
		Assert.isNotNull(size);
		Assert.isTrue(size.x >= 0 && size.y >= 0);
		m_size = size;
	}
}
