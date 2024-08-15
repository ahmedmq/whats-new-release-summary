package com.ahmedmq.whatsnew.release.summary.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory


@Configuration(proxyBeanMethods = false)
class TrackerClientConfig {

    @Bean
    fun restClient(@Value("\${tracker.api.baseUrl}") trackerBaseUrl: String): RestClient =
        RestClient.builder()
            .baseUrl(trackerBaseUrl)
            .build()

    @Bean
    fun trackerClient(restClient: RestClient): TrackerClient {
        val factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
        return factory.createClient(TrackerClient::class.java)
    }
}