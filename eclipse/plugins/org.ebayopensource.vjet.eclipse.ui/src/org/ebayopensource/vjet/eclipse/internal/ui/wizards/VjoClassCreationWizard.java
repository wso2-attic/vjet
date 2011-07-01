/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.wizards;

import static org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjetWizardMessages.ClassCreationWizard_file_content;

import java.io.ByteArrayInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoSourceModulePage.InterfaceWrapper;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.JSSourceModule;
import org.eclipse.dltk.mod.internal.ui.DLTKUIMessages;
import org.eclipse.dltk.mod.javascript.ui.JavaScriptImages;
import org.eclipse.dltk.mod.ui.CodeFormatterConstants;
import org.eclipse.dltk.mod.ui.DLTKUIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * VJET wizard for creating a new vjo class.
 * 
 */
public class VjoClassCreationWizard extends VjoSourceModuleWizard {

	public static final String PROPS = "props";
	public static final String PROTOS = "protos";
	public static final String EMPTY = "";
	private static final String INHERITS = ".inherits(''{0}'')";
	private static final String SATISFIES = ".satisfies(''{0}'')";
	private static final String INDENTATION = "\r\n\t";

	private static final String ATYPE = "atype";
	private static final String TYPE = "ctype";

	// private VjoClassCreationPage page;

	public VjoClassCreationWizard() {
		super();
		setDefaultPageImageDescriptor(JavaScriptImages.DESC_WIZBAN_PROJECT_CREATION);
		setDialogSettings(DLTKUIPlugin.getDefault().getDialogSettings());
		setWindowTitle(VjetWizardMessages.ClassCreationWizard_title);
	}

