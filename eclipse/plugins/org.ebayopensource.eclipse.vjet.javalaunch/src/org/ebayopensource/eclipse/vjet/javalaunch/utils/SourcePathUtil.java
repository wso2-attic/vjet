package org.ebayopensource.eclipse.vjet.javalaunch.utils;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;

public class SourcePathUtil {

	
	
	/**
	 * @deprecated
	 * @param launchProject
	 * @return
	 * @throws CoreException
	 */
	public static String getSoucePathString(IProject launchProject)
			throws CoreException {
		return getSourcePathString(launchProject);
	}

	/**
	 * Creates a string list of the source paths needed to build in this project.
	 * @param launchProject
	 * @return 
	 * @throws CoreException
	 */
	public static String getSourcePathString(IProject launchProject)
			throws CoreException {

		IJavaProject javaProject = EclipseResourceUtils
				.getJavaProject(launchProject);
		List<File> sourcePaths = new ArrayList<File>(100);

		if (javaProject != null) {

			// Create a list to store all the projects
			Map<String, IJavaProject> transitiveClosureProjectList = new LinkedHashMap<String, IJavaProject>();
			Map<String, IPath> transitiveLibrary = new LinkedHashMap<String, IPath>();

			// Start with the passed in project
			transitiveClosureProjectList.put(javaProject.getElementName(),
					javaProject);

			// Get the transitive clousure of all the projects
			EclipseResourceUtils.getTransitiveClosureDependencies(javaProject,
					transitiveClosureProjectList, transitiveLibrary);

			IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace()
					.getRoot();

			// Add all the source paths of all the projects
			for (IJavaProject project : transitiveClosureProjectList.values()) {

				IClasspathEntry[] classPathEntries = project
						.getResolvedClasspath(true);

				EclipseResourceUtils.getProjectSourcePaths(sourcePaths,
						workspaceRoot, classPathEntries);

			}
			for (IPath path : transitiveLibrary.values()) {
				sourcePaths.add(path.toFile());
			}
		} else {
			return "";
		}
		

		// Convert to semi-colon delimeted string
		StringBuilder sb = new StringBuilder(512);
		for (File sourcePath : sourcePaths) {

			sb.append(sourcePath.getAbsolutePath());
			sb.append(File.pathSeparator);

		}

		// Return the semi-colon list minus the last semi-colon
		return sb.substring(0, sb.length() - 1);
	}

	public static String configureVmArguments(String sourcePath, String vmArgs) {

		if (LaunchUtilPlugin.getConfigureSourcePathEnabled()) {
			vmArgs = removeSourcePath(vmArgs);
			vmArgs = addSourcePath(vmArgs, sourcePath);
		}

		return vmArgs.trim();
	}

	public static String JAVA_SOURCE_PATH_PARAM = "java.source.path";

	public static String getSourcePathParam() {

		return "-D" + JAVA_SOURCE_PATH_PARAM;
	}

	public static String addSourcePath(String vmArgs, String sourcePath) {

		String sourcePathParam = getSourcePathParam();

		int javaSourcePathParamIndexStart = vmArgs.indexOf(sourcePathParam);

		// If arguments are not found then add them
		if (javaSourcePathParamIndexStart < 0) {

			vmArgs = MessageFormat.format("{0} {1}={2}", vmArgs,
					sourcePathParam, sourcePath);
		}

		return vmArgs;
	}

	public static String removeSourcePath(String vmArgs) {

		// In case this is null
		if (vmArgs == null) {
			return "";
		}

		int javaSourcePathParamIndexStart = vmArgs
				.indexOf(getSourcePathParam());

		// If pseudo translation arguments are not found then add them
		if (javaSourcePathParamIndexStart >= 0) {

			int javaSourcePathParamIndexEnd = getEndIndex(vmArgs,
					javaSourcePathParamIndexStart);

			vmArgs = MessageFormat.format("{0} {1}", vmArgs.substring(0,
					javaSourcePathParamIndexStart), vmArgs.substring(
					javaSourcePathParamIndexEnd, vmArgs.length()));
		}

		return vmArgs.trim();
	}

	private static int getEndIndex(String vmArgs,
			int pseudoTranslationArgIndexStart) {

		// Look for the first space
		int endIdx = vmArgs.indexOf(" ", pseudoTranslationArgIndexStart);
		if (endIdx == -1) {
			endIdx = vmArgs.length();
		}

		return endIdx;
	}
	
	public static boolean getConfigureSourcePathEnabled() {
		return LaunchUtilPlugin.getDefault().getPreferenceStore()
		.getBoolean(LaunchUtilPlugin.CONFIGURE_SOURCE_PATH_ENABLED);
		
	}
	
}
