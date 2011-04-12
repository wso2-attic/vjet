/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jst.ts;

import java.util.ArrayList;
import java.util.List;


import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstType;
//import org.ebayopensource.dsf.jstojava.resolver.TypeResolverRegistry;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ISourceEvent;
import org.ebayopensource.dsf.ts.event.ISourceEventCallback;
import org.ebayopensource.dsf.ts.event.TypeSpaceEvent;
import org.ebayopensource.dsf.ts.event.TypeSpaceEvent.EventId;
import org.ebayopensource.dsf.ts.event.dispatch.IEventListenerHandle;
import org.ebayopensource.dsf.ts.event.group.AddGroupDependencyEvent;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.event.group.BatchGroupLoadingEvent;
import org.ebayopensource.dsf.ts.event.group.IGroupEventListener;
import org.ebayopensource.dsf.ts.event.group.RemoveGroupDependencyEvent;
import org.ebayopensource.dsf.ts.event.group.RemoveGroupEvent;
import org.ebayopensource.dsf.ts.event.method.AddMethodEvent;
import org.ebayopensource.dsf.ts.event.method.IMethodEventListener;
import org.ebayopensource.dsf.ts.event.method.ModifyMethodEvent;
import org.ebayopensource.dsf.ts.event.method.RemoveMethodEvent;
import org.ebayopensource.dsf.ts.event.method.RenameMethodEvent;
import org.ebayopensource.dsf.ts.event.property.AddPropertyEvent;
import org.ebayopensource.dsf.ts.event.property.IPropertyEventListener;
import org.ebayopensource.dsf.ts.event.property.RemovePropertyEvent;
import org.ebayopensource.dsf.ts.event.property.RenamePropertyEvent;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;
import org.ebayopensource.dsf.ts.event.type.ITypeEventListener;
import org.ebayopensource.dsf.ts.event.type.ModifyTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RemoveTypeEvent;
import org.ebayopensource.dsf.ts.event.type.RenameTypeEvent;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.group.Project;
import org.ebayopensource.dsf.ts.type.TypeName;

/**
 * Listener to JST events.
 * 
 * FIXME: when callback isn't specified, the errors are not propogated to the client code anyhow. Why?
 * 
 * 
 */
