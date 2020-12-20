package kz.kolesateam.confapp.eventDetails.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allEvents.presentation.BRANCH_ID
import kz.kolesateam.confapp.common.interactions.FavoriteListener
import kz.kolesateam.confapp.common.models.EventApiData
import kz.kolesateam.confapp.eventDetails.presentation.viewModel.EventDetailsViewModel
import kz.kolesateam.confapp.utils.DATE_OF_EVENT
import kz.kolesateam.confapp.utils.DEFAULT_BRANCH_ID
import kz.kolesateam.confapp.utils.extensions.getEventFormattedDateTime
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.ZonedDateTime

class EventDetailsActivity : AppCompatActivity(), FavoriteListener {

    private val eventDetailsViewModel: EventDetailsViewModel by viewModel()

    private lateinit var branchArrowTransition: ImageView
    private lateinit var eventTitle: TextView
    private lateinit var eventTimeAndAuditory: TextView
    private lateinit var nameOfSpeaker: TextView
    private lateinit var speakerJob: TextView
    private lateinit var eventDescription: TextView
    private lateinit var imageViewSpeakerPhoto: ImageView
    private lateinit var favoriteImageView: ImageView

    private lateinit var eventApiData: EventApiData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        initViews()
        observeUpcomingEventsViewModel()
        eventDetailsViewModel.onLaunch(intent.extras?.getInt(BRANCH_ID, DEFAULT_BRANCH_ID).toString())
    }

    private fun initViews() {
        branchArrowTransition = findViewById(R.id.event_details_activity_navigation_button)

        branchArrowTransition.setOnClickListener {
            finishActivity()
        }

        eventTitle = findViewById(R.id.title_of_event)
        eventTimeAndAuditory = findViewById(R.id.time_and_auditory)
        nameOfSpeaker = findViewById(R.id.name_of_speaker)
        speakerJob = findViewById(R.id.job_of_speaker)
        eventDescription = findViewById(R.id.event_details_text)
        imageViewSpeakerPhoto = findViewById(R.id.speaker_photo)
        favoriteImageView = findViewById(R.id.ic_in_favourite)
    }

    private fun onBindEvent(eventApiData: EventApiData) {

        eventTimeAndAuditory.text = eventApiData.dateOfEvent
        nameOfSpeaker.text = eventApiData.speaker?.fullName ?: ""
        speakerJob.text = eventApiData.speaker?.job
        eventTitle.text = eventApiData.title
        eventDescription.text = eventApiData.description

        Glide.with(imageViewSpeakerPhoto.context)
            .load(eventApiData.speaker?.photoUrl)
            .into(imageViewSpeakerPhoto)

        favoriteImageView.setImageResource(getFavouriteImageResource(eventApiData.isFavourite))
        setActionForChangeStateOfLikeButton(favoriteImageView)
    }

    private fun setActionForChangeStateOfLikeButton(iconInFavourite: ImageView) {

        iconInFavourite.setOnClickListener {

            eventApiData.isFavourite = !eventApiData.isFavourite

            iconInFavourite.setImageResource(getFavouriteImageResource(eventApiData.isFavourite))
            onFavouriteClick(eventApiData = eventApiData)
        }
    }

    private fun getFavouriteImageResource(
        isFavourite: Boolean
    ): Int = when (isFavourite) {
        true -> R.drawable.favorite_icon_filled
        else -> R.drawable.favourite_icon_not_filled
    }

    private fun finishActivity() {
        finish()
    }

    private fun observeUpcomingEventsViewModel() {
        eventDetailsViewModel.getEventDetailsLiveData().observe(this, ::showResult)
    }

    private fun showResult(eventApiData: EventApiData) {
        this.eventApiData = eventApiData
        onBindEvent(eventApiData)
    }

    override fun onFavouriteClick(eventApiData: EventApiData) {
        eventDetailsViewModel.onFavouriteClick(eventApiData)
    }
}