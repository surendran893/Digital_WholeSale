package com.anz.digital.wholesale.controller;

import com.anz.digital.wholesale.exception.NoDataFoundException;
import com.anz.digital.wholesale.exception.NotFoundException;
import com.anz.digital.wholesale.model.AccountResponse;
import com.anz.digital.wholesale.model.TransactionResponse;
import com.anz.digital.wholesale.service_.AccountDigitalService;
import com.anz.digital.wholesale.util.AnzLogger;
import com.anz.digital.wholesale.util.LoggerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AnzDigitalController {

    private static final AnzLogger logger = AnzLogger.getLogger(AnzDigitalController.class);

    @Autowired
    private AccountDigitalService accountService;

    @GetMapping("/accounts")
    public AccountResponse getAccountDetails() {

        String traceId = UUID.randomUUID().toString();

        logger.info(LoggerConstants.PrexMarker.FLOW, "requesting accounts detail API with traceId " + traceId);

        return accountService.getAccountList(traceId);
    }

    @GetMapping("/transactions/{accountNumber}")
    public TransactionResponse getTransactionDetails(@PathVariable String accountNumber) throws NotFoundException {

        String traceId = UUID.randomUUID().toString();
        logger.info(LoggerConstants.PrexMarker.FLOW, "requesting Transaction details API with traceId " + traceId);

        return accountService.getTransactionList(accountNumber, traceId);
    }

}
