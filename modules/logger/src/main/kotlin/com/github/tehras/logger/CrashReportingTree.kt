package com.github.tehras.logger

import android.util.Log
import android.util.Log.INFO
import timber.log.Timber

/**
 * @author tkoshkin created on 8/26/18
 */
class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        // TODO send to Crashlytics
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority > INFO
    }

}