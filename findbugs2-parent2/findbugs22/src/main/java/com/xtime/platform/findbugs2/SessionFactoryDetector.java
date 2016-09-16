package com.xtime.platform.findbugs2;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.Detector;
import edu.umd.cs.findbugs.ba.*;
import edu.umd.cs.findbugs.ba.constant.ConstantDataflow;
import edu.umd.cs.findbugs.ba.constant.ConstantFrame;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;

import java.util.Iterator;

/**
 * Find invalid calls to org.hibernate.SessionFactory
 * <p>
 * Copied from edu.umd.cs.findbugs.detect.DumbMethodInvocations
 * </p>
 * Created by rmohammed on 8/29/16.
 * Copyright (c) 2015 Xtime, Inc.  All rights reserved.
 */
public final class SessionFactoryDetector implements Detector {

    private final BugReporter _bugReporter;

    public SessionFactoryDetector(BugReporter bugReporter) {
        _bugReporter = bugReporter;
    }

    public void visitClassContext(ClassContext classContext) {
        Method[] methodList = classContext.getJavaClass().getMethods();

        for (Method method : methodList) {
            if (method.getCode() == null)
                continue;

            try {
                analyzeMethod(classContext, method);
            } catch (MethodUnprofitableException mue) {
                _bugReporter.logError("skipping unprofitable method in " + getClass().getName());
            } catch (CFGBuilderException e) {
                _bugReporter.logError("Detector " + getClass().getName() + " caught exception", e);
            } catch (DataflowAnalysisException e) {
                _bugReporter.logError("Detector " + getClass().getName() + " caught exception", e);
            }
        }
    }

    public void report() {
        // do nothing
    }

    private void analyzeMethod(ClassContext classContext, Method method) throws CFGBuilderException, DataflowAnalysisException {
        CFG cfg = classContext.getCFG(method);
        ConstantDataflow constantDataflow = classContext.getConstantDataflow(method);
        ConstantPoolGen cpg = classContext.getConstantPoolGen();
        MethodGen methodGen = classContext.getMethodGen(method);
        String sourceFile = classContext.getJavaClass().getSourceFileName();

        for (Iterator<Location> i = cfg.locationIterator(); i.hasNext(); ) {
            Location location = i.next();

            Instruction ins = location.getHandle().getInstruction();
            if (!(ins instanceof InvokeInstruction))
                continue;
            InvokeInstruction iins = (InvokeInstruction) ins;

            ConstantFrame frame = constantDataflow.getFactAtLocation(location);
            if (!frame.isValid()) {
                // This basic block is probably dead
                continue;
            }

            if (iins.getClassName(cpg).equals("org.hibernate.SessionFactory") && isInvalidMethod(iins.getName(cpg))) {
                _bugReporter.reportBug(new BugInstance(this, "HIBERNATE_SESSIONFACTORY_OPEN", HIGH_PRIORITY)
                        .addClassAndMethod(methodGen, sourceFile).addCalledMethod(cpg, iins).addSourceLine(classContext, method, location));
            }
        }
    }

    /**
     * Return true if the SessionFactory method name is invalid. Return false otherwise.
     *
     * @param methodName the method name (assumed not to be null)
     * @return Return true if the SessionFactory method name is invalid. Return false otherwise.
     */
    private boolean isInvalidMethod(String methodName) {
        return methodName.startsWith("open") || methodName.startsWith("with") || methodName.startsWith("close") || methodName.startsWith("evict") || methodName.equals("getCache");
    }
}
