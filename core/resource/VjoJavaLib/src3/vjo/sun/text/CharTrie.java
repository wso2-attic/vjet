package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   CharTrie.java
import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

import java.io.IOException ;

import vjo.java.lang.* ;

import vjo.java.io.DataInputStream ;
import vjo.java.io.InputStream ;

//Referenced classes of package sun.text:
//         Trie

class CharTrie extends Trie {

	public CharTrie(InputStream inputstream, Trie.DataManipulate datamanipulate)
			throws IOException {
		super(inputstream, datamanipulate);
		if (!isCharTrie())
			throw new IllegalArgumentException(
					"Data given does not belong to a char trie.");
		else
			return;
	}

	public final char getCodePointValue(int i) {
		int j = getCodePointOffset(i);
		return j < 0 ? m_initialValue_ : m_data_[j];
	}

	public final char getLeadValue(char c) {
		return m_data_[getLeadOffset(c)];
	}

	public final char getBMPValue(char c) {
		return m_data_[getBMPOffset(c)];
	}

	public final char getSurrogateValue(char c, char c1) {
		int i = getSurrogateOffset(c, c1);
		if (i > 0)
			return m_data_[i];
		else
			return m_initialValue_;
	}

	public final char getTrailValue(int i, char c) {
		if (m_dataManipulate_ == null)
			throw new NullPointerException(
					"The field DataManipulate in this Trie is null");
		int j = m_dataManipulate_.getFoldingOffset(i);
		if (j > 0)
			return m_data_[getRawOffset(j, (char) (c & 1023))];
		else
			return m_initialValue_;
	}

	public final char getLatin1LinearValue(char c) {
		return m_data_[32 + m_dataOffset_ + c];
	}

	protected final void unserialize(InputStream inputstream)
			throws IOException {
		DataInputStream datainputstream = new DataInputStream(inputstream);
		int i = m_dataOffset_ + m_dataLength_;
		m_index_ = new char[i];
		byte abyte0[] = new byte[i * 2];
		datainputstream.read(abyte0);
		int j = 0;
		for (int k = 0; k < i; k++)
			m_index_[k] = (char) (abyte0[j++] << 8 | abyte0[j++] & 255);

		m_data_ = m_index_;
		m_initialValue_ = m_data_[m_dataOffset_];
	}

	protected final int getSurrogateOffset(char c, char c1) {
		if (m_dataManipulate_ == null)
			throw new NullPointerException(
					"The field DataManipulate in this Trie is null");
		int i = m_dataManipulate_.getFoldingOffset(getLeadValue(c));
		if (i > 0)
			return getRawOffset(i, (char) (c1 & 1023));
		else
			return -1;
	}

	protected final int getValue(int i) {
		return m_data_[i];
	}

	protected final int getInitialValue() {
		return m_initialValue_;
	}

	private char m_initialValue_;

	private char m_data_[];
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
