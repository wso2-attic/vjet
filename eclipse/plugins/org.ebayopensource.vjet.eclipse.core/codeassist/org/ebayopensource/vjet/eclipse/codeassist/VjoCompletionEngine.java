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
package org.ebayopensource.vjet.eclipse.codeassist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jstojava.translator.JstUtil;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.DictionaryKeywordCompletionEngine;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.IVjoCompletionData;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.JSTCompletionDictionary;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.KeywordCompletionEngine;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.VjoKeywordCompletionResult;
import org.ebayopensource.vjet.eclipse.codeassist.keywords.VjoKeywordFactory;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjet.eclipse.core.parser.VjoParserToJstAndIType;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.mod.codeassist.IAssistParser;
import org.eclipse.dltk.mod.codeassist.ScriptCompletionEngine;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;
import org.eclipse.dltk.mod.core.CompletionContext;
import org.eclipse.dltk.mod.core.CompletionProposal;
import org.eclipse.dltk.mod.core.CompletionRequestor;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.IField;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.internal.core.VjoSourceModule;

/**
 * Vjo implementation of the completion engine.
 * @deprecated by Jack on 2009.10.14
 * @see org.ebayopensource.vjo.tool.codecompletion.engine.VjoCcEngine
 * 
 * 
 */
public class VjoCompletionEngine extends ScriptCompletionEngine {

	private static final List<String> UNRESOLVED_COMPLETIONS = new ArrayList<String>();
	public static final List<String> UNEXIST_TYPES = new ArrayList<String>();

	static {
		UNRESOLVED_COMPLETIONS.add("type");
		UNRESOLVED_COMPLETIONS.add("atype");
		UNRESOLVED_COMPLETIONS.add("makeFinal");
		UNEXIST_TYPES.add(TypeSpaceMgr.GLOBAL);
	}

