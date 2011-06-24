/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.ui.text.folding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.IImportContainer;
import org.ebayopensource.vjet.eclipse.core.IJSType;
import org.ebayopensource.vjet.eclipse.core.VjoNature;
import org.ebayopensource.vjet.eclipse.ui.VjetPreferenceConstants;
import org.ebayopensource.vjet.eclipse.ui.VjetUIPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.dltk.mod.ast.ASTNode;
import org.eclipse.dltk.mod.core.IMember;
import org.eclipse.dltk.mod.core.IMethod;
import org.eclipse.dltk.mod.core.IModelElement;
import org.eclipse.dltk.mod.core.IParent;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.ISourceRange;
import org.eclipse.dltk.mod.core.ISourceReference;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.corext.SourceRange;
import org.eclipse.dltk.mod.internal.core.IJSInitializer;
import org.eclipse.dltk.mod.javascript.internal.ui.text.JavascriptPartitionScanner;
import org.eclipse.dltk.mod.javascript.scriptdoc.IScanner;
import org.eclipse.dltk.mod.javascript.scriptdoc.PublicScanner;
import org.eclipse.dltk.mod.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.mod.ui.text.folding.AbstractASTFoldingStructureProvider;
import org.eclipse.dltk.mod.ui.text.folding.IElementCommentResolver;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.text.folding.DefaultJavaFoldingStructureProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.source.projection.IProjectionPosition;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;
import org.eclipse.jface.text.source.projection.ProjectionViewer;

