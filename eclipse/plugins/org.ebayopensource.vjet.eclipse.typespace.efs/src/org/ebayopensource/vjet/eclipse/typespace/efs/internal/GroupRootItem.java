package org.ebayopensource.vjet.eclipse.typespace.efs.internal;


import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.mod.internal.core.VjoLocalVariable;

public class GroupRootItem extends GroupPkgDirectoryItem{

	private String m_group;
	private boolean m_initialized;

	public GroupRootItem(String group){
		super(group, null);
		m_group = group;
		createEntries(group);
	}
	
	public GroupRootItem(String group, IFile zip){
		super(group, null);
		m_group = group;
		createEntries(zip);
	}

//
	public String getFullName() {
		return m_group;
	}
//
	// TODO do I need to add input stream here?
//	protected InputStream getInputStream(ZipEntry entry) throws IOException {
//		return file.getInputStream(entry);
//	}

	public synchronized void createEntries(IFile zip){
		if(m_initialized==true){
			return;
		}
		
		// open zip file looking for types.txt
		ZipInputStream in = null;
		try {
			IFileStore store = EFS.getStore(zip.getLocationURI());
			in = new ZipInputStream(store.openInputStream(EFS.NONE, null));
			ZipEntry current;
			while ((current = in.getNextEntry()) != null) {
				if(!(current.isDirectory()) && current.getName().equals("types.txt") ){
					// TODO close stream correctly
					InputStream stream = new ZipFile(store.toLocalFile(0, null)).getInputStream(current);
					String typesStr = VjoParser.load(stream, "types.txt");
					String[] types = typesStr.split("\n");
					GroupRootItem root = this;
					for(String t: types){
						GroupPkgDirectoryItem parent = root;

//						// TODO move this into manifest location since types may not exist yet
//						if(type instanceof JstObjectLiteralType){
//							System.out.println(type);
//							fullName = type.getPackage().getName();
//						}
						
						addEntries(parent, t);
					}
					stream.close();
				}
			}
			
			m_initialized = true;
			
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}

	private synchronized final void createEntries(String group) {
		
		if(m_initialized==true){
			return;
		}
		
		IGroup<IJstType> groupSpace = null;
		// TODO use cache or manifest to avoid NPE
		groupSpace = TypeSpaceMgr.getInstance().getTypeSpace()
					.getGroup(group);
		if(groupSpace==null){
			return;
		}
		
		GroupRootItem root = this;
		
		for (IJstType type : groupSpace.getEntities().values()) {
			
	
			GroupPkgDirectoryItem parent = root;
			
			String fullName = type.getName();
			// TODO move this into manifest location since types may not exist yet
			if(type instanceof JstObjectLiteralType){
				System.out.println(type);
				fullName = type.getPackage().getName();
			}
			
			addEntries(parent, fullName);
			
		}
		m_initialized = true;
		
	}

	private void addEntries(GroupPkgDirectoryItem parent, String fullName) {
		fullName = fullName.replace(".", "/");
		Path path = new Path(fullName);
		for (int i = 0; i < path.segmentCount() - 1; i++) {
			String dirName = path.segment(i);

			GroupItem item = parent
					.getItem(dirName);
			GroupPkgDirectoryItem newParent =(GroupPkgDirectoryItem) item;
			if (newParent == null) {
				newParent = new GroupPkgDirectoryItem(dirName, parent);
			}
			parent = newParent;
		}
		
		String name = path.lastSegment();
		
		new GroupFileItem(name + ".js", parent);
	}
	
	
}
