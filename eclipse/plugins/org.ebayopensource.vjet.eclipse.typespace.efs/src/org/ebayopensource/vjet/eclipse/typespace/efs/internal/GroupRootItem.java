package org.ebayopensource.vjet.eclipse.typespace.efs.internal;


import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.runtime.Path;

public class GroupRootItem extends GroupPkgDirectoryItem{

	private String m_group;

	public GroupRootItem(String group){
		super(group, null);
		m_group = group;
		createEntries(group);
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

	

	private void createEntries(String group) {
		
		IGroup<IJstType> groupSpace = null;
		// TODO use cache 
		groupSpace = TypeSpaceMgr.getInstance().getTypeSpace()
					.getGroup(group);
		GroupRootItem root = this;
		
		for (IJstType type : groupSpace.getEntities().values()) {
			
	
			GroupPkgDirectoryItem parent = root;
			
			String fullName = type.getName();
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
			
			
			String name = type.getSimpleName();
			
			new GroupFileItem(name + ".js", parent);
			
		}

		
	}
	
	
}
