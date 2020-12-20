package kz.kolesateam.confapp.common.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kz.kolesateam.confapp.utils.EVENTS_FIELD
import kz.kolesateam.confapp.utils.ID_FIELD
import kz.kolesateam.confapp.utils.TITLE_FIELD

@JsonIgnoreProperties(ignoreUnknown = true)
data class BranchApiData (
        @JsonProperty(value = ID_FIELD)
    val id: Int?,
        @JsonProperty(value = TITLE_FIELD)
    val title: String?,
        @JsonProperty(value = EVENTS_FIELD)
    val events: List<EventApiData>
)