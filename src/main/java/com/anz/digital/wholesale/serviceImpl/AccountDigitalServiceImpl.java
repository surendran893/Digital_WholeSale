package com.anz.digital.wholesale.serviceImpl;

import com.anz.digital.wholesale.config.RestApiClient;
import com.anz.digital.wholesale.exception.NoDataFoundException;
import com.anz.digital.wholesale.exception.NotFoundException;
import com.anz.digital.wholesale.exception.TransactionException;
import com.anz.digital.wholesale.model.AccountResponse;
import com.anz.digital.wholesale.model.TransactionResponse;
import com.anz.digital.wholesale.service_.AccountDigitalService;
import com.anz.digital.wholesale.util.AnzLogger;
import com.anz.digital.wholesale.util.LoggerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import static com.anz.digital.wholesale.util.TransactionErrorCode.DATA_FETCH_ERROR;
import static com.anz.digital.wholesale.util.TransactionErrorCode.NOT_FOUND;

@Service
public class AccountDigitalServiceImpl implements AccountDigitalService {

    private static final AnzLogger logger = AnzLogger.getLogger(AccountDigitalServiceImpl.class);

    @Value("${anz.wholesale.wiremock.stub.path:http://localhost:8081}")
    private String baseUrl;

    @Autowired
    private RestApiClient restApiClient;

    /**
     * @param traceId Used to trace the request based on id.
     * @return AccountResponse List of Accounts to be populated.
     */
    @Override
    public AccountResponse getAccountList(String traceId) {
        logger.info(LoggerConstants.PrexMarker.FLOW, "processing accountList API for traceId " + traceId);

        try {
            AccountResponse response = restApiClient.callRestApi(null, AccountResponse.class, HttpMethod.GET, baseUrl + "/stub/accounts");

            logger.info(LoggerConstants.PrexMarker.FLOW, "processing accountList API Completed for traceId " + traceId);

            return response;

        } catch (Exception e) {
            logger.error(LoggerConstants.PrexError.ERR, "Error during fetching account list ", e.getMessage());
            throw new TransactionException(DATA_FETCH_ERROR, e);
        }

    }

    /**
     * @param accountNumber list of transaction will be returned based on the account number.
     * @param traceId       Used to trace the request based on id.
     * @return TransactionResponse List of Transaction for the particular account to be populated.
     */
    @Override
    public TransactionResponse getTransactionList(String accountNumber, String traceId) throws NotFoundException {

        logger.info(LoggerConstants.PrexMarker.FLOW, "processing account transaction List API for traceId " + traceId);


        if(accountNumber.equalsIgnoreCase("123-2223-212") || accountNumber.equalsIgnoreCase("223-3323-212")){

            try {
                TransactionResponse response =
                        restApiClient.callRestApi(null, TransactionResponse.class, HttpMethod.GET, baseUrl + "/stub/transactions/" + accountNumber);

                logger.info(LoggerConstants.PrexMarker.FLOW, "processing account transaction List API Completed for traceId " + traceId);

                return response;

            } catch (Exception e) {
                logger.error(LoggerConstants.PrexError.ERR, "Error during fetching transaction list ", e.getMessage());
                throw new TransactionException(DATA_FETCH_ERROR, e);
            }

        }else{
            logger.error(LoggerConstants.PrexError.ERR, "No Data Found for the given account Number "+accountNumber);
            throw new NotFoundException("No Data Found");
        }



    }


}
