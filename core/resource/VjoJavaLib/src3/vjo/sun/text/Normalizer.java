package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   Normalizer.java
import java.lang.IndexOutOfBoundsException;
import java.lang.InternalError;

import java.text.CharacterIterator;

import vjo.java.lang.* ;
import vjo.java.lang.Character ;
import vjo.java.lang.Integer ;
import vjo.java.lang.Math; 
import vjo.java.lang.StringBuffer ;
import vjo.java.lang.System ;

//Referenced classes of package sun.text:
//         UCharacterIterator, NormalizerImpl, Utility

public final class Normalizer implements java.lang.Cloneable {
	private static final class IsNextTrueStarter implements IsNextBoundary {

		public boolean isNextBoundary(UCharacterIterator ucharacteriterator,
				int i, int j, int ai[]) {
			int k = j << 2 & 15;
			long l = Normalizer.getNextNorm32(ucharacteriterator, i, j | k, ai);
			return NormalizerImpl.isTrueStarter(l, j, k);
		}

		private IsNextTrueStarter() {
		}

	}

	private static final class IsNextNFDSafe implements IsNextBoundary {

		public boolean isNextBoundary(UCharacterIterator ucharacteriterator,
				int i, int j, int ai[]) {
			return NormalizerImpl.isNFDSafe(Normalizer.getNextNorm32(
					ucharacteriterator, i, j, ai), j, j & 63);
		}

		private IsNextNFDSafe() {
		}

	}

	private static interface IsNextBoundary {

		public abstract boolean isNextBoundary(
				UCharacterIterator ucharacteriterator, int i, int j, int ai[]);
	}

	private static final class IsPrevTrueStarter implements IsPrevBoundary {

		public boolean isPrevBoundary(UCharacterIterator ucharacteriterator,
				int i, int j, char ac[]) {
			int k = j << 2 & 15;
			long l = Normalizer.getPrevNorm32(ucharacteriterator, i, j | k, ac);
			return NormalizerImpl.isTrueStarter(l, j, k);
		}

		private IsPrevTrueStarter() {
		}

	}

	private static final class IsPrevNFDSafe implements IsPrevBoundary {

		public boolean isPrevBoundary(UCharacterIterator ucharacteriterator,
				int i, int j, char ac[]) {
			return NormalizerImpl.isNFDSafe(Normalizer.getPrevNorm32(
					ucharacteriterator, i, j, ac), j, j & 63);
		}

		private IsPrevNFDSafe() {
		}

	}

	private static interface IsPrevBoundary {

		public abstract boolean isPrevBoundary(
				UCharacterIterator ucharacteriterator, int i, int j, char ac[]);
	}

	public static final class QuickCheckResult {

		private int resultValue;

		private QuickCheckResult(int i) {
			resultValue = i;
		}

	}

	private static final class FCDMode extends Mode {

		protected QuickCheckResult quickCheck(char ac[], int i, int j,
				boolean flag, int k) {
			return NormalizerImpl.checkFCD(ac, i, j, k) ? Normalizer.YES
					: Normalizer.NO;
		}

		private FCDMode(int i) {
			super(i);
		}

	}

	private static final class NFKCMode extends Mode {

		protected int normalize(char ac[], int i, int j, char ac1[], int k,
				int l, int i1) {
			return NormalizerImpl.compose(ac, i, j, ac1, k, l, true, i1);
		}

		protected String normalize(String s, int i) {
			return Normalizer.compose(s, true, i);
		}

		protected int getMinC() {
			return NormalizerImpl.getFromIndexesArr(7);
		}

		protected IsPrevBoundary getPrevBoundary() {
			return new IsPrevTrueStarter();
		}

		protected IsNextBoundary getNextBoundary() {
			return new IsNextTrueStarter();
		}

		protected int getMask() {
			return 65314;
		}

		protected QuickCheckResult quickCheck(char ac[], int i, int j,
				boolean flag, int k) {
			return NormalizerImpl.quickCheck(ac, i, j, NormalizerImpl
					.getFromIndexesArr(7), 34, flag, k);
		}

		private NFKCMode(int i) {
			super(i);
		}

	}

