package kz.kolesateam.confapp.events.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kz.kolesateam.confapp.utils.FULL_NAME_FIELD
import kz.kolesateam.confapp.utils.ID_FIELD
import kz.kolesateam.confapp.utils.JOB_FIELD
import kz.kolesateam.confapp.utils.PHOTO_URL_FIELD

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpeakerApiData (
        @JsonProperty(value = ID_FIELD)
        val id: Int?,
        @JsonProperty(value = FULL_NAME_FIELD)
        val fullName: String?,
        @JsonProperty(value = JOB_FIELD)
        val job: String?
)