/*
 * Copyright (c) 2018 Evernote Corporation. All rights reserved.
 */
package com.github.tehras.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.github.tehras.styles.R
import kotlinx.android.synthetic.main.rating_circle.view.*
import kotlin.math.roundToInt

/**
 * @author tkoshkin created on 8/28/18
 */
class RatingCircle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.rating_circle, this)

        rating_progress.progress = 0
    }

    @SuppressLint("SetTextI18n")
    fun setProgress(percent: Double) {
        rating_progress.progress = percent.roundToInt()
        rating_score.text = "%.1f".format(percent.div(10))
    }

}