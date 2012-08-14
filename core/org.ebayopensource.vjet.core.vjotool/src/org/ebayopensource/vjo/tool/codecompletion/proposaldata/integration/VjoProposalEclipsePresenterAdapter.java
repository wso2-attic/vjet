/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.tool.codecompletion.proposaldata.integration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstFuncType;
import org.ebayopensource.dsf.jst.declaration.JstModifiers;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstPackage;
import org.ebayopensource.dsf.jst.declaration.SynthOlType;
import org.ebayopensource.dsf.jst.token.ILHS;
import org.ebayopensource.dsf.jstojava.translator.TranslateHelper.RenameableSynthJstProxyProp;
import org.ebayopensource.vjo.meta.VjoKeywords;
import org.ebayopensource.vjo.tool.codecompletion.CodeCompletionUtils;
import org.ebayopensource.vjo.tool.codecompletion.IVjoCcProposalData;
import org.ebayopensource.vjo.tool.codecompletion.StringUtils;
import org.ebayopensource.vjo.tool.codecompletion.VjoCcCtx;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoAttributedProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCCVjoUtilityAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcAdvisorConstances;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcAliasProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcCTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcConstructorGenProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcDerivedPropMethodAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcEnumElementAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcFunctionArgumentAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcFunctionGenProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcGlobalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcGlobalExtensionAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcInterfaceProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcKeywordAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcKeywordInCommentProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcKeywordInMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcMTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcNeedsItemProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcObjLiteralAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcOuterPropMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcOverrideProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcOwnerTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcPackageProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcParameterHintAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcParameterProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcPropMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcStaticPropMethodProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcThisProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeNameAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeNameAliasProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcTypeProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.VjoCcVariableProposalAdvisor;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.CompletionConstants;
import org.ebayopensource.vjo.tool.codecompletion.advisor.keyword.IVjoKeywordCompletionData;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.AbstractVjoProposalPresenter;
import org.ebayopensource.vjo.tool.codecompletion.proposaldata.VjoCcAliasProposalData;

/**
 * @author huzhou
 * 
 * This adapter keeps the proposal control in DSFVjoTool, the actual eclipse presenter should composite this adapter
 * and satisifies the generics and DI, which handles eclipse proposal specifics.
 * This adapter creates a list of proposal data that are ready for tools to transform to the actual proposals
 */
public class VjoProposalEclipsePresenterAdapter<DOCUMENT, POINT, IMAGE_DESCRIPTOR, IMAGE, CONTEXT_INFO, INDENTER> extends AbstractVjoProposalPresenter {
	
	private DOCUMENT m_document;
	private POINT m_selectedRange;
	
	private VjoCcCtx m_vjoCcCtx;
	
	private int m_replaceOffset;
	private int m_replaceLength;
	private int m_replacePOffset;
	private int m_replacePLength;
	private int m_offset;
	private String m_lineSeperator;
	
	private IVjoProposalAditionalInfoGeneratorAdapter m_additionalInfoGenerator;
	private IVjoProposalLabelProviderAdapter<IMAGE_DESCRIPTOR, IMAGE> m_labelProvider;
	private IVjoProposalLabelUtilAdapter<DOCUMENT, INDENTER> m_labelUtil;
	private IVjoEclipseContextInformationFactory<IMAGE, CONTEXT_INFO> m_contextInfoFactory;
	private IVjoMethodCompletionProposalFactory<IMAGE, CONTEXT_INFO> m_methodProposalFactory;
	private IVjoTypeCompletionProposalFactory<IMAGE, CONTEXT_INFO> m_typeProposalFactory;
	private String m_indent;
	
	/**
	 * TODO replace methodProposalFactory, typeProposalFactory with generics + interface as well
	 * <p> typical constructor DI for {additionalInfoGenerator, labelProvider, labelUtil, contextInfoFactory, methodProposalFactory, typeProposalFactory}
	 *
	 * </p>
	 * @param vjoCcCtx
	 * @param offset
	 * @param selectedRange
	 * @param document
	 * @param additionalInfoGenerator
	 * @param labelProvider
	 * @param labelUtil
	 * @param contextInfoFactory
	 * @param methodProposalFactory
	 * @param typeProposalFactory
	 */
	public VjoProposalEclipsePresenterAdapter(final VjoCcCtx vjoCcCtx,
			final int offset,
			final POINT selectedRange, 
			final DOCUMENT document,
			final IVjoProposalAditionalInfoGeneratorAdapter additionalInfoGenerator,
			final IVjoProposalLabelProviderAdapter<IMAGE_DESCRIPTOR, IMAGE> labelProvider,
			final IVjoProposalLabelUtilAdapter<DOCUMENT, INDENTER> labelUtil,
			final IVjoEclipseContextInformationFactory<IMAGE, CONTEXT_INFO> contextInfoFactory,
			final IVjoMethodCompletionProposalFactory<IMAGE, CONTEXT_INFO> methodProposalFactory,
			final IVjoTypeCompletionProposalFactory<IMAGE, CONTEXT_INFO> typeProposalFactory){
		
		m_vjoCcCtx = vjoCcCtx;
		m_offset = offset;
		m_selectedRange = selectedRange;
		m_document = document;
		
		if(additionalInfoGenerator == null
				|| labelProvider == null
				|| labelUtil == null
				|| contextInfoFactory == null
				|| methodProposalFactory == null
				|| typeProposalFactory == null){
			throw new IllegalArgumentException("{additionalInfoGenerator, labelProvider, labelUtil, contextInfoFactory, methodProposalFactory, typeProposalFactory}, none of them could be null, please check");
		}
		//initialize the injections
		m_additionalInfoGenerator = additionalInfoGenerator;
		m_labelProvider = labelProvider;
		m_labelUtil = labelUtil;
		m_contextInfoFactory = contextInfoFactory;
		m_methodProposalFactory = methodProposalFactory;
		m_typeProposalFactory = typeProposalFactory;
	}

