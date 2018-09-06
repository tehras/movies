package com.github.tehras.boxoffice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.tehras.arch.viewModel
import com.github.tehras.commonviews.movies.MovieItems
import com.github.tehras.commonviews.movies.MoviesAdapter
import com.github.tehras.commonviews.movies.State
import com.github.tehras.dagger.components.findComponent
import com.github.tehras.moviedetails.MovieDetailsActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_box_office.*
import javax.inject.Inject

class BoxOfficeFragment : Fragment() {
    companion object {
        fun newInstance() = BoxOfficeFragment()
    }

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private val startDisposable: CompositeDisposable = CompositeDisposable()
    private val viewModel by viewModel<BoxOfficeViewModel> { factory }
    private val boxOfficeAdapter by lazy {
        MoviesAdapter(Consumer { movie ->
            this.activity?.let {
                MovieDetailsActivity.start(it, movie.id)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<BoxOfficeComponentCreator>()
                ?.plusBoxOfficeComponent()
                ?.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_box_office, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListView()
    }

    override fun onStart() {
        super.onStart()

        startDisposable += viewModel.observeState()
                .filter { it.state == BoxOfficeState.State.DONE }
                .map {
                    MovieItems(State.DONE, it.boxOfficeMovies)
                }
                .doOnError { MovieItems(State.ERROR, mutableListOf()) }
                .subscribe(boxOfficeAdapter.consume())
    }

    override fun onStop() {
        startDisposable.clear()

        super.onStop()
    }

    private fun initListView() {
        box_office_list_view.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = SlideInUpAnimator(DecelerateInterpolator())
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = boxOfficeAdapter
        }
    }

}