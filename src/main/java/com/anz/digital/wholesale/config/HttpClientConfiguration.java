package com.anz.digital.wholesale.config;

import com.anz.digital.wholesale.util.AnzLogger;
import com.anz.digital.wholesale.util.LoggerConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfiguration {

    private static final AnzLogger logger = AnzLogger.getLogger(HttpClientConfiguration.class);

    @Value("${client.connection.timeout:8000}")
    private int connectionTimeOut;

    @Value("${client.read.timeout:180000}")
    private int readTimeOut;

    @Bean
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {

        final HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(connectionTimeOut);
        logger.debug(
                LoggerConstants.AnzMarker.FLOW,
                "Connection Timeout value set for http request: {}",
                connectionTimeOut);

        factory.setReadTimeout(readTimeOut);
        logger.debug(
                LoggerConstants.AnzMarker.FLOW,
                "Connection Read Timeout value set for http request: {}",
                readTimeOut);

        return factory;
    }

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(
            final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory) {

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);

        return restTemplate;
    }
}
