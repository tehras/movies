


package com.github.tehras.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.github.tehras.styles.R
import ext.android.widget.isEllipsized
import kotlinx.android.synthetic.main.view_expandable_text_layout.view.*

/**
 * @author tkoshkin created on 8/29/18
 */
class ExpandableTextLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var isExpanded = false

    init {
        View.inflate(context, R.layout.view_expandable_text_layout, this)
    }

    private fun updateTextView() {
        if (isExpanded) {
            expandable_text.maxLines = 99
        } else {
            expandable_text.maxLines = 5
        }
    }

    fun setText(charSequence: CharSequence) {
        expandable_text.text = charSequence

        textUpdated()
    }

    private fun textUpdated() {
        isExpanded = false
        if (expandable_text.text.isEmpty()) {
            visibility = View.GONE
        } else {
            if (expandable_text.isEllipsized()) {
                expandable_click.setOnClickListener {
                    isExpanded = !isExpanded
                    updateTextView()
                }
            }
        }

        updateTextView()
    }
}