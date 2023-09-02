package com.mmaracic.httpclient.model

class HttpClientRequest (
    val name: String,
    val method: HttpMethod = HttpMethod.GET,
    val url: String,
    val headers: List<HttpClientHeader> = listOf(),
    val params: List<HttpClientRequestParam> = listOf()
) {
    val description: String? = null
    val body: HttpClientRequestBody? = null
}