/**
 * 
 */
package org.eclipse.dltk.mod.javascript.dom.support.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.dltk.mod.internal.javascript.typeinference.IReference;
import org.eclipse.dltk.mod.internal.javascript.typeinference.UnknownReference;
import org.mozilla.mod.javascript.Scriptable;

/**
 * @author jcompagner
 * 
 */
public class UnknownReferenceScope implements Scriptable {
	private final UnknownReference ur;

	public UnknownReferenceScope(UnknownReference ur) {
		this.ur = ur;
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#delete(java.lang.String)
	 */
	public void delete(String name) {
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#delete(int)
	 */
	public void delete(int index) {
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#get(java.lang.String,
	 *      org.mozilla.mod.javascript.Scriptable)
	 */
	public Object get(String name, Scriptable start) {
		IReference child = ur.getChild(name, true);
		if (child instanceof UnknownReference) {
			return new UnknownReferenceScope((UnknownReference) child);
		}
		return null;
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#get(int,
	 *      org.mozilla.mod.javascript.Scriptable)
	 */
	public Object get(int index, Scriptable start) {
		return null;
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#getClassName()
	 */
	public String getClassName() {
		return "UnknowReferenceScope";
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#getDefaultValue(java.lang.Class)
	 */
	public Object getDefaultValue(Class hint) {
		return "UnknowReferenceScope";
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#getIds()
	 */
	public Object[] getIds() {
		Set childs = ur.getChilds(true);
		ArrayList al = new ArrayList();
		Iterator it = childs.iterator();
		while (it.hasNext()) {
			IReference ref = (IReference) it.next();
			al.add(ref.getName());
		}
		return al.toArray();
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#getParentScope()
	 */
	public Scriptable getParentScope() {
		return null;
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#getPrototype()
	 */
	public Scriptable getPrototype() {
		return null;
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#has(java.lang.String,
	 *      org.mozilla.mod.javascript.Scriptable)
	 */
	public boolean has(String name, Scriptable start) {
		return ur.getChild(name, true) != null;
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#has(int,
	 *      org.mozilla.mod.javascript.Scriptable)
	 */
	public boolean has(int index, Scriptable start) {
		return false;
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#hasInstance(org.mozilla.mod.javascript.Scriptable)
	 */
	public boolean hasInstance(Scriptable instance) {
		return false;
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#put(java.lang.String,
	 *      org.mozilla.mod.javascript.Scriptable, java.lang.Object)
	 */
	public void put(String name, Scriptable start, Object value) {
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#put(int,
	 *      org.mozilla.mod.javascript.Scriptable, java.lang.Object)
	 */
	public void put(int index, Scriptable start, Object value) {
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#setParentScope(org.mozilla.mod.javascript.Scriptable)
	 */
	public void setParentScope(Scriptable parent) {
	}

	/**
	 * @see org.mozilla.mod.javascript.Scriptable#setPrototype(org.mozilla.mod.javascript.Scriptable)
	 */
	public void setPrototype(Scriptable prototype) {
	}

	/**
	 * @return
	 */
	public UnknownReference getReference() {
		return ur;
	}
}
