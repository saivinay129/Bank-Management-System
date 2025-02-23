package com.bank_application_system.services;

import com.bank_application_system.dto.TransactionDto;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
