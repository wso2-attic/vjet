/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/*
 * HandlerBase.java
 *
 * Modified from Open-Source CSS Parser (Steady State Software) under
 * GNU Lesser General Public License.
 * 
 */

package org.ebayopensource.dsf.css.parser;

import org.ebayopensource.dsf.css.sac.CssException;
import org.ebayopensource.dsf.css.sac.CssParseException;
import org.ebayopensource.dsf.css.sac.IErrorHandler;
import org.ebayopensource.dsf.css.sac.InputSource;
import org.ebayopensource.dsf.css.sac.ILexicalUnit;
import org.ebayopensource.dsf.css.sac.ISacMediaList;
import org.ebayopensource.dsf.css.sac.ISelectorList;
import org.ebayopensource.dsf.css.sac.IDocumentHandler;

public class HandlerBase implements IDocumentHandler, IErrorHandler {

    public void startDocument(InputSource source)
        throws CssException {
    }
    
    public void endDocument(InputSource source) throws CssException {
    }

    public void comment(String text) throws CssException {
    }

    public void ignorableAtRule(String atRule) throws CssException {
    }

    public void namespaceDeclaration(String prefix, String uri)
	    throws CssException {
    }

    public void importStyle(String uri, ISacMediaList media, 
			String defaultNamespaceURI)
	    throws CssException {
    }

    public void startMedia(ISacMediaList media) throws CssException {
    }

    public void endMedia(ISacMediaList media) throws CssException {
    }

    public void startPage(String name, String pseudo_page) throws CssException {
    }

    public void endPage(String name, String pseudo_page) throws CssException {
    }

    public void startFontFace() throws CssException {
    }

    public void endFontFace() throws CssException {
    }

    public void startSelector(ISelectorList selectors) throws CssException {
    }

    public void endSelector(ISelectorList selectors) throws CssException {
    }

    public void property(String name, ILexicalUnit value, boolean important)
        throws CssException {
    }

    public void warning(CssParseException exception) throws CssException {
        StringBuffer sb = new StringBuffer();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
    }

    public void error(CssParseException exception) throws CssException {
        StringBuffer sb = new StringBuffer();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
    }

    public void fatalError(CssParseException exception) throws CssException {
        StringBuffer sb = new StringBuffer();
        sb.append(exception.getURI())
            .append(" [")
            .append(exception.getLineNumber())
            .append(":")
            .append(exception.getColumnNumber())
            .append("] ")
            .append(exception.getMessage());
    }
}
