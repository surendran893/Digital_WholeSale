package com.anz.digital.wholesale.service_;

import com.anz.digital.wholesale.exception.NotFoundException;
import com.anz.digital.wholesale.model.AccountResponse;
import com.anz.digital.wholesale.model.TransactionResponse;

public interface AccountDigitalService {

    /**
     * @param traceId Used to trace the request based on id.
     * @return AccountResponse List of Accounts to be populated.
     */
    AccountResponse getAccountList(String traceId);

    /**
     * @param accountNumber list of transaction will be returned based on the account number.
     * @param traceId       Used to trace the request based on id.
     * @return TransactionResponse List of Transaction for the particular account to be populated.
     */
    TransactionResponse getTransactionList(String accountNumber, String traceId) throws NotFoundException;
}
