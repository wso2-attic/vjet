/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.debug.ui.actions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.actions.IVariableValueEditor;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.debug.core.eval.IScriptEvaluationResult;
import org.eclipse.dltk.mod.debug.core.model.IScriptStackFrame;
import org.eclipse.dltk.mod.debug.core.model.IScriptValue;
import org.eclipse.dltk.mod.debug.core.model.IScriptVariable;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptVariable;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.progress.WorkbenchJob;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstDefaultConstructor;
import org.ebayopensource.dsf.jst.declaration.JstVars;
import org.ebayopensource.dsf.jst.expr.AssignExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.ts.GenericVisitor;
import org.ebayopensource.vjet.eclipse.debug.ui.VjetDebugUIPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.VjetDebugPlugin;
import org.ebayopensource.vjet.eclipse.internal.debug.debugger.pref.VjetDebugPreferenceConstants;

/**
 * 
 * 
 *  Ouyang
 * 
 */
public class VjetScriptVariableValueEditor implements IVariableValueEditor {

	private static final String	LINE_SEPARATOR	= System
														.getProperty("line.separator");
	private static final char	QUOT			= '"';
	private static final char	SINGLE_QUOT		= '\'';

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.actions.IVariableValueEditor#editVariable(org.eclipse
	 * .debug.core.model.IVariable, org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public boolean editVariable(IVariable variable, Shell shell) {
		if (!(variable instanceof IScriptVariable)) {
			return false;
		}
		final IScriptVariable scriptVar = (IScriptVariable) variable;
		if (scriptVar.isConstant() || (!variable.supportsValueModification())) {
			return true;
		}

		String input;
		try {
			// get user input value
			input = getInput(variable, shell);

			String varValue = scriptVar.getValue().getValueString();
			if (input == null || varValue.equals(input)) {
				return true;
			}
		} catch (DebugException e) {
			VjetDebugUIPlugin.errorDialog(shell,
					ActionMessages.ChangeVariableValue_errorDialogTitle,
					ActionMessages.ChangeVariableValue_errorDialogMessage, e);
			return true;
		}

		asyncSetValue(scriptVar, input, shell);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.actions.IVariableValueEditor#saveVariable(org.eclipse
	 * .debug.core.model.IVariable, java.lang.String,
	 * org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public boolean saveVariable(IVariable variable, String expression,
			Shell shell) {
		if (!(variable instanceof IScriptVariable)) {
			return false;
		}
		final IScriptVariable scriptVar = (IScriptVariable) variable;
		if (scriptVar.isConstant() || (!variable.supportsValueModification())) {
			return true;
		}

		// check value changed
		try {
			String valueString = scriptVar.getValue().getValueString();
			if (expression.equals(valueString)) {
				return true;
			}

			if (!variable.verifyValue(expression)) {
				return true;
			}
		} catch (DebugException e) {
			VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
		}

		asyncSetValue(scriptVar, expression, shell);
		return true;
	}

	private void asyncSetValue(final IScriptVariable scriptVar,
			final String input, Shell shell) {
		Job job = new WorkbenchJob(shell.getDisplay(), "") {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				setValue(scriptVar, input, getDisplay().getActiveShell());
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();
	}

	private String concate(String[] errorMessages) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < errorMessages.length; i++) {
			b.append(errorMessages[i]).append(LINE_SEPARATOR);
		}
		return b.toString();
	}

	private String convert2JSStringExp(String input) {
		return SINGLE_QUOT + escape(input) + SINGLE_QUOT;
	}

	private String escape(String input) {
		input = input.replaceAll("\\\\", "\\\\" + "\\\\");
		input = input.replaceAll("" + SINGLE_QUOT, "\\\\" + SINGLE_QUOT);
		input = input.replaceAll("" + QUOT, "\\\\" + QUOT);
		return input;
	}

	private IScriptValue evaluate(String input, IScriptStackFrame frame,
			Shell shell) {
		IScriptEvaluationResult evalResult = frame.getScriptThread()
				.getEvaluationEngine().syncEvaluate(input, frame);
		if (evalResult.hasErrors()) {
			String[] errorMessages = evalResult.getErrorMessages();
			VjetDebugUIPlugin.errorDialog(shell,
					ActionMessages.ChangeVariableValue_errorDialogTitle,
					concate(errorMessages), evalResult.getException());
			return null;
		}
		return evalResult.getValue();
	}

