/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.dagger.components

import android.app.Activity
import androidx.fragment.app.Fragment
import com.github.tehras.dagger.DaggerApplication

/**
 * @author tkoshkin created on 8/24/18
 */
object Components {
    fun find(fragment: Fragment): AppComponent? {
        return (fragment.context?.applicationContext as? DaggerApplication)?.getComponent()
    }

    fun find(activity: Activity): AppComponent? {
        return (activity.application as? DaggerApplication)?.getComponent()
    }
}