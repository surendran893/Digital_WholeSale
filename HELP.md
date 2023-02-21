# Getting Started

* Step1:
  Download wiremock and run the below command  or Use the wiremock jar in the repo. 
  Place the  json file in mapping folder of wiremock for stubbing which is available in resource folder
* java -jar wiremock-jre8-standalone-2.35.0.jar --port 8081 --verbose

* Run the application and try with given url for the result. All are GET Method.
1. Get the Transaction for account Number -> 123-2223-212
      http://localhost:8080/api/transactions/123-2223-212
2. Get the Transaction for account Number -> 223-3323-21
      http://localhost:8080/api/transactions/223-3323-21
3. Get the Transaction for account Number -> any account number
      http://localhost:8080/api/transactions/123466 => No Data Found
4. Get the Account List for all account
      http://localhost:8080/api/accounts


