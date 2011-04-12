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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.ebayopensource.vjo.meta.VjoKeywords;

/**
 * Used for creating vjo keywords.
 */
public class VjoKeywordFactory {

	public static final IVjoCompletionData KWD_NEEDS = new NeedsKeyword();
	public static final IVjoCompletionData KWD_NEEDSLIB = new NeedsLibKeyword();

	public static final IVjoCompletionData KWD_VJOTYPE = new VjoTypeKeyword();
	public static final IVjoCompletionData KWD_VJOCTYPE = new VjoCTypeKeyword();
//	public static final IVjoCompletionData KWD_VJOATYPE = new VjoATypeKeyword();
	public static final IVjoCompletionData KWD_VJOITYPE = new VjoITypeKeyword();
	public static final IVjoCompletionData KWD_VJOETYPE = new VjoETypeKeyword();
	public static final IVjoCompletionData KWD_VJOMTYPE = new VjoMTypeKeyword();
	public static final IVjoCompletionData KWD_VJOOTYPE = new VjoOTypeKeyword();

	public static final IVjoCompletionData KWD_INHERITS = new InherithsKeyword();
	public static final IVjoCompletionData KWD_SATISFIES = new SatisfiesKeyword();
	public static final IVjoCompletionData KWD_EXPECTS = new ExpectsKeyword();

	public static final IVjoCompletionData KWD_MIXIN = new MixinKeyword();
	public static final IVjoCompletionData KWD_MIXINPROPS = new MixinPropsKeyword();
//	public static final IVjoCompletionData KWD_MAKEFINAL = new MakeFinalKeyword();
//	public static final IVjoCompletionData KWD_SINGLETON = new SingletonKeyword();
	public static final IVjoCompletionData KWD_PROTOS = new ProtosKeyword();
	public static final IVjoCompletionData KWD_PROPS = new PropsKeyword();
	public static final IVjoCompletionData KWD_INITS = new InitsKeyword();
	public static final IVjoCompletionData KWD_FUNC = new FuncKeyword();
	public static final IVjoCompletionData KWD_VALUES = new ValuesKeyword();

	public static final IVjoCompletionData KWD_WHILE = new WhileKeyword();
	public static final IVjoCompletionData KWD_DO = new DoKeyword();
	public static final IVjoCompletionData KWD_FOR = new ForKeyword();
	public static final IVjoCompletionData KWD_THIS = new ThisKeyword();
	public static final IVjoCompletionData KWD_RETURN = new ReturnKeyword();
	public static final IVjoCompletionData KWD_VAR = new VarKeyword();
	public static final IVjoCompletionData KWD_NEW = new NewKeyword();
	public static final IVjoCompletionData KWD_IF = new IfKeyword();
	public static final IVjoCompletionData KWD_ELSE = new ElseKeyword();
	public static final IVjoCompletionData KWD_CONTINUE = new ContinueKeyword();
	public static final IVjoCompletionData KWD_BREACK = new BreakKeyword();
	public static final IVjoCompletionData KWD_SWITCH = new SwitchKeyword();
	public static final IVjoCompletionData KWD_CASE = new CaseKeyword();
	public static final IVjoCompletionData KWD_DEFAULT = new DefaultKeyword();
	public static final IVjoCompletionData KWD_INSTANCE_OF = new InstanceOfKeyword();
	public static final IVjoCompletionData KWD_TYPE_OF = new TypeOfKeyword();

	public static final IVjoCompletionData KWD_BASE = new BaseKeyword();
	public static final IVjoCompletionData KWD_VJ$ = new Vj$Keyword();
	
	public static final IVjoCompletionData KWD_SYSTEM_OUT = new SysoutKeyword();
	public static final IVjoCompletionData KWD_SYSTEM_ERR = new SyserrKeyword();
	public static final IVjoCompletionData KWD_PRINTLN = new PrintlnKeyword();
	public static final IVjoCompletionData KWD_PRINT = new PrintKeyword();
	public static final IVjoCompletionData KWD_PRINT_STACK_TRACE = new PrintStackTrace();

	public static final IVjoCompletionData KWD_END_TYPE = new EndTypeKeyword();

