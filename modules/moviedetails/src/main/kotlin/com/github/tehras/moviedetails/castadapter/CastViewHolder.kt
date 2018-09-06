package com.github.tehras.moviedetails.castadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.moviedetails.R
import com.github.tehras.restapi.tmdb.IMAGE_URL_PROFILE
import com.github.tehras.restapi.tmdb.models.cast.Cast
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_cast_item.*
import java.lang.ref.WeakReference

class CastViewHolder(override val containerView: View, private val castClicked: (Cast) -> Unit) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer,
        View.OnClickListener {
    private var lastCast: WeakReference<Cast>? = null
    override fun onClick(v: View?) {
        lastCast?.get()?.let(castClicked)
    }

    fun bind(cast: Cast) {
        lastCast = WeakReference(cast)
        cast_clickable_layout.setOnClickListener(this)

        Picasso.get()
                .load("$IMAGE_URL_PROFILE${cast.profilePath}")
                .transform(RoundedCornersTransformation(16, 0, RoundedCornersTransformation.CornerType.ALL))
                .error(R.drawable.placeholder_cast)
                .placeholder(R.drawable.placeholder_cast)
                .fit()
                .into(cast_image)

        cast_name.text = cast.name
        cast_role.text = cast.character
    }
}