/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.codeassist.keywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jsgen.shared.ids.ScopeIds;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstCompletion;
import org.ebayopensource.dsf.jstojava.translator.robust.completion.JstTypeCompletion;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.eclipse.dltk.mod.compiler.env.ISourceModule;

/**
 * This keyword completion engine obtain {@link IVjoCompletionData} objects from
 * {@link VjoKeywordFactory}. Every {@link IVjoCompletionData} object accept or
 * decline of the specified {@link CompletionAcceptor} object.
 * 
 * 
 * 
 * 
 */
public class DictionaryKeywordCompletionEngine implements
		KeywordCompletionEngine {

	private int position;
	private JstCompletion completion;
	private ISourceModule module;
	private static Map<IVjoCompletionData, CompletionAcceptor> acceptors = new HashMap<IVjoCompletionData, CompletionAcceptor>();

	static {
		acceptors.put(VjoKeywordFactory.KWD_CATCH, new AcceptCatchCompletion());
		acceptors.put(VjoKeywordFactory.KWD_FINALLY,
				new AcceptCatchCompletion());
		acceptors.put(VjoKeywordFactory.KWD_ELSE, new ElseCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_CONTINUE,
				new ContinueCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_BREACK,
				new BreakCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_CASE, new CaseCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_DEFAULT,
				new CaseCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_SYSTEM_OUT,
				new VjoStaticCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_SYSTEM_ERR,
				new VjoStaticCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_PRINTLN,
				new VjoSysoutCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_PRINT,
				new VjoSysoutCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_PRINT_STACK_TRACE,
				new VjoStaticCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_BASE,
				new ThisBaseCompletionAcceptor());
		acceptors.put(VjoKeywordFactory.KWD_VJ$,
				new ThisVj$CompletionAcceptor());

	}

	public DictionaryKeywordCompletionEngine(ISourceModule sourceModule,
			int position, JstCompletion completion) {
		this.position = position;
		this.module = sourceModule;
		this.completion = completion;
	}

	/**
	 * Creates and returns {@link VjoKeywordCompletionResult} object. Calls
	 * {@link DictionaryKeywordCompletionEngine#createCompletaionData(ISourceModule, JstCompletion)}
	 * methods for creation list of the {@link IVjoCompletionData} objects.
	 */
	public VjoKeywordCompletionResult getCompletionResult() {

		VjoKeywordCompletionResult result;

		if (completion != null) {

			List<IVjoCompletionData> list;
			list = createCompletaionData(this.module, completion);

			int offset = completion.getSource().getStartOffSet();

			if (isEmptyInCompletePart(completion)) {
				offset = position;
			}
			String token = completion.getToken();
			int idx = token.lastIndexOf('.');
			if (idx != -1) {
				offset = offset + idx + 1;
			}
			result = new VjoKeywordCompletionResult(list, offset);

		} else {
			result = null;
		}

		return result;
	}

	private boolean isEmptyInCompletePart(JstCompletion completion) {
		return completion.getIncompletePart() != null
				&& completion.getIncompletePart().trim().length() == 0;
	}

	/**
	 * Creates list of the {@link IVjoCompletionData} objects using
	 * {@link ISourceModule} and {@link JstCompletion} object. Calls
	 * {@link DictionaryKeywordCompletionEngine#createGlobalCompletions(JstCompletion)}
	 * for creation global completions.
	 * 
	 * @param sourceModule
	 *            {@link ISourceModule} object.
	 * @param completion
	 *            {@link JstCompletion} object.
	 * @return list of the {@link IVjoCompletionData} objects
	 */
	private List<IVjoCompletionData> createCompletaionData(
			ISourceModule sourceModule, JstCompletion completion) {

		List<IVjoCompletionData> list = new ArrayList<IVjoCompletionData>();
		if (completion != null) {

			String[] strings = completion.getCompletion();
			for (String string : strings) {
				IVjoCompletionData keyword;
				keyword = VjoKeywordFactory.getKeywordByName(string);

				if (keyword != null && keyword instanceof IAutoComplemented) {
					((IAutoComplemented) keyword).autoComplete(CodeassistUtils
							.autoCreateTypeNameFor(sourceModule));
				}

				if (keyword == null) {
					keyword = new DefaultVjoKeyword(string);
				}
				list.add(keyword);
			}
		}

		List<IVjoCompletionData> globals = createGlobalCompletions(completion);
		list.addAll(globals);

		return list;
	}

	/**
	 * Creates list of the global {@link IVjoCompletionData} objects using
	 * {@link JstCompletion} object. Obtains {@link IVjoCompletionData} object
	 * from the {@link VjoKeywordFactory#METHOD_KEYWORDS},{@link VjoKeywordFactory#UTILITY_METHOD_KEYWORDS},
	 * {@link VjoKeywordFactory#GLOBAL_KEYWORDS} static keyword list.
	 * 
	 * @param comp
	 *            {@link JstCompletion} object
	 * @return list of the global {@link IVjoCompletionData} objects
	 */
	private List<IVjoCompletionData> createGlobalCompletions(JstCompletion comp) {

		List<IVjoCompletionData> list = new ArrayList<IVjoCompletionData>();

		String token = comp.getToken();

		if (comp.inScope(ScopeIds.METHOD)) {

			IVjoCompletionData[] datas = VjoKeywordFactory.METHOD_KEYWORDS;
			addCompletions(list, datas, token);

			datas = VjoKeywordFactory.UTILITY_METHOD_KEYWORDS;
			addCompletions(list, datas, token);
		}

		if (comp.isEmptyStack() && comp.getCompletion().length == 0
				&& isTypeCompletion(comp)) {
			IVjoCompletionData[] datas = VjoKeywordFactory.GLOBAL_KEYWORDS;
			addCompletions(list, datas, token);
		}

		return list;
	}

	private boolean isTypeCompletion(JstCompletion comp) {
		return comp.getClass().equals(JstTypeCompletion.class);
	}

	private void addCompletions(List<IVjoCompletionData> list,
			IVjoCompletionData[] datas, String token) {
		for (int i = 0; i < datas.length; i++) {

			IVjoCompletionData vjoCompletionData = datas[i];

			if (token != null && vjoCompletionData.canComplete(token)) {
				addCompletionData(list, vjoCompletionData);
			}
		}
	}

	private void addCompletionData(List<IVjoCompletionData> list,
			IVjoCompletionData data) {

		CompletionAcceptor acceptor = acceptors.get(data);

		if (acceptor != null) {

			acceptor.setSourceModule(module);

			if (acceptor.accept(position, completion)) {
				list.add(data);
			}

		} else {

			list.add(data);
		}
	}

	public int getPosition() {
		return position;
	}

	public ISourceModule getModule() {
		return module;
	}

}
