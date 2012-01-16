package org.ebayopensource.vjet.extjs.core.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.ebayopensource.dsf.jst.IJstParseController;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.loader.DefaultJstTypeLoader;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.jstojava.prebuild.FileUtil;
import org.ebayopensource.dsf.jstojava.resolver.TypeConstructorRegistry;
import org.ebayopensource.dsf.ts.TypeSpace;
import org.ebayopensource.dsf.ts.event.type.AddTypeEvent;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjet.extjs.core.ExtDefineTypeResolver;
import org.ebayopensource.vjo.lib.TsLibLoader;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExtJSJstTypeModelTests {

	@BeforeClass
	public static void setup() {
		// java api for registration
		TypeConstructorRegistry.getInstance().addResolver("Ext::define", new ExtDefineTypeResolver());

	}
	
	@Test
	public void testSimpleExtDefine() throws Exception {
		
		// scaffolding
		IJstParseController pc = new JstParseController(new VjoParser());
		JstTypeSpaceMgr ts = new JstTypeSpaceMgr(pc, new DefaultJstTypeLoader());
		ts.initialize();
		TsLibLoader.loadDefaultLibs(ts);
		
		// bootstrap types
		addTypeToTS(pc, ts, new File(getClass().getResource("extdefine.js").getFile()), "test", "Ext");
		addTypeToTS(pc, ts, new File(getClass().getResource("ExtBase.js").getFile()), "test", "Ext.Base");
		
		// user defined type
		addTypeToTS(pc, ts, new File(getClass().getResource("usingextdefine.js").getFile()), "test", "a.b.c.D");
		
		
		// reading from type space
		IJstType exttype = ts.getTypeSpace().getType(new TypeName("test", "Ext"));
		assertEquals("Ext", exttype.getName());
		IJstType type = ts.getTypeSpace().getType(new TypeName("test", "a.b.c.D"));
		assertEquals("a.b.c.D", type.getName());
		assertTrue(type.getMethod("foo")!=null);
		assertEquals("Ext.Base",type.getExtend().getName());
		
		
		
	}

	private void addTypeToTS(IJstParseController pc, JstTypeSpaceMgr ts, File file, String groupName, String typeName) {
		IScriptUnit su1 = pc.parseAndResolve(groupName, typeName, VjoParser.getContent(file));
		ts.processEvent(new AddTypeEvent<IJstType>(new TypeName(groupName, su1.getType().getName()),su1.getType()));
	}
	
}
