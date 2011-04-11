/**
 * 
 */
package org.eclipse.dltk.mod.internal.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.mod.core.IBuildpathEntry;
import org.eclipse.dltk.mod.internal.core.ModelManager.PerProjectInfo;

/**
 * @author xingzhu
 * 
 */
public class NullPerProjectInfo extends PerProjectInfo {
	public NullPerProjectInfo(IProject project) {
		super(project);

		rawBuildpath = new IBuildpathEntry[0];
		resolvedBuildpath = new IBuildpathEntry[0];
	}

	public void rememberExternalLibTimestamps() {
	}

	public synchronized void updateBuildpathInformation(
			IBuildpathEntry[] newRawBuildpath) {
	}

	public String toString() {
		return "No Script Nature DLTK Project Info";
	}
}
