/*******************************************************************************
 * Copyright (c) 2005-2011 eBay Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.ebayopensource.dsf.jsgen.shared.validation.vjo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoSemanticValidator;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.VjoValidationCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.VjoSemanticRuleRepo;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.rulectx.BaseVjoSemanticRuleCtx;
import org.ebayopensource.dsf.jsgen.shared.validation.vjo.semantic.rules.util.TypeCheckUtil;
import org.ebayopensource.dsf.jst.IJstMethod;
import org.ebayopensource.dsf.jst.IJstProperty;
import org.ebayopensource.dsf.jst.IJstType;
import org.ebayopensource.dsf.jst.ISynthesized;
import org.ebayopensource.dsf.jst.declaration.JstArg;
import org.ebayopensource.dsf.jst.declaration.JstMethod;
import org.ebayopensource.dsf.jst.declaration.JstParamType;
import org.ebayopensource.dsf.jst.declaration.JstProxyMethod;
import org.ebayopensource.dsf.jst.declaration.JstProxyProperty;
import org.ebayopensource.dsf.jst.declaration.JstType;
import org.ebayopensource.dsf.jst.declaration.JstTypeWithArgs;

public class MixinValidationUtil {

    public static boolean validateMixin(final VjoValidationCtx ctx,
            final VjoSemanticValidator validator, final IJstType targetType,
            final IJstType mixinType) {
        return validateMixin(ctx, validator, targetType, mixinType, false);
    }

    public static boolean validateMixin(final VjoValidationCtx ctx,
            final VjoSemanticValidator validator, final IJstType targetType,
            final IJstType mixinType, final boolean isInstanceMixin) {
        if (targetType == null || mixinType == null) {
            return false;// cannot do mixin
        }

        if (!targetType.isClass()) {
            // report problem, only ctype could be mixined
            return false;// cannot do mixin
        } else if (!mixinType.isMixin()) {
            // report problem, only mtype could be used for mixin
            return false;// cannot do mixin
        }

        final Map<String, List<IJstMethod>> instanceMethodMap = new HashMap<String, List<IJstMethod>>();
        final List<? extends IJstMethod> allInstanceMethods = targetType
                .getMethods(false, true);
        for (IJstMethod m : allInstanceMethods) {
            List<IJstMethod> mList = instanceMethodMap.get(m.getName()
                    .getName());
            if (mList == null) {
                mList = new ArrayList<IJstMethod>();
                instanceMethodMap.put(m.getName().getName(), mList);
            }
            mList.add(m);
        }
        // indexing static methods, group by name
        final Map<String, List<IJstMethod>> staticMethodMap = new HashMap<String, List<IJstMethod>>();
        final List<? extends IJstMethod> allStaticMethods = targetType
                .getMethods(true);
        for (IJstMethod m : allStaticMethods) {
            List<IJstMethod> mList = staticMethodMap.get(m.getName().getName());
            if (mList == null) {
                mList = new ArrayList<IJstMethod>();
                staticMethodMap.put(m.getName().getName(), mList);
            }
            mList.add(m);
        }

        return validateMixin(ctx, validator, targetType, mixinType,
                instanceMethodMap, staticMethodMap, isInstanceMixin);
    }

    public static boolean validateMixin(final VjoValidationCtx ctx,
            final VjoSemanticValidator validator, final IJstType targetType,
            final IJstType mixinType,
            final Map<String, List<IJstMethod>> instanceMtds,
            final Map<String, List<IJstMethod>> staticMtds,
            final boolean isInstanceMixin) {
        if (targetType == null || mixinType == null) {
            return false;// cannot do mixin
        }

        if (!targetType.isClass()) {
            // report problem, only ctype could be mixined
            return false;// cannot do mixin
        } else if (!mixinType.isMixin()) {
            // report problem, only mtype could be used for mixin
            return false;// cannot do mixin
        } else if (isInstanceMixin) {
            if (mixinType.getMethods(true).size() > 0
                    || mixinType.getProperties(true).size() > 0) {
                // report problem, instance mixin couldn't be done with mtype
                // having static members;
            }
        }

        outer: for (IJstType expectType : mixinType.getExpects()) {
            if (expectType != null) {
                expectType = ctx.getTypeSpaceType(expectType);
                for (IJstType satisfyType : getSatisfies(ctx, targetType)) {
                    if (satisfyType != null
                            && satisfyType.getName().equals(
                                    expectType.getName())) {
                        // matched
                        continue outer;
                    }
                }

                if (expectType.isInterface()
                        || expectType.getModifiers().isAbstract()) {
                    if (targetType instanceof JstType) {
                        validateImplemented(ctx, (JstType) targetType,
                                expectType, validator, mixinType);
                    }
                }
            }
        }

        for (IJstMethod toMixMethod : mixinType.getMethods(false, true)) {
        	if(toMixMethod instanceof ISynthesized){
        		continue;
        	}
            List<IJstMethod> candidateMethods = instanceMtds.get(toMixMethod
                    .getName().getName());
            if (candidateMethods != null && candidateMethods.size() > 0) {
                for (IJstMethod mtd : candidateMethods) {
                    if (!(mtd instanceof JstProxyMethod)) {
                        if (toMixMethod instanceof JstProxyMethod && toMixMethod.getOwnerType().isClass()) {
                            fireDuplicateProblem(ctx, validator, mtd);
                        } else if (toMixMethod instanceof JstMethod && toMixMethod.getOwnerType().isMixin()) {
                            fireDuplicateProblem(ctx, validator, mtd);
                        }
                    }
                }
            }
        }

        for (IJstMethod toMixMethod : mixinType.getMethods(true, true)) {
        	if(toMixMethod instanceof ISynthesized){
        		continue;
        	}
            List<IJstMethod> candidateMethods = staticMtds.get(toMixMethod
                    .getName().getName());

            if (candidateMethods != null && candidateMethods.size() > 0) {
                for (IJstMethod mtd : candidateMethods) {
                    if (!(mtd instanceof JstProxyMethod)) {
                        if (toMixMethod instanceof JstProxyMethod && toMixMethod.getOwnerType().isClass()) {
                            fireDuplicateProblem(ctx, validator, mtd);
                        } else if (toMixMethod instanceof JstMethod &&
                                toMixMethod.getOwnerType().isMixin()) {
                            fireDuplicateProblem(ctx, validator, mtd);
                        }
                    }
                }
            }
        }

        for (IJstProperty toMixProperty : mixinType.getAllPossibleProperties(
                false, true)) {
            IJstProperty p = targetType.getProperty(toMixProperty.getName()
                    .getName(), false, true);
            if (p != null
                    && !(p instanceof JstProxyProperty || p instanceof ISynthesized)) {
                final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(
                        p, ctx.getGroupId(), new String[] { toMixProperty
                                .getName().getName() });
                validator.satisfyRule(ctx,
                        VjoSemanticRuleRepo.getInstance().DUPLICATE_PROPERTY,
                        ruleCtx);
            }
        }

        for (IJstProperty toMixProperty : mixinType.getAllPossibleProperties(
                true, true)) {
            IJstProperty p = targetType.getProperty(toMixProperty.getName()
                    .getName(), true, false);
            if (p != null
                    && !(p instanceof JstProxyProperty || p instanceof ISynthesized)) {
                final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(
                        p, ctx.getGroupId(), new String[] { toMixProperty
                                .getName().getName() });
                validator.satisfyRule(ctx,
                        VjoSemanticRuleRepo.getInstance().DUPLICATE_PROPERTY,
                        ruleCtx);
            }
        }

        return true;
    }

    private static void fireDuplicateProblem(final VjoValidationCtx ctx, final VjoSemanticValidator validator,
             IJstMethod mtd) {
        final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(
                mtd.getName(),
                ctx.getGroupId(),
                new String[] { mtd.getName().getName() });
        validator.satisfyRule(ctx, VjoSemanticRuleRepo
                .getInstance().DUPLICATE_METHOD, ruleCtx);
    }

    private static Set<IJstType> getSatisfies(final VjoValidationCtx ctx,
            final IJstType jstType) {
        final Set<IJstType> satisfies = new HashSet<IJstType>();
        getSatifies(ctx, jstType, satisfies, new HashSet<IJstType>());
        return satisfies;
    }

    private static void getSatifies(final VjoValidationCtx ctx, IJstType root,
            final Set<IJstType> satifies, final Set<IJstType> visited) {
        root = ctx.getTypeSpaceType(root);
        if (root == null || visited.contains(root)) {
            return;
        } else {
            visited.add(root);
        }

        if (root.isClass()) {
            for (IJstType satisfy : root.getSatisfies()) {
                getSatifies(ctx, satisfy, satifies, visited);
            }

            for (IJstType extend : root.getExtends()) {
                getSatifies(ctx, extend, satifies, visited);
            }
        } else if (root.isInterface()) {
            satifies.add(root);

            for (IJstType extend : root.getExtends()) {
                getSatifies(ctx, extend, satifies, visited);
            }
        }
    }

    private static boolean validateImplemented(final VjoValidationCtx ctx,
            final JstType jstType, IJstType extendType,
            VjoSemanticValidator validator, IJstType mixType) {

        JstTypeWithArgs typeWithArgs = null;

        if (extendType instanceof JstTypeWithArgs) {
            typeWithArgs = (JstTypeWithArgs) extendType;
            extendType = typeWithArgs.getType();
        }

        List<? extends IJstMethod> extTypeMtds = extendType.getMethods(false,
                true);
        if (extTypeMtds != null) {
            for (IJstMethod extTypeMtd : extTypeMtds) {
                if (extTypeMtd == null) {
                    continue;
                }

                final IJstMethod toExtMethod = extTypeMtd;
                // skip super private methods
                if (toExtMethod.isPrivate()) {
                    continue;
                }

                IJstMethod candidateMtd = jstType.getMethod(toExtMethod
                        .getName().getName(), false, true);
                IJstType candidateType = jstType;

                if (candidateMtd != null) {
                    while (candidateType != null
                            && candidateType.getMethod(candidateMtd.getName()
                                    .getName(), false, false) != candidateMtd) {
                        candidateType = candidateType.getExtend();
                    }
                } else {
                    candidateType = null;
                }

                boolean overriden = false;
                IJstMethod implMethodFound = null;

                if (candidateMtd != null) {
                    final IJstMethod implMethod = candidateMtd;
                    if (implMethod.equals(toExtMethod)) {
                        overriden = false;
                    } else if (implMethod.isAbstract()) {
                        overriden = false;
                    } else {// if(isCompatibleMethod(toExtMethod, implMethod)){
                        overriden = true;
                        implMethodFound = implMethod;
                    }
                }
                if (overriden && implMethodFound != null) {
                    JstTypeWithArgs implType = null;

                    if (candidateType != null
                            && candidateType instanceof JstTypeWithArgs) {
                        implType = (JstTypeWithArgs) candidateType;
                    }
                    if (toExtMethod.isFinal()) {
                        final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(
                                implMethodFound.getName(),
                                ctx.getGroupId(),
                                new String[] { toExtMethod.getName().getName() });
                        validator
                                .satisfyRule(
                                        ctx,
                                        VjoSemanticRuleRepo.getInstance().FINAL_METHOD_SHOULD_NOT_BE_OVERRIDEN,
                                        ruleCtx);
                    }
                    if (toExtMethod.isPublic() && (!implMethodFound.isPublic())
                            || toExtMethod.isProtected()
                            && implMethodFound.isPrivate()) {
                        // reducing the visibility, report problem
                        final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(
                                implMethodFound.getName(),
                                ctx.getGroupId(),
                                new String[] { toExtMethod.getName().getName() });
                        validator
                                .satisfyRule(
                                        ctx,
                                        VjoSemanticRuleRepo.getInstance().OVERRIDE_METHOD_SHOULD_NOT_REDUCE_VISIBILITY,
                                        ruleCtx);
                    } else if (!isCompatibleMethod(typeWithArgs, implType,
                            toExtMethod, implMethodFound)) {
                        if (toExtMethod.isAbstract()
                                || extendType.isInterface()) {
                            final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(
                                    jstType, ctx.getGroupId(), new String[] {
                                            mixType.getName(),
                                            toExtMethod.getName().getName(),
                                            jstType.getName() });
                            validator
                                    .satisfyRule(
                                            ctx,
                                            VjoSemanticRuleRepo.getInstance().MIXIN_EXPECTS_INSTANCE_METHOD_MUST_BE_SATISFIED,
                                            ruleCtx);
                        } else {
                            // incompatible methods structure
                            final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(
                                    implMethodFound.getName(),
                                    ctx.getGroupId(),
                                    new String[] { toExtMethod.getName()
                                            .getName() });
                            validator
                                    .satisfyRule(
                                            ctx,
                                            VjoSemanticRuleRepo.getInstance().OVERRIDE_METHOD_SHOULD_HAVE_COMPATIBLE_SIGNATURE,
                                            ruleCtx);
                        }
                    }
                } else if (!overriden
                        && (toExtMethod.isAbstract()
                                || toExtMethod.getOwnerType().isInterface() || extendType
                                .isInterface())) {
                    // Added by Eric.Ma on 20100609 for mtype expect itype. but
                    // didn't implement method
                    final BaseVjoSemanticRuleCtx ruleCtx = new BaseVjoSemanticRuleCtx(
                            jstType, ctx.getGroupId(), new String[] {
                                    mixType.getName(),
                                    toExtMethod.getName().getName(),
                                    jstType.getName() });
                    validator
                            .satisfyRule(
                                    ctx,
                                    VjoSemanticRuleRepo.getInstance().MIXIN_EXPECTS_INSTANCE_METHOD_MUST_BE_SATISFIED,
                                    ruleCtx);
                    // End of added.
                }
            }
        }
        return true;
    }

    /**
     * <p>
     * checks if contract method is compatible with impl method including their
     * overloaded signatures
     * </p>
     * 
     * @param contract
     * @param impl
     * @return boolean
     */
    public static boolean isCompatibleMethod(
            JstTypeWithArgs typeWithArgsContract,
            JstTypeWithArgs typeWithArgsImpl, IJstMethod contract,
            IJstMethod impl) {
        final List<IJstMethod> contractOverloaded = new ArrayList<IJstMethod>();
        contractOverloaded.add(contract);
        contractOverloaded.addAll(contract.getOverloaded());

        out: for (IJstMethod contractMtd : contractOverloaded) {
            final List<IJstMethod> implOverloaded = impl.getOverloaded();
            if (isCompatibleMethodNoOverload(typeWithArgsContract,
                    typeWithArgsImpl, contractMtd, impl)) {
                continue out;
            }
            for (IJstMethod implMtd : implOverloaded) {
                if (isCompatibleMethodNoOverload(typeWithArgsContract,
                        typeWithArgsImpl, contractMtd, implMtd)) {
                    continue out;
                }
            }
            return false;
        }

        return true;
    }

    private static boolean isCompatibleMethodNoOverload(
            JstTypeWithArgs typeWithArgsContract,
            JstTypeWithArgs typeWithArgsImpl, IJstMethod contract,
            IJstMethod impl) {
        final List<JstArg> args = contract.getArgs();
        final List<JstArg> mArgs = impl.getArgs();
        if (args.size() != mArgs.size()) {
            // param compatibilities issue
            return false;
        }

        for (int i = 0, len = args.size(); i < len; i++) {
            final JstArg arg = args.get(i);
            final JstArg mArg = mArgs.get(i);

            IJstType argType = arg.getType();

            if (typeWithArgsContract != null && argType instanceof JstParamType) {
                argType = typeWithArgsContract
                        .getParamArgType((JstParamType) argType);
            }

            IJstType mArgType = mArg.getType();

            if (typeWithArgsImpl != null && mArgType instanceof JstParamType) {
                mArgType = typeWithArgsImpl
                        .getParamArgType((JstParamType) mArgType);
            }

            if (argType instanceof JstTypeWithArgs
                    && mArgType instanceof JstTypeWithArgs) {
                if (!TypeCheckUtil.isAssignable(typeWithArgsContract,
                        typeWithArgsImpl, (JstTypeWithArgs) argType,
                        (JstTypeWithArgs) mArgType)) {
                    return false;
                }
            }
            // if arg & mArg doesn't match
            else if (!TypeCheckUtil.isAssignable(argType, mArgType)) {
                return false;
            }
        }

        IJstType rtnType = contract.getRtnType();
        IJstType mRtnType = impl.getRtnType();

        if (typeWithArgsContract != null && rtnType instanceof JstParamType) {
            rtnType = typeWithArgsContract
                    .getParamArgType((JstParamType) rtnType);
        }

        if (typeWithArgsImpl != null && mRtnType instanceof JstParamType) {
            mRtnType = typeWithArgsImpl
                    .getParamArgType((JstParamType) mRtnType);
        }

        if (rtnType instanceof JstTypeWithArgs
                && mRtnType instanceof JstTypeWithArgs) {
            if (!TypeCheckUtil.isAssignable(typeWithArgsContract,
                    typeWithArgsImpl, (JstTypeWithArgs) rtnType,
                    (JstTypeWithArgs) mRtnType)) {
                return false;
            }
        } else if (!TypeCheckUtil.isAssignable(rtnType, mRtnType)) {
            // return type compatibilities issue
            return false;
        }

        return true;
    }
}
