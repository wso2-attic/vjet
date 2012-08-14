package org.ebayopensource.dsf.jstojava.manifest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.declaration.JstObjectLiteralType;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.vjo.lib.TsLibLoader;

/**
 * This file will create types.txt
 * 
 * 
 */
public class CreateTypesList {

	private static String createManifest(Collection<IJstType> types) {

		StringBuilder sb = new StringBuilder();
		// TODO sort this
		for (IJstType type : types) {

			String fullName = type.getName();
			if (type instanceof JstObjectLiteralType) {
				System.out.println(type);
				fullName = type.getPackage().getName();
			}
			sb.append(fullName + "\n");

		}
		return sb.toString();

	}

	public static void main(String[] args) {
		// load in group

		JstParseController controller = new JstParseController(new VjoParser());
		JstTypeSpaceMgr ts = new JstTypeSpaceMgr(controller,
				new DefaultJstTypeLoader());
		ts.initialize();
		TsLibLoader.loadDefaultLibs(ts);

		IGroup<IJstType> group = ts.getTypeSpace().getGroup(
				TsLibLoader.getBrowserGroups()[0]);

		String manifest = CreateTypesList.createManifest(group.getEntities()
				.values());

		System.out.println(manifest);

	}

	public static void createManifest(File typestxt, Collection<IJstType> values) {
		// TODO Auto-generated method stub
		String manifestTxt = createManifest(values);
		FileWriter fos = null;
		try {
			fos = new FileWriter(typestxt);
			fos.append(manifestTxt);
			fos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
