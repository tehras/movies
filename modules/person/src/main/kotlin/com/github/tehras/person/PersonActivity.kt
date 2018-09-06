package com.github.tehras.person

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.tehras.arch.viewModelActivity
import com.github.tehras.dagger.components.findComponent
import com.github.tehras.restapi.tmdb.IMAGE_URL_PROFILE
import com.github.tehras.restapi.tmdb.models.cast.Person
import com.squareup.picasso.Picasso
import ext.android.view.gone
import ext.kotlin.toAge
import ext.kotlin.toDate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_person_layout.*
import kotlinx.android.synthetic.main.content_person_layout.*
import javax.inject.Inject

class PersonActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel by viewModelActivity<PersonViewModel> { factory }
    private val startDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        findComponent<PersonComponentCreator>()
                ?.plusPersonActivity()
                ?.personId(intent.personId)
                ?.build()
                ?.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_person_layout)

        initToolbar()
    }

    override fun onStart() {
        super.onStart()

        startDisposable += viewModel.observeState()
                .filter { it.state == PersonState.State.SUCCESS }
                .map { it.person }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { person ->
                    person?.let {
                        populatePersonInfo(it)
                    }
                }
    }

    override fun onStop() {
        startDisposable.clear()

        super.onStop()
    }

    private fun initToolbar() {
        person_toolbar.setOnClickListener { finish() }
    }

    @SuppressLint("SetTextI18n")
    private fun populatePersonInfo(person: Person) {
        Picasso.get()
                .load("$IMAGE_URL_PROFILE${person.profilePath}")
                .transform(RoundedCornersTransformation(16, 0))
                .error(R.drawable.placeholder_cast)
                .placeholder(R.drawable.placeholder_cast)
                .into(person_image)

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