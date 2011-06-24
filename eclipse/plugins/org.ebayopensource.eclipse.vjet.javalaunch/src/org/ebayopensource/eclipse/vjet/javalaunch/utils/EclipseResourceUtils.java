package org.ebayopensource.eclipse.vjet.javalaunch.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.core.JavaProject;

public class EclipseResourceUtils {
	
	static int tabCount = 0;

	public static File getFile(IFile f) {
		return f.getLocation().toFile();
	}

	static public String getResourceUri(IResource resource) {

		String resourceName = resource.getName();
		IPath parentPath = resource.getParent().getProjectRelativePath();

		String[] segments = parentPath.segments();
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		if (segments.length > 1) {

			for (int idx = 1; idx < segments.length; idx++) {
				sb.append(segments[idx]);
				sb.append("/");
			}
		}
		sb.append(resourceName);
		return sb.toString().substring(0, sb.toString().length());
	}

	static public String getResourceClassName(IResource resource) {

		IJavaProject javaProject = getJavaProject(resource.getProject());
		if (javaProject != null) {
			String className = EclipseResourceUtils.getQualifiedClassName(resource);
			return className;
		}

		return null;
	}
	
	static public IJavaProject getJavaProject(IProject project) {
		
		if (JavaProject.hasJavaNature(project) == true) {
			return JavaCore.create(project);
		}

		return null;
	}

	public static void appendFile(File f, StringBuffer sb) throws IOException {
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			char[] buffer = new char[1000];
			int c = 0;

			while ((c = br.read(buffer)) > 0) {
				sb.append(buffer, 0, c);
			}
		} catch (IOException e) {
			throw (e);
		}

	}

	public static List<IClasspathEntry> getClasspathEntry(int type, IClasspathEntry[] classPathEntries) {
		List<IClasspathEntry> classPathEntryList = new ArrayList<IClasspathEntry>();

		for (int idx = 0; idx < classPathEntries.length; idx++) {
			IClasspathEntry entry = classPathEntries[idx];
			if (entry.getEntryKind() == type) {
				classPathEntryList.add(entry);
			}
		}

		return classPathEntryList;
	}

	public static File getSourceRoot(IJavaProject project, IJavaElement e) {
		try {
			IPackageFragment frag = project.findPackageFragment(e.getPath());
			while (frag.isDefaultPackage() == false) {
				e = e.getParent();
				frag = project.findPackageFragment(e.getPath());
			}
			File f = e.getResource().getLocation().toFile();
			return f;
		} catch (JavaModelException e1) {
			throw new RuntimeException(e1);
		}
	}

	public static boolean isResourceInSourceLocation(IResource resource) {
		boolean resourceIsInSourceLocation = false;

		IProject resourceProject = resource.getProject();
		IJavaProject javaProject = JavaCore.create(resourceProject);
		if (javaProject != null || JavaProject.hasJavaNature(resourceProject) == false) {
			// IPath outputPath = null;
			IPath sourcePath = null;
			try {

				IClasspathEntry[] classPathEntries = javaProject.getRawClasspath();
				List<IClasspathEntry> sourceEntries = getClasspathEntry(IClasspathEntry.CPE_SOURCE, classPathEntries);

				for (IClasspathEntry entry : sourceEntries) {
					sourcePath = entry.getPath();

					resourceIsInSourceLocation = isResourceInClasspathEntry(resource, sourcePath);
					if (resourceIsInSourceLocation == true)
						break;

				}

			} catch (JavaModelException e) {

				// Swallow this exception. This just means we could not find
				// the class path definition, and hence does not have an output
				// location.
				resourceIsInSourceLocation = false;
			}
		}
		return resourceIsInSourceLocation;

	}

	private static boolean isResourceInClasspathEntry(IResource resource, IPath sourcePath) {
		IPath resourcePath = resource.getFullPath();
		boolean resourceIsInSourceLocation = true;

		// Search for differences in the paths
		for (int idx = 0; idx < sourcePath.segmentCount(); idx++) {

			if (resourcePath.segmentCount() <= idx) {
				// The output path has more segments than the resource path
				// which implies that the resource can not possibly be
				// in the
				resourceIsInSourceLocation = false;
				break;
			}

			// If the segment is NOT the same break out.
			if (sourcePath.segments()[idx].compareToIgnoreCase(resourcePath.segments()[idx]) != 0) {
				resourceIsInSourceLocation = false;
				break;
			}
		}
		return resourceIsInSourceLocation;
	}

