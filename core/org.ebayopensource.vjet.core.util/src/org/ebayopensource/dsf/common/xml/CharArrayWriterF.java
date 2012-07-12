/*******************************************************************************
 * Copyright (c) 2005-2012 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.xml;

import java.io.IOException;
import java.io.Writer;

/**
 * This class takes an input char[] buffer so that it can be used as a Writer.  
 * 
 * The data can be retrieved "copied" using toCharArray() and toString().
 * 
 * getBackingChars() will return the passed in or set char[] UNLESS the current in
 * char[] size was exceeded and then a new char[] buffer is created.
 * 
 * setBackingChars(char[]) can be called at anytime.  It is up to the client to
 * understand that replacing the backing char[] simply means to continue processing
 * but use the newly passed in char[].
 *
 * This implementation is NOT threadsafe and was designed for maximum speed
 * and the reuse of externally provided char[]'s.  Superclass methods:
 * write(char[], int, int), write(int), write(String, int, int) were specifically
 * overriden to NOT use a lock.
 * 
 * <P>
 * Note: Invoking close() on this class has no effect, and methods
 * of this class can be called after the stream has closed
 * without generating an IOException.
 */
public class CharArrayWriterF extends Writer {
    // The buffer where data is stored.
    private char m_buf[];

    // The number of chars in the buffer.
    private int m_count;

    /**
     * Creates a new CharArrayWriterF.  The passed in char[] must be non-null and size > 0.
     */
    public CharArrayWriterF(char[] buf) {
    	setBackingChars(buf) ;
    }

    /**
     * Return the "true" backing char[] NOT some copy of it.
     */
    public char[] getBackingChars() {
    	return m_buf ;
    }
    
    public void setBackingChars(char[] buf) {
    	if (buf == null || buf.length == 0) {
    		throw new RuntimeException("Backing char[] must not be null or of size 0") ;
    	}
    	m_buf = buf ;
    }
    
    /**
     * Writes a character to the buffer.
     */
    @Override
    public void write(int c) {
	    int newcount = m_count + 1;
	    grow(newcount) ;
	    m_buf[m_count] = (char)c;
	    m_count = newcount;
    }

    /**
     * Writes characters to the buffer.
     * @param c	the data to be written
     * @param off	the start offset in the data
     * @param len	the number of chars that are written
     */
    @Override
    public void write(char c[], int off, int len) {
		if ((off < 0) 
				|| (off > c.length) 
				|| (len < 0) 
				|| ((off + len) > c.length) 
	            || ((off + len) < 0)) {
		    throw new IndexOutOfBoundsException();
		} 
		else if (len == 0) {
		    return;
		}

	    int newcount = m_count + len;
	    grow(newcount) ;
	    
	    System.arraycopy(c, off, m_buf, m_count, len);
	    m_count = newcount;
    }

    /**
     * Write a portion of a string to the buffer.
     * @param  str  String to be written from
     * @param  off  Offset from which to start reading characters
     * @param  len  Number of characters to be written
     */
    @Override
    public void write(String str, int off, int len) {
	    int newcount = m_count + len;
	    grow(newcount) ;
	    str.getChars(off, off + len, m_buf, m_count);
	    m_count = newcount;
    }

    /**
     * Writes the contents of the buffer to another character stream.
     *
     * @param out	the output stream to write to
     * @throws IOException If an I/O error occurs.
     */
    public void writeTo(Writer out) throws IOException {
	    out.write(m_buf, 0, m_count);
    }

    /**
     * Appends the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.toString()) </pre>
     *
     * <p> Depending on the specification of <tt>toString</tt> for the
     * character sequence <tt>csq</tt>, the entire sequence may not be
     * appended. For instance, invoking the <tt>toString</tt> method of a
     * character buffer will return a subsequence whose content depends upon
     * the buffer's position and limit.
     *
     * @param  csq
     *         The character sequence to append.  If <tt>csq</tt> is
     *         <tt>null</tt>, then the four characters <tt>"null"</tt> are
     *         appended to this writer.
     *
     * @return  This writer
     *
     * @since  1.5
     */
    public CharArrayWriterF append(CharSequence csq) {
		String s = (csq == null ? "null" : csq.toString());
		write(s, 0, s.length());
		return this;
    }

    /**
     * Appends a subsequence of the specified character sequence to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(csq, start,
     * end)</tt> when <tt>csq</tt> is not <tt>null</tt>, behaves in
     * exactly the same way as the invocation
     *
     * <pre>
     *     out.write(csq.subSequence(start, end).toString()) </pre>
     *
     * @param  csq
     *         The character sequence from which a subsequence will be
     *         appended.  If <tt>csq</tt> is <tt>null</tt>, then characters
     *         will be appended as if <tt>csq</tt> contained the four
     *         characters <tt>"null"</tt>.
     *
     * @param  start
     *         The index of the first character in the subsequence
     *
     * @param  end
     *         The index of the character following the last character in the
     *         subsequence
     *
     * @return  This writer
     *
     * @throws  IndexOutOfBoundsException
     *          If <tt>start</tt> or <tt>end</tt> are negative, <tt>start</tt>
     *          is greater than <tt>end</tt>, or <tt>end</tt> is greater than
     *          <tt>csq.length()</tt>
     *
     * @since  1.5
     */
    public CharArrayWriterF append(CharSequence csq, int start, int end) {
		String s = (csq == null ? "null" : csq).subSequence(start, end).toString();
		write(s, 0, s.length());
		return this;
    }

    /**
     * Appends the specified character to this writer.
     *
     * <p> An invocation of this method of the form <tt>out.append(c)</tt>
     * behaves in exactly the same way as the invocation
     *
     * <pre>
     *     out.write(c) </pre>
     *
     * @param  c
     *         The 16-bit character to append
     *
     * @return  This writer
     *
     * @since 1.5
     */
    public CharArrayWriterF append(char c) {
    	write(c);
    	return this;
    }

    /**
     * Resets the buffer so that you can use it again without
     * throwing away the already allocated buffer.
     */
    public void reset() {
    	m_count = 0;
    }

    /**
     * Returns a copy of the input data.
     *
     * @return an array of chars copied from the input data.
     */
    public char toCharArray()[] {
	    char newbuf[] = new char[m_count];
	    System.arraycopy(m_buf, 0, newbuf, 0, m_count);
	    return newbuf;
    }

    /**
     * Returns the current size of the buffer.
     *
     * @return an int representing the current size of the buffer.
     */
    public int size() {
    	return m_count;
    }

    /**
     * Converts input data to a string.
     * @return the string.
     */
    public String toString() {
	    return new String(m_buf, 0, m_count);
    }

    /**
     * Flush the stream.
     */
    public void flush() { }

    /**
     * Close the stream.  This method does not release the buffer, since its
     * contents might still be required. Note: Invoking this method in this class
     * will have no effect.
     */
    public void close() { }
    
    //
    // Private
    //
    private void grow(int newcount) {
	    if (newcount > m_buf.length) {
//	    	throw new RuntimeException("buf size of " + m_buf.length + " exceeded by need for " + newcount) ;
			char newbuf[] = new char[Math.max(m_buf.length << 1, newcount)];
			System.arraycopy(m_buf, 0, newbuf, 0, m_count);
			m_buf = newbuf;
	    }
    }
}
