/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.mod.internal.core.hierarchy;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.codeassist.CodeassistUtils;
import org.ebayopensource.vjet.eclipse.core.ClassFileConstants;
import org.ebayopensource.vjet.eclipse.core.IVjoSourceModule;
import org.ebayopensource.vjet.eclipse.core.IVjoTypeHierarchy;
import org.ebayopensource.vjet.eclipse.core.VjetPlugin;
import org.ebayopensource.vjo.tool.typespace.SourceTypeName;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.dltk.mod.ast.Modifiers;
import org.eclipse.dltk.mod.core.DLTKCore;
import org.eclipse.dltk.mod.core.ElementChangedEvent;
import org.eclipse.dltk.mod.core.Flags;
import org.eclipse.dltk.mod.core.IElementChangedListener;
import org.eclipse.dltk.mod.core.IScriptProject;
import org.eclipse.dltk.mod.core.ISourceModule;
import org.eclipse.dltk.mod.core.IType;
import org.eclipse.dltk.mod.core.ITypeHierarchy;
import org.eclipse.dltk.mod.core.ITypeHierarchyChangedListener;
import org.eclipse.dltk.mod.core.ModelException;
import org.eclipse.dltk.mod.internal.core.JSSourceType;
import org.eclipse.dltk.mod.internal.core.JSSourceTypeElementInfo;
import org.eclipse.dltk.mod.internal.core.ModelElement;
import org.eclipse.dltk.mod.internal.core.ScriptProject;
import org.eclipse.dltk.mod.internal.core.TypeVector;
import org.eclipse.dltk.mod.internal.core.util.Messages;
import org.eclipse.dltk.mod.internal.core.util.Util;

/**
 * Vjo type hierarchy implementation based on type space
 * 
 * 
 * 
 */
