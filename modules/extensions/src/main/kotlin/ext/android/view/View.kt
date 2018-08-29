/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package ext.android.view

import android.view.View

/**
 * @author tkoshkin created on 8/28/18
 */
fun View.visible() {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.invisible() {
    if (this.visibility != View.INVISIBLE) {
        this.visibility = View.INVISIBLE
    }
}