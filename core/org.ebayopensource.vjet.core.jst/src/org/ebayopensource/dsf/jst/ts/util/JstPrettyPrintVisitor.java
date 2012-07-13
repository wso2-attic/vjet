/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts.util;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.JstSource;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.declaration.JstName;
import org.ebayopensource.dsf.jst.declaration.JstTypeReference;
import org.ebayopensource.dsf.jst.declaration.JstVar;
import org.ebayopensource.dsf.jst.expr.MtdInvocationExpr;
import org.ebayopensource.dsf.jst.term.JstIdentifier;
import org.ebayopensource.dsf.jst.token.IExpr;
import org.ebayopensource.dsf.jst.token.IStmt;
import org.ebayopensource.dsf.jst.traversal.IJstVisitor;
import org.ebayopensource.dsf.common.StringUtils;

/**
 * Visitor that visualizes a JST type/tree. Use IndentedPrintStream for indented
 * printout.
 */
public class JstPrettyPrintVisitor implements IJstVisitor {
	private final PrintStream m_ps;
	private final boolean m_support_indent_escape_symbols;
	private Map<IJstNode, Integer> m_ids = new IdentityHashMap<IJstNode, Integer>();
	private int m_id = 0;

	public JstPrettyPrintVisitor(PrintStream ps) {
		m_ps = ps;

		// do not introduce binary dependency on DarwinTestUtils
		m_support_indent_escape_symbols = (ps.getClass().getCanonicalName()
				.endsWith("IndentedPrintStream"));
	}

