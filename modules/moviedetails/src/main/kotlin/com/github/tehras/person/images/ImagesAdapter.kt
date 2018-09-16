package com.github.tehras.person.images

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.models.tmdb.models.cast.Image
import com.github.tehras.moviedetails.R
import com.jakewharton.rxrelay2.PublishRelay
import ext.android.view.inflateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer

class ImagesAdapter : RecyclerView.Adapter<ImagesViewHolder>() {

    private val images: MutableList<Image> = mutableListOf()
    private val relay = PublishRelay.create<MutableList<Image>>().also { relay ->
        relay
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    images.clear()
                    images.addAll(it)

                    notifyDataSetChanged()
                }
    }

    fun consume(): Consumer<MutableList<Image>> = relay

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder = ImagesViewHolder(parent.inflateView(R.layout.view_image_layout))
    override fun getItemCount(): Int = images.size
    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(image = images[position])
    }

}