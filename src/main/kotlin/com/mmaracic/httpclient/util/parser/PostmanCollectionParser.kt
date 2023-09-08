package com.mmaracic.httpclient.util.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.mmaracic.httpclient.model.HttpClientCollection

class PostmanCollectionParser(objectMapper: ObjectMapper) : ExternalCollectionParser(objectMapper) {

    companion object {
        private const val INFO: String = "info"
        private const val VERSION: String = "version"
        private const val NAME: String = "name"
        private const val MAJOR: String = "major"
        private const val ITEM: String = "item"
    }

    override fun supports(json: Map<String, *>): Boolean {
        val info = json.getMap(INFO)
        return if (info.isNotEmpty()) {
            val major = info.getMap(VERSION).getAttribute(MAJOR)
            val name = info.getAttribute(NAME) as String?
            ("2" == major && name != null)
        } else {
            false
        }
    }

    override fun parseInternal(json: Map<String, *>): HttpClientCollection {
        val info = json.getMap(INFO)
        if (info.isNotEmpty()) {
            val major = info.getMap(VERSION).getAttribute(MAJOR)
            val name = info.getAttribute(NAME) as String?
            if ("2" == major && name != null) {
                val collection = HttpClientCollection(name)
                val item = json.getList(ITEM)
                return collection
            } else {
                throw Exception("Version of the postman collection not supported: $major or name undefined: $name")
            }
        } else {
            throw CollectionParseException(listOf(), INFO)
        }
    }
}
