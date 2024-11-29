package com.genesys.wiremock.repro.matcherbug

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.and
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath
import com.github.tomakehurst.wiremock.client.WireMock.matchingXPath
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.runBlocking

fun main() {
    val wireMockServer = WireMockServer()
    wireMockServer.start()

    stubFor(
        post(urlEqualTo("/send-email"))
            .withRequestBody(
                and(
                    matchingJsonPath("$.recipient", equalTo("test@example.com")),
                    matchingJsonPath(
                        "$.body",
                        matchingXPath("//a[@id='example-link']/@href", equalTo("https://example.com"))
                    )
                )
            )
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withBody("""{ "status": "success" }""")
            )
    )

    val response = runBlocking {
        HttpClient().use { client ->
            client.post("/send-email") {
                contentType(ContentType.Application.Json)
                setBody("""{
                    "recipient": "test@example.com",
                    "body": "<a id=\"example-link\" href=\"https://example.com\">Example link</a>"
                }""".replaceIndent())
            }
        }
    }

    assert(response.status.value == 200) {
        "Expected status code 200, but got ${response.status.value}"
    }
}
