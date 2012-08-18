package org.eclipse.dltk.mod.ast.references;

import org.eclipse.dltk.mod.ast.references.TypeReference;

public class VjoTypeReference extends TypeReference {

	private String m_packageName;
	
	public VjoTypeReference(int start, int end, String name) {
		super(start, end, name);
	}

	public String getPackageName() {
		return m_packageName;
	}

	public void setPackageName(String packageName) {
		this.m_packageName = packageName;
	}

}
