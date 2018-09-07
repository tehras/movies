package com.github.tehras.person.images

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.person.R
import com.github.tehras.restapi.tmdb.IMAGE_URL_PROFILE
import com.github.tehras.restapi.tmdb.models.cast.Image
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_image_layout.*

class ImagesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(image: Image) {
        Picasso.get()
                .load("$IMAGE_URL_PROFILE${image.filePath}")
                .transform(RoundedCornersTransformation(16, 0, RoundedCornersTransformation.CornerType.ALL))
                .placeholder(R.drawable.placeholder_cast)
                .fit()
                .into(image_person)
    }
}