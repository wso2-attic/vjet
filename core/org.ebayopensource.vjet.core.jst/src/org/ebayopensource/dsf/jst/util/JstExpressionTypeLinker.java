/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
//package org.ebayopensource.dsf.jst.util;
//
//import java.util.LinkedList;
//import java.util.List;
//
//import org.ebayopensource.dsf.jst.IJstNode;
//import org.ebayopensource.dsf.jst.IJstType;
//import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
//import org.ebayopensource.dsf.jst.ts.JstQueryExecutor;
//import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
//
//import org.ebayopensource.dsf.jst.BaseJstNode;
//import org.ebayopensource.dsf.jst.IJstMethod;
//import org.ebayopensource.dsf.jst.IJstProperty;
//import org.ebayopensource.dsf.jst.IJstTypeReference;
//import org.ebayopensource.dsf.jst.declaration.JstArg;
//import org.ebayopensource.dsf.jst.declaration.JstBlock;
//import org.ebayopensource.dsf.jst.declaration.JstMethod;
//import org.ebayopensource.dsf.jst.declaration.JstType;
//import org.ebayopensource.dsf.jst.declaration.JstVar;
//import org.ebayopensource.dsf.jst.declaration.JstVars;
//import org.ebayopensource.dsf.jst.expr.AssignExpr;
//import org.ebayopensource.dsf.jst.stmt.CatchStmt;
//import org.ebayopensource.dsf.jst.stmt.ForInStmt;
//import org.ebayopensource.dsf.jst.stmt.ForStmt;
//import org.ebayopensource.dsf.jst.term.JstIdentifier;
//import org.ebayopensource.dsf.jst.token.IInitializer;
//import org.ebayopensource.dsf.jst.token.ILHS;
//import org.ebayopensource.dsf.jst.token.IStmt;
//import org.ebayopensource.dsf.jst.expr.FieldAccessExpr;
//import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
//import org.ebayopensource.dsf.jst.expr.ObjCreationExpr;
//import org.ebayopensource.dsf.jst.token.IExpr;
//import org.ebayopensource.dsf.ts.type.TypeName;
//
//@Deprecated
//class JstExpressionTypeLinker implements IJstVisitor {
//
//	private static final String THIS = "this";
//	private static final String BASE = "base";
//	private static final String VJO = "vjo";
//	private static final String STATIC_THIS = "$static(this)";
//	private static final String NS_THIS = "$ns(this)";
//	private static final String DELIMITER = ".";
//	public static final String WINDOW = "Window";
//	public static final String GLOBAL = "Global";
//	public static final String WINDOW_VAR = "window";
//
//	private final JstExpressionBindingResolver m_resolver;
//
//	JstExpressionTypeLinker(JstExpressionBindingResolver resolver) {
//		m_resolver = resolver;
//	}
//
//	class HierarchyDepth {
//
//		public static final int ALL = -1;
//
//		public static final int ONE = 1;
//
//		private int startDepth = 0;
//
//		private int finishDepth = 0;
//
//		public HierarchyDepth(int finishDepth) {
//			super();
//			this.finishDepth = finishDepth;
//		}
//
//		public HierarchyDepth(boolean inDepth) {
//			// TODO Auto-generated constructor stub
//		}
//
//		public void nextlevel() {
//			this.startDepth++;
//		}
//
//		public boolean isFinish() {
//			return this.finishDepth == this.startDepth;
//		}
//
//	}
//
//	class HierarcheQualifierSearcher {
//		public JstType searchType(IJstType type, String methodName, int inDepth, boolean isStatic) {
//			HierarchyDepth depth = new HierarchyDepth(inDepth);
//			return searchType(type, methodName, depth, isStatic);
//		}
//
//		public JstType searchType(IJstType type, String methodName,
//				HierarchyDepth depth, boolean isStatic) {
//			if (type == null || methodName == null) {
//				return null;
//			}
//			IJstType resultType = type;
//			IJstMethod jstMethod = type.getMethod(methodName, isStatic);
//
//			if (jstMethod == null && !depth.isFinish()) {
//
//				// gets extended type
//				IJstType extendType = type.getExtend();
//
//				if (extendType != null) {
//					resultType = searchType(extendType, methodName, depth, isStatic);
//					if(resultType != null)
//						return (JstType) resultType;
//				}
//
//				// gets mixed types
//				List<? extends IJstTypeReference> mixinTypes = type.getMixins();
//				for(IJstTypeReference mixinType : mixinTypes){
//					resultType = searchType(mixinType.getReferencedType(), methodName, depth, isStatic);
//					if(resultType != null)
//						return (JstType) resultType;
//				}
//
//				// gets static mixed types
////				Note: mixinProps no longer supported
////				if(isStatic) {
////					List<? extends IJstTypeReference> staticMixinTypes = type.getStaticMixins();
////					for(IJstTypeReference staticMixinType : staticMixinTypes){
////						resultType = searchType(staticMixinType.getReferencedType(), methodName, depth, isStatic);
////						if(resultType != null)
////							return (JstType) resultType;
////					}
////				}
//
//				depth.nextlevel();
//			}
//
//			return (JstType) resultType;
//		}
//
//		public JstType searchReturnType(IJstType type, String methodName) {
//
//			IJstType resultType = null;
//			IJstMethod jstMethod = type.getMethod(methodName);
//
//			if (jstMethod == null) {
//
//				// gets extended type
//				IJstType jstType = type.getExtend();
//				if (jstType != null) {
//					resultType = searchReturnType(jstType, methodName);
//					if(resultType != null)
//						return (JstType) resultType;
//				}
//
//				// gets mixed types
//				List<? extends IJstTypeReference> mixinTypes = type.getMixins();
//				for(IJstTypeReference mixinType : mixinTypes){
//					resultType = searchReturnType(mixinType.getReferencedType(), methodName);
//					if(resultType != null)
//						return (JstType) resultType;
//				}
//
//				// gets static mixed types
////				Note: mixinProps no longer supported
////				List<? extends IJstTypeReference> staticMixinTypes = type.getStaticMixins();
////				for(IJstTypeReference staticMixinType : staticMixinTypes){
////					resultType = searchReturnType(staticMixinType.getReferencedType(), methodName);
////					if(resultType != null)
////						return (JstType) resultType;
////				}
//
//			} else {
//				resultType = jstMethod.getRtnType();
//			}
//
//			return (JstType) resultType;
//		}
//
//		public JstType searchPropertyType(IJstType type, String propertyName,
//				int inDepth, boolean isStatic) {
//			if (type == null || propertyName == null) {
//				return null;
//			}
//			HierarchyDepth depth = new HierarchyDepth(inDepth);
//			return searchPropertyType(type, propertyName, depth, isStatic);
//		}
//
//		public JstType searchPropertyType(IJstType type, String propertyName,
//				HierarchyDepth depth, boolean isStatic) {
//			IJstType resultType = type;
//			IJstProperty jstProperty = type.getProperty(propertyName, isStatic);
//
//			if (jstProperty == null && !depth.isFinish()) {
//
//				// gets extended type
//				IJstType jstType = type.getExtend();
//
//				if (jstType != null) {
//					resultType = searchPropertyType(jstType, propertyName, depth, isStatic);
//					if(resultType != null)
//						return (JstType) resultType;
//				}
//
//				// gets mixed types
//				List<? extends IJstTypeReference> mixinTypes = type.getMixins();
//				for(IJstTypeReference mixinType : mixinTypes){
//					resultType = searchPropertyType(mixinType.getReferencedType(), propertyName, depth, isStatic);
//					if(resultType != null)
//						return (JstType) resultType;
//				}
//
//				// gets static mixed types
////				Note: mixinProps no longer supported
////				if(isStatic) {
////					List<? extends IJstTypeReference> staticMixinTypes = type.getStaticMixins();
////					for(IJstTypeReference staticMixinType : staticMixinTypes){
////						resultType = searchPropertyType(staticMixinType.getReferencedType(), propertyName, depth, isStatic);
////						if(resultType != null)
////							return (JstType) resultType;
////					}
////				}
//				depth.nextlevel();
//			}
//
//			return (JstType) resultType;
//		}
//
//
//		/**
//		 * @param identifier - expression to process;
//		 * @return local var declaration
//		 */
//		public IJstNode searchLocalVar(JstIdentifier identifier) {
//			final String name = identifier.getName();
//			BaseJstNode currentNode = identifier;
//			BaseJstNode parentNode = identifier.getParentNode();
//			while (!(parentNode instanceof JstMethod)) {
//				if (parentNode instanceof CatchStmt) {
//					JstVar exception = ((CatchStmt) parentNode).getException();
//					if (exception != null && name.equals(exception.getName())) {
//						return exception;
//					}
//				} else if (parentNode instanceof ForInStmt) {
//					ILHS var = ((ForInStmt) parentNode).getVar();
//					if (var instanceof JstVars && name.equals(((JstVar) var).getName())) {
//						return var;
//					}
//				} else if (parentNode instanceof ForStmt) {
//					IInitializer  initializer = ((ForStmt)parentNode).getInitializers();
//					if (isVarsContainsName(initializer, name)) {
//						return initializer;
//					}
//				}
//				while (!(parentNode instanceof JstBlock) && parentNode != null) {
//					currentNode = parentNode;
//					parentNode = parentNode.getParentNode();
//				}
//
//				if (parentNode == null) {
//					return null;
//				}
//
//				List<IStmt> statements = ((JstBlock) parentNode).getStmts();
//				for (IStmt stmt : statements) {
//					if (!stmt.equals(currentNode)) {
//						if (stmt instanceof AssignExpr) {
//							ILHS lhs = ((AssignExpr) stmt).getLHS();
//							if (lhs instanceof JstVar
//									&& name.equals(((JstVar) lhs).getName())) {
//								return lhs;
//							}
//						} else if (stmt instanceof JstVars) {
//							JstVars vars = (JstVars) stmt;
//							if (isVarsContainsName(vars, name)) {
//								return stmt;
//							}
//						}
//					} else {
//						break;
//					}
//				}
//				currentNode = parentNode;
//				parentNode = parentNode.getParentNode();
//			}
//
//			List<JstArg> args = ((JstMethod) parentNode).getArgs();
//			for (JstArg jstArg : args) {
//				if (jstArg.getName().equals(name)) {
//					return jstArg;
//				}
//			}
//
//			return null;
//		}
//
//		private boolean isVarsContainsName(IInitializer initializer, String name) {
//			for (AssignExpr assignExpr : initializer.getAssignments()) {
//				ILHS lhs = assignExpr.getLHS();
//				if (lhs instanceof JstIdentifier && name.equals(((JstIdentifier) lhs).getName())) {
//					return true;
//				}
//			}
//
//			return false;
//		}
//	}
//
//	private IJstType m_currentType;
//	HierarcheQualifierSearcher m_searcher = new HierarcheQualifierSearcher();
//
//	public IJstType getType() {
//		return m_currentType;
//	}
//
//	public void setType(IJstType currentType) {
//		this.m_currentType = currentType;
//	}
//
//	public void preVisit(IJstNode node) {
//
//	}
//
//	public void endVisit(IJstNode node) {
//
//	}
//
//	public void postVisit(IJstNode node) {
//
//	}
//
//	public boolean visit(IJstNode node) {
//		if (node instanceof MtdInvocationExpr) {
//			MtdInvocationExpr expr = (MtdInvocationExpr) node;
//			List<IExpr> qualifiers = new LinkedList<IExpr>();
//			qualifiers.add(expr);
//			getQualifiersAsList(expr, qualifiers);
//			processQualifier(qualifiers);
//			return false;
//		} else if (node instanceof FieldAccessExpr) {
//			FieldAccessExpr expr = (FieldAccessExpr) node;
//			List<IExpr> qualifiers = new LinkedList<IExpr>();
//			qualifiers.add(expr);
//			getQualifiersAsList(expr, qualifiers);
//			processQualifier(qualifiers);
//			return false;
//		} else if (node instanceof ObjCreationExpr) {
//			MtdInvocationExpr expr = ((ObjCreationExpr) node)
//					.getInvocationExpr();
//			if (expr != null) {
//				final String typeName = getName(expr);
//				IJstType resultType = null;
//				if (m_currentType.getSimpleName().equals(typeName)) {
//					resultType = m_currentType;
//				} else {
//					// search in imports
//					resultType = m_currentType.getImport(typeName);
//					if (resultType != null) {
//						setType(expr, resultType);
//						if(expr.getMethodIdentifier() instanceof JstIdentifier){
//							((JstIdentifier )expr.getMethodIdentifier()).setJstBinding(
//								resultType.getConstructor());
//						}
//					}
//				}
//			}
//			return false;
//		} else if (node instanceof JstVar || node instanceof JstVars) {
//			// skip local var declarations.
//			return false;
//		} else if (node instanceof JstIdentifier) {
//			List<IExpr> qualifiers = new LinkedList<IExpr>();
//			qualifiers.add((IExpr) node);
//			processQualifier(qualifiers);
//			return false;
//		}
//		return true;
//	}
//
//	private void getQualifiersAsList(Object qualifier, List<IExpr> list) {
//		if (qualifier == null) {
//			return;
//		}
//		IExpr expression = null;
//		if (qualifier instanceof FieldAccessExpr) {
//			expression = ((FieldAccessExpr) qualifier).getExpr();
//		} else if (qualifier instanceof MtdInvocationExpr) {
//			expression = ((MtdInvocationExpr) qualifier).getQualifyExpr();
//		} else if (qualifier instanceof JstIdentifier) {
//			expression = ((JstIdentifier) qualifier).getQualifier();
//		}
//		if (expression != null) {
//			list.add(expression);
//			getQualifiersAsList(expression, list);
//		}
//		return;
//	}
//
//	private void processQualifier(List<IExpr> qualifiers) {
//		int lastIdx = qualifiers.size() - 1;
//		IJstType nodeType = m_currentType;
//		boolean isStatic = false;
//		if (lastIdx > 0) {
//			final String lastExpr = qualifiers.get(lastIdx).toExprText();
//			if (lastExpr.equals(THIS)) {
//				if (lastIdx > 1
//						&& qualifiers.get(lastIdx - 1).toExprText().equals(
//								THIS + DELIMITER + BASE)) {
//					nodeType = resolveThis(qualifiers.get(lastIdx - 2),
//							HierarchyDepth.ONE, false);
//					lastIdx = lastIdx - 3;
//				} else {
//					nodeType = resolveThis(qualifiers.get(lastIdx - 1),
//							HierarchyDepth.ALL, false);
//					lastIdx = lastIdx - 2;
//				}
//			} else if (lastExpr.equals(VJO)) {
//				if (lastIdx > 1) {
//					final String prevExpr = qualifiers.get(lastIdx - 1)
//							.toExprText();
//					if (prevExpr.equals(VJO + DELIMITER + STATIC_THIS)) {
//						isStatic = true;
//						nodeType = resolveThis(qualifiers.get(lastIdx - 2),
//								HierarchyDepth.ONE, true);
//						lastIdx = lastIdx - 3;
//					} else if (prevExpr.equals(VJO + DELIMITER + NS_THIS)) {
//						// next segment have to be type name
//						if (lastIdx > 2
//								&& qualifiers.get(lastIdx - 2) instanceof FieldAccessExpr) {
//							FieldAccessExpr expr = (FieldAccessExpr) qualifiers
//									.get(lastIdx - 2);
//							final String typeName = getName(expr);
//							nodeType = m_currentType.getImport(typeName);
//							expr.getName().setJstBinding(nodeType);
//							isStatic = true;
//							lastIdx = lastIdx - 3;
//						} else {
//							System.err.println("Type name should follow after "
//									+ VJO + "." + STATIC_THIS);
//							return;
//						}
//					} else {
//						System.err.println("Unrecognized statement \""
//								+ prevExpr + "\" after vjo.");
//						return;
//					}
//				}
//			} else if (isNativeElement(getName(qualifiers, lastIdx))) {
//				nodeType = getNativeElementType(getName(qualifiers, lastIdx));
//			}
//		}
//		for (int idx = lastIdx; idx >= 0; idx--) {
//			IExpr segment = qualifiers.get(idx);
//			if (segment instanceof JstIdentifier) {
//				JstIdentifier identifier = (JstIdentifier) segment;
//				IJstNode var = m_searcher.searchLocalVar(identifier);
//				if (var != null) {
//					identifier.setJstBinding(var);
//					nodeType = getVarType(var);
//					setType(identifier, nodeType);
//				} else {
//					 nodeType = setNativeProperty(nodeType, isStatic, identifier);
//				}
//
//			} else if (segment instanceof FieldAccessExpr) {
//				FieldAccessExpr fieldAccessExpr = (FieldAccessExpr) segment;
//				String fieldName = getName(segment);
//
//				IJstProperty pty = (nodeType == null) ? null : nodeType.getProperty(fieldName);
//
//				if (pty != null) {
//					nodeType = pty.getType();
//					fieldAccessExpr.getName().setJstBinding(pty);
//					setType(fieldAccessExpr, nodeType);
//					isStatic = false;
//				}
//				else {
//					JstType declaringType = m_searcher.searchPropertyType(nodeType,
//							fieldName, HierarchyDepth.ALL, isStatic);
//					if (declaringType != null) {
//						IJstProperty property = declaringType.getProperty(
//								fieldName, isStatic);
//						isStatic = false;
//						if (property != null) {
//							nodeType = property.getType();
//							fieldAccessExpr.getName().setJstBinding(property);
//							setType(fieldAccessExpr, nodeType);
//						} else {
//							System.err.println("Cant find " + fieldName
//									+ " field in " + declaringType.getName()
//									+ " type. with source = " + segment.getSource().toString());
//						}
//					}
//				}
//			} else if (segment instanceof MtdInvocationExpr) {
//				MtdInvocationExpr mtdInvocationExpr = (MtdInvocationExpr) segment;
//				String methodName = getName(segment);
//
//				IJstMethod mtd = (nodeType == null) ? null : nodeType.getMethod(methodName);
//
//				if (mtd != null) {
//					nodeType = mtd.getRtnType();
//					if(mtdInvocationExpr.getMethodIdentifier() instanceof JstIdentifier){
//						((JstIdentifier)mtdInvocationExpr.getMethodIdentifier()).setJstBinding(mtd);
//					}
//					resolveMethodArgs(mtdInvocationExpr);
//					setType(mtdInvocationExpr, nodeType);
//					isStatic = false;
//				}
//				else {
//					JstType declaringType = m_searcher.searchType(nodeType,
//							methodName, HierarchyDepth.ALL, isStatic);
//					if (declaringType != null) {
//						IJstMethod method = declaringType.getMethod(methodName,
//								isStatic);
//						isStatic = false;
//						if (method != null) {
//							nodeType = method.getRtnType();
//							if(mtdInvocationExpr.getMethodIdentifier() instanceof JstIdentifier){
//								((JstIdentifier)mtdInvocationExpr.getMethodIdentifier()).setJstBinding(method);
//							}
//							resolveMethodArgs(mtdInvocationExpr);
//						} else {
//							System.err.println("Cant find " + methodName
//									+ " method in " + declaringType.getName()
//									+ " type.");
//						}
//						setType(mtdInvocationExpr, nodeType);
//					}
//				}
//			}
//		}
//	}
//
//	private void setType(IExpr expr, IJstType type) {
//
//		if (expr instanceof JstIdentifier) {
//			JstIdentifier identifier = (JstIdentifier) expr;
//			identifier.setType(type);
//		} else if (expr instanceof FieldAccessExpr) {
//			FieldAccessExpr identifier = (FieldAccessExpr) expr;
//			identifier.setType((JstType) type);
//		} else if (expr instanceof MtdInvocationExpr) {
//			MtdInvocationExpr mtdExpr = (MtdInvocationExpr) expr;
//			mtdExpr.setResultType(type);
//		} else {
//			System.err.println("Unprocessed type : " + expr);
//		}
//
//	}
//
//	private String getName(IExpr expr) {
//		String s = expr.toString();
//
//		if (expr instanceof MtdInvocationExpr) {
//			MtdInvocationExpr expr2 = (MtdInvocationExpr) expr;
//			s = expr2.getMethodIdentifier().toExprText();
//		} else if (expr instanceof FieldAccessExpr) {
//			s = ((FieldAccessExpr) expr).getName().getName();
//		}
//
//		return s;
//	}
//
//	private String getName(List<IExpr> qualifiers, int lastIdx) {
//		IExpr expr = qualifiers.get(lastIdx);
//		String name = expr.toExprText();
//
//		if (expr instanceof MtdInvocationExpr) {
//			MtdInvocationExpr mtd = (MtdInvocationExpr) expr;
//			name = mtd.getMethodIdentifier().toExprText();
//		}
//
//		if (expr instanceof FieldAccessExpr) {
//			FieldAccessExpr field = (FieldAccessExpr) expr;
//			name = field.getName().getName();
//		}
//
//		return name;
//	}
//
//	private void resolveMethodArgs(MtdInvocationExpr mtdInvocationExpr) {
//		if (mtdInvocationExpr == null) {
//			return;
//		}
//		List<IExpr> args = mtdInvocationExpr.getArgs();
//		for (IExpr arg : args) {
//			visit(arg);
//		}
//	}
//
//	private IJstType resolveThis(MtdInvocationExpr expr, int inDepth,
//			boolean isStatic) {
//		String methodName = expr.getMethodIdentifier().toExprText();
//		JstType declaringType = m_searcher.searchType(m_currentType, methodName,
//				inDepth, isStatic);
//		IJstMethod method = declaringType.getMethod(methodName, isStatic);
//		if(expr.getMethodIdentifier() instanceof JstIdentifier){
//			((JstIdentifier)expr.getMethodIdentifier()).setJstBinding(method);
//		}
//		if (method != null) {
//			setType(expr, method.getRtnType());
//			return method.getRtnType();
//		}
//		return null;
//	}
//
//	private IJstType resolveThis(FieldAccessExpr expr, int inDepth,
//			boolean isStatic) {
//		String fieldName = expr.getName().getName();
//		JstType declaringType = m_searcher.searchPropertyType(m_currentType,
//				fieldName, inDepth, isStatic);
//		IJstProperty property = declaringType.getProperty(fieldName, isStatic);
//		if (property != null) {
//			expr.getName().setJstBinding(property);
//			setType(expr, property.getType());
//			return property.getType();
//		} else {
//
//			System.err.println("Cant find " + fieldName + " field in "
//					+ declaringType.getName() + " type. with source = " + expr.getSource().toString());
//		}
//
//		return null;
//	}
//
//	private IJstType resolveThis(IExpr expr, int inDepth, boolean isStatic) {
//		if (expr instanceof FieldAccessExpr) {
//			return resolveThis((FieldAccessExpr) expr, inDepth, isStatic);
//		} else if (expr instanceof MtdInvocationExpr) {
//			return resolveThis((MtdInvocationExpr) expr, inDepth, isStatic);
//		}
//		return null;
//	}
//
//	private IJstType getNativeTypeFromTS(String name) {
//		TypeName typeName = new TypeName(JstTypeSpaceMgr.JS_NATIVE_GRP, name);
//
//		JstTypeSpaceMgr tsMgr = m_resolver.getController().getJstTypeSpaceMgr();
//		JstQueryExecutor queryExecutor = tsMgr.getQueryExecutor();
//
//		return queryExecutor.findType(typeName);
//	}
//
//	private IJstType getNativeElementType(String lastExpr) {
//
//		// by default
//		IJstType type = null;
//
//		if (WINDOW_VAR.endsWith(lastExpr)) {
//
//			type = getNativeTypeFromTS(WINDOW);
//
//		} else {
//
//			type = getNativeElementType(lastExpr, WINDOW);
//
//			if (type == null) {
//				type = getNativeElementType(lastExpr, GLOBAL);
//			}
//		}
//
//		return type;
//	}
//
//	private IJstType getNativeElementType(String lastExpr, String name) {
//
//		IJstType jstType = null;
//
//		IJstType type = getNativeTypeFromTS(name);
//
//		IJstMethod jstMethod = type.getMethod(lastExpr);
//
//		if (jstMethod != null) {
//
//			jstType = type;
//
//		} else {
//
//			IJstProperty property = type.getProperty(lastExpr);
//			if (property != null) {
//
//				jstType = type;
//			}
//		}
//
//		return jstType;
//	}
//
//	private IJstType setNativeProperty(IJstType type, boolean isStatic,
//			JstIdentifier prop) {
//
//		String name = prop.getName();
//		IJstProperty property = type.getProperty(name, isStatic);
//		if (property != null) {
//			prop.setJstBinding(property);
//			type = property.getType();
//			setType(prop, property.getType());
//		}
//
//		return type;
//	}
//
//	public boolean isNativeElement(String lastExpr) {
//
//		boolean isNative = false;
//		isNative = WINDOW_VAR.endsWith(lastExpr);
//
//		if (!isNative) {
//			isNative = isNativeExpression(lastExpr, WINDOW);
//			if (!isNative) {
//				isNative = isNativeExpression(lastExpr, GLOBAL);
//			}
//		}
//
//		return isNative;
//	}
//
//	public boolean isNativeExpression(String lastExpr, String name) {
//		boolean isNative = false;
//		IJstType type = getNativeTypeFromTS(name);
//		if (type != null) {
//			isNative = type.getMethod(lastExpr) != null
//					|| type.getProperty(lastExpr) != null;
//		}
//		return isNative;
//	}
//
//	private IJstType getVarType(IJstNode var) {
//		if (var instanceof JstVar) {
//			return ((JstVar) var).getType();
//		} else if (var instanceof JstVars) {
//			return ((JstVars) var).getType();
//		} else if (var instanceof JstArg) {
//			return ((JstArg) var).getType();
//		}
//		return null;
//	}
//
//
//
//}
