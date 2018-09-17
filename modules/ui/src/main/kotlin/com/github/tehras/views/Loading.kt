package com.github.tehras.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.github.tehras.styles.R
import ext.android.view.gone
import ext.android.view.visible
import kotlinx.android.synthetic.main.view_loading.view.*

class Loading @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_loading, this)
    }

    fun start() {
        visible()

        loading_animation.progress = 0.0f
        loading_animation.playAnimation()
    }

    fun stop() {
        gone()

        loading_animation.cancelAnimation()
    }
}