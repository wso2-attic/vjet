package org.ebayopensource.vjet.eclipse.typespace.efs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipException;

import org.apache.tools.ant.filters.StringInputStream;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.eclipse.typespace.efs.internal.GroupFileItem;
import org.ebayopensource.vjet.eclipse.typespace.efs.internal.GroupItem;
import org.ebayopensource.vjet.eclipse.typespace.efs.internal.GroupPkgDirectoryItem;
import org.ebayopensource.vjet.eclipse.typespace.efs.internal.GroupRootItem;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

/**
 * In memory file system implementation used for testing.
 */
public class TypeSpaceFileStore extends FileStore {
	private static final long CURRENT_TIME_MILLIS = System.currentTimeMillis();

	static GroupItem getRoot(URI uri) throws URISyntaxException, ZipException,
			IOException, CoreException {
//		URI type = new URI(uri.getHost());
		return TypeSpaceFileSystem.getItem(uri);
	}

	private String name;
	private TypeSpaceFileStore parent;
	private URI uri;

	private GroupItem groupItem;

	private TypeSpaceFileStore(String name, TypeSpaceFileStore parent) {
		this.name = name;
		this.parent = parent;
	}

	public TypeSpaceFileStore(String name, TypeSpaceFileStore parent, URI uri)
			throws ZipException, URISyntaxException, IOException, CoreException {
		this(name, parent, getRoot(uri).getItem(new Path(uri.getPath()), 0));
		this.uri = uri;
	}

	public TypeSpaceFileStore(String name, TypeSpaceFileStore parent,
			GroupItem item) {
		this(name, parent);
		this.groupItem = item;

	}

	public String[] childNames(int options, IProgressMonitor monitor)
			throws CoreException {
		if (isDirectory()) {
			Collection collection = ((GroupPkgDirectoryItem) groupItem)
					.getChildren();
			String[] children = new String[collection.size()];
			Iterator iterator = collection.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				GroupItem zipItem = (GroupItem) iterator.next();
				children[i++] = zipItem.getName();
			}
			return children;
		} else {
			return new String[0];
		}
	}

	private boolean isDirectory() {
		return groupItem instanceof GroupPkgDirectoryItem;
	}

//	public String[] childNames2(int options, IProgressMonitor monitor) {
//
//		if (getTypeName() == null || getTypeName().length() == 0) {
//			IGroup<IJstType> group = null;
//			// TODO use cache
//			group = TypeSpaceMgr.getInstance().getTypeSpace()
//					.getGroup(getGroupName());
//
//			List<String> children = new ArrayList<String>();
//			if (group == null) {
//				children.add("unknown");
//				String[] ary = new String[children.size()];
//				return children.toArray(ary);
//			}
//			for (IJstType type : group.getEntities().values()) {
//				children.add(type.getName());
//			}
//			String[] ary = new String[children.size()];
//			return children.toArray(ary);
//		}
//
//		return EMPTY_STRING_ARRAY;
//
//		// final String[] names = TREE.childNames(path);
//		// return names == null ? EMPTY_STRING_ARRAY : names;
//	}

	private String getGroupName() {
		return toURI().getHost();
	}

	private String getTypeName() {
		if(!isDirectory()){
			StringBuilder sb = new StringBuilder();
			GroupItem gi = groupItem;
			int count =0;
			do{
				
				
				String groupName = gi.getName();
				
				if(count==0){
					sb.insert(0, groupName);
				}else{
					sb.insert(0, groupName +".");
				}
				
				
				count++;
				
				gi = gi.getParent();
			}while(!(gi instanceof GroupRootItem));
			
			String typename = sb.toString();
			typename = typename.replace(".js", "");
			
			return typename;	
		}
		else{
			return "";
		}
	}

	public void delete(int options, IProgressMonitor monitor)
			throws CoreException {
		throw new UnsupportedOperationException();
	}

	public IFileInfo fetchInfo(int options, IProgressMonitor monitor) {

		FileInfo fileInfo = new FileInfo(getName());
		if (isDirectory()) {
			fileInfo.setDirectory(true);
		} else {
			fileInfo.setDirectory(false);
			fileInfo.setLastModified(CURRENT_TIME_MILLIS);
		}
		fileInfo.setExists(true);
		fileInfo.setAttribute(EFS.ATTRIBUTE_READ_ONLY, true);
		return fileInfo;

		// if (isDirectory() && !(groupItem instanceof GroupRootItem)) {
		// FileInfo fileInfo = new FileInfo(getName());
		// fileInfo.setDirectory(true);
		// }else{
		// FileInfo fi = new FileInfo(this.uri.getTypeName() + ".js");
		// fi.setExists(true);
		// fi.setLastModified(CURRENT_TIME_MILLIS);
		// fi.setDirectory(false);
		// fi.setAttribute(EFS.ATTRIBUTE_READ_ONLY, true);
		// fi.setAttribute(EFS.ATTRIBUTE_HIDDEN, false);
		// return fi;
		//
		//
		// }
		//
		// return TREE.fetchInfo(this.uri);
	}