	public List<? extends IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>> doPresenter(List<IVjoCcProposalData> datas) {

		List<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>> result = new ArrayList<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>>();
		for (Iterator<IVjoCcProposalData> it = datas.iterator(); it.hasNext();) {
			final IVjoCcProposalData data = it.next();
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = genProposal(data);
			if (proposal != null) {
				result.add(proposal);
			} else {
				List<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>> list = genProposals(data);
				if (!list.isEmpty()) {
					result.addAll(list);
				}
			}
		}
		return result;
	}

	/**
	 * this is the main proposal generation entry, which does the following:
	 * <ol>
	 * <li>find proper advisors</li>
	 * <li>choose proposal generation context accordingly</li>
	 * <li>delegate the proposal generation to the context and return the results collected</li>
	 * </ol>
	 * @param data
	 * @return
	 */
	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genProposal(IVjoCcProposalData data) {
		final String advisor = data.getAdvisor();
		if (VjoCcKeywordAdvisor.ID.equals(advisor)
				|| VjoCcKeywordInCommentProposalAdvisor.ID.equals(advisor)
				|| VjoCcKeywordInMethodProposalAdvisor.ID.equals(advisor)
				|| VjoCcThisProposalAdvisor.ID.equals(advisor)
				|| VjoCCVjoUtilityAdvisor.ID.equals(advisor)) {
			return genKeywordProposal(data);
		} else if(VjoCcFunctionArgumentAdvisor.ID.equals(advisor)){
			return genFunctionArgumentProposal(data);
		} else if (VjoCcTypeProposalAdvisor.ID.equals(advisor)
				|| VjoCcOwnerTypeProposalAdvisor.ID.equals(advisor)
				|| VjoCcCTypeProposalAdvisor.ID.equals(advisor)
				|| VjoCcNeedsItemProposalAdvisor.ID.equals(advisor)
				|| VjoCcMTypeProposalAdvisor.ID.equals(advisor)
				|| VjoCcInterfaceProposalAdvisor.ID.equals(advisor)) {
			return genTypeProposal(data);
		} else if (VjoCcPropMethodProposalAdvisor.ID.equals(advisor)
				|| VjoCcDerivedPropMethodAdvisor.ID.equals(advisor)
				|| VjoCcStaticPropMethodProposalAdvisor.ID.equals(advisor)
				|| VjoCcEnumElementAdvisor.ID.equals(advisor)
				|| VjoCcGlobalAdvisor.ID.equals(advisor)) {
			return genPropMethodProposal(data, VjoCcGlobalAdvisor.ID
					.equals(advisor));
		} else if (VjoCcOverrideProposalAdvisor.ID.equals(advisor)) {
			return genOverrideProposal(data);
		} else if (VjoCcParameterProposalAdvisor.ID.equals(advisor)) {
			return genArgumentProposal(data);
		} else if (VjoCcVariableProposalAdvisor.ID.equals(advisor)) {
			return genVariableProposal(data);
		} else if (VjoCcPackageProposalAdvisor.ID.equals(advisor)) {
			return genPackageProposal(data);
		} else if (VjoCcTypeNameAdvisor.ID.equals(advisor)
				|| VjoCcTypeNameAliasProposalAdvisor.ID.equals(advisor)) {
			return genTypeNameProposal(data);
		} else if (VjoCcConstructorGenProposalAdvisor.ID.equals(advisor)) {
			return genConstructorGenProposal(data);
		} else if (VjoCcAliasProposalAdvisor.ID.equals(advisor)) {
			return genAliasProposalAdvisor(data);
		} else if (VjoCcParameterHintAdvisor.ID.equals(advisor)) {
			return getParameterHintAdvisor(data);
		} else if (VjoCcOuterPropMethodProposalAdvisor.ID.equals(advisor)) {
			return genOutPropMethodAdvisor(data);
		} else if (VjoCcGlobalExtensionAdvisor.ID.equals(advisor)) {
			return genPropMethodProposal(data, true);	
		} else if (VjoCcObjLiteralAdvisor.ID.equals(advisor)) {
			return genObjLiteralProposal(data);	
		} else if(VjoAttributedProposalAdvisor.ID.equals(advisor)){
			return genAttributedProposal(data);
		} else {
			return null;
		}
	}


	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genFunctionArgumentProposal(
			IVjoCcProposalData data) {
		Object node = data.getData();
		
		if (node instanceof IJstMethod) {
			final IJstMethod method = (IJstMethod)node;
			String displayString = CodeCompletionUtils.getFullMethodString(method, method.getOwnerType(), false);
			String replaceString = CodeCompletionUtils.getFullFunctionWithoutOverloadingOrNaming(method, getIndent());
			replaceString = replaceString.replaceAll(CodeCompletionUtils.SEPERATE_TOKEN, getLineSeperator());
			replaceString = m_labelUtil.evaluateIndent(replaceString, m_document, m_replaceOffset);
			int cursorPosition = getCursorPosition(replaceString);
			replaceString = replaceCursorPositionToken(replaceString);
			return new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
					m_replaceOffset, m_replaceLength, cursorPosition,
					m_labelProvider
							.getMethodImage(m_labelUtil
									.translateModifers(method.getModifiers().getFlags())), displayString,
					null, null);
		}
		return null;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genOutPropMethodAdvisor(IVjoCcProposalData data) {
		Object node = data.getData();
		if (node instanceof IJstMethod) {
			IJstMethod method = (IJstMethod) node;
			if (m_additionalInfoGenerator.isBrowserNoneNode(method)) {
				return null;
			}
			String replaceString = getOuterMethodProposalReplaceStr(method);
			String displayString = CodeCompletionUtils
					.getFullMethodString(method);
			String externalInfo = m_additionalInfoGenerator
					.getAdditionalPropesalInfo(method);
			int cursorPosition = getCursorPosition(replaceString);
			if (cursorPosition != replaceString.length()) {
				replaceString = replaceCursorPositionToken(replaceString);
			}
			IMAGE image = m_labelProvider.getScriptImage(method);
			String contextArgInfo = CodeCompletionUtils
					.getJstArgsString(method);
			final CONTEXT_INFO proposalContext = StringUtils
					.isBlankOrEmpty(contextArgInfo) ? null
					: m_contextInfoFactory.buildContextInfo(image, displayString,
							contextArgInfo);
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = null;
			List<JstArg> args = method.getArgs();
			if (m_vjoCcCtx.isVjoMethod(method)
					|| (args == null || args.isEmpty())) {
				replaceString = m_labelUtil.evaluateIndent(
						replaceString, m_document, m_replaceOffset);
				proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString, m_replaceOffset,
						m_replaceLength, cursorPosition, image, displayString,
						proposalContext, externalInfo, method);
			} else {
				proposal = m_methodProposalFactory.createMethodCompletionProposal(replaceString,
						m_replaceOffset, m_replaceLength, cursorPosition, image,
						displayString, proposalContext, externalInfo, method);
			}
			return proposal;
		} else if (node instanceof IJstProperty) {
			IJstProperty property = (IJstProperty) node;
			if (m_additionalInfoGenerator.isBrowserNoneNode(property)) {
				return null;
			}
			String replaceString = getOuterPropertyProposalReplaceStr(property);
			String externalInfo = m_additionalInfoGenerator
					.getAdditionalPropesalInfo(property);
			String displayString = CodeCompletionUtils
					.getPropertyString(property);

			int kcursorPostion = replaceString.length();
			if (replaceString.endsWith("})") || replaceString.endsWith("'')")) {
				kcursorPostion = kcursorPostion - 2;
			}
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
					m_replaceOffset, m_replaceLength, kcursorPostion,
					m_labelProvider.getScriptImage(property),
					displayString, null, externalInfo, property);
			return proposal;
		} else if (node instanceof IJstType) {
			IJstType type = (IJstType) node;
			String replaceString = getOuterInnerTypeAsPropertyProposalReplaceStr(type);

			String externalInfo = "";
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
					m_replaceOffset, m_replaceLength, replaceString.length(),
					m_labelProvider.getScriptImage(type),
					CodeCompletionUtils.getTypeDispalyString(type), null,
					externalInfo);
			return proposal;
		}
		return null;

	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> getParameterHintAdvisor(IVjoCcProposalData data) {
		Object node = data.getData();
		if (node instanceof IJstMethod) {
			IJstMethod method = (IJstMethod) node;
			if (m_additionalInfoGenerator.isBrowserNoneNode(method)) {
				return null;
			}
			String displayString = CodeCompletionUtils
					.getFullMethodString(method);
			String externalInfo = m_additionalInfoGenerator
					.getAdditionalPropesalInfo(method);
			String replaceString = "";
			IMAGE image = m_labelProvider.getScriptImage(method);
			String contextArgInfo = CodeCompletionUtils
					.getJstArgsString(method);
			CONTEXT_INFO proposalContext = StringUtils
					.isBlankOrEmpty(contextArgInfo) ? null
					: m_contextInfoFactory.buildContextInfo(image, displayString,
							contextArgInfo);
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
					0, 0, m_offset, image, displayString, proposalContext,
					externalInfo, method);
			return proposal;
		}
		return null;
	}

	public List<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>> genProposals(IVjoCcProposalData data) {
		String advisor = data.getAdvisor();
		if (VjoCcFunctionGenProposalAdvisor.ID.equals(advisor)
				&& m_vjoCcCtx.needAdvisor(VjoCcFunctionGenProposalAdvisor.ID)) {
			return genFunctionProposal(data);
		}
		return Collections.emptyList();
	}

	public List<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>> genFunctionProposal(
			IVjoCcProposalData data) {
		IJstType type = m_vjoCcCtx.getActingType();
		boolean isInterface = type.isInterface()|| type.isMetaType() ;
		String token = m_vjoCcCtx.getActingToken();
		if (CompletionConstants.FUNCTION_NAME_MAIN.equals(token)
				&& !isInterface && m_vjoCcCtx.isInStatic()) {
			return genMainFunctionProposals(data);
		}
		int[] identifiers = null;
		if (isInterface) {
			identifiers = new int[] { JstModifiers.PUBLIC };
		} else {
			identifiers = new int[] { JstModifiers.PUBLIC,
					JstModifiers.PROTECTED, JstModifiers.PRIVATE };
		}
		List<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>> list = new ArrayList<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>>();
		for (int identifier : identifiers) {
			String replaceString = getFunctionString(identifier, token,
					isInterface, getIndent());
			String sidentifier = CodeCompletionUtils.getModifierStr(identifier);
			String name = token + " " + sidentifier + " Function";
			replaceString = m_labelUtil.evaluateIndent(replaceString,
					m_document, m_replaceOffset);
			int cursorPosition = getCursorPosition(replaceString);
			replaceString = replaceCursorPositionToken(replaceString);
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
					m_replaceOffset, m_replaceLength, cursorPosition,
					m_labelProvider
							.getMethodImage(m_labelUtil
									.translateModifers(identifier)), name,
					null, null);
			list.add(proposal);
		}
		return list;
	}

	private String getIndent() {
		return m_indent;
	}
	
	public void setIndent(String indent){
		m_indent = indent;
	}
	

	public List<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>> genMainFunctionProposals(
			IVjoCcProposalData data) {
		List<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>> list = new ArrayList<IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO>>();
		int identifier = JstModifiers.PUBLIC;
		String replaceString = getMainFunctionString( getIndent());
		String sidentifier = CodeCompletionUtils.getModifierStr(identifier);
		String name = "main " + sidentifier + " Function";
		replaceString = m_labelUtil.evaluateIndent(replaceString,
				m_document, m_replaceOffset);
		int cursorPosition = getCursorPosition(replaceString);
		replaceString = replaceCursorPositionToken(replaceString);
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
				m_replaceOffset, m_replaceLength, cursorPosition,
				m_labelProvider.getMethodImage(m_labelUtil
						.translateModifers(identifier)), name, null, null);
		list.add(proposal);
		return list;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genAliasProposalAdvisor(IVjoCcProposalData data) {
		VjoCcAliasProposalData adata = (VjoCcAliasProposalData) data;
		IJstType type = adata.getType();
		String alias = adata.getAlias();
		String replaceString = alias;
		if (!CodeCompletionUtils.isNativeType(type)) {
			// see if need add this.vj$
			if (m_vjoCcCtx.getPositionType() != VjoCcCtx.POSITION_AFTER_THISVJO) {
				replaceString = CompletionConstants.THIS_VJO
						+ CompletionConstants.DOT + replaceString;
			}
		}
		String externalInfo = "";
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
				m_replaceOffset, m_replaceLength, replaceString.length(),
				m_labelProvider.getScriptImage(type), alias, null,
				externalInfo);
		return proposal;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genConstructorGenProposal(
			IVjoCcProposalData data) {
		String name = VjoCcAdvisorConstances.CONSTRUCTOR;
		IJstType type = m_vjoCcCtx.getActingType();
		String modifier = "public";
		if (type != null && type.isEnum()) {
			modifier = "private";
		}
		String cString = getConstructorString(modifier, getIndent());
		String replaceString = m_labelUtil.evaluateIndent(cString,
				m_document, m_replaceOffset);
		int cursorPosition = getCursorPosition(replaceString);
		replaceString = replaceCursorPositionToken(replaceString);
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
				m_replaceOffset, m_replaceLength, cursorPosition, null, name, null,
				null);
		return proposal;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genTypeNameProposal(IVjoCcProposalData data) {
		String name = (String) data.getData();
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(name,
				m_replaceOffset, m_replaceLength, name.length() - m_replaceLength);
		return proposal;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genPackageProposal(IVjoCcProposalData data) {
		JstPackage packge = (JstPackage) data.getData();
		String replaceString = packge.getName();
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
				m_replacePOffset, m_replacePLength, replaceString.length(),
				m_labelProvider.getScriptImage(packge), packge
						.getName(), null, null);
		return proposal;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genVariableProposal(IVjoCcProposalData data) {
		final IJstNode proposed = (IJstNode)data.getData();
		if(proposed instanceof JstArg){
			JstArg arg = (JstArg) data.getData();
			String displayString = arg.getName();
			if (arg.getType() != null) {
				displayString = displayString + " " + arg.getType().getSimpleName();
			} else {
				displayString = displayString + " " + "Object";
			}
			String name = arg.getName();
			String externalInfo = "";
			return new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(arg.getName(),
					m_replaceOffset, m_replaceLength, name.length(),
					m_labelProvider.getScriptImage(arg), displayString,
					null, externalInfo);
		}
		else if(proposed instanceof ILHS){
			ILHS local = (ILHS) data.getData();
			String displayString = local.toLHSText();
			if (local.getType() != null) {
				displayString = displayString + " " + local.getType().getSimpleName();
			} else {
				displayString = displayString + " " + "Object";
			}
			String name = local.toLHSText();
			String externalInfo = "";
			return new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(local.toLHSText(),
					m_replaceOffset, m_replaceLength, name.length(),
					m_labelProvider.getScriptImage(local), displayString,
					null, externalInfo);
		}
		else if(proposed instanceof IJstMethod){
			final IJstMethod method = (IJstMethod) proposed;
			if (m_additionalInfoGenerator.isBrowserNoneNode(method)) {
				return null;
			}
			String displayString = CodeCompletionUtils
					.getFullMethodString(method);
			String externalInfo = m_additionalInfoGenerator
					.getAdditionalPropesalInfo(method);
			//always true as the method came from local var table
			//which is a function in fact
			String replaceString = getMethodProposalReplaceStr(true,
					method, m_vjoCcCtx);
			int cursorPosition = getCursorPosition(replaceString);
			if (cursorPosition != replaceString.length()) {
				replaceString = replaceCursorPositionToken(replaceString);
			}
			IMAGE image = m_labelProvider.getScriptImage(method);
			String contextArgInfo = CodeCompletionUtils
					.getJstArgsString(method);
			CONTEXT_INFO proposalContext = StringUtils
					.isBlankOrEmpty(contextArgInfo) ? null
					: m_contextInfoFactory.buildContextInfo(image, displayString,
							contextArgInfo);
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = null;
			List<JstArg> args = method.getArgs();
			if (m_vjoCcCtx.isVjoMethod(method)
					|| (args == null || args.isEmpty())) {
				replaceString = m_labelUtil.evaluateIndent(
						replaceString, m_document, m_replaceOffset);
				proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString, m_replaceOffset,
						m_replaceLength, cursorPosition, image, displayString,
						proposalContext, externalInfo, method);
			} else {
				proposal = m_methodProposalFactory.createMethodCompletionProposal(replaceString,
						m_replaceOffset, m_replaceLength, cursorPosition, image,
						displayString, proposalContext, externalInfo, method);
			}
			return proposal;
		}
		
		return null;
	}
	
	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genObjLiteralProposal(IVjoCcProposalData data) {
		Object node = data.getData();
		String displayString = null;
		String name = null;
		String externalInfo = "";

		if (node instanceof RenameableSynthJstProxyProp) {
			final IJstProperty pty = (RenameableSynthJstProxyProp)node;
			final String ptyName = pty.getName().getName();
			if(pty.getDoc()!=null){
				externalInfo = pty.getDoc().getComment();
			}
			displayString = CodeCompletionUtils.getPropertyString(pty);
			int cursorPosition = ptyName.length();
			final IJstType ptyType = pty.getType();
			if(ptyType instanceof SynthOlType || ptyType instanceof JstObjectLiteralType){
				cursorPosition--;
			}
			if(ptyType instanceof JstFuncType){
				displayString = CodeCompletionUtils.getFullMethodString(((JstFuncType)ptyType).getFunction(), pty.getOwnerType(), displayString.contains("?"));
			}
		
			return new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(ptyName,
					getReplacementOffset(data), getReplacementLength(data), cursorPosition,
					m_labelProvider.getScriptImage(pty), displayString,
					null, externalInfo);
		}
		
		if (node instanceof IJstMethod) {
			final IJstMethod method = (IJstMethod)node;
			if(method.getDoc()!=null){
				externalInfo = method.getDoc().getComment();
			}
			String replaceString = CodeCompletionUtils.getFullFunctionWithoutOverloading(method, getIndent());
			replaceString = replaceString.replaceAll(CodeCompletionUtils.SEPERATE_TOKEN, getLineSeperator());
			replaceString = m_labelUtil.evaluateIndent(replaceString, m_document, m_replaceOffset);
			int cursorPosition = getCursorPosition(replaceString);
			replaceString = replaceCursorPositionToken(replaceString);
			return new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
					m_replaceOffset, m_replaceLength, cursorPosition,
					m_labelProvider
							.getMethodImage(m_labelUtil
									.translateModifers(method.getModifiers().getFlags())), name,
					null, externalInfo);
		}
		else{
			return null;
		}
	}
	
	/**
	 * for Attributed proposals
	 * @param data
	 * @return
	 */
	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genAttributedProposal(IVjoCcProposalData data) {
		Object node = data.getData();
		if (node instanceof IJstProperty) {
			final IJstProperty pty = (IJstProperty)node;
			final String ptyName = pty.getName().getName();
			final String displayString = CodeCompletionUtils.getPropertyString(pty);
			final String externalInfo = "";
			final String paddingColon = addColon(data, ptyName);
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(paddingColon,
					getReplacementOffset(data), getReplacementLength(data), paddingColon.length(),
					m_labelProvider.getScriptImage(pty), displayString,
					null, externalInfo);
			
			return proposal;
		}
		else if (node instanceof IJstMethod) {
			final IJstMethod mtd = (IJstMethod)node;
			final String mtdName = mtd.getName().getName();
			final String displayString = CodeCompletionUtils.getFullMethodString(mtd);
			final String externalInfo = "";
			final String paddingColon = addColon(data, mtdName);
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(paddingColon,
					getReplacementOffset(data), getReplacementLength(data), paddingColon.length(),
					m_labelProvider.getScriptImage(mtd), displayString,
					null, externalInfo);
			
			return proposal;
		}
		return null;
	}

	private int getReplacementOffset(final IVjoCcProposalData proposalData) {
		return /*proposalData.isAccurateMatch() ? m_replaceOffset : */m_replaceOffset;
	}
	
	private int getReplacementLength(final IVjoCcProposalData proposalData) {
		return /*proposalData.isAccurateMatch() ? */m_replaceLength /*: 0*/;
	}

	private String addColon(IVjoCcProposalData data, final String ptyName) {
		return data.isAccurateMatch() ? ptyName : "::" + ptyName;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genArgumentProposal(IVjoCcProposalData data) {
		JstArg arg = (JstArg) data.getData();
		String replaceString = arg.getName();
		String displayString = replaceString;
		if (arg.getType() != null) {
			displayString = displayString + " - "
					+ arg.getType().getSimpleName();
		} else {
			displayString = displayString + " - " + "Object";
		}
		String externalInfo = "";
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
				m_replaceOffset, m_replaceLength, replaceString.length(),
				m_labelProvider.getScriptImage(arg), displayString,
				null, externalInfo);
		return proposal;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genPropMethodProposal(IVjoCcProposalData data,
			boolean isGlobal) {
		Object node = data.getData();
		
		String methodName = null;
		if (node instanceof IJstProperty){
			final IJstProperty pty = (IJstProperty)node;
			final IJstType ptyType = pty.getType();
			if(ptyType instanceof JstFuncType){
				node = ((JstFuncType)ptyType).getFunction();
				methodName = pty.getName().getName();
			}
		}
		
		if (node instanceof IJstMethod) {
			IJstMethod method = (IJstMethod) node;
			if (methodName == null) {
				methodName = method.getName().getName();
			}
			if (m_additionalInfoGenerator.isBrowserNoneNode(method)) {
				return null;
			}
			String displayString = CodeCompletionUtils
					.getFullMethodString(methodName, method, method.getOwnerType(), false);
			String externalInfo = m_additionalInfoGenerator
					.getAdditionalPropesalInfo(method);
			String replaceString = getMethodProposalReplaceStr(isGlobal,
					method, methodName, m_vjoCcCtx);
			int cursorPosition = getCursorPosition(replaceString);
			if (cursorPosition != replaceString.length()) {
				replaceString = replaceCursorPositionToken(replaceString);
			}
			IMAGE image = m_labelProvider.getScriptImage(method);
			String contextArgInfo = CodeCompletionUtils
					.getJstArgsString(method);
			CONTEXT_INFO proposalContext = StringUtils
					.isBlankOrEmpty(contextArgInfo) ? null
					: m_contextInfoFactory.buildContextInfo(image, displayString,
							contextArgInfo);
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = null;
			List<JstArg> args = method.getArgs();
			if (m_vjoCcCtx.isVjoMethod(method)
					|| (args == null || args.isEmpty())) {
				replaceString = m_labelUtil.evaluateIndent(
						replaceString, m_document, m_replaceOffset);
				proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString, m_replaceOffset,
						m_replaceLength, cursorPosition, image, displayString,
						proposalContext, externalInfo, method);
			} else {
				proposal = m_methodProposalFactory.createMethodCompletionProposal(replaceString,
						m_replaceOffset, m_replaceLength, cursorPosition, image,
						displayString, proposalContext, externalInfo, method);
			}
			return proposal;
		} else if (node instanceof IJstProperty) {
			IJstProperty property = (IJstProperty) node;
			if (m_additionalInfoGenerator.isBrowserNoneNode(property)) {
				return null;
			}

			String externalInfo = m_additionalInfoGenerator
					.getAdditionalPropesalInfo(property);
			String displayString = CodeCompletionUtils
					.getPropertyString(property);
			String replaceString = getPropertyProposalReplaceStr(isGlobal,
					property, m_vjoCcCtx);

			int kcursorPostion = replaceString.length();
			if (replaceString.endsWith("})") || replaceString.endsWith("'')")) {
				kcursorPostion = kcursorPostion - 2;
			}
			final boolean noneIdentifierPropertyAccess = replaceString.endsWith("]");
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
					noneIdentifierPropertyAccess ? m_replaceOffset - 1 : m_replaceOffset, 
					noneIdentifierPropertyAccess ? m_replaceLength + 1 : m_replaceLength, kcursorPostion,
					m_labelProvider.getScriptImage(property),
					displayString, null, externalInfo, property);
			return proposal;
		} else if (node instanceof IJstType) {
			IJstType type = (IJstType) node;
			String replaceString = getInnerTypeAsPropertyProposalReplaceStr(
					isGlobal, type, m_vjoCcCtx);
			String externalInfo = "";
			IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
					m_replaceOffset, m_replaceLength, replaceString.length(),
					m_labelProvider.getScriptImage(type),
					CodeCompletionUtils.getTypeDispalyString(type), null,
					externalInfo);
			return proposal;
		}
		return null;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genOverrideProposal(IVjoCcProposalData data) {
		Object obj = data.getData();
		if (!(obj instanceof IJstMethod)) {
			return null;
		}
		IJstMethod method = (IJstMethod) obj;
		String displayString = CodeCompletionUtils
				.getMethodStringForOverrideProposal(method);
		String replaceString = getReplaceStringForOverrideProposal(method, getIndent());
		replaceString = m_labelUtil.evaluateIndent(replaceString,
				m_document, m_replaceOffset);
		int cursorPosition = getCursorPosition(replaceString);
		replaceString = replaceCursorPositionToken(replaceString);
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
				m_replaceOffset, m_replaceLength, cursorPosition,
				m_labelProvider.getScriptImage(method), displayString,
				null, null);
		return proposal;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genTypeProposal(IVjoCcProposalData data) {
		Object obj = data.getData();
		if (!(obj instanceof IJstType)) {
			return null;
		}
		IJstType type = (IJstType) obj;
		String replaceString = getTypeReplaceString(type, m_vjoCcCtx);
		String externalInfo = "";
		if (m_vjoCcCtx.isInSyntaxScope()
				|| (m_vjoCcCtx.getPositionType() == VjoCcCtx.POSITION_AFTER_THISVJO)) {
			return new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString, m_replaceOffset,
					m_replaceLength, replaceString.length(),
					m_labelProvider.getScriptImage(type),
					CodeCompletionUtils.getTypeDispalyString(type), null,
					externalInfo);
		} else if (m_vjoCcCtx.isInCommentArea()) {
			if (!m_vjoCcCtx.isInInactiveArea()) {
				replaceString = getCommentTypeReplaceString(type, m_vjoCcCtx);
			}
			if (!m_vjoCcCtx.isInInactiveArea() && m_vjoCcCtx.needInsertInactiveNeedsExpr(type)) {
				String insertStr = "";
//				if(!m_vjoCcCtx.getJstType().isFakeType()){
//					insertStr = "//>" + (VjoKeywords.NEEDS + "(" + type.getName()
//					+ ")" + m_lineSeperator);
//				}
					return m_typeProposalFactory.createTypeCompletionProposal(replaceString,
							m_replaceOffset, m_replaceLength, replaceString
						.length()
						+ insertStr.length(), m_vjoCcCtx
						.getNeedsPosition() - 1, insertStr,
				m_labelProvider.getScriptImage(type),
				CodeCompletionUtils.getTypeDispalyString(type), null,
				externalInfo);
				
			}
			return new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString, m_replaceOffset,
					m_replaceLength, replaceString.length(),
					m_labelProvider.getScriptImage(type),
					CodeCompletionUtils.getTypeDispalyString(type), null,
					externalInfo);
		} else {
			String ptoken = m_vjoCcCtx.getActingPackageToken();
			String token = m_vjoCcCtx.getActingToken();
			int tempReplaceOffset = m_replaceOffset;
			int tempReplaceLength = m_replaceLength;
			if (ptoken != null && token != null && !ptoken.equals(token)) {
				tempReplaceLength = m_replaceLength + ptoken.length()
						- token.length();
				tempReplaceOffset = m_replaceOffset + token.length()
						- ptoken.length();
			}
//			if (m_vjoCcCtx.needInsertNeedsExpr(type)) {
//				String insertStr = VjoKeywords.NEEDS + "('" + type.getName()
//						+ "')" + m_lineSeperator + CompletionConstants.DOT;
//				return m_typeProposalFactory.createTypeCompletionProposal(replaceString,
//						tempReplaceOffset, tempReplaceLength, replaceString
//								.length()
//								+ insertStr.length(), m_vjoCcCtx
//								.getNeedsPosition(), insertStr,
//						m_labelProvider.getScriptImage(type),
//						CodeCompletionUtils.getTypeDispalyString(type), null,
//						externalInfo);
//			} else {
				return new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString, tempReplaceOffset,
						tempReplaceLength, replaceString.length(),
						m_labelProvider.getScriptImage(type),
						CodeCompletionUtils.getTypeDispalyString(type), null,
						externalInfo);
