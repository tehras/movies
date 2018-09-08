package com.github.tehras.animations

import android.animation.Animator
import android.view.animation.Animation

class AnimationListener(val onEnd: () -> Unit = {}, val onStart: () -> Unit = {}) : Animation.AnimationListener, Animator.AnimatorListener {
    override fun onAnimationRepeat(p0: Animator?) {}

    override fun onAnimationEnd(p0: Animator?) {
        onEnd()
    }

    override fun onAnimationStart(p0: Animator?) {
        onStart()
    }

    override fun onAnimationCancel(p0: Animator?) {
        onEnd()
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }

    override fun onAnimationEnd(p0: Animation?) {
        onEnd()
    }

    override fun onAnimationStart(p0: Animation?) {
        onStart()
    }

}