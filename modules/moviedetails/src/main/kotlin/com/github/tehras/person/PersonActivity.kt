package com.github.tehras.person

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.tehras.animations.fadeIn
import com.github.tehras.animations.fadeOut
import com.github.tehras.arch.viewModelActivity
import com.github.tehras.dagger.components.findComponent
import com.github.tehras.models.tmdb.models.cast.Person
import com.github.tehras.moviedetails.MovieDetailsActivity
import com.github.tehras.moviedetails.R
import com.github.tehras.person.images.ImagesAdapter
import com.github.tehras.person.moviecredits.MovieCreditsAdapter
import com.github.tehras.restapi.IMAGE_URL_PROFILE
import com.github.tehras.restapi.IMAGE_URL_SMALL
import com.github.tehras.views.ChipSelector
import ext.android.view.gone
import ext.android.view.invisible
import ext.android.view.isVisible
import ext.android.view.visible
import ext.kotlin.toAge
import ext.kotlin.toDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_person_layout.*
import kotlinx.android.synthetic.main.content_person_layout.*
import timber.log.Timber
import javax.inject.Inject

class PersonActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModelActivity<PersonViewModel> { factory }
    private val startDisposable = CompositeDisposable()

    private val imagesAdapter by lazy { ImagesAdapter() }
    private val movieCreditsAdapter by lazy { MovieCreditsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<PersonComponentCreator>()
                ?.plusPersonActivity()
                ?.personId(intent.personId)
                ?.build()
                ?.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_person_layout)

        initToolbar()
        initImagesView()
        initMovieCredits()
    }

    override fun onStart() {
        super.onStart()

        startDisposable += viewModel
                .observeState()
                .filter { it.state == PersonState.State.SUCCESS && it.person != null }
                .map { it.person }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { person ->
                    person?.let {
                        populatePersonInfo(it)
                    }
                }

        startDisposable += viewModel
                .observeState()
                .filter { it.state == PersonState.State.SUCCESS && it.images.profiles.isNotEmpty() }
                .map { it.images.profiles.toMutableList() }
                .subscribe(imagesAdapter.consume())

        startDisposable += viewModel
                .observeState()
                .filter { it.state == PersonState.State.SUCCESS && it.credits.isNotEmpty() }
                .map { it.credits.selectedCredits }
                .subscribe(movieCreditsAdapter.consume())

        startDisposable += viewModel
                .observeState()
                .filter { it.state == PersonState.State.SUCCESS && it.credits.tags.isNotEmpty() }
                .map { it.credits }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    updateTags(it.tags)
                }

        startDisposable += movieCreditsAdapter
                .handleUiEvents()
                .subscribe {
                    MovieDetailsActivity.start(this, it.movieId)
                }

        startDisposable += viewModel
                .observeState()
                .map { it.state }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loading(it == PersonState.State.LOADING)
                }
    }

    override fun onStop() {
        startDisposable.clear()

        super.onStop()
    }

    fun loading(show: Boolean) {
        if (show) {
            loading_layout.start()
        } else {
            loading_layout.stop()
        }
    }

    private fun updateTags(tags: MutableList<Credits.Tags>) {
        person_movie_credits_categories.setChips(
                tags.map {
                    ChipSelector.ChipItem(it.displayName, it.selected) {
                        Timber.d("Tag toggled $it")
                        viewModel
                                .consume()
                                .accept(PersonUiEvent.TagUpdate(it, !it.selected))
                    }
                }
        )
    }

    private fun initToolbar() {
        person_toolbar.setOnClickListener { finish() }
    }

    private fun initImagesView() {
        person_images.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PersonActivity, LinearLayoutManager.HORIZONTAL, false)
            isNestedScrollingEnabled = false
            itemAnimator = SlideInRightAnimator()
            adapter = imagesAdapter
        }
    }

    private fun initMovieCredits() {
        person_movie_credits.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PersonActivity)
            isNestedScrollingEnabled = false
            itemAnimator = SlideInUpAnimator()
            adapter = movieCreditsAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populatePersonInfo(person: Person) {
        Glide.with(person_image)
                .load("$IMAGE_URL_PROFILE${person.profilePath}")
                .apply(RequestOptions()
                        .transform(RoundedCorners(16))
                        .placeholder(R.drawable.placeholder_cast)
                        .error(R.drawable.placeholder_cast)
                )
                .into(person_image)

        Glide.with(person_toolbar_image)
                .load("$IMAGE_URL_SMALL${person.profilePath}")
                .apply(RequestOptions()
                        .transform(RoundedCorners(16))
                        .placeholder(R.drawable.placeholder_cast)
                        .error(R.drawable.placeholder_cast)
                )
                .into(person_toolbar_image)

        person_toolbar_title.text = person.name
        person_name.text = person.name
        person_bio.setText(person.biography)
        person_age.text = calculateAge(person.birthday)

        person.placeOfBirth?.let {
            person_place_of_birth.text = "Born in $it"
        }

        person.deathday?.let {
            person_death.text = "Died on ${it.toDate()}"
        } ?: kotlin.run {
            person_death.gone()
        }

        val imageHeight = resources.getDimensionPixelSize(R.dimen.movie_image_height)
        person_scroll_view.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY > imageHeight) {
                person_toolbar_title.fadeIn({ !isVisible() }) { visible() }
                person_toolbar_image.fadeIn({ !isVisible() }) { visible() }
            } else {
                person_toolbar_title.fadeOut({ isVisible() }) { invisible() }
                person_toolbar_image.fadeOut({ isVisible() }) { invisible() }
            }
        }
    }

    private fun calculateAge(birthday: String?): CharSequence? {
        return if (birthday == null) {
            "Not Available"
        } else {
            "Age: ${birthday.toAge()}"
        }
    }

    companion object {
        fun start(activity: Activity, id: Long) {
            activity.startActivity(
                    Intent(activity, PersonActivity::class.java).apply { personId = id }
            )
        }
    }
}

var Intent.personId: Long
    set(value) {
        putExtra("extra_person_id", value)
    }
    get() {
        return getLongExtra("extra_person_id", -1L)
    }