	/**
	 * Does code completion. Parses source module and resolves type before
	 * completions.
	 * 
	 * @param module
	 *            source module to be parsed.
	 * @param position
	 *            completion position.
	 */
	public void complete(ISourceModule module, int position, int maxLen) {
		JstCompletion completion = null;
		VjoSourceModule vjoModule = (VjoSourceModule) module;
		actualCompletionPosition = position;
		requestor.beginReporting();

		VjoParserToJstAndIType parser = new VjoParserToJstAndIType();
		
		// Modify by Oliver, 2009-12-01, fix findbugs bug.
//		String typeFileName = new String(module
//				.getFileName());

		IJstType type = null;
		VjoSourceModule vmodule = (VjoSourceModule)module;
		// parse and resolve content of the source module
		try {
			type = parser
					.parse(vjoModule.getGroupName(), vmodule.getTypeName().typeName(),  module.getSourceContents(),
							position).getType();

			TypeSpaceMgr.parser().resolve(type);
						
//			IVjoCcEngine engine = new VjoCcEngine(TypeSpaceMgr.parser());
////			VjoCcCtx ctx = ((VjoCcEngine) engine).genCcContext(vjoModule
////					.getGroupName(), new String(module.getFileName()), module
////					.getSourceContents(), position);
//
//			List<IVjoCcProposalData> list = engine.complete(vjoModule.getGroupName(), new String(module
//					.getFileName()), module.getSourceContents(), position);
//			printProposal(list);
//			testPosition(type);
			completion = getCompletion(type, position, position);
		} catch (Exception e) {
			// TODO can we add fake empty error code completion just to notify
			// that we had completion error
			VjetPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, VjetPlugin.PLUGIN_ID,
							IStatus.ERROR, "Error during code completion", e));
			DLTKCore.error(e.toString(), e);
		}

		// process keyword completions
		doKeywordProposal(module, position, completion);

		if (completion != null) {
			// process member completions
			List<CompletionProposal> proposals = JSTCompletionDictionary
					.getCompletions(module, position, completion);

			CompletionContext context = new CompletionContext();
			this.requestor.acceptContext(context);

			for (CompletionProposal completionProposal : proposals) {

				if (isEmptyRange(completionProposal)) {

					completionProposal.setReplaceRange(
							getReplaceStart(completion), this.endPosition
									- this.offset);
				}

				this.requestor.accept(completionProposal);
			}
		}

		requestor.endReporting();
		
	}

	private int getReplaceStart(JstCompletion completion) {
		String token = completion.getCompositeToken();
		if (token != null
				&& token.startsWith(VjoKeywordFactory.KWD_VJ$.getName())) {
			int index = token.lastIndexOf(".");
			return this.startPosition + index + 1;
		} else {
			return this.startPosition - this.offset;
		}
	}

	private boolean isEmptyRange(CompletionProposal completionProposal) {
		return completionProposal.getReplaceEnd() == 0
				&& completionProposal.getReplaceStart() == 0;
	}

	/**
	 * Perform keyword proposal using specified {@link ISourceModule} object,
	 * current completion position and {@link JstCompletion} object. Creates
	 * completion list using {@link KeywordCompletionEngine} object.
	 * 
	 * @param sourceModule
	 *            {@link ISourceModule} object.
	 * @param position
	 *            current completion position.
	 * @param completion
	 *            {@link JstCompletion} object.
	 */
	private void doKeywordProposal(ISourceModule sourceModule, int position,
			JstCompletion completion) {

		KeywordCompletionEngine engine = new DictionaryKeywordCompletionEngine(
				sourceModule, position, completion);

		VjoKeywordCompletionResult result = engine.getCompletionResult();
		if (result != null) {
			List<IVjoCompletionData> keywords = result.getKeywords();

			String token = completion.getToken();
			this.setSourceRange(position - token.length(), position);

			createProposals(keywords);
		}
	}

	/**
	 * Create proposals from the list of the {@link IVjoCompletionData} objects.
	 * Created proposals accepts {@link CompletionRequestor} object.
	 * 
	 * @param keywords
	 *            list of the {@link IVjoCompletionData} objects.
	 */
	private void createProposals(List<IVjoCompletionData> keywords) {
		Iterator<IVjoCompletionData> iterator = keywords.iterator();
		// Jack, move it from while loop, based on the comment of
		// "acceptContext"
		CompletionContext context = new CompletionContext();
		this.requestor.acceptContext(context);
		while (iterator.hasNext()) {

			IVjoCompletionData keyword = iterator.next();

			// disable unresolved completions
			if (getUnresolvedCompletions().contains(keyword.getName())) {
				iterator.remove();
				continue;
			}

			if (!requestor.isIgnored(keyword.getType())) {

				CompletionProposal proposal = createProposal(keyword.getType(),
						actualCompletionPosition);

				char[] completion = keyword.toString().toCharArray();
				proposal.setCompletion(completion);

				proposal.setRelevance(keyword.getRelevance());

				proposal.setName(keyword.getName().toCharArray());
				proposal.setFlags(keyword.getFlags());

				if (keyword.isProposal()) {
					proposal.setReplaceRange(this.startPosition - this.offset,
							this.endPosition - this.offset);
				}

				this.requestor.accept(proposal);

			}
		}
	}

	@Override
	protected int getEndOfEmptyToken() {
		return 0;
	}

	@Override
	public IAssistParser getParser() {
		return null;
	}

	@Override
	protected String processFieldName(IField field, String token) {
		return field.getElementName();
	}

	@Override
	protected String processMethodName(IMethod method, String token) {
		return method.getElementName();
	}

	@Override
	protected String processTypeName(IType type, String token) {
		return type.getFullyQualifiedName().replace('/', '.');
	}

	private JstCompletion getCompletion(IJstType type, int startOffset,
			int endOffset) {
		Collection<? extends IJstNode> fields = type.getChildren(); // TODO this
		// may not
		// work for
		// sub
		// children?
		for (IJstNode field : fields) {
			if (field instanceof JstCompletion) {
				JstCompletion completion = (JstCompletion) field;
				JstSource source = completion.getSource();
				if (JstUtil.includes(source, startOffset, endOffset)) {
					return completion;
				}
			}
		}
		return null;
	}

	private List<String> getUnresolvedCompletions() {
		return UNRESOLVED_COMPLETIONS;
	}

	public void testPosition(IJstType type) {
		System.out.println("Begin report ... ");
		int startOffset = type.getSource().getStartOffSet();
		int endOffset = type.getSource().getEndOffSet();
		// for (int i = 0; i < 1000; i++) {
		// Object node = JstUtil.getNode(type, i, i);
		// String s = null;
		// if (node != null && node instanceof IJstNode) {
		// IJstNode jnode = (IJstNode) node;
		// s = jnode.getClass().getName() + " at "
		// + jnode.getSource().getRow() + ":"
		// + jnode.getSource().getColumn() + ":"
		// + jnode.getSource().getLength();
		// System.out.println(i + "\t: \t" + s);
		//
		// }
		//
		// }
		// System.out.println("Complete report ... ");
		printChildNode(type, 0);
	}

	private void printChildNode(IJstNode node, int i) {
		List<? extends IJstNode> nodes = node.getChildren();
		for (int j = 0; j < i; j++) {
			System.out.print("\t");
		}
		if (node != null && node instanceof IJstNode) {
			IJstNode jnode = (IJstNode) node;
			String s = jnode.getClass().getName();
			if (jnode.getSource() != null) {
				s = s + " at " + jnode.getSource().getStartOffSet() + ":"
						+ jnode.getSource().getEndOffSet() + ":"
						+ jnode.getSource().getLength() + ">"
						+ node.getSource().getRow();
				System.out.println(i + "\t: \t" + s);
			}

		}
		// System.out.println(node.getClass().getName());
		Iterator<? extends IJstNode> it = nodes.iterator();
		while (it.hasNext()) {
			printChildNode(it.next(), i + 1);
		}
	}
}
