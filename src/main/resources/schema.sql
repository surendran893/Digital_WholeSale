DROP TABLE IF EXISTS ACCOUNTS;  
CREATE TABLE ACCOUNTS (  
id INT AUTO_INCREMENT  PRIMARY KEY, 
accountName VARCHAR(50) NOT NULL, 
accountNumber VARCHAR(50) NOT NULL,
accountType VARCHAR(50) NOT NULL,
balance VARCHAR(50) NOT NULL,
balanceDate VARCHAR(50) NOT NULL,
currency VARCHAR(50) NOT NULL
);  

DROP TABLE IF EXISTS TRANSACTIONS;  
CREATE TABLE TRANSACTIONS (  
id INT AUTO_INCREMENT  PRIMARY KEY,  
accountNumber VARCHAR(50) NOT NULL,
accountName VARCHAR(50) NOT NULL,
valueDate VARCHAR(50) NOT NULL,
currency VARCHAR(50) NOT NULL,
debitAmount VARCHAR(50) NOT NULL,
creditAmount VARCHAR(50) NOT NULL,
debitOrCredit VARCHAR(50) NOT NULL,
transactionNarrative VARCHAR(50) NOT NULL
);  