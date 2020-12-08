package kz.kolesateam.confapp.events.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventApiData(
        @JsonProperty(value = "id")
        val id: Int?,
        @JsonProperty(value = "title")
        val title: String?,
        @JsonProperty(value = "startTime")
        val startTime: String?,
        @JsonProperty(value = "endTime")
        val endTime: String?,
        @JsonProperty(value = "description")
        val description: String?,
        @JsonProperty(value = "place")
        val place: String?,
        @JsonProperty(value = "speaker")
        val speaker: SpeakerApiData?
)