	@Override
	protected VjoSourceModulePage createVjoSourceModulePage() {
		return new VjoClassCreationPage();
		// return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ebayopensource.vjet.eclipse.internal.ui.wizards.VjoSourceModuleWizard#finishPage(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void finishPage(IProgressMonitor monitor)
			throws InterruptedException, CoreException {
		super.finishPage(monitor);
		JSSourceModule module = (JSSourceModule) getCreatedElement();
		IFile file = (IFile) module.getResource();
		byte[] content = getInitialFileContent(file);
		ByteArrayInputStream stream = new ByteArrayInputStream(content);
		file.appendContents(stream, false, false, monitor);
	}

	/**
	 * Returns the initial file content for creating class.
	 * 
	 * @param file
	 *            {@link IFile}
	 * 
	 * @return an array of bytes of the initial content for created class.
	 * 
	 * @throws {@link ModelException}
	 */
	protected byte[] getInitialFileContent(IFile file) throws ModelException {
		// Add by Oliver. The old file content template is
		// "ClassCreationWizard_file_content =
		// vjo.{0}(''{1}''){5}{2}{6}\r\n.{4}('{'\r\n {3}
		// \r\n'}')\r\n.{7}('{'\r\n{9}{8}\r\n'}')\r\n.endType();"
		String content = getFileContentTamplate();
		String buffer = CodeassistUtils.getClassName(file);

		// Modify by Oliver. This method--"getModifierIndex()" comes from its
		// parent wizard.
		String modifiers = getModifierIndex();
		String modifierDesc = formatModifier(modifiers);
		modifierDesc += "\r\n";

		String type = getClassType();
		// if ("ctype".equals(type) && modifiers.contains("abstract")) {
		// type = ATYPE;
		// }

		String inheritance = getInheritance();
		if (inheritance.length() > 0) {
			inheritance += "\r\n";
		}

		String satsifies = getSatisfies();
		if (satsifies.length() > 0) {
			satsifies += "\r\n";
		}

		String constStub = getConstructorStub();
		if (constStub.length() > 0) {
			constStub += "\r\n";
		}

		String satisfiesMethods = getSastisfiesMethods();
		if (satisfiesMethods.length() > 0) {
			// If there is constructor after the methods, will add comma.
			if (constStub.length() > 0) {
				satisfiesMethods += ",";
			}
			satisfiesMethods += "\r\n";
		}
		String methodStub = getMethodStub();
		String blockName = getBlockName();

		// Modify by Oliver. When create etype, the brace that follow by
		// '.values' should be '('.
		if ("etype".equals(type)) {
			String valueSection = ".values('\r\n\t''''\r\n')\r\n";
			int position = content.indexOf(".{4}(");
			String sufContent = content.substring(0, position);
			String newContent = sufContent + valueSection
					+ content.substring(content.indexOf(".{4}("));
			content = newContent;
			// if (content.indexOf("('{'") > 0) {
			// content = content.replace("('{'", "(");
			// }
			// if (content.indexOf("'}')") > 0) {
			// content = content.replace("'}')", ")");
			// }
			// int position = content.indexOf(".{7}(");
			// content = content.substring(0, position)
			// + ".{7}('{'\r\n {8} \r\n'}')\r\n.endType();";
			//
			// // Modify by Oliver. 2009-06-09. Add ' ' into the values block of
			// // EType.Otherwise there is a validation error about the empty
			// // .value block.
			// methodStub = "\' \'";
		}

		// Add by Oliver.2009-06-22. For OType code format.
		if ("otype".equals(type)) {
			int position = content.indexOf(".{7}(");
			content = content.substring(0, position) + ".endType();";
		}

		IPreferenceStore store = VjetUIPlugin.getDefault().getPreferenceStore();
		String tabStyle = store
				.getString(CodeFormatterConstants.FORMATTER_TAB_CHAR);

		// if (CodeFormatterConstants.SPACE.equals(tabStyle)){
		// content = content.replace("\r", "\t").trim();
		// }
		Object[] objects = new Object[] { type, buffer, inheritance,
				methodStub, blockName, modifierDesc, satsifies, PROTOS,
				constStub, satisfiesMethods };

		return MessageFormat.format(content, objects).getBytes();
	}

	private static boolean isMethodEquivalent(IJstMethod m1, IJstMethod m2) {
		if (!m1.getName().getName().equals(m2.getName().getName())) {
			return false;
		}
		List<JstArg> args1 = m1.getArgs();
		List<JstArg> args2 = m2.getArgs();
		if (args1.size() == args2.size()) {
			for (int i = 0; i < args1.size(); i++) {
				if (!args1.get(i).getType().getName().equals(
						args2.get(i).getType().getName())) {
					return false;
				}
			}

			return true;
		}
		return false;
	}

	private static boolean isContainMethod(List<IJstMethod> ms, IJstMethod m) {
		for (IJstMethod jstMethod : ms) {
			if (isMethodEquivalent(jstMethod, m)) {
				return true;
			}
		}
		return false;
	}

	private String getSastisfiesMethods() {
		if (!(page instanceof VjoClassCreationPage)) {
			return EMPTY;
		}
		List elms = page.getSuperInterfacesDialogField().getElements();
		IJstType baseType = ((VjoClassCreationPage) page).getBaseType();

		List<IJstMethod> canOverrideMethods = new ArrayList<IJstMethod>();
		if (baseType != null) {
			List baseMethods = baseType.getMethods(false, true);
			for (Iterator iterator = baseMethods.iterator(); iterator.hasNext();) {
				IJstMethod m = (IJstMethod) iterator.next();
				if (m.isProtected() || m.isPublic()) {
					canOverrideMethods.add(m);
				}
			}
		}

		String satisfiesMethods = EMPTY;
		List<IJstMethod> toBeAddedMethods = new ArrayList<IJstMethod>();
		for (Object object : elms) {
			// InterfaceWrapper
			InterfaceWrapper iw = (InterfaceWrapper) object;
			IJstType type = iw.getSourceType();
			List<? extends IJstMethod> ml = type.getMethods(false, true);
			for (IJstMethod jstMethod : ml) {
				if ((!isContainMethod(canOverrideMethods, jstMethod))
						&& (!isContainMethod(toBeAddedMethods, jstMethod))) {
					toBeAddedMethods.add(jstMethod);
				}
			}
		}

		for (IJstMethod jstMethod2 : toBeAddedMethods) {
			satisfiesMethods += getReplaceStringForOverrideProposal(jstMethod2);
			satisfiesMethods += ",\r\n";
		}
		int length = satisfiesMethods.lastIndexOf(",");
		return length == -1 ? satisfiesMethods : satisfiesMethods.substring(0,
				length);

	}

	/**
	 * Generate a string for a given method. There is also
	 * org.ebayopensource.vjet.eclipse.internal.ui.text.completion.VjoProposalLabelUtil.getJstCommentStringR(method)
	 * for this function. I extract it here to add some indentation.
	 * 
	 * @param method
	 * @return
	 */
	public static String getReplaceStringForOverrideProposal(IJstMethod method) {
		StringBuffer buffer = new StringBuffer();
		String commStr = CodeCompletionUtils.getJstCommentStringR(method);
		buffer.append(INDENTATION + "//>" + commStr);
		// buffer.append("\n");
		String name = method.getName().getName();
		buffer.append(INDENTATION + name);
		buffer.append(" : function(");
		String aname = CodeCompletionUtils.getJstArgsStringR(method);
		if (aname.length() > 0) {
			buffer.append(aname);
		}
		buffer.append(")");
		// buffer.append("{\n " + INDENTATION + "}");

		// fix bug 3861. Add the return part code.BEGIN.
		String returnStatement = null;
		String defaultReturnValue = JstTypeDefaultValueHelper
				.getDefaultValue(method.getRtnType());
		if (defaultReturnValue == null)
			returnStatement = "\t\t" + INDENTATION;
		else
			returnStatement = "\t\treturn " + defaultReturnValue + ";\r\n\t";
		buffer.append("{\r\n" + returnStatement + "}");
		// Add the return part code.END.
		return buffer.toString();
	}

	protected String getBlockName() {
		return PROPS;
	}

	protected String getFileContentTamplate() {
		return ClassCreationWizard_file_content;
	}

	protected String getInheritance() {
		String className = ((VjoClassCreationPage) page)
				.getSuperclassFieldText();
		String inheritance = EMPTY;
		if (className != null && !className.trim().equals(EMPTY)) {
			inheritance = MessageFormat.format(INHERITS, className);
		}
		return inheritance;
	}

	protected String getSatisfies() {
		List elms = page.getSuperInterfacesDialogField().getElements();
		String satisfies = EMPTY;
		for (Object object : elms) {
			String interfacename = object.toString();
			if (interfacename != null && !interfacename.trim().equals(EMPTY)) {
				if (VjoInterfaceCreationWizard.ITYPE.equals(getClassType())) {
					// fix bug 4995
					if (satisfies.equals(EMPTY))
						satisfies += MessageFormat.format(INHERITS,
								interfacename);
					else
						// Modify by Oliver for the '\r\n' format.2009-06-18
						satisfies += "\r\n"
								+ MessageFormat.format(INHERITS, interfacename);
				} else {
					// fix bug 4217
					if (satisfies.equals(EMPTY))
						satisfies += MessageFormat.format(SATISFIES,
								interfacename);
					else
						// Modify by Oliver for the '\r\n' format.2009-06-18
						satisfies += "\r\n"
								+ MessageFormat
										.format(SATISFIES, interfacename);
				}
			}
		}

		return satisfies;

	}

	protected String getClassType() {
		String type = TYPE;
		// if (page.isAbstractButtonSelected()) {
		// type = ATYPE;
		// }

		return type;
	}

	/**
	 * @param modifier
	 * @return
	 */
	protected String formatModifier(String modifier) {
		String modDesc = "";
		String preFix = " //< ";
		if (modifier.contains("public")) {
			modDesc = "public";
		}
		if (modifier.contains("default")) {
			// This is for "//< default"
			modDesc = "";
		}
		if (modifier.contains("private")) {
			modDesc = "private";
		}
		if (modifier.contains("protected")) {
			modDesc = "protected";
		}

		if (modifier.contains("abstract")) {
			modDesc += " abstract";
		}

		if (modifier.contains("final")) {
			modDesc += " final";
		}

		// Sometimes no any modifier, so it is empty returned string.
		if (modDesc.trim().length() > 0) {
			modDesc = preFix + modDesc;
		}
		return modDesc;
	}

	protected String getOpenTypeErrorMessage() {
		return DLTKUIMessages.OpenTypeAction_errorMessage;
	}

	protected String getOpenTypeErrorTitle() {
		return DLTKUIMessages.OpenTypeAction_errorTitle;
	}

	private String getMethodStub() {
		String content = EMPTY;
		// check the selection state of the 'Create Main' checkbox
		if (page != null && (page instanceof VjoClassCreationPage)
				&& ((VjoClassCreationPage) page).isMethodStubsButtonSelected())
			content = VjetWizardMessages.ClassCreationWizard_main_method_content;

		return content;
	}

	private String getConstructorStub() {
		String content = EMPTY;

		// check the selection state of the 'Constructor' checkbox
		if (page != null && (page instanceof VjoClassCreationPage)
				&& ((VjoClassCreationPage) page).isConstructorButtonSelected())
			content += VjetWizardMessages.ClassCreationWizard_constructor_content;

		return content;
	}

}