	private static final IVjoCompletionData KWD_WHILE_AS_COMPLEMENTED = new AbstractVjoCompletionData(
			"while", "();") {

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}

		public boolean isComplementedPart() {
			return true;
		}

	};

	public static final IVjoCompletionData KWD_TRY = new TryKeyword();

	public static final IVjoCompletionData KWD_CATCH = new CatchKeyword();

	public static final IVjoCompletionData KWD_FINALLY = new FinallyKeyword();

	public static final IVjoCompletionData KWD_VJO_UTILITY = new VjoUtilityKeyword();

	protected static final IVjoCompletionData[] UTILITY_METHOD_KEYWORDS = new IVjoCompletionData[] {
			KWD_BASE, KWD_VJ$, KWD_SYSTEM_OUT, KWD_SYSTEM_ERR, KWD_PRINTLN, KWD_PRINT,
			KWD_PRINT_STACK_TRACE };

	protected final static IVjoCompletionData[] METHOD_KEYWORDS = new IVjoCompletionData[] {
			KWD_CATCH, KWD_DO, KWD_ELSE, KWD_FINALLY, KWD_FOR, KWD_IF, KWD_NEW,
			KWD_RETURN, KWD_THIS, KWD_TRY, KWD_VAR, KWD_WHILE, KWD_CONTINUE,
			KWD_SWITCH, KWD_BREACK, KWD_CASE, KWD_DEFAULT, KWD_INSTANCE_OF,
			KWD_TYPE_OF, KWD_VJO_UTILITY };

	protected final static IVjoCompletionData[] GLOBAL_KEYWORDS = new IVjoCompletionData[] {
			KWD_NEEDS, KWD_PROTOS, KWD_PROPS, KWD_VJOCTYPE,
			KWD_VJOITYPE, KWD_VJOTYPE, KWD_VJOMTYPE, KWD_VJOOTYPE, KWD_INITS, KWD_INHERITS,
			KWD_SATISFIES, KWD_EXPECTS, KWD_MIXIN, KWD_MIXINPROPS,
			KWD_VJO_UTILITY, KWD_VALUES };

	private static class NeedsLibKeyword extends AbstractVjoCompletionData
			implements ITopLevel {

		NeedsLibKeyword() {
			super("vjo.needsLib", "(\'\');", false, false, true);
		}

		public List<IVjoCompletionData> getPeerKeywords() {
			return Arrays.asList(KWD_VJOTYPE, KWD_VJOCTYPE,
					KWD_VJOITYPE, KWD_VJOMTYPE, KWD_VJOOTYPE, KWD_NEEDS, KWD_NEEDSLIB);
		}

		public boolean isAllowedTrigger(char trigger) {
			return trigger == '.';
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 3;
		}

	}

	private static class NeedsKeyword extends AbstractVjoCompletionData
			implements IComposable {

		NeedsKeyword() {
			super("needs", "(\'\')", false, true, false);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_NEEDS);
		}

	}

	private static abstract class AutoComplementedKeyword extends
			AbstractVjoCompletionData implements IAutoComplemented {

		private String autoCompletedName;

		public AutoComplementedKeyword(String name, String trailingPart,
				boolean isEnclosable, boolean isComposable, boolean isTopLevel) {
			super(name, trailingPart, isEnclosable, isComposable, isTopLevel);
		}

		public void autoComplete(Object autoComplement) {

			if (autoComplement instanceof CharSequence) {

				autoCompletedName = new StringBuffer(super.toString()).insert(
						getCursorOffsetBeforeAutoCompletion(),
						(String) autoComplement).toString();

			}
		}

		public String toString() {
			return autoCompletedName == null ? super.toString()
					: autoCompletedName;
		}

		public int getCursorOffsetAfterCompletion() {
			return autoCompletedName == null ? getCursorOffsetBeforeAutoCompletion()
					: autoCompletedName.length();
		}

		protected int getCursorOffsetBeforeAutoCompletion() {
			return super.getCursorOffsetAfterCompletion();
		}

	}

	private static class VjoTypeKeyword extends AutoComplementedKeyword
			implements IComposable, ITopLevel {

		public List<IVjoCompletionData> getPeerKeywords() {
			return Arrays.asList(KWD_NEEDSLIB);
		}

		VjoTypeKeyword() {
			super("type", "(\'\')", false, true, true);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_NEEDS, KWD_PROTOS, KWD_PROPS, 
					KWD_INHERITS, KWD_SATISFIES, KWD_INITS,
					KWD_MIXIN, KWD_MIXINPROPS);
		}

		public boolean isAllowedTrigger(char trigger) {
			return trigger == '.';
		}

		protected final int getCursorOffsetBeforeAutoCompletion() {
			return super.getCursorOffsetBeforeAutoCompletion() - 2;
		}

	}

	private static class VjoCTypeKeyword extends AutoComplementedKeyword
			implements IComposable, ITopLevel {

		public List<IVjoCompletionData> getPeerKeywords() {
			return Arrays.asList(KWD_NEEDSLIB);
		}

		VjoCTypeKeyword() {
			super("ctype", "(\'\')", false, true, true);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_NEEDS, KWD_PROTOS, KWD_PROPS, 
					 KWD_INHERITS, KWD_SATISFIES, KWD_INITS,
					KWD_MIXIN, KWD_MIXINPROPS);
		}

		public boolean isAllowedTrigger(char trigger) {
			return trigger == '.';
		}

		protected final int getCursorOffsetBeforeAutoCompletion() {
			return super.getCursorOffsetBeforeAutoCompletion() - 2;
		}
	}

	private static class VjoATypeKeyword extends AutoComplementedKeyword
			implements IComposable, ITopLevel {

		public List<IVjoCompletionData> getPeerKeywords() {
			return Arrays.asList(KWD_NEEDS, KWD_NEEDSLIB);
		}

		VjoATypeKeyword() {
			super("atype", "(\'\')", false, true, true);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS, KWD_PROPS, 
					KWD_INHERITS, KWD_SATISFIES, KWD_INITS, KWD_MIXIN,
					KWD_MIXINPROPS);
		}

		public boolean isAllowedTrigger(char trigger) {
			return trigger == '.';
		}

		protected final int getCursorOffsetBeforeAutoCompletion() {
			return super.getCursorOffsetBeforeAutoCompletion() - 2;
		}
	}

	private static class VjoITypeKeyword extends AutoComplementedKeyword
			implements IComposable, ITopLevel {

		public List<IVjoCompletionData> getPeerKeywords() {
			return Arrays.asList(KWD_NEEDSLIB);
		}

		VjoITypeKeyword() {
			super("itype", "(\'\')", false, true, true);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_NEEDS, KWD_PROTOS, KWD_PROPS, KWD_INHERITS);
		}

		public boolean isAllowedTrigger(char trigger) {
			return trigger == '.';
		}

		protected final int getCursorOffsetBeforeAutoCompletion() {
			return super.getCursorOffsetBeforeAutoCompletion() - 2;
		}
	}

	private static class VjoETypeKeyword extends AutoComplementedKeyword
			implements IComposable, ITopLevel {

		public List<IVjoCompletionData> getPeerKeywords() {
			return Arrays.asList();
		}

		VjoETypeKeyword() {
			super("etype", "(\'\')", false, true, true);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS, KWD_VALUES);
		}

		public boolean isAllowedTrigger(char trigger) {
			return trigger == '.';
		}

		protected final int getCursorOffsetBeforeAutoCompletion() {
			return super.getCursorOffsetBeforeAutoCompletion() - 2;
		}
	}

	private static class VjoMTypeKeyword extends AutoComplementedKeyword
			implements IComposable, ITopLevel {

		public List<IVjoCompletionData> getPeerKeywords() {
			return Arrays.asList(KWD_NEEDSLIB);
		}

		VjoMTypeKeyword() {
			super("mtype", "(\'\')", false, true, true);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_NEEDS, KWD_PROTOS, KWD_PROPS, KWD_EXPECTS);
		}

		public boolean isAllowedTrigger(char trigger) {
			return trigger == '.';
		}

		protected final int getCursorOffsetBeforeAutoCompletion() {
			return super.getCursorOffsetBeforeAutoCompletion() - 2;
		}
	}
	
	private static class VjoOTypeKeyword extends AutoComplementedKeyword
			implements IComposable, ITopLevel {

		public List<IVjoCompletionData> getPeerKeywords() {
			return Arrays.asList(KWD_NEEDSLIB);
		}

		VjoOTypeKeyword() {
			super("otype", "(\'\')", false, true, true);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_NEEDS, KWD_PROTOS, KWD_PROPS, KWD_EXPECTS);
		}

		public boolean isAllowedTrigger(char trigger) {
			return trigger == '.';
		}

		protected final int getCursorOffsetBeforeAutoCompletion() {
			return super.getCursorOffsetBeforeAutoCompletion() - 2;
		}
	}

	private static class SatisfiesKeyword extends AbstractVjoCompletionData
			implements IComposable {

		SatisfiesKeyword() {
			super("satisfies", "(\'\')", false, true, false);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS, KWD_PROPS, 
					KWD_INHERITS, KWD_INITS);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}

	}

	private static class InherithsKeyword extends AbstractVjoCompletionData
			implements IComposable {

		InherithsKeyword() {
			super("inherits", "(\'\')", false, true, false);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS, KWD_PROPS, 
					 KWD_SATISFIES, KWD_INITS);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}

	}

	private static class ExpectsKeyword extends AbstractVjoCompletionData
			implements IComposable {

		ExpectsKeyword() {
			super("expects", "(\'\')", false, true, false);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS, KWD_PROPS);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}
	}

	private static class MixinKeyword extends AbstractVjoCompletionData
			implements IComposable {

		MixinKeyword() {
			super("mixin", "(\'\')", false, true, false);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS, KWD_PROPS, 
					 KWD_INITS);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}

	}

	private static class MixinPropsKeyword extends AbstractVjoCompletionData
			implements IComposable {

		MixinPropsKeyword() {
			super("mixinProps", "(\'\')", false, true, false);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS, KWD_PROPS, 
					 KWD_INITS);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}

	}

