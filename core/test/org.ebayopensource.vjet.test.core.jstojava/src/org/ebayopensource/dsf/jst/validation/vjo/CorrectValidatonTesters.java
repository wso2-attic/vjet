/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
///* 
// * $Id: CorrectValidatonTesters.java, May 21, 2009, 8:15:10 PM, liama. Exp$
// *
// * Copyright (c) 2006-2009 Ebay Technologies. All Rights Reserved.
// * This software program and documentation are copyrighted by Ebay 
// * Technologies.
// */
//package org.ebayopensource.dsf.jst.validation.vjo;
//
//import org.junit.runner.RunWith;
//import org.junit.runners.Suite;
//import org.junit.runners.Suite.SuiteClasses;
//
///**
// * Here are all correct test cases to ensure correct one will always correct
// * Because now a lot of validation test cases are fail. 
// *  *
// * @author <a href="mailto:liama@ebay.com">liama</a>
// * @since JDK 1.5
// */
//
//@RunWith(Suite.class)
//@SuiteClasses({
//    
//    //Added by eric 2009.05.22
//    
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes.TypecheckCompartibleType1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes.TypecheckCompartibleType4Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes.TypecheckCompartibleType5Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes.TypecheckCompartibleType6Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.compatibletypes.TypecheckCompartibleType7Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.paramtypes.Paramtype2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.paramtypes.Paramtype3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.paramtypes.ArgCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.paramtypes.ArgTest2.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.TypecheckReturnType1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.TypecheckReturnType2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.TypecheckReturnType3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.returntype.TypecheckReturnTypeCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.array.CorrectArray3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.typecheck.array.ArrayType1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.generic.DeclaredGeneric1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.generic.DeclaredGeneric2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.method.impl.SyntaxInterfaceCorrect2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.method.impl.SyntaxInterfaceCorrect3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.method.abstractMethod.SyntaxAbstractCorrect2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.inits.CorrectInitTest1.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload.MethodOverload1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload.MethodOverloadCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload.MethodOverloadCorrect2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload.construct.ConstructorOverloadCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.method.overload.construct.ConstructorOverload1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxUniqueCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxUniqueCorrect2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxVarUnqiue1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxVarUnqiue2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxVarUnqiue3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxClassUnqiue1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxClassUnqiue2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxMetUnqiue1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxMetUnqiue2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxArgUnqiue1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.SyntaxArgUnqiue2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.JavaKeyWord1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.unique.JavaKeyWord2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB305Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB355Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB356Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB394Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB464Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB470Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB477Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB481Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB483Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB490Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB1074Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB2192ATester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB2192BTester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB2192CTester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.ValidationB3238Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.bugs.Validation3273Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.needs.Needs1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.needs.Needs2Testr.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.mixin.MixinType4Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.mixin.MixInTypeCorrect3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.ifstatement.SyntaxIfCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.ifstatement.SyntaxIfCorrect2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.ifstatement.SyntaxIfCorrect3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.ifstatement.SyntaxIfStatement1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.dowhile.Dowhile1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.dowhile.Dowhile2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.AccessOuterType.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.AnonDeclaredCorrect1.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.NestedTypesCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.innertype.NestedTypesCorrect2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype4Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtype5Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtypeCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.ctype.DeclaredCtypeCorrect4Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItype2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItype3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItype4Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.itype.DeclaredItypeCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype4Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype5Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.etype.DeclaredEtype6Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype.DeclaredMtype1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype.DeclaredMtype2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.syntax.declare.mtype.DeclaredMtype3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.returncheck.AccessReturnCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.returncheck.AccessReturn1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.returncheck.AccessReturn2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord.CorrectThisWord1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord.CorrectThisWord2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord.CorrectThisWord3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord.CorrectThisWord4Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord.CorrectThisWord6Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord.ThisWord1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord.ThisWord2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.scope.thisKeyWord.ThisWord3Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.finalcheck.FinalVar1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.finalcheck.FinalVar2Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.finalcheck.FinalVarCorrect1Tester.class,
//    org.ebayopensource.dsf.jst.validation.vjo.access.finalcheck.FinalVarCorrect2Tester.class
//
//    //End added
//})
//
//public class CorrectValidatonTesters {
//
//}
