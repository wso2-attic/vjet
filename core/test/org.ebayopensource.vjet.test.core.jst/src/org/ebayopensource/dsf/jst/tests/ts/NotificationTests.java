/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.tests.ts;



import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ITypeSpaceEventListener;
import org.ebayopensource.dsf.ts.event.TypeSpaceEvent;
import org.ebayopensource.dsf.ts.event.TypeSpaceEvent.EventId;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;



//@ModuleInfo(value="DsfPrebuild",subModuleId="JavaToJs")
public class NotificationTests extends BaseTest {
	
	//@Category( { P1, UNIT })
	//@Description("Test notifications from event listeners")
	public void testNotification(){
		final JstTypeSpaceMgr tsMgr = new JstTypeSpaceMgr(null, null);
		tsMgr.registerTypeSpaceEventListener(createListener());
		tsMgr.initialize();
		tsMgr.processEvent(new AddTypeEvent(TYPE_B.groupName(), TYPE_B.typeName(), TYPE_B.typeName()));
		tsMgr.close();
	}
	
	private ITypeSpaceEventListener createListener(){
		return new ITypeSpaceEventListener(){
			public void onLoaded(TypeSpaceEvent event, EventListenerStatus status){
				assertEquals(EventId.Loaded, event.getId());
				System.out.println("TypeSpaceEvent:" + event.getId());
				System.out.println("Status:" + status.getCode());
			}
			public void onUpdated(TypeSpaceEvent event, EventListenerStatus status){
				assertEquals(EventId.Updated, event.getId());
				System.out.println("TypeSpaceEvent:" + event.getId());
				System.out.println("Status:" + status.getCode());
			}
			public void onUnloaded(TypeSpaceEvent event, EventListenerStatus status){
				assertEquals(EventId.Unloaded, event.getId());
				System.out.println("TypeSpaceEvent:" + event.getId());
				System.out.println("Status:" + status.getCode());
			}
		};
	}
}
