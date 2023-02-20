package com.anz.digital.wholesale.config;

import com.anz.digital.wholesale.exception.ApiCallException;
import com.anz.digital.wholesale.exception.NoDataFoundException;
import com.anz.digital.wholesale.exception.TransactionException;
import com.anz.digital.wholesale.util.AnzLogger;
import com.anz.digital.wholesale.util.LoggerConstants;
import com.anz.digital.wholesale.util.TransactionErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.anz.digital.wholesale.util.ApplicationConstants.*;


@Service
public class RestApiClient {

    private static final AnzLogger logger = AnzLogger.getLogger(RestApiClient.class);

    @Autowired
    @Qualifier(value = "restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    public <T, U> T callRestApi(
            final U request, final Class<T> responseType, final HttpMethod httpMethod, final String uri)
            throws Exception {

        final Map<String, String> threadContextMDCMap = new LinkedHashMap<>();

        final HttpEntity<U> requestEntity;
        if (null == request) {
            requestEntity = new HttpEntity<>(getDefaultHttpHeaders());
        } else {
            requestEntity = new HttpEntity<>(request, getDefaultHttpHeaders());
            threadContextMDCMap.put(HTTP_BODY, requestEntity.getBody().toString());
        }

        threadContextMDCMap.put(HTTP_METHOD, httpMethod.toString());
        threadContextMDCMap.put(HTTP_URI, uri);
        threadContextMDCMap.put(HTTP_REQUEST, requestEntity.toString());

        logger.info(LoggerConstants.PrexMarker.FLOW, "Http Information is {} ", threadContextMDCMap);

        final ResponseEntity<T> responseEntity;

        try {

            responseEntity = restTemplate.exchange(uri, httpMethod, requestEntity, responseType);

            logger.debug(LoggerConstants.PrexMarker.FLOW, "responseEntity {}", responseEntity.toString());
            logger.info(LoggerConstants.PrexMarker.FLOW, "responseEntityBody {}", responseEntity.getBody());

        } catch (final HttpClientErrorException | HttpServerErrorException error) {
            logger.error(
                    LoggerConstants.PrexError.ERR,
                    "Failed to call API  "
                            + error.getMessage()
                            + "(responseBody is: "
                            + error.getResponseBodyAsString()
                            + ")",
                    error);
            if (error.getStatusCode().toString().equalsIgnoreCase(HttpStatus.NOT_FOUND.toString())) {
                throw new NoDataFoundException(error.getMessage());
            }
            throw new ApiCallException(
                    error.getResponseBodyAsString(), error.getStatusCode().value(), uri, "");
        } catch (Exception error) {
            logger.error(LoggerConstants.PrexError.ERR, "Failed to call API  " + error.getMessage(), error);
            throw new TransactionException(TransactionErrorCode.GENERIC_ERROR, error);
        }
        validateEntity(responseEntity, uri);
        return responseEntity.getBody();
    }

    protected <T> void validateEntity(final ResponseEntity<T> responseEntity, final String uri)
            throws ApiCallException {
        if (responseEntity == null) {
            throw new ApiCallException("Entity is null!", 500, uri, "");
        }
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new ApiCallException(
                    "Error Processing the request", responseEntity.getStatusCodeValue(), uri, "100");
        }
    }

    protected HttpHeaders getDefaultHttpHeaders() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    public <T> T mapObjectData(final String request, final Class<T> responseType)
            throws JsonProcessingException {

        logger.debug(LoggerConstants.PrexMarker.FLOW, "request {}", request);
        logger.debug(LoggerConstants.PrexMarker.FLOW, "responseType is {}", responseType);

        return mapper.readValue(request, responseType);
    }
}
