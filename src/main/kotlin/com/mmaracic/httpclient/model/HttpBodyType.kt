package com.mmaracic.httpclient.model

import com.fasterxml.jackson.annotation.JsonValue

enum class HttpBodyType(
    @get:JsonValue val type: String
) {
    JSON("json");
}