package org.ebayopensource.dsf.jsgen.shared.util;

import java.util.Iterator;
import java.util.List;

import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstConstructor;

public class JstDisplayUtils {
	public static String getFullMethodString(IJstMethod method,
			final IJstType ownerType, final boolean optional) {
		final StringBuilder strBldr = new StringBuilder();
		String name = method.getName().getName();

		name = renameInvoke(method, name);

		if (method instanceof JstConstructor) {
			JstConstructor c = (JstConstructor) method;
			name = c.getOwnerType().getName();
		}

		strBldr.append(name);
		strBldr.append("(");
		IJstType ref = method.getRtnType();
		String oname = "";
		if (ownerType != null) {
			oname = ownerType.getSimpleName();
		}
		String aname = getJstArgsString(method);
		if (aname.length() > 0) {
			strBldr.append(aname);
		}
		strBldr.append(")");
		if (optional) {
			strBldr.append(" ? ");
		}
		if (ref != null) {
			final String rname = ref.getSimpleName();
			strBldr.append(" ").append(rname);
		}
		strBldr.append(" - ");
		strBldr.append(oname);
		return strBldr.toString();

	}

	public static String renameInvoke(IJstMethod method, String name) {
		if(name.equals("_invoke_") && method.getOwnerType().isFType()){
			name = method.getOwnerType().getSimpleName();
		}
		return name;
	}
	
	public static String getJstArgsString(IJstMethod method) {
		StringBuffer buffer = new StringBuffer();
		List<JstArg> args = method.getArgs();
		if (args != null && !args.isEmpty()) {
			Iterator<JstArg> it = args.iterator();
			while (it.hasNext()) {
				JstArg arg = it.next();
				IJstType type = arg.getType();
				if (type != null) {
					buffer.append(type.getSimpleName());
				} else {
					buffer.append("Object");
				}
				buffer.append(" " + arg.getName());
				buffer.append(", ");
			}
		}
		String result = buffer.toString();
		if (result.length() > 2) {
			result = result.substring(0, result.length() - 2);
		}
		return result;
	}
}
