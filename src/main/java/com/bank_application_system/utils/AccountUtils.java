package com.bank_application_system.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE  = "This user already has an account Created!";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been successfully created!";
    public static final String ACCOUNT_NOT_EXISTS_CODE = "003";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE  = "User With Provided Account Number Does Not Exists!";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_MESSAGE  = "Account Number Founded!";
    public static final String ACCOUNT_CREDITED_CODE = "005";
    public static final String ACCOUNT_CREDITED_MESSAGE  = "Amount Credited!";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE  = "INSUFFICIENT BALANCE!";
    public static final String ACCOUNT_DEBITED_CODE = "007";
    public static final String ACCOUNT_DEBITED_MESSAGE  = "Amount Debited!";
    public static final String TRANSFER_SUCCESSFULL_CODE = "008";
    public static final String TRANSFER_SUCCESSFULL_MESSAGE  = "TRANSFER SUCCESSFULL!";

    public static String generateAccountNumber(){
        Year currentYear = Year.now();
        long min = 100000000000L;
        long max = 999999999999L;
        long randNumber = (long) Math.floor(Math.random() * (max - min + 1) + min);
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(randomNumber).toString();
    }
}
