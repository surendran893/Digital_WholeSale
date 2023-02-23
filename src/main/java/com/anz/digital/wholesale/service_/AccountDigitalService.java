package com.anz.digital.wholesale.service_;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anz.digital.wholesale.exception.NoDataFoundException;
import com.anz.digital.wholesale.model.Account;
import com.anz.digital.wholesale.model.Transaction;

public interface AccountDigitalService {

    /**
     * @param pageable page and size for the search 
     * @param traceId Used to trace the request based on id.
     * @return List<Accounts> List of Accounts to be populated.
     * @throws NoDataFoundException 
     */
	List<Account> getAccountList(Pageable pageable, String traceId) throws NoDataFoundException;

    /**
     * @param pageable page and size for the search 
     * @param accountNumber list of transaction will be returned based on the account number.
     * @param traceId       Used to trace the request based on id.
     * @return List<Transaction> List of Transaction for the particular account to be populated.
     * @throws NoDataFoundException 
     */
	List<Transaction> getTransactionList(Pageable pageable, String accountNumber, String traceId) throws NoDataFoundException;
}