	/*
	 * each child node is also a parent's member and would have an accessor
	 * method.
	 */
	private String get_role_in_parent(IJstNode node) {
		IJstNode parent = node.getParentNode();
		if (parent != null) {
			// look through parent's accessor methods to see which one returned
			// this node
			for (Method accessor : parent.getClass().getMethods()) {
				if (accessor.getParameterTypes().length > 0
						|| !accessor.getName().startsWith("get")
						|| accessor.getName().equals("getChildren"))
					continue;
				try {
					Object result = accessor.invoke(parent);
					if (result == node) {
						return accessor.getName().substring("get".length())
								.toUpperCase();
					} else if (result instanceof List
							&& ((List) result).contains(node)) {
						return accessor.getName().substring("get".length())
								.toUpperCase()
								+ "#";
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return "CHILD";
	}

	public void preVisit(IJstNode node) {
		String role = get_role_in_parent(node) + ":";
		m_ps.print(m_support_indent_escape_symbols ? "\1" + role + "[" : role
				+ "[");
	}

	public void postVisit(IJstNode node) {
		m_ps.println(m_support_indent_escape_symbols ? "]\2" : "]");
	}

	public void endVisit(IJstNode node) {
	}

	private int get_id(IJstNode node) {
		Integer id = m_ids.get(node);
		if (id == null) {
			id = m_id++;
			m_ids.put(node, id);
		}
		return id.intValue();
	}

	public boolean visit(IJstNode node) {
		return visit(node, false);
	}

	private boolean visit(IJstNode node, boolean abbreviated) {
		if (node == null) {
			m_ps.print("{IJstNode=null}");
		} else if (node instanceof IJstType) {
			return visitJstType((IJstType) node, abbreviated);
		} else if (node instanceof IJstProperty) {
			return visitJstProperty((IJstProperty) node, abbreviated);
		} else if (node instanceof IJstMethod) {
			return visitJstMethod((IJstMethod) node, abbreviated);
		} else if (node instanceof JstBlock) {
			return visitJstBlock((JstBlock) node, abbreviated);
		} else if (node instanceof JstIdentifier) {
			return visitJstIdentifier((JstIdentifier) node, abbreviated);
		} else if (node instanceof JstArg) {
			return visitJstArg((JstArg) node, abbreviated);
		} else if (node instanceof JstName) {
			return visitJstName((JstName) node, abbreviated);
		} else if (node instanceof IExpr) {
			return visitExpr((IExpr) node, abbreviated);
		} else if (node instanceof IStmt) {
			return visitStmt((IStmt) node, abbreviated);
		} else if (node instanceof JstVar) {
			return visitJstVar((JstVar) node, abbreviated);
		} else if (node instanceof JstTypeReference) {
			m_ps.println("type: "
					+ ((JstTypeReference) node).getReferencedType().getName());
			return true;
		} else {
			m_ps.print("*** Unknown node type " + node.getClass());
		}
		return true;
	}

	private boolean printJstSource(JstSource node) {
		if (node != null) {
			m_ps.println("source info:");
			m_ps.println("start offset : " + node.getStartOffSet());
			m_ps.println("end offset : " + node.getEndOffSet());
		}
		return true;
	}

	private boolean visitJstBlock(JstBlock block, boolean abbreviated) {
		prt_JstBlock(block);
		if (abbreviated)
			return true;
		m_ps.print(m_support_indent_escape_symbols ? "\n\1" : "\n");
		if (block.getOwnerType() != null) {
			m_ps.print("defined_by=[");
			visit(block.getOwnerType(), true);
			m_ps.println("]");
		}
		prt_list("Statements", block.getStmts());
		block.getVarTable();
		if (m_support_indent_escape_symbols)
			m_ps.print('\2');
		return true;
	}

	private boolean visitJstMethod(IJstMethod node, boolean abbreviated) {
		prt_JstMethod(node);
		if (abbreviated)
			return true;
		m_ps.print(m_support_indent_escape_symbols ? "\n\1" : "\n");
		if (node.getOwnerType() != null) {
			m_ps.print("declared_by=[");
			visit(node.getRootType(), true);
			m_ps.println("]");
		}

		m_ps.print("type=[");
		prt_JstType(node.getRtnType());
		m_ps.println("]\nmodifiers=" + node.getModifiers());
		prt_list("args", node.getArgs());
		if (m_support_indent_escape_symbols)
			m_ps.print('\2');
		return true;
	}

	private boolean visitJstProperty(IJstProperty node, boolean abbreviated) {
		prt_JstProperty(node);
		if (abbreviated)
			return true;
		m_ps.print(m_support_indent_escape_symbols ? "\n\1" : "\n");
		if (node.getOwnerType() != null) {
			m_ps.print("declared_by=[");
			visit(node.getRootType(), true);
			m_ps.println("]");
		}
		if (node.getInitializer() != null) {
			m_ps.print("initializer=[");
			visit(node.getInitializer(), true);
			m_ps.println("]");
		}
		m_ps.print("type=[");
		prt_JstType(node.getType());
		m_ps.println("]\nmodifiers=" + node.getModifiers()
				+ (m_support_indent_escape_symbols ? "\2" : ""));

		return true;
	}

	private boolean visitJstType(IJstType node, boolean abbreviated) {
		prt_JstType(node);
		if (abbreviated)
			return true;
		m_ps.print(m_support_indent_escape_symbols ? "\n\1" : "\n");
		if (node.getOwnerType() != null) {
			m_ps.print("owner_type=[");
			visit(node.getOwnerType(), true);
			m_ps.println("]");
		}
		try {
			m_ps.print("ctor=[");
			IJstMethod cons = node.getConstructor();
			if (cons instanceof ISynthesized) {
				cons = null;
			}
			visitJstMethod(cons, true);
			m_ps.println("]\nmodifiers=" + node.getModifiers());
			m_ps.println(m_support_indent_escape_symbols ? "Details=[\1"
					: "Details=[");
			prt_list("Expects", node.getExpects());
			prt_list("Extends", node.getExtends());
			prt_list("Imports", node.getImports());
			prt_list("Satisfies", node.getSatisfies());
			prt_list("Mixins", node.getMixinsRef());
			// prt_list("StaticMixins", node.getStaticMixins());
			prt_list("InstanceProperties", node.getInstanceProperties());
			prt_list("StaticProperties", node.getStaticProperties());
			prt_list("InstanceMethods", node.getMethods(false));
			prt_list("StaticMethods", node.getMethods(true));
			prt_list("InstanceInitializers", node.getInstanceInitializers());
			prt_list("StaticInitializers", node.getStaticInitializers());

			m_ps.println(m_support_indent_escape_symbols ? "\2]" : "]");
		} finally {
			if (m_support_indent_escape_symbols)
				m_ps.print('\2');
		}
		return true;
	}

	private boolean visitJstName(JstName name, boolean abbreviated) {
		prt_JstName(name);
		return true;
	}

	private boolean visitStmt(IStmt stmt, boolean abbreviated) {
		prt_JstStmt(stmt);
		if (abbreviated)
			return true;
		m_ps.print('\n');
		return true;
	}

	private boolean visitExpr(IExpr expr, boolean abbreviated) {
		prt_JstExpr(expr);
		if (abbreviated)
			return true;
		m_ps.print('\n');
		if (m_support_indent_escape_symbols)
			m_ps.print('\1');
		try {
			if (expr.getResultType() != null) {
				m_ps.print("result_type=[");
				visit(expr.getResultType(), true);
				m_ps.println("]");
			}
			if (expr instanceof MtdInvocationExpr) {
				if (((MtdInvocationExpr) expr).getMethod() != null) {
					m_ps.print("declared_by=[");
					visit(((MtdInvocationExpr) expr).getMethod().getRootType(),
							true);
					m_ps.println("]");
				} else if (((MtdInvocationExpr) expr).getQualifyExpr() != null) {
					// qualify expression did not define the return type, so the
					// method declarator could not be resolved above
					m_ps.println("declared_by=[{??? text=\""
							+ sanitize(((MtdInvocationExpr) expr)
									.getQualifyExpr().toExprText()) + "\"}]");
				}
			}
		} finally {
			if (m_support_indent_escape_symbols)
				m_ps.print('\2');
		}
		return true;
	}

	private boolean visitJstIdentifier(JstIdentifier identifier,
			boolean abbreviated) {
		prt_JstIdentifier(identifier);
		if (abbreviated)
			return true;
		m_ps.print('\n');
		if (m_support_indent_escape_symbols)
			m_ps.print('\1');
		try {
			if (identifier.getJstBinding() != null) {
				m_ps.print("binding=[");
				visit(identifier.getJstBinding(), true);
				m_ps.println("]");
			}
			if (identifier.getResultType() != null) {
				m_ps.print("result_type=[");
				visit(identifier.getResultType(), true);
				m_ps.println("]");
			}
			if (identifier.getName() != null) {
				m_ps.println("name=\"" + identifier.getName() + "\"");
			}
		} finally {
			if (m_support_indent_escape_symbols)
				m_ps.print('\2');
		}
		return true;
	}

	private boolean visitJstArg(JstArg arg, boolean abbreviated) {
		prt_JstArg(arg);
		return true;
	}

	private boolean visitJstVar(JstVar var, boolean abbreviated) {
		m_ps.print("JstVar: @" + get_id(var) + " \"" + var.getName() + "\"");
		return true;
	}

	private void prt_JstProperty(IJstProperty prop) {
		m_ps.print("IJstProperty: @" + get_id(prop) + " \"" + prop.getName()
				+ "\"");
	}

	private void prt_JstType(IJstType node) {
		if (node == null) {
			m_ps.print("{IJstType=null}");
			return;
		}
		m_ps
				.print("IJstType: @" + get_id(node) + " \"" + node.getName()
						+ "\"");
	}

	private void prt_JstMethod(IJstMethod method) {
		if (method == null) {
			m_ps.print("{IJstMethod=null}");
			return;
		}
		m_ps.print("IJstMethod: @" + get_id(method) + " \"" + method.getName()
				+ "\"");
	}

	private void prt_JstArg(JstArg arg) {

		m_ps.print("JstArg: @" + get_id(arg) + " \"" + arg.getName()
				+ "\" + type: "
				+ arg.getTypeRef().getReferencedType().getName());
		printJstSource(arg.getSource());
	}

	private void prt_JstBlock(JstBlock block) {
		m_ps.print("JstBlock: @" + get_id(block));
		printJstSource(block.getSource());
	}

	private void prt_JstName(JstName name) {
		m_ps.print("JstName: @" + get_id(name) + " \"" + name.getName() + "\"");
		printJstSource(name.getSource());
	}

	private void prt_JstStmt(IStmt stmt) {
		m_ps.print("IStmt:" + stmt.getClass().getSimpleName() + ": @"
				+ get_id(stmt) + " \"" + sanitize(stmt.toStmtText()) + "\"");
		printJstSource(stmt.getSource());
	}

	private void prt_JstExpr(IExpr expr) {
		m_ps.print("IExpr:" + expr.getClass().getSimpleName() + ": @"
				+ get_id(expr) + " \"" + sanitize(expr.toExprText()) + "\"");
		printJstSource(expr.getSource());
	}

	private void prt_JstIdentifier(JstIdentifier identifier) {
		m_ps.print("JstIdentifier: @" + get_id(identifier) + " \""
				+ sanitize(identifier.toExprText()) + "\"");
		printJstSource(identifier.getSource());
	}

	private void prt_list(String name, List<? extends IJstNode> lst) {
		if (lst == null || lst.size() == 0)
			return;

		if (lst.size() == 1) {
			m_ps.print(name + "=[");
			visit(lst.get(0), true);
		} else {
			m_ps.print(name
					+ (m_support_indent_escape_symbols ? "=[\n\1" : "=[\n"));
			try {
				for (IJstNode n : lst) {
					printJstSource(n.getSource());
					visit(n, true);
					m_ps.print('\n');
				}
			} finally {
				if (m_support_indent_escape_symbols)
					m_ps.print('\2');
			}
		}
		m_ps.println("]");
	}

	/**
	 * replace newlines and multiple spaces with a single space
	 * 
	 * @param s
	 * @return
	 */
	private String sanitize(String s) {
		String[] a = s.split("\\s");
		return StringUtils.join(Arrays.asList(a), " ");
	}
}
