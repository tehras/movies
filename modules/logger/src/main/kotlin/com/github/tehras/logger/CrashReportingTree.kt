package com.github.tehras.logger

import android.util.Log
import android.util.Log.DEBUG
import com.microsoft.appcenter.utils.AppCenterLog
import timber.log.Timber

/**
 * @author tkoshkin created on 8/26/18
 */
class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        when (priority) {
            Log.ERROR -> AppCenterLog.error(tag, message, t)
            Log.INFO -> AppCenterLog.info(tag, message, t)
            Log.WARN -> AppCenterLog.warn(tag, message, t)
            Log.ASSERT -> AppCenterLog.logAssert(tag, message, t)
        }
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority > DEBUG
    }

}