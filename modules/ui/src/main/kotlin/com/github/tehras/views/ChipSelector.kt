package com.github.tehras.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.github.tehras.styles.R
import kotlinx.android.synthetic.main.view_chip_selector.view.*
import kotlinx.android.synthetic.main.view_chip_selector_item.view.*

class ChipSelector @JvmOverloads constructor(
        context: Context, val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_chip_selector, this)
    }

    private var items: List<ChipItem> = mutableListOf()

    fun setChips(chipItems: List<ChipItem>) {
        chip_selector_group.removeAllViews()

        this.items = chipItems

        items.forEach {
            @Suppress("DEPRECATION")
            val chip = View.inflate(context, R.layout.view_chip_selector_item, null)
                    .apply {
                        val updateLayout: () -> Unit = {
                            chip_item_text.text = it.text

                            if (it.checked) {
                                chip_item_text.setBackgroundResource(R.drawable.bg_circle_selected)
                            } else {
                                chip_item_text.setBackgroundResource(R.drawable.bg_circle)
                            }
                        }

                        chip_selector_item.setOnClickListener { _ ->
                            // Toggle
                            it.selected()
                        }
                        updateLayout()
                    }

            chip_selector_group.addView(chip)

        }
    }

    data class ChipItem(val text: CharSequence, var checked: Boolean, val selected: () -> Unit)
}