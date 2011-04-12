/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Locker to synchronize data lookup and update operations.
 * Internally it has two collaborating lockers:
 * <li>Shared Lock: blocks if and only if there is update operation going on</li>
 * <li>Exclusive Lock: blocks if and only if there is any lookup or update operation going on.</li>
 * 
 * Please note: 
 * <li>Any Lock operation much be put inside a try block, and paired 
 * with a corresponding Release operation in the finally block</li>
 * <li>Any lookup operation must use lockShared() and releaseShared()</li>
 * <li>Any update operation must use lockExclusive() and releaseExclusive()</li>
 * <li>update operations, if blocked, will be queued and unblocked 
 * in the same order as lockExclusive() is invoked.</li>
 * <li>Update operation can have nested lookup operations. 
 * However, nested update operation inside lookup operation is not supported</li>
 * 
 * 
 * 
 * Delegate to Java 5 ReadWriteLock implementation 1/23/09 by Yubin
 * 
 */
public final class TypeSpaceLocker {

	
	private final ReadWriteLock m_rdwtLock;
	
	//
	// Constructor
	//
	public TypeSpaceLocker(){
		m_rdwtLock = new ReentrantReadWriteLock(false); // non fair
	}
	
	//
	// API
	//
	/**
	 * Request shared lock. It blocks unless/until the lock is granted.
	 * Must be called for each lookup operation.
	 */
	public void lockShared(){
		m_rdwtLock.readLock().lock();
	}
	
	/**
	 * Release shared lock. 
	 * Must be called after each lookup operation is complete.
	 */
	public void releaseShared(){
		m_rdwtLock.readLock().unlock();
	}
	
	/**
	 * Request exclusive lock. It blocks unless/until the lock is granted.
	 * Must be called for each update operation.
	 */
	public void lockExclusive(){
		m_rdwtLock.writeLock().lock();
	}
	
	/**
	 * Release exclusive lock. 
	 * Must be called after each update operation is complete.
	 */
	public void releaseExclusive(){
		m_rdwtLock.writeLock().unlock();
	}	
	
}
