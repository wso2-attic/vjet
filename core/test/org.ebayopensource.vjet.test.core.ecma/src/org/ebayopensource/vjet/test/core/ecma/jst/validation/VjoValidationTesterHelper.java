/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
/* 
 * $Id: VjoValiationTesterHelper.java, May 7, 2009, 6:34:28 PM, liama. Exp$
 *
 * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
 * This software program and documentation are copyrighted by Ebay 
 * Technologies.
 */
package org.ebayopensource.vjet.test.core.ecma.jst.validation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.ebayopensource.dsf.jsgen.shared.ids.VjoSyntaxProbIds;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationDriver;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationResult;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.IScriptProblem;
import org.ebayopensource.dsf.jst.IScriptUnit;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.declaration.JstBlock;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.dsf.jstojava.controller.JstParseController;
import org.ebayopensource.dsf.jstojava.parser.VjoParser;
import org.ebayopensource.dsf.ts.event.EventListenerStatus;
import org.ebayopensource.dsf.ts.event.ITypeSpaceEventListener;
import org.ebayopensource.dsf.ts.event.TypeSpaceEvent;
import org.ebayopensource.dsf.ts.event.TypeSpaceEvent.EventId;
import org.ebayopensource.dsf.ts.event.group.AddGroupEvent;
import org.ebayopensource.dsf.ts.group.Group;
import org.ebayopensource.dsf.ts.group.IGroup;
import org.ebayopensource.dsf.ts.type.TypeName;
import org.ebayopensource.vjo.lib.LibManager;
import org.ebayopensource.vjo.lib.TsLibLoader;
import org.junit.Assert;

import org.ebayopensource.dsf.common.resource.ResourceUtil;

