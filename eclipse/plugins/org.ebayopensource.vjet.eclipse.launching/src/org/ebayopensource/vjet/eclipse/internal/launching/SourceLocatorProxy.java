/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.internal.launching;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IPersistableSourceLocator;
import org.eclipse.debug.core.model.ISourceLocator;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.sourcelookup.IPersistableSourceLocator2;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputer;
import org.eclipse.dltk.mod.internal.debug.core.model.ScriptStackFrame;

import org.ebayopensource.vjo.tool.codecompletion.StringUtils;

public class SourceLocatorProxy implements ISourceLookupDirector {
	private ISourceLocator	m_js;
	private boolean			m_jsIsDirector;
	private boolean			m_jsIsPersistable;
	private boolean			m_jsIsPersistable2;
	private ISourceLocator	m_orig;
	private boolean			m_origIsDirector;
	private boolean			m_origIsPersistable;
	private boolean			m_origIsPersistable2;

	public SourceLocatorProxy(ISourceLocator orig, ISourceLocator delegate) {
		m_orig = orig;
		m_js = delegate;

		m_origIsDirector = m_orig instanceof ISourceLookupDirector;
		m_jsIsDirector = m_js instanceof ISourceLookupDirector;

		m_origIsPersistable2 = m_orig instanceof IPersistableSourceLocator2;
		m_jsIsPersistable2 = m_js instanceof IPersistableSourceLocator2;

		m_origIsPersistable = m_orig instanceof IPersistableSourceLocator;
		m_jsIsPersistable = m_js instanceof IPersistableSourceLocator;
	}

	@Override
	public void addParticipants(ISourceLookupParticipant[] participants) {
		if (m_origIsDirector) {
			getOriginalDirector().addParticipants(participants);
		}

		if (m_jsIsDirector) {
			getJsDirector().addParticipants(participants);
		}
	}

	@Override
	public void clearSourceElements(Object element) {
		if (m_origIsDirector) {
			getOriginalDirector().clearSourceElements(element);
		}

		if (m_jsIsDirector) {
			getJsDirector().clearSourceElements(element);
		}
	}

	@Override
	public void dispose() {
		if (m_origIsPersistable2) {
			getOriginalPersistable2().dispose();
		}
		if (m_jsIsPersistable2) {
			getJsPersistable2().dispose();
		}
	}

	@Override
	public Object[] findSourceElements(Object object) throws CoreException {
		Set<Object> elements = new HashSet<Object>(0);
		if (m_origIsDirector) {
			elements.addAll(Arrays.asList(getOriginalDirector()
					.findSourceElements(object)));
		}
		if (m_jsIsDirector) {
			elements.addAll(Arrays.asList(getJsDirector().findSourceElements(
					object)));
		}
		return elements.toArray();
	}

	@Override
	public String getId() {
		String id = null;
		if (m_origIsDirector) {
			id = getOriginalDirector().getId();
		}
		if (StringUtils.isBlankOrEmpty(id) && m_jsIsDirector) {
			id = getJsDirector().getId();
		}
		return id;
	}

	@Override
	public ILaunchConfiguration getLaunchConfiguration() {
		ILaunchConfiguration config = null;
		if (m_origIsDirector) {
			config = getOriginalDirector().getLaunchConfiguration();
		}
		if ((config == null) && m_jsIsDirector) {
			config = getJsDirector().getLaunchConfiguration();
		}
		return config;
	}

	@Override
	public String getMemento() throws CoreException {
		String memento = null;
		if (m_origIsPersistable) {
			memento = getOriginalPersistable().getMemento();
		}
		if (StringUtils.isBlankOrEmpty(memento) && m_jsIsPersistable) {
			memento = getJsPersistable().getMemento();
		}
		return memento;
	}

	@Override
	public ISourceLookupParticipant[] getParticipants() {
		Set<ISourceLookupParticipant> participants = new HashSet<ISourceLookupParticipant>(
				0);
		if (m_origIsDirector) {
			participants.addAll(Arrays.asList(getOriginalDirector()
					.getParticipants()));
		}
		if (m_jsIsDirector) {
			participants.addAll(Arrays
					.asList(getJsDirector().getParticipants()));
		}
		return participants.toArray(new ISourceLookupParticipant[participants
				.size()]);
	}

	@Override
	public ISourceContainer[] getSourceContainers() {
		Set<ISourceContainer> containers = new HashSet<ISourceContainer>(0);
		if (m_origIsDirector) {
			containers.addAll(Arrays.asList(getOriginalDirector()
					.getSourceContainers()));
		}
		if (m_jsIsDirector) {
			containers.addAll(Arrays.asList(getJsDirector()
					.getSourceContainers()));
		}
		return containers.toArray(new ISourceContainer[containers.size()]);
	}

