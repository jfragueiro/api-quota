package com.vicarius.apiquota.config;

import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
@EnableRetry
public class WebClientConfig {

    @Bean
    public WebClient webClient() {

        ConnectionProvider connProvider = ConnectionProvider
                .builder("webclient-conn-pool")
                .maxIdleTime(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.create(connProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                .responseTimeout(Duration.ofSeconds(40));

        WebClient webClient = WebClient.builder().clientConnector(
                new ReactorClientHttpConnector(httpClient)
        ).filters(exchangeFilterFunctions -> {
            exchangeFilterFunctions.add(WebClientFilter.logRequest());
        }).build();

        httpClient.warmup().block();
        return webClient;
    }

}
