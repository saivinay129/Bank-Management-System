package com.bank_application_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_application_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    User findByAccountNumber(String accountNumber);
}
