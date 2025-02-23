package com.bank_application_system.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequest {

    @Schema(
        name = "User Account Number"
    )
    private String accountNumber;

    @Schema(
        name = "User Amount To Credit Or Debit"
    )
    private BigDecimal amount;
}
