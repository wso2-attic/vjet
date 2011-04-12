package vjo.java.sun.misc;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   LRUCache.java

import vjo.java.lang.* ;

public abstract class LRUCache<N, V> {

	public LRUCache(int i) {
		oa = null;
		size = i;
	}

	protected abstract V create(N obj);

	protected abstract boolean hasName(V obj, N obj1);

	public static void moveToFront(Object aobj[], int i) {
		Object obj = aobj[i];
		for (int j = i; j > 0; j--)
			aobj[j] = aobj[j - 1];

		aobj[0] = obj;
	}

	public V forName(N obj) {
		if (oa == null) {
			oa = (V[]) new Object[size];
		} else {
			for (int i = 0; i < oa.length; i++) {
				V obj2 = oa[i];
				if (obj2 != null && hasName(obj2, obj)) {
					if (i > 0)
						moveToFront(oa, i);
					return obj2;
				}
			}

		}
		V obj1 = create(obj);
		oa[oa.length - 1] = obj1;
		moveToFront(oa, oa.length - 1);
		return obj1;
	}

	private V oa[];

	private final int size;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\core.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/
