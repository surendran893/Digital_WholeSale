package com.anz.digital.wholesale.serviceImpl;

import static com.anz.digital.wholesale.util.ApplicationConstants.NO_DATA_FOUND_MESSAGE;
import static com.anz.digital.wholesale.util.TransactionErrorCode.DATA_FETCH_ERROR;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anz.digital.wholesale.entity.Accounts;
import com.anz.digital.wholesale.entity.Transactions;
import com.anz.digital.wholesale.exception.NoDataFoundException;
import com.anz.digital.wholesale.exception.TransactionException;
import com.anz.digital.wholesale.model.Account;
import com.anz.digital.wholesale.model.Transaction;
import com.anz.digital.wholesale.repository.AccountRepository;
import com.anz.digital.wholesale.repository.TransactionRepository;
import com.anz.digital.wholesale.service_.AccountDigitalService;
import com.anz.digital.wholesale.util.AnzLogger;
import com.anz.digital.wholesale.util.LoggerConstants;
import com.anz.digital.wholesale.util.LoggerConstants.AnzMarker;

@Service
public class AccountDigitalServiceImpl implements AccountDigitalService {

    private static final AnzLogger logger = AnzLogger.getLogger(AccountDigitalServiceImpl.class);

    @Autowired
    private TransactionRepository transactionRepo;
    
    @Autowired
    private AccountRepository accountRepo;
    

    /**
     * @param traceId Used to trace the request based on id.
     * @return AccountResponse List of Accounts to be populated.
     * @throws NoDataFoundException 
     */
    @Override
    public List<Account> getAccountList(Pageable pageable, String traceId) throws NoDataFoundException {
        logger.info(LoggerConstants.AnzMarker.FLOW, "processing accountList API for traceId " + traceId);

        List<Account> accountResponse  = new ArrayList<>();
        try {
        	
        	Page<Accounts> accountsList = accountRepo.findAll(pageable);

            logger.info(LoggerConstants.AnzMarker.FLOW, "processing accountList API Completed for traceId " + traceId);

            if(accountsList.getContent().isEmpty()) {
           	 logger.info(LoggerConstants.AnzMarker.FLOW, "No Data Found for traceId "+traceId);
                throw new NoDataFoundException(NO_DATA_FOUND_MESSAGE);
           }else {
        	   accountsList.getContent().forEach(action -> {
               	Account account = new Account();
               	account.setAccountName(action.getAccountName());
               	account.setAccountNumber(action.getAccountNumber());
               	account.setAccountType(action.getAccountType());
               	account.setBalance(action.getBalance());
               	account.setBalanceDate(action.getBalanceDate());
               	account.setCurrency(action.getCurrency());
               	accountResponse.add(account);
               });
               
               return accountResponse;
           }

        } catch (NoDataFoundException e) {
            logger.error(LoggerConstants.AnzError.ERR, "No Data Found for traceId " +traceId, e.getMessage());
            throw e;
        }catch (Exception e) {
            logger.error(LoggerConstants.AnzError.ERR, "Error during fetching account list "+traceId, e.getMessage());
            throw new TransactionException(DATA_FETCH_ERROR, e);
        }

    }

    /**
     * @param accountNumber list of transaction will be returned based on the account number.
     * @param traceId       Used to trace the request based on id.
     * @return TransactionResponse List of Transaction for the particular account to be populated.
     * @throws NoDataFoundException 
     */
    @Override
    public List<Transaction> getTransactionList(Pageable pageable, String accountNumber, String traceId) throws NoDataFoundException {

        logger.info(LoggerConstants.AnzMarker.FLOW, "processing account transaction List API for traceId " + traceId);
        
        try {
        	List<Transaction> transactionResponse = new ArrayList<>();
        	
        	Page<Transactions> accountsResp = transactionRepo.findByAccountNumber(pageable, accountNumber);
            
            if(accountsResp.getContent().isEmpty()) {
            	 logger.info(LoggerConstants.AnzMarker.FLOW, "No Data Found for the given account Number "+maskString(accountNumber) + " of traceId "+traceId);
                 throw new NoDataFoundException(NO_DATA_FOUND_MESSAGE);
            }else {
            	logger.info(AnzMarker.FLOW, "Total records available for the account number "+maskString(accountNumber) + " is "+accountsResp.getContent().size()+ " of traceId "+traceId);
            	
            	accountsResp.getContent().forEach(action -> {
            		Transaction transaction = new Transaction();
            		transaction.setAccountName(action.getAccountName());
            		transaction.setAccountNumber(action.getAccountNumber());
            		transaction.setCreditAmount(action.getCreditAmount());
            		transaction.setCurrency(action.getCurrency());
            		transaction.setDebitAmount(action.getDebitAmount());
            		transaction.setDebitOrCredit(action.getDebitOrCredit());
            		transaction.setTransactionNarrative(action.getTransactionNarrative());
            		transaction.setValueDate(action.getValueDate());
            		
            		transactionResponse.add(transaction);
            	});
            	return transactionResponse;
           
            }
        }catch (NoDataFoundException e) {
            logger.error(LoggerConstants.AnzError.ERR, "No Data Found for traceId "+traceId, e.getMessage());
            throw e;
        }catch (Exception e) {
            logger.error(LoggerConstants.AnzError.ERR, "Error during fetching transaction list for traceId "+traceId, e.getMessage());
            throw new TransactionException(DATA_FETCH_ERROR, e);
        }
    }
    
    public String maskString(String strText) {

		int maskLength = strText.length() - 4;

		StringBuilder sbMaskString = new StringBuilder(maskLength);

		for (int i = 0; i < maskLength; i++) {
			sbMaskString.append('X');
		}

		return strText.substring(0, 2) + sbMaskString.toString() + strText.substring(2 + maskLength);
	}

}
