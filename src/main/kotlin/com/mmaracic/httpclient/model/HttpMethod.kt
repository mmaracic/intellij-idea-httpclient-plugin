package com.mmaracic.httpclient.model

import com.fasterxml.jackson.annotation.JsonCreator

enum class HttpMethod (val hasBody: Boolean)
{
    GET(false),
    POST(true),
    PATCH(true);

    companion object {
        @JsonCreator
        fun fromName(name: String): HttpMethod {
            return HttpMethod.values().first { it.name == name }
        }
    }
}