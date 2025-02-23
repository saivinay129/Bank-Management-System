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
public class UserRequest {
    @Schema(
        name = "User First Name"
    )
    private String firstName;

    @Schema(
        name = "User Last Name"
    )
    private String lastName;

    @Schema(
        name = "User Middle Name"
    )
    private String middleName;

    @Schema(
        name = "User Gender"
    )
    private String gender;

    @Schema(
        name = "User Address"
    )
    private String address;

    @Schema(
        name = "User State"
    )
    private String state;

    @Schema(
        name = "User Country"
    )
    private String country;

    @Schema(
        name = "User Email"
    )
    private String email;

    @Schema(
        name = "User Phone Number"
    )
    private String phoneNumber;

    @Schema(
        name = "User Alternative Phone Number"
    )
    private String alternativePhoneNumber;
}