public class VjoFoldingStructureProvider extends
		AbstractASTFoldingStructureProvider {

	/**
	 * Projection position that will return two foldable regions: one folding
	 * away the lines before the one containing the simple name of the script
	 * element, one folding away any lines after the caption.
	 */
	private static final class ScriptElementPosition extends Position implements
			IProjectionPosition {
		public ScriptElementPosition(int offset, int length) {
			super(offset, length);
		}

		/*
		 * @see org.eclipse.jface.text.source.projection.IProjectionPosition#computeFoldingRegions(org.eclipse.jface.text.IDocument)
		 */
		public IRegion[] computeProjectionRegions(IDocument document)
				throws BadLocationException {
			int nameStart = offset;
			int firstLine = document.getLineOfOffset(offset);
			int captionLine = document.getLineOfOffset(nameStart);
			int lastLine = document.getLineOfOffset(offset + length);
			/*
			 * see comment above - adjust the caption line to be inside the
			 * entire folded region, and rely on later element deltas to correct
			 * the name range.
			 */
			if (captionLine < firstLine)
				captionLine = firstLine;
			if (captionLine > lastLine)
				captionLine = lastLine;
			IRegion preRegion;
			if (firstLine < captionLine) {
				int preOffset = document.getLineOffset(firstLine);
				IRegion preEndLineInfo = document
						.getLineInformation(captionLine);
				int preEnd = preEndLineInfo.getOffset();
				preRegion = new Region(preOffset, preEnd - preOffset);
			} else {
				preRegion = null;
			}
			if (captionLine < lastLine) {
				int postOffset = document.getLineOffset(captionLine + 1);
				IRegion postRegion = new Region(postOffset, offset + length
						- postOffset);
				if (preRegion == null)
					return new IRegion[] { postRegion };
				return new IRegion[] { preRegion, postRegion };
			}
			if (preRegion != null)
				return new IRegion[] { preRegion };
			return null;
		}

		/*
		 * @see org.eclipse.jface.text.source.projection.IProjectionPosition#computeCaptionOffset(org.eclipse.jface.text.IDocument)
		 */
		public int computeCaptionOffset(IDocument document)
				throws BadLocationException {
			return 0;
		}
	}

	/* preferences */
	private boolean m_CollapseImportContainer = true;
	private boolean m_CollapseJavadoc= false;
	private boolean m_CollapseInnerTypes = false;
	private boolean m_CollapseMembers = false;
	private boolean m_CollapseHeaderComments = true;

	@Override
	protected String[] getCommentPartition() {
		return new String[]{/*IJavaScriptPartitions.JS_SINGLE_COMMENT,*/
				IJavaScriptPartitions.JS_MULTI_COMMENT,IJavaScriptPartitions.JS_DOC};
	}

	@Override
	protected ILog getLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getNatureId() {
		return VjoNature.NATURE_ID;
	}

	@Override
	protected String getPartition() {
		return IJavaScriptPartitions.JS_PARTITIONING;
	}

	@Override
	protected IPartitionTokenScanner getPartitionScanner() {
		return new JavascriptPartitionScanner();
	}

	@Override
	protected String[] getPartitionTypes() {
		return IJavaScriptPartitions.JS_PARTITION_TYPES;
	}

	@Override
	protected boolean initiallyCollapse(ASTNode s,
			FoldingStructureComputationContext ctx) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	protected boolean mayCollapse(ASTNode s,
			FoldingStructureComputationContext ctx) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean computeFoldingStructure(String contents,
			FoldingStructureComputationContext ctx2) {
		FoldingStructureComputationContext2 ctx = (FoldingStructureComputationContext2) ctx2;
		// ctx.getScanner().setSource(contents.toCharArray());
		IModelElement[] elements;
		try {
			IModelElement element = getInputElement();
			if (CodeassistUtils.isVjoSourceModule(element) && CodeassistUtils.isModuleInBuildPath(element)) {
				elements = ((IParent) getInputElement()).getChildren();
				computeFoldingStructure(elements, ctx);
			}
		} catch (ModelException e) {
			e.printStackTrace();
			return false;
		}

		if (fCommentsFolding) {
			// 1. Compute regions for comments
			IRegion[] commentRegions = computeCommentsRanges(contents);
			// modify by patrick for bug 1288
			if (commentRegions.length == 0) {
				return true;
			}
			// comments
			Map<Position, ScriptProjectionAnnotation> typeAnnotationList = new HashMap<Position, ScriptProjectionAnnotation>();

			for (int i = 0; i < commentRegions.length; i++) {
				IRegion normalized = alignRegion(commentRegions[i], ctx);
				if (normalized != null) {
					Position position = createCommentPosition(normalized);
					if (position != null) {
						int hash = contents
								.substring(
										normalized.getOffset(),
										normalized.getOffset()
												+ normalized.getLength())
								.hashCode();
						IModelElement element = null;
						IElementCommentResolver res = getElementCommentResolver();
						if (res != null && fInput != null) {
							element = res.getElementByCommentPosition(
									(ISourceModule) fInput, position.offset,
									position.length);
						}
						boolean toAdd = initiallyCollapseComments(normalized,
								ctx);

						ScriptProjectionAnnotation annotation = new ScriptProjectionAnnotation(
								toAdd, true, new SourceRangeStamp(hash,
										normalized.getLength()), element);

						typeAnnotationList.put(position, annotation);

						if (element instanceof IType) {
							if (isInnerType((IType) element)) {
								continue;
							}

							if (!ctx.hasFirstType())
								ctx.setFirstType((IType) element);
						}
					}
				}
			}

			handleHeaderComment(ctx, contents, typeAnnotationList);

			// set to ctx
			Set<Position> positionSet = typeAnnotationList.keySet();
			for (Position pos : positionSet) {
				ctx.addProjectionRange(
						(ScriptProjectionAnnotation) typeAnnotationList
								.get(pos), pos);
			}
			// end modify

		}

		return true;
	}
	
	//add by patrick
	private void handleHeaderComment(FoldingStructureComputationContext2 ctx,
			String contents,
			Map<Position, ScriptProjectionAnnotation> typeAnnotationMap) {
		try {
			if (!ctx.hasHeaderComment() && fInput != null
					&& ((ISourceModule) fInput).hasChildren()) {
				// find the first element's position
				ISourceModule module = (ISourceModule) fInput;
				int firstElementOffset = getFirstElementOffset(module);

				Set<Position> positionSet = typeAnnotationMap.keySet();
				List<Position> positions = getSortedPositionList(positionSet);
				// check multiple header comments
				List<Position> headerCommentPositions = getHeaderCommentList(
						firstElementOffset, positions);

				if (headerCommentPositions == null
						|| headerCommentPositions.isEmpty()) {
					return;
				}

				ctx.setHasHeaderComment();

				if (headerCommentPositions.size() > 1) {
					// merge multiple comments to one annotation
					Position lastHeaderComment = headerCommentPositions
							.get(headerCommentPositions.size() - 1);

					IModelElement firstElement = typeAnnotationMap.get(
							lastHeaderComment).getElement();

					int newOffset = headerCommentPositions.get(0).offset;
					int newLength = lastHeaderComment.getOffset()
							+ lastHeaderComment.getLength() - newOffset;
					Position newHeaderCommentPosition = createCommentPosition(new Region(
							newOffset, newLength));

					int hash = contents.substring(newOffset,
							newOffset + newLength).hashCode();
					ScriptProjectionAnnotation newHeaderCommentAnnotation = new ScriptProjectionAnnotation(
							ctx.collapseHeaderComments(), true,
							new SourceRangeStamp(hash, newLength), firstElement);

					// remove old header comments firstly
					for (Position p : headerCommentPositions) {
						typeAnnotationMap.remove(p);
					}

					typeAnnotationMap.put(newHeaderCommentPosition,
							newHeaderCommentAnnotation);
				} else {
					// one header comment
					ScriptProjectionAnnotation headerCommentAnno = typeAnnotationMap
							.get(headerCommentPositions.get(0));
					if (ctx.collapseHeaderComments()) {
						headerCommentAnno.markCollapsed();
					} else {
						headerCommentAnno.markExpanded();
					}
				}

			}
		} catch (ModelException e) {
			VjetUIPlugin.log(e);
		}
	}

	private List<Position> getHeaderCommentList(int firstElementOffset,
			List<Position> positions) {
		List<Position> headerCommentPositions = null;
		final int len = positions.size();
		int i = -1;
		for (i = 0; i < len; i++) {
			if (firstElementOffset < positions.get(i).getOffset()) {
				headerCommentPositions = positions.subList(0, i);
				break;
			}
		}
		if(i == len){
			headerCommentPositions = positions;
		}
		return headerCommentPositions;
	}

	private List<Position> getSortedPositionList(Set<Position> positionSet) {
		List<Position> positions = new ArrayList<Position>(positionSet);
		if (positionSet.size() > 1) {// if more than 1, start compare
			Collections.sort(positions, new Comparator<Position>() {
				public int compare(Position o1, Position o2) {
					return o1.getOffset() - o2.getOffset();
				}

			});

		}
		return positions;
	}

	private int getFirstElementOffset(ISourceModule module)
			throws ModelException {
		int firstElementOffset = -1;
		if (module.hasChildren()) {
			List<ISourceRange> ranges = new ArrayList<ISourceRange>();
			IModelElement[] children = module.getChildren();
			for (IModelElement child : children) {
				if (child instanceof ISourceReference) {
					ranges.add(((ISourceReference) child).getSourceRange());
				}
			}
			if (ranges.size() > 0) {
				Collections.sort(ranges, new Comparator<ISourceRange>() {
					@Override
					public int compare(ISourceRange object1,
							ISourceRange object2) {
						return object1.getOffset() - object2.getOffset();
					}
				});
				firstElementOffset = ranges.get(0).getOffset();
			}
		}
		return firstElementOffset;
	}
	//end add

	@Override
	protected boolean initiallyCollapseComments(IRegion commentRegion, FoldingStructureComputationContext ctx2) {
		FoldingStructureComputationContext2 ctx=(FoldingStructureComputationContext2)ctx2;
		return ctx.collapseJavadoc()&&ctx.allowCollapsing();
	}

	private void computeFoldingStructure(IModelElement[] elements,
			FoldingStructureComputationContext ctx) throws ModelException {
		for (int i = 0; i < elements.length; i++) {
			IModelElement element = elements[i];

			computeFoldingStructure(element, ctx);

			if (element instanceof IParent) {
				IParent parent = (IParent) element;
				computeFoldingStructure(parent.getChildren(), ctx);
			}
		}
	}
	
	
	
	

	@Override
	public IElementCommentResolver getElementCommentResolver() {
		return new VjetElementCommentResolver();
	}

	@Override
	protected FoldingStructureComputationContext createContext(boolean allowCollapse) {
		if (!isInstalled())
			return null;
		ProjectionAnnotationModel model = getModel();
		if (model == null)
			return null;
		IDocument doc = getDocument();
		if (doc == null)
			return null;
		return new FoldingStructureComputationContext2(doc, model, allowCollapse);
	}
	
	/**
	 * Returns <code>true</code> if <code>type</code> is an anonymous enum declaration,
	 * <code>false</code> otherwise. See also https://bugs.eclipse.org/bugs/show_bug.cgi?id=143276
	 * 
	 * @param type the type to test
	 * @return <code>true</code> if <code>type</code> is an anonymous enum declaration
	 * @since 3.3
	 */
	private boolean isAnonymousEnum(IType type) {
		if(type instanceof IJSType){
			IJSType jsType=(IJSType)type;
			try {
				return jsType.isEnum() && jsType.getElementName().length()==0;
			} catch (ModelException x) {
				return false; // optimistically
			}
		}
		
		return false;
	}

	/**
	 * Returns <code>true</code> if <code>type</code> is not a top-level type, <code>false</code> if it is.
	 * 
	 * @param type the type to test
	 * @return <code>true</code> if <code>type</code> is an inner type
	 */
	private boolean isInnerType(IType type) {
		return type.getDeclaringType() != null&& !isAnonymousEnum(type);
	}
	

	/**
	 * Computes the folding structure for a given
	 * {@link IJavaElement java element}. Computed projection annotations are
	 * {@link DefaultJavaFoldingStructureProvider.FoldingStructureComputationContext#addProjectionRange(DefaultJavaFoldingStructureProvider.JavaProjectionAnnotation, Position) added}
	 * to the computation context.
	 * <p>
	 * Subclasses may extend or replace. The default implementation creates
	 * projection annotations for the following elements:
	 * <ul>
	 * <li>true members (not for top-level types)</li>
	 * <li>the javadoc comments of any member</li>
	 * <li>header comments (javadoc or multi-line comments appearing before the
	 * first type's javadoc or before the package or import declarations).</li>
	 * </ul>
	 * </p>
	 * 
	 * @param element
	 *            the java element to compute the folding structure for
	 * @param ctx
	 *            the computation context
	 */
	protected void computeFoldingStructure(IModelElement element,
			FoldingStructureComputationContext ctx2) {
		FoldingStructureComputationContext2 ctx=(FoldingStructureComputationContext2)ctx2;
		boolean collapse = false;
		boolean collapseCode = true;
		switch (element.getElementType()) {

		case IImportContainer.ELEMENT_TYPE:
			collapse = ctx.allowCollapsing() && m_CollapseImportContainer;
			break;
		case IModelElement.TYPE:
			//collapseCode = false;
			collapseCode = isInnerType((IType) element) ;
			collapse = ctx.collapseInnerTypes() && collapseCode;
			break;
		case IModelElement.METHOD:
		case IModelElement.FIELD:
			// case IModelElement.INITIALIZER:
			collapse = ctx.allowCollapsing() && m_CollapseMembers;
			break;
		case IJSInitializer.ELEMENT_TYPE:
			// case IModelElement.INITIALIZER:
			collapse = ctx.allowCollapsing() && m_CollapseMembers;
			break;
		default:
			return;
		}

		IRegion[] regions = computeProjectionRanges((ISourceReference) element,
				ctx);
		if (regions.length > 0) {
			// // comments
//			for (int i = 0; i < regions.length - 1; i++) {
//				IRegion normalized = alignRegion(regions[i], ctx);
//				if (normalized != null) {
//					Position position = createCommentPosition(normalized);
//					if (position != null) {
//						boolean commentCollapse;
//						if (i == 0 && (regions.length > 2 || ctx.hasHeaderComment()) && element == ctx.getFirstType()) {
//							commentCollapse = ctx.collapseHeaderComments();
//						} else {
//							commentCollapse = ctx.collapseJavadoc();
//						}
//						//ctx.addProjectionRange(new ScriptProjectionAnnotation(commentCollapse, element, true), position);
//						ctx.addProjectionRange(new ScriptProjectionAnnotation(commentCollapse,true,new SourceRangeStamp(0, position.length),element), position);
//					}
//				}
//			}
			// code
			if (collapseCode) {
				IRegion normalized = alignRegion(regions[regions.length - 1], ctx);
				if (normalized != null) {
					Position position = element instanceof IMember ? createMemberPosition(normalized, (IMember) element) : createCommentPosition(normalized);
					if (position != null)
						ctx.addProjectionRange(new ScriptProjectionAnnotation(collapse, false, new SourceRangeStamp(0, position.length), element), position);
				}
			}
		}
	}

	/**
	 * Computes the projection ranges for a given <code>ISourceReference</code>.
	 * More than one range or none at all may be returned. If there are no
	 * foldable regions, an empty array is returned.
	 * <p>
	 * The last region in the returned array (if not empty) describes the region
	 * for the java element that implements the source reference. Any preceding
	 * regions describe javadoc comments of that java element.
	 * </p>
	 * 
	 * @param reference
	 *            a java element that is a source reference
	 * @param ctx
	 *            the folding context
	 * @return the regions to be folded
	 */
	protected final IRegion[] computeProjectionRanges(
			ISourceReference reference, FoldingStructureComputationContext ctx2) {
		try {
			FoldingStructureComputationContext2 ctx=(FoldingStructureComputationContext2)ctx2;
			ISourceRange range = reference.getSourceRange();
			if (!SourceRange.isAvailable(range))
				return new IRegion[0];

			String contents = reference.getSource();
			if (contents == null)
				return new IRegion[0];
			
			if(reference instanceof IType&& isInnerType((IType)reference) ){
				IType innerType=(IType)reference;
				ISourceRange sourceRange=innerType.getSourceRange();
				//TODO handle sourceRange. Inner Type suport failed as Inner Type offset is minus 1
				
			}
			
			// Fixed bug: collapsing comment should not collapse
			// the following function too
			if (reference instanceof IMethod) {

				ISourceRange actuallyMemberSourceRange = ((IMember) reference).getNameRange();

				// the function has preceding comment
				if (actuallyMemberSourceRange.getOffset() > range.getOffset()) {

					int end = range.getOffset() + range.getLength();
					range = new SourceRange(actuallyMemberSourceRange.getOffset(), end - actuallyMemberSourceRange.getOffset());

				}
			}

			List<IRegion> regions = new ArrayList<IRegion>();
//			if (!ctx.hasFirstType() && reference instanceof IType) {
//				ctx.setFirstType((IType) reference);
//				IRegion headerComment = computeHeaderComment(ctx);
//				if (headerComment != null) {
//					regions.add(headerComment);
//					ctx.setHasHeaderComment();
//				}
//			}
//
//			final int shift = range.getOffset();
//			IScanner scanner = ctx.getScanner();
//			scanner.resetTo(shift, shift + range.getLength());
//
//			int start = shift;
//			while (true) {
//
//				int token = scanner.getNextToken();
//				start = scanner.getCurrentTokenStartPosition();
//
//				switch (token) {
//				case ITerminalSymbols.TokenNameCOMMENT_JAVADOC:
//				case ITerminalSymbols.TokenNameCOMMENT_BLOCK: {
//					int end = scanner.getCurrentTokenEndPosition() + 1;
//					regions.add(new Region(start, end - start));
//					continue;
//				}
//				case ITerminalSymbols.TokenNameCOMMENT_LINE:
//					continue;
//				}
//
//				break;
//			}
//
//			regions.add(new Region(start, shift + range.getLength() - start));
			
			

			regions.add(new Region(range.getOffset(), range.getLength()));		
			IRegion[] result = new IRegion[regions.size()];
			regions.toArray(result);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new IRegion[0];
	}
	
	
//	private IRegion computeHeaderComment(FoldingStructureComputationContext ctx2) throws ModelException {
//		// search at most up to the first type
//		FoldingStructureComputationContext2 ctx=(FoldingStructureComputationContext2)ctx2;
//		ISourceRange range= ((VjoSourceType)ctx.getFirstType()).getSourceRange();
//		if (range == null)
//			return null;
//		int start= 0;
//		int end= range.getOffset();
//
//
//		/* code adapted from CommentFormattingStrategy:
//		 * scan the header content up to the first type. Once a comment is
//		 * found, accumulate any additional comments up to the stop condition.
//		 * The stop condition is reaching a package declaration, import container,
//		 * or the end of the input.
//		 */
//		IScanner scanner= ctx.getScanner();
//		scanner.resetTo(start, end);
//
//		int headerStart= -1;
//		int headerEnd= -1;
//		try {
//			boolean foundComment= false;
//			int terminal= scanner.getNextToken();
//			while (terminal != ITerminalSymbols.TokenNameEOF && !(terminal == ITerminalSymbols.TokenNameclass || terminal == ITerminalSymbols.TokenNameinterface || terminal == ITerminalSymbols.TokenNameenum || (foundComment && (terminal == ITerminalSymbols.TokenNameimport || terminal == ITerminalSymbols.TokenNamepackage)))) {
//
//				if (terminal == ITerminalSymbols.TokenNameCOMMENT_JAVADOC || terminal == ITerminalSymbols.TokenNameCOMMENT_BLOCK || terminal == ITerminalSymbols.TokenNameCOMMENT_LINE) {
//					if (!foundComment)
//						headerStart= scanner.getCurrentTokenStartPosition();
//					headerEnd= scanner.getCurrentTokenEndPosition();
//					foundComment= true;
//				}
//				terminal= scanner.getNextToken();
//			}
//
//
//		} catch (InvalidInputException ex) {
//			return null;
//		}
//
//		if (headerEnd != -1) {
//			return new Region(headerStart, headerEnd - headerStart);
//		}
//		return null;
//	}

	/**
	 * Creates a folding position that remembers its member from an
	 * {@link #alignRegion(IRegion, DefaultJavaFoldingStructureProvider.FoldingStructureComputationContext) aligned}
	 * region.
	 * 
	 * @param aligned
	 *            an aligned region
	 * @param member
	 *            the member to remember
	 * @return a folding position corresponding to <code>aligned</code>
	 */
	protected final Position createMemberPosition(IRegion aligned,
			IMember member) {
		return new ScriptElementPosition(aligned.getOffset(), aligned
				.getLength());
	}

	@Override
	protected void initializePreferences(IPreferenceStore store) {
		m_CollapseInnerTypes = store
				.getBoolean(VjetPreferenceConstants.EDITOR_FOLDING_INNERTYPES);
		m_CollapseImportContainer = store
				.getBoolean(VjetPreferenceConstants.EDITOR_FOLDING_IMPORTS);
		m_CollapseJavadoc=
		    store.getBoolean(VjetPreferenceConstants.EDITOR_FOLDING_JAVADOC);
		m_CollapseMembers = store
				.getBoolean(VjetPreferenceConstants.EDITOR_FOLDING_METHODS);
		m_CollapseHeaderComments = store
				.getBoolean(VjetPreferenceConstants.EDITOR_FOLDING_HEADERS);
		super.initializePreferences(store);
	}

	@Override
	protected CodeBlock[] getCodeBlocks(String code, int offset) {
//		ISourceParser parser = getSourceParser();
//		IResource resource = getInputElement().getResource();
//
//		ModuleDeclaration decl = parser.parse(resource.getFullPath().toString()
//				.toCharArray(), code.toCharArray(), null);
//		return buildCodeBlocks(decl, offset);
		
		return null;// do not need parse
	}
	
	
	

	
	protected final class FoldingStructureComputationContext2 extends FoldingStructureComputationContext {
	

		public FoldingStructureComputationContext2(IDocument document, ProjectionAnnotationModel model, boolean allowCollapsing) {
			super(document, model, allowCollapsing);
		}

		private IType fFirstType;
		private boolean fHasHeaderComment;
		
		private IScanner fScanner;

		
		
		private void setFirstType(IType type) {
			if (hasFirstType())
				throw new IllegalStateException();
			fFirstType= type;
		}
		
		boolean hasFirstType() {
			return fFirstType != null;
		}
		
		private IType getFirstType() {
			return fFirstType;
		}

		private boolean hasHeaderComment() {
			return fHasHeaderComment;
		}

		private void setHasHeaderComment() {
			fHasHeaderComment= true;
		}
		
		
		private IScanner getScanner() {
			if (fScanner == null)
				fScanner= new PublicScanner(true /*comment*/, false /*whitespace*/, false /*nls*/, 4 /*sourceLevel*/, null/*taskTag*/, null/*taskPriorities*/, true /*taskCaseSensitive*/);
			return fScanner;
//			return null;
		}
		
		/**
		 * Adds a projection (folding) region to this context. The created annotation / position
		 * pair will be added to the {@link ProjectionAnnotationModel} of the
		 * {@link ProjectionViewer} of the editor.
		 * 
		 * @param annotation the annotation to add
		 * @param position the corresponding position
		 */
		public void addProjectionRange(ScriptProjectionAnnotation annotation, Position position) {
			fMap.put(annotation, position);
		}

		/**
		 * Returns <code>true</code> if header comments should be collapsed.
		 * 
		 * @return <code>true</code> if header comments should be collapsed
		 */
		public boolean collapseHeaderComments() {
			return allowCollapsing() && m_CollapseHeaderComments;
		}

		/**
		 * Returns <code>true</code> if import containers should be collapsed.
		 * 
		 * @return <code>true</code> if import containers should be collapsed
		 */
		public boolean collapseImportContainer() {
			return allowCollapsing() && m_CollapseImportContainer;
		}

		/**
		 * Returns <code>true</code> if inner types should be collapsed.
		 * 
		 * @return <code>true</code> if inner types should be collapsed
		 */
		public boolean collapseInnerTypes() {
			return allowCollapsing() && m_CollapseInnerTypes;
		}

		/**
		 * Returns <code>true</code> if javadoc comments should be collapsed.
		 * 
		 * @return <code>true</code> if javadoc comments should be collapsed
		 */
		public boolean collapseJavadoc() {
			return allowCollapsing() && m_CollapseJavadoc;
		}

		/**
		 * Returns <code>true</code> if methods should be collapsed.
		 * 
		 * @return <code>true</code> if methods should be collapsed
		 */
		public boolean collapseMembers() {
			return allowCollapsing() && m_CollapseMembers;
		}
	}
	
}