//	public static boolean isEmpty(IFile file) {
//		try {
//			InputStream is = file.getContents();
//			InputStreamReader reader = new InputStreamReader(is);
//
//			int count = 0;
//			char[] buff = new char[1000];
//
//			while ((count = reader.read(buff)) > 0) {
//				String s = new String(buff, 0, count);
//				if (s.trim().length() > 0) {
//					return false;
//				}
//			}
//			return count <= 0;
//		} catch (CoreException e) {
//			logger.error(e.getMessage(), e);
//			return false;
//		} catch (IOException e) {
//			logger.error(e.getMessage(), e);
//			return false;
//		}
//
//	}

	public static String getQualifiedClassName(IResource pResource) {

		String qualifiedClassName = null;
		IProject project = pResource.getProject();

		try {
			IJavaProject proj = JavaCore.create(project);
			String filePathName = pResource.getLocation().toString();

			if (pResource.getFileExtension().toLowerCase().equals("class")) {
				String outputPath = proj.getOutputLocation().toString();
				String projectPath = project.getLocation().toString();
				outputPath = projectPath + outputPath.substring(project.getName().length());
				qualifiedClassName = filePathName.substring(outputPath.length(), filePathName.length() - 6);
				return qualifiedClassName = qualifiedClassName.replace('/', '.');
			}
			
			IPath resourcePath = EclipseResourceUtils.getResourcePackagePath(pResource, proj);
			String resName = pResource.getName();
			String resExtension = pResource.getFileExtension();
			qualifiedClassName = resourcePath.toString();
			
			qualifiedClassName = qualifiedClassName.replace('/', '.');
			qualifiedClassName = qualifiedClassName + "." +  resName.substring(0, resName.length() - (resExtension.length()+1));

		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return qualifiedClassName;
	}

	static public IResource getResource(URL url) throws URISyntaxException {
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// Get the dependent project
		IFile[] files = workspace.getRoot().findFilesForLocationURI(url.toURI());
		
		if (files.length == 1) {
			return files[0];
		}
		return null;
	}

	
	/**
	 * Searches through the tree of dependencies and adds to a list of projects.
	 * @param javaProject - The Java project to search
	 * @param transitiveClosureProjectList - The list to store the projects
	 * @throws JavaModelException
	 */
	public static void getTransitiveClosureDependencies(
			IJavaProject javaProject,
			Map<String, IJavaProject> transitiveClosureProjectList,
			Map<String, IPath> transitiveLibrarySet)
			throws JavaModelException {
		
		IClasspathEntry[] classPathEntries = javaProject.getResolvedClasspath(true);
		
		if (classPathEntries != null) {
				
			for (IClasspathEntry classPathEntry : classPathEntries) {
				
				if (classPathEntry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
					IResource classPathProject = ResourcesPlugin.getWorkspace().getRoot().findMember(
								classPathEntry.getPath());
					if (classPathProject != null) {
						if (transitiveClosureProjectList.containsKey(classPathProject.getName())== false) {
							
							IJavaProject subJavaProject = getJavaProject(classPathProject);
							transitiveClosureProjectList.put(classPathProject.getName(), subJavaProject);
							
							getTransitiveClosureDependencies(subJavaProject,  transitiveClosureProjectList,transitiveLibrarySet);
						}
					}
				}else if(classPathEntry !=null && classPathEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY){
					if(classPathEntry.getSourceAttachmentPath()!=null){
						String key = classPathEntry.getSourceAttachmentPath().toString();
						transitiveLibrarySet.put(key,classPathEntry.getSourceAttachmentPath());
					}
				}
			}
		}

	}

	
	/**
	 * Returns a IJavaProject if the resource passed in is a IJavaProject.
	 * @param resource
	 * @return
	 */
	public static IJavaProject getJavaProject(IResource resource) {
		
		if (resource instanceof IProject) {
			return getJavaProject((IProject)resource);
		}
		
		return null;
	}

	/**
	 * Given a set of classpath entries, popullate the list of source paths.
	 * @param sourcePaths - The list to be populated
	 * @param workspaceRoot - Workspace root, passed in.
	 * @param classPathEntries
	 */
	public static void getProjectSourcePaths(List<File> sourcePaths, IWorkspaceRoot workspaceRoot, IClasspathEntry[] classPathEntries) {
		if (classPathEntries != null) {
			for (IClasspathEntry classPathEntry : classPathEntries) {
				if (classPathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {

					File sourceFile;
					if (classPathEntry.getPath().segmentCount() == 1) {
						IProject project = workspaceRoot
								.getProject(classPathEntry.getPath()
										.lastSegment());
						sourceFile = project.getLocation().toFile();
					} else {
						IFile sourceIFile = workspaceRoot
								.getFile(classPathEntry.getPath());
						sourceFile = sourceIFile.getLocation().toFile();
					}

					assert (sourceFile.exists());

					sourcePaths.add(sourceFile);

				} 
			}
		}
	}
	
	
	static public IProject getWorkspaceProject(String projectName) throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		// Get the dependent project
		IProject codeGenProject = workspace.getRoot().getProject(projectName);

		if (codeGenProject != null && codeGenProject.isOpen() == false) {
			codeGenProject.open(null);
		}

		return codeGenProject;

	}

	public static IFile getEclipseFile(File f) {
		IPath location = Path.fromOSString(f.getAbsolutePath());

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] workspaceFiles = workspaceRoot.findFilesForLocation(location);

		if (workspaceFiles.length >= 1) {
			return workspaceFiles[0];
		}

		return workspaceRoot.getFileForLocation(location);
	}

	public static IPath getResourceSourcePath(IResource resource,
			IJavaProject jProject) {

		IPath sourceFolderPath = getResourcePath(resource, jProject);
		if (sourceFolderPath != null) {
			return sourceFolderPath.removeFirstSegments(1);
		}
		
		return null;
	}

	
	
	public static IPath getResourcePackagePath(IResource resource,
			IJavaProject jProject) {
		
		IPath resourcePath = getResourcePath(resource, jProject);
		if (resourcePath != null) {
			IPath basePath = resource.getFullPath().removeFirstSegments(resourcePath.segmentCount());
			return basePath.removeLastSegments(1);	// Removed the filename
		}
		return null;
	}
	
	
	public static IPath getCommonResourcePackagePath(IResource resource,
			IJavaProject jProject) {
		
		IPath resourcePath = getResourcePath(resource, jProject);
		if (resourcePath != null) {
			IPath basePath = resource.getFullPath().removeFirstSegments(resourcePath.segmentCount());
			return basePath;
		}
		return null;
	}

	
	
	/**
	 * Find the source folder for this resource. For instance if the resource is
	 * in "/src/main/java/com/ebay/blogs/login" then return "src/main/java"
	 * 
	 * To do this we must iterate through all the package fragments and look for
	 * source folders. If this is the source folder for the resource then remove
	 * the source folder path.
	 * 
	 * @param resource
	 * @param jProject
	 * @return
	 */
	protected static IPath getResourcePath(IResource resource,
			IJavaProject jProject) {
		
		/**
		 */
		if (jProject != null) {
			try {
				IPackageFragmentRoot[] packageRoots = jProject.getPackageFragmentRoots();
				if (packageRoots != null) {
					
					// Look through the package fragment roots
					for (IPackageFragmentRoot packageFragmentRoot : packageRoots) {
						
						// If this is a source folder
						if (packageFragmentRoot.getKind() == IPackageFragmentRoot.K_SOURCE) {
							
							// If this is the source folder for the resource we are interested in.
							if (packageFragmentRoot.getPath().isPrefixOf(resource.getFullPath())) {
								
								return packageFragmentRoot.getPath();
							}
						}
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		
		return null;
		
		
	}
	
    /**
     * 
     */
    public static String computeProviderText(String esfFileName) {
        String titleName = esfFileName.substring(esfFileName.lastIndexOf("/") + 1, esfFileName
                    .indexOf("."));
        if (titleName.length() > 0) {
            titleName = titleName.substring(0, 1).toUpperCase() + titleName.substring(1);
        }
        String result = esfFileName.substring(0, esfFileName.lastIndexOf("/") + 1) + titleName
                    + "EsfCtxProvider#getEsfCtx";
        result = result.replace(File.separatorChar, '.');
        result = result.replace('/', '.');
        while (result.startsWith(".")) {
            result = result.substring(1);
        }
        return result;
    }

}
