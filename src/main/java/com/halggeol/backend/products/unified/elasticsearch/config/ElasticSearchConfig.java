package com.halggeol.backend.products.unified.elasticsearch.config;

import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost(host, port, "https"))
            .setHttpClientConfigCallback(httpClientBuilder -> {
                BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));
                return httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
            })
            .build();
        ElasticsearchTransport transport = new RestClientTransport(
            restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);

    }
}