//	private static class MakeFinalKeyword extends AbstractVjoCompletionData
//			implements IComposable {
//
//		MakeFinalKeyword() {
//			super("makeFinal", "()", false, true, false);
//		}
//
//		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
//			return Arrays.asList(KWD_PROTOS, KWD_PROPS, KWD_SINGLETON,
//					KWD_MIXIN, KWD_MIXINPROPS, KWD_INITS);
//		}
//
//	}

	private static class SingletonKeyword extends AbstractVjoCompletionData
			implements IComposable {

		SingletonKeyword() {
			super("singleton", "()", false, true, false);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS, KWD_PROPS, 
					KWD_MIXIN, KWD_MIXINPROPS, KWD_INITS);
		}

	}

	private static class PropsKeyword extends AbstractVjoCompletionData
			implements IEnclosable, IComposable {

		PropsKeyword() {
			super("props", "({})", true, true, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_FUNC);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROTOS,  
					KWD_MIXIN, KWD_MIXINPROPS, KWD_INITS, KWD_END_TYPE);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}

	}

	private static class ProtosKeyword extends AbstractVjoCompletionData
			implements IEnclosable, IComposable {

		ProtosKeyword() {
			super("protos", "({})", true, true, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_FUNC);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_PROPS,  
					KWD_MIXIN, KWD_MIXINPROPS, KWD_INITS, KWD_END_TYPE);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}
	}

	private static class ValuesKeyword extends AbstractVjoCompletionData
			implements IEnclosable, IComposable {

		ValuesKeyword() {
			super("values", "()", true, true, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_VALUES);
		}

		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_END_TYPE);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 1;
		}
	}

	private static class InitsKeyword extends AbstractVjoCompletionData
			implements IEnclosable, IComposable {

		public InitsKeyword() {
			super("inits", "(function(){})", true, true, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_FUNC);
		}

		// this keyword must at the end of editor page so it cann't be composite
		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Arrays.asList(KWD_END_TYPE);
		}

		public int getCursorOffsetAfterCompletion() {
			int offset = super.getCursorOffsetAfterCompletion();
			return offset - 2;
		}

	}

	private static class EndTypeKeyword extends AbstractVjoCompletionData
			implements IEnclosable, IComposable {

		public EndTypeKeyword() {
			super("endType", "()", true, true, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList();
		}

		// this keyword must at the end of editor page so it cann't be composite
		public List<IVjoCompletionData> getAllowedCompositeKeywords() {
			return Collections.EMPTY_LIST;
		}

	}

	private static class FuncKeyword extends AbstractVjoCompletionData
			implements IEnclosable {

		FuncKeyword() {
			super("function", "", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

	}

	private static class ThisKeyword extends AbstractVjoCompletionData {
		ThisKeyword() {
			super("this", "");
		}
	}
	
	private static class BaseKeyword extends AbstractVjoCompletionData {
		BaseKeyword() {
			super("base", "");
		}
		
		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}
	}

	private static class Vj$Keyword extends AbstractVjoCompletionData {
		Vj$Keyword() {
			super("this.vj$", "");
		}
		
		public boolean canComplete(String text) {
			//Jack: for this keyword, JstCompletion will regard all the "this.xx" as a whole token, so need not trim the begin part
//			int idx = text.lastIndexOf('.');
//			if (idx != -1) {
//				text = text.substring(idx + 1).trim();
//			}
			return text.equals("")
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}
	}
	
	private static class ReturnKeyword extends AbstractVjoCompletionData {
		ReturnKeyword() {
			super("return", " ");
		}
	}

	private static class VarKeyword extends AbstractVjoCompletionData {
		VarKeyword() {
			super("var", " ");
		}
	}

	private static class NewKeyword extends AbstractVjoCompletionData {
		NewKeyword() {
			super("new", " ");
		}
	}

	private static class TryKeyword extends AbstractVjoCompletionData implements
			IEnclosable, IUnclosed {

		public TryKeyword() {
			super("try", "", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

		public boolean isUnclosed() {
			return true;
		}

		public List<IVjoCompletionData> getComplementedKeywords() {
			return Arrays.asList(KWD_CATCH, KWD_FINALLY);
		}

		public boolean isSelfSufficient() {
			return false;
		}

	}

	private static class CatchKeyword extends AbstractVjoCompletionData
			implements IEnclosable, IUnclosed {

		public CatchKeyword() {
			super("catch", "", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

		public List<IVjoCompletionData> getComplementedKeywords() {
			return Arrays.asList(KWD_FINALLY);
		}

		public boolean isUnclosed() {
			return true;
		}

		public boolean isSelfSufficient() {
			return true;
		}

	}

	private static class FinallyKeyword extends AbstractVjoCompletionData
			implements IEnclosable {

		public FinallyKeyword() {
			super("finally", "", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_VAR, KWD_NEW, KWD_IF, KWD_FOR,
					KWD_WHILE, KWD_DO, KWD_TRY);
		}

	}

	private static class DoKeyword extends AbstractVjoCompletionData implements
			IEnclosable, IUnclosed {

		public DoKeyword() {
			super("do", "", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

		public boolean isUnclosed() {
			return true;
		}

		public List<IVjoCompletionData> getComplementedKeywords() {
			return Arrays.asList(KWD_WHILE_AS_COMPLEMENTED);
		}

		public boolean isSelfSufficient() {
			return false;
		}

	}

	private static class WhileKeyword extends AbstractVjoCompletionData
			implements IEnclosable {

		public WhileKeyword() {
			super("while", "", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

	}

	private static class ForKeyword extends AbstractVjoCompletionData implements
			IEnclosable {

		ForKeyword() {
			super("for", "", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

	}

	private static class IfKeyword extends AbstractVjoCompletionData implements
			IEnclosable, IUnclosed {

		IfKeyword() {
			super("if", "", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

		public boolean isUnclosed() {
			return true;
		}

		public List<IVjoCompletionData> getComplementedKeywords() {
			return Arrays.asList(KWD_ELSE);
		}

		public boolean isSelfSufficient() {
			return true;
		}

	}

	private static class ElseKeyword extends AbstractVjoCompletionData
			implements IEnclosable {

		public ElseKeyword() {
			super("else", " ", true, false, false);
		}

		public List<IVjoCompletionData> getAllowedEnclosedKeywords() {
			return Arrays.asList(KWD_THIS, KWD_RETURN, KWD_VAR, KWD_NEW,
					KWD_IF, KWD_FOR, KWD_WHILE, KWD_DO, KWD_TRY);
		}

	}

	private static class ContinueKeyword extends AbstractVjoCompletionData {

		public ContinueKeyword() {
			super("continue", " ", true, false, false);

		}

	}

	private static class BreakKeyword extends AbstractVjoCompletionData {

		public BreakKeyword() {
			super("break", " ", true, false, false);

		}

	}

	private static class SwitchKeyword extends AbstractVjoCompletionData {

		public SwitchKeyword() {
			super("switch", " ", true, false, false);

		}

	}

	private static class CaseKeyword extends AbstractVjoCompletionData {

		public CaseKeyword() {
			super("case", " ", true, false, false);

		}

	}

	private static class DefaultKeyword extends AbstractVjoCompletionData {

		public DefaultKeyword() {
			super("default", " ", true, false, false);

		}

	}

	private static class TypeOfKeyword extends AbstractVjoCompletionData {

		public TypeOfKeyword() {
			super("typeof()", " ", true, false, false);

		}

	}

	private static class InstanceOfKeyword extends AbstractVjoCompletionData {

		public InstanceOfKeyword() {
			super("instanceof", " ", true, false, false);

		}
	}

	private static class VjoUtilityKeyword extends AbstractVjoCompletionData {
		public VjoUtilityKeyword() {
			super(VjoKeywords.VJO, "", false, true, true);

		}
	}

	private static class SysoutKeyword extends AbstractVjoCompletionData {

		public SysoutKeyword() {
			super("sysout", "", true, false, false);
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}
	
	private static class SyserrKeyword extends AbstractVjoCompletionData {

		public SyserrKeyword() {
			super("syserr", "", true, false, false);
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}

	private static class PrintlnKeyword extends AbstractVjoCompletionData {

		public PrintlnKeyword() {
			super("println", "(\"\");", true, false, false);
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}
	
	private static class PrintKeyword extends AbstractVjoCompletionData {

		public PrintKeyword() {
			super("print", "(\"\");", true, false, false);
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}
	
	private static class PrintStackTrace extends AbstractVjoCompletionData {

		public PrintStackTrace() {
			super("printStackTrace", "(\"\");", true, false, false);
		}

		public boolean canComplete(String text) {
			int idx = text.lastIndexOf('.');
			if (idx != -1) {
				text = text.substring(idx + 1).trim();
			}
			return text == ""
					|| getName().toLowerCase().startsWith(text.toLowerCase());
		}

	}

	/**
	 * Gets the collection of installed keywords.
	 * 
	 * @return the collection of installed keywords.
	 */
	public static Collection<IVjoCompletionData> getInstalledKeywords() {

		return AbstractVjoCompletionData.installedKeywords.values();

	}

	/**
	 * Gets the collection of top-level keywords.
	 * 
	 * @return the collection of top-level keywords.
	 */
	public static List<IVjoCompletionData> getTopLevelKeywords() {

		List<IVjoCompletionData> topLevelKeywords = new ArrayList<IVjoCompletionData>();

		Collection<IVjoCompletionData> installedKeywords = getInstalledKeywords();
		Iterator<IVjoCompletionData> iterator = installedKeywords.iterator();

		while (iterator.hasNext()) {

			IVjoCompletionData keyword = iterator.next();

			if (keyword.isTopLevelKeyword()) {
				topLevelKeywords.add(keyword);
			}

		}

		return topLevelKeywords;
	}

	/**
	 * Gets keyword by its name.
	 * 
	 * @param keyword
	 *            the keyword name
	 * @return Vjo keyword
	 */
	public static IVjoCompletionData getKeywordByName(String keyword) {
		return AbstractVjoCompletionData.installedKeywords.get(keyword);
	}

}