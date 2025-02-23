package com.bank_application_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank_application_system.entity.Transaction;
import com.bank_application_system.services.BankStatement;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/bankStatement")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionController {
    private BankStatement bankStatement;

    @GetMapping()
    public List<Transaction> generateBankstatement(@RequestParam String accountNumber, @RequestParam String startDate, @RequestParam String endDate){
        return bankStatement.generateStatement(accountNumber, startDate, endDate);
    }
}