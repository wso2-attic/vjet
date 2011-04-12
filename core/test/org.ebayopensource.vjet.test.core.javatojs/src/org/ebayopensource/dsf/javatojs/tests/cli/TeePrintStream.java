/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.tests.cli;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

/**
 * captures output in system in a ByteArrayOutputStream as well sends to console
 * if debug is enabled
 * 
 * 
 * 
 */
public class TeePrintStream extends PrintStream {

	private ByteArrayOutputStream buffer;
	private PrintStream bufferOut;
	private PrintStream console;
	private boolean debug = false;

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public ByteArrayOutputStream getBuffer() {
		return buffer;
	}

	public TeePrintStream(ByteArrayOutputStream stream) {
		super(stream);
		buffer = stream;
		bufferOut = new PrintStream(buffer);
		console = System.out; 
	}

	public void write(int b) {
		if (debug)
			console.write(b);
		bufferOut.write(b);
	}

	public void write(byte buf[], int off, int len) {
		if (debug)
			console.write(buf, off, len);
		bufferOut.write(buf, off, len);
	}

	public void print(boolean b) {
		if (debug)
			console.print(b);
		bufferOut.print(b);
	}

	public void print(char c) {
		if (debug)
			console.write(c);
		bufferOut.write(c);
	}

	public void print(int i) {
		if (debug)
			console.print(i);
		bufferOut.print(i);
	}

	public void print(long l) {
		if (debug)
			console.print(l);
		bufferOut.print(l);
	}

	public void print(float f) {
		if (debug)
			console.print(f);
		bufferOut.print(f);

	}

	public void print(double d) {
		if (debug)
			console.print(d);
		bufferOut.print(d);

	}

	public void print(char s[]) {
		if (debug)
			console.print(s);
		bufferOut.print(s);

	}

	public void print(String s) {
		if (debug)
			console.print(s);
		bufferOut.print(s);

	}

	public void print(Object obj) {
		console.print(obj);
		bufferOut.print(obj);

	}

	public void println() {
		if (debug)
			console.println();
		bufferOut.println();

	}

	public void println(boolean x) {
		if (debug)
			console.println(x);
		bufferOut.println(x);

	}

	public void println(char x) {
		if (debug)
			console.println(x);
		bufferOut.println(x);

	}

	public void println(int x) {
		if (debug)
			console.println(x);
		bufferOut.println(x);

	}

	public void println(long x) {
		if (debug)
			console.println(x);
		bufferOut.println(x);

	}

	public void println(float x) {
		if (debug)
			console.println(x);
		bufferOut.println(x);

	}

	public void println(double x) {
		console.println(x);
		bufferOut.println(x);

	}

	public void println(char x[]) {
		if (debug)
			console.println(x);
		bufferOut.println(x);

	}

	public void println(String x) {
		if (debug)
			console.println(x);
		bufferOut.println(x);

	}

	public void println(Object x) {
		if (debug)
			console.println(x);
		bufferOut.println(x);

	}

	public PrintStream printf(String format, Object... args) {
		if (debug)
			console.format(format, args);
		return bufferOut.format(format, args);
	}

	public PrintStream printf(Locale l, String format, Object... args) {
		if (debug)
			console.format(l, format, args);

		return bufferOut.format(l, format, args);
	}

	public PrintStream format(String format, Object... args) {

		if (debug)
			console.format(format, args);

		return bufferOut.format(format, args);

	}

	public PrintStream format(Locale l, String format, Object... args) {

		if (debug)
			console.format(l, format, args);
		return bufferOut.format(l, format, args);

	}

	public PrintStream append(CharSequence csq) {

		if (debug)
			console.append(csq);
		return bufferOut.append(csq);
	}

	public PrintStream append(CharSequence csq, int start, int end) {

		if (debug)
			console.append(csq, start, end);
		return bufferOut.append(csq, start, end);
	}

	public PrintStream append(char c) {

		if (debug)
			console.append(c);
		return bufferOut.append(c);
	}
}
