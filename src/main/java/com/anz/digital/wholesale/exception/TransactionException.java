package com.anz.digital.wholesale.exception;

import com.anz.digital.wholesale.util.TransactionErrorCode;
import lombok.Getter;

@Getter
public class TransactionException extends RuntimeException {

    private final TransactionErrorCode errorCode;

    public TransactionException(TransactionErrorCode errorCode, Exception ex) {
        super(ex);
        this.errorCode = errorCode;
    }
}