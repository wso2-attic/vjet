/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.af.common.error;

// Java imports
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;

import org.ebayopensource.dsf.common.exceptions.BaseRuntimeException;


/**
 * Aggregates information about several individual errors.
 * Allows to filter, search for, and sort the errors.
 * <br><br>
 * In addition to the methods of ErrorMarkableList interface
 * and simple get() and size() methods,
 * ErrorList provides semiDeepClone() to enable "clean slate" filtering:
 * just clone the list and call unmarkAll() on the clone.
 * <br><br>
 * ErrorList also provides find*() methods that act as a search combined with
 * the semi-deep clone.
 * <br><br>
 * ErrorList provides one sorting method - sortBySeverity().
 * <br><br>
 * ErrorList implements custom serialization/deserialization methods in order
 * to speed up the serialization and make it independent from insignificant
 * code changes in other methods.
 *
 *  Lopatin
 * @see ErrorObject
 * @see ErrorId
 * @see ErrorSeverity
 * @see ErrorArgsInterface
*/
public class ErrorList implements Serializable {
		
	/**
	 * Empty error list
	 * @throws UnsupportedOperationException for all operations that modify the list.
	 */
	public static final ErrorList EMPTY_LIST = new EmptyErrorList();
	
	// Initial capacity is tuned for short lists
	// This is the most frequently used category of ErrorList
	private static final int INITIAL_CAPACITY = 3;
	private transient DedupErrorList m_errors;
	
	private static class DedupErrorList extends ArrayList {
		
		HashMap m_dedupMap; // HashMap used to dedup of error object in the list
		
		private DedupErrorList() {
			super();
			
			m_dedupMap = new HashMap(INITIAL_CAPACITY);
		}
		
		private DedupErrorList(int initSize) {
			super(initSize);
			
			m_dedupMap = new HashMap(initSize);
		}
		
		public boolean add(MarkableErrorObject obj) {
			
			ErrorObject key = obj.errorObject;
			
			if (m_dedupMap.get(key) != null) {
				return true;
			}
			else {
				m_dedupMap.put(key, obj);
				return super.add(obj);
			}
		}	
		
		 public void clear() {
			 m_dedupMap.clear();
			 super.clear();
		 }
	}

	//
	// Constructor(s)
	//
	public ErrorList() {
		this(INITIAL_CAPACITY);
	}
	public ErrorList(int initialSize) {
		if (initialSize < 0) {
			throw new BaseRuntimeException("Initial size must be > 0.");
		}
		m_errors = new DedupErrorList(initialSize);
	}

	/**
	 * Inner class for MarkableList implementation.
	 * It envelops the ErrorObject adding isMarked boolean field.
	 */
	private class MarkableErrorObject implements Comparable {
		ErrorObject errorObject;
		boolean isMarked;

		/**
		 * Constructor
		 * @param wrappedErrorObject error object to be wrapped
		 */
		MarkableErrorObject(ErrorObject wrappedErrorObject) {
			this.errorObject = wrappedErrorObject;
			this.isMarked = false;
		}

		/**
		 * compareTo() delegates actual comparison to the
		 * ErrorSeverity.compareTo()
		 */
		public int compareTo(Object thatMarkable) {
			return errorObject.m_severity.compareTo(((MarkableErrorObject) thatMarkable).errorObject.m_severity);
		}
	}

	// Serializable interface methods.
	// Specialized code is used in order 
	// to simplify, accelerate, and make the serialization
	// more reslilent to insignificant code changes in other methods.
	/**
	 * Manually created serialVersionUID to make the serialization more
	 * robust to changes in methods other that
	 * writeObject() and readObject().
	 * <br><br>
	 * This id <b>MUST</b> be changed if the writeObject() and/or readObject()
	 * are changed.
	 */
	static final long serialVersionUID = 1282003;

