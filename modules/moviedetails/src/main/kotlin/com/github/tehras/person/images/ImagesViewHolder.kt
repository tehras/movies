package com.github.tehras.person.images

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.tehras.models.tmdb.models.cast.Image
import com.github.tehras.moviedetails.R
import com.github.tehras.restapi.IMAGE_URL_PROFILE
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_image_layout.*

class ImagesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(image: Image) {
        Glide.with(image_person)
                .load("$IMAGE_URL_PROFILE${image.filePath}")
                .apply(RequestOptions()
                        .placeholder(R.drawable.placeholder_cast)
                        .error(R.drawable.placeholder_cast)
                        .transform(RoundedCorners(16))
                        .fitCenter()
                )
                .into(image_person)
    }
}