	private static final class NFCMode extends Mode {

		protected int normalize(char ac[], int i, int j, char ac1[], int k,
				int l, int i1) {
			return NormalizerImpl.compose(ac, i, j, ac1, k, l, false, i1);
		}

		protected String normalize(String s, int i) {
			return Normalizer.compose(s, false, i);
		}

		protected int getMinC() {
			return NormalizerImpl.getFromIndexesArr(6);
		}

		protected IsPrevBoundary getPrevBoundary() {
			return new IsPrevTrueStarter();
		}

		protected IsNextBoundary getNextBoundary() {
			return new IsNextTrueStarter();
		}

		protected int getMask() {
			return 65297;
		}

		protected QuickCheckResult quickCheck(char ac[], int i, int j,
				boolean flag, int k) {
			return NormalizerImpl.quickCheck(ac, i, j, NormalizerImpl
					.getFromIndexesArr(6), 17, flag, k);
		}

		private NFCMode(int i) {
			super(i);
		}

	}

	private static final class NFKDMode extends Mode {

		protected int normalize(char ac[], int i, int j, char ac1[], int k,
				int l, int i1) {
			int ai[] = new int[1];
			return NormalizerImpl.decompose(ac, i, j, ac1, k, l, true, ai, i1);
		}

		protected String normalize(String s, int i) {
			return Normalizer.decompose(s, true, i);
		}

		protected int getMinC() {
			return 768;
		}

		protected IsPrevBoundary getPrevBoundary() {
			return new IsPrevNFDSafe();
		}

		protected IsNextBoundary getNextBoundary() {
			return new IsNextNFDSafe();
		}

		protected int getMask() {
			return 65288;
		}

		protected QuickCheckResult quickCheck(char ac[], int i, int j,
				boolean flag, int k) {
			return NormalizerImpl.quickCheck(ac, i, j, NormalizerImpl
					.getFromIndexesArr(9), 8, flag, k);
		}

		private NFKDMode(int i) {
			super(i);
		}

	}

	private static final class NFDMode extends Mode {

		protected int normalize(char ac[], int i, int j, char ac1[], int k,
				int l, int i1) {
			int ai[] = new int[1];
			return NormalizerImpl.decompose(ac, i, j, ac1, k, l, false, ai, i1);
		}

		protected String normalize(String s, int i) {
			return Normalizer.decompose(s, false, i);
		}

		protected int getMinC() {
			return 768;
		}

		protected IsPrevBoundary getPrevBoundary() {
			return new IsPrevNFDSafe();
		}

		protected IsNextBoundary getNextBoundary() {
			return new IsNextNFDSafe();
		}

		protected int getMask() {
			return 65284;
		}

		protected QuickCheckResult quickCheck(char ac[], int i, int j,
				boolean flag, int k) {
			return NormalizerImpl.quickCheck(ac, i, j, NormalizerImpl
					.getFromIndexesArr(8), 4, flag, k);
		}

		private NFDMode(int i) {
			super(i);
		}

	}

	public static class Mode {

		protected int normalize(char ac[], int i, int j, char ac1[], int k,
				int l, int i1) {
			int j1 = j - i;
			int k1 = l - k;
			if (j1 > k1) {
				return j1;
			} else {
				System.arraycopy(ac, i, ac1, k, j1);
				return j1;
			}
		}

		protected String normalize(String s, int i) {
			return s;
		}

		protected int getMinC() {
			return -1;
		}

		protected int getMask() {
			return -1;
		}

		protected IsPrevBoundary getPrevBoundary() {
			return null;
		}

		protected IsNextBoundary getNextBoundary() {
			return null;
		}

		protected QuickCheckResult quickCheck(char ac[], int i, int j,
				boolean flag, int k) {
			if (flag)
				return Normalizer.MAYBE;
			else
				return Normalizer.NO;
		}

		private int modeValue;

		private Mode(int i) {
			modeValue = i;
		}

	}

