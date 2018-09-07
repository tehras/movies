package com.github.tehras.moviedetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.tehras.arch.viewModelActivity
import com.github.tehras.dagger.components.findComponent
import com.github.tehras.moviedetails.castadapter.Action
import com.github.tehras.moviedetails.castadapter.CastAdapter
import com.github.tehras.person.PersonActivity
import com.github.tehras.restapi.tmdb.IMAGE_URL_LARGE
import com.github.tehras.restapi.tmdb.models.moviedetails.MovieDetails
import com.squareup.picasso.Picasso
import ext.android.view.gone
import ext.android.view.visible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_description.*
import kotlinx.android.synthetic.main.content_movie_details.*
import javax.inject.Inject

/**
 * @author tkoshkin created on 8/29/18
 */
class MovieDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModelActivity<MovieDetailsViewModel> { factory }

    private val castAdapter by lazy { CastAdapter() }

    private val startDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<MovieDetailsComponentCreator>()
                ?.plusMovieDetailsActivity()
                ?.movieId(intent.movieId)
                ?.build()
                ?.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        initToolbar()
        initRecyclerViews()
    }

    override fun onStart() {
        super.onStart()

        startDisposable += viewModel
                .observeState()
                .filter { it.state == MovieDetailsState.State.DONE && it.movieDetails != null }
                .map { it.movieDetails!! }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieDetailsConsumer)

        startDisposable += viewModel
                .observeState()
                .filter { it.cast.isNotEmpty() }
                .map { it.cast }
                .subscribe(castAdapter.consume())

        startDisposable += viewModel
                .observeState()
                .filter { it.movieDetails != null }
                .doOnNext {
                    if (it.movieDetails?.voteCount ?: 0.0 == 0.0) {
                        movie_reviews.gone()
                        movie_reviews_title.gone()
                    } else {
                        movie_reviews.visible()
                        movie_reviews_title.visible()
                    }
                }
                .map { Pair(it.movieDetails!!, it.reviews) }
                .subscribe(movie_reviews.consume())

        startDisposable += castAdapter.observeActions()
                .ofType(Action.CastSelected::class.java)
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.cast }
                .subscribeBy {
                    PersonActivity.start(this, it.id)
                }
    }

    override fun onStop() {
        startDisposable.clear()

        super.onStop()
    }

    private fun showLoading() {
        // TODO
    }

    private fun showError() {
        // TODO
    }

    private val movieDetailsConsumer: Consumer<MovieDetails> = Consumer { movie ->
        movie_details_toolbar.title = movie.title
        collapsing_toolbar_layout.title = movie.title

        movie_description.setText(movie.overview!!)

        Picasso.get()
                .load("$IMAGE_URL_LARGE${movie.backdropPath}")
                .into(background_image)
    }

    private fun initRecyclerViews() {
        movie_cast.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MovieDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = SlideInRightAnimator()
            adapter = castAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun initToolbar() {
        movie_details_toolbar?.run {
            setSupportActionBar(movie_details_toolbar)
            movie_details_toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        fun start(activity: Activity, id: Long) {
            activity.startActivity(
                    Intent(activity, MovieDetailsActivity::class.java).apply { movieId = id }
            )
        }
    }
}

var Intent.movieId: Long
    set(value) {
        putExtra("movie_details_extra_id", value)
    }
    get() {
        return getLongExtra("movie_details_extra_id", -1L)
    }