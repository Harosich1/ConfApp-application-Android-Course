package kz.kolesateam.confapp.events.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SpeakerApiData (
        @JsonProperty(value = "id")
        val id: Int?,
        @JsonProperty(value = "fullName")
        val fullName: String?,
        @JsonProperty(value = "job")
        val job: String?
)