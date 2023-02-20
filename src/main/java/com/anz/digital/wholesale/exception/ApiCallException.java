package com.anz.digital.wholesale.exception;

/**
 * Generic exception for failing API calls.
 */
public class ApiCallException extends Exception {

    private static final long serialVersionUID = -3728361065282945603L;

    private Integer httpStatus;

    private String message;
    private String messageId;

    /**
     * the type of backend API that threw the exception
     */
    private String type;

    /**
     * the URI called
     */
    private String uri;

    public ApiCallException(final String message, Throwable err) {
        super(err);
        this.message = message;
    }

    public ApiCallException(final String message) {
        this.message = message;
    }

    public ApiCallException(
            final String message, final Integer httpStatus, final String type, final String messageId) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.type = type;
        this.messageId = messageId;
    }

    public ApiCallException(
            final String message,
            final Integer httpStatus,
            final String type,
            final String messageId,
            final String uri) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.type = type;
        this.uri = uri;
        this.messageId = messageId;
    }

    public ApiCallException(
            final String message, final Integer httpStatus, final String type, Throwable err) {
        this(message, err);
        this.httpStatus = httpStatus;
        this.type = type;
    }

    public ApiCallException(
            final String message,
            final Integer httpStatus,
            final String type,
            final String uri,
            Throwable err) {
        this(message, err);
        this.httpStatus = httpStatus;
        this.type = type;
        this.uri = uri;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(final Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
