package vjo.net;

/*
 * @(#)src/classes/sov/java/net/SocketOutputStream.java, net, asdev, 20070119 1.13
 * ===========================================================================
 * Licensed Materials - Property of IBM
 * "Restricted Materials of IBM"
 *
 * IBM SDK, Java(tm) 2 Technology Edition, v5.0
 * (C) Copyright IBM Corp. 1998, 2005. All Rights Reserved
 * ===========================================================================
 */

/*
 * ===========================================================================
 (C) Copyright Sun Microsystems Inc, 1992, 2004. All rights reserved.
 * ===========================================================================
 */

import java.lang.ArrayIndexOutOfBoundsException;

import java.io.IOException;

import vjo.lang.* ;

import vjo.io.FileDescriptor;
import vjo.io.FileOutputStream;

//import java.nio.channels.FileChannel;

/**
 * This stream extends FileOutputStream to implement a
 * SocketOutputStream. Note that this class should <b>NOT</b> be
 * public.
 *
 * @version     1.30, 12/19/03
 * @author 	Jonathan Payne
 * @author	Arthur van Hoff
 */
class SocketOutputStream extends FileOutputStream
{
    static {
        init();
    }

    private PlainSocketImpl impl = null;
    private byte temp[] = new byte[1];
    private Socket socket = null;
    
    /**
     * Creates a new SocketOutputStream. Can only be called
     * by a Socket. This method needs to hang on to the owner Socket so
     * that the fd will not be closed.
     * @param impl the socket output stream inplemented
     */
    SocketOutputStream(PlainSocketImpl impl) throws IOException {
	super(impl.getFileDescriptor());
	this.impl = impl;
	socket = impl.getSocket();
    }

    /**
     * Returns the unique {@link java.nio.channels.FileChannel FileChannel}
     * object associated with this file output stream. </p>
     *
     * The <code>getChannel</code> method of <code>SocketOutputStream</code>
     * returns <code>null</code> since it is a socket based stream.</p>
     *
     * @return  the file channel associated with this file output stream
     *
     * @since 1.4
     * @spec JSR-51
     */
    public final FileChannel getChannel() {
        return null;
    }

    /**
     * Writes to the socket.
     * @param fd the FileDescriptor
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    private native void socketWrite0(FileDescriptor fd, byte[] b, int off,
				     int len) throws IOException;

    /**
     * Writes to the socket with appropriate locking of the 
     * FileDescriptor.
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    private void socketWrite(byte b[], int off, int len) throws IOException {

	if (len <= 0 || off < 0 || off + len > b.length) {
	    if (len == 0) {
		return;
	    }
	    throw new ArrayIndexOutOfBoundsException();
	}

	FileDescriptor fd = impl.acquireFD();
	try {
	    socketWrite0(fd, b, off, len);
	} catch (SocketException se) {
	    if (se instanceof sun.net.ConnectionResetException) {
		impl.setConnectionResetPending();
		se = new SocketException("Connection reset");
	    }
	    if (impl.isClosedOrPending()) {
                throw new SocketException("Socket closed");
            } else {
		throw se;
	    }
	} finally {
	    impl.releaseFD();
	}
    }

    /** 
     * Writes a byte to the socket. 
     * @param b the data to be written
     * @exception IOException If an I/O error has occurred. 
     */
    public void write(int b) throws IOException {
	temp[0] = (byte)b;
	socketWrite(temp, 0, 1);
    }

    /** 
     * Writes the contents of the buffer <i>b</i> to the socket.
     * @param b the data to be written
     * @exception SocketException If an I/O error has occurred. 
     */
    public void write(byte b[]) throws IOException {
	socketWrite(b, 0, b.length);
    }

    /** 
     * Writes <i>length</i> bytes from buffer <i>b</i> starting at 
     * offset <i>len</i>.
     * @param b the data to be written
     * @param off the start offset in the data
     * @param len the number of bytes that are written
     * @exception SocketException If an I/O error has occurred.
     */
    public void write(byte b[], int off, int len) throws IOException {
	socketWrite(b, off, len);
    }

    /**
     * Closes the stream.
     */
    private boolean closing = false;
    public void close() throws IOException {
	// Prevent recursion. See BugId 4484411
	if (closing)
	    return;
	closing = true;
	if (socket != null) {
	    if (!socket.isClosed())
		socket.close();
	} else
	    impl.close();
	closing = false;
    }

    /** 
     * Overrides finalize, the fd is closed by the Socket.
     */
    protected void finalize() {}

    /**
     * Perform class load-time initializations.
     */
    private native static void init();

}

