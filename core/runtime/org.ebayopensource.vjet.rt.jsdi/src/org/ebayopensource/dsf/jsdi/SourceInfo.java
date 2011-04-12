/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsdi;

import org.mozilla.mod.javascript.debug.DebuggableScript;

/**
 * Class to store information about a script source.
 */
public class SourceInfo implements ISourceInfo {

	private static final long serialVersionUID = -8051720475243424001L;

	/**
	 * An empty array of booleans.
	 */
	private static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

	/**
	 * The script.
	 */
	private String m_text;

	/**
	 * The URI of the script.
	 */
	private String m_uri;

	/**
	 * Array indicating which lines can have breakpoints set.
	 */
	private boolean[] m_breakableLines;

	/**
	 * Array indicating whether a breakpoint is set on the line.
	 */
	private boolean[] m_breakpoints;

	/**
	 * Array of FunctionSource objects for the functions in the script.
	 */
	private FunctionSource[] m_functionSources;

	/**
	 * Creates a new SourceInfo object.
	 */
	public SourceInfo(String text, DebuggableScript[] functions,
			String normilizedUri, int[] breakPoints) {
		m_text = text;
		m_uri = normilizedUri;

		int N = functions.length;
		int[][] lineArrays = new int[N][];
		for (int i = 0; i != N; ++i) {
			lineArrays[i] = functions[i].getLineNumbers();
		}

		int minAll = 0, maxAll = -1;
		int[] firstLines = new int[N];
		for (int i = 0; i != N; ++i) {
			int[] lines = lineArrays[i];
			if (lines == null || lines.length == 0) {
				firstLines[i] = -1;
			} else {
				int min, max;
				min = max = lines[0];
				for (int j = 1; j != lines.length; ++j) {
					int line = lines[j];
					if (line < min) {
						min = line;
					} else if (line > max) {
						max = line;
					}
				}
				firstLines[i] = min;
				if (minAll > maxAll) {
					minAll = min;
					maxAll = max;
				} else {
					if (min < minAll) {
						minAll = min;
					}
					if (max > maxAll) {
						maxAll = max;
					}
				}
			}
		}

		if (minAll > maxAll) {
			// No line information
			m_breakableLines = EMPTY_BOOLEAN_ARRAY;
			m_breakpoints = EMPTY_BOOLEAN_ARRAY;
		} else {
			if (minAll < 0) {
				// Line numbers can not be negative
				throw new IllegalStateException(String.valueOf(minAll));
			}
			int linesTop = maxAll + 1;
			m_breakableLines = new boolean[linesTop];
			m_breakpoints = new boolean[linesTop];
			for (int i = 0; i != N; ++i) {
				int[] lines = lineArrays[i];
				if (lines != null && lines.length != 0) {
					for (int j = 0; j != lines.length; ++j) {
						int line = lines[j];
						m_breakableLines[line] = true;
					}
				}
			}
		}
		//set breakpoints
		if (breakPoints != null) {
			for (int line : breakPoints) {
				if (line < m_breakpoints.length && isBreakableLine(line)) {
					m_breakpoints[line] = true;
				}
			}
		}
		
		m_functionSources = new FunctionSource[N];
		for (int i = 0; i != N; ++i) {
			String name = functions[i].getFunctionName();
			if (name == null) {
				name = "";
			}
			m_functionSources[i] = new FunctionSource(this,
					firstLines[i], name);
		}
	}

	public String getText() {
		return m_text;
	}

	public String getUri() {
		return m_uri;
	}

	/**
	 * Returns the number of FunctionSource objects stored in this object.
	 */
	public int getFunctionSourceCount() {
		return m_functionSources.length;
	}

	/**
	 * Returns the FunctionSource object with the given index.
	 */
	public FunctionSource getFunctionSource(int i) {
		return m_functionSources[i];
	}

	/**
	 * Copies the breakpoints from the given SourceInfo object into this
	 * one.
	 */
	public void copyBreakpointsFrom(SourceInfo old) {
		int end = old.m_breakpoints.length;
		if (end > m_breakpoints.length) {
			end = m_breakpoints.length;
		}
		for (int line = 0; line != end; ++line) {
			if (old.m_breakpoints[line]) {
				m_breakpoints[line] = true;
			}
		}
	}

	public boolean isBreakableLine(int line) {
		return (line < m_breakableLines.length)
				&& m_breakableLines[line];
	}

	public boolean isBreakpoint(int line) {
		if (!isBreakableLine(line)) {
			throw new IllegalArgumentException(String.valueOf(line));
		}
		return line < m_breakpoints.length && m_breakpoints[line];
	}

	public boolean setBreakpoint(int line, boolean value) {
		if (!isBreakableLine(line)) {
			throw new IllegalArgumentException(String.valueOf(line));
		}
		boolean changed;
		synchronized (m_breakpoints) {
			if (m_breakpoints[line] != value) {			
				m_breakpoints[line] = value;
				changed = true;
			} else {
				changed = false;
			}
		}
		return changed;
	}

	/**
	 * Removes all breakpoints from the script.
	 */
	public void removeAllBreakpoints() {
		synchronized (m_breakpoints) {
			for (int line = 0; line != m_breakpoints.length; ++line) {
				m_breakpoints[line] = false;
			}
		}
	}

	public boolean[] getBreakpoints() {
		return m_breakpoints;
	}
}
