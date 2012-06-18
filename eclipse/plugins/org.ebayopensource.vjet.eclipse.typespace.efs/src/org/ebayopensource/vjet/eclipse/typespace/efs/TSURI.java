package org.ebayopensource.vjet.eclipse.typespace.efs;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class TSURI {

	private String groupName;
	private String typeName;
	private URI uri;

	public TSURI(URI uri) throws URISyntaxException, MalformedURLException,
			UnsupportedEncodingException {
		this.uri = uri;
		decode(uri);
	}

	private void decode(URI uri) {
		String path = uri.getPath();
		String[] segments = path.split("/");
		groupName = uri.getHost();

		typeName = calcTypeName(segments);
	}

	private String calcTypeName(String[] segments) {
		StringBuilder type = new StringBuilder();
		for (int i = 1; i < segments.length; i++) {
			type.append(segments[i]);
			if (i != segments.length - 1) {
				type.append(".");
			}
		}
		return type.toString();
	}

	public String getGroupName() {
		return groupName;
	}

	public String getTypeName() {
		return typeName;
	}

	public TSURI child(String name) throws MalformedURLException,
			UnsupportedEncodingException, URISyntaxException {
		String nameToPath = name.replace(".", "/");
		String childPath = this.uri.getScheme() + "://" + this.uri.getHost()
				+"/" + name;
		System.out.println(childPath);
		return new TSURI(new URI(childPath));
	}

	public static void main(String[] args) throws Exception {
		URI uri = new URI("typespace://group/a/b/C");
		URI parent = new URI("typespace://group/");
		TSURI parentTS = new TSURI(parent);
		TSURI child = parentTS.child("a.b.C");
		System.out.println("group: " + child.getGroupName());
		System.out.println("type: " + child.getTypeName());

		TSURI group = new TSURI(uri);
		System.out.println("group: " + group.getGroupName());
		System.out.println("type: " + group.getTypeName());
	}

	public URI getURI() {
		return uri;
	}

}