/**
 * Vjo Validation tester helper class
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public class VjoValidationTesterHelper {

    private static final String ONDEMAND = "ONDEMAND";

    private IScriptUnit m_unit = null;

    private IJstType m_jstType = null;

    private List<IScriptUnit> m_unitList = new LinkedList<IScriptUnit>();

    VjoParser m_p = new VjoParser() {
        @Override
        public IScriptUnit postParse(IScriptUnit unit) {
            return unit;
        }
    };

    JstParseController m_c = new JstParseController(m_p);

	private String m_groupName = "DEFAULT";

    static {
        String userDir = System.getProperty("user.dir");
        String testFiles = userDir + File.separatorChar + "testFiles";
        String vjLib = userDir + File.separatorChar + "vjLibTestFiles";
        System.setProperty("java.source.path", testFiles + File.pathSeparator + vjLib);
    }

    /**
     * get vjo problem
     * 
     * @param testFolderName
     * @param pacakgeName
     * @param checkedFileName
     * @param currentClass
     * @return List
     */
    public List<VjoSemanticProblem> getVjoSemanticProblem(
            String testFolderName, String pacakgeName, String checkedFileName,
            Class<? extends VjoValidationBaseTester> currentClass) {
        return getVjoProblemSequentialLoad(testFolderName, pacakgeName,
                checkedFileName, currentClass);
    }

    public List<VjoSemanticProblem> getVjoProblemSequentialLoad(
            String testFolderName, String pacakgeName, String checkedFileName,
            Class<? extends VjoValidationBaseTester> currentClass)
            throws AssertionError {

        if (testFolderName == null) {
            testFolderName = VjoValidationBaseTester.TEST_FOLDER;
        }

        final String testFilesPath = new File(testFolderName).getAbsolutePath();
        StringBuffer sb = new StringBuffer(testFilesPath);
        File testFile = null;
        if (pacakgeName == null) {
            try {
                testFile = new File(ResourceUtil.getResource(currentClass,
                        checkedFileName).getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            pacakgeName = pacakgeName.replace("/", ".");
            pacakgeName = pacakgeName.replace('.', File.separatorChar);
            sb.append(File.separatorChar);
            sb.append(pacakgeName);
            sb.append(checkedFileName);
            testFile = new File(sb.toString());
        }

        // if(!AllVjoValidationTests.loadAllTypeFlag){
     
        // }
        VjoValidationResult result = doValidateScriptUnits(testFile,
                getTypespaceMgr(testFolderName));
        return result.getAllProblems();

    }

    public List<VjoSemanticProblem> getVjoSemanticProblemOnDemand(
            String testFolderName, String pacakgeName, String checkedFileName,
            Class<? extends VjoValidationBaseTester> currentClass, String groupName, boolean printTree) {
    	if(groupName==null){
    		groupName = ONDEMAND;
    	}
    	
    	final String grpName = groupName ;
    	
    	
        m_jstType = null;
        m_unit = null;
        m_unitList.clear();

        if (testFolderName == null) {
            testFolderName = VjoValidationBaseTester.TEST_FOLDER;
        }

        URL testFile = null;
        if (pacakgeName == null) {
            try {
                testFile = ResourceUtil.getResource(currentClass, checkedFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            pacakgeName = pacakgeName.replace('.', '/');
            testFile = this.getClass().getClassLoader().getResource(pacakgeName+checkedFileName);
        }
        
        if (testFile == null){
        	System.out.println(">>>>> pacakgeName = " + pacakgeName);
        	System.out.println(">>>>> currentClass = " + currentClass.getName());
        	System.out.println(">>>>> checkedFileName = " + checkedFileName);
        	System.out.println(">>>>> java.source.path = " + System.getProperty("java.source.path"));
			System.out.println(">>>>> java.class.path = " + System.getProperty("java.class.path"));
        	throw new RuntimeException("testFile is null");
        }

        // bugfix by roy, parse without resolve cause unit's syntax root to be
        // unresolved
        // jsttype will be resolved in typespace event though
        String typeName = getTypeName(testFile.getFile());
	
		IScriptUnit unit = m_c.parse(grpName, typeName,
                VjoParser.getContent(testFile));
        final JstTypeSpaceMgr mgr = new JstTypeSpaceMgr(m_c, new OnDemandValidationTestLoader(
                grpName, unit.getType(), testFile));
        //added by huzhou as asynchronous ITypeSpaceEventListener is causing underdetermined test case failures
        mgr.getConfig().setSynchronousEvents(true);
        mgr.initialize();
        TsLibLoader.loadDefaultLibs(mgr);
     
        
        List<String> libs = new ArrayList<String>();
        libs.addAll(Arrays.asList(TsLibLoader.getDefaultLibNames()));
   
   
        
        //added by huzhou to add project to library dependencies till loader of typespace actually does it
        mgr.registerTypeSpaceEventListener(new ITypeSpaceEventListener() {
			
        	private volatile boolean done = false;
			@SuppressWarnings("unchecked")
			@Override
			public void onUpdated(TypeSpaceEvent event, EventListenerStatus status) {
				if(!done && EventId.Updated == event.getId()){
					final Object trigger = event.getTrigger();
					if(trigger instanceof AddGroupEvent){
						final AddGroupEvent addGroupEvt = (AddGroupEvent)trigger;
						final String groupName = addGroupEvt.getGroupName();
						final Group<IJstType> group = (Group<IJstType>)mgr.getTypeSpace().getGroup(groupName);
						if(group != null && grpName.equals(group.getName())){
							final String[] libraries = {JstTypeSpaceMgr.JAVA_PRIMITIVE_GRP, JstTypeSpaceMgr.JS_BROWSER_GRP, JstTypeSpaceMgr.JS_NATIVE_GRP, JstTypeSpaceMgr.VJO_BASE_LIB_NAME, JstTypeSpaceMgr.VJO_SELF_DESCRIBED};
							for(String lib: libraries){
								final IGroup<IJstType> libGroup = mgr.getTypeSpace().getGroup(lib);
								if(lib != null){
									group.addGroupDependency(libGroup);
								}
							}
							done = true;
						}
					}
				}
			}
			
			@Override
			public void onUnloaded(TypeSpaceEvent event, EventListenerStatus status) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoaded(TypeSpaceEvent event, EventListenerStatus status) {
				// TODO Auto-generated method stub
				
			}
		});
        mgr.processEvent(new AddGroupEvent(grpName, null,
                Collections.EMPTY_LIST, Collections.EMPTY_LIST, libs));

 
        if(printTree)
        ParseUtils.printTree2(unit.getType());
        
        unit = m_c.parseAndResolve(grpName, typeName,
                VjoParser.getContent(testFile));

        if(printTree)
        ParseUtils.printTree2(unit.getType());

        
        TestCase.assertNotNull(mgr.getTypeSpace().getGroup(grpName));
        TestCase.assertNotNull(mgr.getQueryExecutor().findAllTypesInPackage(
                unit.getType().getPackage().getName()));

        VjoValidationResult result = doValidate(unit, mgr);
        return result.getAllProblems();
    }

    private String getTypeName(String file) {
    	
		String typeName = file.replace("/", ".");
		if(typeName.indexOf("bin")!=-1){
			typeName = typeName.substring( typeName.indexOf(".bin.")+".bin.".length(), typeName.length());
		}
		if(typeName.indexOf("DSFJsToJavaTests")!=-1){
			typeName = typeName.substring( typeName.indexOf(".DSFJsToJavaTests.")+".DSFJsToJavaTests.".length(), typeName.length());
		}
		
		typeName = typeName.substring(0, typeName.lastIndexOf(".js"));
		return typeName;
	}

	/**
     * Get type space mgr
     * 
     * @param testFolderName
     * @return {@link JstTypeSpaceMgr}
     */
    private static JstTypeSpaceMgr getTypespaceMgr(String testFolderName) {
        if (testFolderName
                .equalsIgnoreCase(VjoValidationBaseTester.TEST_FOLDER)) {
            return TestFileTypeSpaceLoader.getTestFileSpaceLoader();
        } 
        return null;
    }

    /**
     * @param testFile
     * @return
     * @throws AssertionError
     */
    public List<VjoSemanticProblem> getVjoSyntaxProblemOnDemand(
            String testFolderName, String pacakgeName, String checkedFileName,
            Class<? extends VjoValidationBaseTester> currentClass) {

        if (testFolderName == null) {
            testFolderName = VjoValidationBaseTester.TEST_FOLDER;
        }

        final String testFilesPath = new File(testFolderName).getAbsolutePath();
        StringBuffer sb = new StringBuffer(testFilesPath);
//        System.out.println("testFiles path @ " + testFilesPath);
        File testFile = null;
        if (pacakgeName == null) {
            try {
                testFile = new File(ResourceUtil.getResource(currentClass,
                        checkedFileName).getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            pacakgeName = pacakgeName.replace("/", ".");
            pacakgeName = pacakgeName.replace('.', File.separatorChar);
            sb.append(File.separatorChar);
            sb.append(pacakgeName);
            sb.append(checkedFileName);
            testFile = new File(sb.toString());
        }

        VjoParser p = new VjoParser() {
            @Override
            public IScriptUnit postParse(IScriptUnit unit) {
//                System.out.println("POSTPARSE TYPE :" + unit.getType());
//                for (IJstTypeReference ref : JstRefTypeDependencyCollector
//                        .getDependency(unit.getType()).values()) {
//                    System.out.println("DEPENDENCIES");
//                    System.out.println(ref.getReferencedType().getName());
//                }

                return unit;
            }
        };

        JstParseController c = new JstParseController(p);

        IScriptUnit unit = c.parse(ONDEMAND, testFile.getAbsolutePath(),
                VjoParser.getContent(testFile));

        unit = c.parseAndResolve(ONDEMAND, testFile.getAbsolutePath(),
                VjoParser.getContent(testFile));

        List<IScriptProblem> lists = unit.getProblems();
        List<VjoSemanticProblem> actualList = new ArrayList<VjoSemanticProblem>();
        JstProblemId problemID;
        for (IScriptProblem scriptProblem : lists) {
            if (scriptProblem.getID() != null) {
                problemID = scriptProblem.getID();
            } else {
                problemID = VjoSyntaxProbIds.IncorrectVjoSyntax;
            }
            actualList.add(new VjoSemanticProblem(null, problemID,
                    scriptProblem.getMessage(), scriptProblem
                            .getOriginatingFileName(), 0, 0, scriptProblem
                            .getSourceLineNumber(), 0, scriptProblem.type()));
        }
        return actualList;
    }

    public static void main(String[] args) {
        File file = new File(
                "C:\\Eric\\views\\LiangMa_VJet_LIGER1\\v4darwin\\DSFJsToJavaTests\\testFiles\\access\\finalcheck\\FinalAbstract.js");
    }

    /**
     * @param testFile
     * @return
     * @throws AssertionError
     */
    private VjoValidationResult doValidate(final IScriptUnit unit,
            JstTypeSpaceMgr ts) throws AssertionError {
        // unitList.clear();
        IJstType jstType = null;
        // IScriptUnit unit = null;
        VjoValidationDriver driver = new VjoValidationDriver();
        // final VjoParser p = new VjoParser(); // syntax validation
        // unit = p.parse("test", testFile);
        if (unit == null)
            throw new AssertionError("Unable to find specified test file.");
        // if (VjoValidationBaseTester.isOnDemand()) {
//        jstType = ts.getTypeSpace().getType(
//                new TypeName(ONDEMAND, unit.getType().getName()));
        jstType = unit.getType();
        // } else {
        // jstType = unit.getType();
        // }

        Assert.assertNotNull(jstType);
        if (jstType == null)
            throw new AssertionError("Unable to parse specified test file.");
        if (unit.getProblems().size() > 0) {
        	final StringBuilder sb = new StringBuilder();
        	for(IScriptProblem p: unit.getProblems()){
        		sb.append(p.toString());
        		sb.append('\n');
        	}
        	Assert.fail(sb.toString());
        }
        // if(unit.getType().isClass() && unit.getType().getExtend() == null)
        // throw new AssertionError(
        // "Can't find the type extends, Please check it.");
        // unitList.add(unit);
        // ts.getTypeSpace().addAllGlobalTypeMembers(new
        // TypeName(LibManager.JS_NATIVE_LIB_NAME,"Global"));
        // driver.setTypeSpaceMgr(ts);

        // unitList.add(unit);
        // ts.getTypeSpace().addAllGlobalTypeMembers(new
        // TypeName(LibManager.JS_NATIVE_LIB_NAME,"Global"));
        driver.setTypeSpaceMgr(ts);

        List<IScriptUnit> types = new ArrayList<IScriptUnit>();
        final IJstType resolvedType = jstType;

        types.add(new IScriptUnit() {

            @Override
            public List<JstBlock> getJstBlockList() {
                return unit.getJstBlockList();
            }

            @Override
            public IJstNode getNode(int startOffset) {
                return unit.getNode(startOffset);
            }

            @Override
            public List<IScriptProblem> getProblems() {
                return unit.getProblems();
            }

            @Override
            public JstBlock getSyntaxRoot() {
                return unit.getSyntaxRoot();
            }

            @Override
            public IJstType getType() {
                return resolvedType;
            }

        });
        VjoValidationResult result = driver.validateComplete(types, ONDEMAND);
        Assert.assertFalse(driver.hasInternalErrors());
        return result;
    }

    private VjoValidationResult doValidateScriptUnits(File testFile,
            JstTypeSpaceMgr ts) throws AssertionError {
        m_unitList.clear();
        m_jstType = null;
        m_unit = null;
        VjoValidationDriver driver = new VjoValidationDriver();
        final VjoParser p = new VjoParser(); // syntax validation
        m_unit = p.parse("test", testFile);
        if (m_unit == null)
            throw new AssertionError(
                    "Can't find specify test file, Please check it.");
        m_jstType = m_unit.getType();

        Assert.assertNotNull(m_jstType);
        if (m_jstType == null)
            throw new AssertionError(
                    "Can't parse specify test file, Please check it.");
        if (m_unit.getProblems().size() > 0) {
            throw new AssertionError(
                    "Test js file have syntax error, Please check it.");
        }
        // if(unit.getType().isClass() && unit.getType().getExtend() == null)
        // throw new AssertionError(
        // "Can't find the type extends, Please check it.");
        m_unitList.add(m_unit);
        ts.getTypeSpace().addAllGlobalTypeMembers(
                new TypeName(LibManager.JS_NATIVE_LIB_NAME, "Global"));
        driver.setTypeSpaceMgr(ts);

        Assert.assertFalse(driver.hasInternalErrors());
        VjoValidationResult result = driver
                .validateComplete(m_unitList, "test");

        return result;
    }

}
