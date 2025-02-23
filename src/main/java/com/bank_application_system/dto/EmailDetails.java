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
public class EmailDetails {

    @Schema(
        name = "Recipient email id"
    )
    private String recipient;

    @Schema(
        name = "Banks message"
    )
    private String messageBody;

    @Schema(
        name = "email subject"
    )
    private String subject;

    @Schema(
        name = "Banks documents"
    )
    private String attachment;
}
