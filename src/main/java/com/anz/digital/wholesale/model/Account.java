package com.anz.digital.wholesale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String accountNumber;

    private String accountName;

    private String accountType;

    private String balanceDate;

    private String currency;

    private String balance;
}