	public Object getSourceElement(IStackFrame stackFrame) {
		if (stackFrame instanceof ScriptStackFrame) {
			return m_js.getSourceElement(stackFrame);
		}
		return m_orig.getSourceElement(stackFrame);
	}

	@Override
	public Object getSourceElement(Object element) {
		Object sourceElement = null;
		if (m_origIsDirector) {
			sourceElement = getOriginalDirector().getSourceElement(element);
		}
		if ((sourceElement == null) && m_jsIsDirector) {
			sourceElement = getJsDirector().getSourceElement(element);
		}
		return sourceElement;
	}

	@Override
	public ISourcePathComputer getSourcePathComputer() {
		ISourcePathComputer computer = null;
		if (m_origIsDirector) {
			computer = getOriginalDirector().getSourcePathComputer();
		}
		if ((computer == null) && m_jsIsDirector) {
			computer = getJsDirector().getSourcePathComputer();
		}
		return computer;
	}

	@Override
	public void initializeDefaults(ILaunchConfiguration configuration)
			throws CoreException {
		if (m_origIsPersistable) {
			getOriginalDirector().initializeDefaults(configuration);
		}
		if (m_jsIsPersistable) {
			getJsDirector().initializeDefaults(configuration);
		}
	}

	@Override
	public void initializeFromMemento(String memento) throws CoreException {
		if (m_origIsPersistable) {
			getOriginalDirector().initializeFromMemento(memento);
		}
		if (m_jsIsPersistable) {
			getJsDirector().initializeFromMemento(memento);
		}
	}

	@Override
	public void initializeFromMemento(String memento,
			ILaunchConfiguration configuration) throws CoreException {
		if (m_origIsPersistable) {
			getOriginalDirector().initializeFromMemento(memento, configuration);
		}
		if (m_jsIsPersistable) {
			getJsDirector().initializeFromMemento(memento, configuration);
		}
	}

	@Override
	public void initializeParticipants() {
		if (m_origIsDirector) {
			getOriginalDirector().initializeParticipants();
		}
		if (m_jsIsDirector) {
			getJsDirector().initializeParticipants();
		}
	}

	@Override
	public boolean isFindDuplicates() {
		boolean duplicates = false;
		if (m_origIsDirector) {
			duplicates = getOriginalDirector().isFindDuplicates();
		}
		if ((!duplicates) && m_jsIsDirector) {
			duplicates = getJsDirector().isFindDuplicates();
		}
		return duplicates;
	}

	@Override
	public void removeParticipants(ISourceLookupParticipant[] participants) {
		if (m_origIsDirector) {
			getOriginalDirector().removeParticipants(participants);
		}
		if (m_jsIsDirector) {
			getJsDirector().removeParticipants(participants);
		}
	}

	@Override
	public void setFindDuplicates(boolean findDuplicates) {
		if (m_origIsDirector) {
			getOriginalDirector().setFindDuplicates(findDuplicates);
		}
		if (m_jsIsDirector) {
			getJsDirector().setFindDuplicates(findDuplicates);
		}
	}

	@Override
	public void setSourceContainers(ISourceContainer[] containers) {
		if (m_origIsDirector) {
			getOriginalDirector().setSourceContainers(containers);
		}
		if (m_jsIsDirector) {
			getJsDirector().setSourceContainers(containers);
		}
	}

	@Override
	public void setSourcePathComputer(ISourcePathComputer computer) {
		if (m_origIsDirector) {
			getOriginalDirector().setSourceContainers(getSourceContainers());
		}
		if (m_jsIsDirector) {
			getJsDirector().setSourceContainers(getSourceContainers());
		}
	}

	@Override
	public boolean supportsSourceContainerType(ISourceContainerType type) {
		boolean support = false;
		if (m_origIsDirector) {
			support = getOriginalDirector().supportsSourceContainerType(type);
		}
		if ((!support) && m_jsIsDirector) {
			support = getJsDirector().supportsSourceContainerType(type);
		}
		return support;
	}

	private ISourceLookupDirector getJsDirector() {
		return (ISourceLookupDirector) m_js;
	}

	private IPersistableSourceLocator getJsPersistable() {
		return (IPersistableSourceLocator) m_js;
	}

	private IPersistableSourceLocator2 getJsPersistable2() {
		return (IPersistableSourceLocator2) m_js;
	}

	private ISourceLookupDirector getOriginalDirector() {
		return (ISourceLookupDirector) m_orig;
	}

	private IPersistableSourceLocator getOriginalPersistable() {
		return (IPersistableSourceLocator) m_orig;
	}

	private IPersistableSourceLocator2 getOriginalPersistable2() {
		return (IPersistableSourceLocator2) m_orig;
	}

}
