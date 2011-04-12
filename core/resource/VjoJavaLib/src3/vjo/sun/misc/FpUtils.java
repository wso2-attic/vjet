package vjo.java.sun.misc;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   FpUtils.java


import vjo.java.lang.AssertionError;
import vjo.java.lang.Double;
import vjo.java.lang.Float;
import vjo.java.lang.Math;

public class FpUtils {

	private FpUtils() {
	}

	public static int getExponent(double d) {
		return (int) (((Double.doubleToRawLongBits(d) & 9218868437227405312L) >> 52) - 1023L);
	}

	public static int getExponent(float f) {
		return ((Float.floatToRawIntBits(f) & 2139095040) >> 23) - 127;
	}

	static double powerOfTwoD(int i) {
		if (!$assertionsDisabled && (i < -1022 || i > 1023))
			throw new AssertionError();
		else
			return Double
					.longBitsToDouble((long) i + 1023L << 52 & 9218868437227405312L);
	}

	static float powerOfTwoF(int i) {
		if (!$assertionsDisabled && (i < -126 || i > 127))
			throw new AssertionError();
		else
			return Float.intBitsToFloat(i + 127 << 23 & 2139095040);
	}

	public static double rawCopySign(double d, double d1) {
		return Double.longBitsToDouble(Double.doubleToRawLongBits(d1)
				& -9223372036854775808L | Double.doubleToRawLongBits(d)
				& 9223372036854775807L);
	}

	public static float rawCopySign(float f, float f1) {
		return Float.intBitsToFloat(Float.floatToRawIntBits(f1) & -2147483648
				| Float.floatToRawIntBits(f) & 2147483647);
	}

	public static boolean isFinite(double d) {
		return Math.abs(d) <= 1.7976931348623157E+308D;
	}

	public static boolean isFinite(float f) {
		return Math.abs(f) <= 3.402823E+038F;
	}

	public static boolean isInfinite(double d) {
		return Double.isInfinite(d);
	}

	public static boolean isInfinite(float f) {
		return Float.isInfinite(f);
	}

	public static boolean isNaN(double d) {
		return Double.isNaN(d);
	}

	public static boolean isNaN(float f) {
		return Float.isNaN(f);
	}

	public static boolean isUnordered(double d, double d1) {
		return isNaN(d) || isNaN(d1);
	}

	public static boolean isUnordered(float f, float f1) {
		return isNaN(f) || isNaN(f1);
	}

	public static int ilogb(double d) {
		int i = getExponent(d);
		switch (i) {
		case 1024:
			return !isNaN(d) ? 268435456 : 1073741824;

		case -1023:
			if (d == 0.0D)
				return -268435456;
			long l = Double.doubleToRawLongBits(d);
			l &= 4503599627370495L;
			if (!$assertionsDisabled && l == 0L)
				throw new AssertionError();
			while (l < 4503599627370496L) {
				l *= 2L;
				i--;
			}
			i++;
			if (!$assertionsDisabled && (i < -1074 || i >= -1022))
				throw new AssertionError();
			else
				return i;
		}
		if (!$assertionsDisabled && (i < -1022 || i > 1023))
			throw new AssertionError();
		else
			return i;
	}

	public static int ilogb(float f) {
		int i = getExponent(f);
		switch (i) {
		case 128:
			return !isNaN(f) ? 268435456 : 1073741824;

		case -127:
			if (f == 0.0F)
				return -268435456;
			int j = Float.floatToRawIntBits(f);
			j &= 8388607;
			if (!$assertionsDisabled && j == 0)
				throw new AssertionError();
			while (j < 8388608) {
				j *= 2;
				i--;
			}
			i++;
			if (!$assertionsDisabled && (i < -149 || i >= -126))
				throw new AssertionError();
			else
				return i;
		}
		if (!$assertionsDisabled && (i < -126 || i > 127))
			throw new AssertionError();
		else
			return i;
	}

	public static double scalb(double d, int i) {
		int j = 0;
		char c = '\0';
		double d1 = (0.0D / 0.0D);
		if (i < 0) {
			i = Math.max(i, -2099);
			c = '\uFE00';
			d1 = twoToTheDoubleScaleDown;
		} else {
			i = Math.min(i, 2099);
			c = '\u0200';
			d1 = twoToTheDoubleScaleUp;
		}
		int k = (i >> 8) >>> 23;
		j = (i + k & 511) - k;
		d *= powerOfTwoD(j);
		for (i -= j; i != 0; i -= c)
			d *= d1;

		return d;
	}

