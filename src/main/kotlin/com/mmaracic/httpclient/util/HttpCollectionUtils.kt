package com.mmaracic.httpclient.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.mmaracic.httpclient.model.HttpClientCollection
import java.nio.file.Path

class HttpCollectionUtils {
    companion object {

        fun getObjectMapper(): ObjectMapper {
            return ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(JavaTimeModule())
                .registerModule(KotlinModule())
        }

        fun fromFile(path: String, objectMapper: ObjectMapper): HttpClientCollection {
            val content = String(this::class.java.getResourceAsStream(path)?.readAllBytes()!!)
            return objectMapper.readValue(content, HttpClientCollection::class.java)
        }

        fun toJson(objectMapper: ObjectMapper, collection: HttpClientCollection): String {
            return objectMapper.writeValueAsString(collection)
        }

        fun toFile(objectMapper: ObjectMapper, path: Path, collection: HttpClientCollection) {

            val content = toJson(objectMapper, collection)

        }
    }
}