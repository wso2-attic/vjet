package vjo.sun.awt;

/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://www.kpdus.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   EventListenerAggregate.java

import java.lang.ClassNotFoundException;
import java.lang.ClassCastException;
import java.lang.NullPointerException;

import java.util.EventListener;

import vjo.lang.* ;
import vjo.lang.StringBuilder ;
import vjo.lang.System ;

import vjo.lang.reflect.Array;

public class EventListenerAggregate {

	public EventListenerAggregate(Class class1) {
		if (class1 == null)
			throw new NullPointerException("listener class is null");
		if (!EventListener.class.isAssignableFrom(class1)) {
			throw new ClassCastException((new StringBuilder()).append(
					"listener class ").append(class1).append(
					" is not assignable to EventListener").toString());
		} else {
			listenerList = (EventListener[]) (EventListener[]) Array
					.newInstance(class1, 0);
			return;
		}
	}

	private Class getListenerClass() {
		return listenerList.getClass().getComponentType();
	}

	public synchronized void add(EventListener eventlistener) {
		Class class1 = getListenerClass();
		if (!class1.isInstance(eventlistener)) {
			throw new ClassCastException((new StringBuilder()).append(
					"listener ").append(eventlistener).append(" is not ")
					.append("an instance of listener class ").append(class1)
					.toString());
		} else {
			EventListener aeventlistener[] = (EventListener[]) (EventListener[]) Array
					.newInstance(class1, listenerList.length + 1);
			System.arraycopy(listenerList, 0, aeventlistener, 0,
					listenerList.length);
			aeventlistener[listenerList.length] = eventlistener;
			listenerList = aeventlistener;
			return;
		}
	}

	public synchronized boolean remove(EventListener eventlistener) {
		Class class1 = getListenerClass();
		if (!class1.isInstance(eventlistener))
			throw new ClassCastException((new StringBuilder()).append(
					"listener ").append(eventlistener).append(" is not ")
					.append("an instance of listener class ").append(class1)
					.toString());
		for (int i = 0; i < listenerList.length; i++)
			if (listenerList[i].equals(eventlistener)) {
				EventListener aeventlistener[] = (EventListener[]) (EventListener[]) Array
						.newInstance(class1, listenerList.length - 1);
				System.arraycopy(listenerList, 0, aeventlistener, 0, i);
				System.arraycopy(listenerList, i + 1, aeventlistener, i,
						listenerList.length - i - 1);
				listenerList = aeventlistener;
				return true;
			}

		return false;
	}

	public synchronized EventListener[] getListenersInternal() {
		return listenerList;
	}

	public synchronized EventListener[] getListenersCopy() {
		return listenerList.length != 0 ? (EventListener[]) (EventListener[]) listenerList
				.clone()
				: listenerList;
	}

	public synchronized int size() {
		return listenerList.length;
	}

	public synchronized boolean isEmpty() {
		return listenerList.length == 0;
	}

	private EventListener listenerList[];
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\opt\java5-ibm-2007-12-13\jre\lib\graphics.jar
	Total time: 16 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/