package com.github.tehras.moviedetails.castadapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.tehras.moviedetails.R
import com.github.tehras.models.tmdb.models.cast.Cast
import com.jakewharton.rxrelay2.PublishRelay
import ext.android.view.inflateView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.subscribeBy

@SuppressLint("CheckResult")
class CastAdapter : RecyclerView.Adapter<CastViewHolder>() {
    private val cast: MutableList<Cast> = mutableListOf()

    private val actions = PublishRelay.create<Action>()
    fun observeActions(): Observable<Action> = actions

    private val relay = PublishRelay.create<MutableList<Cast>>()
    fun consume(): Consumer<MutableList<Cast>> = relay.also { relay ->
        relay.observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    cast.clear()
                    cast.addAll(it)

                    notifyDataSetChanged()
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(parent.inflateView(R.layout.view_cast_item)) { cast ->
            Observable
                    .just(cast)
                    .map { Action.CastSelected(it) }
                    .subscribe(actions)
        }
    }

    override fun getItemCount(): Int = cast.size

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(cast = cast[position])
    }
}

sealed class Action {
    data class CastSelected(val cast: Cast) : Action()
}