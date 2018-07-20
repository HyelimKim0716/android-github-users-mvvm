package com.githubapi.search.searchgithubusers.common

import android.text.TextUtils
import android.util.Log

object LogMgr {
    fun getFunction(): String {
        val stackTraceElements = Throwable().stackTrace
        val splitter = TextUtils.SimpleStringSplitter('.').apply {
            setString(stackTraceElements[1].fileName)
        }

        val function = StringBuilder(splitter.iterator().next())
                .append(".")
                .append(stackTraceElements[1].methodName)
                .append("()")
        return function.toString()
    }

    fun d(message: String) {
        Log.d(getFunction(), message)
    }

    fun e(message: String) {
        Log.e(getFunction(), message)
    }
}