package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   Trie.java
import java.lang.IllegalArgumentException;

import java.io.IOException;

import vjo.java.lang.* ;

import vjo.java.io.DataInputStream;
import vjo.java.io.InputStream ;

public abstract class Trie {
	public static interface DataManipulate {

		public abstract int getFoldingOffset(int i);
	}

	public final boolean isLatin1Linear() {
		return m_isLatin1Linear_;
	}

	protected Trie(InputStream inputstream, DataManipulate datamanipulate)
			throws IOException {
		DataInputStream datainputstream = new DataInputStream(inputstream);
		int i = datainputstream.readInt();
		m_options_ = datainputstream.readInt();
		if (!checkHeader(i)) {
			throw new IllegalArgumentException(
					"ICU data file error: Trie header authentication failed, please check if you have the most updated ICU data file");
		} else {
			m_dataManipulate_ = datamanipulate;
			m_isLatin1Linear_ = (m_options_ & 512) != 0;
			m_dataOffset_ = datainputstream.readInt();
			m_dataLength_ = datainputstream.readInt();
			unserialize(inputstream);
			return;
		}
	}

	protected Trie(char ac[], int i, DataManipulate datamanipulate) {
		m_options_ = i;
		m_dataManipulate_ = datamanipulate;
		m_isLatin1Linear_ = (m_options_ & 512) != 0;
		m_index_ = ac;
	}

	protected abstract int getSurrogateOffset(char c, char c1);

	protected abstract int getValue(int i);

	protected abstract int getInitialValue();

	protected final int getRawOffset(int i, char c) {
		return (m_index_[i + (c >> 5)] << 2) + (c & 31);
	}

	protected final int getBMPOffset(char c) {
		return c < '\uD800' || c > '\uDBFF' ? getRawOffset(0, c)
				: getRawOffset(320, c);
	}

	protected final int getLeadOffset(char c) {
		return getRawOffset(0, c);
	}

	protected final int getCodePointOffset(int i) {
		if (i >= 0) {
			if (i < 65536)
				return getBMPOffset((char) i);
			if (i <= 1114111)
				return getSurrogateOffset((char) ((i - 65536 >> 10) + 55296),
						(char) (i & 1023));
		}
		return -1;
	}

	protected void unserialize(InputStream inputstream) throws IOException {
		m_index_ = new char[m_dataOffset_];
		DataInputStream datainputstream = new DataInputStream(inputstream);
		byte abyte0[] = new byte[m_dataOffset_ * 2];
		datainputstream.read(abyte0);
		int i = 0;
		for (int j = 0; j < m_dataOffset_; j++)
			m_index_[j] = (char) (abyte0[i++] << 8 | abyte0[i++] & 255);

	}

	protected final boolean isIntTrie() {
		return (m_options_ & 256) != 0;
	}

	protected final boolean isCharTrie() {
		return (m_options_ & 256) == 0;
	}

	private final boolean checkHeader(int i) {
		if (i != 1416784229)
			return false;
		return (m_options_ & 15) == 5 && (m_options_ >> 4 & 15) == 2;
	}

	protected static final int LEAD_INDEX_OFFSET_ = 320;

	protected static final int INDEX_STAGE_1_SHIFT_ = 5;

	protected static final int INDEX_STAGE_2_SHIFT_ = 2;

	protected static final int INDEX_STAGE_3_MASK_ = 31;

	protected static final int SURROGATE_MASK_ = 1023;

	protected char m_index_[];

	protected DataManipulate m_dataManipulate_;

	protected int m_dataOffset_;

	protected int m_dataLength_;

	private static final int HEADER_OPTIONS_LATIN1_IS_LINEAR_MASK_ = 512;

	private static final int HEADER_SIGNATURE_ = 1416784229;

	private static final int HEADER_OPTIONS_SHIFT_MASK_ = 15;

	private static final int HEADER_OPTIONS_INDEX_SHIFT_ = 4;

	private static final int HEADER_OPTIONS_DATA_IS_32_BIT_ = 256;

	private boolean m_isLatin1Linear_;

	private int m_options_;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
