package com.github.tehras.moviedetails.castadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.tehras.models.tmdb.models.cast.Cast
import com.github.tehras.moviedetails.R
import com.github.tehras.restapi.IMAGE_URL_PROFILE
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

        Glide
                .with(cast_image)
                .load("$IMAGE_URL_PROFILE${cast.profilePath}")
                .apply(RequestOptions()
                        .placeholder(R.drawable.placeholder_cast)
                        .centerCrop()
                )
                .into(cast_image)

        cast_name.text = cast.name
        cast_role.text = cast.character
    }
}