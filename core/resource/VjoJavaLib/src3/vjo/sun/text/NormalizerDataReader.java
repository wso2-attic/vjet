package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   NormalizerDataReader.java

import java.io.IOException ;

import vjo.java.lang.* ;

import vjo.java.io.DataInputStream ;
import vjo.java.io.InputStream ;

//Referenced classes of package sun.text:
//         ICUBinary

final class NormalizerDataReader implements ICUBinary.Authenticate {

	protected NormalizerDataReader(InputStream inputstream) throws IOException {
		ICUBinary.readHeader(inputstream, DATA_FORMAT_ID, this);
		dataInputStream = new DataInputStream(inputstream);
	}

	protected int[] readIndexes(int i) throws IOException {
		int ai[] = new int[i];
		for (int j = 0; j < i; j++)
			ai[j] = dataInputStream.readInt();

		return ai;
	}

	protected void read(byte abyte0[], byte abyte1[], byte abyte2[], char ac[],
			char ac1[], Object aobj[]) throws IOException {
		dataInputStream.read(abyte0);
		byte abyte3[] = new byte[(ac.length + ac1.length) * 2];
		dataInputStream.read(abyte3);
		int i = 0;
		for (int j = 0; j < ac.length; j++)
			ac[j] = (char) (abyte3[i++] << 8 | abyte3[i++] & 255);

		for (int k = 0; k < ac1.length; k++)
			ac1[k] = (char) (abyte3[i++] << 8 | abyte3[i++] & 255);

		dataInputStream.read(abyte1);
	}

	public byte[] getDataFormatVersion() {
		return DATA_FORMAT_VERSION;
	}

	public boolean isDataVersionAcceptable(byte abyte0[]) {
		return abyte0[0] == DATA_FORMAT_VERSION[0]
				&& abyte0[2] == DATA_FORMAT_VERSION[2]
				&& abyte0[3] == DATA_FORMAT_VERSION[3];
	}

	private DataInputStream dataInputStream;

	private static final byte DATA_FORMAT_ID[] = { 78, 111, 114, 109 };

	private static final byte DATA_FORMAT_VERSION[] = { 2, 2, 5, 2 };

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 0 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/