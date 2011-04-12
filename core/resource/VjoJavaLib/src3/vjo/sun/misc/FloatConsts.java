package vjo.java.sun.misc;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   FloatConsts.java

public class FloatConsts {

	private FloatConsts() {
	}

	public static final float POSITIVE_INFINITY = (1.0F / 0.0F);

	public static final float NEGATIVE_INFINITY = (-1.0F / 0.0F);

	public static final float NaN = (0.0F / 0.0F);

	public static final float MAX_VALUE = 3.402823E+038F;

	public static final float MIN_VALUE = 1.401298E-045F;

	public static final float MIN_NORMAL = 1.175494E-038F;

	public static final int SIGNIFICAND_WIDTH = 24;

	public static final int MAX_EXPONENT = 127;

	public static final int MIN_EXPONENT = -126;

	public static final int MIN_SUB_EXPONENT = -149;

	public static final int EXP_BIAS = 127;

	public static final int SIGN_BIT_MASK = -2147483648;

	public static final int EXP_BIT_MASK = 2139095040;

	public static final int SIGNIF_BIT_MASK = 8388607;

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 31 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
