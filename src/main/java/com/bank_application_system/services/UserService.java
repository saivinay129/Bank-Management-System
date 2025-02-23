package com.bank_application_system.services;

import com.bank_application_system.dto.BankResponse;
import com.bank_application_system.dto.CreditDebitRequest;
import com.bank_application_system.dto.EnquiryRequest;
import com.bank_application_system.dto.TransferRequest;
import com.bank_application_system.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);
    BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transferAccount(TransferRequest request);
}
