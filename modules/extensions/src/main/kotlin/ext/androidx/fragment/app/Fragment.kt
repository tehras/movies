/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package ext.androidx.fragment.app

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

/**
 * @author tkoshkin created on 8/28/18
 */

fun Fragment.setToolbar(toolbar: Toolbar) {
    (this.activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
}