	/**
	 * Serialize ErrorList object
	 * @param out object output stream
	 * @throws IOException
	 */
	private void writeObject(java.io.ObjectOutputStream out)
		throws IOException {
		int size = m_errors.size();
		BitSet bitSet = new BitSet(size);
		out.writeInt(size);
		for (int i = 0; i < size; i++) {
			MarkableErrorObject markable = (MarkableErrorObject) m_errors.get(i);
			out.writeObject(markable.errorObject);
			if (markable.isMarked) {
				bitSet.set(i);
			} else {
				bitSet.clear(i);
			}
		}
		out.writeObject(bitSet);
	}
	/**
	 * De-serialize ErrorList object
	 * @param in object input stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(java.io.ObjectInputStream in)
		throws IOException, ClassNotFoundException {
		int size = in.readInt();
		m_errors = new DedupErrorList(size);
		MarkableErrorObject markable;
		for (int i = 0; i < size; i++) {
			ErrorObject error_object = (ErrorObject) in.readObject();
			markable = new MarkableErrorObject(error_object);
			m_errors.add(markable);
		}
		BitSet bitSet = (BitSet) in.readObject();
		for (int i = 0; i < size; i++) {
			markable = (MarkableErrorObject) m_errors.get(i);
			markable.isMarked = bitSet.get(i);
		}
	}
	
	public boolean hasAnyErrors() {
		return !isEmpty() ;
	}
	
	/**
	 * Answer true if any ErrorObject matches the errorFilter.
	 * 
	 * @param errorFilter ErrorFilter to match.  Should not be null.
	 */
	public boolean hasAnyErrors(ErrorFilter errorFilter) {
		final int size = m_errors.size();
		
		for (int i = 0; i < size; i++) {
			ErrorObject eo = 
				((MarkableErrorObject) m_errors.get(i)).errorObject;
			if (errorFilter.matches(eo)) {
				return true ;
			}
		}
		
		return false ;
	}
	
	public ErrorList getAllErrors() {
		return this ;
	}
	
	/**
	 * Answer an ErrorList containing all the ErrorObjects matching errorFilter.
	 * If no matches and empty ErrorList is returned.
	 * 
	 * @param errorFilter ErrorFilter to match.  Should not be null.
	 */
	public ErrorList getAllErrors(ErrorFilter errorFilter) {
		final int size = m_errors.size();
		final ErrorList result = new ErrorList(size/2);	
		
		for (int i = 0; i < size; i++) {
			ErrorObject eo = 
				((MarkableErrorObject) m_errors.get(i)).errorObject;
			if (errorFilter.matches(eo)) {
				result.add(eo);
			}
		}
		return result;
	}

	public void clearAllErrors() {
		// MrP ??? - might want to remember the passed in capacity if any...
		if (m_errors == null) {
			return ; // nothing to clear
		}
		m_errors.clear();
		m_errors = new DedupErrorList(INITIAL_CAPACITY);
	}

	/**
	 * Clear all ErrorObjects matching errorFilter from this instance.
	 *
	 * @param errorFilter ErrorFilter to match.  Should not be null.
	 */
	public void clearAllErrors(ErrorFilter errorFilter) {
		if (errorFilter == null || isEmpty()) {
			return ;	// nothing to clear
		}
		final int size = m_errors.size();
		// need clone so we can change original as we loop
		List findList = (List)m_errors.clone() ;
		DedupErrorList newList = new DedupErrorList();
		for (int i = 0; i < size; i++) {
			ErrorObject eo = 
				((MarkableErrorObject) findList.get(i)).errorObject;
			if (! errorFilter.matches(eo)) {
				newList.add(m_errors.get(i));
			}
		}
		m_errors.clear();
		m_errors = newList;
	}
		
	/**
	 * Cloning is semi-deep so that ErrorMarkableList methods can operate
	 * independently on the clone without changing the marked status
	 * of the original ErrorList elements.
	 * <br><br>
	 * In practical terms, the ErrorMarkableList is implemented using
	 * hidden wrappers for the ErrorObjects. The wrapper has two fields:
	 * reference to ErrorObject and boolean "marked" flag. The semiDeepClone
	 * clones the ErrorList and the wrappers
	 * yet does not clone the ErrorObjects.
	 *
	 * @return cloned list
	 */
	public ErrorList semiDeepClone() {
		ErrorList clone = new ErrorList();
		int size = m_errors.size();
		for (int i = 0; i < size; i++) {
			MarkableErrorObject markable = (MarkableErrorObject) m_errors.get(i);
			MarkableErrorObject markableClone = new MarkableErrorObject(markable.errorObject);
			markableClone.isMarked = markable.isMarked;
			clone.m_errors.add(markableClone);
		}
		return clone;
	}
	// List methods
	/**
	 * Add an error object.
	 * <br><br>
	 * Adding a duplicate amounts to "no operation".
	 *
	 * @param errorObject error object
	 */
	public void add(ErrorObject errorObject) {
		if (errorObject == null) {
			throw new NullPointerException("Error object must be non-null");
		}

		// Check for duplicates
		//for (int i = m_errors.size() - 1; i >= 0; i--) {
			//ErrorObject existingErrorObject = ((MarkableErrorObject) m_errors.get(i)).errorObject;
			//if (existingErrorObject.equals(errorObject)) {
				//return;
			//}
		//}
		
		m_errors.add(new MarkableErrorObject(errorObject));
	}
	/**
	 * Add several error objects.
	 * <br><br>
	 * Adding a duplicate amounts to "no operation".
	 *
	 * @param errorObjects array of error objects
	 */
	public void add(ErrorObject[] errorObjects) {
		for (int i = 0; i < errorObjects.length; i++) {
			add(errorObjects[i]);
		}
	}
	/**
	 * Add error objects from another ErrorList.
	 * <br><br>
	 * Adding a duplicate error object amounts to "no operation".
	 *
	 * @param errorList another ErrorList
	 */
	public void add(ErrorList errorList) {
		int size = errorList.size();
		for (int i = 0; i < size; i++) {
			add(errorList.get(i));
		}
	}
	/**
	 * How many error objects are there?
	 * @return number of error objects
	 */
	public int size() {
		return m_errors.size();
	}
	/**
	 * Is this list empty?
	 * @return <b>true</b> if the list is empty, <b>false</b> otherwise
	 */
	public boolean isEmpty() {
		return (size() == 0);
	}

