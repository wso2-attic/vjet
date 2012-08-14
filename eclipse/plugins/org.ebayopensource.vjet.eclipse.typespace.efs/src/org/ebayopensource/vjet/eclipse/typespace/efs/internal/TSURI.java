//package org.ebayopensource.vjet.eclipse.typespace.efs.internal;
//
//import java.io.UnsupportedEncodingException;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
//
//public class TSURI {
//
//	private String groupName;
//	private String typeName;
//	private URI uri;
//
//	public TSURI(URI uri) throws URISyntaxException, MalformedURLException,
//			UnsupportedEncodingException {
//		this.uri = uri;
//		decode(uri);
//	}
//
//	private void decode(URI uri) {
//		String path = uri.getPath();
//		String[] segments = path.split("/");
//		groupName = uri.getHost();
//
//		typeName = calcTypeName(segments);
//	}
//
//	private String calcTypeName(String[] segments) {
//		StringBuilder type = new StringBuilder();
//		for (int i = 1; i < segments.length; i++) {
//			System.out.println("segment: " + segments[i]);
//			int indexOfDotJs = segments[i].indexOf(".js");
//			if(indexOfDotJs!=-1){
//				String s = segments[i].substring(0,indexOfDotJs);
//				type.append(s);
//			}else{
//				type.append(segments[i]);
//			}
//			
//			if (i != segments.length - 1) {
//				type.append(".");
//			}
//		}
//		return type.toString();
//	}
//
//	public String getGroupName() {
//		return groupName;
//	}
//
//	public String getTypeName() {
//		return typeName;
//	}
//
//	public TSURI child(String name) throws MalformedURLException,
//			UnsupportedEncodingException, URISyntaxException {
//		String ending = "/";
//		if(name.endsWith(".js")){
//			name = name.substring(0, name.length()-3);
//			ending = ".js";
//		}
//		
//		String nameToPath = name.replace(".", "/");
//		String childPath = this.uri.getScheme() + "://" + this.uri.getHost()
//				 +"/" + nameToPath + ending;
//		System.out.println(childPath);
//		return new TSURI(new URI(childPath));
//	}
//
//	public static void main(String[] args) throws Exception {
//		URI uri = new URI("typespace://group/a/");
//		URI parent = new URI("typespace://group/a/");
//		TSURI parentTS = new TSURI(parent);
//		TSURI child = parentTS.child("C.js");
//		System.out.println("group: " + child.getGroupName());
//		System.out.println("type: " + child.getTypeName());
//
//		TSURI group = new TSURI(uri);
//		System.out.println("group: " + group.getGroupName());
//		System.out.println("type: " + group.getTypeName());
//	}
//
//	public URI getURI() {
//		return uri;
//	}
//
//}
