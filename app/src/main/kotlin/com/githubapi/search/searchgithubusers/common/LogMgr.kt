package com.githubapi.search.searchgithubusers.common

import android.text.TextUtils
import android.util.Log

object LogMgr {
    fun getFunction(): String {
        val stringBuilder = StringBuilder("SearchGithubUsers/")
        var stackTrace: StackTraceElement? = null
        var simpleClassName = ""

        for (i in 5..7) {
            stackTrace = Thread.currentThread().stackTrace[i]
            simpleClassName = stackTrace.className.substringAfterLast(".").substringBefore("$")
            if (simpleClassName != this::class.java.simpleName) break
        }

//        if (customTag.isNotEmpty()) {
//            stringBuilder.append("[$customTag] ")
//        }
//
//        if (isShowThreadName) {
//            stringBuilder.append("${Thread.currentThread().name}: ")
//        }

        stringBuilder.append("$simpleClassName > " +
                "${stackTrace?.methodName}:${stackTrace?.lineNumber}")


        return stringBuilder.toString()
    }

    fun d(message: String = "called") {
        Log.d(getFunction(), message)
    }

    fun e(message: String = "called") {
        Log.e(getFunction(), message)
    }
}