	private String getInput(final IVariable variable, Shell shell)
			throws DebugException {
		ChangeJsVariableValueInputDialog inputDialog = new ChangeJsVariableValueInputDialog(
				shell, ActionMessages.ChangeVariableValue_1, MessageFormat
						.format(ActionMessages.ChangeVariableValue_2,
								new Object[] { variable.getName() }), variable
						.getValue().getValueString(), new IInputValidator() { // 
					/**
					 * Returns an error string if the input is invalid
					 */
					public String isValid(String input) {
						try {
							if (variable.verifyValue(input)) {
								return null; // null means valid
							}
						} catch (DebugException exception) {
							return ActionMessages.ChangeVariableValue_3;
						}
						return ActionMessages.ChangeVariableValue_4;
					}
				});
		int flags = inputDialog.open();
		if (Window.CANCEL == flags) {
			return null;
		}
		String input = inputDialog.getValue();
		return input;
	}

	private IScriptStackFrame getStackFrame() {
		return (IScriptStackFrame) DebugUITools.getDebugContext().getAdapter(
				IScriptStackFrame.class);
	}

	private String getTypeNameFromJST(final String varName,
			IScriptStackFrame frame, IVjoSourceModule vjoSourceModule)
			throws BadLocationException, ModelException, DebugException {
		IJstType valueType = getJstType(varName, vjoSourceModule, frame);
		return valueType != null ? valueType.getName() : null;
	}

	private IJstType getJstType(final String varName,
			IVjoSourceModule vjoSourceModule, IScriptStackFrame frame)
			throws BadLocationException, ModelException, DebugException {
		int lineOffset = new Document(vjoSourceModule.getSource())
				.getLineOffset(frame.getLineNumber() - 1);
		IJstType jstType = CodeassistUtils.getJstType(vjoSourceModule);
		if (jstType == null) {
			return null;
		}
		IJstMethod jstMethod = getJstMethod(lineOffset, jstType);
		if (jstMethod == null) {
			return null;
		}
		final IJstType[] results = new IJstType[1];
		jstMethod.accept(new GenericVisitor() {

			@Override
			public void visit(JstArg jstArg) {
				if (varName.equals(jstArg.getName())) {
					results[0] = jstArg.getType();
				}
			}

			@Override
			public void visit(JstVars jstVars) {
				List<AssignExpr> assignments = jstVars.getInitializer()
						.getAssignments();
				for (AssignExpr assignExpr : assignments) {
					IJstNode jstNode = assignExpr.getChildren().get(0);
					if (!(jstNode instanceof JstIdentifier)) {
						continue;
					}
					if (varName.equals(((JstIdentifier) jstNode).getName())) {
						results[0] = jstVars.getType();
						return;
					}
				}

			}

		});
		IJstType valueType = results[0];
		return valueType;
	}

	private IJstMethod getJstMethod(int lineOffset, IJstType jstType) {
		List<IJstMethod> methods = new ArrayList<IJstMethod>();
		methods.addAll(jstType.getInstanceMethods());
		methods.addAll(jstType.getStaticMethods());
		if (!(jstType.getConstructor() instanceof JstDefaultConstructor)) {
			methods.add(jstType.getConstructor());
		}
		return JstUtil.getMethod(lineOffset, methods);
	}

