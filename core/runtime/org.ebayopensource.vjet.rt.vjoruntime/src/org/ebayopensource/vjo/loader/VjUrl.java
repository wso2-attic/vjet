/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjo.loader;

public class VjUrl {

	private String scheme;
	private String domain;
	private String port;
	private String path;
	private String query;
	private String relativePath;
	private String resourceName;
	private String externalForm;
	public String getScheme() {
		return scheme;
	}
	public VjUrl setScheme(String scheme) {
		this.scheme = scheme;
		return this;
	}
	public String getDomain() {
		return domain;
	}
	public VjUrl setDomain(String domain) {
		this.domain = domain;
		return this;
	}
	public String getPort() {
		return port;
	}
	public VjUrl setPort(String port) {
		this.port = port;
		return this;
	}
	public String getPath() {
		return path;
	}
	public VjUrl setPath(String path) {
		this.path = path;
		return this;
	}
	public String getQuery() {
		return query;
	}
	public VjUrl setQuery(String query) {
		this.query = query;
		return this;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public VjUrl setRelativePath(String relativePath) {
		this.relativePath = relativePath;
		return this;
	}
	public String getResourceName() {
		return resourceName;
	}
	public VjUrl setResourceName(String resourceName) {
		this.resourceName = resourceName;
		return this;
	}
	public String getExternalForm() {
		return externalForm;
	}
	public VjUrl setExternalForm(String externalForm) {
		this.externalForm = externalForm;
		return this;
	}
}
