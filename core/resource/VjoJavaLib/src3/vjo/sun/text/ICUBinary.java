package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   ICUBinary.java

import java.io.IOException ;

import vjo.java.lang.* ;

import vjo.java.io.DataInputStream ;
import vjo.java.io.InputStream ;

import vjo.java.util.Arrays;


public final class ICUBinary {
	public static interface Authenticate {

		public abstract boolean isDataVersionAcceptable(byte abyte0[]);
	}

	public ICUBinary() {
	}

	public static final byte[] readHeader(InputStream inputstream,
			byte abyte0[], Authenticate authenticate) throws IOException {
		DataInputStream datainputstream = new DataInputStream(inputstream);
		char c = datainputstream.readChar();
		c -= '\002';
		byte byte0 = datainputstream.readByte();
		c--;
		byte byte1 = datainputstream.readByte();
		c--;
		if (byte0 != -38 || byte1 != 39)
			throw new IOException("ICU data file error: Not an ICU data file");
		datainputstream.readChar();
		c -= '\002';
		datainputstream.readChar();
		c -= '\002';
		byte byte2 = datainputstream.readByte();
		c--;
		byte byte3 = datainputstream.readByte();
		c--;
		byte byte4 = datainputstream.readByte();
		c--;
		datainputstream.readByte();
		c--;
		byte abyte1[] = new byte[4];
		datainputstream.readFully(abyte1);
		c -= '\004';
		byte abyte2[] = new byte[4];
		datainputstream.readFully(abyte2);
		c -= '\004';
		byte abyte3[] = new byte[4];
		datainputstream.readFully(abyte3);
		c -= '\004';
		datainputstream.skipBytes(c);
		if (byte2 != 1 || byte3 != 0 || byte4 != 2
				|| !Arrays.equals(abyte0, abyte1) || authenticate != null
				&& !authenticate.isDataVersionAcceptable(abyte2))
			throw new IOException(
					"ICU data file error: Header authentication failed, please check if you have a valid ICU data file");
		else
			return abyte3;
	}

	private static final byte MAGIC1 = -38;

	private static final byte MAGIC2 = 39;

	private static final byte BIG_ENDIAN_ = 1;

	private static final byte CHAR_SET_ = 0;

	private static final byte CHAR_SIZE_ = 2;

	private static final String MAGIC_NUMBER_AUTHENTICATION_FAILED_ = "ICU data file error: Not an ICU data file";

	private static final String HEADER_AUTHENTICATION_FAILED_ = "ICU data file error: Header authentication failed, please check if you have a valid ICU data file";
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
