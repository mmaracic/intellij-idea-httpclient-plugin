package com.mmaracic.httpclient.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.nio.file.Path
import java.time.OffsetDateTime

class HttpClientCollection(
    @JsonProperty("collectionName")
    val name: String,
    val client: String = "HttpClient",
    val requests: List<HttpClientRequest> = listOf()
) {
    val path: Path? = null
    val dateExported: OffsetDateTime? = null
}