	public Normalizer(String s, Mode mode1, int i) {
		buffer = new char[100];
		bufferStart = 0;
		bufferPos = 0;
		bufferLimit = 0;
		mode = COMPOSE;
		options = 0;
		text = UCharacterIterator.getInstance(s);
		mode = mode1;
		options = i;
	}

	public Normalizer(CharacterIterator characteriterator, Mode mode1, int i) {
		buffer = new char[100];
		bufferStart = 0;
		bufferPos = 0;
		bufferLimit = 0;
		mode = COMPOSE;
		options = 0;
		text = UCharacterIterator
				.getInstance((CharacterIterator) characteriterator.clone());
		mode = mode1;
		options = i;
	}

	private Normalizer(UCharacterIterator ucharacteriterator, Mode mode1, int i) {
		buffer = new char[100];
		bufferStart = 0;
		bufferPos = 0;
		bufferLimit = 0;
		mode = COMPOSE;
		options = 0;
		try {
			text = (UCharacterIterator) ucharacteriterator.clone();
			mode = mode1;
			options = i;
		} catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
			throw new InternalError(clonenotsupportedexception.toString());
		}
	}

	public Normalizer(String s, Mode mode1) {
		this(s, mode1, 0);
	}

	public Normalizer(CharacterIterator characteriterator, Mode mode1) {
		this(characteriterator, mode1, 0);
	}

	public Object clone() {
		try {
			Normalizer normalizer = (Normalizer) super.clone();
			normalizer.text = (UCharacterIterator) text.clone();
			if (buffer != null) {
				normalizer.buffer = new char[buffer.length];
				System
						.arraycopy(buffer, 0, normalizer.buffer, 0,
								buffer.length);
			}
			return normalizer;
		} catch (java.lang.CloneNotSupportedException clonenotsupportedexception) {
			throw new InternalError(clonenotsupportedexception.toString());
		}
	}

	public static String compose(String s, boolean flag, int i) {
		char ac[] = new char[s.length() * 2];
		boolean flag1 = false;
		char ac1[] = s.toCharArray();
		do {
			int j = NormalizerImpl.compose(ac1, 0, ac1.length, ac, 0,
					ac.length, flag, i);
			if (j <= ac.length)
				return new String(ac, 0, j);
			ac = new char[j];
		} while (true);
	}

	public static String decompose(String s, boolean flag, int i) {
		char ac[] = new char[s.length() * 3];
		int ai[] = new int[1];
		boolean flag1 = false;
		do {
			int j = NormalizerImpl.decompose(s.toCharArray(), 0, s.length(),
					ac, 0, ac.length, flag, ai, i);
			if (j <= ac.length)
				return new String(ac, 0, j);
			ac = new char[j];
		} while (true);
	}

	public static String decompose(String s, boolean flag, int i, boolean flag1) {
		char ac[] = new char[s.length() * 3];
		int ai[] = new int[1];
		do {
			int j = NormalizerImpl.decompose(s.toCharArray(), 0, s.length(),
					ac, 0, ac.length, flag, ai, i);
			if (j <= ac.length)
				if (!flag1) {
					return new String(ac, 0, j);
				} else {
					StringBuffer stringbuffer = new StringBuffer();
					return new String(ac, 0, j);
				}
			ac = new char[j];
		} while (true);
	}

	public static String normalize(String s, Mode mode1, int i) {
		return mode1.normalize(s, i);
	}

	private static int normalize(char ac[], int i, int j, char ac1[], int k,
			int l, Mode mode1, int i1) {
		int j1 = mode1.normalize(ac, i, j, ac1, k, l, i1);
		if (j1 <= l - k)
			return j1;
		else
			throw new IndexOutOfBoundsException(Integer.toString(j1));
	}

	public static QuickCheckResult quickCheck(String s, Mode mode1, int i) {
		return mode1.quickCheck(s.toCharArray(), 0, s.length(), true, i);
	}

	public static final int getClass(int i) {
		return NormalizerImpl.getCombiningClass(i);
	}

	public int current() {
		if (bufferPos < bufferLimit || nextNormalize())
			return getCodePointAt(bufferPos);
		else
			return -1;
	}

	public int next() {
		if (bufferPos < bufferLimit || nextNormalize()) {
			int i = getCodePointAt(bufferPos);
			bufferPos += i <= 65535 ? 1 : 2;
			return i;
		} else {
			return -1;
		}
	}

	public int previous() {
		if (bufferPos > 0 || previousNormalize()) {
			int i = getCodePointAt(bufferPos - 1);
			bufferPos -= i <= 65535 ? 1 : 2;
			return i;
		} else {
			return -1;
		}
	}

	public void reset() {
		currentIndex = nextIndex = text.getBeginIndex();
		text.setIndex(currentIndex);
		clearBuffer();
	}

	public void setIndexOnly(int i) {
		text.setIndex(i);
		currentIndex = nextIndex = i;
		clearBuffer();
	}

	public int setIndex(int i) {
		setIndexOnly(i);
		return current();
	}

	public int getBeginIndex() {
		return 0;
	}

	public int getEndIndex() {
		return text.getLength();
	}

	public int first() {
		reset();
		return next();
	}

	public int last() {
		text.setToLimit();
		currentIndex = nextIndex = text.getIndex();
		clearBuffer();
		return previous();
	}

	public int getIndex() {
		if (bufferPos < bufferLimit)
			return currentIndex;
		else
			return nextIndex;
	}

	public void setMode(Mode mode1) {
		mode = mode1;
	}

	public Mode getMode() {
		return mode;
	}

	public void setOption(int i, boolean flag) {
		if (flag)
			options |= i;
		else
			options &= ~i;
	}

	public int getOption(int i) {
		return (options & i) == 0 ? 0 : 1;
	}

	public void setText(String s) {
		UCharacterIterator ucharacteriterator = UCharacterIterator
				.getInstance(s);
		if (ucharacteriterator == null) {
			throw new InternalError("Could not create a new UCharacterIterator");
		} else {
			text = ucharacteriterator;
			reset();
			return;
		}
	}

	public void setText(CharacterIterator characteriterator) {
		UCharacterIterator ucharacteriterator = UCharacterIterator
				.getInstance(characteriterator);
		if (ucharacteriterator == null) {
			throw new InternalError("Could not create a new UCharacterIterator");
		} else {
			text = ucharacteriterator;
			reset();
			return;
		}
	}

	private static long getPrevNorm32(UCharacterIterator ucharacteriterator,
			int i, int j, char ac[]) {
		int k = 0;
		if ((k = ucharacteriterator.previous()) == -1)
			return 0L;
		ac[0] = (char) k;
		ac[1] = '\0';
		if (ac[0] < i)
			return 0L;
		if (ac[0] < '\uD800' || ac[0] > '\uDFFF')
			return NormalizerImpl.getNorm32(ac[0]);
		if (Character.isHighSurrogate(ac[0])
				|| ucharacteriterator.getIndex() == 0) {
			ac[1] = (char) ucharacteriterator.current();
			return 0L;
		}
		if (Character.isHighSurrogate(ac[1] = (char) ucharacteriterator
				.previous())) {
			long l = NormalizerImpl.getNorm32(ac[1]);
			if ((l & (long) j) == 0L)
				return 0L;
			else
				return NormalizerImpl.getNorm32FromSurrogatePair(l, ac[0]);
		} else {
			ucharacteriterator.moveIndex(1);
			return 0L;
		}
	}

	private static int findPreviousIterationBoundary(
			UCharacterIterator ucharacteriterator,
			IsPrevBoundary isprevboundary, int i, int j, char ac[], int ai[]) {
		char ac1[] = new char[2];
		ai[0] = ac.length;
		ac1[0] = '\0';
		boolean flag;
		do {
			if (ucharacteriterator.getIndex() <= 0 || ac1[0] == '\uFFFF')
				break;
			flag = isprevboundary.isPrevBoundary(ucharacteriterator, i, j, ac1);
			if (ai[0] < (ac1[1] != 0 ? 2 : 1)) {
				char ac2[] = new char[ac.length * 2];
				System.arraycopy(ac, ai[0], ac2, ac2.length
						- (ac.length - ai[0]), ac.length - ai[0]);
				ai[0] += ac2.length - ac.length;
				ac = ac2;
				ac2 = null;
			}
			ac[--ai[0]] = ac1[0];
			if (ac1[1] != 0)
				ac[--ai[0]] = ac1[1];
		} while (!flag);
		return ac.length - ai[0];
	}

	private static int previous(UCharacterIterator ucharacteriterator,
			char ac[], int i, int j, Mode mode1, boolean flag, boolean aflag[],
			int k) {
		int ai[] = new int[1];
		int i2 = j - i;
		int l = 0;
		char ac1[] = new char[100];
		if (aflag != null)
			aflag[0] = false;
		char c = (char) mode1.getMinC();
		int j1 = mode1.getMask();
		IsPrevBoundary isprevboundary = mode1.getPrevBoundary();
		if (isprevboundary == null) {
			l = 0;
			int k1;
			if ((k1 = ucharacteriterator.previous()) >= 0) {
				l = 1;
				if (Character.isLowSurrogate((char) k1)) {
					int l1 = ucharacteriterator.previous();
					if (l1 != -1)
						if (Character.isHighSurrogate((char) l1)) {
							if (i2 >= 2) {
								ac[1] = (char) k1;
								l = 2;
							}
							k1 = l1;
						} else {
							ucharacteriterator.moveIndex(1);
						}
				}
				if (i2 > 0)
					ac[0] = (char) k1;
			}
			return l;
		}
		int i1 = findPreviousIterationBoundary(ucharacteriterator,
				isprevboundary, c, j1, ac1, ai);
		if (i1 > 0)
			if (flag) {
				l = normalize(ac1, ai[0], ai[0] + i1, ac, i, j, mode1, k);
				if (aflag != null)
					aflag[0] = l != i1
							|| Utility.arrayRegionMatches(ac1, 0, ac, i, j);
			} else if (i2 > 0)
				System.arraycopy(ac1, ai[0], ac, 0, i1 >= i2 ? i2 : i1);
		return l;
	}

	private static long getNextNorm32(UCharacterIterator ucharacteriterator,
			int i, int j, int ai[]) {
		ai[0] = ucharacteriterator.next();
		ai[1] = 0;
		if (ai[0] < i)
			return 0L;
		long l = NormalizerImpl.getNorm32((char) ai[0]);
		if (Character.isHighSurrogate((char) ai[0])) {
			if (ucharacteriterator.current() != -1
					&& Character
							.isLowSurrogate((char) (ai[1] = ucharacteriterator
									.current()))) {
				ucharacteriterator.moveIndex(1);
				if ((l & (long) j) == 0L)
					return 0L;
				else
					return NormalizerImpl.getNorm32FromSurrogatePair(l,
							(char) ai[1]);
			} else {
				return 0L;
			}
		} else {
			return l;
		}
	}

	private static int findNextIterationBoundary(
			UCharacterIterator ucharacteriterator,
			IsNextBoundary isnextboundary, int i, int j, char ac[]) {
		int ai[] = new int[2];
		int k = 0;
		if (ucharacteriterator.current() == -1)
			return 0;
		ai[0] = ucharacteriterator.next();
		ac[0] = (char) ai[0];
		k = 1;
		if (Character.isHighSurrogate((char) ai[0])
				&& ucharacteriterator.current() != -1)
			if (Character.isLowSurrogate((char) (ai[1] = ucharacteriterator
					.next())))
				ac[k++] = (char) ai[1];
			else
				ucharacteriterator.moveIndex(-1);
		do {
			if (ucharacteriterator.current() == -1)
				break;
			if (isnextboundary.isNextBoundary(ucharacteriterator, i, j, ai)) {
				ucharacteriterator.moveIndex(ai[1] != 0 ? -2 : -1);
				break;
			}
			if (k + (ai[1] != 0 ? 2 : 1) <= ac.length) {
				ac[k++] = (char) ai[0];
				if (ai[1] != 0)
					ac[k++] = (char) ai[1];
			} else {
				char ac1[] = new char[ac.length * 2];
				System.arraycopy(ac, 0, ac1, 0, k);
				ac = ac1;
				ac[k++] = (char) ai[0];
				if (ai[1] != 0)
					ac[k++] = (char) ai[1];
			}
		} while (true);
		return k;
	}

	private static int next(UCharacterIterator ucharacteriterator, char ac[],
			int i, int j, Mode mode1, boolean flag, boolean aflag[], int k) {
		char ac1[] = new char[100];
		int l1 = j - i;
		int i2 = 0;
		int ai[] = new int[1];
		if (aflag != null)
			aflag[0] = false;
		char c = (char) mode1.getMinC();
		int l = mode1.getMask();
		IsNextBoundary isnextboundary = mode1.getNextBoundary();
		if (isnextboundary == null) {
			i2 = 0;
			int j1 = ucharacteriterator.next();
			if (j1 != -1) {
				i2 = 1;
				if (Character.isHighSurrogate((char) j1)) {
					int k1 = ucharacteriterator.next();
					if (k1 != -1)
						if (Character.isLowSurrogate((char) k1)) {
							if (l1 >= 2) {
								ac[1] = (char) k1;
								i2 = 2;
							}
						} else {
							ucharacteriterator.moveIndex(-1);
						}
				}
				if (l1 > 0)
					ac[0] = (char) j1;
			}
			return i2;
		}
		int i1 = findNextIterationBoundary(ucharacteriterator, isnextboundary,
				c, l, ac1);
		if (i1 > 0)
			if (flag) {
				i2 = mode1.normalize(ac1, ai[0], i1, ac, i, j, k);
				if (aflag != null)
					aflag[0] = i2 != i1
							|| Utility
									.arrayRegionMatches(ac1, ai[0], ac, i, i2);
			} else if (l1 > 0)
				System.arraycopy(ac1, 0, ac, i, Math.min(i1, l1));
		return i2;
	}

	private void clearBuffer() {
		bufferLimit = bufferStart = bufferPos = 0;
	}

	private boolean nextNormalize() {
		clearBuffer();
		currentIndex = nextIndex;
		text.setIndex(nextIndex);
		bufferLimit = next(text, buffer, bufferStart, buffer.length, mode,
				true, null, options);
		nextIndex = text.getIndex();
		return bufferLimit > 0;
	}

	private boolean previousNormalize() {
		clearBuffer();
		nextIndex = currentIndex;
		text.setIndex(currentIndex);
		bufferLimit = previous(text, buffer, bufferStart, buffer.length, mode,
				true, null, options);
		currentIndex = text.getIndex();
		bufferPos = bufferLimit;
		return bufferLimit > 0;
	}

	private int getCodePointAt(int i) {
		if (Character.isHighSurrogate(buffer[i])) {
			if (i + 1 < bufferLimit && Character.isLowSurrogate(buffer[i + 1]))
				return Character.toCodePoint(buffer[i], buffer[i + 1]);
		} else if (Character.isLowSurrogate(buffer[i]) && i > 0
				&& Character.isHighSurrogate(buffer[i - 1]))
			return Character.toCodePoint(buffer[i - 1], buffer[i]);
		return buffer[i];
	}

	private char buffer[];

	private int bufferStart;

	private int bufferPos;

	private int bufferLimit;

	private UCharacterIterator text;

	private Mode mode;

	private int options;

	private int currentIndex;

	private int nextIndex;

	public static final int IGNORE_HANGUL = 1;

	public static final int DONE = -1;

	public static final Mode NO_OP = new Mode(1);

	public static final Mode DECOMP = new NFDMode(2);

	public static final Mode DECOMP_COMPAT = new NFKDMode(3);

	public static final Mode COMPOSE = new NFCMode(4);

	public static final Mode COMPOSE_COMPAT = new NFKCMode(5);

	public static final Mode FCD = new FCDMode(6);

	public static final QuickCheckResult NO = new QuickCheckResult(0);

	public static final QuickCheckResult YES = new QuickCheckResult(1);

	public static final QuickCheckResult MAYBE = new QuickCheckResult(2);

	private static final int MAX_BUF_SIZE_COMPOSE = 2;

	private static final int MAX_BUF_SIZE_DECOMPOSE = 3;

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 79 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/