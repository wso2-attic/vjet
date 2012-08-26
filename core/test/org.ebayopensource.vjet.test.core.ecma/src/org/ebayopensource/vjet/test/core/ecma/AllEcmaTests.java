package org.ebayopensource.vjet.test.core.ecma;

import org.ebayopensource.dsf.ast.recovery.RecoveryTests;
import org.ebayopensource.dsf.jslang.feature.bugtests.JsParserTestCases;
import org.ebayopensource.vjet.test.core.ecma.jst.validation.AllEcmaValidationTests;
import org.ebayopensource.vjet.test.core.ecma.ts.tests.JsLangTypeSpaceTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AllEcmaValidationTests.class, JsLangTypeSpaceTests.class,
		JsParserTestCases.class, RecoveryTests.class })
public class AllEcmaTests {

}
