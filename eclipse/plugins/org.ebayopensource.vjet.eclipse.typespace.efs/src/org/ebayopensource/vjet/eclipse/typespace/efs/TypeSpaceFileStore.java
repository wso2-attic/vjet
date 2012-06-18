package org.ebayopensource.vjet.eclipse.typespace.efs;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.filters.StringInputStream;
import org.ebayopensource.dsf.jsgen.shared.generate.CodeStyle;
import org.ebayopensource.dsf.jsgen.shared.vjo.GeneratorCtx;
import org.ebayopensource.dsf.jsgen.shared.vjo.VjoGenerator;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.tool.typespace.TypeSpaceMgr;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.filesystem.provider.FileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * In memory file system implementation used for testing.
 */
public class TypeSpaceFileStore extends FileStore {
	private static final long CURRENT_TIME_MILLIS = System.currentTimeMillis();

	private static final MemoryTree TREE = MemoryTree.TREE;

	private IPath path;
	private final TSURI uri;

	private Map<String,TypeSpaceFileStore> m_cache = new HashMap<String, TypeSpaceFileStore>();

	public TypeSpaceFileStore(TSURI cmisUri) {
		this.uri = cmisUri;
	}

	public String[] childNames(int options, IProgressMonitor monitor) {

		if (this.uri.getTypeName() == null || this.uri.getTypeName().length()==0 ) {

			List<String> children = new ArrayList<String>();
			IGroup<IJstType> group = TypeSpaceMgr.getInstance().getTypeSpace()
					.getGroup(uri.getGroupName());
			if(group==null){
				children.add("unknown");
				String[] ary = new String[children.size()];
				return children.toArray(ary);
			}
			for (IJstType type : group.getEntities().values()) {
				children.add(type.getName());
			}
			String[] ary = new String[children.size()];
			return children.toArray(ary);
		}
		
		return EMPTY_STRING_ARRAY;
		
		// final String[] names = TREE.childNames(path);
		// return names == null ? EMPTY_STRING_ARRAY : names;
	}

	public void delete(int options, IProgressMonitor monitor)
			throws CoreException {
		throw new UnsupportedOperationException();
	}

	public IFileInfo fetchInfo(int options, IProgressMonitor monitor) {

		if (this.uri.getTypeName().length() > 0) {
			FileInfo fi = new FileInfo(this.uri.getTypeName() + ".js");
			fi.setExists(true);
			fi.setLastModified(CURRENT_TIME_MILLIS);
			fi.setDirectory(false);
			fi.setAttribute(EFS.ATTRIBUTE_READ_ONLY, true);
			fi.setAttribute(EFS.ATTRIBUTE_HIDDEN, false);
			return fi;
			

		}

		return TREE.fetchInfo(this.uri);
	}

	public IFileStore getChild(String name) {
		try {
			TSURI childuri = uri.child(name);
			if(m_cache.containsKey(childuri.getURI().toASCIIString())){
				return m_cache.get(childuri.getURI().toASCIIString());
			}
			TypeSpaceFileStore store = new TypeSpaceFileStore(childuri);
			m_cache.put(childuri.getURI().toASCIIString(), store);
			return store;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String getName() {
		final String name = this.uri.getTypeName();
		return name == null ? "" : name;
	}

	public IFileStore getParent() {
		// if (path.segmentCount() == 0)
		return null;
		// return new TypeSpaceFileStore(path.removeLastSegments(1));
	}

	public IFileStore mkdir(int options, IProgressMonitor monitor)
			throws CoreException {
		throw new UnsupportedOperationException();
	}

	public InputStream openInputStream(int options, IProgressMonitor monitor)
			throws CoreException {
		// TODO add extension point here so that multiple providers of view can be used here
		IJstType type = TypeSpaceMgr.getInstance().getTypeSpace().getType(new TypeName(uri.getGroupName(), uri.getTypeName().substring(0, uri.getTypeName().lastIndexOf("."))));
		if(type!=null){
			VjoGenerator gen = new VjoGenerator(new GeneratorCtx(CodeStyle.PRETTY));
			gen.writeType(type);
			return new StringInputStream(gen.getGeneratedText());
		}
		
		return new StringInputStream(uri.getGroupName() + " : " + uri.getTypeName());

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
		return this.uri.getURI();
		// return TypeSpaceFileSystem.toURI(path);
	}
}