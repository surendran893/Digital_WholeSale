package com.anz.digital.wholesale.controller;

import com.anz.digital.wholesale.exception.NotFoundException;
import com.anz.digital.wholesale.exception.TransactionException;
import com.anz.digital.wholesale.model.AccountResponse;
import com.anz.digital.wholesale.model.TransactionResponse;
import com.anz.digital.wholesale.service_.AccountDigitalService;
import com.anz.digital.wholesale.util.AnzLogger;
import com.anz.digital.wholesale.util.LoggerConstants;
import com.anz.digital.wholesale.util.LoggerConstants.AnzError;
import com.anz.digital.wholesale.util.LoggerConstants.AnzMarker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.anz.digital.wholesale.util.TransactionErrorCode.GENERIC_ERROR;

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

        logger.info(LoggerConstants.AnzMarker.FLOW, "requesting accounts detail API with traceId " + traceId);

        try {
        	return accountService.getAccountList(traceId);
        }catch(Exception e) {
        	logger.error(AnzError.ERR, e.getMessage());
        	throw new TransactionException(GENERIC_ERROR,e); 
        }
    }

    @GetMapping("/transactions/{accountNumber}")
    public TransactionResponse getTransactionDetails(@PathVariable String accountNumber){

        String traceId = UUID.randomUUID().toString();
        logger.info(LoggerConstants.AnzMarker.FLOW, "requesting Transaction details API with traceId " + traceId);

        try {
        	return accountService.getTransactionList(accountNumber, traceId);
        }catch(TransactionException ex) {
        	logger.error(AnzError.ERR, ex.getMessage());
        	throw ex; 
        }catch(NotFoundException ex) {
        	logger.info(AnzMarker.FLOW, ex.getMessage());
        	throw ex; 
        }catch(Exception e) {
        	logger.error(AnzError.ERR, e.getMessage());
        	throw new TransactionException(GENERIC_ERROR,e); 
        }
        
        }

}
