/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.vjet.eclipse.core.test;

import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.core.test.contentassist.TestConstants;
import org.ebayopensource.vjet.eclipse.core.test.parser.AbstractVjoModelTests;
import org.ebayopensource.vjet.testframework.fixture.FixtureManager;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;

public class FixtureUtils {
		
	private static final class FixtureCallback implements ISourceEventCallback {
		
		private static boolean isLoaded = false;
		
		public void onComplete(EventListenerStatus status) {		
			isLoaded = true;
		}

		public void waitForComplete() {
			while (!isLoaded) {
			}
		}
	
		public void onProgress(float percent) {
//			System.out.println("Percentage of completion " + percent);
		}
	}

	public static void tearDownFixture(FixtureManager m_fixtureManager) {
		if(m_fixtureManager!=null){
			m_fixtureManager.tearDown();			
		}		
	}

	public static FixtureManager setUpFixture(AbstractVjoModelTests test) {		
		TypeSpaceMgr.getInstance().setAllowChanges(false);
		FixtureManager m_fixtureManager  = new FixtureManager(test);
		m_fixtureManager.setUp(TestConstants.FIXTURE_ID_VJETPROJECT);
		TypeSpaceMgr.getInstance().reload(new FixtureCallback());
		waitForComplete();
		return m_fixtureManager;
	}
	
	public static FixtureManager setUpFixture(AbstractVjoModelTests test, String js) {		
		TypeSpaceMgr.getInstance().setAllowChanges(false);
		FixtureManager m_fixtureManager  = new FixtureManager(test);
		m_fixtureManager.setUp(TestConstants.FIXTURE_ID_VJETPROJECT);
		TypeName typeName = 
			new TypeName(TestConstants.PROJECT_NAME_VJETPROJECT, getJsName(js));
		if (TypeSpaceMgr.getInstance().existType(typeName)){
			//Do not load type space as type is already present
		} else{
			TypeSpaceMgr.getInstance().reload(new FixtureCallback()); 
			waitForComplete();
		}
		return m_fixtureManager;
	}

	private static void waitForComplete() {
		while (!TypeSpaceMgr.getInstance().isLoaded()){
			try {
				Thread.sleep(100);
			}
			catch (Exception e) {
				
			}
		}
		
	}

	private static String getJsName(String js) {
		js = js.substring(0, js.lastIndexOf(".js"));
		return js.replace("/", ".").replace("\\", ".");
	}
	
	public static FixtureManager setUpFixtureVjoLib(AbstractVjoModelTests test, String js) {		
		TypeSpaceMgr.getInstance().setAllowChanges(false);
		FixtureManager m_fixtureManager  = new FixtureManager(test);
		m_fixtureManager.setUp(TestConstants.FIXTURE_ID_VJOJAVALIB);
		TypeName typeName = 
			new TypeName(TestConstants.PROJECT_NAME_VJOJAVALIB, getJsName(js));
		if (TypeSpaceMgr.getInstance().existType(typeName)){
			//Do not load type space as type is already present
		} else{
			TypeSpaceMgr.getInstance().reload(new FixtureCallback());
			waitForComplete();
		}
		return m_fixtureManager;
	}
}