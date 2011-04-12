package vjo.java.sun.text;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   NormalizerUtilities.java
import java.lang.ArrayIndexOutOfBoundsException;

import vjo.java.lang.* ;

//Referenced classes of package sun.text:
//         Normalizer

public class NormalizerUtilities {

	public NormalizerUtilities() {
	}

	public static int toLegacyMode(Normalizer.Mode mode) {
		int i = legacyModeMap.length;
		do {
			if (i <= 0)
				break;
			i--;
		} while (legacyModeMap[i] != mode);
		return i;
	}

	public static Normalizer.Mode toNormalizerMode(int i) {
		Normalizer.Mode mode;
		try {
			mode = legacyModeMap[i];
		} catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
			mode = Normalizer.NO_OP;
		}
		return mode;
	}

	static Normalizer.Mode legacyModeMap[];

	static {
		legacyModeMap = (new Normalizer.Mode[] { Normalizer.NO_OP,
				Normalizer.DECOMP, Normalizer.DECOMP_COMPAT });
	}
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
