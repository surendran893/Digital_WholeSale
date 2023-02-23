package com.anz.digital.wholesale.controller;

import static com.anz.digital.wholesale.util.TransactionErrorCode.GENERIC_ERROR;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anz.digital.wholesale.exception.BadRequestException;
import com.anz.digital.wholesale.exception.TransactionException;
import com.anz.digital.wholesale.model.Account;
import com.anz.digital.wholesale.model.Transaction;
import com.anz.digital.wholesale.service_.AccountDigitalService;
import com.anz.digital.wholesale.util.AnzLogger;
import com.anz.digital.wholesale.util.LoggerConstants;
import com.anz.digital.wholesale.util.LoggerConstants.AnzError;

@RestController
@RequestMapping("/api")
public class AnzDigitalController {

    private static final AnzLogger logger = AnzLogger.getLogger(AnzDigitalController.class);

    @Autowired
    private AccountDigitalService accountService;

    @GetMapping("/accounts")
    public List<Account> getAccountDetails(@RequestParam int page, @RequestParam int size) throws Exception {

        String traceId = UUID.randomUUID().toString();

        logger.info(LoggerConstants.AnzMarker.FLOW, "requesting accounts detail API with traceId " + traceId);

        try {
        	Pageable pageable = requestParamValidation(page, size, traceId);
        	
        	
        	return accountService.getAccountList(pageable, traceId);
        }catch(Exception e) {
        	logger.error(AnzError.ERR, e.getMessage());
        	throw e; 
        }
    }

    @GetMapping("/transactions/{accountNumber}")
    public List<Transaction> getTransactionDetails(@PathVariable String accountNumber,@RequestParam int page, @RequestParam int size) throws Exception{

        String traceId = UUID.randomUUID().toString();
        logger.info(LoggerConstants.AnzMarker.FLOW, "requesting Transaction details API with traceId " + traceId);
        
        try {
        	Pageable pageable = requestParamValidation(page, size, traceId);
        	
        	return accountService.getTransactionList(pageable, accountNumber, traceId);
        }catch(Exception e) {
        	logger.error(AnzError.ERR, e.getMessage());
        	throw e; 
        }
        
        }
    
    private Pageable requestParamValidation(int page, int size, String traceId){
    	
    	if(page < 0) {
        	logger.error(AnzError.ERR, "Page No should not be less than 0 for traceId "+traceId);
        	throw new BadRequestException("Page No should not be less than 0");
        }else if (size <= 0) {
        	logger.error(AnzError.ERR, "Size should be greater than 0 for traceId "+traceId);
        	throw new BadRequestException("Size should be greater than 0");
        }
    	
    	Pageable pageable = PageRequest.of(page, size);
    	
    	return pageable;
    	
    }

}