	public static float scalb(float f, int i) {
		i = Math.max(Math.min(i, 278), -278);
		return (float) ((double) f * powerOfTwoD(i));
	}

	public static double nextAfter(double d, double d1) {
		if (isNaN(d) || isNaN(d1))
			return d + d1;
		if (d == d1)
			return d1;
		long l = Double.doubleToRawLongBits(d + 0.0D);
		if (d1 > d) {
			l += l < 0L ? -1L : 1L;
		} else {
			if (!$assertionsDisabled && d1 >= d)
				throw new AssertionError();
			if (l > 0L)
				l--;
			else if (l < 0L)
				l++;
			else
				l = -9223372036854775807L;
		}
		return Double.longBitsToDouble(l);
	}

	public static float nextAfter(float f, double d) {
		if (isNaN(f) || isNaN(d))
			return f + (float) d;
		if ((double) f == d)
			return (float) d;
		int i = Float.floatToRawIntBits(f + 0.0F);
		if (d > (double) f) {
			i += i < 0 ? -1 : 1;
		} else {
			if (!$assertionsDisabled && d >= (double) f)
				throw new AssertionError();
			if (i > 0)
				i--;
			else if (i < 0)
				i++;
			else
				i = -2147483647;
		}
		return Float.intBitsToFloat(i);
	}

	public static double nextUp(double d) {
		if (isNaN(d) || d == (1.0D / 0.0D)) {
			return d;
		} else {
			d += 0.0D;
			return Double.longBitsToDouble(Double.doubleToRawLongBits(d)
					+ (d < 0.0D ? -1L : 1L));
		}
	}

	public static float nextUp(float f) {
		if (isNaN(f) || f == (1.0F / 0.0F)) {
			return f;
		} else {
			f += 0.0F;
			return Float.intBitsToFloat(Float.floatToRawIntBits(f)
					+ (f < 0.0F ? -1 : 1));
		}
	}

	public static double nextDown(double d) {
		if (isNaN(d) || d == (-1.0D / 0.0D))
			return d;
		if (d == 0.0D)
			return -4.9406564584124654E-324D;
		else
			return Double.longBitsToDouble(Double.doubleToRawLongBits(d)
					+ (d <= 0.0D ? 1L : -1L));
	}

	public static double nextDown(float f) {
		if (isNaN(f) || f == (-1.0F / 0.0F))
			return (double) f;
		if (f == 0.0F)
			return -1.4012984643248171E-045D;
		else
			return (double) Float.intBitsToFloat(Float.floatToRawIntBits(f)
					+ (f <= 0.0F ? 1 : -1));
	}

	public static double copySign(double d, double d1) {
		return rawCopySign(d, isNaN(d1) ? 1.0D : d1);
	}

	public static float copySign(float f, float f1) {
		return rawCopySign(f, isNaN(f1) ? 1.0F : f1);
	}

	public static double ulp(double d) {
		int i = getExponent(d);
		switch (i) {
		case 1024:
			return Math.abs(d);

		case -1023:
			return 4.9406564584124654E-324D;
		}
		if (!$assertionsDisabled && (i > 1023 || i < -1022))
			throw new AssertionError();
		i -= 52;
		if (i >= -1022)
			return powerOfTwoD(i);
		else
			return Double.longBitsToDouble(1L << i - -1074);
	}

	public static float ulp(float f) {
		int i = getExponent(f);
		switch (i) {
		case 128:
			return Math.abs(f);

		case -127:
			return 1.401298E-045F;
		}
		if (!$assertionsDisabled && (i > 127 || i < -126))
			throw new AssertionError();
		i -= 23;
		if (i >= -126)
			return powerOfTwoF(i);
		else
			return Float.intBitsToFloat(1 << i - -149);
	}

	public static double signum(double d) {
		return d != 0.0D && !isNaN(d) ? copySign(1.0D, d) : d;
	}

	public static float signum(float f) {
		return f != 0.0F && !isNaN(f) ? copySign(1.0F, f) : f;
	}

	static double twoToTheDoubleScaleUp = powerOfTwoD(512);

	static double twoToTheDoubleScaleDown = powerOfTwoD(-512);

//MrP - JAD casuality
//	static final boolean $assertionsDisabled = !sun / misc
//			/ FpUtils.desiredAssertionStatus();
	static final boolean $assertionsDisabled = true ;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 15 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
