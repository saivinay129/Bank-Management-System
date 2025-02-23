package com.bank_application_system.services;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank_application_system.dto.AccountInfo;
import com.bank_application_system.dto.BankResponse;
import com.bank_application_system.dto.CreditDebitRequest;
import com.bank_application_system.dto.EmailDetails;
import com.bank_application_system.dto.EnquiryRequest;
import com.bank_application_system.dto.TransactionDto;
import com.bank_application_system.dto.TransferRequest;
import com.bank_application_system.dto.UserRequest;
import com.bank_application_system.entity.User;
import com.bank_application_system.repository.UserRepository;
import com.bank_application_system.utils.AccountUtils;

@Service
public class UserServiceImplement implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionService transactionService;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        if(userRepository.existsByEmail(userRequest.getEmail())){
            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
            .accountInfo(null)
            .build();
        }
        User newUser = User.builder()
        .firstName(userRequest.getFirstName())
        .lastName(userRequest.getLastName())
        .middleName(userRequest.getMiddleName())
        .gender(userRequest.getGender())
        .address(userRequest.getAddress())
        .state(userRequest.getState())
        .country(userRequest.getCountry())
        .accountNumber(AccountUtils.generateAccountNumber())
        .accountBalance(BigDecimal.ZERO)
        .email(userRequest.getEmail())
        .phoneNumber(userRequest.getPhoneNumber())
        .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
        .status("ACTIVE")
        .build();
        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
        .recipient(savedUser.getEmail())
        .subject("ACCOUNT CREATION")
        .messageBody("Congratulations! Your Account Has Been Successfully Created.\nYour Account Details: \n "+"Account name: "+savedUser.getFirstName() +" " + savedUser.getMiddleName()+" "+savedUser.getLastName() + "\nAccount Number: " + savedUser.getAccountNumber())
        .build();
        emailService.sendEmailAlert(emailDetails);
        return BankResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
        .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
        .accountInfo(AccountInfo.builder().accountBalance(savedUser.getAccountBalance()).accountNumber(savedUser.getAccountNumber()).accountName(savedUser.getFirstName() + " " + savedUser.getMiddleName() + " " + savedUser.getLastName()).build())
        .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
            .accountInfo(null)
            .build();
        }

        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
        .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
        .accountInfo(AccountInfo.builder()
            .accountBalance(foundUser.getAccountBalance())
            .accountNumber(foundUser.getAccountNumber())
            .accountName(foundUser.getFirstName() + " " + foundUser.getMiddleName() + " " + foundUser.getLastName())
            .build())
        .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return AccountUtils.ACCOUNT_NOT_EXISTS_CODE;
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getMiddleName() + " " + foundUser.getLastName();   
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
            .accountInfo(null)
            .build();
        }

        User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        userRepository.save(userToCredit);

        TransactionDto transactionDto = TransactionDto.builder()
        .accountNumber(userToCredit.getAccountNumber())
        .transactionType("CREDIT")
        .amount(request.getAmount())
        .build();

        transactionService.saveTransaction(transactionDto);

        return BankResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_CREDITED_CODE)
        .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
        .accountInfo(AccountInfo.builder()
            .accountBalance(userToCredit.getAccountBalance())
            .accountNumber(userToCredit.getAccountNumber())
            .accountName(userToCredit.getFirstName() + " " + userToCredit.getMiddleName() + " " + userToCredit.getLastName())
            .build())
        .build();

    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExist){
            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
            .accountInfo(null)
            .build();
        }

        User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = request.getAmount().toBigInteger();
        if(availableBalance.intValue() < debitAmount.intValue()){
            return BankResponse.builder()
            .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
            .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
            .accountInfo(null)
            .build();
        }
        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(userToDebit);

        TransactionDto transactionDto = TransactionDto.builder()
        .accountNumber(userToDebit.getAccountNumber())
        .transactionType("DEBIT")
        .amount(request.getAmount())
        .build();

        transactionService.saveTransaction(transactionDto);


        return BankResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_DEBITED_CODE)
        .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
        .accountInfo(AccountInfo.builder()
            .accountBalance(userToDebit.getAccountBalance())
            .accountNumber(userToDebit.getAccountNumber())
            .accountName(userToDebit.getFirstName() + " " + userToDebit.getMiddleName() + " " + userToDebit.getLastName())
            .build())
        .build();
    }

    @Override
    public BankResponse transferAccount(TransferRequest request) {
        boolean isdestinationAccountExist = userRepository.existsByAccountNumber(request.getDestinationAccountNumber());
        if(!isdestinationAccountExist ){
            return BankResponse.builder()
            .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
            .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
            .accountInfo(null)
            .build();
        }

        User sourceAccountUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        if(request.getAmount().compareTo(sourceAccountUser.getAccountBalance()) < 0){
            return BankResponse.builder()
            .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
            .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
            .accountInfo(null)
            .build();
        }
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(sourceAccountUser);

        TransactionDto transactionDto = TransactionDto.builder()
        .accountNumber(sourceAccountUser.getAccountNumber())
        .transactionType("DEBIT")
        .amount(request.getAmount())
        .build();

        transactionService.saveTransaction(transactionDto);


        String sourceAccountName = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getMiddleName() + " " + sourceAccountUser.getLastName();
        EmailDetails debitAlert = EmailDetails.builder()
        .subject("DEBIT ALERT")
        .recipient(sourceAccountUser.getEmail())
        .messageBody("The sum of " + request.getAmount() + " has been deducted from your account! Your current balance  is " + sourceAccountUser.getAccountBalance())
        .build();

        emailService.sendEmailAlert(debitAlert);

        User destinationAccountUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));
        userRepository.save(destinationAccountUser);

        TransactionDto transactionDto2 = TransactionDto.builder()
        .accountNumber(destinationAccountUser.getAccountNumber())
        .transactionType("CREDIT")
        .amount(request.getAmount())
        .build();

        transactionService.saveTransaction(transactionDto2);

        

        EmailDetails cerditAlert = EmailDetails.builder()
        .subject("CERDIT ALERT")
        .recipient(destinationAccountUser.getEmail())
        .messageBody("The sum of " + request.getAmount() + " has been sent to  your account from " + sourceAccountName +"\n Your current balance  is " + destinationAccountUser.getAccountBalance())
        .build();

        emailService.sendEmailAlert(cerditAlert);

        return BankResponse.builder()
            .responseCode(AccountUtils.TRANSFER_SUCCESSFULL_CODE)
            .responseMessage(AccountUtils.TRANSFER_SUCCESSFULL_MESSAGE)
            .accountInfo(null)
            .build();

    }
}
