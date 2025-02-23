package com.bank_application_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_application_system.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String>{

}
