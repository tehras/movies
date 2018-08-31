


package com.github.tehras.views

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import com.github.tehras.styles.R
import kotlinx.android.synthetic.main.view_genre_outline.view.*

/**
 * @author tkoshkin created on 8/28/18
 */
class GenreOutline @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_genre_outline, this)

        elevation = context.resources.getDimensionPixelSize(R.dimen.elevation_standard).toFloat()
    }

    fun setText(charSequence: CharSequence) {
        genre_title.text = charSequence
    }

    fun setTint(@ColorRes color: Int) {
        @Suppress("DEPRECATION")
        genre_background.background.colorFilter = PorterDuffColorFilter(context.resources.getColor(color), PorterDuff.Mode.SRC)
    }

}