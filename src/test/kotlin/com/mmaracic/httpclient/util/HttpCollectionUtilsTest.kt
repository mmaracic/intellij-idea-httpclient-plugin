package com.mmaracic.httpclient.util

import com.mmaracic.httpclient.model.HttpBodyType
import com.mmaracic.httpclient.model.HttpMethod
import org.junit.Assert
import org.junit.jupiter.api.Disabled
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.test.Test

class HttpCollectionUtilsTest {

    @Test
    fun `test-deserializing-thunder-collection`() {
        val objectMapper = HttpCollectionUtils.getObjectMapper()
        val path = "/thunder-collection_test.json"

        val collection = HttpCollectionUtils.fromFile(path, objectMapper)
        Assert.assertNotNull(collection)
        Assert.assertEquals(4, collection.requests.size)

        Assert.assertEquals("http://localhost:7071/api/HttpTriggerJava1?test=test", collection.requests[0].name)
        Assert.assertEquals("http://localhost:7071/api/HttpTriggerJava1?name=test", collection.requests[0].url)
        Assert.assertEquals(HttpMethod.GET, collection.requests[0].method)
        Assert.assertEquals(1, collection.requests[0].params.size)
        Assert.assertEquals("name", collection.requests[0].params[0].name, "name")
        Assert.assertEquals("test", collection.requests[0].params[0].value)

        Assert.assertEquals(HttpMethod.POST, collection.requests[3].method)
        Assert.assertNotNull(collection.requests[3].body)
        Assert.assertEquals(HttpBodyType.JSON, collection.requests[3].body?.type)
        Assert.assertEquals(
            "{\n  \"title\": \"Test message\",\n  \"description\": \"Today is a nice day\"\n}",
            collection.requests[3].body?.raw
        )
    }

    @Test
    @Disabled("Work in progress")
    fun `test-deserializing-postman-collection`() {
        val objectMapper = HttpCollectionUtils.getObjectMapper()
        val path = "/postman-collection-v2.json"

        val collection = HttpCollectionUtils.fromFile(path, objectMapper)
        Assert.assertNotNull(collection)
        Assert.assertEquals(4, collection.requests.size)
    }

    @Test
    fun `test-serializing-thunder-collection`() {
        val objectMapper = HttpCollectionUtils.getObjectMapper()
        val path = "/thunder-collection_test.json"
        val startJson = String(this::class.java.getResourceAsStream(path)?.readAllBytes()!!)
        val startMap = objectMapper.readValue(startJson, Map::class.java)

        val collection = HttpCollectionUtils.fromFile(path, objectMapper)
        val resultJson = HttpCollectionUtils.toJson(objectMapper, collection)
        val resultMap = objectMapper.readValue(resultJson, Map::class.java)

        Assert.assertEquals(startMap["collectionName"], resultMap["collectionName"])
        Assert.assertEquals(startMap["client"], resultMap["client"])
        Assert.assertEquals(startMap["dateExported"], resultMap["dateExported"])

        val resultRequestList = resultMap["requests"] as ArrayList<*>
        val startRequestList = startMap["requests"] as ArrayList<*>
        Assert.assertEquals(startRequestList.size, resultRequestList.size)
        Assert.assertEquals(4, resultRequestList.size)

        compareThunderRequests(startRequestList[0] as Map<*, *>, resultRequestList[0] as Map<*, *>)
        compareThunderRequests(startRequestList[3] as Map<*, *>, resultRequestList[3] as Map<*, *>)
    }

    private fun compareThunderRequests(startRequest: Map<*, *>, resultRequest: Map<*, *>) {
        Assert.assertEquals(startRequest["name"], resultRequest["name"])
        Assert.assertEquals(startRequest["method"], resultRequest["method"])
        Assert.assertEquals(startRequest["url"], resultRequest["url"])
        Assert.assertEquals(startRequest["description"], resultRequest["description"])

        val startParams = startRequest["params"] as? ArrayList<*>
        val resultParams = resultRequest["params"] as? ArrayList<*>
        compareListOfKeyValuesPairs(startParams, resultParams, "Params")

        val startBody = startRequest["body"] as? Map<*, *>
        val resultBody = resultRequest["body"] as? Map<*, *>

        if (startBody != null && resultBody != null) {
            Assert.assertEquals(startBody["type"], resultBody["type"])
            Assert.assertEquals(startBody["raw"], resultBody["raw"])
        } else if (startBody == null && resultBody == null) {
        } else {
            throw Exception("Null in one side in body")
        }
    }

    private fun compareListOfKeyValuesPairs(startList: ArrayList<*>?, resultList: ArrayList<*>?, path: String) {
        if (startList != null && resultList != null) {
            Assert.assertEquals(path, startList.size, resultList.size)
            startList.zip(resultList).forEach { pair ->
                val startPair = pair.first as Map<*, *>
                val resultPair = pair.second as Map<*, *>

                Assert.assertEquals(path, startPair["name"], resultPair["name"])
                Assert.assertEquals(path, startPair["value"], resultPair["value"])
            }
        } else if (startList == null && resultList == null) {
        } else {
            throw Exception("Null in one side in $path")
        }
    }
}