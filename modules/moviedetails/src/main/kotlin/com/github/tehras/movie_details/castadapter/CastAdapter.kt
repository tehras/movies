package com.github.tehras.movie_details.castadapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.movie_details.R
import com.github.tehras.restapi.tmdb.models.cast.Cast
import com.jakewharton.rxrelay2.BehaviorRelay
import ext.android.view.inflateView
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy

@SuppressLint("CheckResult")
class CastAdapter : RecyclerView.Adapter<CastViewHolder>() {
    private val cast: MutableList<Cast> = mutableListOf()

    private val relay = BehaviorRelay.create<MutableList<Cast>>()
    fun consume(): Consumer<MutableList<Cast>> = relay.also { relay ->
        relay.subscribeBy {
            val animate = cast.isEmpty()

            cast.clear()
            cast.addAll(it)

            if (animate) {
                notifyItemRangeInserted(0, cast.size)
            } else {
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(parent.inflateView(R.layout.view_cast_item))
    }

    override fun getItemCount(): Int = cast.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(cast = cast[position])
    }

}