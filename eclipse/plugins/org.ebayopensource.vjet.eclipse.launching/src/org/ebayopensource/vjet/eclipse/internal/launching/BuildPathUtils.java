package org.ebayopensource.vjet.eclipse.internal.launching;

import java.net.URI;
import java.net.URISyntaxException;

import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.internal.core.ExternalFoldersManager;
import org.eclipse.dltk.mod.internal.core.ModelManager;

public class BuildPathUtils {

	
	public static IPath createPathForGroup(String groupName){
		IPath p = null;
		URI uri = null;
		try {
			// TODO how to get this without adding linked resource?
			// must be absolute path for build path to work
			String paramString = "typespace://"+groupName+"/";
			uri = new URI(paramString);
			
			
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot(); 
			IFile[] files = root.findFilesForLocationURI(uri,IContainer.INCLUDE_HIDDEN );
			p = files[0].getFullPath();

			
		} catch (URISyntaxException e) {
			VjetPlugin.error("could not find VjoSelfDescribed library" ,e);
		}
		
		return p;
	}
	
	
	public static void addLinkForGroup(String group) throws CoreException {
		String paramString = "typespace://"+group +"/";
		URI uri=null;
		try {
			uri = new URI(paramString);
		} catch (URISyntaxException e) {
			VjetPlugin.error("could not create url for group "+ group, e);
		}
		
		ExternalFoldersManager foldersManager = ModelManager
				.getExternalManager();
		foldersManager.getExternalFoldersProject();

		foldersManager.createLinkFolderKeepFolderName(group,new Path(group), uri,true, null );
	}

	
	
}
