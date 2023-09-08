package com.mmaracic.httpclient.util.parser

import com.mmaracic.httpclient.util.HttpCollectionUtils
import org.junit.Assert
import kotlin.test.Test

class PostmanCollectionParserTest {

    @Test
    fun `test-deserializing-postman-collection`() {
        val objectMapper = HttpCollectionUtils.getObjectMapper()
        val path = "/postman-collection-v2.json"

        val parser = PostmanCollectionParser(objectMapper)
        val json = HttpCollectionUtils.readFile(path)
        Assert.assertTrue(parser.supports(json))
        val collection = parser.parse(json)
        Assert.assertNotNull(collection)
        //Assert.assertEquals(4, collection.requests.size)
    }

}