public class JstEventListener implements
	IGroupEventListener<IJstType>,
	ITypeEventListener<IJstType>,
	IPropertyEventListener,
	IMethodEventListener{
	
	private JstTypeSpaceMgr m_mgr;
	
	//
	// Constructor
	//
	public JstEventListener(JstTypeSpaceMgr mgr){
		assert mgr != null : "mgr cannot be null";
		m_mgr = mgr;
	}
	
	//
	// Satisfy IGroupEventListener
	//
	/**
	 * @see IGroupEventListener#onGroupAdded(AddGroupEvent)
	 */
	public EventListenerStatus<IJstType> onGroupAdded(AddGroupEvent event,
							IEventListenerHandle handle, 
							ISourceEventCallback<IJstType> callBack) {
		
		if (event == null){
			EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.NoOp, "event is null");
			callback(callBack, status);
			return status;
		}
		
		ArrayList<AddGroupEvent> list = new ArrayList<AddGroupEvent>(1);
		list.add(event);
		
		EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
				EventListenerStatus.Code.Started);
		
		try {
			status = m_mgr.getJstTypeSpaceLoader().loadJstTypesIntoTS(list, callBack);
		}
		catch(Throwable e) {
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Exception, e.getMessage());
		}
		finally {
			notify(EventId.Updated, event, status);
			callback(callBack, status);
		}
		
		return status;
		
	}
	
	/**
	 * @see IGroupEventListener#onGroupRemoveDependency(RemoveGroupDependencyEvent)
	 */
	public EventListenerStatus<IJstType> onGroupRemoveDependency(RemoveGroupDependencyEvent event, 
						IEventListenerHandle handle, 
						ISourceEventCallback<IJstType> callBack) {
		
		if (event == null){
			EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.NoOp, "event is null");
			callback(callBack, status);
			return status;
		}
		
		EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
				EventListenerStatus.Code.Started);
		
		List<RemoveGroupEvent> dependencyList = event.getDependencyList();
		String targetProjName = event.getGroupName();
		
		Group target = m_mgr.getTypeSpaceImpl().getGroup(targetProjName);
		
		Project project = null;
		
		if (target != null && target instanceof Project) {
			project = (Project)target;
		}
		
		try {
			
			if (target == null) {
				status = new EventListenerStatus<IJstType>(
						EventListenerStatus.Code.Failed, "fail to locate target group " + targetProjName);
			}
			else if (project != null){
				for (RemoveGroupEvent dependencyGrp : dependencyList) {
					// remove each group dependency to target project
					//
					project.removeGroupDependency(dependencyGrp.getGroupName());
				}
			}
		}
		catch(Throwable e){
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Exception, e.getMessage());
		}
		finally {
			notify(EventId.Updated, event, status);
			callback(callBack, status);
		}
				
		return status;
	}
	
	/**
	 * @see IGroupEventListener#onGroupAddDependency(AddGroupDependencyEvent)
	 */
	public EventListenerStatus<IJstType> onGroupAddDependency(AddGroupDependencyEvent event, 
						IEventListenerHandle handle, 
						ISourceEventCallback<IJstType> callBack) {
		
		if (event == null){
			EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.NoOp, "event is null");
			callback(callBack, status);
			return status;
		}
		
		EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
				EventListenerStatus.Code.Started);
		
		List<AddGroupEvent> dependencyList = event.getDependencyList();
		String targetProjName = event.getGroupName();
		
		Group target = m_mgr.getTypeSpaceImpl().getGroup(targetProjName);
		
		try {
		
			if (target == null) {
				status = new EventListenerStatus<IJstType>(
						EventListenerStatus.Code.Failed, "fail to locate target group " + targetProjName);
			}
			else {
				ArrayList<AddGroupEvent> groupList = new ArrayList<AddGroupEvent>(1);
				
				// collect list of dependency group to be loaded in TS
				for (AddGroupEvent dependencyGrp : dependencyList) {					
					if (m_mgr.getTypeSpaceImpl().getGroup(dependencyGrp.getGroupName()) == null) {
						// dependency was not loaded in TS, add it to list to be loaded
						groupList.add(dependencyGrp);
					}
				}
					
				status = m_mgr.getJstTypeSpaceLoader().loadJstTypesIntoTS(groupList, callBack);
				
				Project thisProj = null;
				if ((target instanceof Project)) {
					thisProj = (Project)target;
				}
				
				if (thisProj != null) {
					for (AddGroupEvent dependencyGrp : dependencyList) {
						// add each group dependency to target project
						//
						thisProj.addGroupDependency(dependencyGrp.getGroupName());
						
					}
				}
			}
		}
		catch(Throwable e){
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Exception, e.getMessage());
		}
		finally {
			notify(EventId.Updated, event, status);
			callback(callBack, status);
		}
				
		return status;		
	}
	
	
	/**
	 * @see IGroupEventListener#onBatchGroupLoaded(BatchGroupLoadingEvent)
	 */
	public EventListenerStatus<IJstType> onBatchGroupLoaded(BatchGroupLoadingEvent event, 
									IEventListenerHandle handle, 
									ISourceEventCallback<IJstType> callBack) {
		if (event == null){
			EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.NoOp, "event is null");
			callback(callBack, status);
			return status;
		}
	
		EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
				EventListenerStatus.Code.Started);
		
		try {
			status = m_mgr.getJstTypeSpaceLoader().loadJstTypesIntoTS(event.getAllGroups(), callBack);
		}
		catch(Throwable e) {
			e.printStackTrace();
			
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Exception, e.getMessage());
		}
		finally {
			notify(EventId.Loaded, event, status);
			callback(callBack, status);
		}
		
		return status;
		
	}
	
	/**
	 * @see IGroupEventListener#onGroupRemoved(RemoveGroupEvent)
	 */
	public void onGroupRemoved(RemoveGroupEvent event){
		if (event == null){
			return;
		}
	//	TypeResolverRegistry.getInstance().clear(event.getGroupName());
		m_mgr.getTypeSpaceImpl().removeGroup(event.getGroupName());
	};
	
	//
	// Satisfy ITypeEventListener
	//
	/**
	 * @see ITypeEventListener#onTypeAdded(AddTypeEvent,IEventListenerHandle,ISourceEventCallback)
	 */
	public EventListenerStatus<IJstType> onTypeAdded(
			AddTypeEvent<IJstType> event, 
			IEventListenerHandle handle, 
			ISourceEventCallback<IJstType> callback){
		
		if (event == null){
			EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.NoOp, "event is null");
			callback(callback, status);
			return status;
		}
		
		IJstType type = event.getType();
		String groupName = event.getGroupName();
		String fileName = event.getFileName();
		EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
				EventListenerStatus.Code.Started);
		try {

			IJstParseController jstParseController = m_mgr.getJstParseController();
			if (type == null) {
				type = jstParseController.parseAndResolve(groupName, fileName, event.getTypeSource()).getType();
			}
			
			if (type == null) {
				status = new EventListenerStatus<IJstType>(
						EventListenerStatus.Code.Failed, "fail to parse the source of " + groupName + ": " + fileName);
			}
			else {
				
				if(type instanceof JstType){
					JstType jstType = (JstType)type;
					if(!jstType.getStatus().hasResolution()){
						jstParseController.resolve(type);
					}
				}
				
				m_mgr.getTypeDependencyMgr().addType(new TypeName(groupName, type.getName()), type);
				Object userObj = event.getUserObject();
				if (userObj != null) {
					m_mgr.getTypeSpaceImpl().setUserObject(new TypeName(groupName, type.getName()), userObj);
				}
				status = new EventListenerStatus<IJstType>(
						EventListenerStatus.Code.Successful, type);
			}
		}
		catch(Throwable e){
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Exception, e.getMessage());
		}
		finally {
			notify(EventId.Updated, event, status);
			callback(callback, status);
		}
		
		return status;
	};
	
	/**
	 * @see ITypeEventListener#onTypeRenamed(RenameTypeEvent,IEventListenerHandle,ISourceEventCallback)
	 */
	public EventListenerStatus<IJstType> onTypeRenamed(
			RenameTypeEvent event, 
			IEventListenerHandle handle, 
			ISourceEventCallback<IJstType> callback){
		
		if (event == null){
			EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.NoOp, "event is null");
			callback(callback, status);
			return status;
		}
		IJstType type = null;
		EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
				EventListenerStatus.Code.Started);
		try {
			type = m_mgr.getTypeSpaceImpl().renameType(event.getTypeName(), event.getNewTypeName());
			
			if (type != null) {
				renameTypeInPackage(type, event.getTypeName(), event.getNewTypeName());
			}
			
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Successful, type);
		}
		catch(Throwable e){
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Exception, e.getMessage());
		}
		finally {
			notify(EventId.Updated, event, status);
			callback(callback, status);
		}
		
		return status;
	};
	
	private void renameTypeInPackage(IJstType type, TypeName oldName, String newName) {
		
		String oldPackageName = "";
		String newPackageName = "";
		
		String groupName = oldName.groupName();
		String oldTypeName = oldName.typeName();
		
		int last = oldTypeName.lastIndexOf(".");
		
		if (last > 0) {
			oldPackageName = oldTypeName.substring(0, last);
		}
		
		last = newName.lastIndexOf(".");
		
		if (last > 0) {
			newPackageName = newName.substring(0, last);
		}
		
		m_mgr.getTypeSpaceImpl().removeTypeFromPackage(oldPackageName, oldName);
		
		m_mgr.getTypeSpaceImpl().addTypeToPackage(newPackageName, new TypeName(groupName, newName), type);
		
	}
	
	/**
	 * @see ITypeEventListener#onTypeModified(ModifyTypeEvent,IEventListenerHandle,ISourceEventCallback)
	 */
	public EventListenerStatus<IJstType> onTypeModified(
			ModifyTypeEvent event, 
			IEventListenerHandle handle, 
			ISourceEventCallback<IJstType> callback){
		
		if (event == null){
			EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.NoOp, "event is null");
			callback(callback, status);
			return status;
		}
		IJstType type = event.getType();
		String groupName = event.getGroupName();
		String fileName = event.getFileName();
		EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
				EventListenerStatus.Code.Started);
		try {
			
			if (type == null) {
				IJstParseController jstParseController = m_mgr.getJstParseController();
				type = jstParseController.parseAndResolve(groupName, fileName, event.getTypeSource()).getType();
			}
			
			if (type == null){
				status = new EventListenerStatus<IJstType>(
						EventListenerStatus.Code.Failed, "fail to parse the source");
			}
			else {
				TypeName typeName = event.getTypeName();
				
				if (typeName == null) {
					typeName = new TypeName(groupName, type.getName());
				}
				m_mgr.getTypeDependencyMgr().removeType(typeName);
								
				m_mgr.getTypeDependencyMgr().addType(typeName, type);
				
				List<IJstType> dependents = null;
				
				if (type.isMixin()) {
					dependents = m_mgr.getTypeSpace().getAllDependents(typeName);
					
					for (IJstType dependent : dependents) {
						((JstType)dependent).fixMixin(type);
					}
				}
				
				// re-link all direct dependent types since old binding are no longer valid
				//
				dependents = m_mgr.getQueryExecutor().findAllDependentTypes(typeName);
				
				for (IJstType dependent : dependents) {
					m_mgr.getJstParseController().resolve(dependent);
				}				
				
				status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Successful, type);
			}
		}
		catch(Throwable e){
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Exception, e.getMessage());
		}
		finally {
			notify(EventId.Updated, event, status);
			callback(callback, status);
		}
		
		return status;
	};
	
	/**
	 * @see ITypeEventListener#onTypeRemoved(RemoveTypeEvent,IEventListenerHandle,ISourceEventCallback)
	 */
	public EventListenerStatus<IJstType> onTypeRemoved(
			RemoveTypeEvent event, 
			IEventListenerHandle handle, 
			ISourceEventCallback<IJstType> callback){
		
		if (event == null){
			EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.NoOp, "event is null");
			callback(callback, status);
			return status;
		}
		EventListenerStatus<IJstType> status = new EventListenerStatus<IJstType>(
				EventListenerStatus.Code.Started);
		IJstType type = null;
		try {
			type = m_mgr.getTypeDependencyMgr().removeType(event.getTypeName());
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Successful, type);
		}
		catch(Throwable e){
			status = new EventListenerStatus<IJstType>(
					EventListenerStatus.Code.Exception, e.getMessage());
		}
		finally {
			notify(EventId.Updated, event, status);
			callback(callback, status);
		}
		
		return status;
	};
	
	//
	// Satisfy IPropertyEventListener
	//
	/**
	 * @see IPropertyEventListener#onPropertyAdded(AddPropertyEvent)
	 */
	public void onPropertyAdded(AddPropertyEvent event){
		if (event == null){
			return;
		}
		IJstParseController jstParseController = m_mgr.getJstParseController();
		jstParseController.resolve(event.getProperty());
		m_mgr.getPropertyIndexMgr().addProperty(event.getPropertyName(), event.isPropertyStatic(), event.getProperty());
	};
	/**
	 * @see IPropertyEventListener#onPropertyRenamed(RenamePropertyEvent)
	 */
	public void onPropertyRenamed(RenamePropertyEvent event){
		if (event == null){
			return;
		}
		m_mgr.getTypeSpaceImpl().renameProperty(event.getPropertyName(), event.getNewPropertyName());
	};
	/**
	 * @see IPropertyEventListener#onPropertyRemoved(RemovePropertyEvent)
	 */
	public void onPropertyRemoved(RemovePropertyEvent event){
		if (event == null){
			return;
		}
		m_mgr.getPropertyIndexMgr().removeProperty(event.getPropertyName(), event.isPropertyStatic());
	};
	
	//
	// Satisfy IMethodEventListener
	//
	/**
	 * @see IMethodEventListener#onMethodAdded(AddMethodEvent)
	 */
	public void onMethodAdded(AddMethodEvent event){
		if (event == null){
			return;
		}
		IJstParseController jstParseController = m_mgr.getJstParseController();
		jstParseController.resolve(event.getMethod());
		m_mgr.getMethodIndexMgr().addMethod(event.getMethodName(), event.getMethod());
	};
	/**
	 * @see IMethodEventListener#onMethodRenamed(RenameMethodEvent)
	 */
	public void onMethodRenamed(RenameMethodEvent event){
		if (event == null){
			return;
		}
		m_mgr.getTypeSpaceImpl().renameMethod(event.getMethodName(), event.getNewMethodName());
	};
	/**
	 * @see IMethodEventListener#onMethodModified(ModifyMethodEvent)
	 */
	public void onMethodModified(ModifyMethodEvent event){
		if (event == null){
			return;
		}
		IJstParseController jstParseController = m_mgr.getJstParseController();
		jstParseController.resolve(event.getMethod());
		m_mgr.getMethodIndexMgr().modifyMethod(event.getMethodName(), event.isMethodStatic(), event.getMethod());
	};
	/**
	 * @see IMethodEventListener#onMethodRemoved(RemoveMethodEvent)
	 */
	public void onMethodRemoved(RemoveMethodEvent event){
		if (event == null){
			return;
		}
		m_mgr.getMethodIndexMgr().removeMethod(event.getMethodName(), event.isMethodStatic());
	};
	
	//
	// Private
	//
	private void notify(EventId eventId, ISourceEvent event, EventListenerStatus status){
		
		m_mgr.getTypeSpaceEventDispatcher().dispatch(
				new TypeSpaceEvent<ISourceEvent>(eventId, event), status);
	
	}
	
	private void callback(ISourceEventCallback<IJstType> callback, EventListenerStatus<IJstType> status){
		if (callback == null){
			return;
		}
		callback.onComplete(status);
	}
}