public class VjoTypeHierarchy implements ITypeHierarchy,
		IElementChangedListener, IVjoTypeHierarchy {

	public static final String VJO_OBJECT = "Object";

	public static final String VJO_OBJECT_FULLNAME = "vjo.Object";

	private static final boolean DEBUG = false;

	protected static final IType[] NO_TYPE = new IType[0];

	protected ArrayList<IType> interfaces = new ArrayList<IType>(10);

	protected IType type;

	protected ISourceModule module;

	protected IProgressMonitor progressMonitor;

	private boolean computeSubtypes;

	private boolean needsRefresh = false;

	public TypeSpaceMgr typeSpaceMgr = TypeSpaceMgr.getInstance();

	public IJstType rootType;

	protected ArrayList listeners = new ArrayList();

	private boolean isNativeType = false;

	protected Map<IType, IType> classToSuperclass = new HashMap<IType, IType>();
	protected Map<IType, IType[]> typeToSuperInterfaces = new HashMap<IType, IType[]>();
	protected Map<IType, TypeVector> typeToSubtypes = new HashMap<IType, TypeVector>();
	protected TypeVector rootClasses = new TypeVector();

	/**
	 * Caches the handle of the superclass for the specified type. As a side
	 * effect cache this type as a subtype of the superclass.
	 */
	protected void cacheSuperclass(IType type, IType superclass) {
		if (superclass != null) {
			this.classToSuperclass.put(type, superclass);
			addSubtype(superclass, type);
		}
	}

	/**
	 * Caches all of the superinterfaces that are specified for the type.
	 */
	protected void cacheSuperInterfaces(IType type, IType[] superinterfaces) {
		this.typeToSuperInterfaces.put(type, superinterfaces);
		for (int i = 0; i < superinterfaces.length; i++) {
			IType superinterface = superinterfaces[i];
			addInterface(superinterface);
			if (superinterface != null) {
				addSubtype(superinterface, type);
			}
		}
	}

	/**
	 * Adds the given subtype to the type.
	 */
	protected void addSubtype(IType type, IType subtype) {
		if (subtype == null) {
			return;
		}
		TypeVector subtypes = (TypeVector) this.typeToSubtypes.get(type);
		if (subtypes == null) {
			subtypes = new TypeVector();
			this.typeToSubtypes.put(type, subtypes);
		}
		if (!subtypes.contains(subtype)) {
			subtypes.add(subtype);
		}
	}

	/**
	 * Creates a TypeHierarchy on the given type.
	 */
	public VjoTypeHierarchy(IType type) {
		this.type = type;
		this.module = type.getSourceModule();
		this.isNativeType = CodeassistUtils.isNativeObject(type);
	}

	public boolean isNativeType() {
		return isNativeType;
	}

	/**
	 * Compute this type hierarchy.
	 */
	protected void compute() throws ModelException, CoreException {
		computeSuperClasses(type);
		computeSubClasses(type);
	}

	/**
	 * Adds the type to the collection of interfaces.
	 */
	private void addInterface(IType type) {
		this.interfaces.add(type);
	}

	/**
	 * Adds the type to the collection of root classes if the classes is not
	 * already present in the collection.
	 */
	private void addRootClass(IType type) {
		if (this.rootClasses.contains(type))
			return;
		this.rootClasses.add(type);
	}

	private void computeInterfaces(IType fType) {
		TypeName name = getTypeName(fType);
		IJstType jsttype = findType(name);
		if (jsttype != null) {
			List interfaces = jsttype.getSatisfies();
			if (interfaces == null || interfaces.isEmpty())
				return;

			List<IType> superInts = toITypes(fType, interfaces);
			cacheSuperInterfaces(fType, toTypeArray(superInts));
		}
	}

	private void computeSuperClasses(IType fType) {
		TypeName name = getTypeName(fType);
		computeInterfaces(fType);

		IJstType jstType = findType(name);
		if (jstType == null)
			return;

		IJstType superType = jstType.getExtend();
		if (superType == null) {
			rootType = jstType;
			// if (hasNotObjectSuperType(superTypes)) {
			// rootType = superTypes.get(1);
			// }
			addRootClass(CodeassistUtils.findType((ScriptProject) fType
					.getScriptProject(), rootType));
		} else {
			IType superItype = CodeassistUtils.findType((ScriptProject) fType
					.getScriptProject(), superType);
			if (superItype != null) {
				cacheSuperclass(fType, superItype);
				computeSuperClasses(superItype);
			}else{
				VjetPlugin.error("Type hierarchy could created could not find dltk type for: " + superType.getName() );
			}
		}
	}

	private boolean hasNotObjectSuperType(List<IJstType> superTypes) {
		if (superTypes.size() > 1) {
			if (VJO_OBJECT.equals(rootType.getName())
					|| VJO_OBJECT_FULLNAME.equals(rootType.getName())) {

				// is our Object
				if (CodeassistUtils.isNativeType(rootType)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Compute the given type's children.
	 * 
	 * Add by Oliver for bug:5675 on 2009-10-31.
	 * 
	 * @param type
	 * @return
	 */
	private List<IType> RegistryAllSubtypesForType(IType type) {
		List<IType> subTypes = new ArrayList<IType>();
		computeAndAddSubClasses(type, subTypes);
		return subTypes;
	}

	/**
	 * Iterate all level children and add the parent / children relationship.
	 * 
	 * Add by Oliver for bug:5675 on 2009-10-31.
	 * 
	 * @param fType
	 * @param subs
	 */
	private void computeAndAddSubClasses(IType fType, List<IType> subs) {
		TypeName name = getTypeName(fType);
		List<IJstType> subTypes;
		subTypes = typeSpaceMgr.findSubTypes(name);
		IJstType currentType = typeSpaceMgr.findType(name);

		if(currentType==null){
			return;
		}
		
		if (isInterface(fType) || currentType.isInterface()) {
			// find which class implements this interface
			// FIXME backend API cannot find which interfaces extends my
			// interface
			List<IJstType> list = typeSpaceMgr.findSatisfiers(name);
			for (IJstType jstType : list) {
				subTypes.add(jstType);
			}
		}

		for (IJstType subType : subTypes) {
			if (subType != null) {
				IType isubType = CodeassistUtils.findType((ScriptProject) fType
						.getScriptProject(), subType);
				if (isubType != null) {
					addSubtype(fType, isubType);
					subs.add(isubType);
					computeAndAddSubClasses(isubType, subs);
				}
			}
		}

	}

	private void computeSubClasses(IType fType) {
		RegistryAllSubtypesForType(fType);

		// Comment by Oliver. The old codes for computing the sub classes, if
		// the new codes are
		// stable will remove following codes.

		// TypeName name = getTypeName(type);
		// List<IJstType> subTypes;
		// subTypes = typeSpaceMgr.findSubTypes(name);
		// List<IType> subITypes = toITypes(subTypes);
		// subClasses.addAll(subITypes);
		//
		// IJstType currentType = typeSpaceMgr.findType(name);
		//
		// if (isInterface(type) || currentType.isInterface()) {
		// // find which class implements this interface
		// // FIXME backend API cannot find which interfaces extends my
		// // interface
		// List<IJstType> list = typeSpaceMgr.findSatisfiers(name);
		// for (IJstType jstType : list) {
		// subITypes.add(toIType(jstType));
		// }
		// }

		// for (IType subtype : subITypes) {
		// addSubtype(fType, subtype);
		// }

	}

	private List<IType> toITypes(IType itype, List<IJstType> list) {
		List<IType> result = new ArrayList<IType>();
		for (IJstType jstType : list) {
			IType type = CodeassistUtils.findType((ScriptProject) itype
					.getScriptProject(), jstType);
			if (type != null) {
				result.add(type);
			}
		}
		return result;
	}

	/**
	 * @see ITypeHierarchy TODO (jerome) should use a PerThreadObject to build
	 *      the hierarchy instead of synchronizing (see also
	 *      isAffected(IJavaElementDelta))
	 */
	public synchronized void refresh(IProgressMonitor monitor)
			throws ModelException {
		try {
			this.progressMonitor = monitor;
			if (monitor != null) {
				if (this.type != null) {
					monitor.beginTask(Messages.bind(
							Messages.hierarchy_creatingOnType, this.type
									.getFullyQualifiedName()), 100);
				} else {
					monitor.beginTask(Messages.hierarchy_creating, 100);
				}
			}
			long start = -1;
			if (DEBUG) {
				start = System.currentTimeMillis();
				if (computeSubtypes) {
					System.out
							.println("CREATING TYPE HIERARCHY [" + Thread.currentThread() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					System.out
							.println("CREATING SUPER TYPE HIERARCHY [" + Thread.currentThread() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				if (this.type != null) {
					System.out
							.println("  on type " + ((ModelElement) this.type).toStringWithAncestors()); //$NON-NLS-1$
				}
			}

			compute();

			this.needsRefresh = false;

			if (DEBUG) {
				if (this.computeSubtypes) {
					System.out
							.println("CREATED TYPE HIERARCHY in " + (System.currentTimeMillis() - start) + "ms"); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					System.out
							.println("CREATED SUPER TYPE HIERARCHY in " + (System.currentTimeMillis() - start) + "ms"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				System.out.println(this.toString());
			}
		} catch (ModelException e) {
			throw e;
		} catch (CoreException e) {
			throw new ModelException(e);
		} finally {
			if (monitor != null) {
				monitor.done();
			}
			this.progressMonitor = null;
		}
	}

	public boolean hasFineGrainChanges() {
		return false;
	}

	// public IType[] getAllSupertypes(IType type) {
	// ArrayList supers = new ArrayList();
	// if (type != null) {
	// getAllSupertypes0(type, supers);
	// }
	// IType[] supertypes = new IType[supers.size()];
	// supers.toArray(supertypes);
	// return supertypes;
	// }
	//
	// private void getAllSupertypes0(IType type, ArrayList supers) {
	// TypeVector superTypes = new TypeVector();
	// superTypes.addAll(getSupertypes(type));
	// if (superTypes != null) {
	// IType[] superclasses = superTypes.elements();
	// if (superclasses.length != 0) {
	// addAllCheckingDuplicates(supers, superclasses);
	// for (int i = 0; i < superclasses.length; i++) {
	// getAllSupertypes0(superclasses[i], supers);
	// }
	// }
	// }
	// }

	// public IType[] getSupertypes(IType type) {
	// IType[] types= getSuperclass(type);
	// List<IType> result = new ArrayList<IType>();
	// Collections.addAll(result, types);
	//		
	//		
	// TypeName name = getTypeName(type);
	// IJstType jstType=findType(name);
	// // if(isInterface(type)){
	// // List<IJstType> is;
	// //IJstType jt = typeSpaceMgr.findType(name);
	// List atisfies = jstType.getSatisfies();
	// //jstType.getExtends()
	//		
	// // is = typeSpaceMgr.findSatisfiers(name);
	// if (atisfies != null ) {
	// for (Object object : atisfies) {
	// result.add(toIType((IJstType)object));
	// }
	//				
	// }
	// // }
	// return (IType[]) result.toArray(new IType[result.size()]);
	// }

	/**
	 * Adds all of the elements in the collection to the list if the element is
	 * not already in the list.
	 */
	private void addAllCheckingDuplicates(ArrayList list, IType[] collection) {
		for (int i = 0; i < collection.length; i++) {
			IType element = collection[i];
			if (!list.contains(element)) {
				list.add(element);
			}
		}
	}

	public void initialize(int size) {

	}

	public void store(OutputStream output, IProgressMonitor monitor)
			throws ModelException {
	}

	public boolean contains(IType type) {
		// interfaces
		// if (this.interfaces.contains(type))
		// return true;
		// classes
		// classes
		if (this.classToSuperclass.get(type) != null) {
			return true;
		}

		// root classes
		if (this.rootClasses.contains(type))
			return true;

		// interfaces
		if (this.interfaces.contains(type))
			return true;

		return false;
	}

	public void addTypeHierarchyChangedListener(
			ITypeHierarchyChangedListener listener) {

		// register with JavaCore to get Java element delta on first listener
		// added
		if (listeners.size() == 0) {
			DLTKCore.addElementChangedListener(this);
		}

		// add listener only if it is not already present
		if (listeners.indexOf(listener) == -1) {
			listeners.add(listener);
		}

	}

	public synchronized boolean exists() {
		if (!needsRefresh) {
			return true;
		}

		return (this.type == null || this.type.exists())
				&& this.javaProject().exists();
	}

	public IType[] getAllClasses() {
		// return toTypeArray(allClasses);
		TypeVector classes = this.rootClasses.copy();
		for (Iterator iter = this.classToSuperclass.keySet().iterator(); iter
				.hasNext();) {
			classes.add((IType) iter.next());
		}
		return classes.elements();
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getAllTypes() {
		IType[] classes = getAllClasses();
		int classesLength = classes.length;
		IType[] allInterfaces = getAllInterfaces();
		int interfacesLength = allInterfaces.length;
		IType[] all = new IType[classesLength + interfacesLength];
		System.arraycopy(classes, 0, all, 0, classesLength);
		System
				.arraycopy(allInterfaces, 0, all, classesLength,
						interfacesLength);
		return all;
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getAllInterfaces() {
		IType[] collection = new IType[this.interfaces.size()];
		this.interfaces.toArray(collection);
		return collection;
	}

	private IType[] toTypeArray(List<IType> a) {
		return (IType[]) a.toArray(new IType[a.size()]);
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getAllSubtypes(IType type) {
		return getAllSubtypesForType(type);
	}

	/**
	 * @see #getAllSubtypes(IType)
	 */
	private IType[] getAllSubtypesForType(IType type) {
		ArrayList subTypes = new ArrayList();
		getAllSubtypesForType0(type, subTypes);
		IType[] subClasses = new IType[subTypes.size()];
		subTypes.toArray(subClasses);
		return subClasses;
	}

	/**
	 */
	private void getAllSubtypesForType0(IType type, ArrayList subs) {
		IType[] subTypes = getSubtypesForType(type);
		if (subTypes.length != 0) {
			for (int i = 0; i < subTypes.length; i++) {
				IType subType = subTypes[i];
				subs.add(subType);
				getAllSubtypesForType0(subType, subs);
			}
		}
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getAllSuperclasses(IType type) {
		IType[] superclasses = getSuperclass(type);
		IType superclass = superclasses.length == 1 ? superclasses[0] : null;
		TypeVector supers = new TypeVector();
		while (superclass != null) {
			supers.add(superclass);
			superclasses = getSuperclass(superclass);
			superclass = superclasses.length == 1 ? superclasses[0] : null;
		}
		return supers.elements();
	}

	/**
	 * Returns an array of subtypes for the given type - will never return null.
	 */
	private IType[] getSubtypesForType(IType type) {
		TypeVector vector = (TypeVector) this.typeToSubtypes.get(type);
		if (vector == null)
			return NO_TYPE;
		else
			return vector.elements();
	}

	/**
	 * 
	 * 
	 * @see ITypeHierarchy
	 */
	public IType[] getAllSuperInterfaces(IType type) {
		ArrayList supers = new ArrayList();
		if (this.typeToSuperInterfaces.get(type) == null) {
			return NO_TYPE;
		}
		getAllSuperInterfaces0(type, supers);
		IType[] superinterfaces = new IType[supers.size()];
		supers.toArray(superinterfaces);
		return superinterfaces;
	}

	private void getAllSuperInterfaces0(IType type, ArrayList supers) {
		IType[] superinterfaces = (IType[]) this.typeToSuperInterfaces
				.get(type);
		if (superinterfaces != null && superinterfaces.length != 0) {
			addAllCheckingDuplicates(supers, superinterfaces);
			for (int i = 0; i < superinterfaces.length; i++) {
				getAllSuperInterfaces0(superinterfaces[i], supers);
			}
		}
		IType superclass = (IType) this.classToSuperclass.get(type);
		if (superclass != null) {
			getAllSuperInterfaces0(superclass, supers);
		}
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getAllSupertypes(IType type) {
		ArrayList supers = new ArrayList();
		if (this.typeToSuperInterfaces.get(type) == null
				&& this.classToSuperclass.get(type) == null) {
			return NO_TYPE;
		}
		getAllSupertypes0(type, supers);
		IType[] supertypes = new IType[supers.size()];
		supers.toArray(supertypes);
		return supertypes;
	}

	private void getAllSupertypes0(IType type, ArrayList supers) {
		IType[] superinterfaces = (IType[]) this.typeToSuperInterfaces
				.get(type);
		if (superinterfaces != null && superinterfaces.length != 0) {
			addAllCheckingDuplicates(supers, superinterfaces);
			for (int i = 0; i < superinterfaces.length; i++) {
				getAllSuperInterfaces0(superinterfaces[i], supers);
			}
		}
		IType superclass = (IType) this.classToSuperclass.get(type);
		if (superclass != null) {
			supers.add(superclass);
			getAllSupertypes0(superclass, supers);
		}
	}

	public int getCachedFlags(IType type) {
		JSSourceType sourceType = (JSSourceType) type;
		int flags = 0;
		try {
			JSSourceTypeElementInfo info;
			info = (JSSourceTypeElementInfo) sourceType.getElementInfo();
			if (info != null) {
				flags = info.getModifiers();
				if ((flags & ClassFileConstants.AccInterface) != 0) {
					flags = Modifiers.AccInterface;
				}
			}
		} catch (ModelException e) {
			DLTKCore.error(e.toString(), e);
		}
		return flags;
	}

	public IType[] getRootClasses() {
		IType[] rootClasses = new IType[] { getType() };
		if (rootType != null && type != null) {
			IType atype = CodeassistUtils.findType((ScriptProject) type
					.getScriptProject(), rootType);
			if (atype != null) {
				rootClasses = new IType[] { atype };
			}
		}
		return rootClasses;
	}

	private boolean isInterface(IType type) {
		try {
			return Flags.isInterface(type.getFlags());
		} catch (ModelException e) {
			return false;
		}
	}

	// public IType[] getSuperclass(IType type) {
	// List<IJstType> result = new ArrayList<IJstType>();
	//		
	// TypeName name = getTypeName(type);
	//			
	// IJstType jstType=findType(name);
	// if (jstType == null) {
	// return new IType[0];
	// }
	//			
	// IJstType superType = jstType.getExtend();
	// if (superType != null) {
	// result.add(superType);
	// }
	//		
	//		
	// return toTypeArray(toITypes(result));
	// }

	// public IType[] getSubclasses(IType type) {
	// List<IJstType> result = new ArrayList<IJstType>();
	// // if (type.getResource().exists()) {
	// TypeName name = getTypeName(type);
	// List<IJstType> jstType = typeSpaceMgr.findSubTypes(name);
	// result.addAll(jstType);
	// return toTypeArray(toITypes(result));
	// }

	// public IType[] getSubtypes(IType type) {
	// IType[] types = getSubclasses(type);
	// List<IType> result = new ArrayList<IType>();
	// Collections.addAll(result, types);
	//
	// TypeName name = getTypeName(type);
	//
	// if (isInterface(type)) {
	// // find which class implements this interface
	// // FIXME backend API cannot find which interfaces extends my
	// // interface
	// List<IJstType> list = typeSpaceMgr.findSatisfiers(name);
	// for (IJstType jstType : list) {
	// result.add(toIType(jstType));
	// }
	//
	// }
	//
	// return (IType[]) result.toArray(new IType[result.size()]);
	//
	// }

	private TypeName getTypeName(IType type) {
		IVjoSourceModule vjoSourceModule = (IVjoSourceModule) type
				.getSourceModule();
	




		if (vjoSourceModule != null) {
			TypeName typeName = vjoSourceModule.getTypeName();
			TypeName typeName2 = new SourceTypeName(typeName.groupName(), type.getFullyQualifiedName("."));
//			TypeName tName = vjoSourceModule.getTypeName();
			return typeName2;
		} else {
			return null;
		}
	}

	private IJstType findType(TypeName typeName) {
		if (isNativeType()) {
			return CodeassistUtils.findNativeJstType(typeName.typeName());
		} else {
			return typeSpaceMgr.findType(typeName);
		}
	}

	public IType getType() {
		return type;
	}

	public void removeTypeHierarchyChangedListener(
			ITypeHierarchyChangedListener listener) {
		// nothing

	}

	public void elementChanged(ElementChangedEvent event) {
		if (event.getType() == ElementChangedEvent.POST_CHANGE) {
			fireChanged();
		}
	}

	public void fireChanged() {
		// clone so that a listener cannot have a side-effect on this list when
		// being notified
		listeners = (ArrayList) listeners.clone();
		for (int i = 0; i < listeners.size(); i++) {
			final ITypeHierarchyChangedListener listener = (ITypeHierarchyChangedListener) listeners
					.get(i);
			SafeRunner.run(new ISafeRunnable() {
				public void handleException(Throwable exception) {
					Util
							.log(exception,
									"Exception occurred in listener of Type hierarchy change notification"); //$NON-NLS-1$
				}

				public void run() throws Exception {
					listener.typeHierarchyChanged(VjoTypeHierarchy.this);
				}
			});
		}
	}

	public IScriptProject javaProject() {
		return this.type.getScriptProject();
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getSubclasses(IType type) {
		if (this.isInterface(type)) {
			return NO_TYPE;
		}
		TypeVector vector = (TypeVector) this.typeToSubtypes.get(type);
		if (vector == null)
			return NO_TYPE;
		else
			return vector.elements();
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getSubtypes(IType type) {
		return getSubtypesForType(type);
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getSuperclass(IType type) {
		if (type == null || this.isInterface(type)) {
			return null;
		}
		return new IType[] { this.classToSuperclass.get(type) };
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getSuperInterfaces(IType type) {
		IType[] types = (IType[]) this.typeToSuperInterfaces.get(type);
		if (types == null) {
			return NO_TYPE;
		}
		return types;
	}

	/**
	 * @see ITypeHierarchy
	 */
	public IType[] getSupertypes(IType type) {
		IType superclass = getSuperclass(type).length > 0 ? getSuperclass(type)[0]
				: null;
		if (superclass == null) {
			return getSuperInterfaces(type);
		} else {
			TypeVector superTypes = new TypeVector(getSuperInterfaces(type));
			superTypes.add(superclass);
			return superTypes.elements();
		}
	}

}