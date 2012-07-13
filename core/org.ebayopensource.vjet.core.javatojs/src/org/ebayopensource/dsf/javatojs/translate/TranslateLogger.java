/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import org.ebayopensource.dsf.common.tracer.TraceUtil;
import org.ebayopensource.dsf.javatojs.parse.BaseTypeVisitor;
import org.ebayopensource.dsf.javatojs.report.ErrorReportPolicy;
import org.ebayopensource.dsf.javatojs.report.ErrorReporter;
import org.ebayopensource.dsf.javatojs.trace.TranslateError;
import org.ebayopensource.dsf.javatojs.trace.TranslateMsgId;
import org.ebayopensource.dsf.javatojs.util.AstBindingHelper;
import org.ebayopensource.dsf.jst.BaseJstNode;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.logger.LogLevel;

public class TranslateLogger {
	
	private TranslateCtx m_ctx;
	private ErrorReportPolicy m_policy;
	
	public TranslateLogger(ErrorReportPolicy policy){
		m_policy = policy;
		m_ctx = TranslateCtx.ctx();
	}
	
	public void logUnsupportedNode(final BaseTranslator translator, final ASTNode astNode, final BaseJstNode jstNode){
		traceNode(LogLevel.WARN, TranslateMsgId.UNSUPPORTED_NODE, translator, astNode, jstNode);
	}
	
	public void logUnsupportedType(final BaseTranslator translator, final ASTNode type, final JstType ownerType){
		
		logUnhandledType(translator.getErrorReporter(), type.getClass(), type);
		
		traceType(LogLevel.WARN, TranslateMsgId.EXCLUDED_TYPE, translator, type, ownerType);
	}

	public void logUnhandledNode(final BaseTypeVisitor visitor, final ASTNode astNode, final BaseJstNode jstNode){

//		logUnhandledNode(translator.getErrorReporter(), astNode.getClass(), jstNode.getOwnerType().getClass(), astNode);
		
		traceNode(LogLevel.ERROR, TranslateMsgId.UNHANDLED_NODE, visitor, astNode, jstNode);
	}
	
	public void logUnhandledNode(final BaseTranslator translator, final ASTNode astNode, final BaseJstNode jstNode){

		logUnhandledNode(translator.getErrorReporter(), astNode.getClass(), jstNode.getOwnerType().getClass(), astNode);
		
		traceNode(LogLevel.ERROR, TranslateMsgId.UNHANDLED_NODE, translator, astNode, jstNode);
	}
	
	public void logUnhandledNode(final ErrorReporter reporter, 
			final Class srcType, final Class targetType, final ASTNode currNode){
		
//		String message = "Failed to translate from " 
//			+ srcType.getSimpleName() + " to " + targetType.getSimpleName();
//		
//		reporter.report(m_policy.getUnsupportedTypeLevel(),
//					message, TranslateHelper.getResourceString(currNode), 
//					TranslateHelper.getLineNumber(currNode),
//					TranslateHelper.getColumnNumber(currNode));
	}
	
	public void logUnhandledType(final BaseTranslator translator, final ASTNode type, final BaseJstNode jstNode){
		
		logUnhandledType(translator.getErrorReporter(), type.getClass(), type);
		
		traceType(LogLevel.ERROR, TranslateMsgId.UNHANDLED_TYPE, translator, type, jstNode);
	}

	public void logUnhandledType(final ErrorReporter reporter, 
			final Class srcDataType, final ASTNode type){
		String message = "Data type '" + type + "' is not supported";
		reporter.report(m_policy.getUnsupportedDataTypeLevel(), 
				message, TranslateHelper.getResourceString(type), 
				TranslateHelper.getLineNumber(type),
				TranslateHelper.getColumnNumber(type));
	}
	
	public void logError(final String msgId, final String msg, 
			final BaseTranslator translator, final ASTNode astNode, final BaseJstNode jstNode){

		log(LogLevel.ERROR, msgId, msg, translator, astNode, jstNode);
	}
	
