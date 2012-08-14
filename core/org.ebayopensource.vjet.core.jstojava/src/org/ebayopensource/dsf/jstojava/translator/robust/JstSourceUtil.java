/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jstojava.translator.robust;

import java.util.ArrayList;
import java.util.List;

import org.ebayopensource.dsf.jstojava.translator.IFindTypeSupport;
import org.ebayopensource.dsf.jstojava.translator.robust.AvlTree.AvlNode;

public class JstSourceUtil implements IFindTypeSupport.ILineInfoProvider {

	// private static final char CHAR = '\n';

	private static final LineInfo NEGONE = new LineInfo(-1, -1, -1);

	public static class LineInfo implements Comparable<LineInfo> {

		private final int m_line;
		private final int m_colStart;
		private final int m_colEnd;

		// private final int m_startOffset;

		public LineInfo(int lineCount, int colStart, int colEnd) {
			m_line = lineCount;
			m_colStart = colStart;
			m_colEnd = colEnd;
			// System.out.println("line:" + lineCount + " start:" + colStart+
			// "end: " + colEnd );
		}

		public int line() {
			return m_line;
		}

		public int colStart() {
			return m_colStart;
		}

		public int colEnd() {
			return m_colEnd;
		}

		@Override
		public int compareTo(LineInfo o) {
			int thisVal = this.colStart();
			int anotherVal = o.colStart();
			return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));

		}
		
		@Override
		public String toString() {
			return new StringBuilder().append(this.m_colStart).toString();
		}

	}

	private char[] m_source;
	private List<LineInfo> m_lineInfo = new ArrayList<LineInfo>();
	private boolean m_init = false;
	private AvlTree m_tree;

	public JstSourceUtil(char[] source) {
		m_source = source;
	}

	public LineInfo lineInfo(int startOffset) {
		init();
		if(m_tree.getRoot()==null){
			return new LineInfo(0, 0, 0);
		}
		return binarySearch(m_lineInfo, startOffset);
	}

	public int line(int startOffset) {
		init();
		LineInfo line = binarySearchNew(m_tree, startOffset);
		return line.m_line;
		// return binarySearch(m_lineInfo, startOffset).m_line;
	}

	public static LineInfo binarySearch(List<LineInfo> a, int startOffset) {
		int low = 0;
		int high = a.size() - 1;
		int mid;
		if (startOffset == 0) {
			return a.get(0);
		}

		while (low <= high) {
			mid = (low + high) / 2;

			LineInfo lineInfo = a.get(mid);
			if (startOffset <= lineInfo.m_colEnd
					&& startOffset >= lineInfo.m_colStart) {
				return lineInfo;
			} else if (inLowerRange(lineInfo, startOffset)) {
				high = mid - 1;
			} else if (inHigherRange(lineInfo, startOffset)) {
				low = mid + 1;
			} else {
				return lineInfo;
			}
		}

		return NEGONE; // NOT_FOUND = -1
	}

	public static LineInfo binarySearchNew(AvlTree tree, int startOffset) {
		//tree.printTree();
		return ((LineInfo) findLine(tree, new LineInfo(0, startOffset, 0)).element);
	}

	private static AvlNode findLine(AvlTree tree, Comparable x) {
		AvlNode t = tree.getRoot();
		AvlNode preNode = null;
		while (t != null) {
			if(preNode==null){
				AvlNode rootCheck = checkOffset(x, t, null);
				if(rootCheck!=null){return rootCheck;}
			}else{
				AvlNode rootCheck  = checkOffset(x, t, preNode);
				if(rootCheck!=null){return rootCheck;}
			}
			
			if (x.compareTo(t.element) < 0 && t.left != null) {
				preNode = t;
				t = t.left;
			} else if (x.compareTo(t.element) > 0 && t.right != null ) {
				preNode = t;
				t = t.right;
			} else {
				t = checkOffset(x, t, preNode);
				if(t!=null){return t;}
			}
		}

		return null; // No match

	}

	private static AvlNode checkOffset(Comparable x, AvlNode t, AvlNode preNode) {
		LineInfo tlio = (LineInfo) t.element;
		LineInfo xlio = (LineInfo) x;
		LineInfo prlio = null;
		if(preNode!=null){
			prlio = (LineInfo) preNode.element;
		}
		
		if (xlio.m_colStart >= tlio.m_colStart
				&& xlio.m_colStart <= tlio.m_colEnd) {
			return t;
		} else if (prlio!=null && xlio.m_colStart >= prlio.m_colStart
				&& xlio.m_colStart <= prlio.m_colEnd) {
			return preNode;
		}else{
			t=null;
		}
		return t;
	}

	private static boolean inHigherRange(LineInfo lineInfo, int startOffset) {
		return startOffset > lineInfo.m_colEnd;
	}

	private static boolean inLowerRange(LineInfo lineInfo, int startOffset) {
		return startOffset < lineInfo.m_colStart;
	}

	private void init() {
		if (m_init == true || m_source == null) {
			return;
		}
		String source = new String(m_source);

		int lineCount = 1;
		int colStart = 0;
		int colEnd = 0;

		String[] lines = source.split("\n");
		for (String line : lines) {

			colEnd = colEnd + line.length() + 1;

			m_lineInfo.add(new LineInfo(lineCount, colStart, colEnd));

			colStart = colEnd + 1;

			lineCount++;

		}

		setupBinaryTree();

		m_init = true;

	}

	private void setupBinaryTree() {
		// *

		AvlTree tree = new AvlTree();

		for (int i = 0; i < m_lineInfo.size(); i++) {
			LineInfo linfo = m_lineInfo.get(i);
			tree.insert(linfo);
		}

		m_tree = tree;
		// printFrontToBack(tree.root, m_lineInfo.get(mid).m_colStart);
	}

	public int col(int startOffset) {
		init();
		// LineInfo lineInfo = binarySearch(m_lineInfo, startOffset);
		LineInfo lineInfo = binarySearchNew(m_tree, startOffset);
		return startOffset - lineInfo.m_colStart;
	}

	public static void main(String[] args) {
		String x = "abcd\nxyz\nddddddd\ndddddd\ndd\ndddd\ndddddd\nddddddd\ndddddd\n";

		JstSourceUtil y = new JstSourceUtil(x.toCharArray());
		for (int i = 0; i < x.length(); i++) {
			System.out.println("i=" + i + ": " + y.line(i) + "," + y.col(i));
		}
		/*
		 * System.out.println(y.line(3) +","+ y.col(3));
		 * System.out.println(y.line(4)+","+ y.col(4));
		 * System.out.println(y.line(5)+","+ y.col(5));
		 * System.out.println(y.line(20)+","+ y.col(20));
		 * System.out.println(y.line(50)+","+ y.col(50));
		 */

	}

}
