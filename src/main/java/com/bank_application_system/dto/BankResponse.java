package com.bank_application_system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {

    @Schema(
        name = "Bank Response With Code"
    )
    private String responseCode;

    @Schema(
        name = "Bank Response With Message"
    )
    private String responseMessage;

    @Schema(
        name = "Bank Response  With accountInfo"
    )
    private AccountInfo accountInfo;
}
