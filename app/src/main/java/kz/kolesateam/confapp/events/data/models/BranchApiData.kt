package kz.kolesateam.confapp.events.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class BranchApiData (
        @JsonProperty(value = "id")
    val id: Int?,
        @JsonProperty(value = "title")
    val title: String?,
        @JsonProperty(value = "events")
    val events: List<EventApiData>
)