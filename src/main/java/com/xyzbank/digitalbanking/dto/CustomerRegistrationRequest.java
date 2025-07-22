package com.xyzbank.digitalbanking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotNull
    private LocalDate dob;

    @NotBlank
    private String country;

    @NotBlank
    private String accountType;

    private MultipartFile document;
}
