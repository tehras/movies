package com.github.tehras.animations

import android.view.View

inline fun View.fadeIn(crossinline precondition: View.() -> Boolean = { true }, crossinline onStart: View.() -> Unit = {}) {
    if (this.precondition()) {
        this.alpha = 0f
        this.animate().alpha(1.0f).setListener(AnimationListener(onStart = {
            this.onStart()
        })).start()
    }
}

inline fun View.fadeOut(crossinline precondition: View.() -> Boolean = { true }, crossinline onEnd: View.() -> Unit = {}) {
    if (this.precondition()) {
        this.animate().alpha(0.0f).setListener(AnimationListener(onEnd = {
            this.onEnd()
        })).start()
    }
}