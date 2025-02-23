package com.bank_application_system.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bank_application_system.dto.TransactionDto;
import com.bank_application_system.entity.Transaction;
import com.bank_application_system.repository.TransactionRepository;

@Component
public class TransactionServiceImplemention implements TransactionService{
    
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
        .transactionType(transactionDto.getTransactionType())
        .accountNumber(transactionDto.getAccountNumber())
        .amount(transactionDto.getAmount())
        .status("SUCCESS")
        .build();

        transactionRepository.save(transaction);
        System.out.println("Transaction saved successfully");
    }
}
