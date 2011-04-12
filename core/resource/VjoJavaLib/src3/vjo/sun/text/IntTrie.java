package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   IntTrie.java

import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

import java.io.IOException ;

import vjo.java.lang.* ;
import vjo.java.lang.Character ;

import vjo.java.io.InputStream ;
import vjo.java.io.DataInputStream ;

//Referenced classes of package sun.text:
//         Trie

public class IntTrie extends Trie {

	public IntTrie(InputStream inputstream, Trie.DataManipulate datamanipulate)
			throws IOException {
		super(inputstream, datamanipulate);
		if (!isIntTrie())
			throw new IllegalArgumentException(
					"Data given does not belong to a int trie.");
		else
			return;
	}

	public final int getCodePointValue(int i) {
		int j = getCodePointOffset(i);
		return j < 0 ? m_initialValue_ : m_data_[j];
	}

	public final int getLeadValue(char c) {
		return m_data_[getLeadOffset(c)];
	}

	public final int getBMPValue(char c) {
		return m_data_[getBMPOffset(c)];
	}

	public final int getSurrogateValue(char c, char c1) {
		if (!Character.isHighSurrogate(c) || !Character.isLowSurrogate(c1))
			throw new IllegalArgumentException(
					"Argument characters do not form a supplementary character");
		int i = getSurrogateOffset(c, c1);
		if (i > 0)
			return m_data_[i];
		else
			return m_initialValue_;
	}

	public final int getTrailValue(int i, char c) {
		if (m_dataManipulate_ == null)
			throw new NullPointerException(
					"The field DataManipulate in this Trie is null");
		int j = m_dataManipulate_.getFoldingOffset(i);
		if (j > 0)
			return m_data_[getRawOffset(j, (char) (c & 1023))];
		else
			return m_initialValue_;
	}

	public final int getLatin1LinearValue(char c) {
		return m_data_[32 + c];
	}

	protected final void unserialize(InputStream inputstream)
			throws IOException {
		super.unserialize(inputstream);
		m_data_ = new int[m_dataLength_];
		DataInputStream datainputstream = new DataInputStream(inputstream);
		byte abyte0[] = new byte[m_dataLength_ * 4];
		datainputstream.read(abyte0);
		int i = 0;
		for (int j = 0; j < m_dataLength_; j++)
			m_data_[j] = abyte0[i++] << 24 & -16777216 | abyte0[i++] << 16
					& 16711680 | abyte0[i++] << 8 & 65280 | abyte0[i++] & 255;

		m_initialValue_ = m_data_[0];
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

	IntTrie(char ac[], int ai[], int i, int j,
			Trie.DataManipulate datamanipulate) {
		super(ac, j, datamanipulate);
		m_index_ = ac;
		m_data_ = ai;
		m_dataLength_ = m_data_.length;
		m_initialValue_ = i;
	}

	private int m_initialValue_;

	private int m_data_[];
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
