/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.common.statistics;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.ebayopensource.dsf.common.context.BaseSubCtx;
import org.ebayopensource.dsf.common.context.ContextHelper;
import org.ebayopensource.dsf.common.context.DsfCtx;

public class DarwinStatisticsCtx extends BaseSubCtx {

	public static final String DARWIN_STATISTICS_SUB_CTX = "DARWIN_STATISTICS_SUB_CTX";

	private ConcurrentMap<String, DarwinStatisticsCount> m_componentStatistics;

	private ConcurrentMap<String, DarwinStatisticsCount> m_contentStatistics;

	private ConcurrentMap<String, DarwinStatisticsCount> m_javascriptStatistics;

	private ConcurrentMap<String, DarwinStatisticsCount> m_cssStatistics;

	private ConcurrentMap<String, DarwinStatisticsCount> m_linkStatistics;

	private ConcurrentMap<String, DarwinStatisticsCount> m_imageStatistics;

	private ConcurrentMap<String, DarwinStatisticsCount> m_esfTemplateStatistics;

	private ConcurrentMap<String, DarwinStatisticsCount> m_esfTemplateFailureStatistics;

	private ConcurrentMap<String, DarwinStatisticsCount> m_bizMoStatistics;
	
	private ConcurrentMap<String, DarwinStatisticsCount> m_bizOpStatistics;
	
	public static DarwinStatisticsCtx ctx() {
		DarwinStatisticsCtx context = CtxAssociator.getCtx();
		if (context == null) {
			context = new DarwinStatisticsCtx();
			setCtx(context);
		}
		return context;
	}

	public static void setCtx(final DarwinStatisticsCtx context) {
		CtxAssociator.setCtx(context);
	}

	private DarwinStatisticsCtx() {
		// empty on purpose
	}

	public static String getDARWIN_STATISTICS_SUB_CTX() {
		return DARWIN_STATISTICS_SUB_CTX;
	}
	/**
	 * @return ConcurrentMap
	 */
	public ConcurrentMap<String, DarwinStatisticsCount> getComponentStatistics() {
		if (m_componentStatistics == null) {
			m_componentStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_componentStatistics;
	}

	public DarwinStatisticsCtx setComponentStatistics(
			ConcurrentMap<String, DarwinStatisticsCount> stat) {
		m_componentStatistics = stat;
		return this;
	}

	public ConcurrentMap<String, DarwinStatisticsCount> getContentStatistics() {
		if (m_contentStatistics == null) {
			m_contentStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_contentStatistics;
	}

	public void setContentStatistics(
			ConcurrentMap<String, DarwinStatisticsCount> contentStatistics) {
		m_contentStatistics = contentStatistics;
	}

	public ConcurrentMap<String, DarwinStatisticsCount> getCssStatistics() {
		if (m_cssStatistics == null) {
			m_cssStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_cssStatistics;
	}

	public ConcurrentMap<String, DarwinStatisticsCount> getEsfTemplateStatistics() {
		if (m_esfTemplateStatistics == null) {
			m_esfTemplateStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_esfTemplateStatistics;
	}

	public ConcurrentMap<String, DarwinStatisticsCount> getEsfTemplateFailureStatistics() {
		if (m_esfTemplateFailureStatistics == null) {
			m_esfTemplateFailureStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_esfTemplateFailureStatistics;
	}

	public ConcurrentMap<String, DarwinStatisticsCount> getBizMoStatistics() {
		if (m_bizMoStatistics == null) {
			m_bizMoStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_bizMoStatistics;
	}
	
	public ConcurrentMap<String, DarwinStatisticsCount> getBizOpStatistics() {
	   if (m_bizOpStatistics == null) {
	      m_bizOpStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
	   }
	   return m_bizOpStatistics;
	}
	
	public void setCssStatistics(
			ConcurrentMap<String, DarwinStatisticsCount> cssStatistics) {
		m_cssStatistics = cssStatistics;
	}

	public ConcurrentMap<String, DarwinStatisticsCount> getJavascriptStatistics() {
		if (m_javascriptStatistics == null) {
			m_javascriptStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_javascriptStatistics;
	}

	public void setJavascriptStatistics(
			ConcurrentMap<String, DarwinStatisticsCount> javascriptStatistics) {
		m_javascriptStatistics = javascriptStatistics;
	}

	public ConcurrentMap<String, DarwinStatisticsCount> getImageStatistics() {
		if (m_imageStatistics == null) {
			m_imageStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_imageStatistics;
	}

	public void setImageStatistics(
			ConcurrentMap<String, DarwinStatisticsCount> imageStatistics) {
		m_imageStatistics = imageStatistics;
	}

	public ConcurrentMap<String, DarwinStatisticsCount> getLinkStatistics() {
		if (m_linkStatistics == null) {
			m_linkStatistics = new ConcurrentHashMap<String, DarwinStatisticsCount>();
		}
		return m_linkStatistics;
	}

	public void setLinkStatistics(
			ConcurrentMap<String, DarwinStatisticsCount> linkStatistics) {
		m_linkStatistics = linkStatistics;
	}

	public static class DarwinStatisticsCount {
		private AtomicLong m_usage = new AtomicLong(0l);

		private AtomicLong m_ref = new AtomicLong(0l);

		public long getUsage() {
			return m_usage.get();
		}

		public DarwinStatisticsCount setUsage(long usage) {
			m_usage = new AtomicLong(usage);
			return this;
		}

		public DarwinStatisticsCount incrementUsage() {
			m_usage.incrementAndGet();
			return this;
		}

		public DarwinStatisticsCount incrementUsage(long inc) {
			m_usage.addAndGet(inc);
			return this;
		}

		public long getRef() {
			return m_ref.get();
		}

		public DarwinStatisticsCount setRef(long ref) {
			m_ref = new AtomicLong(ref);
			return this;
		}

		public DarwinStatisticsCount incrementRef() {
			m_ref.incrementAndGet();
			return this;
		}

		public DarwinStatisticsCount incrementRef(long inc) {
			m_ref.addAndGet(inc);
			return this;
		}
	}

	private static class CtxAssociator extends ContextHelper {
		private static final String CTX_NAME = DarwinStatisticsCtx.class
				.getSimpleName();

		protected static DarwinStatisticsCtx getCtx() {
			return (DarwinStatisticsCtx) getSubCtx(DsfCtx.ctx(), CTX_NAME);
		}

		protected static void setCtx(final DarwinStatisticsCtx ctx) {
			setSubCtx(DsfCtx.ctx(), CTX_NAME, ctx);
		}
	}
}