	private boolean isAssignable(String typeName, String evalValue) {
		if (isStringType(typeName)) {
			return true;
		} else if (isNumberType(typeName)) {
			try {
				Double.parseDouble(evalValue);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else if (isBooleanType(typeName)) {
			return "true".equalsIgnoreCase(evalValue)
					|| "false".equalsIgnoreCase(evalValue);
		} else if (isCharType(typeName)) {
			return evalValue.length() == 1;
		} else if ("null".equalsIgnoreCase(evalValue)) {
			return true;
		}
		return false;
	}

	private boolean isCharType(String typeName) {
		return "char".equals(typeName)
				|| "vjo.java.lang.Character".equals(typeName);
	}

	private boolean isBooleanType(String typeName) {
		return "boolean".equals(typeName)
				|| "vjo.java.lang.Boolean".equals(typeName);
	}

	private boolean isNumberType(String typeName) {
		return "Number".equals(typeName) || "int".equals(typeName)
				|| "byte".equals(typeName) || "short".equals(typeName)
				|| "long".equals(typeName) || "float".equals(typeName)
				|| "double".equals(typeName)
				|| "vjo.java.lang.Integer".equals(typeName)
				|| "vjo.java.lang.Byte".equals(typeName)
				|| "vjo.java.lang.Short".equals(typeName)
				|| "vjo.java.lang.Long".equals(typeName)
				|| "vjo.java.lang.Float".equals(typeName)
				|| "vjo.java.lang.Double".equals(typeName);
	}

	private boolean isStringType(String typeName) {
		return "String".equals(typeName)
				|| "vjo.java.lang.String".equals(typeName);
	}

	private void setValue(IScriptVariable variable, String input, Shell shell) {
		if (!enableModicicationConstraints()) {
			try {
				variable.setValue(input);
			} catch (DebugException e) {
				VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
			}
			return;
		}
		IScriptStackFrame frame = getStackFrame();
		Object sourceElement = variable.getLaunch().getSourceLocator()
				.getSourceElement(frame);
		if (sourceElement instanceof IFile) {
			// get type of variable
			IModelElement element = DLTKCore.create((IFile) sourceElement);
			if (element == null || !(element instanceof IVjoSourceModule)) {
				return;
			}
			IVjoSourceModule vjoSourceModule = (IVjoSourceModule) element;
			try {
				final String varName = variable.getName();
				String varTypeName = null;
				if (variable instanceof ScriptVariable) {
					ScriptVariable scriptVar = (ScriptVariable) variable;
					// check if the var is a sub property of a local var in
					// stack frame
					if (scriptVar.isChild()) {
						String fullName = scriptVar.getEvalName();
						String[] propNames = fullName.split("\\.");
						String localVarName = propNames[0];
						IJstType localVarType = getJstType(localVarName,
								vjoSourceModule, frame);
						IJstType varType = getSubVariableJstType(localVarType,
								propNames);
						if (varType != null) {
							varTypeName = varType.getName();
						}
					}
				}
				if (varTypeName == null) {
					// get var name from local var in stack frame directly
					varTypeName = getTypeNameFromJST(varName, frame,
							vjoSourceModule);
				}
				// if cannot find promoted type, set value directly
				if (varTypeName == null) {
					variable.setValue(input);
					return;
				}

				// check value compatibility
				boolean isString = isStringType(varTypeName);
				if (isString) {
					input = convert2JSStringExp(input);
				}
				boolean isChar = isCharType(varTypeName);
				if (isChar) {
					input = convert2Char(input);
				}

				// verify if value is compatiable with type
				IScriptValue evalValue = evaluate(input, frame, shell);
				if (evalValue == null) {
					return;
				}
				String evalValueString = evalValue.getValueString();

				// evaluated value equals the orignal value
				if (evalValueString
						.equals(variable.getValue().getValueString())) {
					return;
				}

				if (!isAssignable(varTypeName, evalValueString)) {
					return;
				}

				// convert evaluated value before set
				if (isString) {
					evalValueString = convert2JSStringExp(evalValueString);
				} else if (isNumberType(varTypeName)) {
					evalValueString = Double.valueOf(evalValueString)
							.toString();
				} else if (isChar) {
					evalValueString = convert2Char(evalValueString);
				}

				variable.setValue(evalValueString);
			} catch (Exception e) {
				VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
			}
		} else {
			// set value directly
			try {
				variable.setValue(input);
			} catch (DebugException e) {
				VjetDebugUIPlugin.error(e.getLocalizedMessage(), e);
			}
		}
	}

	private String convert2Char(String evalValueString) {
		return SINGLE_QUOT + escape(evalValueString.toCharArray()[0] + "")
				+ SINGLE_QUOT;
	}

	private boolean enableModicicationConstraints() {
		return VjetDebugPlugin
				.getDefault()
				.getPreferenceStore()
				.getBoolean(
						VjetDebugPreferenceConstants.PREF_VJET_ENABLE_MODIFICATION_CONSTRAINTS);
	}

	private IJstType getSubVariableJstType(final IJstType varType,
			String[] propNames) {
		IJstType type = varType;
		IJstProperty prop = null;
		for (int i = 1; i < propNames.length && type != null; i++) {
			prop = type.getProperty(propNames[i], false, true);
			if (prop == null) {
				prop = type.getProperty(propNames[i], true, true);
			}
			if (prop == null) {
				break;
			}
			type = prop.getType();
		}
		if (prop == null || type == null) {
			return null;
		}

		return type;
	}

}
