package com.mmaracic.httpclient.util

import com.mmaracic.httpclient.model.HttpBodyType
import com.mmaracic.httpclient.model.HttpMethod
import org.junit.Assert
import org.junit.jupiter.api.Disabled
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
}