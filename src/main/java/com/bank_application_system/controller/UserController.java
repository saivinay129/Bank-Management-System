package com.bank_application_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank_application_system.dto.BankResponse;
import com.bank_application_system.dto.CreditDebitRequest;
import com.bank_application_system.dto.EnquiryRequest;
import com.bank_application_system.dto.TransferRequest;
import com.bank_application_system.dto.UserRequest;
import com.bank_application_system.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Management APIs")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(
        summary = "Create new User Account",
        description = "Creating a new user and assigning  an account"
    )
    @ApiResponse(
        responseCode = "201",
        description = "Http status 201 CREATED"
    )
    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @Operation(
        summary = "Balance Enquiry",
        description = "Given an account number, check how much the user has"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Http status 200 SUCCESS"
    )
    @GetMapping("balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request){
        return userService.balanceEnquiry(request);
    }


    @Operation(
        summary = "Name Enquiry",
        description = "Enquiry user Name"
    )
    @ApiResponse(
        responseCode = "202",
        description = "Http status 202 SUCCESS"
    )
    @GetMapping("nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest request){
        return userService.nameEnquiry(request);
    }



    @Operation(
        summary = "Credit",
        description = "Credit Ammount to User"
    )
    @ApiResponse(
        responseCode = "203",
        description = "Http status 203 SUCCESS"
    )
    @PostMapping("credit")
    public BankResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }


    @Operation(
        summary = "Debit",
        description = "Debit Ammount from User"
    )
    @ApiResponse(
        responseCode = "204",
        description = "Http status 204 SUCCESS"
    )
    @PostMapping("debit")
    public BankResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

    @Operation(
        summary = "Transfer",
        description = "Transfer Ammount from User TO other User"
    )
    @ApiResponse(
        responseCode = "205",
        description = "Http status 205 SUCCESS"
    )
    @PostMapping("transfer")
    public BankResponse transferAccount(@RequestBody TransferRequest request){
        return userService.transferAccount(request);
    }
}
