package com.anz.digital.wholesale.util;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionErrorCode {

    DATA_FETCH_ERROR(500, "DATA_FETCH_ERROR", "unable to get the data from database", Severity.HIGH),
    DATA_MAPPING_ERROR(500, "DATA_MAPPING_ERROR", "error occurred while data conversion/mapping",
            Severity.HIGH),
    NOT_FOUND(404, "NO_DATA_FOUND", "No Data Found",
            Severity.LOW),
    GENERIC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), Severity.FATAL);


    private int code;
    private String message;
    private String description;
    private Severity severity;

}