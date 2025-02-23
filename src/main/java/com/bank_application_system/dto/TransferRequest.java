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
public class TransferRequest {

    @Schema(
        name = "Transfer Amounts Source Account Number "
    )
    private String sourceAccountNumber;

    @Schema(
        name = "Transfer Amounts Destination Account Number "
    )
    private String destinationAccountNumber;

    @Schema(
        name = "Transfer Amount"
    )
    private BigDecimal amount;
}
