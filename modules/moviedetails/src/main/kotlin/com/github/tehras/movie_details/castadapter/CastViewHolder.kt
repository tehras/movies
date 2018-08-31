package com.github.tehras.movie_details.castadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.restapi.tmdb.IMAGE_URL_PROFILE
import com.github.tehras.restapi.tmdb.models.cast.Cast
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_cast_item.*

class CastViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(cast: Cast) {
        Picasso.get()
                .load("$IMAGE_URL_PROFILE${cast.profilePath}")
                .into(cast_image)

        cast_name.text = cast.name
        cast_role.text = cast.character
    }
}