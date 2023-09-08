package com.mmaracic.httpclient.util.parser

import com.fasterxml.jackson.databind.ObjectMapper
import com.mmaracic.httpclient.model.HttpClientCollection

class CollectionParseException(path: List<String>, value: String?) :
    Exception("Error while parsing path ${path.joinToString("/")}. Found value: $value")

abstract class ExternalCollectionParser(private val objectMapper: ObjectMapper) {

    private fun getInitialMap(json: String): Map<String, *> =
        objectMapper.readValue(json, Map::class.java) as Map<String, *>

    protected fun <V> Map<String, V>.getAttribute(attribute: String): V? = this[attribute]

    protected fun <V> Map<String, V>.getMap(attribute: String): Map<String, *> = this[attribute] as Map<String, *>

    protected fun <V> Map<String, V>.getList(attribute: String): ArrayList<*> = this[attribute] as ArrayList<*>

    fun parse(json: String): HttpClientCollection {
        return parseInternal(getInitialMap(json))
    }

    fun supports(json: String): Boolean {
        return supports(getInitialMap(json))
    }

    abstract fun parseInternal(json: Map<String, *>): HttpClientCollection

    abstract fun supports(json: Map<String, *>): Boolean
}