	/**
	 * Returns a list iterator over the ErrorObject elements in this ErorrList.
	 * Note: the returned iterator is backedup by a new list of ErrorObjects
	 * in this ErrorList. Any modification to the list via returned list iterator is
	 * not reflected to the original ErrorList.
	 * 
	 * @return listIterator over ErrorObjects contains in this ErroList.
	 */
	public ListIterator listIterator() {
		final List list = new ArrayList(m_errors.size());
		for (int i = 0; i < m_errors.size(); i++) {
			ErrorObject eo = ((MarkableErrorObject) m_errors.get(i)).errorObject;
			list.add(eo);
		}
		return list.listIterator();
	}
	
	/**
	 * Get the error object with given index
	 * @param idex error object index
	 * @return error object
	 */
	public ErrorObject get(int index) {
		return ((MarkableErrorObject) m_errors.get(index)).errorObject;
	}
	// Finders
	// They use simple linear search as the list size
	// is expected to be small
	/**
	 * Get the sub-list of error objects that have the id equal
	 * to the given one.
	 * <br><br>
	 * The resulting list is constructed in the semiDeepClone() way.
	 * @param id error id
	 * @return list of error objects or <b>null</b> if no objects found
	 */
	public ErrorList findById(ErrorId id) {
		ErrorList result = null;
		int size = m_errors.size();
		for (int i = 0; i < size; i++) {
			ErrorObject eo = ((MarkableErrorObject) m_errors.get(i)).errorObject;
			if (eo.m_id.equals(id)) {
				if (result == null) {
					result = new ErrorList();
				}
				result.add(eo);
			}
		}
		return result;
	}
	/**
	 * Get the sub-list of error objects that have the severity equal
	 * to the given one.
	 * <br><br>
	 * The resulting list is constructed in the semiDeepClone() way.
	 * @param severity error severity
	 * @return list of error objects or <b>null</b> if no objects found
	 */
	public ErrorList findBySeverity(ErrorSeverity severity) {
		ErrorList result = null;
		int size = m_errors.size();
		for (int i = 0; i < size; i++) {
			ErrorObject eo = ((MarkableErrorObject) m_errors.get(i)).errorObject;
			if (eo.m_severity.equals(severity)) {
				if (result == null) {
					result = new ErrorList();
				}
				result.add(eo);
			}
		}
		return result;
	}
	/**
	 * Get the sub-list of error objects that have the parameters equal
	 * to the given ones.
	 * <br><br>
	 * If <code>null</code> is given for the input to this method, all of the
	 * <code>ErrorObject</code>s in this <code>ErrorList</code> which do not
	 * have parameter lists will be returned.
	 * <br><br>
	 * The resulting list is constructed in the semiDeepClone() way.
	 *
	 * @param parameters error parameters
	 * @return list of error objects or <b>null</b> if no objects found
	 */
	public ErrorList findByParameters(ErrorArgsInterface parameters) {
		ErrorList result = null;
		int size = m_errors.size();
		for (int i = 0; i < size; i++) {
			ErrorObject eo = ((MarkableErrorObject) m_errors.get(i)).errorObject;
			ErrorArgsInterface currentParameters = eo.m_parameters;
			if (((currentParameters != null) && currentParameters.equals(parameters))
					|| ((currentParameters == null) && (parameters == null))) {
				if (result == null) {
					result = new ErrorList();
				}
				result.add(eo);
			}
		}
		return result;
	}
	/**
	 * Get the sub-list of error objects that have the correlations equal
	 * to the given ones.
	 * <br><br>
	 * If <code>null</code> is given for the input to this method, all of the
	 * <code>ErrorObject</code>s in this <code>ErrorList</code> which do not
	 * have correlation lists will be returned.
	 * <br><br>
	 * The resulting list is constructed in the semiDeepClone() way.
	 *
	 * @param correlations error correlations
	 * @return list of error objects or <b>null</b> if no objects found
	 */
	public ErrorList findByCorrelations(ErrorArgsInterface correlations) {
		ErrorList result = null;
		int size = m_errors.size();
		for (int i = 0; i < size; i++) {
			ErrorObject eo = ((MarkableErrorObject) m_errors.get(i)).errorObject;
			ErrorArgsInterface currentCorrelations = eo.m_correlations;
			if (((currentCorrelations != null) && currentCorrelations.equals(correlations))
					|| ((currentCorrelations == null) && (correlations == null))) {
				if (result == null) {
					result = new ErrorList();
				}
				result.add(eo);
			}
		}
		return result;
	}
	// Sorting methods
	/**
	 * Sort the error list based on the severity of its error objects.
	 * Sorting occurs in-place. The sorting order is descending:
	 * FATALs go first, then ERRORs, then WARNINGs, then INFOs.
	 */
	public void sortBySeverity() {
		TreeSet treeSet = new TreeSet();
		int size = m_errors.size();
		int i = 0;

		// Put elements in the TreeSet
		for (i = 0; i < size; i++) {
			treeSet.add(m_errors.get(i));
		}

		// Extract elements from the TreeSet (they are sorted)
		// and insert back into the ArrayList in reverse order
		for (Iterator treeItr = treeSet.iterator(); treeItr.hasNext();) {
			m_errors.set(--i, treeItr.next());
		}
	}
	// ErrorMarkableListInterface methods
	/**
	 * Mark an error object based on its index
	 * @param index error object index
	 */
	public void mark(int index) {
		((MarkableErrorObject) m_errors.get(index)).isMarked = true;
	}
	/**
	 * Mark the first error object equal to the given one.
	 * Not finding the object results in "no operation".
	 * @param thatErrorObject given error object
	 */
	public void mark(ErrorObject thatErrorObject) {
		int index = findObject(thatErrorObject);
		if (index == -1) {
			return;
		}
		mark(index);
	}
	/**
	 * Mark all error objects in this list that are equal to any object
	 * in the given list.
	 * <br><br>
	 * Simple implementation presupposes that the lists in question
	 * are short (a dozen or less elements).
	 * The marking time is proportional to this.size() * thatList.size().
	 * <b>Please do not use this method if the lists are long!</b>
	 * @param thatErrorMarkableList given list
	 */
//	public void mark(ErrorMarkableListInterface thatErrorMarkableList) {
//		ErrorList thatErrorList = (ErrorList) thatErrorMarkableList;
//		int size = thatErrorList.size();
//		for (int i = 0; i < size; i++) {
//			int index = findObject(thatErrorList.get(i));
//			if (index != -1) {
//				mark(index);
//			}
//		}
//	}
	/**
	 * Mark all elements
	 */
	public void markAll() {
		for (int i = m_errors.size() - 1; i >= 0; i--) {
			mark(i);
		}
	}
	/**
	 * Unmark an error object based on its index
	 * @param index error object index
	 */
	public void unmark(int index) {
		((MarkableErrorObject) m_errors.get(index)).isMarked = false;
	}
	/**
	 * Unmark the first error object equal to the given one.
	 * Not finding the object results in "no operation".
	 * @param thatErrorObject error object
	 */
	public void unmark(ErrorObject thatErrorObject) {
		int index = findObject(thatErrorObject);
		if (index == -1) {
			return;
		}
		unmark(index);
	}
	/**
	 * Unmark all error objects in this list that are equal to any object
	 * in the given list.
	 * <br><br>
	 * Simple implementation presupposes that the lists in question
	 * are short (a dozen or less elements).
	 * The unmarking time is proportional to this.size() * thatList.size().
	 * <b>Please do not use this method if the lists are long!</b>
	 * @param thatErrorMarkableList given list
	 */
//	public void unmark(ErrorMarkableListInterface thatErrorMarkableList) {
//		ErrorList thatErrorList = (ErrorList) thatErrorMarkableList;
//		int size = thatErrorList.size();
//		for (int i = 0; i < size; i++) {
//			int index = findObject(thatErrorList.get(i));
//			if (index != -1) {
//				unmark(index);
//			}
//		}
//	}
	/**
	 * Unmark all elements
	 */
	public void unmarkAll() {
		for (int i = m_errors.size() - 1; i >= 0; i--) {
			unmark(i);
		}
	}
	/**
	 * Check whether an error object with given index is marked
	 * @param index error object index
	 * @return <b>true</b> if the object is marked, <b>false</b> otherwise
	 */
	public boolean isMarked(int index) {
		return ((MarkableErrorObject) m_errors.get(index)).isMarked;
	}
	/**
	 * Check whether the first error object equal to the given object
	 * is marked
	 *
	 * @param errorObject error object
	 * @return <b>true</b> if the element is marked, <b>false</b> otherwise
	 * @return <b>false</b> if the object is not found
	 */
	public boolean isMarked(ErrorObject errorObject) {
		int index = findObject(errorObject);
		if (index == -1) {
			return false;
		}
		return isMarked(index);
	}
	// Auxillary methods
	/**
	 * Return index of an error object equal to the given object.
	 * @param errorObject given error object
	 * @return index if the object is found, -1 otherwise
	 */
	private int findObject(ErrorObject errorObject) {
		if (errorObject == null) {
			throw new NullPointerException("The error object must be non-null");
		}
		int size = m_errors.size();
		for (int i = 0; i < size; i++) {
			ErrorObject element = ((MarkableErrorObject) m_errors.get(i)).errorObject;
			if (element.equals(errorObject)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Get String representation
	 * @return "{" [ErrorObject.toString()] { "," ErrorObject.toString() } "}"
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		int size = size();
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(get(i).toString());
		}
		sb.append(" }");
		return sb.toString();
	}
	
	/**
	 * Empty error list
	 * Will throw UnsupportedOperationException for all operations that modify the list.
	 */
	private static class EmptyErrorList extends ErrorList {
	
		public boolean hasErrors() {
			return false;
		}
	
		public boolean hasErrors(ErrorFilter errorFilter) {		
			return false ;
		}
	
//		public boolean hasErrors(Pattern pattern, PatternMatchErrorFilter errorFilter) {
//			return false ;
//		}
	
		public ErrorList getErrors() {
			return this ;
		}
	
//		public ErrorList getErrors(Pattern pattern, PatternMatchErrorFilter errorFilter) {
//			return this;
//		}
	
		public ErrorList getErrors(ErrorFilter errorFilter) {
			return this;
		}

		public void clearErrors() {
			// do nothing
		}

		public void clearErrors(ErrorFilter errorFilter) {
			// do nothing
		}
	
//		public void clearErrors(Pattern pattern, PatternMatchErrorFilter errorFilter) {
//			//	do nothing
//		}
		
		public ErrorList semiDeepClone() {
			return this;
		}
		public void add(ErrorObject errorObject) {
			throw new UnsupportedOperationException();
		}
	
		public void add(ErrorObject[] errorObjects) {
			throw new UnsupportedOperationException();
		}
	
		public void add(ErrorList errorList) {
			throw new UnsupportedOperationException();
		}
	
		public int size() {
			return 0;
		}

		public boolean isEmpty() {
			return true;
		}

		public ListIterator listIterator() {
			return Collections.EMPTY_LIST.listIterator();
		}
	
		public ErrorObject get(int index) {
			return null;
		}
	
		public ErrorList findById(ErrorId id) {
			return this;
		}

		public ErrorList findBySeverity(ErrorSeverity severity) {
			return this;
		}

		public ErrorList findByParameters(ErrorArgsInterface parameters) {
			return this;
		}

		public ErrorList findByCorrelations(ErrorArgsInterface correlations) {
			return this;
		}

		public void sortBySeverity() {
			// do nothing
		}

		public void mark(int index) {
			throw new UnsupportedOperationException();
		}
	
		public void mark(ErrorObject thatErrorObject) {
			throw new UnsupportedOperationException();
		}

//		public void mark(ErrorMarkableListInterface thatErrorMarkableList) {
//			throw new UnsupportedOperationException();
//		}
	
		public void markAll() {
			throw new UnsupportedOperationException();
		}
	
		public void unmark(int index) {
			throw new UnsupportedOperationException();
		}

		public void unmark(ErrorObject thatErrorObject) {
			throw new UnsupportedOperationException();
		}

//		public void unmark(ErrorMarkableListInterface thatErrorMarkableList) {
//			throw new UnsupportedOperationException();
//		}

		public void unmarkAll() {
			throw new UnsupportedOperationException();
		}
	
		public boolean isMarked(int index) {
			return false;
		}
	
		public boolean isMarked(ErrorObject errorObject) {
			return false;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{ ");
			int size = size();
			for (int i = 0; i < size; i++) {
				if (i != 0) {
					sb.append(", ");
				}
				sb.append(get(i).toString());
			}
			sb.append(" }");
			return sb.toString();
		}

		private static final long serialVersionUID = 3721746678364120215L;

	}
}