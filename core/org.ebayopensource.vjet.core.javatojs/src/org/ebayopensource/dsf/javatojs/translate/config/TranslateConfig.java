/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.javatojs.translate.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ebayopensource.dsf.javatojs.control.BuildFileFilter;
import org.ebayopensource.dsf.javatojs.control.ExpressionTypeVisitor;
import org.ebayopensource.dsf.javatojs.control.IBuildResourceFilter;
import org.ebayopensource.dsf.javatojs.report.DefaultErrorReportPolicy;
import org.ebayopensource.dsf.javatojs.report.ErrorReportPolicy;
import org.ebayopensource.dsf.javatojs.translate.TranslatorProvider;
import org.ebayopensource.dsf.javatojs.translate.custom.ICustomTranslator;
import org.ebayopensource.dsf.javatojs.translate.custom.anno.IAnnoProcessor;
import org.ebayopensource.dsf.javatojs.translate.policy.ITranslationPolicy;
import org.ebayopensource.dsf.javatojs.translate.policy.TranslationPolicy;
import org.ebayopensource.dsf.javatojs.translate.post.DirectDependencyVisitor;
import org.ebayopensource.dsf.javatojs.translate.post.FieldPostTranslationVisitor;
import org.ebayopensource.dsf.javatojs.translate.post.JavaOnlyVisitor;
import org.ebayopensource.dsf.javatojs.translate.post.MethodPostTranslationVisitor;
import org.ebayopensource.dsf.javatojs.translate.post.OptimizationVisitor;
import org.ebayopensource.dsf.javatojs.translate.post.TypePostTranslationVisitor;
import org.ebayopensource.dsf.jst.IJstLib;
import org.ebayopensource.dsf.jst.lib.IJstLibProvider;
import org.ebayopensource.dsf.jst.traversal.JstVisitorAdapter;
import org.ebayopensource.vjo.meta.VjoConvention;

public final class TranslateConfig {

	private String m_javaVersion;
	private String m_jsVersion;
	private String m_jstVersion;
	
	private ITranslationPolicy m_policy;
	private IBuildResourceFilter m_fileFilter;
	private IJstLibProvider m_jstLibProvider;
	
	private TranslatorProvider m_provider; 
	
	private List<IAnnoProcessor> m_annoProcessors = new ArrayList<IAnnoProcessor>();
	private List<ICustomTranslator> m_customTranslators = new ArrayList<ICustomTranslator>();
	private List<JstVisitorAdapter> m_postTranslationVisitors = new ArrayList<JstVisitorAdapter>();
	
	private PackageMapping m_pkgMapping;
	private JavaTranslationConvention m_javaTranslationConvention;
	private VjoConvention m_vjoConvention;
	private ErrorReportPolicy m_errorPolicy;
	private boolean m_parseComments = false;
	
	private static final Map<String,String> JAVATOJSNATIVEMAP = new HashMap<String, String>();
	
	static{
		JAVATOJSNATIVEMAP.put(String.class.getName(), org.ebayopensource.dsf.jsnative.global.String.class.getName());
		JAVATOJSNATIVEMAP.put(Date.class.getName(), org.ebayopensource.dsf.jsnative.global.Date.class.getName());
		JAVATOJSNATIVEMAP.put(Boolean.class.getName(), org.ebayopensource.dsf.jsnative.global.Boolean.class.getName());
		
	}
	
	
	//
	// Constructor
	//
	public TranslateConfig(){
		m_postTranslationVisitors.add(new TypePostTranslationVisitor());
		m_postTranslationVisitors.add(new FieldPostTranslationVisitor());
		m_postTranslationVisitors.add(new MethodPostTranslationVisitor());
		m_postTranslationVisitors.add(new JavaOnlyVisitor());
		m_postTranslationVisitors.add(new ExpressionTypeVisitor());
		m_postTranslationVisitors.add(new DirectDependencyVisitor());
		m_postTranslationVisitors.add(new OptimizationVisitor());
	}
	
	//
	// API
	//
	public String getJavaVersion() {
		return m_javaVersion;
	}
	
	public void setJavaVersion(String javaVersion) {
		m_javaVersion = javaVersion;
	}
	
	public String getJsVersion() {
		return m_jsVersion;
	}
	
