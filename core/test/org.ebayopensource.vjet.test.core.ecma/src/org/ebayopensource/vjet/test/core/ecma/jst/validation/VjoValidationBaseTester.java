package org.ebayopensource.vjet.test.core.ecma.jst.validation;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticProblem;
import org.ebayopensource.dsf.jst.IJstNode;
import org.ebayopensource.dsf.jst.JstProblemId;
import org.ebayopensource.dsf.jst.declaration.JstCache;
import org.ebayopensource.dsf.jst.ts.JstTypeSpaceMgr;
import org.ebayopensource.vjet.test.util.JstLibResolver;
import org.ebayopensource.vjet.test.util.VJetSdkEnvironment;
import org.ebayopensource.vjo.lib.IResourceResolver;
import org.ebayopensource.vjo.lib.LibManager;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Vjo Validation Baser class Include get problem id. judge problem is equals
 * methods
 * 
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
/**
 * Class/Interface description
 *
 * @author <a href="mailto:liama@ebay.com">liama</a>
 * @since JDK 1.5
 */
public abstract class VjoValidationBaseTester {

	private static boolean s_ondemand = true;

    protected List<VjoSemanticProblem> expectProblems = new LinkedList<VjoSemanticProblem>();
    
    private ArrayList<IJstNode> syntaxlist = new ArrayList<IJstNode>();

    List<JstProblemId> tempList = new LinkedList<JstProblemId>();

    public static final String TEST_FOLDER = "testFiles";
    
    private LineNumberComparator comparator = new LineNumberComparator();
    
    @BeforeClass
    public static void start(){

    	JstCache.getInstance().clear();
    	LibManager.getInstance().clear();
    	
		IResourceResolver jstLibResolver = JstLibResolver
				.getInstance()
				.setSdkEnvironment(
						new VJetSdkEnvironment(
								new String[0], "DefaultSdk"));

		LibManager.getInstance().setResourceResolver(jstLibResolver);
    	
    }
    
    
    @Before
    public void clear() {
		// TODO Auto-generated method stub
    	syntaxlist.clear();
    	tempList.clear();
    	expectProblems.clear();
    	singleTypeSpace = null;
    	expectProblems.clear();
    	
	}
    
    /**
     * Get checked file's semantic problem
     * 
     * @param checkedFileName
     *            {@link String}
     * @return List {@link VjoSemanticProblem}
     */
    /**
     * @param checkedFileName
     * @param currentClass
     * @return
     */
   
    
    public List<VjoSemanticProblem> getVjoSemanticProblem(
            String checkedFileName,
            Class<? extends VjoValidationBaseTester> currentClass) {
        if(isOnDemand()){
        	return new VjoValidationTesterHelper().getVjoSemanticProblemOnDemand(null, null, checkedFileName, currentClass, null, false);
        }
        
        judgeLoadCustomType();
        return new VjoValidationTesterHelper().getVjoSemanticProblem(null, null,checkedFileName,
                currentClass);
    }
    
    /**
     * @param checkedFilePack
     * @param checkedFileName
     * @param currentClass
     * @return
     */
    public List<VjoSemanticProblem> getVjoSemanticProblem(
            String checkedFilePack,
            String checkedFileName,
            Class<? extends VjoValidationBaseTester> currentClass) {
         return new VjoValidationTesterHelper().getVjoSemanticProblemOnDemand(null, checkedFilePack, checkedFileName, currentClass, null, false);
    }
    
    /**
     * @param checkedFilePack
     * @param checkedFileName
     * @param currentClass
     * @return
     */
    public List<VjoSemanticProblem> getVjoSyntaxProblem(
            String checkedFilePack,
            String checkedFileName,
            Class<? extends VjoValidationBaseTester> currentClass) {
         return new VjoValidationTesterHelper().getVjoSyntaxProblemOnDemand(null, checkedFilePack, checkedFileName, currentClass);
    }
    
    
    /**
     * @param testFolderName
     * @param checkedFilePack
     * @param checkedFileName
     * @param currentClass
     * @return
     */
    public List<VjoSemanticProblem> getVjoSemanticProblem(
    		String testFolderName,
            String checkedFilePack,
            String checkedFileName,
            Class<? extends VjoValidationBaseTester> currentClass) {
    	 if(isOnDemand()){
         	return new VjoValidationTesterHelper().getVjoSemanticProblemOnDemand(testFolderName, checkedFilePack, checkedFileName, currentClass, null, false);
         }
    	
        judgeLoadCustomType();
        return new VjoValidationTesterHelper().getVjoSemanticProblem(testFolderName, checkedFilePack,checkedFileName,
                currentClass);
    }
    
    /**
     * @param testFolderName
     * @param checkedFilePack
     * @param checkedFileName
     * @param currentClass
     * @return
     */
    public List<VjoSemanticProblem> getVjoSemanticProblem(
    		String testFolderName,
    		String checkedFilePack,
    		String checkedFileName,
    		Class<? extends VjoValidationBaseTester> currentClass,
    		String groupName, boolean printTree) {
    	if(isOnDemand()){
    		return new VjoValidationTesterHelper().getVjoSemanticProblemOnDemand(testFolderName, checkedFilePack, checkedFileName, currentClass, groupName, printTree);
    	}
    	
    	judgeLoadCustomType();
    	return new VjoValidationTesterHelper().getVjoSemanticProblem(testFolderName, checkedFilePack,checkedFileName,
    			currentClass);
    }
    
    
    protected String printMessgeinLog( List<VjoSemanticProblem> actualProblems){
        StringBuffer message = new StringBuffer();
        for (VjoSemanticProblem vjoSemanticProblem : actualProblems) {
            message.append("Actual problem :"+vjoSemanticProblem + "\n");
            message.append("====================================\n");
        }
        return message.toString();
    }
    