//			}
		}

	}


	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genEmbedTypeData(IVjoCcProposalData data) {
		Object obj = data.getData();
		if (!(obj instanceof IJstType)) {
			return null;
		}
		IJstType type = (IJstType) obj;
		String replaceString = type.getSimpleName();
		String externalInfo = "";
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
				m_replaceOffset, m_replaceLength, replaceString.length(),
				m_labelProvider.getScriptImage(type),
				CodeCompletionUtils.getTypeDispalyString(type), null,
				externalInfo);
		return proposal;
	}

	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> genKeywordProposal(IVjoCcProposalData data) {

		IVjoKeywordCompletionData keyword = (IVjoKeywordCompletionData) data;
		String replaceString = getKeywordReplaceString(keyword.getName(),
				m_vjoCcCtx.getTypeName(), m_vjoCcCtx.isInCommentArea(), getIndent());
		int cursorPosition = getCursorPosition(replaceString);
		replaceString = replaceCursorPositionToken(replaceString);
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = new VjoEclipseCompletionProposalAdapter<IMAGE, CONTEXT_INFO>(replaceString,
				m_replaceOffset, m_replaceLength, cursorPosition, null, keyword
						.getName(), null, null);
		return proposal;
	}

	@Deprecated
	public IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> createProposal(int kind, int completionOffset) {
		IVjoEclipseCompletionProposal<IMAGE, CONTEXT_INFO> proposal = null;
		return proposal;
	}
	
	public void setDocument(DOCUMENT mDocument) {
		m_document = mDocument;
	}
	
	
	public void setSelectedRange(POINT mSelectedRange) {
		m_selectedRange = mSelectedRange;
	}
	
	
	public void setVjoCcCtx(VjoCcCtx mVjoCcCtx) {
		m_vjoCcCtx = mVjoCcCtx;
	}
	
	
	public void setReplaceOffset(int mReplaceOffset) {
		m_replaceOffset = mReplaceOffset;
	}
	
	
	public void setReplaceLength(int mReplaceLength) {
		m_replaceLength = mReplaceLength;
	}
	
	
	public void setReplacePOffset(int mReplacePOffset) {
		m_replacePOffset = mReplacePOffset;
	}
	
	
	public void setReplacePLength(int mReplacePLength) {
		m_replacePLength = mReplacePLength;
	}
	
	
	public void setOffset(int mOffset) {
		m_offset = mOffset;
	}
	
	
	public void setLineSeperator(String mLineSeperator) {
		m_lineSeperator = mLineSeperator;
	}
	
	
	public void setAdditionalInfoGenerator(
			IVjoProposalAditionalInfoGeneratorAdapter mAdditionalInfoGenerator) {
		m_additionalInfoGenerator = mAdditionalInfoGenerator;
	}
	
	
	public void setLabelProvider(
			IVjoProposalLabelProviderAdapter<IMAGE_DESCRIPTOR, IMAGE> mLabelProvider) {
		m_labelProvider = mLabelProvider;
	}
	
	
	public void setLabelUtil(
			IVjoProposalLabelUtilAdapter<DOCUMENT, INDENTER> mLabelUtil) {
		m_labelUtil = mLabelUtil;
	}
	
	
	public void setContextInfoFactory(
			IVjoEclipseContextInformationFactory<IMAGE, CONTEXT_INFO> mContextInfoFactory) {
		m_contextInfoFactory = mContextInfoFactory;
	}
	
	
	public void setMethodProposalFactory(
			IVjoMethodCompletionProposalFactory<IMAGE, CONTEXT_INFO> mMethodProposalFactory) {
		m_methodProposalFactory = mMethodProposalFactory;
	}
	
	
	public void setTypeProposalFactory(
			IVjoTypeCompletionProposalFactory<IMAGE, CONTEXT_INFO> mTypeProposalFactory) {
		m_typeProposalFactory = mTypeProposalFactory;
	}
	
	
	public DOCUMENT getDocument() {
		return m_document;
	}
	
	
	public POINT getSelectedRange() {
		return m_selectedRange;
	}
	
	
	public VjoCcCtx getVjoCcCtx() {
		return m_vjoCcCtx;
	}
	
	
	public int getReplaceOffset() {
		return m_replaceOffset;
	}
	
	
	public int getReplaceLength() {
		return m_replaceLength;
	}
	
	
	public int getReplacePOffset() {
		return m_replacePOffset;
	}
	
	
	public int getReplacePLength() {
		return m_replacePLength;
	}
	
	
	public int getOffset() {
		return m_offset;
	}

	@Override
	public String getLineSeperator() {
		if (m_lineSeperator != null) {
			return m_lineSeperator;
		} else {
			return super.getLineSeperator();
		}
	}
	
	public IVjoProposalAditionalInfoGeneratorAdapter getAdditionalInfoGenerator() {
		return m_additionalInfoGenerator;
	}
	
	
	public IVjoProposalLabelProviderAdapter<IMAGE_DESCRIPTOR, IMAGE> getLabelProvider() {
		return m_labelProvider;
	}
	
	
	public IVjoProposalLabelUtilAdapter<DOCUMENT, INDENTER> getLabelUtil() {
		return m_labelUtil;
	}
	
	
	public IVjoEclipseContextInformationFactory<IMAGE, CONTEXT_INFO> getContextInfoFactory() {
		return m_contextInfoFactory;
	}
	
	
	public IVjoMethodCompletionProposalFactory<IMAGE, CONTEXT_INFO> getMethodProposalFactory() {
		return m_methodProposalFactory;
	}
	
	
	public IVjoTypeCompletionProposalFactory<IMAGE, CONTEXT_INFO> getTypeProposalFactory() {
		return m_typeProposalFactory;
	}
}