	public void setJsVersion(String jsVersion) {
		m_jsVersion = jsVersion;
	}
	
	public ITranslationPolicy getPolicy() {
		if (m_policy == null) {
			m_policy = new TranslationPolicy();
		}
		return m_policy;
	}

	public TranslateConfig setPolicy(ITranslationPolicy policy) {
		m_policy = policy;
		return this;
	}

	public String getJstVersion() {
		return m_jstVersion;
	}
	
	public void setJstVersion(String jstVersion) {
		m_jstVersion = jstVersion;
	}
	
	public void setFileFilter(IBuildResourceFilter filter){
		m_fileFilter = filter;
	}
	
	public IBuildResourceFilter getFileFilter(){
		if (m_fileFilter == null){
			m_fileFilter = new BuildFileFilter(this.getPolicy());
		}
		return m_fileFilter;
	}
	
	public void setProvider(TranslatorProvider provider) {
		m_provider = provider;
	}
	
	public TranslatorProvider getProvider() {
		if (m_provider == null){
			return m_provider = new TranslatorProvider();
		}
		return m_provider;
	}
	
	public IJstLibProvider getJstLibProvider() {
		
		if(m_jstLibProvider ==null ){
			m_jstLibProvider = new IJstLibProvider(){
				public List<IJstLib> getAll(){return Collections.emptyList();};
				public IJstLibProvider add(IJstLib jstLib){return this;};
				public IJstLib remove(String lib){return null;};
				public void clearAll(){};
			};
		}
		return m_jstLibProvider;
	}
	
	public void setJstLibProvider(IJstLibProvider libProvider) {
		m_jstLibProvider = libProvider;
	}
	
	public TranslateConfig addAnnoProcessor(final IAnnoProcessor annoProcessor){
		if (annoProcessor != null && m_annoProcessors.isEmpty()){
			m_annoProcessors.add(annoProcessor);
		}
		return this;
	}
	
	public List<IAnnoProcessor> getAnnoProcessors(){
		return Collections.unmodifiableList(m_annoProcessors);
	}
	
	public TranslateConfig addCustomTranslator(final ICustomTranslator customTranslator){
		if (customTranslator != null && !m_customTranslators.contains(customTranslator)){
			m_customTranslators.add(customTranslator);
		}
		return this;
	}
	
	public List<ICustomTranslator> getCustomTranslators(){
		return Collections.unmodifiableList(m_customTranslators);
	}
	
	public TranslateConfig addPostTranslationVisitor(final JstVisitorAdapter visitor){
		if (visitor != null){
			m_postTranslationVisitors.add(visitor);
		}
		return this;
	}
	
	public List<JstVisitorAdapter> getPostTranslationVisitors(){
		return Collections.unmodifiableList(m_postTranslationVisitors);
	}

	public JavaTranslationConvention getJavaTranslationConvention() {
		if (m_javaTranslationConvention == null){
			m_javaTranslationConvention = new JavaTranslationConvention();
		}
		return m_javaTranslationConvention;
	}
	
	public void setJavaForEachTranslationConvention(JavaTranslationConvention convention) {
		m_javaTranslationConvention = convention;
	}
	
	public VjoConvention getVjoConvention() {
		if (m_vjoConvention == null){
			m_vjoConvention = new VjoConvention();
		}
		return m_vjoConvention;
	}
	public void setVjoConvention(VjoConvention vjoConvention) {
		m_vjoConvention = vjoConvention;
	}

	public PackageMapping getPackageMapping() {
		if (m_pkgMapping == null){
			m_pkgMapping = new PackageMapping();
		}
		return m_pkgMapping;
	}
	
	public void setPkgMapping(PackageMapping pkgMapping) {
		m_pkgMapping = pkgMapping;
	}
	
	public ErrorReportPolicy getErrorPolicy() {
		if (m_errorPolicy == null) {
			m_errorPolicy = new DefaultErrorReportPolicy();
		}
		return m_errorPolicy;
	}
	
	public void setErrorPolicy(ErrorReportPolicy policy) {
		m_errorPolicy = policy;
	}

	public boolean shouldParseComments() {
		return m_parseComments;
	}
	
	public void setParseComments(boolean value) {
		m_parseComments = value;
	}

	public String mapToNative(String fullName) {
		return JAVATOJSNATIVEMAP.get(fullName);
	}	
}