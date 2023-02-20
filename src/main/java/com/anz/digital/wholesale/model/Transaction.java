package com.anz.digital.wholesale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {


    private String accountNumber;

    private String accountName;

    private String valueDate;

    private String currency;

    private String debitAmount;

    private String creditAmount;

    private String debitOrCredit;

    private String transactionNarrative;
}