	public void logWarning(final String msgId, final String msg, 
			final BaseTranslator translator, final ASTNode astNode, final BaseJstNode jstNode){

		log(LogLevel.WARN, msgId, msg, translator, astNode, jstNode);
	}
	
	public void logInfo(final String msgId, final String msg, 
			final BaseTranslator translator, final ASTNode astNode, final BaseJstNode jstNode){

		log(LogLevel.INFO, msgId, msg, translator, astNode, jstNode);
	}
	
	public void log(final LogLevel level, final String msgId, final String msg, 
			final BaseTranslator translator, final ASTNode astNode, final BaseJstNode jstNode){

		if (level == LogLevel.ERROR) {
			logError(translator.getErrorReporter(), msg, astNode);
		}
		else if (level == LogLevel.WARN) {
			logWarning(translator.getErrorReporter(), msg, astNode);
		}
		
		trace(level, msgId, msg, astNode, jstNode);
	}
	
	public void logError(final ErrorReporter reporter, 
			final String msg, final ASTNode currNode){
		reporter.error(msg, TranslateHelper.getResourceString(currNode), 
				TranslateHelper.getLineNumber(currNode),
				TranslateHelper.getColumnNumber(currNode));
	}
	
	public void logWarning(final ErrorReporter reporter, 
			final String msg, final ASTNode currNode){
		reporter.warning(msg, TranslateHelper.getResourceString(currNode), 
				TranslateHelper.getLineNumber(currNode),
				TranslateHelper.getColumnNumber(currNode));
	}
	
	//
	// Private
	//
	public void trace(final LogLevel level, final String msgId, final String msg, final ASTNode astNode, BaseJstNode jstNode){

		TranslationStatus status = m_ctx.getTranslateInfo(jstNode.getOwnerType()).getStatus();
		if (!status.hasError(astNode)){
			TranslateError error = new TranslateError(
					level,
					msgId,
					AstBindingHelper.getSourceName(jstNode), 
					TranslateHelper.getLineNumber(astNode),
					msg);
	
			status.addError(astNode, error);
		}
	}
	
	private void traceNode(final LogLevel level, final String msgId, final Object caller, final ASTNode astNode, final BaseJstNode jstNode){
		
		TranslationStatus status = m_ctx.getTranslateInfo(jstNode.getOwnerType()).getStatus();
		if (!status.hasError(astNode)){
			final Throwable t = new Throwable();
			t.fillInStackTrace();
			
			final String clsName = TraceUtil.getClassName(caller);
			final String methodName = TraceUtil.getMethodName(caller, t);
			
			TranslateError error = new TranslateError(
					level,
					msgId,
					AstBindingHelper.getSourceName(jstNode.getOwnerType()), 
					TranslateHelper.getLineNumber(astNode),
					clsName + "::" + methodName + "(...) '" + astNode.getClass().getSimpleName() + "' not translated");
	
			status.addError(astNode, error);
		}
	}
	
	private void traceType(final LogLevel level, final String msgId, final BaseTranslator translator, final ASTNode astNode, final BaseJstNode jstNode){
		
		TranslationStatus status = m_ctx.getTranslateInfo(jstNode.getOwnerType()).getStatus();
		if (!status.hasError(astNode)){
			final Throwable t = new Throwable();
			t.fillInStackTrace();
			
			final String clsName = TraceUtil.getClassName(translator);
			final String methodName = TraceUtil.getMethodName(translator, t);
			String type = astNode.toString();
			if (astNode instanceof ImportDeclaration){
				type = ((ImportDeclaration)astNode).getName().toString();
			}
			
			TranslateError error = new TranslateError(
					level,
					msgId,
					AstBindingHelper.getSourceName(jstNode.getOwnerType()), 
					TranslateHelper.getLineNumber(astNode),
					clsName + "::" + methodName + "(...) '" + type + "' not translated");
	
			status.addError(astNode, error);
		}
	}
}
