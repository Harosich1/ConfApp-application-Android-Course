package kz.kolesateam.confapp.common.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kz.kolesateam.confapp.utils.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventApiData(
    @JsonProperty(value = ID_FIELD)
    val id: Int?,
    @JsonProperty(value = TITLE_FIELD)
    val title: String?,
    @JsonProperty(value = START_TIME_FIELD)
    val startTime: String?,
    @JsonProperty(value = END_TIME_FIELD)
    val endTime: String?,
    @JsonProperty(value = DESCRIPTION_FIELD)
    val description: String?,
    @JsonProperty(value = PLACE_FIELD)
    val place: String?,
    @JsonProperty(value = SPEAKER_FIELD)
    val speaker: SpeakerApiData?
) {
    @JsonIgnore
    var isFavourite: Boolean = false
}
