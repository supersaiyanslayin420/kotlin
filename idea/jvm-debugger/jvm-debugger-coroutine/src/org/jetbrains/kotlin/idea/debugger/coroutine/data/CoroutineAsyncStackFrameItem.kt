/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.idea.debugger.coroutine.data

import com.intellij.debugger.engine.DebugProcessImpl
import com.intellij.debugger.jdi.StackFrameProxyImpl
import com.intellij.debugger.memory.utils.StackFrameItem
import com.intellij.debugger.ui.impl.watch.MethodsTracker
import com.intellij.debugger.ui.impl.watch.StackFrameDescriptorImpl
import com.intellij.xdebugger.frame.XNamedValue
import com.sun.jdi.Location
import org.jetbrains.kotlin.idea.debugger.*
import org.jetbrains.kotlin.idea.debugger.coroutine.util.logger

/**
 * Creation frame of coroutine either in RUNNING or SUSPENDED state.
 */
class CreationCoroutineStackFrameItem(
    val stackTraceElement: StackTraceElement,
    location: Location,
    val first: Boolean
) : CoroutineStackFrameItem(location, emptyList()) {
    override fun descriptor(frame: StackFrameProxyImpl) =
        RestoredStackFrameDescriptor(stackTraceElement, frame)

    override fun createFrame(debugProcess: DebugProcessImpl): CapturedStackFrame {
        return if (first)
            CreationCoroutineStackFrame(debugProcess, this)
        else
            super.createFrame(debugProcess)
    }
}

/**
 * Restored frame in SUSPENDED coroutine, not attached to any thread.
 */
class SuspendCoroutineStackFrameItem(
    val stackTraceElement: StackTraceElement,
    location: Location,
    spilledVariables: List<XNamedValue> = emptyList()
) : CoroutineStackFrameItem(location, spilledVariables) {

    override fun descriptor(frame: StackFrameProxyImpl) =
        RestoredStackFrameDescriptor(stackTraceElement, frame)
}

/**
 * Restored frame in RUNNING coroutine, attached to running thread. Frame references a 'preflight' or 'exit' frame.
 */
class RestoredCoroutineStackFrameItem(
    val frame: StackFrameProxyImpl,
    location: Location,
    spilledVariables: List<XNamedValue>
) : CoroutineStackFrameItem(location, spilledVariables) {

    fun descriptor() =
        descriptor(frame)

    override fun descriptor(frame: StackFrameProxyImpl) =
        StackFrameDescriptorImpl(frame, MethodsTracker())
}

/**
 * Restored from memory dump
 */
class DefaultCoroutineStackFrameItem(location: Location, spilledVariables: List<XNamedValue>) :
    CoroutineStackFrameItem(location, spilledVariables) {

    override fun descriptor(frame: StackFrameProxyImpl) =
        StackFrameDescriptorImpl(frame, MethodsTracker())
}

/**
 * Original frame appeared before resumeWith call.
 *
 * Sequence is the following
 *
 * - KotlinStackFrame
 * - invokeSuspend(KotlinStackFrame) -|
 *                                    | replaced with CoroutinePreflightStackFrame
 * - resumeWith(KotlinStackFrame) ----|
 * - Kotlin/JavaStackFrame -> PreCoroutineStackFrameItem : CoroutinePreflightStackFrame.threadPreCoroutineFrames
 *
 */
class PreCoroutineStackFrameItem(val frame: StackFrameProxyImpl, location: Location, variables: List<XNamedValue> = emptyList()) :
    CoroutineStackFrameItem(location, variables) {
    constructor(frame: StackFrameProxyImpl, variables: List<XNamedValue> = emptyList()) : this(frame, frame.location(), variables)

    constructor(frame: StackFrameProxyImpl, restoredCoroutineStackFrameItem: CoroutineStackFrameItem) : this(
        frame,
        restoredCoroutineStackFrameItem.location,
        restoredCoroutineStackFrameItem.spilledVariables
    )

    override fun createFrame(debugProcess: DebugProcessImpl): CapturedStackFrame {
        return PreCoroutineStackFrame(frame, debugProcess, this)
    }
}

class RunningCoroutineStackFrameItem(
    val frame: StackFrameProxyImpl,
    spilledVariables: List<XNamedValue> = emptyList()
) : CoroutineStackFrameItem(frame.location(), spilledVariables)

sealed class CoroutineStackFrameItem(val location: Location, val spilledVariables: List<XNamedValue>) :
    StackFrameItem(location, spilledVariables) {
    val log by logger

    fun uniqueId(): String {
        return location.safeSourceName() + ":" + location.safeMethod().toString() + ":" +
                location.safeLineNumber() + ":" + location.safeKotlinPreferredLineNumber()
    }

    override fun createFrame(debugProcess: DebugProcessImpl): CapturedStackFrame {
        return CoroutineStackFrame(debugProcess, this)
    }

    open fun descriptor(frame: StackFrameProxyImpl): StackFrameDescriptorImpl? =
        null
}

class RestoredStackFrameDescriptor(val frame: StackTraceElement, proxy: StackFrameProxyImpl) :
    StackFrameDescriptorImpl(proxy, MethodsTracker())