    private JstTypeSpaceMgr singleTypeSpace = null;
    
    
    /**
     * If user run test case from single test case
     * Structure will load custom type from sub test cases 
     */
    protected void judgeLoadCustomType(){
//        if(!AllVjoValidationTests.loadAllTypeFlag){
//            singleTypeSpace = VjoValidationTesterHelper.getTs(VjoValidationBaseTester.SINGLE_FOLDER);
//            loadCustomType();
//        }
    }
    
    /**
     * 
     * Load package level js file to current typespace
     * @param jsProjectName test js file source folder name
     * @param packageName String js file package Name
     * @param fileName js file name
     */
    protected void loadJsToType(String jsProjectName, String packageName, String fileName){
        CodeCompletionUtil.loadSingleJsToTypeSpace(singleTypeSpace, jsProjectName, packageName, fileName);
    }
    
    
    /**
     * Sub class should overriden this methods to add new type into current typespace
     * @param customTypeSpace {@link JstTypeSpaceMgr}
     */
    protected void loadCustomType(){
    }
    
    
    /**
     * Judge the expect problem is same with real problems Includes the
     * problem's number
     * 
     * @param expectProblems
     *            List {@link JstProblemId} expects problem IDS
     * @param actualProblems
     *            List {@link VjoSemanticProblem} the actual problems
     * @return boolean
     * 
     * 
     * refactored to report the validation failure in major 2 categories:
     * 1. unexpected errors
     * 2. expected errors not met
     * 
     * which saves effort for testing and debugging
     */
    public void assertProblemEquals(List<VjoSemanticProblem> expectedProblems,
            List<VjoSemanticProblem> actualProblems) {
    	final List<VjoSemanticProblem> expectedProblemsReplica = new ArrayList<VjoSemanticProblem>(expectedProblems);
    	final List<VjoSemanticProblem> unexpectedProblems = new ArrayList<VjoSemanticProblem>(actualProblems.size());
    	for(VjoSemanticProblem actual : actualProblems){
    		//ignore the severity for list element lookup
    		final VjoSemanticProblem actualWithoutSeverity = new VjoSemanticProblem(actual.getID(), actual.getSourceLineNumber(), 0, 0); 
    		if(!expectedProblemsReplica.remove(actualWithoutSeverity)){
    			unexpectedProblems.add(actual);
    		}
    	}
    	
    	//sort by line number for convenience
    	Collections.sort(unexpectedProblems, comparator);
    	Collections.sort(expectedProblemsReplica, comparator);
    	
    	final StringBuilder sb = new StringBuilder();
    	if(unexpectedProblems.size() > 0){
    		sb.append("[unexpected problems found] as the following: ");
	    	for(VjoSemanticProblem unexpected : unexpectedProblems){
	    		sb.append('\n').append(unexpected);
	    	}
    	}
    	//report expected problems not met only when unexpected problems do not exist
    	//as report for both could easily occur and confuse the end user
    	else if(expectedProblemsReplica.size() > 0){
    		sb.append("[expected problems not met] as the following");
    		for(VjoSemanticProblem unmet : expectedProblemsReplica){
	    		sb.append('\n').append(unmet);
	    	}
    	}
    	
    	if(sb.length() > 0){
    		fail(sb.toString());
    	}
    }

    /**
     * Get a new {@link VjoSemanticProblem} object
     * 
     * @param problemId {@link JstProblemId}
     * @param lineNumber int
     * @param colNumber int
     * @return {@link JstProblemId}
     */
    public VjoSemanticProblem createNewProblem(JstProblemId problemId,
            int lineNumber, int colNumber) {
        return new VjoSemanticProblem(null, problemId, "Test Error Msg", null,
                0, 0, lineNumber, colNumber, null);
    }

    /**
     * Get expect problem ids
     * 
     * @return {@link JstProblemId} array
     */
    public Object[] getExpectProblemIDs(List<VjoSemanticProblem> problems) {
        tempList.clear();
        for (VjoSemanticProblem problem : problems) {
            tempList.add(problem.getID());
        }
        return tempList.toArray();
    }
    
    
    /**
     * return syntax error
     * 
     * @return {@link ArrayList}
     */
    protected ArrayList<IJstNode> getSyntaxError(){
    	return null;
    	// this is not correct way to access syntax errors use errorReporter
//        syntaxlist.clear();
//        for (IJstNode node : getJstType().getChildren()) {
//            if(node instanceof JstSyntaxError){
//                syntaxlist.add(node);
//            }
//        }
//        return syntaxlist;
    }

	public static boolean isOnDemand() {
		return s_ondemand;
	}

	public static void setOnDemand(boolean ondemand) {
//		s_ondemand = ondemand;
	}
}