//	public IFileStore getChild4(String name) {
//		if (isDirectory()) {
//			// TSURI childuri =null;
//			// try {
//			// childuri = uri.child(name);
//			// } catch (MalformedURLException e) {
//			// // TODO Auto-generated catch block
//			// e.printStackTrace();
//			// } catch (UnsupportedEncodingException e) {
//			// // TODO Auto-generated catch block
//			// e.printStackTrace();
//			// } catch (URISyntaxException e) {
//			// // TODO Auto-generated catch block
//			// e.printStackTrace();
//			// }
//			GroupItem child = ((GroupPkgDirectoryItem) groupItem).getItem(name);
//			return new TypeSpaceFileStore(this, child);
//		} else {
//			return null;
//		}
//	}
	
	public IFileStore getChild(String name) {
		
		if (isDirectory()) {
			GroupItem child = ((GroupPkgDirectoryItem) groupItem).getItem(name);
			return new TypeSpaceFileStore(name, this, child);
		} else {
			return null;
		}
	}
	

	// public IFileStore getChild2(String name) {
	// try {
	// TSURI childuri = uri.child(name);
	// if(m_cache.containsKey(childuri.getURI().toASCIIString())){
	// return m_cache.get(childuri.getURI().toASCIIString());
	// }
	// // TypeSpaceFileStore store = new TypeSpaceFileStore(childuri);
	// // m_cache.put(childuri.getURI().toASCIIString(), store);
	// // return store;
	// } catch (MalformedURLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (UnsupportedEncodingException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (URISyntaxException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	//
	// }

	public String getName() {
		return name;
	}

	public IFileStore getParent() {
		// if (path.segmentCount() == 0)
		return parent;
		// return new TypeSpaceFileStore(path.removeLastSegments(1));
	}

	public IFileStore mkdir(int options, IProgressMonitor monitor)
			throws CoreException {
		throw new UnsupportedOperationException();
	}

	public InputStream openInputStream(int options, IProgressMonitor monitor)
			throws CoreException {
		// TODO add extension point here so that multiple providers of view can
		// be used here
		String typeName = getTypeName();
		IJstType type = TypeSpaceMgr.getInstance().getTypeSpace()
				.getType(new TypeName(getGroupName(), typeName));
		if (type != null) {
			VjoGenerator gen = new VjoGenerator(new GeneratorCtx(
					CodeStyle.PRETTY));
			gen.writeType(type);
			return new StringInputStream(gen.getGeneratedText());
		}

		return new StringInputStream(getGroupName() + " : " + getTypeName());

	}

	public OutputStream openOutputStream(int options, IProgressMonitor monitor)
			throws CoreException {
		// return TREE.openOutputStream(path, options);
		return null;
	}

	public void putInfo(IFileInfo info, int options, IProgressMonitor monitor)
			throws CoreException {
		// TREE.putInfo(path, info, options);
	}

	public URI toURI() {
		try {
			URI base = getBase();
			return new URI(base.getScheme(), null, base.getHost(), 0, getPath()
					.toString(), base.getQuery(), null);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public URI getUri() {
		return toURI();
	}

	protected IPath getPath() {
		if (parent == null) {
			return new Path("/");
		} else {
			return parent.getPath().append(getName());
		}
	}

	protected URI getBase() {
		if (parent == null) {
			return uri;
		} else {
			return ((TypeSpaceFileStore) getParent()).getBase();
		}
	}

	// public TSURI getTsURI(){
	// return uri;
	// }
}